package gov.moeaic.sql.bean;

import java.io.Serializable;

public class WebTitle implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String ch_title;
	String en_title;
	int sort;
	
	@Override
	public String toString()
	{
		return "WebTitle [id=" + id + ", ch_title=" + ch_title + ", en_title=" + en_title + ", sort=" + sort + "]";
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getCh_title()
	{
		return ch_title;
	}

	public void setCh_title(String ch_title)
	{
		this.ch_title = ch_title;
	}

	public String getEn_title()
	{
		return en_title;
	}

	public void setEn_title(String en_title)
	{
		this.en_title = en_title;
	}

	public int getSort()
	{
		return sort;
	}

	public void setSort(int sort)
	{
		this.sort = sort;
	}
	
	
}
