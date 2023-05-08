package gov.moeaic.sql.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dasin.cryptography.dCipher;
import org.dasin.tools.dTools;

import com.google.gson.Gson;

import gov.moeaic.sql.bean.BusinessPub;
import gov.moeaic.sql.bean.BusinessPubFile;
import gov.moeaic.sql.bean.BusinessPubOrder;
import gov.moeaic.sql.bean.Business_category;
import gov.moeaic.sql.bean.OpenData;
import gov.moeaic.sql.bean.OpenDataFile;
import gov.moeaic.sql.bean.Option;
import gov.moeaic.sql.bean.Pages;
import gov.moeaic.sql.bean.PagesNews;
import gov.moeaic.sql.bean.PagesNewsFile;
import gov.moeaic.sql.bean.User;
import gov.moeaic.sql.bean.WebName;
import gov.moeaic.sql.bean.WebOrder;
import gov.moeaic.sql.bean.WebTitle;
import gov.moeaic.sql.dao.PagesDAO;
import gov.moeaic.sql.dao.PagesService;
import gov.moeaic.web.bean.OptionManager;
import gov.moeaic.web.bean.URLManager;

public class PagesServlet extends HttpServlet
{
	PagesService psService = null;
	Gson gson = null;
	Map<String, String> pagesNews_title = null;
	OptionManager options = null;

	@Override
	public void init() throws ServletException
	{
		pagesNews_title = new HashMap<>();
		pagesNews_title.put("about_intro", "認識本會");
		pagesNews_title.put("about_menager", "認識首長");
		pagesNews_title.put("about_organization", "組織與執掌");
		pagesNews_title.put("about_policy", "本會政策");
		pagesNews_title.put("about_implementation_plan", "施政計畫");
		pagesNews_title.put("about_traffic", "交通位置");
		pagesNews_title.put("about_responsibility", "Responsibility");
		pagesNews_title.put("about_fuDir", "Future Directions");
		
		pagesNews_title.put("studyReport", "研究報告");
		pagesNews_title.put("new_ann", "最新公告");
		pagesNews_title.put("business_ann", "業務統計"); 

		pagesNews_title.put("privacy", "隱私權及安全政策 ");
		
		pagesNews_title.put("contact-info", "聯絡資訊 ");
		pagesNews_title.put("contact-apply", "陳情管道 ");
	}

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
		gson = new Gson();
		String doThing = request.getParameter("doThing");
//		System.out.println("doThing="+doThing);
		// CkEditor：取得內容
		if (doThing != null && "seltContent".equalsIgnoreCase(doThing))
		{
			selectPages(request, response);
		}

		// CkEditor：更新內容
		else if (doThing != null && "udtContent".equalsIgnoreCase(doThing))
		{
			updateContext(request, response);
		}
		
		// CkEditor：index
		else if (doThing != null && "pagesIndex".equalsIgnoreCase(doThing))
		{
			pages_index(request, response);
		}		
		

		// 相關網站-分類管理：新增或修改
		else if (doThing != null && "web_TitleOne".equalsIgnoreCase(doThing))
		{
			insertOrUpdateWebTitle(request, response);
		}

		// 相關網站-分類管理：取得All
		else if (doThing != null && "getWebTitle".equalsIgnoreCase(doThing))
		{
			getAllWebTitle(request, response);
		}
		
		// 相關網站-分類管理：刪除之前，先檢查是否有網站資料依存在這個分類之下
		else if (doThing != null && "isParentId_used".equalsIgnoreCase(doThing))
		{
			isParentId_used(request, response);
		}

		// 相關網站-分類管理：刪除
		else if (doThing != null && "removeWeb_TitleOne".equalsIgnoreCase(doThing))
		{
			removeWeb_TitleOne(request, response);
		}
		
		// 相關網站-資料管理：取得分類全部 + 分類＆資料列表
		else if (doThing != null && "getWebName".equalsIgnoreCase(doThing))
		{
			getWebName(request, response);
		}
		
		// 相關網站-資料管理：刪除WebOrderOne
		else if (doThing != null && "remove_WebOrderOne".equalsIgnoreCase(doThing))
		{
			remove_WebOrderOne(request, response);
		}
		
		// 相關網站-資料管理：更新webOrder的sort排序
		else if (doThing != null && "update_webOrderOne".equalsIgnoreCase(doThing))
		{
			update_webOrderOne(request, response);
		}

		// 相關網站-資料管理：完全刪除WebName
		else if (doThing != null && "remove_WebNameOne".equalsIgnoreCase(doThing))
		{
			remove_WebNameOne(request, response);
		}
		
		// 相關網站-資料管理：新增WebName
		else if (doThing != null && "newWebName".equalsIgnoreCase(doThing))
		{
			newOrSelectWebName(request, response);
		}		
		
		// 相關網站-資料管理：選取WebNameOne
		else if (doThing != null && "selWebName".equalsIgnoreCase(doThing))
		{
			newOrSelectWebName(request, response);
		}			
		
		
		
		// 研究報告：新增全新一筆空資料
		else if (doThing != null && "newPagesNews_One".equalsIgnoreCase(doThing))
		{
			getPagesNews_One(request, response);
		}		
		
		// 研究報告：取得其中一筆資料
		else if (doThing != null && "getPagesNews_One".equalsIgnoreCase(doThing))
		{
			getPagesNews_One(request, response);
		}		
		
		// PagesNews - 取得All：研究報告studyReport , 最新公告new_ann , 業務統計最新公告business_ann
		else if (doThing != null && "studyReport".equalsIgnoreCase(doThing)
			  || doThing != null && "new_ann".equalsIgnoreCase(doThing) 
			  || doThing != null && "business_ann".equalsIgnoreCase(doThing) )
		{
			getAll_PagesNews(request, response);
		}	

		// PagesNews - 最新公告：取得All （DataTable Ajax）
		else if (doThing != null && "getAll_new_ann".equalsIgnoreCase(doThing)
			  || doThing != null && "getAll_business_ann".equalsIgnoreCase(doThing)
			  || doThing != null && "getAll_studyReport".equalsIgnoreCase(doThing) )
		{
			getAll_PagesNews_ajax(request, response);
		}

		
		// 研究報告：新增一筆_ch
		else if (doThing != null && "newPagesNews_ch".equalsIgnoreCase(doThing))
		{
			newOrUpdate_PagesNews(request, response);
		}
		
		// 研究報告：新增一筆_en
		else if (doThing != null && "newPagesNews_en".equalsIgnoreCase(doThing))
		{
			newOrUpdate_PagesNews(request, response);
		}
		
		// 研究報告：更新一筆_ch
		else if (doThing != null && "updatePagesNews_ch".equalsIgnoreCase(doThing))
		{
			newOrUpdate_PagesNews(request, response);
		}
		
		// 研究報告：更新一筆_en
		else if (doThing != null && "updatePagesNews_en".equalsIgnoreCase(doThing))
		{
			newOrUpdate_PagesNews(request, response);
		}
		
		// 研究報告：刪除一筆
		else if (doThing != null && "removePagesNews_One".equalsIgnoreCase(doThing))
		{
			removePagesNews_One(request, response);
		}

		// 研究報告：刪除一筆_內頁刪除
		else if (doThing != null && "removePagesNews_One_btn".equalsIgnoreCase(doThing))
		{
			removePagesNews_One_btn(request, response);
		}		
		
		// 研究報告：找出某一筆的所有附檔
		else if (doThing != null && "getFile_list".equalsIgnoreCase(doThing))
		{
			getFile_list(request, response);
		}
		
