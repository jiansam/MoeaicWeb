package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.CountryCode;

public class CountryCodeDAO
{

	public static Map<String, CountryCode> get()
	{
		Map<String, CountryCode> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_CountryCode");
			rs = pstmt.executeQuery();
			result = new HashMap<>();
			while(rs.next()) {
				String code = rs.getString("code");
				CountryCode bean = new CountryCode();
				bean.setEn_name(rs.getString("en_name"));
				bean.setCh_name(rs.getString("ch_name"));
				bean.setCode(code);
				result.put(code, bean);
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
