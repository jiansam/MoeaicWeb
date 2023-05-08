package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.dasin.tools.dWebTools;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.Option;

public class OptionDAO {
	
	public static ArrayList<Option> listFront(){
		ArrayList<Option> result = new ArrayList<Option>();
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare(
				  "SELECT * FROM TB_Option "
				+ "WHERE (type = 'opendata_type' "
				+ 		 "AND id IN ( SELECT type FROM TB_OpenData) ) "
				+    "OR (type = 'business_type' "
				+ 		 "AND id IN ( SELECT type FROM TB_BusinessPub ) ) "
				+    "OR (type = 'business_category' "
				+ 		 "AND id IN ( SELECT op_id FROM TB_BusinessPubOrder ) ) "
				+ "ORDER BY type, seq, id "
			);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				result.add(setOption(rs, new Option()));
			}
			
			rs.close();
			statement.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				sqltools.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static ArrayList<Option> list(){
		ArrayList<Option> result = new ArrayList<Option>();
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare(
				  "SELECT * FROM TB_Option ORDER BY type, seq, id "
			);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				result.add(setOption(rs, new Option()));
			}
			
			rs.close();
			statement.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				sqltools.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static ArrayList<Option> listByType(String type){
		ArrayList<Option> result = new ArrayList<Option>();
		for(Option option : list()){
			if(option.getType().equalsIgnoreCase(type)){
				result.add(option);
			}
		}
		
		return result;
	}
	
	public static Option get(int id){
		Option result = null;
		SQL sqltools = new SQL();
		try {
			PreparedStatement statement = sqltools.prepare("SELECT * FROM TB_Option WHERE id = ? ");
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				result = setOption(rs, new Option());
			}
			rs.close();
			
			statement.close();
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
	
	public static Option insert(Option option){
		SQL sqltools = new SQL();
		Option result = null;
		try {
			PreparedStatement statement = sqltools.prepare(
				"INSERT INTO TB_Option (name, ename, value, type, seq, enable) " +
				"VALUES (?, ?, ?, ?, ?, 1) " , new String[]{"id"}
			);
			
			int column = 1;
			statement.setString(column++, dWebTools.escapeXssSymbols(option.getName()));
			statement.setString(column++, dWebTools.escapeXssSymbols(option.getEname()));
			statement.setString(column++, option.getValue());
			statement.setString(column++, option.getType());
			statement.setInt(column++, option.getSeq());
			statement.executeUpdate();
			
			ResultSet rs = statement.getGeneratedKeys();
			if(rs.next()){
				option.setId(rs.getInt(1));
				result = option;
			}
			rs.close();
			
			statement.close();
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
	
	public static boolean update(Option option){
		SQL sqltools = new SQL();
		boolean result = false;
		try {
			PreparedStatement statement = sqltools.prepare(
				"UPDATE TB_Option SET "
				+ "name = ?, "
				+ "ename = ?, "
				+ "value = ?, "
				+ "type = ?, "
				+ "seq = ? "
				+ "WHERE id = ? "
			);
			
			int column = 1;
			statement.setString(column++, dWebTools.escapeXssSymbols(option.getName()));
			statement.setString(column++, dWebTools.escapeXssSymbols(option.getEname()));
			statement.setString(column++, option.getValue());
			statement.setString(column++, option.getType());
			statement.setInt(column++, option.getSeq());
			statement.setInt(column++, option.getId());
			
			if(statement.executeUpdate() != 0){
				result = true;
			}
			statement.close();
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
	
	public static void delete(int id){
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare("DELETE FROM TB_Option WHERE id = ? ");
			statement.setInt(1, id);
			statement.executeUpdate();
			statement.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				sqltools.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	static Option setOption(ResultSet rs, Option option) throws SQLException{
		option.setId(rs.getInt("id"));
		option.setName(rs.getString("name"));
		option.setEname(rs.getString("ename"));
		option.setValue(rs.getString("value"));
		option.setType(rs.getString("type"));
		option.setSeq(rs.getInt("seq"));
		option.setEnable(rs.getBoolean("enable"));
		
		return option;
	}
}
