package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.WebOrder;

public class WebOrderDAO
{
	public static ArrayList<WebOrder> get()
	{
		ArrayList<WebOrder> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare(
					"SELECT * FROM VW_WebNameXOrder "
				  + "ORDER BY lang, title_sort, sort, showIndex desc, name");
//					"SELECT A.*, B.*, A.id AS order_id, A.lang AS order_lang, B.id AS name_id, "
//						 + "C.id AS title_id, C.ch_title, C.en_title, C.sort AS title_sort "
//						 + "FROM TB_WebOrder A "
//						 + "LEFT OUTER JOIN TB_WebName B ON A.webName_id = B.id "
//						 + "LEFT OUTER JOIN TB_WebTitle C ON A.parent_id = C.id "
//						 + "ORDER BY A.lang, title_sort, convert(int, A.sort), B.showIndex desc, B.name");
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				WebOrder bean = new WebOrder();
				bean.setId(rs.getInt("order_id"));
				bean.setWebName_id(rs.getInt("webName_id"));
				bean.setParent_id(rs.getInt("parent_id"));
				bean.setSort(rs.getInt("sort"));
				bean.setLang(rs.getString("order_lang"));
				
				bean.setName(rs.getString("name"));
				bean.setWeb_titles(rs.getString("web_titles"));
				bean.setUrl(rs.getString("url"));
				bean.setShowIndex(rs.getBoolean("showIndex"));
				bean.setUpdate_date(rs.getDate("update_date"));
				bean.setPhotoFile(rs.getString("photoFile"));
				
				bean.setCh_title(rs.getString("ch_title"));
				bean.setEn_title(rs.getString("en_title"));
				bean.setParent_sort(rs.getString("title_sort"));
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
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static ArrayList<WebOrder> get(int parent_id)
	{
		ArrayList<WebOrder> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_WebOrder WHERE parent_id = ?");
			pstmt.setInt(1, parent_id);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				WebOrder bean = new WebOrder();
				bean.setId(rs.getInt("id"));
				bean.setWebName_id(rs.getInt("webName_id"));
				bean.setParent_id(rs.getInt("parent_id"));
				bean.setSort(rs.getInt("sort"));
				bean.setLang(rs.getString("lang"));
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
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static ArrayList<WebOrder> getByWebName(int webName_id)
	{
		ArrayList<WebOrder> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_WebOrder WHERE webName_id = ?");
			pstmt.setInt(1, webName_id);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				WebOrder bean = new WebOrder();
				bean.setId(rs.getInt("id"));
				bean.setWebName_id(rs.getInt("webName_id"));
				bean.setParent_id(rs.getInt("parent_id"));
				bean.setSort(rs.getInt("sort"));
				bean.setLang(rs.getString("lang"));
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
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static void update(int webName_id, ArrayList<WebOrder> list){
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			sqltools.noCommit();
			pstmt = sqltools.prepare("DELETE FROM TB_WebOrder where webName_id = ?");
			pstmt.setInt(1, webName_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = sqltools.prepare("INSERT INTO TB_WebOrder VALUES(?,?,?,?)");
			for(WebOrder bean : list){
				int column = 1;
				pstmt.setInt(column++, bean.getWebName_id());
				pstmt.setInt(column++, bean.getParent_id());
				pstmt.setInt(column++, bean.getSort());
				pstmt.setString(column++, bean.getLang());
				pstmt.executeUpdate();
			}
			sqltools.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void update_sort(int id, String sort){
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("UPDATE TB_WebOrder SET sort = ? WHERE id = ?");
			int column = 1;
			pstmt.setString(column++, sort);
			pstmt.setInt(column++, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
	
	public static void insert(int webName_id, ArrayList<WebOrder> list){
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			sqltools.noCommit();
			
			pstmt = sqltools.prepare("INSERT INTO TB_WebOrder VALUES(?,?,?,?)");
			for(WebOrder bean : list){
				int column = 1;
				pstmt.setInt(column++, webName_id);
				pstmt.setInt(column++, bean.getParent_id());
				pstmt.setInt(column++, bean.getSort());
				pstmt.setString(column++, bean.getLang());
				pstmt.executeUpdate();
			}
			sqltools.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void delete(int id)
	{
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("DELETE FROM TB_WebOrder where id = ?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void deleteByWebName(int webName_id)
	{
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("DELETE FROM TB_WebOrder where webName_id = ?");
			pstmt.setInt(1, webName_id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void deleteByWebTitle(int parent_id)
	{
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("DELETE FROM TB_WebOrder where parent_id = ?");
			pstmt.setInt(1, parent_id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
}
