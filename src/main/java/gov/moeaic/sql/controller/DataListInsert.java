package gov.moeaic.sql.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import gov.moeaic.sql.bean.EW_NEWS_ATTACHMENT;
import gov.moeaic.sql.bean.EW_NEWS_ITEMS;
import gov.moeaic.sql.bean.PagesNews;
import gov.moeaic.sql.bean.PagesNewsFile;

public class DataListInsert
{
	private static final String URL ="jdbc:sqlserver://172.31.252.151:1433;DatabaseName=WEBDB";
	private static final String URL2 ="jdbc:sqlserver://172.31.252.151:1433;DatabaseName=MoeaicWeb";
	private static final String USERNAME ="moeaicsa";
	private static final String PASSWORD ="cier.27356006";
	
	
	
	public static void main(String[] args){
//		String file_fir = "Z:/投審會網站資料/web_files/files";
		
		DataListInsert myclass = new DataListInsert();
		
		// news_Ann
		ArrayList<EW_NEWS_ITEMS> news = myclass.getList_newsAnn();
		Map<Integer, ArrayList<EW_NEWS_ATTACHMENT>> news_files = myclass.getFile_map(myclass.getFile_newsAnn());
		myclass.insert_news(news, news_files, "new_ann", "Z:/投審會網站資料/web_files/files/news/");
		
		// 5 = business_ann = 業務統計
		ArrayList<EW_NEWS_ITEMS> news2 = myclass.getList_other(5);
		Map<Integer, ArrayList<EW_NEWS_ATTACHMENT>> news_files2 = myclass.getFile_map(myclass.getFile_other());
		myclass.insert_news(news2, news_files2, "business_ann", "Z:/投審會網站資料/web_files/files/pubs/");
		
		// 6 = studyReport = 研究報告  
		ArrayList<EW_NEWS_ITEMS> news3 = myclass.getList_other(6);
		Map<Integer, ArrayList<EW_NEWS_ATTACHMENT>> news_files3 = myclass.getFile_map(myclass.getFile_other());
		myclass.insert_news(news3, news_files3, "studyReport", "Z:/投審會網站資料/web_files/files/pubs/");
	}
	
