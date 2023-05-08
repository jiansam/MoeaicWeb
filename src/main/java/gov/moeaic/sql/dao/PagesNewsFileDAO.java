package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.formula.functions.Count;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.PagesNewsFile;

public class PagesNewsFileDAO
{
	
	public static Map<Integer , ArrayList<PagesNewsFile>> get()
	{
		Map<Integer , ArrayList<PagesNewsFile>> map = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT id,pagesNews_id,file_name,file_lang FROM TB_PagesNewsFile");
			rs = pstmt.executeQuery();
			map = new HashMap<>();
			while(rs.next()){
				PagesNewsFile bean = setPagesNewsFile(rs, new PagesNewsFile());
				int news_id = bean.getPagesNews_id();
				if(map.get(news_id)==null){
					map.put(news_id, new ArrayList<>());
				}
				map.get(news_id).add(bean);
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
			if(pstmt != null){
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
		return map;
	}
	
	public static Map<Integer , ArrayList<PagesNewsFile>> get(String lang)
	{
		Map<Integer , ArrayList<PagesNewsFile>> map = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT id,pagesNews_id,file_name,file_lang FROM TB_PagesNewsFile "
									+ "WHERE file_lang = ?");
			int column = 1;
			pstmt.setString(column++, lang);
			rs = pstmt.executeQuery();
			map = new HashMap<>();
			while(rs.next()){
				PagesNewsFile bean = setPagesNewsFile(rs, new PagesNewsFile());
				int news_id = bean.getPagesNews_id();
				if(map.get(news_id)==null){
					map.put(news_id, new ArrayList<>());
				}
				map.get(news_id).add(bean);
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
			if(pstmt != null){
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
		return map;
	}
	
	public static PagesNewsFile get(int id)
	{
		PagesNewsFile result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_PagesNewsFile WHERE id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				setPagesNewsFile(rs, new PagesNewsFile());
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
			if(pstmt != null){
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
	
	public static ArrayList<PagesNewsFile> getByPagesNewsFile(int pagesNews_id)
	{
		ArrayList<PagesNewsFile> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT id, pagesNews_id, file_name, file_lang FROM TB_PagesNewsFile "
								   + "WHERE pagesNews_id = ? "
								   + "ORDER BY file_lang ");
			pstmt.setInt(1, pagesNews_id);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				result.add(setPagesNewsFile(rs, new PagesNewsFile()));
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
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}	
	
	
	public static boolean insert(ArrayList<PagesNewsFile> beans)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			sqltools.noCommit();
			pstmt = sqltools.prepare("INSERT INTO TB_PagesNewsFile VALUES(?,?,?,?)");
			for(PagesNewsFile bean : beans){
				int column = 1;
				pstmt.setInt(column++, bean.getPagesNews_id());
				pstmt.setString(column++, bean.getFile_name());
				pstmt.setBytes(column++, bean.getFile_content());
				pstmt.setString(column++, bean.getFile_lang());
				pstmt.executeUpdate();
			}
			sqltools.commit();
			result = true;
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
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public static boolean update(PagesNewsFile bean)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("UPDATE TB_PagesNewsFile SET "
								   + "file_name = ?, "
								   + "file_content = ?, "
								   + "file_lang = ? "
								   + "WHERE id = ? ");
			int column = 1;
			pstmt.setString(column++, bean.getFile_name());
			pstmt.setBytes(column++, bean.getFile_content());
			pstmt.setString(column++, bean.getFile_lang());
			pstmt.setInt(column++, bean.getId());
			int x = pstmt.executeUpdate();
			if(x != 0){
				result = true;
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
			pstmt = sqltools.prepare("DELETE FROM TB_PagesNewsFile WHERE id = ?");
			pstmt.setInt(1, id);
			int x = pstmt.executeUpdate();
			if(x != 0){
				result = true;
			}
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
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}	
	
	public static boolean delete_byPsId(int pagesNews_id)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = sqltools.prepare("DELETE FROM TB_PagesNewsFile WHERE pagesNews_id = ?");
			pstmt.setInt(1, pagesNews_id);
			int x = pstmt.executeUpdate();
			if(x != 0){
				result = true;
			}
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
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	private static PagesNewsFile setPagesNewsFile(ResultSet rs, PagesNewsFile bean) throws SQLException
	{
		bean.setId(rs.getInt("id"));
		bean.setPagesNews_id(rs.getInt("pagesNews_id"));
		bean.setFile_name(rs.getString("file_name"));
		bean.setFile_lang(rs.getString("file_lang"));
		return bean;
	}


}
