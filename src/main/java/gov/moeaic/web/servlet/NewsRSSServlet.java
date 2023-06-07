package gov.moeaic.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dasin.cryptography.dCipher;
import org.dasin.tools.AsciiUtil;
import org.dasin.tools.dTools;
import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jsoup.Jsoup;

import gov.moeaic.sql.bean.DeletedPageNews;
import gov.moeaic.sql.bean.PagesNews;
import gov.moeaic.sql.bean.PagesNewsFile;
import gov.moeaic.sql.dao.PagesNewsDAO;
import gov.moeaic.sql.dao.PagesService;

public class NewsRSSServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private String lang; 
	private ArrayList<String> types = null;
	
	@Override
	 public void init(ServletConfig config) throws ServletException {
	  super.init(config);
	  lang = config.getInitParameter("language");
	  types = new ArrayList<>(Arrays.asList("xls", "xlsx", "doc", "docx", "ppt", "pps", 
			  								"pptx", "ppsx", "txt", "tiff", "tif", "rtf", 
			  								"mp3", "wmv", "csv", "xml", "pdf", "odt", 
			  								"fodt", "ods", "fods", "odp", "fodp", "odb", 
			  								"odg", "fodg", "odf", "jpg", "gif", "png", "bmp", "jpeg"));
	  
	 }
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doGet(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		PagesService ps = new PagesService();
		ArrayList<PagesNews> list = ps.getFrontPagesNews_CSS("new_ann", lang); //zh-tw  en-us
		String lang2 = "zh-tw".equals(lang) ? "ch":"en";
		Map<Integer, ArrayList<PagesNewsFile>> files_map = ps.getPagesNewsFile_map(lang2); //ch  en
		
		String author = "2.16.886.101.20003.20007.20006";
		
		String urlPath = "";
		int port = request.getServerPort();
		if(port != 80){
			urlPath = "http://" + request.getServerName() + ":" + port + request.getContextPath();
		}else{
			urlPath = "http://" + request.getServerName() + request.getContextPath();
		}
		
		
		ServletOutputStream out = null;
		try{
			out = response.getOutputStream();
			
			// root
			Element root = new Element("rss");
			root.setAttribute("version", "2.0");
			// root : 設定sc的Namespace，需要使用
			Namespace dc = Namespace.getNamespace("dc", "http://purl.org/dc/elements/1.1/");
			root.addNamespaceDeclaration(dc);
			// root : 檢查是否有符合提供的xml標準規範
			root.setAttribute("schemaLocation" , "http://www.gov.tw/schema/RSS20.xsd");
			
			
			Document doc = new Document(root);
			
			
			// 子目錄：channel
			Element channel = new Element("channel");
			channel.addContent(new Element("title").setText("zh-tw".equals(lang) ? "經濟部投資審議委員會-最新消息" : "INVESTMENT COMMISSION, MOEA - Latest News"))
				.addContent(new Element("link").setText(request.getRequestURL().toString())) //http://localhost:8080/rss-news.xml
				.addContent(new Element("description").setText("zh-tw".equals(lang) ? "經濟部投資審議委員會-最新消息" : "INVESTMENT COMMISSION, MOEA")) //經濟部投資審議委員會-最新消息RSS頻道
				.addContent(new Element("language").setText(lang))
				.addContent(new Element("copyright").setText("zh-tw".equals(lang) ? "2016 © 版權所有 經濟部投資審議委員會 " : "Copyright © 2016 Investment Commission, MOEA"))
				.addContent(new Element("lastBuildDate").setText(getGMT(new Date())))
				.addContent(new Element("ttl").setText("20"));
			root.addContent(channel);
			
			
			// 子目錄：channel底下的子目錄
			for(PagesNews bean : list){
				
				int news_id = bean.getId();
				String title = "";
				String content = "";
				if("zh-tw".equals(lang)){
					title = bean.getCh_title();
					content = bean.getCh_content();
				}else{
					title = bean.getEn_title();
					content = bean.getEn_content();
				}
				
				
				Element item = new Element("item")
						.addContent(new Element("title").setContent(new CDATA(title))) //<![CDATA[ 經濟部投資審議委員會第1141次委員會議新聞稿 ]]>
						.addContent(new Element("link").setText(
								urlPath + "/news.view?id=" + bean.getId() + "&lang=" + lang2 + "&type=new_ann&do=data") )
						.addContent(new Element("description").setContent(new CDATA(content.replaceAll("<br>", "\n").replaceAll("<br/>", "\n").replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("\\<.*?\\>", ""))))
						.addContent(new Element("author").setText(author))
						.addContent(new Element("comments").setText(""))
						.addContent(new Element("pubDate").setText(getGMT(bean.getPublish_date()))) //
						.addContent(new Element("source").setText(author))
						
						.addContent(new Element("title" , dc).setContent(new CDATA(
								"zh-tw".equals(lang) ? title : AsciiUtil.sbc2dbcCase(title))))
						.addContent(new Element("creator" , dc).setText(author))
						.addContent(new Element("subject" , dc).setContent(new CDATA(title)))
						.addContent(new Element("description" , dc).setContent(new CDATA(
								"zh-tw".equals(lang) ? Jsoup.parse(content.replaceAll("<br>", "\n").replaceAll("<br/>", "\n").replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("\\<.*?\\>", "")).text() :
								AsciiUtil.sbc2dbcCase(Jsoup.parse(content.replaceAll("<br>", "\n").replaceAll("<br/>", "\n").replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("\\<.*?\\>", "")).text())
						)))
						.addContent(new Element("contributor" , dc).setText(author))
						.addContent(new Element("type" , dc).setText("text/html"))
						.addContent(new Element("format" , dc).setText("Text"))
						.addContent(new Element("source" , dc).setText(author))
						.addContent(new Element("language" , dc).setText(lang))
						.addContent(new Element("coverage" , dc).setText(
											getGMT(bean.getPublish_date()) + "-" + getGMT(2050, 11, 31) ))
						.addContent(new Element("publisher" , dc).setText(author))
						.addContent(new Element("date" , dc).setText(getGMT(bean.getPublish_date())))
						.addContent(new Element("identifier" , dc).setText(author))
						.addContent(new Element("relation" , dc).setText(""))
						.addContent(new Element("rights" , dc).setText("zh-tw".equals(lang) ? "2016 © 版權所有 經濟部投資審議委員會 " : "Copyright © 2016 Investment Commission, MOEA"))
						.addContent(new Element("category.theme").setText("950"))
						.addContent(new Element("category.cake").setText("2B6"))
						.addContent(new Element("category.service").setText("I41"))
						.addContent(new Element("keywords").setText(""))
						.addContent(new Element("NewsID").setText("N"+lang+bean.getId())) //
						.addContent(new Element("IsDeleteNews").setText("N")) //
						.addContent(new Element("IsSendEpaper").setText("Y"))
						.addContent(new Element("IsMoveToHistory").setText("Y"))
						.addContent(new Element("RelateContacts").setText(""))
						//2019.10.31.dasin : change RelateUrl to RelateLinks
						//.addContent(new Element("RelateUrl").setText(""))
						.addContent(new Element("RelateLinks").setText(""))
						;
				
				// 檔案 ---------------------------------------------------
				Element relateFiles = new Element("RelateFiles");

				ArrayList<PagesNewsFile> files = files_map.get(news_id);
				ArrayList<String> nameBeUsed = new ArrayList<>();
				
				
				/* <RelateFiles>
				 *	 <FileItem>
				 *	   <FileName>相關檔案</FileName>
				 *	   <FileTypes>
				 *
				 *	     <FileTypeItem>
				 *	       <FileExt>doc</FileExt>
				 *	       <FileUrl>http://www.moea.gov.tw/aaa.doc</FileUrl>
				 *	     </FIleTypeItem>
				 *
				 *	     <FileTypeItem>
				 *	        <FileExt>odf</FileExt>
				 *	        <FileUrl>http://www.moea.gov.tw/aaa.odf</FileUrl>
				 *	     </FIleTypeItem>
				 *
				 *	   </FileTypes>
				 *	 </FileItem>
				 * </RelateFiles>
				 */
				
				if(files != null){
					for(PagesNewsFile bean2 : files){
						String name = bean2.getFile_name(); // 檔名 + 附檔名
						String file_name = name.substring(0, name.lastIndexOf("."));  //檔名
						
						// 該檔名若使用過，就會把所有同檔名附檔都取出使用了，所以同一檔名只使用一次就好
						if(nameBeUsed.contains(file_name)){
							continue;
						}
						
						// 只要檔名有使用了，就加入已使用的LIST內，供之後迴圈檢查避免重做相同檔名
						nameBeUsed.add(file_name);
						
						// 準備map裝載同檔名的所有附檔格式跟URL [附檔名 , url]
						Map<String, String> filetype = new HashMap<>(); 
						for(PagesNewsFile bean3 : files){
							String name2 = bean3.getFile_name();
							// 將相同檔名的附檔名跟檔案下載路徑放入map
							if(file_name.equalsIgnoreCase(name2.substring(0, name2.lastIndexOf(".")))){
								String type = name2.substring(name2.lastIndexOf(".") + 1);
								// 只加入有在上傳名單內的附檔名
								if(types.contains(type)){
									filetype.put(type, urlPath + "/download-file.jsp?id=" + dCipher.encrypt(String.valueOf(bean3.getId())));
								}
								
							}
						}
						
							
						// 檢查若有office附檔名，卻沒有OpenDocument格式，就移除office格式檔案	
						if(filetype.get("doc") != null || filetype.get("docx") != null){
							if(filetype.get("odt") == null){
								filetype.remove("doc");
								filetype.remove("docx");
							}
						}
						
						if(filetype.get("xls") != null || filetype.get("xlsx") != null){
							if(filetype.get("ods") == null){
								filetype.remove("xls");
								filetype.remove("xlsx");
							}
						}
							
						if(filetype.get("ppt") != null || filetype.get("pptx") != null){
							if(filetype.get("odp") == null){
								filetype.remove("ppt");
								filetype.remove("pptx");
							}
						}
						
						
						// 經過上述驗證後，若map內還有資料才裝入RSS內
						if(filetype != null && !filetype.isEmpty()){
							
							Element fileTypes = new Element("FileTypes");
							for(String key : filetype.keySet()){
								// 再檢查有沒有
								
								
								fileTypes.addContent(new Element("FileTypeItem")
														.addContent(new Element("FileExt").setText(key))
														.addContent(new Element("FileUrl").setText(filetype.get(key)))
													);
							}
							Element fileItem = new Element("FileItem")
													.addContent(new Element("FileName").setText(file_name))
													.addContent(fileTypes);
							relateFiles.addContent(fileItem);							
						}
							// 只要是Office的附檔名就進來，檢查有沒有提供od開頭的附檔名
//						if("doc".equalsIgnoreCase(file_type) || "docx".equalsIgnoreCase(file_type)
//							|| "xls".equalsIgnoreCase(file_type) || "xlsx".equalsIgnoreCase(file_type)
//							|| "ppt".equalsIgnoreCase(file_type) || "pptx".equalsIgnoreCase(file_type)){
//							
//							// 只要有符合的附檔名就進來，並加入已使用的LIST內，供之後迴圈檢查避免重做相同檔名
//							nameBeUsed.add(file_name);
//							
//							Element fileTypes = new Element("FileTypes");
//							// 取出檔名後，要開始取出附檔名跟下載連結，並且記錄有沒有od開頭的附檔名
//							boolean ishasOd = false;
//							for(PagesNewsFile bean3 : files){
//								String name2 = bean3.getFile_name();
//								// 找出相同檔名
//								if(file_name.equalsIgnoreCase(name2.substring(0, name2.lastIndexOf(".")))){
//									// 將相同檔名的附檔名跟檔案下載路徑放入 
//									Element fileTypeItem = new Element("FileTypeItem")
//											.addContent(new Element("FileExt").setText(name2.substring(name2.lastIndexOf(".") + 1)))
//											.addContent(new Element("FileUrl").setText(urlPath + "/download-file.jsp?id=" + dCipher.encrypt(String.valueOf(bean3.getId()))));
//									fileTypes.addContent(fileTypeItem);
//									// 如果附檔名有包含od的話，就是true
//									if(name2.substring(name2.lastIndexOf(".")+1).indexOf("od") != -1){
//										ishasOd = true;
//									}
//								}
//							}
//							
//							// 如果office檔案有配一個od的話才加入RSS列表內
//							if(ishasOd == true){
//								Element fileItem = new Element("FileItem")
//										.addContent(new Element("FileName").setText(file_name))
//										.addContent(fileTypes);
//								relateFiles.addContent(fileItem);
//							}
//						}
//						
//						
//						else{
//							
//							// 將相同檔名的附檔名跟檔案下載路徑放入 
//							Element fileTypes = new Element("FileTypes");
//							Element fileTypeItem = new Element("FileTypeItem")
//									.addContent(new Element("FileExt").setText(name.substring(name.lastIndexOf(".") + 1)))
//									.addContent(new Element("FileUrl").setText(urlPath + "/download-file.jsp?id=" + dCipher.encrypt(String.valueOf(bean2.getId()))));
//							fileTypes.addContent(fileTypeItem);
//							
//							Element fileItem = new Element("FileItem")
//									.addContent(new Element("FileName").setText(file_name))
//									.addContent(fileTypes);
//							relateFiles.addContent(fileItem);
//						}
					}
				}
				
				
				item.addContent(relateFiles);
				// ------------------------------------------------------
				
				
				String rss_iamge = "";
				String rss_text =  "";
				
				if ("zh-tw".equals(lang)) {
					if ("0.".equals(bean.getImage_type_ch())) {
						rss_iamge = bean.getRss_image_ch();
					} else {
						rss_iamge = bean.getPhoto_ch();
						//沒上傳圖片用 預設圖片
						if (StringUtils.isEmpty(rss_iamge)) {
							rss_iamge = bean.getRss_image_ch();
						}
					}
					rss_text = bean.getRss_text_ch();
				} else {
					if ("0.".equals(bean.getImage_type_en())) {
						rss_iamge = bean.getRss_image();
					} else {
						rss_iamge = bean.getPhoto_en();
						
						//沒上傳圖片用 預設圖片
						if (StringUtils.isEmpty(rss_iamge)) {
							rss_iamge = bean.getRss_image();
						}
					}
					rss_text = bean.getRss_text();
				}
				
				
				//2019.12.12.dasin.因經濟部要求，英文新聞必須包含至少一個圖檔。
				Element relateImages = dTools.isEmpty(rss_iamge) ? new Element("RelateImages").setText("") :
					   new Element("RelateImages").addContent(new Element("ImageItem")
						.addContent(new Element("ImageName").setText("zh-tw".equals(lang) ? dTools.trim(rss_text) :
							AsciiUtil.sbc2dbcCase(dTools.trim(rss_text))))
						.addContent(new Element("ImageExt").setText(rss_iamge.substring(rss_iamge.lastIndexOf(".") + 1)))
						.addContent(new Element("ImageUrl").setText(request.getScheme() + "://" + request.getServerName() + rss_iamge))
					);
				
/*
				//2019.12.12.dasin.因經濟部要求，英文新聞必須包含至少一個圖檔。
				Element relateImages = dTools.isEmpty(bean.getRss_image()) ? new Element("RelateImages").setText("") :
					new Element("RelateImages").addContent(new Element("ImageItem")
						.addContent(new Element("ImageName").setText("zh-tw".equals(lang) ? dTools.trim(bean.getRss_text()) :
							AsciiUtil.sbc2dbcCase(dTools.trim(bean.getRss_text()))))
						.addContent(new Element("ImageExt").setText(bean.getRss_image().substring(bean.getRss_image().lastIndexOf(".") + 1)))
						.addContent(new Element("ImageUrl").setText(request.getScheme() + "://" + request.getServerName() + bean.getRss_image()))
					);*/
				
				item.addContent(relateImages);
				channel.addContent(item); 
			}
			
			
			/* ----------------------------------------------------------------------------------------------
			 * 子目錄：提供已被刪除的ID、刪除日期，供對方刪除
			 */
			ArrayList<DeletedPageNews> newsBeDeleted = PagesNewsDAO.newsBeDeleted();
			
			if(newsBeDeleted != null && !newsBeDeleted.isEmpty()){
				
				for(DeletedPageNews bean : newsBeDeleted){
					Element item = new Element("item")
							.addContent(new Element("title").setText("")) 
							.addContent(new Element("link").setText(""))
							.addContent(new Element("description").setText(""))
							.addContent(new Element("author").setText(""))
							.addContent(new Element("comments").setText(""))
							.addContent(new Element("pubDate").setText(getGMT(bean.getDelete_date()))) //
							.addContent(new Element("source").setText(""))
							
							.addContent(new Element("title" , dc).setText(""))
							.addContent(new Element("creator" , dc).setText(""))
							.addContent(new Element("subject" , dc).setText(""))
							.addContent(new Element("description" , dc).setText(""))
							.addContent(new Element("contributor" , dc).setText(""))
							.addContent(new Element("type" , dc).setText(""))
							.addContent(new Element("format" , dc).setText(""))
							.addContent(new Element("source" , dc).setText(""))
							.addContent(new Element("language" , dc).setText(""))
							.addContent(new Element("coverage" , dc).setText(""))
							.addContent(new Element("publisher" , dc).setText(""))
							.addContent(new Element("date" , dc).setText(""))
							.addContent(new Element("identifier" , dc).setText(""))
							.addContent(new Element("relation" , dc).setText(""))
							.addContent(new Element("rights" , dc).setText(""))
							.addContent(new Element("category.theme").setText(""))
							.addContent(new Element("category.cake").setText(""))
							.addContent(new Element("category.service").setText(""))
							.addContent(new Element("keywords").setText(""))
							.addContent(new Element("NewsID").setText("N"+lang+bean.getPagesNew_id())) //
							.addContent(new Element("IsDeleteNews").setText("Y")) //
							.addContent(new Element("IsSendEpaper").setText(""))
							.addContent(new Element("IsMoveToHistory").setText(""))
							.addContent(new Element("RelateContacts").setText(""))
							//2019.10.31.dasin : change RelateUrl to RelateLinks
							//.addContent(new Element("RelateUrl").setText(""))
							.addContent(new Element("RelateLinks").setText(""))
							;
					
					// 檔案 ---------------------------------------------------
					Element relateFiles = new Element("RelateFiles");
//					Element fileItem = new Element("FileItem")
//							.addContent(new Element("FileName").setText(""))
//							.addContent(new Element("FileTypes").
//									addContent(new Element("FileTypeItem")
//											.addContent(new Element("FileExt").setText(""))
//											.addContent(new Element("FileUrl").setText(""))
//											)
//									);
					
//					relateFiles.addContent(fileItem);
					item.addContent(relateFiles);
					// ------------------------------------------------------
					
					Element relateImages = new Element("RelateImages").setText("");
					item.addContent(relateImages);
					channel.addContent(item);
				}			
			}
			
			
			
			XMLOutputter outputter = new XMLOutputter(Format.getRawFormat());
			outputter.output(doc, out);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(out != null){
					out.flush();
					out.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public String getGMT(Date date){
		String result = "";
		if(date != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.HOUR_OF_DAY, -8);
			date = cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss" , Locale.ENGLISH);   //Wed, 14 Dec 2016 06:30:00 GMT
			result =  sdf.format(date)+" GMT"; 
		}
		return result;
	}
	public String getGMT(int year, int month, int date){ //自填日期
		String result = "";
		Calendar ca = Calendar.getInstance();
		ca.set(year, month, date);
		ca.add(Calendar.HOUR_OF_DAY, -8);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss" , Locale.ENGLISH);   //Wed, 14 Dec 2016 06:30:00 GMT

		result =  sdf.format(ca.getTime())+" GMT";
		return result;
	}	
}
