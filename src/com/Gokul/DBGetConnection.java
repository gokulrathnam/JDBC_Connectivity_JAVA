package com.Gokul;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
//import javax.sql.DataSource;
import java.sql.*;

public class DBGetConnection {
	//private static DataSource ds;

	public static java.sql.Connection reSolveConnection() throws IOException, SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection dbConnection = null;
		Properties fPro = new Properties();

		FileInputStream find = new FileInputStream("C:/Users/Gokul/eclipse-workspace/DB2/GetConnection.properties");
		fPro.load(find);
		String UserId = fPro.getProperty("UserName").toUpperCase();
		String URL = fPro.getProperty("DSNName");

		try {
			Context ctx = new InitialContext();
			//ds = (DataSource) ctx.lookup("java:comp/env/jdbc/test");
			ctx.close();

		} catch (NamingException e) {

			System.out.println("Class Not Found exception during getConnection" + e);
		} finally {
			fPro = null;
			try {
				if (find != null) {
					find.close();
					find = null;
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		//dbConnection = ds.getConnection("GBM", "gbm");
		if (dbConnection == null) {
			Scanner sc = new Scanner(System.in);
			System.out.println("*************************************************************");
			System.out.println("************************-------------************************");
			System.out.println("***********************|List of Banks|***********************");
			System.out.println("***********************|    BOIDEV   |***********************");
			System.out.println("***********************|    IOBLIVE  |***********************");
			System.out.println("***********************|    CBIUAT   |***********************");
			System.out.println("************************-------------************************");
			System.out.println("*************************************************************");
			String bnkname = sc.next();
			switch (bnkname) {
			case "BOIDEV":
				//dbConnection = DriverManager.getConnection("jbdc:oracle:thin:@10.54.6.10:2066:BOIDEV", UserId, "xxx");
				dbConnection = DriverManager.getConnection(URL, UserId, "xxx");
				if (dbConnection != null)
					System.out.println("***Connected to BOIDEV***");
				break;
			case "IOBLIVE":
				dbConnection = DriverManager.getConnection("jdbc:oracle:thin:@10.54.6.8:3322:IOBLIVE", UserId, "xxx");
				if (dbConnection != null)
				System.out.println("***Connected to IOBIVE***");
				break;
			case "CBIUAT":
				dbConnection = DriverManager.getConnection("jdbc:oracle:thin:@10.54.6.51:2021:CBIUATDB",UserId,"xxx");
				if (dbConnection != null)
				System.out.println("***Connected to CBIUAT***");
				break;
			default:
				System.out.println("***Cannot Resolve to DB***");
				break;
			}
			sc.close();
		}
		return dbConnection;
	}
}
