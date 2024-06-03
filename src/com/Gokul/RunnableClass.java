package com.Gokul;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class RunnableClass {

	public static void main(String[] args) throws Exception, SQLException {
		HashMap hm = new HashMap();
		DBComponent dbc = new DBComponent();
		String Query = "select PPF_ACCT_NUM from gpah where is_deleted='Y' and rownum<=100";
		hm = dbc.getReturnValues(Query);
		System.out.println(hm);
	}
}
