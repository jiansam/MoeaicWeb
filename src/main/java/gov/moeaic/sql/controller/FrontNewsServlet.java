package gov.moeaic.sql.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dasin.cryptography.dCipher;

import com.google.gson.Gson;

import gov.moeaic.sql.bean.PagesNews;
import gov.moeaic.sql.bean.PagesNewsFile;
import gov.moeaic.sql.dao.PagesService;

public class FrontNewsServlet extends HttpServlet
{
	
	Map<String, String> type_map = null;
	Map<String, String> title_map_ch = null;
	Map<String, String> title_map_en = null;
	PagesService pService = null;
	
	@Override
	public void init() throws ServletException
	{
		type_map = new HashMap<>();
		type_map.put("newsAn", "new_ann");
		type_map.put("bsAn", "business_ann");
		type_map.put("stRt", "studyReport");
		
		title_map_ch = new HashMap<>();
		title_map_ch.put("new_ann", "最新公告");
		title_map_ch.put("business_ann", "業務統計");
		title_map_ch.put("studyReport", "研究報告");
		
		title_map_en = new HashMap<>();
		title_map_en.put("new_ann", "Latest News");
		title_map_en.put("business_ann", "Statistics");
		title_map_en.put("studyReport", "Publications");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String doThing = request.getParameter("do");
		pService = new PagesService();
		if("list".equalsIgnoreCase(doThing)){
			toIndex(request, response);
		}
		
		else if("data".equalsIgnoreCase(doThing)){
			toContent(request, response);
		}
	}

	private void toIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String key = request.getParameter("type");
		String type = type_map.get(key);
		String lang = request.getParameter("lang");
		String keyword = request.getParameter("keyword");
		String date_from = request.getParameter("date_from");
		String date_to = request.getParameter("date_to");

		ArrayList<PagesNews> news = pService.getFrontPagesNewsFiltered(type, lang, date_from, date_to, keyword);
		//ArrayList<PagesNews> news = pService.getFrontPagesNews(type, lang);
		
		// DataTable ajax寫法
		ArrayList<String[]> group = new ArrayList<>();
		for(PagesNews bean : news){
			
			String[] tr = new String[2];
			String name = "<span style='color:#FF4000;'><i class='fa fa-caret-right' aria-hidden='true'></i></span> "
							+ "<a href='"+request.getContextPath()+"/news.view?do=data&id="+bean.getId()+"&lang="+lang+"&type="+type+"'";
			
			if("ch".equalsIgnoreCase(lang)){
				name = name + "title='"+bean.getCh_title()+"'>"+ bean.getCh_title() + "</a>";
			}else{
				name = name + "title='"+bean.getEn_title()+"'>"+ bean.getEn_title() + "</a>";
			}
			
			if(bean.getIsNew()){
				name = name + "  <img src='/images/new.gif' alt='new' title='new' style='vertical-align:baseline;'>";
			}
			tr[0] = name;
			tr[1] = bean.getPublish_date_AD();
			group.add(tr);
		}
		Map<String, ArrayList<String[]>> map = new HashMap<>();
		map.put("data", group);
		Gson gson = new Gson();
		String listToJson = gson.toJson(map);
		
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(listToJson);
		out.close();
		return;
		
		// 前端EL寫法：因為資料筆數太多，改用ajax
//		request.setAttribute("news", news);
//		request.setAttribute("title_ch", title_map_ch.get(type));
//		request.setAttribute("title_en", title_map_en.get(type));
//		if("en".equalsIgnoreCase(lang)){
//			request.getRequestDispatcher("/english/news.jsp").forward(request, response);			
//		}else{
//			request.getRequestDispatcher("/chinese/news.jsp").forward(request, response);
//		}
		
	}

	private void toContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		String type = request.getParameter("type");
		String lang = request.getParameter("lang") == null ? "" : request.getParameter("lang");
		PagesNews newsOne = pService.getFrontPagesNews_One(id , lang);
		ArrayList<PagesNewsFile> files = pService.getPagesNewsFile(id);
		ArrayList<PagesNewsFile> files_ch = new ArrayList<>();
		ArrayList<PagesNewsFile> files_en = new ArrayList<>();
		//替file的id編碼
		for(PagesNewsFile bean : files){
			String id_str = dCipher.encrypt(String.valueOf(bean.getId()));
			bean.setId_str(id_str);
			if("ch".equalsIgnoreCase(bean.getFile_lang())){
				files_ch.add(bean);
			}else{
				files_en.add(bean);
			}
		}
		request.setAttribute("newsOne", newsOne);
		request.setAttribute("title_ch", title_map_ch.get(type));
		request.setAttribute("title_en", title_map_en.get(type));
		request.setAttribute("files_ch", files_ch);
		request.setAttribute("files_en", files_en);
		

		if("en".equalsIgnoreCase(lang)){
			request.getRequestDispatcher("/english/newsdata.jsp").forward(request, response);			
		}else{
			request.getRequestDispatcher("/chinese/newsdata.jsp").forward(request, response);
		}
		
	}
}
