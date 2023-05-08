package gov.moeaic.sql.bean;

import java.io.Serializable;
import java.util.Date;

import gov.moeaic.sql.controller.ToolsUtil;

public class LogAccess implements Serializable
{
	int id;
	Date logDate;
	String logPage;
	String logPage_type; 
	String language;
	int logNewsId;
	int logCount;
	
	//newsId用
	String title;
	Date publish_date;
	
	
	@Override
	public String toString()
	{
		return "[id=" + id 
				+ ", logPage=" + logPage + ", logPage_type=" + logPage_type
//				+ ", language=" + language + ", logDate=" + logDate 
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
	public String getLogPage_type()
	{
		return logPage_type;
	}
	public void setLogPage_type(String logPage_type)
	{
		this.logPage_type = logPage_type;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public Date getPublish_date()
	{
		return publish_date;
	}
	public void setPublish_date(Date publish_date)
	{
		this.publish_date = publish_date;
	}
	public String getPublish_dateStr()
	{
		return ToolsUtil.dateToChange(publish_date, "yyyy/MM/dd");
	}
	
	
	
}
