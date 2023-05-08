package gov.moeaic.sql.tool;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;

import gov.moeaic.sql.bean.LogAccess;
import gov.moeaic.sql.controller.ToolsUtil;
import gov.moeaic.sql.dao.LogAccessService;

/* 107-01-04 用於讀取Tomcat底下的 每日localast_access，並將之寫入資料庫，每天晚上執行，預計凌晨2點發動程式
 * 字串只取200(成功)
 * 要檢查資料庫目前儲存到哪一天，也許今天25，但只分析到22號
 * 擷取-日期  網頁大類  網頁標題  中英  ID count(目前採累加，如果要變成一次一次insert再改成count=1) 
 * 
 * */

public class LogAccessRead
{
	
//	public static void main(String[] args) {
//		LogAccessRead log = new LogAccessRead();
////		log.readLog();
//		String url = "D:/logs/localhost_access_log.2017-12-28.txt";
////		log.readFile(url, "2017-12-28");
//		log.doLogAccess(ToolsUtil.dateToChange("2017/11/30", "yyyy/MM/dd"), new Date());
//	}
	
	/**********************************************************************************************/
	LogAccessService service;
//	Map<String, String> URList;
	ArrayList<String> langS = new ArrayList<>(Arrays.asList("ch","en","chinese","english"));
	ArrayList<String> pageS = new ArrayList<>(Arrays.asList("atIo","atMr","atOn","atPy","atImPn","atTc","atRy","atFr")); //關於本會
	ArrayList<String> newsIdS = new ArrayList<>(Arrays.asList("new_ann","business_ann","studyReport"));
	ArrayList<String> opIdS = new ArrayList<>(Arrays.asList("1","3","4","5","6","7","8","9","38"));
	Map<String, Map<String, LogAccess>> headerMap;
	Map<String, LogAccess> indexMap;
	Map<String, LogAccess> aboutMap;
	Map<String, LogAccess> newsMap;
	Map<String, LogAccess> newsIdMap;
	Map<String, LogAccess> business_categoryMap;
	Map<String, LogAccess> businessPubMap;
	Map<String, LogAccess> linksMap;
	Map<String, LogAccess> openDataMap;
	ServletContext context;
	ArrayList<String> excludeIP;
	
	public LogAccessRead(ServletContext context)
	{
		this.context = context;
	}
	
	
	/* LogAccessRead.java             搭配 [TB_LogAccess]           -->記錄  各頁面_每日點擊數量
	 * LogAccessRead_IP.java          搭配 [TB_LogAccessIP]         -->記錄  各IP_index_每日點擊數量
	 * LogAccessRead_IPXCountry.java  搭配 [TB_LogAccessIPXCountry] -->記錄  各IP_各頁面_每日點擊數量
	 * 
	 * 先進資料庫取最新日期，再來決定要讀取到幾號之前
	 * 若資料庫完全沒資料，就取前天日期，然後抓取昨天資料解析並輸入資料庫
	 * 
	 * 因為資料庫取得的日期是已經處理完畢的【最後一天】，這天不需讀取
	 * 而程式啟動在凌晨一點，所以當天是新的開始，LOG還無法讀，所以這【最後一天】天也不能用
	 * 
	 * 因此假設自訂txt讀取範圍是   01/01 ～ 01/31   
	 * 那日期起始要設定                   12/31 ～ 02/01
	 * */
	public void doLogAccess() {
		service = new LogAccessService();
		
		//取資料庫最後日為起始日（前一天）
		Date sqlLastDate = service.getLastLogDate();
		System.out.println("資料庫最後日="+sqlLastDate);
		//資料庫無任何一筆資料就自訂日期
		if(sqlLastDate == null) {
			sqlLastDate = ToolsUtil.dateToOneDate(new Date(), -2);
			System.out.println("自訂最後日="+sqlLastDate);
		}
		
		
		//開始進行 LogAccessRead ＆ LogAccessRead_IP
		readLog(sqlLastDate, ToolsUtil.dateToOneDate(new Date(), 0)); //資料庫最後日OR自訂起始日 、 今天的日期
		
		//109-02-23 加入LogAccessRead_IPXCountry
		LogAccessRead_IPXCountry log_IPXCountry = new LogAccessRead_IPXCountry(context);
		log_IPXCountry.doLogAccessIPXCountry(sqlLastDate, ToolsUtil.dateToOneDate(new Date(), 0)); 
	}
	
	
	
