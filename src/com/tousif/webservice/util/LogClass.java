package com.tousif.webservice.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class LogClass {
	
	public static final Logger log = Logger.getLogger(LogClass.class.getName());
	private static final String LOG_PROPERTIES_FILE ="config/log4J.properties";
	private static boolean loaded = false;
	
	static {
		if(!LogClass.loaded) {
			Properties logProperties = new Properties();
			try {
				logProperties.load(new FileInputStream(LOG_PROPERTIES_FILE));
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
			LogClass.loaded=true;
		}
	}

}
