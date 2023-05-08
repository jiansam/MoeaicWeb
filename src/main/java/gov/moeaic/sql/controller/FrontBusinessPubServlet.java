package gov.moeaic.sql.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dasin.tools.dTools;

import gov.moeaic.sql.bean.BusinessPubFile;
import gov.moeaic.sql.bean.BusinessPubOrder;
import gov.moeaic.sql.bean.Option;
import gov.moeaic.sql.bean.Pages;
import gov.moeaic.sql.dao.PagesService;
import gov.moeaic.web.bean.OptionManager;

public class FrontBusinessPubServlet extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String op_id_one = request.getParameter("op_id_one");
		String lang = request.getParameter("lang");
		String qa = request.getParameter("qa") == null ? "" : request.getParameter("qa");
		request.setAttribute("op_id_one", op_id_one);
		request.setAttribute("lang", lang);
		
		OptionManager options = (OptionManager)request.getServletContext().getAttribute("optionManager");
		Option business_category = options.get("business_category", op_id_one);
		if(business_category == null) {
//			System.out.println("轉錯誤頁面");
			request.getRequestDispatcher("/error-page.jsp").forward(request, response);
			return;
		}
		request.setAttribute("title_ch", business_category.getName());
		request.setAttribute("title_en", business_category.getEname());
		
		PagesService pService = new PagesService();
		
		//常見問答 有使用的分類
		String QA_type_used = pService.getBusPub_QAType_used(lang, op_id_one); //取出使用的type id  39,40,41,40,39,38
		ArrayList<Option> QA_type = new ArrayList<>();
		if(QA_type_used != null){
			
			ArrayList<Option> QA_type_all = null;
			if("ch".equalsIgnoreCase(lang)){
				QA_type_all = options.getCh_QA_type();
			}else{
				QA_type_all = (options.getEn_QA_type() == null) ? new ArrayList<Option>() : options.getEn_QA_type();
			}
			
			//確認有建立中文分類、英文分類才開始建立選項（有使用不代表有建立，有可能分類被刪除了）
			if(QA_type_all != null && !QA_type_all.isEmpty()){
				//建立新的QA_type 首先先放入[全部]選項
				Option op = new Option();
				op.setId(0);
				op.setName("全部");
				op.setEname("All");
				QA_type.add(op);
				
				//2019.5.24.dasin : 由於FAQ使用的QA_type已有3碼, 故必須完全比對正確才能放進來(例如原本的做法 40 會因為 140 而被加入)
				List<String> QA_type_used_list = Arrays.asList(dTools.trim(QA_type_used).split(","));
				for(Option type : QA_type_all){
					//if(QA_type_used.indexOf( String.valueOf(type.getId()) ) != -1){
					if(QA_type_used_list.contains(String.valueOf(type.getId()))) {
						QA_type.add(type);
					}
				}
			}
		}
		request.setAttribute("QA_type", QA_type);
		
		//流程圖
		Pages flow_chart = pService.getPages(op_id_one, lang);
		if(flow_chart != null){
			request.setAttribute("flow_chart", flow_chart.getPage_content());
		}
		
		
		//書表項目名稱 （但沒有檔案的要移除）
		ArrayList<BusinessPubOrder> list = pService.getFrontBusinessPubOrder(op_id_one, lang);
		ArrayList<String> list_name = new ArrayList<>(); //['申請書表','投資法規','說明書']
		Map<String, ArrayList<BusinessPubOrder>> list_map = new HashMap<>(); //[ {'申請書表', [{bean},{bean}]} , {'投資法規', [{bean},{bean}]} ]
		
		for(BusinessPubOrder bean : list){

			// 先將書信type轉成中文或英文
			String type = bean.getType();
			String type_name = "";
			if("ch".equalsIgnoreCase(lang)){
				type_name = options.get("business_type", type).getName();
			}else {
				type_name = options.get("business_type", type).getEname();
			}
			
			//[常見問答集 type = 14]
			if("14".equals(type)){
				
				// 如果有設定分類，就只選該分類 
//				if(qa.trim().length() != 0 && "ch".equalsIgnoreCase(lang)){
//					if(!qa.equals(bean.getCh_QA_type())){
//						continue;
//					}
//					
//				}else if(qa.trim().length() != 0 && "en".equalsIgnoreCase(lang)){
//					if(!qa.equals(bean.getEn_QA_type())){
//						continue;
//					}
//				}
				
				// 本文中 \r\n改成<br>
				String ch_content = bean.getCh_content();
				String en_content = bean.getEn_content();
				if(ch_content != null && ch_content.length() != 0){
					bean.setCh_content(ch_content.replaceAll("\n", "<br>"));
				}
				if(en_content!=null && en_content.length() != 0){
					bean.setEn_content(en_content.replaceAll("\n", "<br>"));
				}
			}
			
			/*  建立 list_name & list_map
			 *   list_name[申請書,投資法規,...] 
			 *   list_map [{申請書,list}, {投資法規,list}, ...]
			 */
			if(list_map.get(type_name) == null){
				list_map.put(type_name, new ArrayList<>());
				list_name.add(type_name);
			}
			
			list_map.get(type_name).add(bean);
		}
		request.setAttribute("list_name", list_name);
		request.setAttribute("list_map", list_map);
		
		
		ArrayList< ArrayList<BusinessPubOrder>> lists = new ArrayList<>();
		for(String name : list_name){
			lists.add(list_map.get(name));
		}
		request.setAttribute("lists", lists);
		
		
		
		// 依據lang，取出書信檔案 [{'businessPub_id',[file,file,file]} , {'businessPub_id',[file,file,file]} ]
		Map<String, ArrayList<BusinessPubFile>> files = pService.getBusinessPubFile(lang);
		request.setAttribute("files", files);
		
		if(request.getParameter("type")!=null){
			request.setAttribute("isShowFlow", "show-flow");
		}
		
		if("ch".equalsIgnoreCase(lang)){
			request.getRequestDispatcher("/chinese/businessPub.jsp").forward(request, response);
		}else{
			request.getRequestDispatcher("/english/businessPub.jsp").forward(request, response);
		}
		
		
	}
	
}
