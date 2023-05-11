package gov.moeaic.sql.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import gov.moeaic.sql.controller.ToolsUtil;

public class PagesNews implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String ch_title;
	String ch_content;
	String en_title;
	String en_content;
	Date publish_date;
	boolean showTop;
	int sort;
	String type;
	String rss_text; //108-12-5 for RSS 新增
	String rss_image; //108-12-5 for RSS 新增
	String rss_text_ch; //108-12-5 for RSS 新增
	String rss_image_ch; //108-12-5 for RSS 新增
	String photo_ch;
	String photo_en;
	
	String image_type_ch;
	String image_type_en;

	@Override
	public String toString()
	{
		return "PagesNews [id=" + id + ", ch_title=" + ch_title + ", ch_content=" + ch_content + ", en_title="
				+ en_title + ", en_content=" + en_content + ", publish_date=" + publish_date + ", showTop=" + showTop
				+ ", sort=" + sort + ", type=" + type + ", rss_text=" + rss_text + ", rss_image=" + rss_image + "]";
	}

	public boolean getIsNew(){
		boolean result = false;
		if(publish_date!=null){
			Date d1 = publish_date; //發佈日
			//根據現在時間計算
			Calendar now = Calendar.getInstance(); 
			now.add(Calendar.DAY_OF_MONTH, -3); //現在時間的3天前
			Date d2 = now.getTime();
			result = d1.after(d2);
		}
		return result;
	}
	
	

	public String getRss_text_ch() {
		return rss_text_ch;
	}

	public void setRss_text_ch(String rss_text_ch) {
		this.rss_text_ch = rss_text_ch;
	}

	public String getRss_image_ch() {
		return rss_image_ch;
	}

	public void setRss_image_ch(String rss_image_ch) {
		this.rss_image_ch = rss_image_ch;
	}

	public String getPhoto_ch() {
		return photo_ch;
	}

	public void setPhoto_ch(String photo_ch) {
		this.photo_ch = photo_ch;
	}

	public String getPhoto_en() {
		return photo_en;
	}

	public void setPhoto_en(String photo_en) {
		this.photo_en = photo_en;
	}

	public String getImage_type_ch() {
		return image_type_ch;
	}

	public void setImage_type_ch(String image_type_ch) {
		this.image_type_ch = image_type_ch;
	}

	public String getImage_type_en() {
		return image_type_en;
	}

	public void setImage_type_en(String image_type_en) {
		this.image_type_en = image_type_en;
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

	public String getCh_content()
	{
		return ch_content;
	}

	public void setCh_content(String ch_content)
	{
		this.ch_content = ch_content;
	}

	public String getEn_title()
	{
		return en_title;
	}

	public void setEn_title(String en_title)
	{
		this.en_title = en_title;
	}

	public String getEn_content()
	{
		return en_content;
	}

	public void setEn_content(String en_content)
	{
		this.en_content = en_content;
	}

	public Date getPublish_date()
	{
		return publish_date;
	}

	public String getPublish_date_ROC(){
		String result = "";
		if(publish_date!=null){
			try {
				int CHyear = Integer.valueOf(ToolsUtil.dateToChange(publish_date, "yyyy")) -1911 ;
				String m_d = ToolsUtil.dateToChange(publish_date, "MM/dd");
				result = String.valueOf(CHyear) +"/"+ m_d;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return result;
	}	
	
	
	public String getPublish_date_AD(){
		String result = "";
		if(publish_date!=null){
			try {
				int CHyear = Integer.valueOf(ToolsUtil.dateToChange(publish_date, "yyyy")) ;
				String m_d = ToolsUtil.dateToChange(publish_date, "MM/dd");
				result = String.valueOf(CHyear) +"/"+ m_d;
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public void setPublish_date_ROC(String publish_date, String publish_APM, String publish_time)
	{
		Date result = null;
		if(publish_date.trim().length()!=0 && publish_time.trim().length()!=0){
			try {
				String[] ary = publish_date.split("/");
				String date = (Integer.valueOf(ary[0]) + 1911) + "-" + ary[1] + "-" + ary[2] 
								+ " " + publish_APM + " " + publish_time;
				result = ToolsUtil.dateToChange(date, "yyyy-MM-dd a hh:mm");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		if(result == null){
			result = new Date();
		}
		this.publish_date = result;
	}
	
	public void setPublish_date_AD(String publish_date, String publish_APM, String publish_time)
	{
		Date result = null;
		if(publish_date.trim().length()!=0 && publish_time.trim().length()!=0){
			try {
				String[] ary = publish_date.split("/");
				String date = ary[0] + "-" + ary[1] + "-" + ary[2] 
								+ " " + publish_APM + " " + publish_time;
				result = ToolsUtil.dateToChange(date, "yyyy-MM-dd a hh:mm");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		if(result == null){
			result = new Date();
		}
		this.publish_date = result;
	}
	
	public void setPublish_date(Date publish_date)
	{
		this.publish_date = publish_date;
	}

	public String getPublish_APM()
	{
		String result = "";
		if(publish_date!=null){
			result = ToolsUtil.dateToChangeTime(publish_date, "a", "EN");
		}
		return result;
	}

	public String getPublish_Time()
	{
		String result = "";
		if(publish_date!=null){
			result = ToolsUtil.dateToChangeTime(publish_date, "hh:mm", "EN");
		}
		return result;
	}	
	
	public boolean isShowTop()
	{
		return showTop;
	}

	public void setShowTop(boolean showTop)
	{
		this.showTop = showTop;
	}

	public int getSort()
	{
		return sort;
	}

	public void setSort(int sort)
	{
		this.sort = sort;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getRss_text()
	{
		return rss_text;
	}

	public void setRss_text(String rss_text)
	{
		this.rss_text = rss_text;
	}

	public String getRss_image()
	{
		return rss_image;
	}

	public void setRss_image(String rss_image)
	{
		this.rss_image = rss_image;
	}
	
	
}
