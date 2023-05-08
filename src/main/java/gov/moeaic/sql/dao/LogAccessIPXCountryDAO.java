package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.LogAccessIP;
import gov.moeaic.sql.bean.LogAccessIPXCounrty;
import gov.moeaic.sql.controller.ToolsUtil;

public class LogAccessIPXCountryDAO
{

	public static void insert(ArrayList<LogAccessIPXCounrty> list)
	{
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare(
					"INSERT INTO TB_LogAccessIPXCountry(logDate,logIP,ipCountry,logPage,logPage_type,language,logNewsId,logCount) "
				  + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
			sqltools.noCommit();
			for(LogAccessIPXCounrty bean : list){
				int column = 1;
				pstmt.setTimestamp(column++ , ToolsUtil.dateToTimestamp(bean.getLogDate()));
				pstmt.setString(column++, bean.getLogIP());
				pstmt.setString(column++, bean.getIpCountry());
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
	
	
	public static ArrayList<LogAccessIPXCounrty> getCountrySLogCount(String logPage, String logPage_type){
		ArrayList<LogAccessIPXCounrty> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String wherSql = "";
		String[] ary = null;
		if(logPage.indexOf("&") != -1) {
			ary = logPage.split("&");
			wherSql = "WHERE (logPage = ? OR logPage = ?) ";
		}else if(logPage_type != null && !logPage_type.isEmpty()) {
			wherSql = "WHERE logPage = ? and logPage_type = ? ";
		}else {
			wherSql = "WHERE logPage = ? ";
		}
		//System.out.println(wherSql);
		
		try {
			pstmt = sqltools.prepare(
				 "SELECT B.ch_name, ipCountry, sum(logCount) as logCount "
			   + "FROM TB_LogAccessIPXCountry A "
			   + "LEFT OUTER JOIN TB_CountryCode B ON A.ipCountry = B.code "
			   + wherSql
			   + "GROUP BY ipCountry, ch_name "
			   + "ORDER BY logCount DESC ");
			int column = 1;
			if(logPage.indexOf("&") != -1) {
				pstmt.setString(column++, ary[0]);
				pstmt.setString(column++, ary[1]);
			}else if(logPage_type != null && !logPage_type.isEmpty()) {
				pstmt.setString(column++, logPage);
				pstmt.setString(column++, logPage_type);
			}else {
				pstmt.setString(column++, logPage);
			}
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()) {
				LogAccessIPXCounrty bean = new LogAccessIPXCounrty();
				String ipCountry = rs.getString("ipCountry");
				if("不在國別表內".equals(ipCountry)) {
					bean.setIpCountry_ch("不在國別表內");
				}else {
					bean.setIpCountry_ch(rs.getString("ch_name"));
				}
				bean.setIpCountry(rs.getString("ipCountry"));
				bean.setLogCount(rs.getInt("logCount"));
				bean.setLogPage(logPage);
				bean.setLogPage_type(logPage_type);
				result.add(bean);
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


	public static ArrayList<LogAccessIPXCounrty> getOneCountryLogCount(String logPage, String logPage_type,	String country)
	{
		ArrayList<LogAccessIPXCounrty> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String wherSql = "";
		String[] ary = null;
		if(logPage.indexOf("&") != -1) {
			ary = logPage.split("&");
			wherSql = "WHERE (logPage = ? OR logPage = ?) and ipCountry = ? ";
		}else if(logPage_type != null && !logPage_type.isEmpty()) {
			wherSql = "WHERE logPage = ? and logPage_type = ? and ipCountry = ? ";
		}else {
			wherSql = "WHERE logPage = ? and ipCountry = ? ";
		}
		
		try {
			pstmt = sqltools.prepare(
				 "SELECT TOP 100 logIP, sum(logCount) as logCount "
			   + "FROM TB_LogAccessIPXCountry "
			   + wherSql
			   + "GROUP BY logIP "
			   + "ORDER BY logCount DESC ");
			int column = 1;
			if(logPage.indexOf("&") != -1) {
				pstmt.setString(column++, ary[0]);
				pstmt.setString(column++, ary[1]);
				pstmt.setString(column++, country);
			}else if(logPage_type != null && !logPage_type.isEmpty()) {
				pstmt.setString(column++, logPage);
				pstmt.setString(column++, logPage_type);
				pstmt.setString(column++, country);
			}else {
				pstmt.setString(column++, logPage);
				pstmt.setString(column++, country);
			}
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()) {
				LogAccessIPXCounrty bean = new LogAccessIPXCounrty();
				bean.setLogIP(rs.getString("logIP"));
				bean.setLogCount(rs.getInt("logCount"));
				bean.setLogPage(logPage);
				bean.setLogPage_type(logPage_type);
				bean.setIpCountry(country);
				result.add(bean);
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


	public static ArrayList<LogAccessIPXCounrty> getIndexOneCountryLogCount(String logPage, String language, String country)
	{
		ArrayList<LogAccessIPXCounrty> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String wherSql = "";
		String[] ary = null;
		
		try {
			pstmt = sqltools.prepare(
				 "SELECT TOP 100 logIP, sum(logCount) as logCount "
			   + "FROM TB_LogAccessIPXCountry "
			   + "WHERE logPage = ? AND language = ? AND ipCountry = ? "
			   + "GROUP BY logIP "
			   + "ORDER BY logCount DESC ");
			int column = 1;
			pstmt.setString(column++, logPage);
			pstmt.setString(column++, language);
			pstmt.setString(column++, country);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()) {
				LogAccessIPXCounrty bean = new LogAccessIPXCounrty();
				bean.setLogIP(rs.getString("logIP"));
				bean.setLogCount(rs.getInt("logCount"));
				bean.setLogPage(logPage);
				bean.setIpCountry(country);
				result.add(bean);
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
	

	
}
