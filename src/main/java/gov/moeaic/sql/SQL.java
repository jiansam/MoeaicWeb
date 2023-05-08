package gov.moeaic.sql;

import org.dasin.tools.SQLTools;

public class SQL extends SQLTools{
	public SQL(){
		super(SQLTools.Tomcat, "jdbc/moea");
	}
}
