package gov.moeaic.sql.bean;


public class Business_category
{
	int id;
	String year;
	double amount;
	String type;
	String type_en;
	int seq;
	
	@Override
	public String toString()
	{
		return "["+ id + ", " + year + ", " + amount + ", " + type + "]";
	}

	
	public String getType_en()
	{
		return type_en;
	}

	public void setType_en(String type_en)
	{
		this.type_en = type_en;
	}

	public int getSeq()
	{
		return seq;
	}

	public void setSeq(int seq)
	{
		this.seq = seq;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getYear()
	{
		return year;
	}

	public void setYear(String year)
	{
		this.year = year;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
	
	
}
