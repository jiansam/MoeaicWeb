package gov.moeaic.sql.bean;

import java.util.Date;

public class DeletedPageNews
{
	int id;
	int pagesNew_id;
	Date delete_date;
	
	@Override
	public String toString()
	{
		return "DeletedPageNews [id=" + id + ", pagesNew_id=" + pagesNew_id + ", delete_date=" + delete_date + "]";
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getPagesNew_id()
	{
		return pagesNew_id;
	}

	public void setPagesNew_id(int pagesNew_id)
	{
		this.pagesNew_id = pagesNew_id;
	}

	public Date getDelete_date()
	{
		return delete_date;
	}

	public void setDelete_date(Date delete_date)
	{
		this.delete_date = delete_date;
	}
	
	
}
