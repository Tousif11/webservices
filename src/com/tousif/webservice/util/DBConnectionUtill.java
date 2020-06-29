package com.tousif.webservice.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DBConnectionUtill {
	
	public static final Properties dbProperties = new Properties();
	private static long modifiedTimestamp = 0L;
	private static final String DB_PROPERTIES_FILE = "config/DB.properties";
	
	public static void loadProperties() {
		System.out.println("Checking Loading Properties");
		File dbFile = new File(DB_PROPERTIES_FILE);
		InputStream input = null;
		long modTS = dbFile.lastModified();
		if(modTS != modifiedTimestamp) {
			modifiedTimestamp = modTS;
			System.out.println("Loading Properties");
		try {
			 input = new FileInputStream(dbFile);
			dbProperties.load(input);
			
		} catch(FileNotFoundException fe){
			throw new RuntimeException("Unable to find Database property "+DB_PROPERTIES_FILE);
		}
		catch (IOException e) {
			throw new RuntimeException("Unable to load Database property "+DB_PROPERTIES_FILE);
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		finally {
			if(input != null) {
				try {
					System.out.println("Closing DB file");
					input.close();
				}catch (IOException e) {
					throw new RuntimeException("Unable to Close Database property "+DB_PROPERTIES_FILE);
			}
			}
		}
		}
	}
	
	
	public static boolean closeConnection(Connection con) {
		 boolean status = false;
		 if(con != null) {
			 try {
				 con.close();
				 status = true;
			 }catch(Exception e) {
				status = false; 
			 }
			 con = null;
		 }
		return status;
	}
	
	public static List<HashMap<String, Object>> getResultSetToList(ResultSet rs){
		
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();
			while(rs.next()) {
				HashMap<String,Object> rowData = new HashMap<String,Object>();
					for(int i = 1; i<=columns; i++) {
						rowData.put(metaData.getColumnName(i), rs.getObject(i));
					}
					list.add(rowData);
			}
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}catch(Exception e) {
				rs = null;
				e.printStackTrace();
				
			}
		}
		
	}
	
	public static void closeStatement(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			}catch(Exception e) {
				stmt = null;
				e.printStackTrace();
				
			}
		}
		
	}
	
}
