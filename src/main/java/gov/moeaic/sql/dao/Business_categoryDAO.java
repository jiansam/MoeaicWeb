package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.Business_category;

public class Business_categoryDAO
{
	public static String insert(ArrayList<Business_category> list)
	{
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		String result = ""; 
		try {
			sqltools.noCommit();
			pstmt = sqltools.prepare("DELETE FROM TB_Business_category");
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = sqltools.prepare("INSERT INTO TB_Business_category VALUES(?, ?, ?, ?)");
			for(Business_category bean : list){
				int column = 1;
				pstmt.setString(column++ , bean.getYear());
				pstmt.setDouble(column++ , bean.getAmount());
				pstmt.setString(column++ , bean.getType());
				pstmt.setInt(column++, bean.getSeq());
				pstmt.executeUpdate();
			}
			sqltools.commit();
			result = "success";
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				sqltools.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if(pstmt!=null){
					pstmt.close();
				}
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static Map<Integer, ArrayList<Business_category>> get()
	{
		Map<Integer, ArrayList<Business_category>> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_Business_category ORDER BY seq, id, year");
			rs = pstmt.executeQuery();
			result = new HashMap<>();
			while (rs.next()) {
				Business_category bc = new Business_category();
				int seq = rs.getInt("seq");
				bc.setId(rs.getInt("id"));
				bc.setYear(rs.getString("year"));
				bc.setAmount(rs.getDouble("amount"));
				bc.setType(rs.getString("type"));
				bc.setSeq(seq);

				if(result.get(seq) == null){
					result.put(seq, new ArrayList<>());
				}
				result.get(seq).add(bc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null){
					rs.close();
				}
				if(pstmt!=null){
					pstmt.close();
				}
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static ArrayList<Business_category> get(int seq)
	{
		ArrayList<Business_category> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_Business_category WHERE seq = ? ORDER BY id, year");
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while (rs.next()) {
				Business_category bc = new Business_category();
				bc.setId(rs.getInt("id"));
				bc.setYear(rs.getString("year"));
				bc.setAmount(rs.getDouble("amount"));
				bc.setType(rs.getString("type"));

				result.add(bc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null){
					rs.close();
				}
				if(pstmt!=null){
					pstmt.close();
				}
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static ArrayList<Business_category> getType()
	{
		ArrayList<Business_category> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT DISTINCT seq , type FROM TB_Business_category ");
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while (rs.next()) {
				Business_category bc = new Business_category();
				bc.setType(rs.getString("type"));
				bc.setSeq(rs.getInt("seq"));
				result.add(bc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null){
					rs.close();
				}
				if(pstmt!=null){
					pstmt.close();
				}
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static boolean updateShowIndex(String showIndex)
	{
		boolean result = false;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("UPDATE TB_Option set value = ? WHERE ename = 'showIndex'");
			pstmt.setString(1, showIndex);
			int x = pstmt.executeUpdate();
			if(x!=0){
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
	
	public static String getShowIndex()
	{
		String result = "";
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT value FROM TB_Option WHERE ename = 'showIndex'");
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getString("value");
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
	
	public static HashMap<Integer, ArrayList<Business_category>> index_map(){
		HashMap<Integer, ArrayList<Business_category>> result = new HashMap<Integer, ArrayList<Business_category>>();
		SQL sqltools = new SQL();
		
		try {
			ResultSet rs = sqltools.query("SELECT substring(year, 1, 4) as year ,sum(amount) as amount ,seq "
					+ "FROM TB_Business_category "
					+ "GROUP BY substring(year, 1, 4), seq "
					+ "ORDER BY seq, year DESC ");
			
			while (rs.next()) {
				Business_category bc = new Business_category();
				int seq = rs.getInt("seq");
				bc.setYear(rs.getString("year"));
				bc.setAmount(rs.getDouble("amount"));
				bc.setSeq(seq);

				if(result.get(seq) == null){
					result.put(seq, new ArrayList<>());
				}
				
				result.get(seq).add(bc);
			}
			
			rs.close();
		} catch (SQLException e) {
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
}