		// 研究報告：刪除某一筆的其中一個附檔
		else if (doThing != null && "remove_fileListOne".equalsIgnoreCase(doThing))
		{
			remove_fileListOne(request, response);
		}
		
		// 研究報告：調整置頂顯示順序
		else if (doThing != null && "setPagesNews_TopOrder".equalsIgnoreCase(doThing))
		{
			setPagesNews_TopOrder(request, response);
		}
		
		// 使用者管理：第一次登入，先判斷是管理員還是使用者，再判斷多久沒變更密碼
		else if (doThing == null || "PSW_userManage".equalsIgnoreCase(doThing))
		{
			CheckUserManage(request, response);
		}		
		
		// 使用者管理：先判斷是管理員還是使用者
		else if (doThing == null || "userManage".equalsIgnoreCase(doThing))
		{
			CheckUserManage(request, response);
		}
		
		// 使用者管理：取得一筆使用者
		else if (doThing != null && "getUser_One".equalsIgnoreCase(doThing))
		{
			getUser_One(request, response);
		}
		
		// 使用者管理：新增使用者
		else if (doThing != null && "new_User".equalsIgnoreCase(doThing))
		{
			getUser_One(request, response);
		}
		
		// 使用者管理：insert OR update
		else if (doThing != null && "insertUser".equalsIgnoreCase(doThing))
		{
			insertOrUpdateUSer(request, response);
		}
		
		// 使用者管理：insert OR update
		else if (doThing != null && "updateUser".equalsIgnoreCase(doThing))
		{
			insertOrUpdateUSer(request, response);
		}
		
		// 使用者管理：修改密碼
		else if (doThing != null && "update_userPSW".equalsIgnoreCase(doThing))
		{
			update_userPSW(request, response);
		}
		
		
		
		// 統計資料管理：取出所有資料 excelUpload_List
		else if (doThing != null && "excelUpload".equalsIgnoreCase(doThing))
		{
			getExcelUpload_List(request, response);
		}
		
		// 統計資料管理：更新首頁輪撥項目
		else if (doThing != null && "updateShowIndex".equalsIgnoreCase(doThing))
		{
			updateShowIndex(request, response);
		}
		
		// 統計資料管理：取得首頁輪撥項目
		else if (doThing != null && "getShowIndex".equalsIgnoreCase(doThing))
		{
			getShowIndex(request, response);
		}		
		

		// 申辦業務-流程圖管理：取出所有資料 
		else if (doThing != null && "flowcChart".equalsIgnoreCase(doThing))
		{
			pages_index_chart(request, response);
		}

		// 申辦業務-公告管理：取出某一業務類型的所有書表
		else if (doThing != null && "businessPub".equalsIgnoreCase(doThing))
		{
			businessPub_index(request, response);
		}

		// 申辦業務-公告管理：新增/選取其中一筆
		else if (doThing != null && "newBusinessPub_One".equalsIgnoreCase(doThing)
				|| doThing != null && "getBusinessPub_One".equalsIgnoreCase(doThing))
		{
			newBusinessPub_One(request, response);
		}
		
		// 申辦業務-公告管理：刪除其中一筆
		else if (doThing != null && "removeBusinessPub_One_btn".equalsIgnoreCase(doThing))
		{
			removeBusinessPub_One_btn(request, response);
		}
		
		// 申辦業務-公告管理：新增/更新公告
		else if (doThing != null && "newBusinessPub_ch".equalsIgnoreCase(doThing) 
				|| doThing != null && "newBusinessPub_en".equalsIgnoreCase(doThing)
				|| doThing != null && "updateBusinessPub_ch".equalsIgnoreCase(doThing)
				|| doThing != null && "updateBusinessPub_en".equalsIgnoreCase(doThing))
		{
			newOrUpdate_BusinessPub_One(request, response);
		}
		
		// 申辦業務-公告管理：找出某一筆的所有附檔
		else if (doThing != null && "getFile_list_BP".equalsIgnoreCase(doThing))
		{
			getFile_list_BP(request, response);
		}
		
		// 申辦業務-公告管理：調整顯示順序
		else if (doThing != null && "setBusinessPub_Order".equalsIgnoreCase(doThing))
		{
			setBusinessPub_Order(request, response);
		}
		
		// 申辦業務-公告管理：刪除某一個附檔
		else if (doThing != null && "removeBPfile_btn".equalsIgnoreCase(doThing))
		{
			removeBPfile_btn(request, response);
		}
		
		// 申辦業務-選項管理：刪除某一個選項
		else if (doThing != null && "isParentId_used_PB".equalsIgnoreCase(doThing))
		{
			isParentId_used_PB(request, response);
		}
		
		
		
		// 政府資訊公開-公告管理：新增公告
		else if (doThing != null && "openData_index".equalsIgnoreCase(doThing))
		{
			openData_index(request, response);
		}		
		
		// 政府資訊公開-公告管理：新增/選取其中一筆
		else if (doThing != null && "newOpenData_One".equalsIgnoreCase(doThing)
				|| doThing != null && "getOpenData_One".equalsIgnoreCase(doThing))
		{
			newOpenData_One(request, response);
		}
		
		// 政府資訊公開-公告管理：新增/更新公告
		else if (doThing != null && "newOpenData_ch".equalsIgnoreCase(doThing) 
				|| doThing != null && "newOpenData_en".equalsIgnoreCase(doThing)
				|| doThing != null && "updateOpenData_ch".equalsIgnoreCase(doThing)
				|| doThing != null && "updateOpenData_en".equalsIgnoreCase(doThing))
		{
			newOrUpdate_OpenData_One(request, response);
		}
		
		// 政府資訊公開-公告管理：找出某一筆的所有附檔
		else if (doThing != null && "getFile_list_OD".equalsIgnoreCase(doThing))
		{
			getFile_list_OD(request, response);
		}
		
		// 政府資訊公開-公告管理：刪除其中一筆
		else if (doThing != null && "removeOpenData_One".equalsIgnoreCase(doThing))
		{
			removeOpenData_One(request, response);
		}
		
		// 政府資訊公開-公告管理：刪除某一個附檔
		else if (doThing != null && "removeFile_OD".equalsIgnoreCase(doThing))
		{
			removeFile_OD(request, response);
		}
		
		// 政府資訊公開-公告管理：更新order
		else if (doThing != null && "setOpenData_Order".equalsIgnoreCase(doThing))
		{
			setOpenData_Order(request, response);
		}
		
