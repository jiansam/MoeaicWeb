package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.OpenData;
import gov.moeaic.sql.controller.ToolsUtil;

public class OpenDataDAO {
	public static OpenData get(int id){
		OpenData result = new OpenData();
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare(
				"SELECT * FROM TB_OpenData WHERE id = ? "
			);
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				setOpenData(rs, result);
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
	
	public static ArrayList<OpenData> list(int type){
		ArrayList<OpenData> result = new ArrayList<OpenData>();
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare(
					"SELECT * FROM TB_OpenData "
				  + "WHERE type = ? "
				  + "ORDER BY ontop DESC, seq, publish_date DESC" );
			statement.setInt(1, type);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				result.add(setOpenData(rs, new OpenData()));
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
	
	public static ArrayList<OpenData> isParentId_used_OD(int type){
		ArrayList<OpenData> result = new ArrayList<OpenData>();
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare("SELECT * FROM TB_OpenData WHERE type = ? ");
			statement.setInt(1, type);
			ResultSet rs = statement.executeQuery();
			if(rs.next()){
				result.add(setOpenData(rs, new OpenData()));
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
	
	
	public static ArrayList<OpenData> list(){
		ArrayList<OpenData> result = new ArrayList<OpenData>();
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare(
				"SELECT * FROM TB_OpenData "
				+ "ORDER BY type, ontop DESC, seq, publish_date DESC "
			);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				result.add(setOpenData(rs, new OpenData()));
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
	
	public static ArrayList<OpenData> getFrontlist(){
		ArrayList<OpenData> result = new ArrayList<OpenData>();
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare(
				  "SELECT A.*, B.* FROM TB_OpenData A "
				+ "LEFT OUTER JOIN TB_Option B ON A.type = B.id "
				+ "WHERE publish_date < GETDATE() "
				+ "ORDER BY B.seq, A.ontop DESC, A.seq, A.publish_date DESC "
			);
			
			ResultSet rs = statement.executeQuery();
			while(rs.next()){
				result.add(setOpenData(rs, new OpenData()));
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
	
	public static OpenData insert(OpenData data){
		OpenData result = null;
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare(
				"INSERT INTO TB_OpenData (ch_title, ch_content, en_title, "
			  + "en_content, publish_date, type, seq, ontop, create_date) " 
			  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE()) ",
				new String[]{"id"}
			);
			
			int column = 1;
			statement.setString(column++, data.getCh_title());
			statement.setString(column++, data.getCh_content());
			statement.setString(column++, data.getEn_title());
			statement.setString(column++, data.getEn_content());
			statement.setTimestamp(column++, ToolsUtil.dateToTimestamp(data.getPublish_date()));
			statement.setInt(column++, data.getType());
			statement.setInt(column++, data.getSeq());
			statement.setBoolean(column++, data.getOntop());
			statement.executeUpdate();

			ResultSet rs = statement.getGeneratedKeys();
			
			if(rs.next()){
				data.setId(rs.getInt(1));
				result = data;
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
	
	public static int insertOpenData(OpenData data){
		int result = 0;
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare(
				"INSERT INTO TB_OpenData (ch_title, ch_content, en_title, "
			  + "en_content, publish_date, type, seq, ontop, create_date) " 
			  + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE()) ",
				new String[]{"id"}
			);
			
			int column = 1;
			statement.setString(column++, data.getCh_title());
			statement.setString(column++, data.getCh_content());
			statement.setString(column++, data.getEn_title());
			statement.setString(column++, data.getEn_content());
			statement.setTimestamp(column++, ToolsUtil.dateToTimestamp(data.getPublish_date()));
			statement.setInt(column++, data.getType());
			statement.setInt(column++, data.getSeq());
			statement.setBoolean(column++, data.getOntop());
			statement.executeUpdate();

			ResultSet rs = statement.getGeneratedKeys();
			
			if(rs.next()){
				result = rs.getInt(1);
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
	
	public static boolean update_ch(OpenData data){
		boolean result = false;
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare(
				"UPDATE TB_OpenData SET "
				+ "ch_title = ?, "
				+ "ch_content = ?, "
				+ "publish_date = ?, "
				+ "type = ?, "
				+ "seq = ?, "
				+ "ontop = ? "
				+ "WHERE id = ? "
			);
			
			int column = 1;
			statement.setString(column++, data.getCh_title());
			statement.setString(column++, data.getCh_content());
			statement.setTimestamp(column++, ToolsUtil.dateToTimestamp(data.getPublish_date()));
			statement.setInt(column++, data.getType());
			statement.setInt(column++, data.getSeq());
			statement.setBoolean(column++, data.getOntop());
			statement.setInt(column++, data.getId());
			statement.executeUpdate();

			if(statement.executeUpdate() != 0){
				result = true;
			}
			
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
	
	public static boolean update_en(OpenData data){
		boolean result = false;
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare(
				"UPDATE TB_OpenData SET "
				+ "en_title = ?, "
				+ "en_content = ?, "
				+ "publish_date = ?, "
				+ "type = ?, "
				+ "seq = ?, "
				+ "ontop = ? "
				+ "WHERE id = ? "
			);
			
			int column = 1;
			statement.setString(column++, data.getEn_title());
			statement.setString(column++, data.getEn_content());
			statement.setTimestamp(column++, ToolsUtil.dateToTimestamp(data.getPublish_date()));
			statement.setInt(column++, data.getType());
			statement.setInt(column++, data.getSeq());
			statement.setBoolean(column++, data.getOntop());
			statement.setInt(column++, data.getId());
			statement.executeUpdate();

			if(statement.executeUpdate() != 0){
				result = true;
			}
			
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
	
	public static boolean update_sort(int id, int seq, boolean ontop){
		boolean result = false;
		SQL sqltools = new SQL();
		
		try{
			PreparedStatement statement = sqltools.prepare(
				"UPDATE TB_OpenData SET "
				+ "ontop = ?, "
				+ "seq = ? "
				+ "WHERE id = ? "
			);
			
			int column = 1;
			statement.setBoolean(column++, ontop);
			statement.setInt(column++, seq);
			statement.setInt(column++, id);
			statement.executeUpdate();

			if(statement.executeUpdate() != 0){
				result = true;
			}
			
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
	
	public static void delete(int id){
		SQL sqltools = new SQL();
		
		try{
			sqltools.noCommit();
			
			PreparedStatement statement = sqltools.prepare("DELETE FROM TB_OpenData WHERE id = ? ");
			statement.setInt(1, id);
			statement.executeUpdate();
			statement.close();
			
			sqltools.commit();
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
	
	public static OpenData setOpenData(ResultSet rs, OpenData data) throws SQLException{
		data.setId(rs.getInt("id"));
		data.setCh_title(rs.getString("ch_title"));
		data.setCh_content(rs.getString("ch_content"));
		data.setEn_title(rs.getString("en_title"));
		data.setEn_content(rs.getString("en_content"));
		
//		Calendar c = Calendar.getInstance();
//		c.setTime(rs.getDate("publish_date"));
//		data.setPublish_date(c);
		data.setPublish_date(rs.getTimestamp("publish_date"));;
		
		data.setType(rs.getInt("type"));
		data.setSeq(rs.getInt("seq"));
		data.setOntop(rs.getBoolean("ontop"));
		
		return data;
	}
}
