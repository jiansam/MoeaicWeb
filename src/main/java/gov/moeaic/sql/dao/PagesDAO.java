package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.Pages;

public class PagesDAO {
	
	public static ArrayList<Pages> list() {
		ArrayList<Pages> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_Pages ");
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while (rs.next()) {
				result.add(setPages(rs, new Pages()));
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
	
	public static Pages get(int id) {
		Pages result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_Pages WHERE id = ? ");
			int column = 1;
			pstmt.setInt(column++, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = setPages(rs, new Pages());
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
	
	
	public static ArrayList<Pages> get(String type) {
		ArrayList<Pages> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_Pages WHERE type = ? ");
			int column = 1;
			pstmt.setString(column++, type);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while (rs.next()) {
				result.add(setPages(rs, new Pages()));
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
	
	public static Pages get(String type, String language) {
		Pages result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_Pages WHERE type = ? AND language = ?");
			int column = 1;
			pstmt.setString(column++, type);
			pstmt.setString(column++, language);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = setPages(rs, new Pages());
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
	
	public static Pages insert(Pages bean) {
		Pages result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("INSERT INTO TB_Pages(type, page_content, language, update_date) "
										+ "VALUES ( ?,?,?,GETDATE() )", new String[]{"id"});
			int column = 1;
			pstmt.setString(column++, bean.getType());
			pstmt.setString(column++, bean.getPage_content());
			pstmt.setString(column++, bean.getLanguage());
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			int id = 0;
			if(rs.next()){
				id = rs.getInt(1);
			}
			pstmt.close();
			rs.close();
			
//			if(id != 0){
//				pstmt = sqltools.prepare("SELECT * FROM TB_Pages WHERE id = ?");
//				pstmt.setInt(1, id);
//				rs = pstmt.executeQuery();
//				if (rs.next()) {
//					result = new Pages();
//					result.setId(id);
//					result.setType(rs.getString("type"));
//					result.setPage_content(rs.getString("page_content"));
//					result.setLanguage(rs.getString("language"));
//					result.setUpdate_date(rs.getTimestamp("update_date"));
//				}
//			}
			
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
	
	public static Pages update(Pages bean) {
		Pages result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("UPDATE TB_Pages SET "
								   + "page_content = ?, "
								   + "update_date = GETDATE() "
								   + "WHERE id = ?");
			int column = 1;
			pstmt.setString(column++, bean.getPage_content());
			pstmt.setInt(column++, bean.getId());
			int x = pstmt.executeUpdate();
			pstmt.close();
			
			if (x!=0) {
				pstmt = sqltools.prepare("SELECT * FROM TB_Pages WHERE id = ?");
				pstmt.setInt(1, bean.getId());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					bean.setUpdate_date(rs.getTimestamp("update_date"));
					result = bean;
				}
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
	

	private static Pages setPages(ResultSet rs, Pages bean) throws SQLException {
		bean.setId(rs.getInt("id"));
		bean.setType(rs.getString("type"));
		bean.setPage_content(rs.getString("page_content"));
		bean.setLanguage(rs.getString("language"));
		bean.setUpdate_date(rs.getTimestamp("update_date"));
		return bean;
	}
}