		// 政府資訊公開-選項管理：刪除某一個選項
		else if (doThing != null && "isParentId_used_OD".equalsIgnoreCase(doThing))
		{
			isParentId_used_OD(request, response);
		}
	}



	private void selectPages(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String type = request.getParameter("type");
		ArrayList<Pages> beans = psService.getPages(type);

		Map<String, String> map = new HashMap<>();
		for (Pages bean : beans)
		{
			String lang = bean.getLanguage();
			map.put(lang + "_context", bean.getPage_content());
			map.put(lang + "_id", String.valueOf(bean.getId()));
		}

		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String mapToJson = gson.toJson(map);
		out.write(mapToJson);
		out.close();
		return;
	}

	private void updateContext(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String type = request.getParameter("type");
		String language = request.getParameter("language");
		String content = request.getParameter("page_content");
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		
		Pages bean = new Pages();
		bean.setId(id);
		bean.setType(type);
		bean.setPage_content(content);
		bean.setLanguage(language);
		if(id == 0){
			psService.insertPages(bean);
		}else{
			psService.updatePages(bean);
		}

		if("apply_online".equalsIgnoreCase(type) || "apply_search".equalsIgnoreCase(type) 
				|| "attachedFile".equalsIgnoreCase(type)){
			((URLManager)getServletContext().getAttribute("urlManager")).init();
		}

		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.write("success");
		out.close();
		return;
	}

	// 相關網站-分類管理：新增或修改
	private void insertOrUpdateWebTitle(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		String ch_title = request.getParameter("ch_title");
		String en_title = request.getParameter("en_title");
		String sort = request.getParameter("sort");
		
		WebTitle bean = new WebTitle();
		bean.setId(id);
		bean.setCh_title(ch_title);
		bean.setEn_title(en_title);
		bean.setSort(Integer.valueOf(sort));
		
		if (id == 0)
		{
			psService.insertWebTitle(bean);
		} else
		{
			psService.updateWebTitle(bean);
		}
		
		ArrayList<WebTitle> list = psService.getWebTitle();
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

//		Gson gson = new Gson();
		String listToJson = gson.toJson(list);
		
		out.write(listToJson);
		out.close();
		return;
	}
	
	// 相關網站-分類管理：取得All
	private void getAllWebTitle(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		ArrayList<WebTitle> list = psService.getWebTitle();
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();

//		Gson gson = new Gson();
		String listToJson = gson.toJson(list);
		
		out.write(listToJson);
		out.close();
		return;
		
	}
	
	// 相關網站-分類管理：刪除之前，先檢查是否有網站資料依存在這個分類之下
	private void isParentId_used(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int parent_id = ToolsUtil.parseInt(request.getParameter("id"));
		ArrayList<WebOrder> list = psService.getWebOrder(parent_id);
		String result = "";
		if(list!=null && !list.isEmpty()){
			result = "true";
		}
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
		return;
		
	}
	
	// 相關網站-分類管理：刪除
	private void removeWeb_TitleOne(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		psService.deleteWebTitle(id);
		psService.deleteWebOrderByWebTitle(id);
		psService.checkNeedRemove();
		getAllWebTitle(request, response);
		
	}
	
	// 相關網站-資料管理：取得分類全部 + 分類＆資料列表
	private void getWebName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//List< [投資臺灣 , XXX] , [在臺工作, XXXX]) >
		ArrayList<WebTitle> title = psService.getWebTitle();
		request.setAttribute("title", title);

		//{ [投資臺灣, List<WebOrder>] , [在臺工作, List<WebOrder>]
		Map<String, ArrayList<WebOrder>> list_map = psService.getWebOrder_map();
		request.setAttribute("list", list_map);
		
		
		request.getRequestDispatcher("/console/websiteLinks/edit-webName.jsp").forward(request, response);
	}
	
	
	/* 相關網站-資料管理：刪除WebOrderOne + 刪除某一分類下的某一筆網站
	 * WebOrder 刪除那一筆
	 * WebName  取出titles，刪除掉這一個title，再update回去
	 */ 
	private void remove_WebOrderOne(HttpServletRequest request, HttpServletResponse response)
	{

		String titles_str = request.getParameter("web_titles");
		String title_remove_id = request.getParameter("webTitle_id");
		ArrayList<String> titles = new ArrayList<>(Arrays.asList(titles_str.split(",")));
		titles.remove(title_remove_id);
		
		int webOrder_id = ToolsUtil.parseInt(request.getParameter("webOrder_id"));
		int webName_id = ToolsUtil.parseInt(request.getParameter("webName_id"));
		boolean isRemove = psService.updateWebName_Titles( webName_id, ToolsUtil.valuesToString(titles.toArray(new String[0])) );
		if(isRemove == true){
			psService.deleteWebOrder(webOrder_id);
		}
		
		return;
	}
	
	/* 相關網站-資料管理：刪除一筆removeWebName
	 * 完全刪除， 刪除 WebOrder + WebName + 圖片刪除
	 */
	private void remove_WebNameOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int webName_id = ToolsUtil.parseInt(request.getParameter("webName_id"));
		WebName bean = psService.getWebName(webName_id);
		//刪除圖片
		if(bean.getPhotoFile()!=null && bean.getPhotoFile().length()!=0){
			File file = new File(request.getServletContext().getRealPath(bean.getPhotoFile()));
			file.delete();
		}
		//刪除WebName
		psService.deleteWebName(webName_id);
		//刪除Order
		psService.deleteWebOrderByWebName(webName_id);
		this.getWebName(request, response);
	}
	
	
	// 相關網站-資料管理：選取WebNameOne 、 新增WebName
	private void newOrSelectWebName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String doThing = request.getParameter("doThing");
		WebName webNameOne = null;
		if("newWebName".equals(doThing)){
			webNameOne = new WebName();
			webNameOne.setId(0);
			webNameOne.setLang(request.getParameter("lang"));
		}else{
			int id = ToolsUtil.parseInt(request.getParameter("webName_id"));
			webNameOne = psService.getWebName(id);
		}
		request.setAttribute("webNameOne", webNameOne);
		
		ArrayList<WebTitle> web_titles = psService.getWebTitle();
		request.setAttribute("web_titles", web_titles);
		request.getRequestDispatcher("/console/websiteLinks/edit-webNameOne.jsp").forward(request, response);;
	}

	
	// 相關網站-資料管理：更新webOrder的sort排序
	private void update_webOrderOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		String sort = request.getParameter("sort");
		psService.updateWebOrder_sort(id, sort);
		
		// 轉頁方法一
		response.sendRedirect("/console/edit-pages.view?doThing=getWebName");
		
		// 轉頁方法二
		//getWebName(request, response);
	}
	
	
	// 研究報告：取得其中一筆資料
	private void getPagesNews_One(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		Map<String, String> map = new HashMap<>();
		Map<String, String> title = new HashMap<>();
		if(id == 0){
			String type = request.getParameter("type");
			title.put("typeName", pagesNews_title.get(type));
			title.put("type", type);
			
			map.put("id", String.valueOf(id));
			map.put("ch_title", "");
			map.put("ch_content", "");
			map.put("en_title", "");
			map.put("en_content", "");
			map.put("publish_date", "");
			map.put("publish_APM", "AM");
			map.put("publish_time", "");
			map.put("showTop", "0");
			map.put("sort", "99");			map.put("type", type);
			map.put("doThing", "newPagesNews");
		}else{
			PagesNews bean = psService.getPagesNews_One(id);
			
			String type = bean.getType();
			title.put("typeName", pagesNews_title.get(type));
			title.put("type", type);
			
			map.put("id", String.valueOf(id));
			map.put("ch_title", bean.getCh_title());
			map.put("ch_content", bean.getCh_content());
			map.put("en_title", bean.getEn_title());
			map.put("en_content", bean.getEn_content());
//			map.put("publish_date", bean.getPublish_date_ROC()); //民國年寫法
			map.put("publish_date", bean.getPublish_date_AD());
			map.put("publish_APM", bean.getPublish_APM());
			map.put("publish_time", bean.getPublish_Time());
			map.put("showTop", bean.isShowTop()==true ? "1" : "0");
			map.put("sort", String.valueOf(bean.getSort()));
			map.put("type", type);
			map.put("rss_text", bean.getRss_text());
			map.put("rss_image", bean.getRss_image());
			map.put("doThing", "updatePagesNews");
		}
		
//		System.out.println(map);
		request.setAttribute("title", title);
		request.setAttribute("pagesNewsOne", map);
		request.getRequestDispatcher("/console/pagesNews/edit-pagesNews.jsp").forward(request, response);
		
//		response.setContentType("text/plain; charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		Gson gson = new Gson();
//		String listToJson = gson.toJson(map);
//		out.write(listToJson);
//		out.close();
//		return;
	}
	
	
	// 研究報告：取得All
	private void getAll_PagesNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String type = request.getParameter("doThing");
		ArrayList<PagesNews> list = psService.getPagesNews(type);
		request.setAttribute("pagesNews_list", list);
		
		Map<String, String> title = new HashMap<>();
		title.put("typeName", pagesNews_title.get(type));
		title.put("type", type);
		request.setAttribute("title", title);
		request.getRequestDispatcher("/console/pagesNews/index.jsp").forward(request, response);
	}
	
	// 研究報告：取得All
	private void getAll_PagesNews_ajax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String type = request.getParameter("type");
