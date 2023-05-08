package gov.moeaic.sql.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.dasin.cryptography.dCipher;
import org.dasin.tools.dTools;

import gov.moeaic.sql.SQL;
import gov.moeaic.sql.bean.User;

public class UserDAO
{

	public static User login(String user_id, String user_password){
		User user = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			pstmt = sqltools.prepare(
				"SELECT * FROM TB_User WHERE account = ? AND password_encrypt = ? AND disable = '0' "
			);
			pstmt.setString(1, user_id);
			pstmt.setString(2, dCipher.encrypt(user_password));
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				user = setUser(rs, new User());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (rs != null) {
					rs.close();
				}
				if(pstmt != null){
					pstmt.close();
				}
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return user;
	}
	
	public static ArrayList<User> getALL()
	{
		ArrayList<User> result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_User");
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()){
				result.add(setUser(rs, new User()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if(pstmt != null){
					pstmt.close();
				}
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	

	public static User get(int id)
	{
		User result = null;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("SELECT * FROM TB_User WHERE id = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = setUser(rs, new User());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if(pstmt != null){
					pstmt.close();
				}
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static int insert(User bean){
		int result = 0;
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = sqltools.prepare("INSERT INTO TB_User (account, password_encrypt, name, department, tel, "
										+ "email, admin, disable, registered_date, registered_manager) "
										+ "VALUES( ?,?,?,?,?,?,?,?,GETDATE(),? )" , new String[]{"id"});
			int count = 1;
			pstmt.setString(count++, bean.getAccount());
			pstmt.setString(count++, bean.getPassword_encrypt());
			pstmt.setString(count++, bean.getName());
			pstmt.setString(count++, bean.getDepartment());
			pstmt.setString(count++, bean.getTel());
			pstmt.setString(count++, bean.getEmail());
			pstmt.setBoolean(count++, bean.isAdmin());
			pstmt.setBoolean(count++, bean.isDisable());
			pstmt.setString(count++, bean.getRegistered_manager());
			int x = pstmt.executeUpdate();
			if(x!=0){
				rs = pstmt.getGeneratedKeys();
				if(rs.next()){
					result = rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null){
					rs.close();
				}
				if(pstmt != null){
					pstmt.close();
				}
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static void updatePassword(User bean){
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("UPDATE TB_User SET "
									+ "password_encrypt = ?, "
									+ "lastUpdate_date = GETDATE(), "
									+ "lastUpdate_user = ? "
									+ "WHERE id = ?"
									);
			int count = 1;
			pstmt.setString(count++, bean.getPassword_encrypt());
			pstmt.setString(count++, bean.getLastUpdate_user());
			pstmt.setInt(count++, bean.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null){
					pstmt.close();
				}
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void update(User bean){
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("UPDATE TB_User SET "
									+ "name = ?, "
									+ "department = ?, "
									+ "tel = ?, "
									+ "email = ?, "
									+ "admin = ?, "
									+ "disable = ?, "
									+ "lastUpdate_date = GETDATE(), "
									+ "lastUpdate_user = ? "
									+ "WHERE id = ?"
									);
			int count = 1;
			pstmt.setString(count++, bean.getName());
			pstmt.setString(count++, bean.getDepartment());
			pstmt.setString(count++, bean.getTel());
			pstmt.setString(count++, bean.getEmail());
			pstmt.setBoolean(count++, bean.isAdmin());
			pstmt.setBoolean(count++, bean.isDisable());
			pstmt.setString(count++, bean.getLastUpdate_user());
			pstmt.setInt(count++, bean.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null){
					pstmt.close();
				}
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	
	public static void userDisable(int id, boolean disable){
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("UPDATE TB_User SET "
									+ "disable = ? "
									+ "WHERE id = ?"
									);
			int count = 1;
			pstmt.setBoolean(count++, disable);
			pstmt.setInt(count++, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null){
					pstmt.close();
				}
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void userAdmin(int id, boolean admin){
		SQL sqltools = new SQL();
		PreparedStatement pstmt = null;
		try {
			pstmt = sqltools.prepare("UPDATE TB_User SET "
									+ "admin = ? "
									+ "WHERE id = ?"
									);
			int count = 1;
			pstmt.setBoolean(count++, admin);
			pstmt.setInt(count++, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null){
					pstmt.close();
				}
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static User setUser(ResultSet rs, User bean) throws SQLException
	{
		bean.setId(rs.getInt("id"));
		bean.setAccount(rs.getString("account"));
		bean.setPassword_encrypt(rs.getString("password_encrypt"));
		bean.setName(rs.getString("name"));
		bean.setDepartment(rs.getString("department"));
		bean.setTel(rs.getString("tel"));
		bean.setEmail(rs.getString("email"));
		bean.setAdmin(rs.getBoolean("admin"));
		bean.setDisable(rs.getBoolean("disable"));
		bean.setRegistered_date(rs.getTimestamp("registered_date"));
		bean.setRegistered_manager(rs.getString("registered_manager"));
		bean.setLastUpdate_date(rs.getTimestamp("lastUpdate_date"));
		bean.setLastUpdate_user(rs.getString("lastUpdate_user"));
		return bean;
	}
}
