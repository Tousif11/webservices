package com.tousif.webservice.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class LogClass {
	
	public static final Logger log = Logger.getLogger(LogClass.class.getName());
	private static final String LOG_PROPERTIES_FILE ="config/log4J.properties";
	private static boolean loaded = false;
	
	static {
		if(!LogClass.loaded) {
			System.out.println("Opening log file");
			Properties logProperties = new Properties();
			InputStream input = null;
			try {
				input = new FileInputStream(LOG_PROPERTIES_FILE);
				logProperties.load(input);
				System.out.println(logProperties.getProperty("tousif.test"));
				PropertyConfigurator.configure(logProperties);
				log.info("***********Logging Initialized**********");
			} catch(FileNotFoundException fe){
				throw new RuntimeException("Unable to find Logging property "+LOG_PROPERTIES_FILE);
			}
			catch (IOException e) {
				throw new RuntimeException("Unable to load Logging property "+LOG_PROPERTIES_FILE);
			} catch(Exception e1) {
				e1.printStackTrace();
			}
			finally {
				if(input != null) {
					try {
						System.out.println("Closing log file");
						input.close();
					}catch (IOException e) {
						throw new RuntimeException("Unable to Close Logging property "+LOG_PROPERTIES_FILE);
				}
			}
			}
			LogClass.loaded=true;
		}
	}

}