//		System.out.println("ajax, type="+type);
		ArrayList<PagesNews> list = psService.getPagesNews(type);
		
		// DataTable ajax寫法
		ArrayList<String[]> group = new ArrayList<>();
		for(PagesNews bean : list){
			
			String[] tr = new String[6];
			tr[0] = String.valueOf(bean.getSort()); //順序
			tr[1] = bean.isShowTop() == true ? "是" : "否"; //置頂
			tr[2] = bean.getPublish_date_AD(); //西元日期
			tr[3] = bean.getCh_title(); //中文標題
			tr[4] = bean.getEn_title(); //英文標題
			tr[5] = 
				"<input type='hidden' name='id' value='" + bean.getId() +"' class='form-control'> "
			  + "<a href='#'>"
			  + "<img src='" + request.getContextPath() + "/images/icons/sort.png' width='24' onclick='setTopOrder(this)'>"
			  + "</a>"
			  + "<a href='" + request.getContextPath() + "/console/edit-pages.view?doThing=getPagesNews_One&id="+bean.getId()+"'>"
  	  		  + "<img src='" + request.getContextPath() + "/images/icons/edit.png' width='24'>"
  	  		  + "</a>"
  	  		  + "<a href='#'>"
  	  		  + "<img src='" + request.getContextPath() + "/images/icons/action_delete.gif' width='24' onclick='dele_pagesNews(this)'>"
  	  		  + "</a>";
			group.add(tr);
		}
		
		Map<String, ArrayList<String[]>> map = new HashMap<>();
		map.put("data", group);
		String listToJson = gson.toJson(map);
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(listToJson);
		out.close();
		return;
		
	}
	
	
	// 研究報告：新增一筆_ch or en
	private void newOrUpdate_PagesNews(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String publish_date = request.getParameter("publish_date");
		String publish_APM = request.getParameter("publish_APM");
		String publish_time = request.getParameter("publish_time");
		String showTop = request.getParameter("showTop");
		String sort = request.getParameter("sort");
		String type = request.getParameter("type");
		String doThing = request.getParameter("doThing");
		String ch_title = ( request.getParameter("ch_title") == null ) ? "" : request.getParameter("ch_title");
		String ch_content = ( request.getParameter("ch_content") == null ) ? "" : request.getParameter("ch_content");
		String en_title = ( request.getParameter("en_title") == null ) ? "" : request.getParameter("en_title");
		String en_content = ( request.getParameter("en_content") == null ) ? "" : request.getParameter("en_content");
		String rss_text = ( request.getParameter("rss_text") == null ) ? "" : request.getParameter("rss_text");
		String rss_image = ( request.getParameter("rss_image") == null ) ? "" : request.getParameter("rss_image");
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		
		PagesNews bean = new PagesNews();
		bean.setId(id);
		bean.setCh_title(ch_title);
		bean.setCh_content(ch_content);
		bean.setEn_title(en_title);
		bean.setEn_content(en_content);
//		bean.setPublish_date_ROC(publish_date, publish_APM, publish_time); //民國年寫法
		bean.setPublish_date_AD(publish_date, publish_APM, publish_time);
		if("1".equals(showTop)){
			bean.setShowTop(true);
			bean.setSort(Integer.valueOf(sort));
		}else{
			bean.setShowTop(false);
			bean.setSort(99);
		}
		bean.setType(type);
		
		//108-12-5 只有最新公告英文版，遇到以下兩參數空白需要強迫塞入 預設內容供RSS使用，其餘type空白皆不處理
		if("new_ann".equals(type) && "updatePagesNews_en".equals(doThing) || "new_ann".equals(type) && "newPagesNews_en".equals(doThing))   {
			if(rss_text.isEmpty()) rss_text = en_title;
			if(rss_image.isEmpty()) rss_image = "/images/news/default/10.jpg";
		}
		bean.setRss_text(rss_text);
		bean.setRss_image(rss_image);
//		System.out.println(bean);
		
		String listToJson = "";
		if(id == 0){
			int pagesNews_id = psService.insertPagesNews(bean);
			Map<String, String> map = new HashMap<>();
			map.put("pagesNews_id", String.valueOf(pagesNews_id));
			map.put("doThing", "updatePagesNews");
			listToJson = gson.toJson(map);
		}else{
			//108-12-5 中文版時不能update rss_text,rss_image ，這是同一筆資料會覆蓋掉英文版可能存在的rss值
			if("updatePagesNews_ch".equals(doThing)){ 
				psService.updatePagesNews_ch(bean);
			}else {
				psService.updatePagesNews_en(bean);
			}
		}
		
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(listToJson);
		out.close();
		return;
		
	}
	
	
	// 研究報告：刪除一筆
	private void removePagesNews_One(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		String type = request.getParameter("type");
		
		// RSS遇到刪除新聞，要發出通知，所以要先記錄下最新公告刪除的時間		
		if("new_ann".equalsIgnoreCase(type)){
//			System.out.println("要先記錄刪除的ID");
			if(psService.record_deleteId(id) == true){
//				System.out.println("有紀錄");
				psService.deletePagesNews(id);
			}
		}else{
			psService.deletePagesNews(id);
			psService.deletePagesNewsFile_byPsId(id);
		}
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write("removePagesNews_One");
		out.close();
		return;
	}

	// 研究報告：刪除一筆_內頁刪除要返回首頁
	private void removePagesNews_One_btn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		String type = request.getParameter("type");
		
		// RSS遇到刪除新聞，要發出通知，所以要先記錄下最新公告刪除的時間		
		if("new_ann".equalsIgnoreCase(type)){
//			System.out.println("要先記錄刪除的ID");
			if(psService.record_deleteId(id) == true){
//				System.out.println("有紀錄");
				psService.deletePagesNews(id);
			}
		}else{
			psService.deletePagesNews(id);
			psService.deletePagesNewsFile_byPsId(id);
		}
		
		// DataTable ajax轉頁
		if("new_ann".equalsIgnoreCase(type)){
			response.sendRedirect("/console/pagesNews/" + type + ".jsp");
		}
		
		else if ("business_ann".equalsIgnoreCase(type)) {
			response.sendRedirect("/console/pagesNews/" + type + ".jsp");
		}
		
		else if ("studyReport".equalsIgnoreCase(type)) {
			response.sendRedirect("/console/pagesNews/" + type + ".jsp");
		}
		
		
