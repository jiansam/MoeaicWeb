package gov.moeaic.sql.bean;

import java.io.Serializable;
import java.util.Date;

public class Pages implements Serializable{

	int id;
	String type;
	String page_content;
	String language;
	Date update_date;
	
	
	
	@Override
	public String toString() {
		return "[id=" + id + ", type=" + type + ", language=" + language
				+ ", page_content=" + page_content + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPage_content() {
		return page_content;
	}
	public void setPage_content(String page_content) {
		this.page_content = page_content;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	
	
	
}