	public void insert_news(ArrayList<EW_NEWS_ITEMS> news , Map<Integer, ArrayList<EW_NEWS_ATTACHMENT>> news_files,
			String type_name, String file_fir){
		if(news!=null && !news.isEmpty()){
			ArrayList<String> outString = new ArrayList<>();
			int count_all = 0;
			int count_del = 0;
			int count_insert = 0;
			int file_all = 0;
			int file_notExist = 0;
			int file_insert = 0;
			for(EW_NEWS_ITEMS temp : news){
				++ count_all;
				//舊資料的 news_id_old
				int news_id_old = temp.getID();
				
				if(temp.isDELETED()){
					++ count_del;
//					System.out.println( "ID="+ news_id_old + ", 此筆資料被刪除？ "+temp.isDELETED());
					outString.add("ID="+ news_id_old + ", 此筆資料被刪除？ "+temp.isDELETED());
//					writeOutText("ID="+ news_id_old + ", 此筆資料被刪除？ "+temp.isDELETED());
					continue;
				}
				
				/* ---------------------------------------------
				 * 先取出一筆 news ，insert後取出新的id
				 */
				PagesNews bean = new PagesNews();
				if(temp.getLANG() == 0){
					bean.setCh_title(temp.getSUBJECT());
					bean.setCh_content(temp.getCONTEXT());
					bean.setEn_title("");
					bean.setEn_content("");
					
				}else{
					bean.setCh_title("");
					bean.setCh_content("");
					bean.setEn_title(temp.getSUBJECT());
					bean.setEn_content(temp.getCONTEXT());
				}
				bean.setPublish_date(temp.getSTARTED_AT());
				bean.setShowTop(false);
				bean.setSort(99);
				bean.setType(type_name);
				int news_id = DataListInsert.insert_news(bean);
				++ count_insert;
				
				/* ---------------------------------------------------------
				 *  取出files
				 */
				ArrayList<EW_NEWS_ATTACHMENT> newsfiles = news_files.get(temp.getID());
				ArrayList<PagesNewsFile> pagesNewsFiles = null;
				if(newsfiles!=null && !newsfiles.isEmpty()){
					pagesNewsFiles = new ArrayList<>();

					for(EW_NEWS_ATTACHMENT temp1 : newsfiles){
						++ file_all;
						String file_name = temp1.getFILENAME(); 
						String file_path = file_fir + news_id_old + "/" + file_name ;
						File file = new File(file_path);
						if(!file.exists()){
//							System.out.println("檔案不存在, 路徑=" + file_path);
							outString.add("檔案不存在, 路徑=" + file_path);
//							writeOutText("檔案不存在, 路徑=" + file_path);
							++ file_notExist;
							continue;
						}
						
						try {
							FileInputStream fio = new FileInputStream(file);
							byte[] file_content = IOUtils.toByteArray(fio);
							
							PagesNewsFile pnf = new PagesNewsFile();
							pnf.setPagesNews_id(news_id);
							pnf.setFile_name(file_name);
							pnf.setFile_content(file_content);
							pnf.setFile_lang(temp.getLANG() == 0 ? "ch" : "en");
							pagesNewsFiles.add(pnf);
							++ file_insert;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					DataListInsert.insert_file(pagesNewsFiles);
				}
			
			}
//			System.out.println(type_name + "檔案總數="+count_all+", insert="+count_insert +", delete="+count_del);
			outString.add(type_name + "檔案總數="+count_all+", insert="+count_insert +", delete="+count_del);
//			writeOutText(type_name + "=" +count_all+", insert="+count_insert +", delete="+count_del);
			
//			System.out.println(type_name + "檔案總數="+file_all+", file_insert="+file_insert +", file_notExist="+file_notExist);
			outString.add(type_name + "檔案總數="+file_all+", file_insert="+file_insert +", file_notExist="+file_notExist);
			
			writeOutText(outString);
		}
	}
	
	private void writeOutText(ArrayList<String> outString)
	{
		BufferedWriter bw = null;
		try {
			File f = new File("C:/Users/IBT-004/Desktop/backup_log.txt");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f,true), "UTF-8"));
			for(String out : outString){
				bw.append(out);
				bw.newLine();
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(bw!=null){
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	ArrayList<EW_NEWS_ITEMS> getList_newsAnn()
	{
		ArrayList<EW_NEWS_ITEMS> result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			pstmt = conn.prepareStatement("SELECT * FROM EW_NEWS_ITEMS");
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				EW_NEWS_ITEMS bean = new EW_NEWS_ITEMS();
				bean.setID(rs.getInt("ID"));
				bean.setLANG(rs.getInt("LANG"));
				bean.setSUBJECT(rs.getString("SUBJECT"));
				bean.setCONTEXT( (rs.getString("CONTEXT")).replace("\r", "").replace("\n", "<br>") );
				bean.setSTARTED_AT(rs.getDate("STARTED_AT"));
				bean.setDELETED(rs.getBoolean("DELETED"));
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	ArrayList<EW_NEWS_ATTACHMENT> getFile_newsAnn()
	{
		ArrayList<EW_NEWS_ATTACHMENT> result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			pstmt = conn.prepareStatement("SELECT * FROM EW_NEWS_ATTACHMENT");
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while (rs.next()){
				EW_NEWS_ATTACHMENT bean = new EW_NEWS_ATTACHMENT();
				bean.setID(rs.getInt("ID"));
				bean.setNEWS_ID(rs.getInt("NEWS_ID"));
				bean.setFILENAME(rs.getString("FILENAME"));
				bean.setCREATED_AT(rs.getDate("CREATED_AT"));
				bean.setDELETED(rs.getBoolean("DELETED"));
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
		}
		return result;
	}
	
	public Map<Integer, ArrayList<EW_NEWS_ATTACHMENT>> getFile_map(ArrayList<EW_NEWS_ATTACHMENT> files)
	{
		Map<Integer, ArrayList<EW_NEWS_ATTACHMENT>> result = null;
		if(files!=null && !files.isEmpty()){
			result = new HashMap<>();
			for(EW_NEWS_ATTACHMENT bean : files){
				int news_id = bean.getNEWS_ID();
				if(result.get(news_id)==null){
					result.put(news_id, new ArrayList<>());
				}
				result.get(news_id).add(bean);
			}
		}
		return result;
	}
	
	
	ArrayList<EW_NEWS_ITEMS> getList_other(int CATEGORY_ID)
	{
		ArrayList<EW_NEWS_ITEMS> result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			pstmt = conn.prepareStatement("SELECT * FROM EW_PUBLICATION_ITEMS WHERE CATEGORY_ID = ?");
			pstmt.setInt(1, CATEGORY_ID);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				EW_NEWS_ITEMS bean = new EW_NEWS_ITEMS();
				bean.setID(rs.getInt("ID"));
				bean.setLANG(0); //看過檔案都是中文資料
				bean.setSUBJECT(rs.getString("SUBJECT"));
				bean.setCONTEXT(rs.getString("DESCRIPTION"));
				bean.setSTARTED_AT(rs.getDate("CREATED_AT"));
				bean.setDELETED(rs.getBoolean("DELETED"));
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	ArrayList<EW_NEWS_ATTACHMENT> getFile_other()
	{
		ArrayList<EW_NEWS_ATTACHMENT> result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			pstmt = conn.prepareStatement("SELECT * FROM EW_PUBLICATION_FILE");
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while (rs.next()){
				EW_NEWS_ATTACHMENT bean = new EW_NEWS_ATTACHMENT();
				bean.setID(rs.getInt("ID"));
				bean.setNEWS_ID(rs.getInt("PUBLICATION_ID"));
				bean.setFILENAME(rs.getString("FILENAME"));
				bean.setCREATED_AT(rs.getDate("CREATED_AT"));
				bean.setDELETED(rs.getBoolean("DELETED"));
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
		}
		return result;
	}
	
	
	public static int insert_news(PagesNews bean)
	{
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DriverManager.getConnection(URL2,USERNAME,PASSWORD);
			pstmt = conn.prepareStatement("INSERT INTO TB_PagesNews VALUES(?,?,?,?,?,?,?,?)", new String[]{"id"});
			int column = 1;
			pstmt.setString(column++, bean.getCh_title());
			pstmt.setString(column++, bean.getCh_content());
			pstmt.setString(column++, bean.getEn_title());
			pstmt.setString(column++, bean.getEn_content());
			pstmt.setTimestamp(column++, ToolsUtil.dateToTimestamp(bean.getPublish_date()));
			pstmt.setBoolean(column++, bean.isShowTop());
			pstmt.setInt(column++, bean.getSort());
			pstmt.setString(column++, bean.getType());
			int x = pstmt.executeUpdate();
			if(x != 0){
				rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					result = rs.getInt(1);
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}

	public static boolean insert_file(ArrayList<PagesNewsFile> beans)
	{
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DriverManager.getConnection(URL2,USERNAME,PASSWORD);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement("INSERT INTO TB_PagesNewsFile VALUES(?,?,?,?)");
			for(PagesNewsFile bean : beans){
				int column = 1;
				pstmt.setInt(column++, bean.getPagesNews_id());
				pstmt.setString(column++, bean.getFile_name());
				pstmt.setBytes(column++, bean.getFile_content());
				pstmt.setString(column++, bean.getFile_lang());
				pstmt.executeUpdate();
			}
			conn.commit();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
}