//		ArrayList<PagesNews> list = psService.getPagesNews(type);
//		request.setAttribute("pagesNews_list", list);
//		
//		Map<String, String> title = new HashMap<>();
//		title.put("typeName", pagesNews_title.get(type));
//		title.put("type", type);
//		request.setAttribute("title", title);
//		request.getRequestDispatcher("/console/pagesNews/index.jsp").forward(request, response);
	}

	// 研究報告：找出某一筆的所有附檔
	private void getFile_list(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int pagesNews_id = ToolsUtil.parseInt(request.getParameter("pagesNews_id"));
		ArrayList<PagesNewsFile> list = psService.getPagesNewsFile(pagesNews_id);
		//替file的id編碼
		for(PagesNewsFile bean : list){
			String id_str = dCipher.encrypt(String.valueOf(bean.getId()));
			bean.setId_str(id_str);
		}
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String listToJson = gson.toJson(list);
		out.write(listToJson);
		out.close();
		return;
	}
	
	
	// 研究報告：刪除某一筆的其中一個附檔
	private void remove_fileListOne(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//刪除
		int file_id = ToolsUtil.parseInt(request.getParameter("id"));
		psService.deletePagesNewsFile(file_id);
		
		//取出全部file
		getFile_list(request, response);
	}
	
	
	// 研究報告：調整置頂顯示順序
	private void setPagesNews_TopOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String showTop = request.getParameter("showTop");
		String sort = request.getParameter("sort"); //新的sort
		String oldSort = request.getParameter("old_sort"); //舊的sort
		String type = request.getParameter("type");
		String autoSort = request.getParameter("autoSort");
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		
		PagesNews bean = new PagesNews();
		bean.setId(id);
		if("是".equals(showTop)){

			//序號要自動排列
			if("1".equals(autoSort)) {
				/* 狀況1： 4   變 1，原本的1,2,3都 +1
				 * 狀況2： 2   變 5，原本的3,4,5都 -1
				 * 狀況4： 99 變 1，原本的1,2,3都 +1 */
				psService.setAutoSort_news(type, Integer.valueOf(sort), Integer.valueOf(oldSort));
			}
			
			bean.setShowTop(true);
			bean.setSort(Integer.valueOf(sort));
		}else{
			
			//序號要自動排列
			if("1".equals(autoSort)) {
				/* 狀況3： 1 變 99，原本的2,3,4都 -1
				 * 狀況5；兩個都99，不處理 */
				psService.setAutoSort_news(type, 99, Integer.valueOf(oldSort));
			}
			bean.setShowTop(false);
			bean.setSort(99);
		}
		psService.updatePagesNews_topSort(bean);
		
		// 轉頁方法一：
		if("new_ann".equalsIgnoreCase(type)){
			response.sendRedirect("/console/pagesNews/" + type + ".jsp");
		}
		
		else if ("business_ann".equalsIgnoreCase(type)) {
			response.sendRedirect("/console/pagesNews/" + type + ".jsp");
		}
		
		else if ("studyReport".equalsIgnoreCase(type)) {
			response.sendRedirect("/console/pagesNews/" + type + ".jsp");
		}

		//response.sendRedirect("/console/edit-pages.view?doThing=" + type);
		
		// 轉頁方法二：
		/*
		ArrayList<PagesNews> list = psService.getPagesNews(type);
		request.setAttribute("pagesNews_list", list);
		
		Map<String, String> title = new HashMap<>();
		title.put("typeName", pagesNews_title.get(type));
		title.put("type", type);
		request.setAttribute("title", title);
		request.getRequestDispatcher("/console/pagesNews/index.jsp").forward(request, response);
		*/
	}
	
	
	// 使用者管理：先判斷是管理員還是使用者
	private void CheckUserManage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		User bean = (User)session.getAttribute("user");

		String doThing = request.getParameter("doThing");
		String isNeedChg = ToolsUtil.dateInRange(bean.getLastUpdate_date(), -180) == false ? "need" : "";
		request.setAttribute("isNeedChg", isNeedChg);
		
		if(bean.isAdmin()==true){
//			System.out.println("管理員");
			
			// 如果第一次登入已經半年沒改密碼，管理員仍是直接跳自己的編輯頁
			if("PSW_userManage".equals(doThing) && isNeedChg.length() != 0){
				request.setAttribute("userOne", psService.getUser(bean.getId()));
				request.setAttribute("doThing", "updateUser");
				request.getRequestDispatcher("/console/user/edit-user.jsp").forward(request, response);
			}
			// 如果第一次登入但是不需改密碼，跟一般進入一樣，管理員全部進到成員列表頁
			else{
				ArrayList<User> list = psService.getAllUser();
				request.setAttribute("userList", list);
				request.getRequestDispatcher("/console/user/user_list.jsp").forward(request, response);
			}
			return;
			
		}else{
			
//			System.out.println("一般使用者");
			request.setAttribute("userOne", psService.getUser(bean.getId()));
			request.setAttribute("doThing", "updateUser");
			request.getRequestDispatcher("/console/user/edit-user.jsp").forward(request, response);
			return;
		}
	}
	
	
	// 使用者管理：取得一筆使用者 、 新增使用者
	private void getUser_One(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String doThing = request.getParameter("doThing");
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		User bean = null;
		if("getUser_One".equalsIgnoreCase(doThing)){
			bean = psService.getUser(id);
			request.setAttribute("doThing", "updateUser");
		}else {
			bean = new User();
			bean.setId(id);
			request.setAttribute("doThing", "insertUser");
		}
		request.setAttribute("userOne", bean);
		request.getRequestDispatcher("/console/user/edit-user.jsp").forward(request, response);
	}
	
	// 使用者管理：insert OR update
	private void insertOrUpdateUSer(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String account = request.getParameter("account") == null? "" : request.getParameter("account");
		String name = request.getParameter("name");
		String department = request.getParameter("department");
		String tel = request.getParameter("tel");
		String email = request.getParameter("email");
		String admin = request.getParameter("admin");
		String disable = request.getParameter("disable");
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		String login_user = request.getParameter("login_user");
		String doThing = request.getParameter("doThing");
		
		User bean = new User();
		bean.setId(id);
		bean.setAccount(account);
		bean.setName(name);
		bean.setDepartment(department);
		bean.setTel(tel);
		bean.setEmail(email);
		bean.setDisable( "0".equals(disable) ? false : true );

		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user.isAdmin() == true){
			bean.setAdmin( "0".equals(admin) ? false : true );
		}else{
			bean.setAdmin(false);
		}
		
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String listToJson = "";
		if("insertUser".equalsIgnoreCase(doThing)){
			String password = request.getParameter("password");
			bean.setPassword(password);
			bean.setPasswordToEncrypt(password);
			bean.setRegistered_manager(login_user);
			int new_id = psService.insertUser(bean);

			Map<String, String> map = new HashMap<>();
			map.put("doThing", "updateUser");
			map.put("id", String.valueOf(new_id));
			listToJson = gson.toJson(map);
		}else{
			bean.setLastUpdate_user(login_user);
			psService.updateUser(bean);
		}
		out.write(listToJson);
		out.close();
		return;
	}
	
	
	// 使用者管理：修改密碼
	private void update_userPSW(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String new_password = request.getParameter("password");
		String login_user = request.getParameter("login_user");
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		User bean = new User();
		bean.setId(id);
		bean.setPassword(new_password);
		bean.setPasswordToEncrypt(new_password);
		bean.setLastUpdate_user(login_user);
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user.isAdmin() == true){
			psService.updateUserPassword(bean);
		}else{
			if(user.getAccount().equals(login_user) && user.getId() == id){
				psService.updateUserPassword(bean);
			}
		}
		
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(new_password);
		out.close();
		return;
	}
	
	// 統計資料管理：取出所有資料 excelUpload_List
	private void getExcelUpload_List(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map<Integer, ArrayList<Business_category>> map = psService.get_BSCT();
		
		Map<String, Map<String, String>> result = null;
		ArrayList<String> order_name = null;
		if(map!=null && !map.isEmpty()){
			result = new HashMap<>();
			order_name = new ArrayList<>();
			
			for(int i=0; i<map.keySet().size(); i++){
				// [0,1,2,3]
				order_name.add(String.valueOf(i));
				
				ArrayList<Business_category> list = map.get(i);
				String type = list.get(0).getType();
				String first_year = list.get(0).getYear();
				String last_year = list.get(list.size()-1).getYear();
				if(last_year.length() > 4){
					last_year = last_year.substring(0, 4) + "/" + last_year.substring(4);
				}
				
				// [ {"0" : ["type":"僑外來臺投資" , "first_year":first_year , "last_year":last_year]} , {"1" : [XXX]}]
				String order_num = String.valueOf(i);
				if(result.get(order_num) == null){
					result.put(order_num, new HashMap<>());
				}
				result.get(order_num).put("type", type);
				result.get(order_num).put("first_year", first_year);
				result.get(order_num).put("last_year", last_year);
			}
		}
		request.setAttribute("order_name", order_name);
		request.setAttribute("result", result);
		request.getRequestDispatcher("/console/pagesNews/excelUploadList.jsp").forward(request, response);
	}
	
	
	// 統計資料管理：更新首頁輪撥項目
	private void updateShowIndex(HttpServletRequest request, HttpServletResponse response)
	{
		String showIndex = request.getParameter("showIndex");
		psService.updateShowIndex(showIndex);
	}
	
	// 統計資料管理：更新首頁輪撥項目
	private void getShowIndex(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String result = psService.getShowIndex();
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
		return;
	}
	
	// 申辦業務-流程圖管理：取出所有資料 
	private void pages_index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String type = request.getParameter("type"); // option的id
