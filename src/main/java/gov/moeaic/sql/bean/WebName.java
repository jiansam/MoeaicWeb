package gov.moeaic.sql.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import gov.moeaic.sql.controller.ToolsUtil;

public class WebName implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String name;
	String lang;
	String web_titles;
	String url;
	boolean showIndex;
	Date update_date;
	String photoFile;
	String file_name;
	byte[] file_content;
	
	
	


	@Override
	public String toString()
	{
		return "WebName [id=" + id + ", name=" + name + ", photoFile=" + photoFile + ", file_name=" + file_name + "]";
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLang()
	{
		return lang;
	}

	public void setLang(String lang)
	{
		this.lang = lang;
	}

	public String getFile_name()
	{
		return file_name;
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

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getWeb_titles()
	{
		return web_titles;
	}

	public void setWeb_titles(String web_titles)
	{
		this.web_titles = web_titles;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public boolean isShowIndex()
	{
		return showIndex;
	}

	public void setShowIndex(boolean showIndex)
	{
		this.showIndex = showIndex;
	}

	public Date getUpdate_date()
	{
		return update_date;
	}
	
	public String getUpdate_date_ROC(){
		String result = "";
		if(update_date!=null){
			try {
				int CHyear = Integer.valueOf(ToolsUtil.dateToChange(update_date, "yyyy")) - 1911;
				String m_d = ToolsUtil.dateToChange(update_date, "MM/dd");
				result = String.valueOf(CHyear) +"/"+ m_d;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public String getUpdate_date_AD(){
		String result = "";
		if(update_date!=null){
			try {
				int CHyear = Integer.valueOf(ToolsUtil.dateToChange(update_date, "yyyy"));
				String m_d = ToolsUtil.dateToChange(update_date, "MM/dd");
				result = String.valueOf(CHyear) +"/"+ m_d;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public void setUpdate_date_ROC(String update_date)
	{
		Date result = null;
		if(update_date!=null){
			try {
				String[] ary = update_date.split("/");
				String date = (Integer.valueOf(ary[0]) + 1911) + "-" + ary[1] + "-" + ary[2];
				result = ToolsUtil.dateToChange(date, "yyyy-MM-dd");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		this.update_date = result;
	}
	
	public void setUpdate_date_AD(String update_date)
	{
		Date result = null;
		if(update_date!=null){
			try {
				String[] ary = update_date.split("/");
				String date = ary[0] + "-" + ary[1] + "-" + ary[2];
				result = ToolsUtil.dateToChange(date, "yyyy-MM-dd");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		this.update_date = result;
	}
	
	public void setUpdate_date(Date update_date)
	{
		this.update_date = update_date;
	}

	public String getPhotoFile()
	{
		return photoFile;
	}

	public void setPhotoFile(String photoFile)
	{
		this.photoFile = photoFile;
	}

	
}
