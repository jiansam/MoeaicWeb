package gov.moeaic.sql.bean;

import java.util.Date;

public class EW_NEWS_ATTACHMENT
{
	int id;
	int news_id;
	String filename;
	Date created_at;
	boolean deleted;
	
	@Override
	public String toString()
	{
		return "EW_NEWS_ATTACHMENT [ID=" + id + ", NEWS_ID=" + news_id + ", FILENAME=" + filename + ", CREATED_AT="
				+ created_at + ", DELETED=" + deleted + "]";
	}

	public int getID()
	{
		return id;
	}

	public void setID(int ID)
	{
		id = ID;
	}

	public int getNEWS_ID()
	{
		return news_id;
	}

	public void setNEWS_ID(int NEWS_ID)
	{
		news_id = NEWS_ID;
	}

	public String getFILENAME()
	{
		return filename;
	}

	public void setFILENAME(String FILENAME)
	{
		filename = FILENAME;
	}

	public Date getCREATED_AT()
	{
		return created_at;
	}

	public void setCREATED_AT(Date CREATED_AT)
	{
		created_at = CREATED_AT;
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
