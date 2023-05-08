package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.OpenDataFile;

public class OpenDataFileDAO
{
	
	public static Map<String , ArrayList<OpenDataFile>> get()
	{
		Map<String , ArrayList<OpenDataFile>> map = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT id, openData_id, file_name, file_lang  FROM TB_OpenDataFile");
			rs = pstmt.executeQuery();
			map = new HashMap<>();
			while(rs.next()){
				OpenDataFile bean = setOpenDataFile(rs, new OpenDataFile());
				String openData_id = String.valueOf(bean.getOpenData_id());
				if(map.get(openData_id)==null){
					map.put(openData_id, new ArrayList<>());
				}
				map.get(openData_id).add(bean);
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
	
	public static OpenDataFile get(int id)
	{
		OpenDataFile result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_OpenDataFile WHERE id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				setOpenDataFile(rs, new OpenDataFile());
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
	
	public static ArrayList<OpenDataFile> getFileByOpenData(int openData_id)
	{
		ArrayList<OpenDataFile> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT id, openData_id, file_name, file_lang FROM TB_OpenDataFile "
								   + "WHERE openData_id = ? "
								   + "ORDER BY file_lang ");
			pstmt.setInt(1, openData_id);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				result.add(setOpenDataFile(rs, new OpenDataFile()));
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
	
	
	public static boolean insert(ArrayList<OpenDataFile> beans)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			sqltools.noCommit();
			pstmt = sqltools.prepare("INSERT INTO TB_OpenDataFile VALUES(?,?,?,?)");
			for(OpenDataFile bean : beans){
				int column = 1;
				pstmt.setInt(column++, bean.getOpenData_id());
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

	public static boolean update(OpenDataFile bean)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("UPDATE TB_OpenDataFile SET "
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
			pstmt = sqltools.prepare("DELETE FROM TB_OpenDataFile WHERE id = ?");
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
	
	public static boolean delete_byODId(int openData_id)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = sqltools.prepare("DELETE FROM TB_OpenDataFile WHERE openData_id = ?");
			pstmt.setInt(1, openData_id);
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
	
	
	private static OpenDataFile setOpenDataFile(ResultSet rs, OpenDataFile bean) throws SQLException
	{
		bean.setId(rs.getInt("id"));
		bean.setOpenData_id(rs.getInt("openData_id"));
		bean.setFile_name(rs.getString("file_name"));
		bean.setFile_lang(rs.getString("file_lang"));
		return bean;
	}


}