//		System.out.println("type="+type);
		request.setAttribute("title", pagesNews_title.get(type));
		
		ArrayList<Pages> list = psService.getPages(type);
		for(Pages bean : list){
			if("ch".equalsIgnoreCase(bean.getLanguage())){
				request.setAttribute("pagesOne_ch", bean);
			}else{
				request.setAttribute("pagesOne_en", bean);
			}
		}
		
		request.getRequestDispatcher("/console/about/pages.jsp").forward(request, response);
	}	
	
	
	// 申辦業務-流程圖管理：取出所有資料 
	private void pages_index_chart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//加入 流程圖項目標題
		String type = request.getParameter("type"); // option的id
		request.setAttribute("op_id", type); //如果是第一次新增流程圖，就用這個op_id取業務類型
		request.setAttribute("title", options.get("business_category", type).getName());
		
		ArrayList<Pages> list = psService.getPages(type);
		for(Pages bean : list){
			if("ch".equalsIgnoreCase(bean.getLanguage())){
				request.setAttribute("pagesOne_ch", bean);
			}else{
				request.setAttribute("pagesOne_en", bean);
			}
		}
		
		request.getRequestDispatcher("/console/business/flow-chart.jsp").forward(request, response);
	}
	
	// 申辦業務-公告管理：取出某一業務類型的所有書表
	private void businessPub_index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//放入業務類型標題
		String op_id_one = request.getParameter("op_id_one"); //business_category
		
		String op_id_oneName = options.get("business_category", op_id_one).getName();
		Map<String, String> title = new HashMap<>();
		title.put("op_id_one", op_id_one);
		title.put("op_id_oneName", op_id_oneName);
		request.setAttribute("title", title);
		
		
		//取出Order
		ArrayList<BusinessPubOrder> list = psService.getBusinessPubOrder(op_id_one);
		ArrayList<BusinessPubOrder> remove = new ArrayList<>();
		
		ArrayList<String> ch_opsId = new ArrayList<>();
		ArrayList<Option> chList = options.getCh_QA_type()==null ? new ArrayList<Option>() : options.getCh_QA_type();
		for(Option bean : chList){
			ch_opsId.add(String.valueOf(bean.getId()));
		}
		
		ArrayList<String> en_opsId = new ArrayList<>();
		ArrayList<Option> enList = options.getEn_QA_type()==null ? new ArrayList<Option>() : options.getEn_QA_type();
		for(Option bean : enList){
			en_opsId.add(String.valueOf(bean.getId()));
		}
		
		for(BusinessPubOrder bean : list){
			
			if(bean.getBusinessPub_id() == 0 || bean.getType() == null){
				remove.add(bean);
				continue;
			}
			bean.setOp_id_str( op_id_oneName );
			bean.setType_str( options.get("business_type", bean.getType()).getName() );
			
			//常見問答是否顯示「已設定分類」圖片
			String ch_QA_type = bean.getCh_QA_type(); 
			String en_QA_type = bean.getEn_QA_type(); 
			if(isShowIcon(ch_QA_type, ch_opsId) == false){
				bean.setCh_QA_type("");
			}
			if(isShowIcon(en_QA_type, en_opsId) == false){
				bean.setEn_QA_type("");
			}			
		}
		list.removeAll(remove);
		request.setAttribute("buinessPub_list", list);
		request.getRequestDispatcher("/console/business/buinesspub-index.jsp").forward(request, response);
	}
	
	
	
	private boolean isShowIcon(String QA_type, ArrayList<String> QAtype_ops)
	{
		boolean result = false;
		if(QA_type != null && QA_type.trim().length() != 0){
			
			//System.out.println(QA_type);
			for(String value : ToolsUtil.getValueToList(QA_type, ",")){
				
				//判斷該筆選中的每一個id，有沒有 contains在 [Option]內的ID群
				if(result == false){
					//只要QA_type中任一個value，有在options裡面，就停止迴圈，並回傳true
					//System.out.println("value="+value+", QAtype_ops="+QAtype_ops);
					//System.out.println(QAtype_ops.contains(value));
					if(QAtype_ops.contains(value) == true){
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}

	// 申辦業務-公告管理：新增/取得其中一筆資料
	private void newBusinessPub_One(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		String op_id_one = request.getParameter("op_id_one");
		
		Map<String, String> title = new HashMap<>();
		title.put("op_id_one", op_id_one);
		title.put("op_id_oneName", options.get("business_category", op_id_one).getName());
		
		Map<String, String> map = new HashMap<>();
		if(id == 0){
			map.put("id", String.valueOf(id));
			map.put("ch_title", "");
			map.put("ch_content", "");
			map.put("en_title", "");
			map.put("en_content", "");
			map.put("publish_date", "");
			map.put("publish_APM", "");
			map.put("publish_time", "");
			map.put("op_ids", "");
			map.put("type", "");
			map.put("ch_QA_type", "");
			map.put("en_QA_type", "");
			map.put("doThing", "newBusinessPub");
		}else{
			BusinessPub bean = psService.getBusinessPubOne(id);
			map.put("id", String.valueOf(id));
			map.put("ch_title", bean.getCh_title());
			map.put("ch_content", bean.getCh_content());
			map.put("en_title", bean.getEn_title());
			map.put("en_content", bean.getEn_content());
//			map.put("publish_date", bean.getPublish_date_ROC()); //民國年寫法
			map.put("publish_date", bean.getPublish_date_AD());
			map.put("publish_APM", bean.getPublish_APM());
			map.put("publish_time", bean.getPublish_Time());
			map.put("op_ids", bean.getOp_ids());
			map.put("type", bean.getType());
			map.put("ch_QA_type", bean.getCh_QA_type());
			map.put("en_QA_type", bean.getEn_QA_type());
			map.put("doThing", "updateBusinessPub");
		}
		
		request.setAttribute("title", title);
		request.setAttribute("businessPubOne", map);
		request.getRequestDispatcher("/console/business/buinesspub-edit.jsp").forward(request, response);
	}
	
	//申辦業務：刪除一筆公告
	private void removeBusinessPub_One_btn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		psService.deleteBusinessPub(id);
		psService.deleteBusinessPubFile_byBsId(id);
		psService.deleteBusinessPubOrder_byBsId(id);
		
		// 轉頁方法一
		String op_id = request.getParameter("op_id_one");
		response.sendRedirect("/console/edit-pages.view?doThing=businessPub&op_id_one="+op_id);
		
		// 轉頁方法二
		//businessPub_index(request, response);
		
	}
	
	// 申辦業務：新增一筆_ch or en
	private void newOrUpdate_BusinessPub_One(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String publish_date = request.getParameter("publish_date");
		String publish_APM = request.getParameter("publish_APM");
		String publish_time = request.getParameter("publish_time");
		String type = request.getParameter("type");
		String doThing = request.getParameter("doThing");
		String ch_title = ( request.getParameter("ch_title") == null ) ? "" : request.getParameter("ch_title");
		String ch_content = ( request.getParameter("ch_content") == null ) ? "" : request.getParameter("ch_content");
		String en_title = ( request.getParameter("en_title") == null ) ? "" : request.getParameter("en_title");
		String en_content = ( request.getParameter("en_content") == null ) ? "" : request.getParameter("en_content");
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		String[] op_ids = request.getParameterValues("op_ids");
		String[] ch_QA_types = request.getParameterValues("ch_QA_type");
		String[] en_QA_types = request.getParameterValues("en_QA_type");

		BusinessPub bean = new BusinessPub();
		bean.setId(id);
		bean.setCh_title(ch_title);
		bean.setCh_content(ch_content);
		bean.setEn_title(en_title);
		bean.setEn_content(en_content);
//		bean.setPublish_date_ROC(publish_date, publish_APM, publish_time); //民國年寫法
		bean.setPublish_date_AD(publish_date, publish_APM, publish_time);
		bean.setType(type);
		bean.setOp_ids(ToolsUtil.valuesToString(op_ids));
		bean.setCh_QA_type(ToolsUtil.valuesToString(ch_QA_types));
		bean.setEn_QA_type(ToolsUtil.valuesToString(en_QA_types));
		
		/*  新增 BusinessPubOrder
		 *  先取出舊的(business_id搜尋)，若舊的有相同分類op_id且已有寫好sort的，要先覆蓋到新的裡面  
		 */
		Map<String, BusinessPubOrder> orders_map = psService.getBusinessPubOrder(id); //map[op_id, LIST<bean>]
		ArrayList<BusinessPubOrder> orders = new ArrayList<>();
		for(String op_id : op_ids){
			
			int sort = 99;
			if(orders_map != null && !orders_map.isEmpty()){
				sort = orders_map.get(op_id) == null ? 99 : (orders_map.get(op_id)).getSort();
			}
			BusinessPubOrder order = new BusinessPubOrder();
			order.setOp_id(op_id);
			order.setSort(sort);
			orders.add(order);
		}
		String listToJson = "";
		if(id == 0){
			
			// 新增 BusinessPub
			int businessPub_id = psService.insertBusinessPub(bean);
			if(businessPub_id != 0){
				psService.insertBusinessPubOrder(businessPub_id, orders);
			}
			
			Map<String, String> map = new HashMap<>();
			map.put("businessPub_id", String.valueOf(businessPub_id));
			map.put("doThing", "updateBusinessPub");
			listToJson = gson.toJson(map);
		}else{
			if("updateBusinessPub_ch".equals(doThing)){
				psService.updateBusinessPub_ch(bean);
			}else {
				psService.updateBusinessPub_en(bean);
			}
			psService.updateBusinessPubOrder(id, orders);
		}
		
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(listToJson);
		out.close();
		return;
	}
	
	// 申辦業務-公告管理：找出某一筆的所有附檔
	private void getFile_list_BP(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int businessPub_id = ToolsUtil.parseInt(request.getParameter("businessPub_id"));
		ArrayList<BusinessPubFile> list = psService.getFileByBusinessPub(businessPub_id);
		//替file的id編碼
		for(BusinessPubFile bean : list){
			String id_str = dCipher.encrypt(String.valueOf(bean.getId()));
			bean.setId_str(id_str);
		}
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String listToJson = gson.toJson(list);
		out.write(listToJson);
		out.close();
		return;
		
	}
	
	// 申辦業務-公告管理：調整顯示順序
	private void setBusinessPub_Order(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		String sort = request.getParameter("sort");
		String oldSort = request.getParameter("old_sort");
		String type = request.getParameter("type"); //資料類型
		String op_id_one = request.getParameter("op_id_one"); //申辦業務類型
		String autoSort = request.getParameter("autoSort"); //申辦業務類型

		//序號要自動排列
		if("1".equals(autoSort)) {
			
			if(Integer.valueOf(sort) < 99) {
				/* 狀況1： 4   變 1，原本的1,2,3都 +1   (sort>=1 AND sort<4)
				 * 狀況2： 2   變 5，原本的3,4,5都 -1   (sort<=5 AND sort>2)
				 * 狀況4： 99 變 1，原本的1,2,3都 +1   (sort>=1 AND sort<99) */
				psService.setAutoSort_Bus(op_id_one, type, Integer.valueOf(sort), Integer.valueOf(oldSort));
			}
			else if(Integer.valueOf(sort) == 99) {
				/* 狀況3： 1  變 99 ，原本的2,3,4都 -1  (sort<99 AND sort>1)
				 * 狀況5；兩個都99，不處理 */
				psService.setAutoSort_Bus(op_id_one, type, Integer.valueOf(sort), Integer.valueOf(oldSort));
			}
		}
		
		psService.updateBusinessPubOrder_sort(id, sort);
		
		// 轉頁方法一
		String op_id = request.getParameter("op_id_one");
		response.sendRedirect(request.getContextPath() +
				"/console/edit-pages.view?doThing=businessPub&op_id_one="+op_id);
		
		// 轉頁方法二
		//businessPub_index(request, response);
	}

	// 申辦業務-公告管理：刪除某一個附檔
	private void removeBPfile_btn(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//刪除
		int file_id = ToolsUtil.parseInt(request.getParameter("id"));
		psService.deleteBusinessPubFile(file_id);
		
		//取出全部file
		getFile_list_BP(request, response);
		
	}
	
	// 申辦業務-分類管理：刪除之前，先檢查是否有網站資料依存在這個分類之下
	private void isParentId_used_PB(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String paper_type = request.getParameter("id");
		ArrayList<BusinessPub> list = psService.isParentId_used_PB(paper_type);
		String result = "";
		if(list!=null && !list.isEmpty()){
			result = "true";
		}
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
		return;
		
	}
	
	
	// 政府資訊公開-公告管理：新增公告
	private void openData_index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//放入業務類型標題
		String op_id_one = request.getParameter("op_id_one"); //opendata_type
		
		String op_id_oneName = options.get("opendata_type", op_id_one).getName();
		Map<String, String> title = new HashMap<>();
		title.put("op_id_one", op_id_one);
		title.put("op_id_oneName", op_id_oneName);
		request.setAttribute("title", title);
		
		//取出list
		ArrayList<OpenData> list = psService.getOpenDataByType(Integer.valueOf(op_id_one));
		for(OpenData bean : list){
			bean.setType_str( options.get("opendata_type", String.valueOf(bean.getType())).getName() );
		}
		
		request.setAttribute("openData_List", list);
		request.getRequestDispatcher("/console/opendata/index.jsp").forward(request, response);
	}
	
	// 政府資訊公開-公告管理：新增/選取其中一筆
	private void newOpenData_One(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		String op_id_one = request.getParameter("op_id_one");

		Map<String, String> title = new HashMap<>();
		title.put("op_id_one", op_id_one);
		title.put("op_id_oneName", options.get("opendata_type", op_id_one).getName());
		
		Map<String, String> map = new HashMap<>();
		if(id == 0){
			map.put("id", String.valueOf(id));
			map.put("ch_title", "");
			map.put("ch_content", "");
			map.put("en_title", "");
			map.put("en_content", "");
			map.put("publish_date", "");
			map.put("publish_APM", "");
			map.put("publish_time", "");
			map.put("type", op_id_one);
			map.put("seq", "99");
			map.put("ontop", "否");
			map.put("doThing", "newOpenData");
		}else{
			OpenData bean = psService.getOpenData(id);
			map.put("id", String.valueOf(id));
			map.put("ch_title", bean.getCh_title());
			map.put("ch_content", bean.getCh_content());
			map.put("en_title", bean.getEn_title());
			map.put("en_content", bean.getEn_content());
//			map.put("publish_date", bean.getPublish_date_ROC()); //民國年寫法
			map.put("publish_date", bean.getPublish_date_AD());
			map.put("publish_APM", bean.getPublish_APM());
			map.put("publish_time", bean.getPublish_Time());
			map.put("type", String.valueOf(bean.getType()));
			map.put("seq", String.valueOf(bean.getSeq()));
			map.put("ontop", bean.getOntop() == true ? "true" : "false");
			map.put("doThing", "updateOpenData");
		}
		
		request.setAttribute("title", title);
		request.setAttribute("openData_One", map);
		request.getRequestDispatcher("/console/opendata/edit-opendata.jsp").forward(request, response);
		
	}
	
	// 政府資訊公開-公告管理：新增/更新公告
	private void newOrUpdate_OpenData_One(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String publish_date = request.getParameter("publish_date");
		String publish_APM = request.getParameter("publish_APM");
		String publish_time = request.getParameter("publish_time");
		String seq = request.getParameter("seq");
		String ontop = request.getParameter("ontop");
		String type = request.getParameter("type");
		String doThing = request.getParameter("doThing");
		String ch_title = ( request.getParameter("ch_title") == null ) ? "" : request.getParameter("ch_title");
		String ch_content = ( request.getParameter("ch_content") == null ) ? "" : request.getParameter("ch_content");
		String en_title = ( request.getParameter("en_title") == null ) ? "" : request.getParameter("en_title");
		String en_content = ( request.getParameter("en_content") == null ) ? "" : request.getParameter("en_content");
		int id = ToolsUtil.parseInt(request.getParameter("id"));

		OpenData bean = new OpenData();
		bean.setId(id);
		bean.setCh_title(ch_title);
		bean.setCh_content(ch_content);
		bean.setEn_title(en_title);
		bean.setEn_content(en_content);
//		bean.setPublish_date_ROC(publish_date, publish_APM, publish_time); //民國年寫法
		bean.setPublish_date_AD(publish_date, publish_APM, publish_time);
		bean.setType(Integer.valueOf(type));
		bean.setSeq("0".equals(ontop) ? 99 : Integer.valueOf(seq));
		bean.setOntop("0".equals(ontop) ? false : true);
		
		String listToJson = "";
		if(id == 0){
			// 新增 BusinessPub
			int openData_id = psService.insertOpenData(bean);
			Map<String, String> map = new HashMap<>();
			map.put("openData_id", String.valueOf(openData_id));
			map.put("doThing", "updateOpenData");
			listToJson = gson.toJson(map);
			
		}else{
			if("updateOpenData_ch".equalsIgnoreCase(doThing)){
				psService.updateOpenData_ch(bean);
			}else{
				psService.updateOpenData_en(bean);
			}
		}
		
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(listToJson);
		out.close();
		return;
		
	}
	
	// 政府資訊公開-公告管理：找出某一筆的所有附檔
	private void getFile_list_OD(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int openData_id = ToolsUtil.parseInt(request.getParameter("openData_id"));
		ArrayList<OpenDataFile> list = psService.getFileByOpenData(openData_id);
		//替file的id編碼
		for(OpenDataFile bean : list){
			String id_str = dCipher.encrypt(String.valueOf(bean.getId()));
			bean.setId_str(id_str);
		}
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String listToJson = gson.toJson(list);
		out.write(listToJson);
		out.close();
		return;
		
	}

	
	// 政府資訊公開-公告管理：刪除某一個附檔
	private void removeFile_OD(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//刪除
		int file_id = ToolsUtil.parseInt(request.getParameter("id"));
		psService.deleteOpenDataFile(file_id);
		
		//取出全部file
		getFile_list_OD(request, response);
		
	}
	// 政府資訊公開-公告管理：刪除其中一筆
	private void removeOpenData_One(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		psService.deleteOpenData(id);
		psService.deleteOpenDataFile_byODId(id);
		String op_id_one = request.getParameter("op_id_one");
		
		// 轉頁方法一：
		response.sendRedirect("/console/edit-pages.view?doThing=openData_index&op_id_one=" + op_id_one);
		
		// 轉頁方法二
		//openData_index(request, response);
		
	}
	
	private void setOpenData_Order(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		int id = ToolsUtil.parseInt(request.getParameter("id"));
		boolean ontop = "是".equals(request.getParameter("ontop")) ? true : false;
		int seq = (ontop == true) ? Integer.valueOf(request.getParameter("seq")) : 99;
		psService.update_OpenDataSort(id, seq, ontop);
		
		// 轉頁方法一：
		String op_id_one = request.getParameter("op_id_one");
		response.sendRedirect("/console/edit-pages.view?doThing=openData_index&op_id_one=" + op_id_one);
		
		// 轉頁方法二
		//openData_index(request, response);

	}
	
	// 政府資訊公開-分類管理：刪除之前，先檢查是否有網站資料依存在這個分類之下
	private void isParentId_used_OD(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int paper_type = ToolsUtil.parseInt(request.getParameter("id"));
		ArrayList<OpenData> list = psService.isParentId_used_OD(paper_type);
		String result = "";
		if(list!=null && !list.isEmpty()){
			result = "true";
		}
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
		return;
		
	}
	
	//列出有流程圖的申辦業務
	public static ArrayList<Integer> list_flow_chart(String language){
		ArrayList<Integer> flow_chart_list = new ArrayList<Integer>(); 
		for(Pages page : PagesDAO.list()){
			if(page.getType().matches("\\d+") && language.equalsIgnoreCase(page.getLanguage())
				&& !dTools.isEmpty(page.getPage_content())){
				flow_chart_list.add(Integer.parseInt(page.getType()));
			}
		}
		return flow_chart_list;
	}
}
