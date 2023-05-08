package gov.moeaic.sql.bean;

public class CountryCode
{

	String en_name;
	String ch_name;
	String code;
	
	
	
	@Override
	public String toString()
	{
		return "CountryCode [en_name=" + en_name + ", ch_name=" + ch_name + ", code=" + code + "]";
	}
	public String getEn_name()
	{
		return en_name;
	}
	public void setEn_name(String en_name)
	{
		this.en_name = en_name;
	}
	public String getCh_name()
	{
		return ch_name;
	}
	public void setCh_name(String ch_name)
	{
		this.ch_name = ch_name;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	
	
}
