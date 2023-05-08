package gov.moeaic.web.bean;

import java.util.ArrayList;
import java.util.HashMap;

import gov.moeaic.sql.bean.Option;
import gov.moeaic.sql.dao.OptionDAO;

public class OptionManager {
	
	HashMap<String, ArrayList<Option>> option_list = new HashMap<String, ArrayList<Option>>(); 
	HashMap<String, ArrayList<Option>> option_list_en = new HashMap<String, ArrayList<Option>>(); 
	HashMap<String, HashMap<String, Option>> option_map = new HashMap<String, HashMap<String, Option>>();
	HashMap<String, HashMap<String, Option>> option_map2 = new HashMap<String, HashMap<String, Option>>();
	
	public OptionManager(){
		init();
	}
	
	public void init(){
		option_list.clear();
		option_map.clear();
		
		for(Option option : OptionDAO.list()){
			if(option_list.get(option.getType()) == null){
				option_list.put(option.getType(), new ArrayList<Option>());
			}
			option_list.get(option.getType()).add(option);
			
			if(option_map.get(option.getType()) == null){
				option_map.put(option.getType(), new HashMap<String, Option>());
			}
			option_map.get(option.getType()).put(String.valueOf(option.getId()), option);
			
			if(option.getEname()!=null && option.getEname().length()>0){
				//英文版 List（可以扣除沒有英文的，就是不出英文版）
				if(option_list_en.get(option.getType()) == null){
					option_list_en.put(option.getType(), new ArrayList<Option>());
				}
				option_list_en.get(option.getType()).add(option);
			}
			
			
			if(option_map2.get(option.getType()) == null){
				option_map2.put(option.getType(), new HashMap<String, Option>());
			}
			option_map2.get(option.getType()).put(option.getName(), option);
		}
		
	}
	
	public ArrayList<Option> list(String type){
		return option_list.get(type);
	}
	
	public ArrayList<Option> list_en(String type){
		return option_list_en.get(type);
	}

	public Option get(String type, String id){
		if(option_map.get(type) == null){
			return null;
		}
		return option_map.get(type).get(id);
	}
	
	public Option getEnName(String type, String name){
		if(option_map2.get(type) == null){
			return null;
		}
		return option_map2.get(type).get(name);
	}
	
	public ArrayList<Option> getBusiness_category(){
		return this.list("business_category");
	}
	
	public ArrayList<Option> getBusiness_type(){
		return this.list("business_type");
	}

	public ArrayList<Option> getOpenData_type(){
		return this.list("opendata_type");
	}
	
	
	public ArrayList<Option> getBusiness_category_en(){
		return this.list_en("business_category");
	}
	
	public ArrayList<Option> getBusiness_type_en(){
		return this.list_en("business_type");
	}
	
	public ArrayList<Option> getCh_QA_type(){
		return this.list("ch_QA_type");
	}
	public ArrayList<Option> getEn_QA_type(){
		return this.list("en_QA_type");
	}

}
