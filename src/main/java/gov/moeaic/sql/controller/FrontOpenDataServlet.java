package gov.moeaic.sql.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.moeaic.sql.bean.OpenData;
import gov.moeaic.sql.bean.OpenDataFile;
import gov.moeaic.sql.dao.PagesService;
import gov.moeaic.web.bean.OptionManager;

public class FrontOpenDataServlet extends HttpServlet
{
	
	PagesService psService = null;
	OptionManager options = null;
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		psService = new PagesService();
		options = (OptionManager)request.getServletContext().getAttribute("optionManager");
		String doThing = request.getParameter("do");
		
		if(doThing == null){
			goIndex(request, response);
		}
		
		else if(doThing != null && "news".equalsIgnoreCase(doThing)){
			selectNews(request, response);
		}
		
		
	}

	private void goIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ArrayList<OpenData> list = psService.getFrontOpenDataByType();
		ArrayList<String> list_name = new ArrayList<>();
		Map<String, ArrayList<OpenData>> list_map = new HashMap<>();
		
		ArrayList<Map<String, String>> maps = new ArrayList<>();
		for(OpenData bean : list){
			String type = String.valueOf(bean.getType());
			String type_name = options.get("opendata_type", type).getName();
			
			/*  建立 list_name & list_map
			 *   list_name[法規及行政規則, 本會組織、職掌及聯絡方式,...] 
			 *   list_map [{法規及行政規則,list}, {本會組織、職掌及聯絡方式,list}, ...]
			 */
			if(list_map.get(type_name) == null){
				list_map.put(type_name, new ArrayList<>());
				list_name.add(type_name);
				
				Map<String, String> listName_map = new HashMap<>();
				listName_map.put("tag", type);
				listName_map.put("name", type_name);
				maps.add(listName_map);
			}
			
			list_map.get(type_name).add(bean);
		}
		
		request.setAttribute("maps", maps); //因為要輸入tag，所以list_name改用maps
		request.setAttribute("list_name", list_name);
		request.setAttribute("list_map", list_map); 
		
		request.getRequestDispatcher("/chinese/openData.jsp").forward(request, response);
	}


	private void selectNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		String type = request.getParameter("type");
		OpenData openData = psService.getOpenData(id);
		
		// 替id編碼
		ArrayList<OpenDataFile> files = psService.getFileByOpenData(id);
		Collections.reverse(files);  //20210907修改，調整檔案下載顯示順序
		request.setAttribute("newsOne", openData);
		request.setAttribute("files", files);
		request.setAttribute("type_name", options.get("opendata_type", type).getName());
		
		request.getRequestDispatcher("/chinese/newsOpenData.jsp").forward(request, response);
	}
	
}