	/* 用於自定義取檔的起始日期 */
	public void doLogAccess(Date startDate, Date endDate) {
		service = new LogAccessService();
		readLog(startDate, endDate); //起始日、截止日
		
		//109-02-23 加入LogAccessRead_IPXCountry
		LogAccessRead_IPXCountry log_IPXCountry = new LogAccessRead_IPXCountry(context);
		log_IPXCountry.doLogAccessIPXCountry(startDate, endDate); 
	}	
	
	
	
	private void readLog(Date startDate, Date endDate)
	{
		// 取出 要取檔案的[日期範圍]
		Map<String, String> URList = readDateRange(startDate, endDate);
		// 取得排除IP
		excludeIP = service.getExcludeIP(); 
		
		/* 利用日期範圍，讀取file資料夾，將每一個檔名都讀一次，符合資料庫日期之後的都可以讀取*/
		ArrayList<LogAccess> list = new ArrayList<>();
		if(URList != null && !URList.isEmpty()) {
			
			for(String date : URList.keySet()) {
				/* 每一天的headerMap裡面，會列出以下所有的JavaBean：  Map[header , Map<細類代稱,JavaBean>]
				 * 	ex:[關於本會-下面6+6個JavaBean紀錄該天中英網頁的點擊數]、[焦點消息-下面3+2個JavaBean紀錄新聞三頁的點擊數]、
				 *     [焦點消息文章數-下面N個JavaBean紀錄每一個新聞頁面的點擊數]、[業務統計圖表-下面1+1個JavaBean記錄圖表頁面的點擊數]、
				 *     [申辦業務-下面9+9記錄每一個申辦業務頁面的點擊數]、[相關網站-下面+1+個JavaBean紀錄中英網頁的點擊數]
				 *     [政府資訊公開-下面1個JavaBean紀錄每天中文頁面的點擊數]
				 * 
				 * 先把每一天的所有JavaBean取出，然後全部放入 ArrayList中再一口氣存入DB
				 */
				Map<String, Map<String, LogAccess>> headerMap = readFile(URList.get(date), date);
				
				
				if(headerMap.isEmpty() || headerMap.size()==0) {
					continue;
				}
				System.out.println(date);
//				System.out.println(headerMap.keySet());
				//headerMap包含 {news[各細項的點擊數map], openData, newsId, business_category, about, index, businessPub, links}
				for(String key : headerMap.keySet()) {
					
					//處理第一層map，把每一大類的map取出(首頁、關於本會、news、申辦業務、公開資訊、網站連結)
					Map<String, LogAccess> map = headerMap.get(key);
					System.out.println(key+"="+map.size());
					
					//處理第二層，每一map內會可能會有-細項各頁面&中英文 各種頁面的點擊JavaBean。將每一map內的list組合，都一同放到一個空的list內
					for(String key2 : map.keySet()) {
						list.add(map.get(key2));
					}
				}
				//System.out.println();
			}//end for
		}//end if
		
		/* 開始存入DB --------------------------------------------------------------------------- */
		if(list != null && !list.isEmpty()) {
			System.out.println(list.size());
			service.insert(list);
		}
		System.out.println("================================================================");
		System.out.println("1.各頁面點擊數量  分析完成");
		System.out.println("================================================================");
		
		/* 107-05-09 新增讀取IP程式
		 * 109-08-31 因為這個只分析首頁-國別-IP-點擊數(未在國別表內的也沒秀)，在第三個IPXCountry正式上線後，就將第二個功能關閉
		LogAccessRead_IP ip = new LogAccessRead_IP(context);
		ip.readLogIP(URList);
		
		System.out.println("================================================================");
		System.out.println("2.首頁點擊數量  分析完成");
		System.out.println("================================================================");
		*/
	}

	
	private Map<String, String> readDateRange(Date startDate, Date endDate)
	{
		Map<String, String> list = new TreeMap<>();
		
		/* 算出sql最後日期與昨天相差幾天，再取出所有要用的日期，跟URL組合後放入List*/
		int range = ToolsUtil.daysOfTwo(startDate, ToolsUtil.dateToOneDate(endDate, -1));
		System.out.println("range="+range);
		if(range == 0) {
			return list;
		}

//		String ver = PropUtil.getPropertyValue("tomcat", "moeaic.properties");
//		String URL1 = "C:/Program Files/Apache Software Foundation/"+ver+"/logs/localhost_access_log.";
		
		//C:\Java_Setting64\workspace_oxygen0315\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\moeaic\
		String URL1 = context.getRealPath("/")+"../../logs/localhost_access_log.";
		String URL2 = ".txt";
		for(int i=1; i<=range; i++) { //包含 sqlLastDate 當天
			String day_str = ToolsUtil.dateToChange( ToolsUtil.dateToOneDate(startDate, i)  , "yyyy-MM-dd");
			list.put(day_str, URL1 + day_str + URL2);
//			System.out.println(URL1 + day_str + URL2);
		}
		
		return list;
	}
	
	
	

