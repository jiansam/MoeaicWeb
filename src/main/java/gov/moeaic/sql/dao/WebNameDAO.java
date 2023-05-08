package gov.moeaic.sql.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.dasin.tools.dWebTools;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.WebName;

public class WebNameDAO
{
	public static WebName get(int id)
	{
		WebName result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT id, name, lang, web_titles, url, showIndex, update_date, photoFile, "
								   + "file_name, file_content "
								   + "FROM TB_WebName WHERE id = ?");
			int column = 1;
			pstmt.setInt(column++, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = setWebName(rs, new WebName());
				result.setFile_name(rs.getString("file_name"));
				result.setFile_content(rs.getBytes("file_content"));
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
	
	public static ArrayList<WebName> getIndexURL(String lang)
	{
		ArrayList<WebName> result = new ArrayList<>();
		ArrayList<Integer> id_list = new ArrayList<Integer>();
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = sqltools.prepare(
				"SELECT A.id, A.name, A.url, A.photoFile FROM TB_WebName A "
				+ "LEFT OUTER JOIN TB_WebOrder B ON A.id = B.webName_id "
				+ "LEFT OUTER JOIN TB_WebTitle C on B.parent_id = C.id "
				+ "WHERE A.lang = ? AND A.showIndex = 1 "
				+ "AND A.id IN (SELECT webName_id FROM TB_WebOrder) "
				+ "ORDER BY C.sort, B.sort, A.id "
			);
			
			int column = 1;
			pstmt.setString(column++, lang);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				if(!id_list.contains(id)){
					id_list.add(id);
				}else{
					continue;
				}
				
				WebName bean = new WebName();
				bean.setName(rs.getString("name"));
				bean.setUrl(rs.getString("url"));
				bean.setPhotoFile(rs.getString("photoFile"));
				result.add(bean);
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

	public static ArrayList<WebName> get()
	{
		ArrayList<WebName> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT id, name, lang, web_titles, url, showIndex, update_date, photoFile "
								   + "FROM TB_WebName");
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while (rs.next()) {
				result.add(setWebName(rs, new WebName()));
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

	public static int insert(WebName bean)
	{
		int result = 0;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare(
						  "INSERT INTO TB_WebName "
						+ "(name, lang, web_titles, url, showIndex, update_date, photoFile, file_name, file_content)"
						+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",  new String[]{"id"} );
			int column = 1;
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getName()));
			pstmt.setString(column++, bean.getLang());
			pstmt.setString(column++, bean.getWeb_titles());
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getUrl()));
			pstmt.setBoolean(column++, bean.isShowIndex());
			pstmt.setDate(column++, new Date(bean.getUpdate_date().getTime()));
			pstmt.setString(column++, bean.getPhotoFile());
			pstmt.setString(column++, bean.getFile_name());
			pstmt.setBytes(column++, bean.getFile_content());
			int x = pstmt.executeUpdate();
			if(x!=0){
				rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					result = rs.getInt(1);
				}
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

	public static boolean update(WebName bean)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("UPDATE TB_WebName SET "
								   + "name = ?, "
								   + "lang = ?, "
								   + "web_titles = ?, "
								   + "url = ?, "
								   + "showIndex = ?, "
								   + "update_date = ?, "
								   + "photoFile = ?, "
								   + "file_name = ?, "
								   + "file_content = ? "
								   + "WHERE id = ?");
			int column = 1;
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getName()));
			pstmt.setString(column++, bean.getLang());
			pstmt.setString(column++, bean.getWeb_titles());
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getUrl()));
			pstmt.setBoolean(column++, bean.isShowIndex());
			pstmt.setDate(column++, new Date(bean.getUpdate_date().getTime()));
			pstmt.setString(column++, bean.getPhotoFile());
			pstmt.setString(column++, bean.getFile_name());
			pstmt.setBytes(column++, bean.getFile_content());
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
	
	public static boolean update_Titles(int id, String web_titles)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("UPDATE TB_WebName SET "
								   + "web_titles = ? "
								   + "WHERE id = ?");
			int column = 1;
			pstmt.setString(column++, web_titles);
			pstmt.setInt(column++, id);
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
			pstmt = sqltools.prepare("DELETE FROM TB_WebName where id=?");
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

	private static WebName setWebName(ResultSet rs, WebName bean) throws SQLException
	{
		bean.setId(rs.getInt("id"));
		bean.setName(rs.getString("name"));
		bean.setLang(rs.getString("lang"));
		bean.setWeb_titles(rs.getString("web_titles"));
		bean.setUrl(rs.getString("url"));
		bean.setShowIndex(rs.getBoolean("showIndex"));
		bean.setUpdate_date(rs.getDate("update_date"));
		bean.setPhotoFile(rs.getString("photoFile"));
		return bean;
	}
}
