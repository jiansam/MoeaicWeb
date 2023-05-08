package gov.moeaic.sql.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.moeaic.sql.bean.Pages;
import gov.moeaic.sql.dao.PagesService;

public class FrontPagesServlet extends HttpServlet
{
	
	Map<String, String> type_map = null;
	Map<String, String> title_map_ch = null;
	Map<String, String> title_map_en = null;
	
	@Override
	public void init() throws ServletException
	{
		type_map = new HashMap<>();
		type_map.put("atIo", "about_intro");
		type_map.put("atMr", "about_menager");
		type_map.put("atOn", "about_organization");
		type_map.put("atPy", "about_policy");
		type_map.put("atImPn", "about_imple_plan");
		type_map.put("atTc", "about_traffic");
		type_map.put("atRy", "about_responsibility");
		type_map.put("atFr", "about_fuDir");
		type_map.put("ayOn", "apply_online");
		type_map.put("aySh", "apply_search");
		type_map.put("atfe", "attachedFile");
		type_map.put("pvay", "privacy");
		type_map.put("ctIf", "contact_info"); 
		type_map.put("ctAy", "contact_apply");
		
		title_map_ch = new HashMap<>();
		title_map_ch.put("atIo", "認識本會");
		title_map_ch.put("atMr", "認識首長");
		title_map_ch.put("atOn", "組織與執掌");
		title_map_ch.put("atPy", "本會政策");
		title_map_ch.put("atImPn", "施政計畫");
		title_map_ch.put("atTc", "交通位置");
		title_map_ch.put("pvay", "隱私權及安全政策");
		title_map_ch.put("ctIf", "聯絡資訊");
		title_map_ch.put("ctAy", "陳情管道");
		
		title_map_en = new HashMap<>();
		title_map_en.put("atIo", "The MOEAIC");
		title_map_en.put("atOn", "Organization");
		title_map_en.put("atPy", "Policies");
		title_map_en.put("atImPn", "Implementation Plan");
		title_map_en.put("atTc", "Map");
		title_map_en.put("atRy", "Responsibility");
		title_map_en.put("atFr", "Future Directions");
//		title_map_en.put("pvay", "隱私權及安全政策");
//		title_map_en.put("ctIf", "聯絡資訊");
//		title_map_en.put("ctAy", "陳情管道");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String key = request.getParameter("type");
		String language = request.getParameter("lang");
		PagesService pService = new PagesService();
		Pages pagesOne = pService.getPages(type_map.get(key), language);
		request.setAttribute("pagesOne", pagesOne);
		if("CH".equalsIgnoreCase(language)){
			
			request.setAttribute("title_ch", title_map_ch.get(key));
			if ("pvay".equalsIgnoreCase(key)) {
				request.getRequestDispatcher("/chinese/privacy_policy.jsp").forward(request, response);
			}else if ("ctIf".equalsIgnoreCase(key) || "ctAy".equalsIgnoreCase(key)) {
				request.getRequestDispatcher("/chinese/contactUs.jsp").forward(request, response);
			}else{
				request.getRequestDispatcher("/chinese/about.jsp").forward(request, response);
			}
			
		}else {
			request.setAttribute("title_en", title_map_en.get(key));
			request.getRequestDispatcher("/english/about.jsp").forward(request, response);
		}
	}

	
}
