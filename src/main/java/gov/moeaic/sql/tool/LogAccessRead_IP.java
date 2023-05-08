package gov.moeaic.sql.tool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;

import com.maxmind.geoip2.exception.GeoIp2Exception;

import gov.moeaic.sql.bean.CountryCode;
import gov.moeaic.sql.bean.LogAccessIP;
import gov.moeaic.sql.controller.ToolsUtil;
import gov.moeaic.sql.dao.LogAccessService;

/* 107-05-07 用於讀取Tomcat底下的 每日localast_access，並將之寫入資料庫，每天晚上執行，預計凌晨2點發動程式
 * 字串只取200(成功)
 * 要檢查資料庫目前儲存到哪一天，也許今天25，但只分析到22號
 * 擷取-日期  網頁大類  網頁標題  中英  ID count(目前採累加，如果要變成一次一次insert再改成count=1) 
 * 
 * */
public class LogAccessRead_IP
{
	String url;
	Map<String, LogAccessIP> ipLog;
	Map<String, CountryCode> countryCode;
	ServletContext context;
	ArrayList<String> excludeIP;
	
	public LogAccessRead_IP(ServletContext context)
	{
		this.context = context;
		url = context.getRealPath("/files/GeoLite2-Country.mmdb");
	}


	public void readLogIP(Map<String, String> URList)
	{
		/* 利用日期範圍，讀取file資料夾，將每一個檔名都讀一次，符合資料庫日期之後的都可以讀取*/
		if(URList == null || URList.isEmpty()) {
			return;
		}
		
		LogAccessService service = new LogAccessService();
		countryCode = service.getCountryCode(); //取得國別代碼、中英文名稱
		excludeIP = service.getExcludeIP(); //取得排除IP
		
		ArrayList<LogAccessIP> list = new ArrayList<>();
		for(String date : URList.keySet()) {
			
			ipLog = readFile(URList.get(date), date);
			if(ipLog.isEmpty() || ipLog.size()==0) {
				continue;
			}
			System.out.println(date + " = log國家數 " + ipLog.size());
			
			//將今天所有log紀錄，都放到list內
			for(String key : ipLog.keySet()) {
				list.add(ipLog.get(key));
			}
			
			
			/* 開始存入DB --------------------------------------------------------------------------- */
			if(list != null && !list.isEmpty() && list.size() > 500000) {
				System.out.println("目前筆數 " + list.size() + "開始insert");
				service.insertIP(list);
				list.clear();
				System.out.println("insert 結束，清除list，目前筆數"+list.size());
			}
		}
		
		/* for迴圈跑完，把剩餘的資料存入DB --------------------------------------------------------------------------- */
		if(list != null && !list.isEmpty()) {
//			System.out.println(list.size());
			System.out.println("目前筆數 " + list.size() + "開始insert");
			service.insertIP(list);
			System.out.println("insert 結束，清除list，目前筆數"+list.size());
		}
	}
	
	
	/* 讀取text檔，先全部取出，然後只取200的行數，只取出首頁的ip來分析國別後放入 map統計
	 * date = 2017-12-28
	 * */
	private Map<String, LogAccessIP> readFile(String url, String date)
	{
		ipLog = new TreeMap<>();

		/* 讀檔：預防當天沒有檔案，出exception處理並傳回空的Map就可以往下一個日期前進 */
		FileReader fr = null;
		BufferedReader br = null;
		ArrayList<String> lineS = null;
		try {
			fr = new FileReader(url);
			br = new BufferedReader(fr);
			lineS = new ArrayList<>();
			while (br.ready()) {
				lineS.add(br.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
			return ipLog;
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		/* 分析每一行字串 */
		for(String line : lineS) {
			try {
				readLine(line, date);
			} catch (Exception e) {
				System.out.println("此筆連線資料分析錯誤："+date+"，"+line);
				e.printStackTrace();
			}
		}
		return ipLog;
	}


	
	
	private void readLine(String line, String date)
	{
		LogAccessIP bean = null;
		//先剔除非200（200=回應成功）
		if(line.indexOf(" 200 ") == -1) {
			return;
		}
		//剔除輸入符號、字串的網址（有urlencode %)剔除  --> type=atRy%E2%8C%A9=en / type=atTc&lang=%E5%8F%A6%E5%B0%8B%E4%BB%96%E6%96%B9
		if(line.indexOf("%") != -1) {
			return;
		}
		//只取GET請求（預防有些是HEAD） 
		if(line.indexOf("GET") == -1 ) {
			return;
		}
		//剔除內部網路連線 192.168開頭、本機127.0.0.1 ｜ 0:0:0:0:0:0:0:1、經濟部網路172.31開頭
		if(line.startsWith("192.168") || line.startsWith("172.31")) {
			return;
		}
		
		//如果遇到在排除名單內的IP，return (本機127.0.0.1 ｜ 0:0:0:0:0:0:0:1 )
		String ip = line.substring(0, line.indexOf(" - - ")).trim();
		if(excludeIP.contains(ip)) { return; }
		
		//有些IP不在表內，會回傳NULL
		String code = "";
		try {
			code = Geoip2Util.getCountry(url, ip);
		} catch (Exception e) {
			System.out.println("["+ip+"}不在國別表內 :"+line);
			return;
		}
		
		//有遇到取IP是null，可是因為有在表內所以沒出exception，所以多判斷一個
		if(code == null || "null".equals(code)) {  
			return;
		}
		
		/* 首頁 ---------------------------------------------------------------------------------------
		 * /
		 * /chinese/ 
		 * /chinese/index.jsp 
		 * */
		if(line.indexOf(" / ") != -1 || line.indexOf(" /chinese/ ") != -1 || line.indexOf(" /chinese/index.jsp ") != -1) 
		{
			if(code==null || "null".equals(code)) {
				System.out.println("code="+code);
				System.out.println(code==null);
			}
			
			String key = "ch-"+code;
			if(ipLog.get(key) == null) {
				bean = new LogAccessIP();
				bean.setLanguage("ch");
				bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
				bean.setLogPage("index");
				bean.setIpCountry(code);
				ipLog.put(key, bean);
			}
			int num = ipLog.get(key).getLogCount();
			ipLog.get(key).setLogCount(num+1);
		}
		
		if(line.indexOf(" /english/ ") != -1 || line.indexOf(" /english/index.jsp ") != -1 )
		{
			if(code==null || "null".equals(code)) {
				System.out.println("code="+code);
				System.out.println(code==null);
			}
				
			
			
			String key = "en-"+code;
			if(ipLog.get(key) == null) {
				bean = new LogAccessIP();
				bean.setLanguage("en");
				bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
				bean.setLogPage("index");
				bean.setIpCountry(code);
				ipLog.put(key, bean);
			}
			int num = ipLog.get(key).getLogCount();
			ipLog.get(key).setLogCount(num+1);
		}
	
	}
}
