package gov.moeaic.sql.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.moeaic.sql.bean.PagesNews;
import gov.moeaic.sql.bean.WebOrder;
import gov.moeaic.sql.bean.WebTitle;
import gov.moeaic.sql.dao.PagesService;

public class FrontWebsiteLinks extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PagesService psService = new PagesService();
		String doThing = request.getParameter("do"); // index、每一個title的id
		String lang = request.getParameter("lang") == null ? "" : request.getParameter("lang");
		
		//List< [投資臺灣 , XXX] , [在臺工作, XXXX]) >
		ArrayList<WebTitle> title = psService.getWebTitle();
		request.setAttribute("linkTitle", title);
		
		//{ [投資臺灣, List<WebOrder>] , [在臺工作, List<WebOrder>]
		Map<String, ArrayList<WebOrder>> list_map = psService.getWebOrder_map();
		request.setAttribute("list", list_map);
		
		request.setAttribute("title_ch", "相關網站");
		request.setAttribute("title_en", "Related Links");

		Map<String, WebTitle> map = psService.getWebTitle_names();
		if("index".equalsIgnoreCase(doThing)){
			String id = String.valueOf(title.get(0).getId());
			request.setAttribute("titleOne", map.get(id));
			if("en".equalsIgnoreCase(lang)){
				request.getRequestDispatcher("/english/websiteLinks.jsp").forward(request, response);				
			}else{
				request.getRequestDispatcher("/chinese/websiteLinks.jsp").forward(request, response);
			}
		}else{
			request.setAttribute("titleOne", map.get(doThing));
			if("en".equalsIgnoreCase(lang)){
				request.getRequestDispatcher("/english/websiteLinks.jsp").forward(request, response);				
			}else{
				request.getRequestDispatcher("/chinese/websiteLinks.jsp").forward(request, response);
			}
		}
		
	}
	
}
