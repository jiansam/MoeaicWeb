package gov.moeaic.sql.bean;

import java.io.Serializable;
import java.util.Date;

public class LogAccessIP implements Serializable
{
	int id;
	Date logDate;
	String logPage;
	String language;
	String ipCountry;
	int logCount;
	
	
	
	
	@Override
	public String toString()
	{
		return "[logPage=" + logPage + ", language=" + language
				+ ", ipCountry=" + ipCountry + ", logCount=" + logCount + "]";
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public Date getLogDate()
	{
		return logDate;
	}
	public void setLogDate(Date logDate)
	{
		this.logDate = logDate;
	}
	public String getLogPage()
	{
		return logPage;
	}
	public void setLogPage(String logPage)
	{
		this.logPage = logPage;
	}
	public String getLanguage()
	{
		return language;
	}
	public void setLanguage(String language)
	{
		this.language = language;
	}
	public int getLogCount()
	{
		return logCount;
	}
	public void setLogCount(int logCount)
	{
		this.logCount = logCount;
	}
	public String getIpCountry()
	{
		return ipCountry;
	}
	public void setIpCountry(String ipCountry)
	{
		this.ipCountry = ipCountry;
	}
	
	
}
