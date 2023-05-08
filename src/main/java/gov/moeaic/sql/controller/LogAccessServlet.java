package gov.moeaic.sql.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.protocol.HTTP;

import gov.moeaic.sql.bean.LogAccess;
import gov.moeaic.sql.bean.LogAccessIP;
import gov.moeaic.sql.bean.LogAccessIPXCounrty;
import gov.moeaic.sql.dao.LogAccessService;

public class LogAccessServlet extends HttpServlet
{
	LogAccessService log;
	HttpSession session;
	String doThing;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		log = new LogAccessService();
		doThing = request.getParameter("doThing");
		
		if(doThing == null) {
			goToIndex(request, response);
		}else if("countrysData".equals(doThing)){
			goToCountryS(request, response);
		}else if("oneCountryData".equals(doThing) || "oneIndexCountryData".equals(doThing)){
			goToOneCountry(request, response);
		}

	}

	private void goToOneCountry(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ArrayList<LogAccessIPXCounrty> list = null;
		
		if("oneCountryData".equals(doThing)) {
			String page = request.getParameter("page");
			String type = request.getParameter("type");
			String country = request.getParameter("country");
			request.setAttribute("page", page);
			request.setAttribute("type", type);
			request.setAttribute("country", country);
			list = log.getOneCountryLogCount(page, type, country);
		}else {
			String page = request.getParameter("page");
			String language = request.getParameter("language");
			String country = request.getParameter("country");
//			System.out.println("page="+page+", language="+language+", country="+country);
			list = log.getIndexOneCountryLogCount(page, language, country);
		}
		request.setAttribute("list", list);
		request.getRequestDispatcher("/console/logAccess/logAccess_detail.jsp").forward(request, response);
		
	}

	private void goToCountryS(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map<String, String> pagenames = new HashMap<String, String>();
		pagenames.put("index", "首頁");		
		pagenames.put("about", "關於本會");		
		pagenames.put("news&business_category", "焦點消息");		
		pagenames.put("news_newAn", "最新公告");		
		pagenames.put("news_bsAn", "業務統計");		
		pagenames.put("news_stRt", "研究報告");		
		pagenames.put("business_category", "業務統計圖表");		
		pagenames.put("businessPub", "申辦業務");		
		pagenames.put("links", "相關網站");		
		pagenames.put("openData", "政府資訊公開");		
		String page = request.getParameter("page");
		String type = request.getParameter("type");
//		System.out.println("page="+page+", type="+type);
		String pagename = "";
		if("news".equals(page)) {
			pagename = pagenames.get(type);
		}else {
			pagename = pagenames.get(page);
		}
		request.setAttribute("page", page);
		request.setAttribute("type", type);
		request.setAttribute("pageName", pagename);
		request.setAttribute("list", log.getCountrySLogCount(page, type));
		request.getRequestDispatcher("/console/logAccess/logAccess_Country.jsp").forward(request, response);
	}
	
	private void goToIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		session = request.getSession();
		if(session.getAttribute("sum") == null) {
//			System.out.println("取資料");
			//總表
			Map<String, Integer> pageLogSUM = log.getSum();
			request.setAttribute("sum", pageLogSUM);
			session.setAttribute("sum", pageLogSUM);
			
			//焦點消息-新聞點擊數 三大表 
			Map<String, ArrayList<LogAccess>> map = log.getNewsIdSum();
			for(String key : map.keySet()) {  
				//key = new_ann_ch、new_ann_en、studyReport_ch、studyReport_en、business_ann_ch、business_ann_en
				request.setAttribute(key, map.get(key));
				session.setAttribute(key, map.get(key));
			}
			
			//統計起始日期
			Map<String, Date> period = log.getLogPeriod();
			for(String key : period.keySet()) { //sDate起始日  、 eDate結束日
				request.setAttribute(key, ToolsUtil.dateToChange(period.get(key), "yyyy/MM/dd"));
				session.setAttribute(key, ToolsUtil.dateToChange(period.get(key), "yyyy/MM/dd"));
			}
			
			//107-05-15 中英文首頁國別統計
			Map<String, ArrayList<LogAccessIPXCounrty>> ipCount = log.getLogSum();
			for(String key : ipCount.keySet()) {
				request.setAttribute(key, ipCount.get(key));
				session.setAttribute(key, ipCount.get(key));
			}
		}
		request.getRequestDispatcher("/console/logAccess/logAccess.jsp").forward(request, response);
	}


}
