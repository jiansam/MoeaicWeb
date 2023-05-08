package gov.moeaic.sql.bean;

import java.io.Serializable;
import java.util.Date;

import gov.moeaic.sql.controller.ToolsUtil;

public class BusinessPubOrder implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String op_id;
	String op_id_str;
	String type_str;
	int businessPub_id;
	int sort;
	String lang;
	
	// BusinessPub
	int bs_id;
	String ch_title;
	String ch_content;
	String en_title;
	String en_content;
	Date publish_date;
	String type;
	String ch_QA_type;
	String en_QA_type;
	
	
	


	@Override
	public String toString()
	{
		return "BusinessPubOrder [id=" + id + ", op_id=" + op_id + ", businessPub_id=" + businessPub_id + ", type="
				+ type + "]";
	}

	public String getBusinessPub_id_str()
	{
		return String.valueOf(businessPub_id);
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
	
	public String getOp_id_str()
	{
		return op_id_str;
	}

	public void setOp_id_str(String op_id_str)
	{
		this.op_id_str = op_id_str;
	}

	public String getType_str()
	{
		return type_str;
	}

	public void setType_str(String type_str)
	{
		this.type_str = type_str;
	}

	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getOp_id()
	{
		return op_id;
	}
	public void setOp_id(String op_id)
	{
		this.op_id = op_id;
	}
	public int getBusinessPub_id()
	{
		return businessPub_id;
	}
	public void setBusinessPub_id(int businessPub_id)
	{
		this.businessPub_id = businessPub_id;
	}
	public int getSort()
	{
		return sort;
	}
	public void setSort(int sort)
	{
		this.sort = sort;
	}
	public String getLang()
	{
		return lang;
	}
	public void setLang(String lang)
	{
		this.lang = lang;
	}
	public int getBs_id()
	{
		return bs_id;
	}
	public void setBs_id(int bs_id)
	{
		this.bs_id = bs_id;
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
	public void setPublish_date(Date publish_date)
	{
		this.publish_date = publish_date;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}

	public String getCh_QA_type()
	{
		return ch_QA_type;
	}

	public void setCh_QA_type(String ch_QA_type)
	{
		this.ch_QA_type = ch_QA_type;
	}

	public String getEn_QA_type()
	{
		return en_QA_type;
	}

	public void setEn_QA_type(String en_QA_type)
	{
		this.en_QA_type = en_QA_type;
	}
	
	
}
