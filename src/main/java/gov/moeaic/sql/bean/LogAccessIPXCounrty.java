package gov.moeaic.sql.bean;

import java.io.Serializable;
import java.util.Date;

public class LogAccessIPXCounrty implements Serializable
{
	int id;
	Date logDate;
	String logIP;
	String ipCountry;
	String ipCountry_ch;
	String logPage;
	String logPage_type; 
	String language;
	int logNewsId;
	int logCount;
	

	@Override
	public String toString()
	{
		return "[logDate=" + logDate + ", logIP=" + logIP + ", ipCountry="
				+ ipCountry + ", logPage=" + logPage + ", logPage_type=" + logPage_type + ", language=" + language
				+ ", logNewsId=" + logNewsId + ", logCount=" + logCount + "]";
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

	public String getLogIP()
	{
		return logIP;
	}

	public void setLogIP(String logIP)
	{
		this.logIP = logIP;
	}

	public String getIpCountry()
	{
		return ipCountry;
	}

	public void setIpCountry(String ipCountry)
	{
		this.ipCountry = ipCountry;
	}

	public String getLogPage()
	{
		return logPage;
	}

	public void setLogPage(String logPage)
	{
		this.logPage = logPage;
	}

	public String getLogPage_type()
	{
		return logPage_type;
	}

	public void setLogPage_type(String logPage_type)
	{
		this.logPage_type = logPage_type;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public int getLogNewsId()
	{
		return logNewsId;
	}

	public void setLogNewsId(int logNewsId)
	{
		this.logNewsId = logNewsId;
	}

	public int getLogCount()
	{
		return logCount;
	}

	public void setLogCount(int logCount)
	{
		this.logCount = logCount;
	}

	public String getIpCountry_ch()
	{
		return ipCountry_ch;
	}

	public void setIpCountry_ch(String ipCountry_ch)
	{
		this.ipCountry_ch = ipCountry_ch;
	}
	
	
}
