package gov.moeaic.sql.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import gov.moeaic.sql.bean.CountryCode;
import gov.moeaic.sql.bean.LogAccess;
import gov.moeaic.sql.bean.LogAccessIP;
import gov.moeaic.sql.bean.LogAccessIPXCounrty;

public class LogAccessService
{
	public Date getLastLogDate() {
		return LogAccessDAO.getLastLogDate();
	}
	
	public void insert(ArrayList<LogAccess> list) {
		LogAccessDAO.insert(list);
	}
	
	public Map<String, ArrayList<LogAccess>> getNewsIdSum(){
		return LogAccessDAO.getNewsIdSum();
	}
	
	public Map<String, Integer> getSum(){
		return LogAccessDAO.getSum();
	}
	
	public Map<String, Date> getLogPeriod(){
		return LogAccessDAO.getLogPeriod();
	}
	
	public ArrayList<String> getExcludeIP(){
		return LogAccessDAO.getExcludeIP();
	}
	
	
	public void insertIP(ArrayList<LogAccessIP> list) {
		LogAccessIPDAO.insert(list);
	}
	
	
	public void insertIPXCountry(ArrayList<LogAccessIPXCounrty> list) {
		LogAccessIPXCountryDAO.insert(list);
	}
	
	
	public Map<String, CountryCode> getCountryCode(){
		return CountryCodeDAO.get();
	}
	
	public Map<String, ArrayList<LogAccessIPXCounrty>> getLogSum(){
		return LogAccessIPDAO.getSum();
	}
	
	public ArrayList<LogAccessIPXCounrty> getCountrySLogCount(String logPage, String logPage_type){
		return LogAccessIPXCountryDAO.getCountrySLogCount(logPage, logPage_type);
	}

	public ArrayList<LogAccessIPXCounrty> getOneCountryLogCount(String logPage, String logPage_type, String country)
	{
		return LogAccessIPXCountryDAO.getOneCountryLogCount(logPage, logPage_type, country);
	}

	public ArrayList<LogAccessIPXCounrty> getIndexOneCountryLogCount(String logPage, String language, String country)
	{
		return LogAccessIPXCountryDAO.getIndexOneCountryLogCount(logPage, language, country);
	}
	
}
