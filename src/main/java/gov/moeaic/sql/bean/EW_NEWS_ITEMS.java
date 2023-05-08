package gov.moeaic.sql.bean;

import java.util.Date;

public class EW_NEWS_ITEMS
{
	int id;
	int lang;
	String subject;
	String context;
	Date started_at;
	boolean deleted;
	
	
	
	@Override
	public String toString()
	{
		return "EW_NEWS_ITEMS [ID=" + id + ", LANG=" + lang + ", SUBJECT=" + subject + ", CONTEXT=" + context
				+ ", STARTED_AT=" + started_at + ", DELETED=" + deleted + "]";
	}
	public int getID()
	{
		return id;
	}
	public void setID(int ID)
	{
		id = ID;
	}
	public int getLANG()
	{
		return lang;
	}
	public void setLANG(int LANG)
	{
		lang = LANG;
	}
	public String getSUBJECT()
	{
		return subject;
	}
	public void setSUBJECT(String SUBJECT)
	{
		subject = SUBJECT;
	}
	public String getCONTEXT()
	{
		return context;
	}
	public void setCONTEXT(String CONTEXT)
	{
		context = CONTEXT;
	}
	public Date getSTARTED_AT()
	{
		return started_at;
	}
	public void setSTARTED_AT(Date STARTED_AT)
	{
		started_at = STARTED_AT;
	}
	public boolean isDELETED()
	{
		return deleted;
	}
	public void setDELETED(boolean DELETED)
	{
		deleted = DELETED;
	}
	
	
}
