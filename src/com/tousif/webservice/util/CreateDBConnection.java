package com.tousif.webservice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDBConnection {
	
	String dbDriver = null;
	String dbURL = null;
	String dbUsername = null;
	String dbPassword = null;
	public Connection getConnection() {
		Connection con =null;
		try {
			DBConnectionUtill.loadProperties();
				dbDriver = DBConnectionUtill.dbProperties.getProperty("db.jdbc-driver");
				dbURL = DBConnectionUtill.dbProperties.getProperty("db.jdbc-url");
				dbUsername = DBConnectionUtill.dbProperties.getProperty("db.owner.user");
				dbPassword = DBConnectionUtill.dbProperties.getProperty("db.owner.password");
			
			Class.forName(dbDriver);
			con = DriverManager.getConnection(dbURL,dbUsername,dbPassword);
			con.setAutoCommit(false);
			
		}catch(ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		catch(SQLException e2) {
			e2.printStackTrace();
		}
		catch(Exception e3) {
			e3.printStackTrace();
		}	
		return con;
				
	}
	

}
