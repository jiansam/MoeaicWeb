package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.dasin.tools.dWebTools;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.WebTitle;

public class WebTitleDAO
{
	public static WebTitle get(int id)
	{
		WebTitle result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_WebTitle WHERE id = ?");
			int column = 1;
			pstmt.setInt(column++, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = setWebTitle(rs, new WebTitle());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
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

	public static ArrayList<WebTitle> get()
	{
		ArrayList<WebTitle> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_WebTitle ORDER BY convert(int, sort)");
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while (rs.next()) {
				result.add(setWebTitle(rs, new WebTitle()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (pstmt != null) {
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

	public static boolean insert(WebTitle bean)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("INSERT INTO TB_WebTitle VALUES(?,?,?)");
			int column = 1;
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getCh_title()));
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getEn_title()));			
			pstmt.setInt(column++, bean.getSort());
			int x = pstmt.executeUpdate();
			if(x!=0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
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

	public static boolean update(WebTitle bean)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("UPDATE TB_WebTitle SET "
								   + "ch_title = ?, "
								   + "en_title = ?, "
								   + "sort = ? "
								   + "WHERE id = ?");
			int column = 1;
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getCh_title()));
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getEn_title()));	
			pstmt.setInt(column++, bean.getSort());
			pstmt.setInt(column++, bean.getId());
			int x = pstmt.executeUpdate();
			if(x!=0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
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

	public static boolean delete(int id)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("DELETE FROM TB_WebTitle where id=?");
			pstmt.setInt(1, id);
			int x = pstmt.executeUpdate();
			if(x!=0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
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
	
	
	public static boolean checkNeedRemove()
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare(
					  "DELETE FROM TB_WebName "
					+ "WHERE id NOT IN "
					+ "(SELECT webName_id FROM TB_WebOrder)");
			int x = pstmt.executeUpdate();
			if(x!=0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
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
	

	private static WebTitle setWebTitle(ResultSet rs, WebTitle bean) throws SQLException
	{
		bean.setId(rs.getInt("id"));
		bean.setCh_title(rs.getString("ch_title"));
		bean.setEn_title(rs.getString("en_title"));
		bean.setSort(rs.getInt("sort"));
		return bean;
	}
}
