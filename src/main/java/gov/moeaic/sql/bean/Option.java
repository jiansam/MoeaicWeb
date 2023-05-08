package gov.moeaic.sql.bean;

import java.io.Serializable;

public class Option implements Serializable, Cloneable{
	private static final long serialVersionUID = 1L;
	int id = 0;
	String name;
	String ename;
	String value;
	String type;
	int seq = 99;
	boolean enable;
	
	

	@Override
	public String toString()
	{
		return "Option [id=" + id + ", name=" + name + ", ename=" + ename + ", value=" + value + ", type=" + type
				+ ", seq=" + seq + ", enable=" + enable + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
}