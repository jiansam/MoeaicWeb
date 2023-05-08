package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.Business_category;
import gov.moeaic.sql.bean.LogAccess;
import gov.moeaic.sql.controller.ToolsUtil;

public class LogAccessDAO
{

	public static Date getLastLogDate()
	{
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Date result = null;
		try {
			pstmt = sqltools.prepare("SELECT TOP 1 logDate FROM TB_LogAccess ORDER BY logDate desc");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getDate("logDate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
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
	
	public static Map<String, Date> getLogPeriod()
	{
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, Date> result = null;
		try {
			pstmt = sqltools.prepare(
					  "SELECT logStart.sDate, logEnd.eDate "
					+ "FROM (SELECT TOP 1 logDate AS sDate FROM TB_LogAccess order by logDate) logStart, "
					+ "	 	(SELECT TOP 1 logDate AS eDate FROM TB_LogAccess order by logDate DESC) logEnd");
			rs = pstmt.executeQuery();
			result = new HashMap<>();
			if(rs.next()) {
				result.put("sDate", rs.getTimestamp("sDate"));
				result.put("eDate", rs.getTimestamp("eDate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
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
	
	
	public static void insert(ArrayList<LogAccess> list)
	{
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("INSERT INTO TB_LogAccess"
					+ "(logDate,logPage,logPage_type,language,logNewsId,logCount) "
					+ "VALUES(?, ?, ?, ?, ?, ?)");
			sqltools.noCommit();
			for(LogAccess bean : list){
				int column = 1;
				pstmt.setTimestamp(column++ , ToolsUtil.dateToTimestamp(bean.getLogDate()));
				pstmt.setString(column++ , bean.getLogPage());
				pstmt.setString(column++ , bean.getLogPage_type());
				pstmt.setString(column++ , bean.getLanguage());
				pstmt.setInt(column++, bean.getLogNewsId());
				pstmt.setInt(column++, bean.getLogCount());
				pstmt.executeUpdate();
			}
			sqltools.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
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
	
	public static Map<String, ArrayList<LogAccess>> getNewsIdSum()
	{
		Map<String, ArrayList<LogAccess>> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare(
				  "SELECT A.id, A.ch_title, A.en_title, A.type, A.publish_date, SUM(ISNULL(B.logCount,0)) newsIdSum "
				+ "FROM TB_PagesNews A "
				+ "LEFT OUTER JOIN TB_LogAccess B ON A.id = B.logNewsId "
				+ "GROUP BY A.id, A.ch_title, A.en_title, A.type, A.publish_date "
				+ "ORDER BY A.type, A.publish_date desc "	
				
					
//				  "SELECT SUM(A.logCount) newsIdSum, A.logPage_type, A.language, A.logNewsId, B.ch_title, "
//				+ "		  B.en_title, B.publish_date "
//				+ "FROM TB_LogAccess A "
//				+ "LEFT OUTER JOIN TB_PagesNews B ON A.logNewsId = B.id "
//				+ "WHERE A.logPage = 'newsId' AND (B.ch_title IS NOT NULL OR B.en_title IS NOT NULL) "
//				+ "GROUP BY A.logPage_type, A.language, A.logNewsId, B.ch_title, B.en_title, B.publish_date "
//				+ "ORDER BY A.logPage_type, B.publish_date desc"
				);
			rs = pstmt.executeQuery();
			result = new HashMap<>();
			while(rs.next()) {
				
				String ch_title = rs.getString("ch_title") == null? "" : rs.getString("ch_title");
				String en_title = rs.getString("en_title") == null? "" : rs.getString("en_title");
				if(ch_title.trim().length() == 0 && en_title.trim().length() == 0) {
					continue;
				}
				
				String logPage_type = rs.getString("type");
				String language = "";
				if(ch_title.trim().length() > 0) {
					language = "ch";
				}else if(en_title.trim().length() > 0) {
					language = "en";
				}
				String key = logPage_type+"_"+language;
				if(result.get(key) == null) {
					result.put(key, new ArrayList<>());
				}
				
				LogAccess bean = new LogAccess();
				bean.setLogCount(rs.getInt("newsIdSum"));
				bean.setLogPage_type(logPage_type);
				bean.setLanguage(language);
				bean.setLogNewsId(rs.getInt("id"));
				if(ch_title.trim().length() > 0) {
					bean.setTitle(ch_title);
				}else {
					bean.setTitle(en_title);
				}
				bean.setPublish_date(rs.getTimestamp("publish_date"));
				
				result.get(key).add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
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
	
	
	public static Map<String, Integer> getSum()
	{
		Map<String, Integer> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare(
				"SELECT a.aboutSum, b.newsSum, b1.newAnSum, b2.stRtSum, b3.bsAnSum, b4.categorySum, "
			   +       "c.businessPubSum, d.linkSum, e.openDataSum, f.allSum, g.indexSum " 
			   +"FROM (SELECT SUM(logCount) AS aboutSum FROM TB_LogAccess WHERE logPage = 'about') a, "
			   + 	 "(SELECT SUM(logCount) AS newsSum FROM TB_LogAccess WHERE (logPage = 'news' OR logPage = 'business_category')) b, "
			   + 	 "(SELECT SUM(logCount) AS newAnSum FROM TB_LogAccess WHERE (logPage = 'news'  AND logPage_type = 'news_newAn')) b1, "
			   + 	 "(SELECT SUM(logCount) AS stRtSum FROM TB_LogAccess WHERE (logPage = 'news'  AND logPage_type = 'news_stRt')) b2, "
			   + 	 "(SELECT SUM(logCount) AS bsAnSum FROM TB_LogAccess WHERE (logPage = 'news'  AND logPage_type = 'news_bsAn')) b3, "
			   + 	 "(SELECT SUM(logCount) AS categorySum FROM TB_LogAccess WHERE (logPage = 'business_category')) b4, "
			   + 	 "(SELECT SUM(logCount) AS businessPubSum FROM TB_LogAccess WHERE logPage = 'businessPub') c, "
			   + 	 "(SELECT SUM(logCount) AS linkSum FROM TB_LogAccess WHERE logPage = 'links') d, "
			   + 	 "(SELECT SUM(logCount) AS openDataSum FROM TB_LogAccess WHERE logPage = 'openData') e, "
			   + 	 "(SELECT SUM(logCount) AS allSum FROM TB_LogAccess) f, "
			   + 	 "(SELECT SUM(logCount) AS indexSum FROM TB_LogAccess WHERE logPage = 'index') g ");
			rs = pstmt.executeQuery();
			result = new TreeMap<>();
			if(rs.next()) {
				result.put("aboutSum", rs.getInt("aboutSum"));
				result.put("newsSum", rs.getInt("newsSum"));
				result.put("newAnSum", rs.getInt("newAnSum"));
				result.put("stRtSum", rs.getInt("stRtSum"));
				result.put("bsAnSum", rs.getInt("bsAnSum"));
				result.put("categorySum", rs.getInt("categorySum"));
				result.put("businessPubSum", rs.getInt("businessPubSum"));
				result.put("linkSum", rs.getInt("linkSum"));
				result.put("openDataSum", rs.getInt("openDataSum"));
				result.put("allSum", rs.getInt("allSum"));
				result.put("indexSum", rs.getInt("indexSum"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
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

	//取得排除IP表（192.168 內部網路IP在程式內排除）
	public static ArrayList<String> getExcludeIP()
	{
		ArrayList<String> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_ExcludeIP");
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()) {
				result.add(rs.getString("IP"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
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
	
	
	private static LogAccess setLogAccess(ResultSet rs, LogAccess bean) throws SQLException
	{
		bean.setId(rs.getInt("id"));
		bean.setLogDate(rs.getTimestamp("logDate"));
		bean.setLogPage(rs.getString("logPage"));
		bean.setLogPage_type(rs.getString("logPage_type"));
		bean.setLanguage(rs.getString("language"));
		bean.setLogNewsId(rs.getInt("logNewsId"));
		bean.setLogCount(rs.getInt("logCount"));
		
		return bean;
	}
	
	
}
