package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.BusinessPubFile;

public class BusinessPubFileDAO
{
	
	public static Map<String , ArrayList<BusinessPubFile>> get()
	{
		Map<String , ArrayList<BusinessPubFile>> map = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT id, businessPub_id, file_name, file_lang  FROM TB_BusinessPubFile");
			rs = pstmt.executeQuery();
			map = new HashMap<>();
			while(rs.next()){
				BusinessPubFile bean = setBusinessPubFile(rs, new BusinessPubFile());
				String businessPub_id = String.valueOf(bean.getBusinessPub_id());
				if(map.get(businessPub_id)==null){
					map.put(businessPub_id, new ArrayList<>());
				}
				map.get(businessPub_id).add(bean);
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
	
	public static Map<String , ArrayList<BusinessPubFile>> get(String lang)
	{
		Map<String , ArrayList<BusinessPubFile>> map = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare(
					  "SELECT id, businessPub_id, file_name, file_lang "
					+ "FROM TB_BusinessPubFile "
					+ "WHERE file_lang = ? ");
			pstmt.setString(1, lang);
			rs = pstmt.executeQuery();
			map = new HashMap<>();
			while(rs.next()){
				BusinessPubFile bean = setBusinessPubFile(rs, new BusinessPubFile());
				String businessPub_id = String.valueOf(bean.getBusinessPub_id());
				if(map.get(businessPub_id)==null){
					map.put(businessPub_id, new ArrayList<>());
				}
				map.get(businessPub_id).add(bean);
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
	
	public static BusinessPubFile get(int id)
	{
		BusinessPubFile result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_BusinessPubFile WHERE id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				setBusinessPubFile(rs, new BusinessPubFile());
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
	
	public static ArrayList<BusinessPubFile> getFileByBusinessPub(int businessPub_id)
	{
		ArrayList<BusinessPubFile> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT id, businessPub_id, file_name, file_lang FROM TB_BusinessPubFile "
								   + "WHERE businessPub_id = ? "
								   + "ORDER BY file_lang ");
			pstmt.setInt(1, businessPub_id);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				result.add(setBusinessPubFile(rs, new BusinessPubFile()));
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
	
	
	public static boolean insert(ArrayList<BusinessPubFile> beans)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			sqltools.noCommit();
			pstmt = sqltools.prepare("INSERT INTO TB_BusinessPubFile VALUES(?,?,?,?)");
			for(BusinessPubFile bean : beans){
				int column = 1;
				pstmt.setInt(column++, bean.getBusinessPub_id());
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

	public static boolean update(BusinessPubFile bean)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("UPDATE TB_BusinessPubFile SET "
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
			pstmt = sqltools.prepare("DELETE FROM TB_BusinessPubFile WHERE id = ?");
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
	
	public static boolean delete_byBsId(int businessPub_id)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = sqltools.prepare("DELETE FROM TB_BusinessPubFile WHERE businessPub_id = ?");
			pstmt.setInt(1, businessPub_id);
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
	
	
	private static BusinessPubFile setBusinessPubFile(ResultSet rs, BusinessPubFile bean) throws SQLException
	{
		bean.setId(rs.getInt("id"));
		bean.setBusinessPub_id(rs.getInt("businessPub_id"));
		bean.setFile_name(rs.getString("file_name"));
		bean.setFile_lang(rs.getString("file_lang"));
		return bean;
	}


}
