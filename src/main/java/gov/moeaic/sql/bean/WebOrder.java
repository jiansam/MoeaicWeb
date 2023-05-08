package gov.moeaic.sql.bean;

import java.io.Serializable;
import java.util.Date;

import gov.moeaic.sql.controller.ToolsUtil;

public class WebOrder implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	int webName_id;
	int parent_id;
	int sort;
	String lang;
	
	//WebName
	String name;
	String web_titles;
	String url;
	boolean showIndex;
	Date update_date;
	String photoFile;	
	
	//WebTitle
	String ch_title;
	String en_title;
	String parent_sort;

	
	@Override
	public String toString()
	{
		return "WebOrder [web_titles=" + web_titles + ", url=" + url + "]";
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
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getWebName_id()
	{
		return webName_id;
	}

	public void setWebName_id(int webName_id)
	{
		this.webName_id = webName_id;
	}

	public int getParent_id()
	{
		return parent_id;
	}

	public void setParent_id(int parent_id)
	{
		this.parent_id = parent_id;
	}

	public int getSort()
	{
		return sort;
	}

	public void setSort(int sort)
	{
		this.sort = sort;
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

	public String getParent_sort()
	{
		return parent_sort;
	}

	public void setParent_sort(String parent_sort)
	{
		this.parent_sort = parent_sort;
	}
	
	
}