	/* 讀取text檔，先全部取出，然後只取200的行數，
	 * 再篩選出是點擊header的urlPattern，放入各個key裡面（關於本會、最新消息、業務統計圖表、申辦業務、相關網站、政府資訊公開）
	 * 再拆解成JavaBean要的資料set進去 (PS：最新消息要再多記錄點擊了哪一個新聞id)
	 * date = 2017-12-28
	 * */
	private Map<String, Map<String, LogAccess>> readFile(String url, String date)
	{
		
		headerMap = new HashMap<>();

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
			return headerMap;
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
		indexMap = new HashMap<>();
		aboutMap = new HashMap<>();
		newsMap = new HashMap<>();
		newsIdMap = new HashMap<>();
		business_categoryMap = new HashMap<>();
		businessPubMap = new HashMap<>();
		linksMap = new HashMap<>();
		openDataMap = new HashMap<>();
		for(String line : lineS) {
			try {
				readLine(line, date);
			} catch (Exception e) {
				System.out.println("此筆連線資料分析錯誤："+date+"，"+line);
				e.printStackTrace();
			}
		}
		return headerMap;
	}

	
	
	

	private void readLine(String line, String date)
	{
		LogAccess bean = null;
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
		//如果遇到在排除名單內的IP，return (本機127.0.0.1 ｜ 0:0:0:0:0:0:0:1)
		String ip = line.substring(0, line.indexOf(" - - ")).trim();
		if(excludeIP.contains(ip)) {
			return;
		}
		
		/* 首頁 ---------------------------------------------------------------------------------------
		 * /chinese/
		 * / 
		 * /chinese/index.jsp */
		if(line.indexOf(" / ") != -1 || line.indexOf(" /chinese/ ") != -1 || line.indexOf(" /chinese/index.jsp ") != -1) {
			String key = "index_ch";
			if(indexMap.get(key) == null) {
				bean = new LogAccess();
				bean.setLanguage("ch");
				bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
				bean.setLogPage("index");
				bean.setLogPage_type("");
				indexMap.put(key, bean);
			}
			int num = indexMap.get(key).getLogCount();
			indexMap.get(key).setLogCount(num+1);
			
			headerMap.put("index", indexMap); //只要有新增一筆，就再覆蓋回headerMap裡面
		}
		
		if(line.indexOf(" /english/ ") != -1 || line.indexOf(" /english/index.jsp ") != -1) {
			String key = "index_en";
			if(indexMap.get(key) == null) {
				bean = new LogAccess();
				bean.setLanguage("en");
				bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
				bean.setLogPage("index");
				bean.setLogPage_type("");
				indexMap.put(key, bean);
			}
			int num = indexMap.get(key).getLogCount();
			indexMap.get(key).setLogCount(num+1);
			
			headerMap.put("index", indexMap); //只要有新增一筆，就再覆蓋回headerMap裡面			
		}
		
		
		/* 關於本會 ------------------------------------------------------------------------------------*/
		if(line.indexOf("about.view")!=-1) {
			/* 拆解urlPattern：
			 * about底下，要再細分
			 * 1.中英文 
			 * 2.認識本會、認識首長、組織與職掌、本會政策、施政計畫、交通位置
				 * 
				 * 原始資料列：
			 * 157.55.39.84 - - [28/Dec/2017:00:05:15 +0800] "GET /about.view?type=atImPn&lang=en HTTP/1.1" 200 20666
			 */
			line = line.substring(line.indexOf("about.view?")+11, line.indexOf(" HTTP"));//type=atImPn&lang=en
			line = line.trim();
			
//			System.out.println(line);
			
			//如果沒有&將字串分開，或沒有兩個=，表示又是被惡搞過的字串，就直接進行下一迴圈；有就將剩餘字串切割 --> type=atImPn&lang=en
			if(line.indexOf("&") == -1 || StringUtils.countMatches(line, "=") != 2) { 
				return;
			}
			String[] ary = null;
			String type = "";
			String lang = "";
			try {
				ary = line.split("&");
				type = ary[0];
				lang = ary[1]; //lang=ch&  有這種判斷字串，所以在上一個if先篩選，並在此處做exception
			} catch (Exception e) {
				System.out.println("ary數量不足，數量="+ary.length+",字串["+line+"]");
				e.printStackTrace();
			}
		
			
			//如果沒有"="，表示網址被亂改過，就不解析這條網址直接換下一個，有就擷取字串，並將ch全調整成小寫（有些ch是大寫）
			if(type.indexOf("=") == -1 || lang.indexOf("=") == -1){ 
				return;
			}
			type = type.substring(type.indexOf("=")+1); 
			lang = lang.substring(lang.indexOf("=")+1).toLowerCase(); 
			
			
			if(langS.contains(lang) && pageS.contains(type)) {
				String key = "about_"+lang+"_"+type;
				if(aboutMap.get(key) == null) {
					bean = new LogAccess();
					bean.setLanguage(lang);
					bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
					bean.setLogPage("about");
					bean.setLogPage_type(type);
					aboutMap.put(key, bean);
				}
				int num = aboutMap.get(key).getLogCount();
				aboutMap.get(key).setLogCount(num+1);
				
				headerMap.put("about", aboutMap); //只要有新增一筆，就再覆蓋回headerMap裡面
			}
		}//end if
		
		
		
		/* 最新消息、業務統計、研究報告 ------------------------------------------------------------------------------
		 * /chinese/news_newAn.jsp | /chinese/news_bsAn.jsp | /chinese/news_stRt.jsp
		 * 先載入jsp頁面，再用dataTable去發動Ajax取得資料。所以先處理載入jsp頁面，計算有幾次點擊 - 
		 * 下一個if再來計算點擊文章的次數 --> /news.view?do=data&id=1193&lang=ch&type=new_ann
		 */
		if(line.indexOf("chinese")!= -1 || line.indexOf("english")!= -1) { //網址有chinese 或 english的，進來解析，有些是index.jsp首頁
			if(line.indexOf("news_newAn.jsp")!= -1 || line.indexOf("news_bsAn.jsp")!= -1 || line.indexOf("news_stRt.jsp")!= -1) {
				/* 拆解urlPattern：
				 * news底下，要再細分
				 * 1.中英文 
				 * 2.最新公告、業務統計、研究報告
				 * 
				 * 原始資料列：
				 * 47.88.224.11 - - [28/Dec/2017:23:25:24 +0800] "GET /chinese/news_bsAn.jsp HTTP/1.1" 200 21972
				 */
//				System.out.println(line);
				if(line.indexOf("chinese/news_newAn.jsp")!= -1) {
					String key = "news_ch_news_newAn";
					if(newsMap.get(key) == null) {
						bean = new LogAccess();
						bean.setLanguage("ch");
						bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
						bean.setLogPage("news");
						bean.setLogPage_type("news_newAn");
						newsMap.put(key, bean);
					}
					int num = newsMap.get(key).getLogCount();
					newsMap.get(key).setLogCount(num+1);
					headerMap.put("news", newsMap); //只要有新增一筆，就再覆蓋回headerMap裡面
				}

				if(line.indexOf("chinese/news_bsAn.jsp")!= -1) {
					String key = "news_ch_news_bsAn";
					if(newsMap.get(key) == null) {
						bean = new LogAccess();
						bean.setLanguage("ch");
						bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
						bean.setLogPage("news");
						bean.setLogPage_type("news_bsAn");
						newsMap.put(key, bean);
					}
					int num = newsMap.get(key).getLogCount();
					newsMap.get(key).setLogCount(num+1);
					headerMap.put("news", newsMap); //只要有新增一筆，就再覆蓋回headerMap裡面
				}

				if(line.indexOf("chinese/news_stRt.jsp")!= -1) {
					String key = "news_ch_news_stRt";
					if(newsMap.get(key) == null) {
						bean = new LogAccess();
						bean.setLanguage("ch");
						bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
						bean.setLogPage("news");
						bean.setLogPage_type("news_stRt");
						newsMap.put(key, bean);
					}
					int num = newsMap.get(key).getLogCount();
					newsMap.get(key).setLogCount(num+1);
					headerMap.put("news", newsMap); //只要有新增一筆，就再覆蓋回headerMap裡面
				}
				
				if(line.indexOf("english/news_newAn.jsp")!= -1) {
					String key = "news_en_news_newAn";
					if(newsMap.get(key) == null) {
						bean = new LogAccess();
						bean.setLanguage("en");
						bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
						bean.setLogPage("news");
						bean.setLogPage_type("news_newAn");
						newsMap.put(key, bean);
					}
					int num = newsMap.get(key).getLogCount();
					newsMap.get(key).setLogCount(num+1);
					headerMap.put("news", newsMap); //只要有新增一筆，就再覆蓋回headerMap裡面
				}
				
				if(line.indexOf("english/news_bsAn.jsp")!= -1) {
					String key = "news_en_news_bsAn";
					if(newsMap.get(key) == null) {
						bean = new LogAccess();
						bean.setLanguage("en");
						bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
						bean.setLogPage("news");
						bean.setLogPage_type("news_bsAn");
						newsMap.put(key, bean);
					}
					int num = newsMap.get(key).getLogCount();
					newsMap.get(key).setLogCount(num+1);
					headerMap.put("news", newsMap); //只要有新增一筆，就再覆蓋回headerMap裡面
				}
				
				if(line.indexOf("english/news_stRt.jsp")!= -1) {
					String key = "news_en_news_stRt";
					if(newsMap.get(key) == null) {
						bean = new LogAccess();
						bean.setLanguage("en");
						bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
						bean.setLogPage("news");
						bean.setLogPage_type("news_stRt");
						newsMap.put(key, bean);
					}
					int num = newsMap.get(key).getLogCount();
					newsMap.get(key).setLogCount(num+1);
					headerMap.put("news", newsMap); //只要有新增一筆，就再覆蓋回headerMap裡面
				}
			}
		}
		
		/* 最新消息、業務統計、研究報告 ------------------------------------------------------------------------------
		 * 計算點擊文章的次數 --> /news.view?do=data&id=1193&lang=ch&type=new_ann
		 */
		
		if(line.indexOf("news.view?do=data&") != -1) { //最新消息、業務統計、研究報告 - 文章點擊率
			/* 拆解urlPattern：
			 * news底下，要再細分
			 * 1.中英文 
			 * 2.最新公告new_ann、業務統計business_ann、研究報告studyReport 的文章id
			 * 
			 * 原始資料列：
			 * 207.46.13.69 - - [28/Dec/2017:23:28:35 +0800] "GET /news.view?do=data&id=1211&lang=ch&type=new_ann HTTP/1.1" 200 44193
			 */
			line = line.substring(line.indexOf("news.view?do=data&")+18, line.indexOf(" HTTP")); //id=1211&lang=ch&type=new_ann
			line = line.trim();
//			System.out.println(line);
			
			
			//如果沒有&將字串分開或沒有兩個=，表示又是被惡搞過的字串，就直接進行下一迴圈；有就將剩餘字串切割 --> id=1211&lang=ch&type=new_ann
			if(StringUtils.countMatches(line, "&") != 2 || StringUtils.countMatches(line, "=") != 3) {
				return;
			}
			String[] ary = null;
			String id = "";
			String lang = "";
			String type = "";
			try {
				ary = line.split("&");
				id = ary[0];
				lang = ary[1];
				type = ary[2];
			} catch (Exception e) {
				System.out.println("ary數量不足，數量="+ary.length+",字串["+line+"]");
				e.printStackTrace();
			}				
			
			
			//如果沒有"="，表示網址被亂改過，就不解析這條網址直接換下一個，有就擷取字串，並將ch全調整成小寫（有些ch是大寫）
			if(id.indexOf("=") == -1 || type.indexOf("=") == -1 || lang.indexOf("=") == -1){ 
				return;
			}
			id = id.substring(id.indexOf("=")+1);
			type = type.substring(type.indexOf("=")+1); 
			lang = lang.substring(lang.indexOf("=")+1).toLowerCase(); 

			
			if(langS.contains(lang) && newsIdS.contains(type) && Integer.valueOf(id) > 0) {
				String key = "newsId_"+lang+"_"+id;
				if(newsIdMap.get(key) == null) {
					bean = new LogAccess();
					bean.setLanguage(lang);
					bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
					bean.setLogPage("newsId");
					bean.setLogNewsId(ToolsUtil.parseInt(id));
					bean.setLogPage_type(type);
					newsIdMap.put(key, bean);
				}
				int num = newsIdMap.get(key).getLogCount();
				newsIdMap.get(key).setLogCount(num+1);
				headerMap.put("newsId", newsIdMap); //只要有新增一筆，就再覆蓋回headerMap裡面
			}					
		}
		
		
		
		/* 業務統計 -----------------------------------------------------------------------------------------------*/
		if(line.indexOf("business_category.view?") != -1) { //業務統計圖表
			
			/* 這裡只需要判斷 中英文，其餘都不用判斷 */
			if(line.indexOf("lang") == -1) {
				return;
			}
			
			line = line.substring(line.indexOf("view?")+5, line.indexOf(" HTTP")); //lang=ch&seq=2  或  seq=0&lang=ch
			line = line.trim(); 
			String lang = line.substring(line.indexOf("lang")+5, line.indexOf("lang")+7).toLowerCase(); //lang=ch&seq=2
			
			if(langS.contains(lang)) {
				String key = "business_category_"+lang;
				if(business_categoryMap.get(key) == null) {
					bean = new LogAccess();
					bean.setLanguage(lang);
					bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
					bean.setLogPage("business_category");
					bean.setLogPage_type("");
					business_categoryMap.put(key, bean);
				}
				int num = business_categoryMap.get(key).getLogCount();
				business_categoryMap.get(key).setLogCount(num+1);
				
				headerMap.put("business_category", business_categoryMap); //只要有新增一筆，就再覆蓋回headerMap裡面
			}
		}
		
		/* 申辦業務 -----------------------------------------------------------------------------------------------*/
		if(line.indexOf("businessPub.view?lang") != -1) { 
			/* 拆解urlPattern：
			 * businessPub底下，要再細分
			 * 1.中英文 
			 * 2.僑外來臺投資 1、陸資來臺投資 3、國外(含港澳地區)投資 4、對中國大陸投資 5、創業家簽證 6、經濟部推薦申請歸化國籍之高級專業人才 38
			 *   大陸地區產業技術引進 7、外籍人士來臺 8、大陸人士來臺 9
			 * 
			 * 原始資料列：
			 * 61.216.1.184 - - [28/Dec/2017:13:17:07 +0800] "GET /businessPub.view?lang=ch&op_id_one=5&tab=0 HTTP/1.1" 200 132123
			 */
			line = line.substring(line.indexOf("view?")+5, line.indexOf(" HTTP"));
			/* parameter有三種組合
			 * 從header進入：        			lang=ch&op_id_one=1
			 * 進畫面後點左側流程圖：			type=flow&lang=ch&op_id_one=3 (進來直接排除了，因為有流程圖的業務不多，排除省一些判斷）
			 * 進畫面後點左側各申辦業務_文件分類：lang=ch&op_id_one=3&tab=1
			 * 所以再截一次字串，只取從lang開頭的字串  lang=ch&op_id_one=1。然後再判斷尾巴有沒有tab將之刪除
			 * */
			line = line.substring(line.indexOf("lang"));
			if(line.indexOf("&tab") != -1) {
				line = line.substring(0, line.indexOf("&tab"));
			}
			
			//如果沒有&將字串分開或沒有兩個=，表示又是被惡搞過的字串，就直接進行下一迴圈；有就將剩餘字串切割 --> lang=ch&op_id_one=1
			if(line.indexOf("&") == -1 || StringUtils.countMatches(line, "=") != 2) { 
				return;
			}
			String[] ary = null;
			String lang = "";
			String opId = "";
			try {
				ary = line.split("&");
				lang = ary[0];
				opId = ary[1]; //lang=ch&  有這種判斷字串，所以在上一個if先篩選，並在此處做exception
			} catch (Exception e) {
				System.out.println("ary數量不足，數量="+ary.length+",字串["+line+"]");
				e.printStackTrace();
			}

			
			//如果沒有"="，表示網址被亂改過，就不解析這條網址直接換下一個，有就擷取字串，並將ch全調整成小寫（有些ch是大寫）
			if(lang.indexOf("=") == -1 || opId.indexOf("=") == -1){ 
				return;
			}
			lang = lang.substring(lang.indexOf("=")+1).toLowerCase(); 
			opId = opId.substring(opId.indexOf("=")+1); 
			
			
			if(langS.contains(lang) && opIdS.contains(opId)) {
				String key = "businessPub_"+lang+"_"+opId;
				if(businessPubMap.get(key) == null) {
					bean = new LogAccess();
					bean.setLanguage(lang);
					bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
					bean.setLogPage("businessPub");
					bean.setLogPage_type(opId);
					businessPubMap.put(key, bean);
				}
				int num = businessPubMap.get(key).getLogCount();
				businessPubMap.get(key).setLogCount(num+1);
				
				headerMap.put("businessPub", businessPubMap); //只要有新增一筆，就再覆蓋回headerMap裡面
			}
			
		}
		
		/* 相關網站 ---------------------------------------------------------------------------------------------*/
		if(line.indexOf("links.view?do") != -1) {
			/* 拆解urlPattern：
			 * links底下，要再細分
			 * 1.中英文。英文會加 lnag=en字串，中文沒有
			 *					
			 * 原始資料列：
			 * 61.228.249.84 - - [28/Dec/2017:01:17:42 +0800] "GET /links.view?do=index HTTP/1.1" 200 41994
			 */					
//			System.out.println(line);
			String lang = "ch";
			if(line.indexOf("lang=en") != -1) {
				lang = "en";
			}
			
			String key = "links_"+lang;
			if(linksMap.get(key) == null) {
				bean = new LogAccess();
				bean.setLanguage(lang);
				bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
				bean.setLogPage("links");
				bean.setLogPage_type("");
				linksMap.put(key, bean);
			}
			int num = linksMap.get(key).getLogCount();
			linksMap.get(key).setLogCount(num+1);
			
			headerMap.put("links", linksMap); //只要有新增一筆，就再覆蓋回headerMap裡面
		}

		/* 政府資訊公開 -----------------------------------------------------------------------------------------*/
		if(line.indexOf("openData.view") != -1) { 
			/* 拆解urlPattern：
			 * openData：
			 * 1.只有中文
			 * 2.細項分類是用html錨點所以無法傳回Servlet，所以只能記錄到openData.view，其餘不列入
			 *					
			 * 原始資料列：
			 * 40.77.167.44 - - [28/Dec/2017:00:51:26 +0800] "GET /openData.view HTTP/1.1" 200 41906
			 * 157.55.39.84 - - [28/Dec/2017:00:25:12 +0800] "GET /openData.view;do=news&id=35&type=20 HTTP/1.1" 200 41906
			 */	
//			System.out.println(line);
			
			String key = "openData";
			if(openDataMap.get(key) == null) {
				bean = new LogAccess();
				bean.setLanguage("ch");
				bean.setLogDate(ToolsUtil.dateToChange(date, "yyyy-MM-dd"));
				bean.setLogPage("openData");
				bean.setLogPage_type("");
				openDataMap.put(key, bean);
			}
			int num = openDataMap.get(key).getLogCount();
			openDataMap.get(key).setLogCount(num+1);
			
			headerMap.put("openData", openDataMap); //只要有新增一筆，就再覆蓋回headerMap裡面
		}
		
	}





}
