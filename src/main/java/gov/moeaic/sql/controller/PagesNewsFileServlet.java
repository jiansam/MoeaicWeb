package gov.moeaic.sql.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.dasin.cryptography.dCipher;

import com.google.gson.Gson;

import gov.moeaic.sql.bean.BusinessPubFile;
import gov.moeaic.sql.bean.OpenDataFile;
import gov.moeaic.sql.bean.PagesNewsFile;
import gov.moeaic.sql.dao.PagesService;

public class PagesNewsFileServlet extends HttpServlet
{

	PagesService psService = null;
	HashMap<String, String> parameters = null;
	HashMap<String, byte[]> files = null;
	Gson gson = null;
	String listToJson = "";
	String doThing = "";
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		psService = new PagesService();
		
		
		// 研究報告：加入附加檔案
		parameters = new HashMap<>();
		files = new HashMap<>();
		
		if(ServletFileUpload.isMultipartContent(request)){
			try{
				ServletFileUpload sfu = new ServletFileUpload();
				sfu.setHeaderEncoding("UTF-8");
				FileItemIterator iter = sfu.getItemIterator(request);

				while(iter.hasNext()){
					FileItemStream fis = iter.next();
					InputStream inputStream = fis.openStream();
					if(fis.isFormField()){
						parameters.put(fis.getFieldName(), Streams.asString(inputStream,"UTF-8"));
					}else{
//						System.out.println("filename="+fis.getName());
						if(fis.getName()==null || fis.getName().trim().length()==0){
							files = null;
						}else{
							files.put(fis.getName(), IOUtils.toByteArray(inputStream));
						}
					}
					inputStream.close();
				}
			}catch(Exception e){
				e.printStackTrace();	
			}
		}
		
		
		gson = new Gson();
		listToJson = "";
		doThing = parameters.get("doThing");
		
		//新增研究報告、最新公告、最新業務統計檔案
		if("newFile_ch".equalsIgnoreCase(doThing) || "newFile_en".equalsIgnoreCase(doThing)){
			doInsertOrUpdateNewsFile(request, response);
		}
		//更新研究報告、最新公告、最新業務檔案
		else if("updateFile_ch".equalsIgnoreCase(doThing) || "updateFile_en".equalsIgnoreCase(doThing)){
			if(files != null) {
				doInsertOrUpdateNewsFile(request, response);
			}
		}		
		//新增申辦業務檔案
		else if("newBPFile_ch".equalsIgnoreCase(doThing) || "newBPFile_en".equalsIgnoreCase(doThing)){
			doInsertOrUpdateBussinessPubFile(request, response);
		}
		//更新申辦業務檔案
		else if("updateBPFile_ch".equalsIgnoreCase(doThing) || "updateBPFile_en".equalsIgnoreCase(doThing)){
			if(files != null) {
				doInsertOrUpdateBussinessPubFile(request, response);
			}
		}
		//新增政府資訊公開檔案
		else if("newODFile_ch".equalsIgnoreCase(doThing) || "newODFile_en".equalsIgnoreCase(doThing)){
			doInsertOrUpdateOpenDataFile(request, response);
		}
		//更新政府資訊公開檔案
		else if("updateODFile_ch".equalsIgnoreCase(doThing) || "updateODFile_en".equalsIgnoreCase(doThing)){
			if(files != null) {
				doInsertOrUpdateOpenDataFile(request, response);
			}
		}		

	}

	private void doInsertOrUpdateOpenDataFile(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int openData_id = Integer.valueOf(parameters.get("master_id"));
		ArrayList<OpenDataFile> beans = new ArrayList<>();
		for(String key : files.keySet()){
			OpenDataFile bean = new OpenDataFile();
			bean.setOpenData_id(openData_id);
			bean.setId(ToolsUtil.parseInt(parameters.get("id")));
			bean.setFile_name(key);
			bean.setFile_content(files.get(key));
			bean.setFile_lang(parameters.get("file_lang"));
			beans.add(bean);
		}
		//insert可多筆
		if("newODFile_ch".equalsIgnoreCase(doThing) || "newODFile_en".equalsIgnoreCase(doThing)){
			psService.insertOpenDataFile(beans);
		}
		//update只有單筆
		else if("updateODFile_ch".equalsIgnoreCase(doThing) || "updateODFile_en".equalsIgnoreCase(doThing)){
			psService.updateOpenDataFile(beans.get(0));
		}
		
		
		//取出所有file傳回
		ArrayList<OpenDataFile> list = psService.getFileByOpenData(openData_id);
		for(OpenDataFile bean : list){
			String id_str = dCipher.encrypt(String.valueOf(bean.getId()));
			bean.setId_str(id_str);
		}
		listToJson = gson.toJson(list);
		
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(listToJson);
		out.close();
		return;
	}

	private void doInsertOrUpdateBussinessPubFile(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int businessPub_id = Integer.valueOf(parameters.get("businessPub_id"));
		ArrayList<BusinessPubFile> beans = new ArrayList<>();
		for(String key : files.keySet()){
			BusinessPubFile bean = new BusinessPubFile();
			bean.setBusinessPub_id(businessPub_id);
			bean.setId(ToolsUtil.parseInt(parameters.get("id")));
			bean.setFile_name(key);
			bean.setFile_content(files.get(key));
			bean.setFile_lang(parameters.get("file_lang"));
			beans.add(bean);
		}
		//insert可多筆
		if("newBPFile_ch".equalsIgnoreCase(doThing) || "newBPFile_en".equalsIgnoreCase(doThing)){
			psService.insertBusinessPubFile(beans);
		}
		//update只有單筆
		else if("updateBPFile_ch".equalsIgnoreCase(doThing) || "updateBPFile_en".equalsIgnoreCase(doThing)){
			psService.updateBusinessPubFile(beans.get(0));
		}
		
		//取出所有file傳回
		ArrayList<BusinessPubFile> list = psService.getFileByBusinessPub(businessPub_id);
		for(BusinessPubFile bean : list){
			String id_str = dCipher.encrypt(String.valueOf(bean.getId()));
			bean.setId_str(id_str);
		}
		listToJson = gson.toJson(list);
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(listToJson);
		out.close();
		return;
	}

	private void doInsertOrUpdateNewsFile(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int pagesNews_id = Integer.valueOf(parameters.get("pagesNews_id"));
		ArrayList<PagesNewsFile> beans = new ArrayList<>();
		for(String key : files.keySet()){
			PagesNewsFile bean = new PagesNewsFile();
			bean.setPagesNews_id(pagesNews_id);
			bean.setId(ToolsUtil.parseInt(parameters.get("id")));
			bean.setFile_name(key);
			bean.setFile_content(files.get(key));
			bean.setFile_lang(parameters.get("file_lang"));
			beans.add(bean);
		}
		//insert可多筆
		if("newFile_ch".equalsIgnoreCase(doThing) || "newFile_en".equalsIgnoreCase(doThing)){
			psService.insertPagesNewsFile(beans);
		}
		//update只有單筆
		else if("updateFile_ch".equalsIgnoreCase(doThing) || "updateFile_en".equalsIgnoreCase(doThing)){
			psService.updatePagesNewsFile(beans.get(0));
		}
		
		
		//取出所有file傳回
		ArrayList<PagesNewsFile> list = psService.getPagesNewsFile(pagesNews_id);
		for(PagesNewsFile bean : list){
			String id_str = dCipher.encrypt(String.valueOf(bean.getId()));
			bean.setId_str(id_str);
		}
		listToJson = gson.toJson(list);
		
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.write(listToJson);
		out.close();
		return;
	}
}
