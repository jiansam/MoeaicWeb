package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.LogAccessIP;
import gov.moeaic.sql.bean.LogAccessIPXCounrty;
import gov.moeaic.sql.controller.ToolsUtil;

public class LogAccessIPDAO
{

	
	public static void insert(ArrayList<LogAccessIP> list)
	{
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare(
					"INSERT INTO TB_LogAccessIP(logDate,logPage,language,ipCountry,logCount) "
				  + "VALUES(?, ?, ?, ?, ?)");
			sqltools.noCommit();
			for(LogAccessIP bean : list){
				int column = 1;
				pstmt.setTimestamp(column++ , ToolsUtil.dateToTimestamp(bean.getLogDate()));
				pstmt.setString(column++ , bean.getLogPage());
				pstmt.setString(column++ , bean.getLanguage());
				pstmt.setString(column++, bean.getIpCountry());
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
	
	
	public static Map<String, ArrayList<LogAccessIPXCounrty>> getSum()
	{
		Map<String, ArrayList<LogAccessIPXCounrty>> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare(
				 "SELECT A.logPage, A.language, A.ipCountry, B.ch_name, SUM(logCount) AS logCount "
			   + "FROM TB_LogAccessIPXCountry A " //108-08-31 從使用 TB_LogAccessIP 改成 TB_LogAccessIPXCountry
			   + "LEFT OUTER JOIN TB_CountryCode B ON A.ipCountry = B.code "
			   + "WHERE logPage = 'index' "
			   + "GROUP BY A.logPage, A.language, A.ipCountry, B.ch_name "
			   + "ORDER BY A.language, logCount DESC, ipCountry ");
			rs = pstmt.executeQuery();
			result = new HashMap<>();
			while(rs.next()) {
				
				String language = "country_" + rs.getString("language");
				if(result.get(language) == null) {
					result.put(language, new ArrayList<>());
				}
				
				LogAccessIPXCounrty bean = new LogAccessIPXCounrty();
				String ipCountry = rs.getString("ipCountry");
				bean.setIpCountry(ipCountry);
				if("不在國別表內".equals(ipCountry)) {
					bean.setIpCountry_ch("不在國別表內");
				}else {
					bean.setIpCountry_ch(rs.getString("ch_name"));
				}
				bean.setLogCount(rs.getInt("logCount"));
				bean.setLanguage(rs.getString("language"));
				result.get(language).add(bean);
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
