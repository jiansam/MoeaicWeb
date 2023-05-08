package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.dasin.tools.dWebTools;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.BusinessPub;
import gov.moeaic.sql.controller.ToolsUtil;

public class BusinessPubDAO
{
	
	public static BusinessPub get(int id)
	{
		BusinessPub result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare(
				"SELECT * FROM TB_BusinessPub WHERE id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = setBusinessPub(rs, new BusinessPub());
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
	
	public static ArrayList<BusinessPub> get(String type)
	{
		ArrayList<BusinessPub> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_BusinessPub WHERE type = ?");
			pstmt.setString(1, type);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			if(rs.next()){
				result.add(setBusinessPub(rs, new BusinessPub()));
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
	
	public static BusinessPub get(int id, String lang)
	{
		BusinessPub result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			if("en".equalsIgnoreCase(lang)){
				sql = "SELECT * FROM TB_BusinessPub WHERE id = ? AND LEN(en_title) > 0";
			}else{
				sql = "SELECT * FROM TB_BusinessPub WHERE id = ? AND LEN(ch_title) > 0";
			}
			pstmt = sqltools.prepare(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = setBusinessPub(rs, new BusinessPub());
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
	
	
	public static String getFrontBusPub_QAType_used(String lang, String op_id_one)
	{
		String result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			if("ch".equalsIgnoreCase(lang)){
				sql = "SELECT STUFF( "
					+ "("
					+ 	"SELECT  Distinct ',' + ch_QA_type "
					+ 	"FROM TB_BusinessPub "
					+ 	"WHERE ch_QA_type IS NOT NULL "
					+ 	"AND LEN(ch_QA_type) > 0 "
					+ 	"AND op_ids like ? "
					+ 	"FOR XML PATH('') "
					+ ") "
					+ ", 1, 1, '' )  AS QA_type";
			}else{
				sql = "SELECT STUFF( "
						+ "("
						+ 	"SELECT  Distinct ',' + en_QA_type "
						+ 	"FROM TB_BusinessPub "
						+ 	"WHERE en_QA_type IS NOT NULL "
						+ 	"AND LEN(en_QA_type) > 0 "
						+ 	"AND op_ids like ? "
						+ 	"FOR XML PATH('') "
						+ ") "
						+ ", 1, 1, '' )  AS QA_type";
			}
			pstmt = sqltools.prepare(sql);
			int column = 1;
			pstmt.setString(column++, "%"+op_id_one+"%");
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getString("QA_type");
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
	
	
	public static int insert(BusinessPub bean)
	{
		int result = 0;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare(
					"INSERT INTO TB_BusinessPub"
				  + "(ch_title, ch_content, en_title, en_content, publish_date, type, op_ids, create_date, ch_QA_type, en_QA_type) "
				  + "VALUES(?,?,?,?,?,?,?,GETDATE(),?,?)", new String[]{"id"});
			int column = 1;
			
			
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getCh_title()));
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getCh_content()));
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getEn_title()));
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getEn_content()));			
			pstmt.setTimestamp(column++, ToolsUtil.dateToTimestamp(bean.getPublish_date()));
			pstmt.setString(column++, bean.getType());
			pstmt.setString(column++, bean.getOp_ids());
			pstmt.setString(column++, bean.getCh_QA_type());
			pstmt.setString(column++, bean.getEn_QA_type());
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
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public static boolean update_ch(BusinessPub bean)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("UPDATE TB_BusinessPub SET "
								   + "type = ?, "
								   + "ch_title = ?, "
								   + "ch_content = ?, "
								   + "publish_date = ?, "
								   + "op_ids = ?, "
								   + "ch_QA_type = ? "
								   + "WHERE id = ? ");
			int column = 1;
			pstmt.setString(column++, bean.getType());
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getCh_title()));
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getCh_content()));
			pstmt.setTimestamp(column++, ToolsUtil.dateToTimestamp(bean.getPublish_date()));
			pstmt.setString(column++, bean.getOp_ids());
			pstmt.setString(column++, bean.getCh_QA_type());
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
	
	public static boolean update_en(BusinessPub bean)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("UPDATE TB_BusinessPub SET "
								   + "type = ?, "
								   + "en_title = ?, "
								   + "en_content = ?, "
								   + "publish_date = ?, "
								   + "op_ids = ?, "
								   + "en_QA_type = ? "
								   + "WHERE id = ? ");
			int column = 1;
			pstmt.setString(column++, bean.getType());
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getEn_title()));
			pstmt.setString(column++, dWebTools.escapeXssSymbols(bean.getEn_content()));			
			pstmt.setTimestamp(column++, ToolsUtil.dateToTimestamp(bean.getPublish_date()));
			pstmt.setString(column++, bean.getOp_ids());
			pstmt.setString(column++, bean.getEn_QA_type());
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
			pstmt = sqltools.prepare("DELETE FROM TB_BusinessPub WHERE id = ?");
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
	
	public static HashMap<Integer, ArrayList<BusinessPub>> mapBussinessPub(String language){
		HashMap<Integer, ArrayList<BusinessPub>> result = new HashMap<Integer, ArrayList<BusinessPub>>();
		
		SQL sqltools = new SQL();
		try {
			ResultSet rs = sqltools.query("SELECT DISTINCT A.op_ids, A.type, B.name, B.ename, B.seq "
				+ "FROM TB_BusinessPub A "
				+ "LEFT OUTER JOIN TB_Option B ON A.type = B.id AND B.type = 'business_type' "
				+ "WHERE ISNULL(" + ("en".equalsIgnoreCase(language) ? "en_title" : "ch_title") + ", '') <> '' "
				+ "ORDER BY A.op_ids, B.seq, A.type "
			);
			
			while(rs.next()){
				BusinessPub bp = new BusinessPub();
				bp.setCh_title(rs.getString("name"));
				bp.setEn_title(rs.getString("ename"));

				//業務類別可能有複選，就要都放進去該業務類別內
				String opIds = rs.getString("op_ids");
				if(opIds != null && opIds.trim().length() != 0){
					
					ArrayList<String> list = ToolsUtil.getValueToList(opIds, ",");
					for(String opId : list){
						
						int op_ids = Integer.valueOf(opId);
						if(result.get(op_ids) == null){
							result.put(op_ids, new ArrayList<BusinessPub>());
						}
						result.get(op_ids).add(bp);
					}
				}
			}
			
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	private static BusinessPub setBusinessPub(ResultSet rs, BusinessPub bean) throws SQLException
	{
		bean.setId(rs.getInt("id"));
		bean.setCh_title(rs.getString("ch_title"));
		bean.setCh_content(rs.getString("ch_content"));
		bean.setEn_title(rs.getString("en_title"));
		bean.setEn_content(rs.getString("en_content"));
		bean.setPublish_date(rs.getTimestamp("publish_date"));
		bean.setType(rs.getString("type"));
		bean.setOp_ids(rs.getString("op_ids"));
		bean.setCh_QA_type(rs.getString("ch_QA_type"));
		bean.setEn_QA_type(rs.getString("en_QA_type"));
		return bean;
	}
}
