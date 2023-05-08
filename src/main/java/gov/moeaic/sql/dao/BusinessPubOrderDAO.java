package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.BusinessPubOrder;

public class BusinessPubOrderDAO
{
	public static ArrayList<BusinessPubOrder> get(String op_id_one)
	{
		ArrayList<BusinessPubOrder> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare(
						   "SELECT * FROM VW_BusinessPubXOrder "
						 + "WHERE op_id = ? "
						 + "AND type IN ( SELECT id FROM TB_Option ) "
						 + "ORDER BY lang, type, sort, publish_date DESC");
//					"SELECT A.*, B.ch_title, B.ch_content, B.en_title, B.en_content, B.publish_date, B.type, "
//						 + "ISNULL(B.ch_QA_type, '') AS ch_QA_type, ISNULL(B.en_QA_type, '') AS en_QA_type "
//						 + "FROM TB_BusinessPubOrder A "
//						 + "LEFT OUTER JOIN TB_BusinessPub B ON A.businessPub_id = B.id "
//						 + "WHERE A.op_id = ? "
//						 + "AND B.type IN ( SELECT id FROM TB_Option ) "
//						 + "ORDER BY lang, B.type, convert(int, A.sort), publish_date DESC");
 			pstmt.setString(1, op_id_one);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				BusinessPubOrder bean = new BusinessPubOrder();
				bean.setId(rs.getInt("id"));
				bean.setOp_id(rs.getString("op_id"));
				bean.setBusinessPub_id(rs.getInt("businessPub_id"));
				bean.setSort(rs.getInt("sort"));
				bean.setLang(rs.getString("lang"));
				bean.setCh_title(rs.getString("ch_title"));
				bean.setCh_content(rs.getString("ch_content"));
				bean.setEn_title(rs.getString("en_title"));
				bean.setEn_content(rs.getString("en_content"));
				bean.setPublish_date(rs.getTimestamp("publish_date"));
				bean.setType(rs.getString("type"));
				bean.setCh_QA_type(rs.getString("ch_QA_type"));
				bean.setEn_QA_type(rs.getString("en_QA_type"));
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
	
	public static Map<String, BusinessPubOrder> get(int businessPub_id)
	{
		Map<String, BusinessPubOrder> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_BusinessPubOrder WHERE businessPub_id = ? ");
			pstmt.setInt(1, businessPub_id);
			rs = pstmt.executeQuery();
			result = new HashMap<>();
			while(rs.next()){
				BusinessPubOrder bean = new BusinessPubOrder();
				bean.setId(rs.getInt("id"));
				bean.setOp_id(rs.getString("op_id"));
				bean.setBusinessPub_id(rs.getInt("businessPub_id"));
				bean.setSort(rs.getInt("sort"));
				result.put(rs.getString("op_id"), bean);
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
	
	public static ArrayList<BusinessPubOrder> get(String op_id_one, String lang)
	{
		ArrayList<BusinessPubOrder> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT A.*, B.ch_title, B.ch_content, B.en_title, B.en_content, B.publish_date, B.type "
						 + "FROM TB_BusinessPubOrder A "
						 + "LEFT OUTER JOIN TB_BusinessPub B ON A.businessPub_id = B.id "
						 + "LEFT OUTER JOIN TB_Option C ON B.type = C.id "
						 + "WHERE A.op_id = ? ";
		
		if("ch".equalsIgnoreCase(lang)){
			sql = sql + "AND LEN(ch_title) > 0 "
					+ "ORDER BY lang, C.seq, sort, publish_date DESC ";
		}else{
			sql = sql + "AND LEN(en_title) > 0"
					+ "ORDER BY lang, C.seq, sort, publish_date DESC ";
		}
		
		try {
			pstmt = sqltools.prepare(sql);
			pstmt.setString(1, op_id_one);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				BusinessPubOrder bean = new BusinessPubOrder();
				bean.setId(rs.getInt("id"));
				bean.setOp_id(rs.getString("op_id"));
				bean.setBusinessPub_id(rs.getInt("businessPub_id"));
				bean.setSort(rs.getInt("sort"));
				bean.setLang(rs.getString("lang"));
				bean.setCh_title(rs.getString("ch_title"));
				bean.setCh_content(rs.getString("ch_content"));
				bean.setEn_title(rs.getString("en_title"));
				bean.setEn_content(rs.getString("en_content"));
				bean.setPublish_date(rs.getTimestamp("publish_date"));
				bean.setType(rs.getString("type"));
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
	
	public static ArrayList<BusinessPubOrder> getFrontBusinessPub(String op_id_one, String lang)
	{
		ArrayList<BusinessPubOrder> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT A.*, B.ch_title, B.ch_content, B.en_title, B.en_content, B.publish_date, B.type, "
						 + "ISNULL(B.ch_QA_type, '') AS ch_QA_type, ISNULL(B.en_QA_type , '') AS en_QA_type "
						 + "FROM TB_BusinessPubOrder A "
						 + "LEFT OUTER JOIN TB_BusinessPub B ON A.businessPub_id = B.id "
						 + "LEFT OUTER JOIN TB_Option C ON B.type = C.id "
						 + "WHERE A.op_id = ? "
						 + "AND publish_date < GETDATE() ";
		
		if("ch".equalsIgnoreCase(lang)){
			sql = sql + "AND LEN(ch_title) > 0 "
				      + "AND LEN(C.name) > 0 "
					  + "ORDER BY lang, C.seq, convert(int, A.sort), publish_date DESC";
		}else{
			sql = sql + "AND LEN(en_title) > 0 "
					  + "AND LEN(C.ename) > 0 "
					  + "ORDER BY lang, C.seq, convert(int, A.sort), publish_date DESC";
		}
		
		try {
			pstmt = sqltools.prepare(sql);
			pstmt.setString(1, op_id_one);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				BusinessPubOrder bean = new BusinessPubOrder();
				bean.setId(rs.getInt("id"));
				bean.setOp_id(rs.getString("op_id"));
				bean.setBusinessPub_id(rs.getInt("businessPub_id"));
				bean.setSort(rs.getInt("sort"));
				bean.setLang(rs.getString("lang"));
				bean.setCh_title(rs.getString("ch_title"));
				bean.setCh_content(rs.getString("ch_content"));
				bean.setEn_title(rs.getString("en_title"));
				bean.setEn_content(rs.getString("en_content"));
				bean.setPublish_date(rs.getTimestamp("publish_date"));
				bean.setType(rs.getString("type"));
				bean.setCh_QA_type(rs.getString("ch_QA_type"));
				bean.setEn_QA_type(rs.getString("en_QA_type"));
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
	
	
	public static void update(int businessPub_id, ArrayList<BusinessPubOrder> list){
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			sqltools.noCommit();
			pstmt = sqltools.prepare("DELETE FROM TB_BusinessPubOrder where businessPub_id = ?");
			pstmt.setInt(1, businessPub_id);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = sqltools.prepare("INSERT INTO TB_BusinessPubOrder VALUES(?,?,?,?)");
			for(BusinessPubOrder bean : list){
				int column = 1;
				pstmt.setString(column++, bean.getOp_id());
				pstmt.setInt(column++, businessPub_id);
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
			pstmt = sqltools.prepare("UPDATE TB_BusinessPubOrder SET sort = ? WHERE id = ?");
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
	
	public static void insert(int businessPub_id, ArrayList<BusinessPubOrder> list){
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			sqltools.noCommit();
			
			pstmt = sqltools.prepare(
					   "INSERT INTO TB_BusinessPubOrder"
					+ "(op_id, businessPub_id, sort, lang) VALUES (?, ?, ?, ?)");
			for(BusinessPubOrder bean : list){
				int column = 1;
				pstmt.setString(column++, bean.getOp_id());
				pstmt.setInt(column++, businessPub_id);
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
			pstmt = sqltools.prepare("DELETE FROM TB_BusinessPubOrder where id = ?");
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
	
	public static void deleteByBusinessPub(int businessPub_id)
	{
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("DELETE FROM TB_BusinessPubOrder where businessPub_id = ?");
			pstmt.setInt(1, businessPub_id);
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
	
	//107-05-22 申辦業務，調整順序時，可設定自動排列。
	public static boolean setAutoSort_Bus(String op_id_one, String type, int newSort, int oldSort)
	{
		
		StringBuilder sql = new StringBuilder();
		//狀況1： 4 變 1，原本的1,2,3都 +1
		if(newSort != 99 && oldSort != 99 && newSort<oldSort) {
			sql.append("UPDATE TB_BusinessPubOrder "
					 + "SET sort = sort+1 "
					 + "WHERE (sort>=? AND sort<?) " //(sort>=1 AND sort<4)
					 + "AND op_id = ? "
					 + "AND businessPub_id IN (SELECT id FROM TB_BusinessPub WHERE type = ?) ");
		}
		//狀況2： 2 變 5，原本的3,4,5都 -1
		else if(newSort != 99 && oldSort != 99 && newSort>oldSort) {
			sql.append("UPDATE TB_BusinessPubOrder "
					 + "SET sort = sort-1 "
					 + "WHERE (sort<=? AND sort>?) " //(sort<=5 AND sort>2)
					 + "AND op_id = ? "
					 + "AND businessPub_id IN (SELECT id FROM TB_BusinessPub WHERE type = ?) ");
		}		
		//狀況3： 1 變 99，原本的2,3,4都 -1
		else if(newSort == 99 && oldSort < 99) {
			sql.append("UPDATE TB_BusinessPubOrder "
					 + "SET sort = sort-1 "
					 + "WHERE (sort<? AND sort>=?) " //(sort<99 AND sort>=1)
					 + "AND op_id = ? "
					 + "AND businessPub_id IN (SELECT id FROM TB_BusinessPub WHERE type = ?) ");
		}
		//狀況4： 99 變 1，原本的1,2,3都 +1
		else if(newSort < 99 && oldSort == 99) {
			sql.append("UPDATE TB_BusinessPubOrder "
					 + "SET sort = sort+1 "
					 + "WHERE (sort>=? AND sort<?) " //(sort>=1 AND sort<99)
					 + "AND op_id = ? "
					 + "AND businessPub_id IN (SELECT id FROM TB_BusinessPub WHERE type = ?) ");
		}	
		//狀況5；兩個都99，不處理
		else if(newSort == 99 && oldSort == 99) {
			return true;
		}
		
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare(sql.toString());
			int col = 1;
			pstmt.setInt(col++, newSort);
			pstmt.setInt(col++, oldSort);
			pstmt.setString(col++, op_id_one);
			pstmt.setString(col++, type);
			pstmt.executeUpdate();
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
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
