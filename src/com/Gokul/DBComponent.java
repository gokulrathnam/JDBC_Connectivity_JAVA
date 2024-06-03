package com.Gokul;

import java.sql.*;
import java.util.*;
public class DBComponent {
	public HashMap getReturnValues(String Query) {
		String ColumnValues = "";
		HashMap hmRes = new HashMap();

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;

		int HashCount = 0;
		try {
			con = DBGetConnection.reSolveConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(Query);
			rsmd = rs.getMetaData();

			while (rs.next()) {
				ArrayList ColValues = new ArrayList();
				HashCount++;
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					ColumnValues = rs.getString(rsmd.getColumnName(i));
					if (ColumnValues != null)
						ColValues.add(ColumnValues);
					else
						ColValues.add("");
				}
				hmRes.put(Integer.toString(HashCount), ColValues);
			}
		} catch (Exception e) {
			System.out.println("GetConnection Exception----> " + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException sqlException) {
				System.out.println("SQLException For Closing a ResultSet--> " + sqlException);
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException sqlException) {
				System.out.println("SQLException For Closing a StateMent--> " + sqlException);
			}
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqlException) {
				System.out.println("SQLException For Closing a Connection--> " + sqlException);
			}
		}
		return hmRes;
	}
	public String exeFunc(String FuncName,ArrayList InParameter,ArrayList OutParameter) {
		String Result = "NO_RESULT";
		String Query = "";
		
		Connection con = null;
		CallableStatement cStmt = null;
		
		int iInParamSize = InParameter.size();
		int iOutParamSize = OutParameter.size();
		int iCount = 1;
		int iCnt = 0;
		int iSetCount = 2;
		
		if(InParameter==null)
			return Result;
		if(InParameter.size()<=0)
			return Result;
		if(OutParameter==null)
			return Result;
		if(FuncName ==null||FuncName.equals(""))
			return Result;
		
		try {
			con = DBGetConnection.reSolveConnection();
			Query = "{?=call"+FuncName+"(";
			for(int i=1;i<=(iInParamSize+iOutParamSize);i++) {
				Query += "?";
			}
			Query = Query.substring(0, Query.lastIndexOf(","));
			Query +=")}";
			
			cStmt = con.prepareCall(Query);
			cStmt.registerOutParameter(1, Types.VARCHAR);
			
			if(iOutParamSize>0) {
				while (iCount<=iOutParamSize) {
					cStmt.registerOutParameter(iInParamSize+iCount+1, Types.VARCHAR);
					iCount++;
				}
			}
			
			for(iCnt=0;iCnt<iInParamSize;iCnt++) {
				cStmt.setString(iSetCount, (String)InParameter.get(iCnt));
				iSetCount++;
			}
			
			if(cStmt.executeUpdate()<=0) {
				System.out.println("******Failed To Process in Execute PLSQL*******");
				cStmt.close();
				return Result;
			}
			
			Result = cStmt.getString(1);
		}
		
		catch (Exception e) {
			System.out.println("Cannot Process ExCeption in executeFunction-----> "+e);
		}
		return Result;
	}
}
