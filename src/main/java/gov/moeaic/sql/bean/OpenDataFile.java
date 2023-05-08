package gov.moeaic.sql.bean;

import java.io.Serializable;

import org.dasin.cryptography.dCipher;

public class OpenDataFile implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String id_str;
	int openData_id;
	String file_name;
	byte[] file_content;
	String file_lang;
	
	@Override
	public String toString()
	{
		return "[id=" + id + ", openData_id=" + openData_id + ", file_name=" + file_name
				 + ", file_lang=" + file_lang + "]";
	}

	public String getId_encrypt()
	{
		return dCipher.encrypt(String.valueOf(id));
	}	
	
	
	public String getId_str()
	{
		return id_str;
	}
	public void setId_str(String id_str)
	{
		this.id_str = id_str;
	}
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getOpenData_id()
	{
		return openData_id;
	}

	public void setOpenData_id(int openData_id)
	{
		this.openData_id = openData_id;
	}

	public String getFile_name()
	{
		return file_name;
	}

	public String getFile_name_str()
	{
		return file_name.substring(0, file_name.lastIndexOf("."));
	}
	
	public void setFile_name(String file_name)
	{
		this.file_name = file_name;
	}

	public byte[] getFile_content()
	{
		return file_content;
	}

	public void setFile_content(byte[] file_content)
	{
		this.file_content = file_content;
	}

	public String getFile_lang()
	{
		return file_lang;
	}

	public void setFile_lang(String file_lang)
	{
		this.file_lang = file_lang;
	}
	
	
	
}
