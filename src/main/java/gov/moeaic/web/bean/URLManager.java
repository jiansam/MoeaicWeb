package gov.moeaic.web.bean;

import java.util.HashMap;
import java.util.Map;

import gov.moeaic.sql.bean.Pages;
import gov.moeaic.sql.dao.PagesDAO;

public class URLManager {
	
	Map<String, String> pages_map = new HashMap<>();
	
	public  URLManager(){
		init();
	}
	
	public void init(){
		pages_map.clear();
		
		for(Pages bean : PagesDAO.list()){
			String type = bean.getType();
			if("apply_online".equalsIgnoreCase(type) || "apply_search".equalsIgnoreCase(type) || "attachedFile".equalsIgnoreCase(type)){
				String name = bean.getType() + "_" + bean.getLanguage(); //apply_online_CH
				pages_map.put(name, bean.getPage_content());
			}
		}
	}
	
	public Map<String, String> getPages_map()
	{
		return pages_map;
	}

	public void setPages_map(Map<String, String> pages_map)
	{
		this.pages_map = pages_map;
	}
	
}
