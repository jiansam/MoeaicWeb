package gov.moeaic.sql.bean;

import java.io.Serializable;
import java.util.Date;

import org.dasin.cryptography.dCipher;


public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String account;
	String password;
	String password_encrypt;
	String name;
	String department;
	String tel;
	String email;
	boolean admin;
	boolean disable;
	Date registered_date;
	String registered_manager;
	Date lastUpdate_date;
	String lastUpdate_user;
	
	
	
	@Override
	public String toString()
	{
		return "[id=" + id + ", account=" + account + ", password=" + password + ", password_encrypt="
				+ password_encrypt + ", name=" + name + ", department=" + department + ", tel=" + tel + ", email="
				+ email + ", admin=" + admin + ", disable=" + disable + ", registered_date=" + registered_date
				+ ", registered_manager=" + registered_manager + ", lastUpdate_date=" + lastUpdate_date
				+ ", lastUpdate_user=" + lastUpdate_user + "]";
	}
	
	public String getPasswordByDecrypt(){
		String result = "";
		if(password_encrypt!=null && password_encrypt.length()!=0){
			result = dCipher.decrypt(password_encrypt);
		}
		return result;
	}
	
	public void setPasswordToEncrypt(String password){
		String result = "";
		if(password!=null && password.length()!=0){
			result = dCipher.encrypt(password);
		}
		this.password_encrypt = result;
	}
	
	public void setPasswordToDecrypt(String password_encrypt){
		String result = "";
		if(password_encrypt!=null && password_encrypt.length()!=0){
			result = dCipher.decrypt(password_encrypt);
		}
		this.password = result;
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getAccount()
	{
		return account;
	}
	public void setAccount(String account)
	{
		this.account = account;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getPassword_encrypt()
	{
		return password_encrypt;
	}
	public void setPassword_encrypt(String password_encrypt)
	{
		this.password_encrypt = password_encrypt;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDepartment()
	{
		return department;
	}
	public void setDepartment(String department)
	{
		this.department = department;
	}
	public String getTel()
	{
		return tel;
	}
	public void setTel(String tel)
	{
		this.tel = tel;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}

	public boolean isAdmin()
	{
		return admin;
	}
	public void setAdmin(boolean admin)
	{
		this.admin = admin;
	}
	public boolean isDisable()
	{
		return disable;
	}
	public void setDisable(boolean disable)
	{
		this.disable = disable;
	}
	public Date getRegistered_date()
	{
		return registered_date;
	}
	public void setRegistered_date(Date registered_date)
	{
		this.registered_date = registered_date;
	}
	public String getRegistered_manager()
	{
		return registered_manager;
	}
	public void setRegistered_manager(String registered_manager)
	{
		this.registered_manager = registered_manager;
	}
	public Date getLastUpdate_date()
	{
		return lastUpdate_date;
	}
	public void setLastUpdate_date(Date lastUpdate_date)
	{
		this.lastUpdate_date = lastUpdate_date;
	}
	public String getLastUpdate_user()
	{
		return lastUpdate_user;
	}
	public void setLastUpdate_user(String lastUpdate_user)
	{
		this.lastUpdate_user = lastUpdate_user;
	}
	
	
}
