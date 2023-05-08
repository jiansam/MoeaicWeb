package gov.moeaic.sql.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.dasin.tools.dTools;

import gov.moeaic.sql.bean.WebName;
import gov.moeaic.sql.bean.WebOrder;
import gov.moeaic.sql.dao.PagesService;

public class WebNameImageServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PagesService psService = null;
	HashMap<String, String> parameters = null; //放置form表單資料 + file_name
	ArrayList<String> ch_titles = null; //放勾選的分類title
	ArrayList<String> titles = null; //放勾選的分類title
	byte[] file_content = null; //把file_content放入
	int webName_id = 0;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		psService = new PagesService();
		parameters = new HashMap<>(); //放置form表單資料 + file_name
		ch_titles = new ArrayList<>(); //放勾選的分類title
		titles = new ArrayList<>(); //放勾選的分類title
		
		/* 相關網站-資料管理：新增修改資料
		 * 先取出回傳回來的資料
		 */
		if(ServletFileUpload.isMultipartContent(request)){
			InputStream is = null;
			try{
				ServletFileUpload sfu = new ServletFileUpload();
				sfu.setHeaderEncoding("UTF-8");
				
				FileItemIterator iter = sfu.getItemIterator(request);

				while(iter.hasNext()){
					FileItemStream fis = iter.next();
					is = fis.openStream();

					// Note: normal form fields MUST be placed before file input.
					if(fis.isFormField()){
						if("web_titles".equals(fis.getFieldName())){
							titles.add(Streams.asString(is, "utf-8"));
						}else{
							parameters.put(fis.getFieldName(), Streams.asString(is, "utf-8"));
						}
					}else{
						ServletContext context = request.getServletContext();
						//在map內儲存路徑名 
						if(fis.getName().trim().length()!=0
								&& dTools.trim(context.getMimeType(fis.getName())).startsWith("image/") ){
							parameters.put("filepath", context.getInitParameter("images_upload_directory") + "/" 
									+ newFilename(fis.getName()));
							
							//把實體檔案+名稱放入參數中
							parameters.put("file_name" , fis.getName());
							file_content = IOUtils.toByteArray(is);
							
							//儲存檔案到專案裡面的實體資料夾位置
							File outFile = new File(context.getRealPath(parameters.get("filepath")));
							FileOutputStream fos = new FileOutputStream(outFile);
							//StreamCopier.copy(is, fos); //inputStream只能使用一次，所以第二次輸出直接使用byte[] write
							fos.write(file_content);
							fos.flush();
							fos.getFD().sync();
							fos.close();
							Thread.sleep(1000);
							
						}
						
						webName_id = ToolsUtil.parseInt(parameters.get("id"));
						if(webName_id!=0){
							WebName webName = psService.getWebName(webName_id);
							//沒有傳新檔案，要拿出舊檔案放入
							if(parameters.get("filepath") == null){
								parameters.put("filepath", webName.getPhotoFile());
								//把實體檔案+名稱放入參數中
								parameters.put("file_name" , webName.getFile_name());
								file_content = webName.getFile_content();
								
							}else{
								//有傳新檔案放入資料夾的話，舊檔要先刪掉，才能update新的路徑（舊的路徑也會消失）
								if(webName.getPhotoFile() !=null && webName.getPhotoFile().trim().length()!=0){
									File file = new File(request.getServletContext().getRealPath(webName.getPhotoFile()));
									file.delete();
								}
							}
						}
						
					}
				}
			} catch (Exception e){
				e.printStackTrace();	
			} finally {
				if(is!=null){
					try {
						is.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		insertOrUpdate(request, response);

	}
	
	
	
	private void insertOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException
	{

		/* 取值結束======================================================================================
		 * 處理選到的title
		 * 將分類+細類+sort 放入  WebOrder的list中
		 */
		ArrayList<WebOrder> list = new ArrayList<>();
		if(titles==null || titles.isEmpty()){ //如果沒有選擇類別，那就歸入[未分類]
//			WebOrder temp = new WebOrder();
//			temp.setWebName_id(webName_id);
//			temp.setParent_id(6);
//			temp.setSort("99");
//			list.add(temp);
//			
//			title_str = "6";
		}else{
			/* 更新順序時，因為有些sort已經有排序，所以不能直接刪除給sort=99
			 * 要先取出該WebName_id的所有資料，如果有跟新選擇的Title相同的話，就取出原本的sort再一起insert回去
			 * WebName_id != 0：表示以前有資料要跑迴圈 
			 * WebName_id == 0：沒資料不用比 
			 */
			ArrayList<WebOrder> orders = psService.getWebOrderByWebName(webName_id);
			for(String temp : titles){
				int parent_id = ToolsUtil.parseInt(temp);
				WebOrder bean = new WebOrder();
				bean.setWebName_id(webName_id);
				bean.setParent_id(parent_id);
				bean.setSort(99);
				bean.setLang(parameters.get("lang"));
				
				if(orders!=null && orders.size()!=0){
					for(WebOrder order : orders){
						if(parent_id == order.getParent_id()){
							bean.setSort(order.getSort());
							break;
						}
					}
				}
				
				list.add(bean);
			}
		}
		String title_str = ToolsUtil.valuesToString(titles.toArray(new String[0]));
		WebName bean = new WebName();
		bean.setId(webName_id);
		bean.setName(parameters.get("name"));
		bean.setLang(parameters.get("lang"));
		bean.setWeb_titles(title_str);
		bean.setUrl(parameters.get("url"));
		bean.setShowIndex( "0".equals(parameters.get("showIndex")) ? false : true);
//		bean.setUpdate_date_ROC(parameters.get("update_date")); //民國寫法
		bean.setUpdate_date_AD(parameters.get("update_date")); //西元寫法
		bean.setPhotoFile(parameters.get("filepath"));
		bean.setFile_name(parameters.get("file_name"));
		bean.setFile_content(file_content);
		
		if(parameters.get("id")!=null && webName_id==0){
			int id = psService.insertWebName(bean);
			psService.insertWebOrder(id, list);
			synchronize(request, id);
		}else if(parameters.get("id")!=null && webName_id!=0){
			psService.updateWebName(bean);
			psService.updateWebOrder(webName_id, list);
			synchronize(request, webName_id);
		}
		
		String path = request.getContextPath();
		response.sendRedirect(path + "/console/edit-pages.view?doThing=getWebName");
	}

	public static String newFilename(String filename){
		if(filename.lastIndexOf(".") != -1){
			return UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
		}else{
			return UUID.randomUUID().toString();
		}
	}
	
	public static void synchronize(HttpServletRequest request, int id) {
		try {
			HttpURLConnection conn = (HttpURLConnection)(
				new URL(request.getServletContext().getInitParameter("SynchronizeURL") + "/webnamesynchronize?id=" + id).openConnection());
			conn.setRequestMethod("GET");
			InputStream is = conn.getInputStream();
//			is.readAllBytes();
			is.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
