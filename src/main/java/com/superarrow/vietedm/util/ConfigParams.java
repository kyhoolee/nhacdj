package com.superarrow.vietedm.util;

import java.util.Properties;


public class ConfigParams {
	public static final String CONFIG_PATH = "/redis.properties";
	
	public static String redis_host = "localhost";
	public static int redis_port = 6379;
	public static String redis_pass = null;
	public static int redis_index = 0;
	
	
	
	public static void load() {
		Properties properties = new Properties();
		try {
			properties.load(ConfigParams.class.getResourceAsStream(CONFIG_PATH));
			
			redis_host = properties.getProperty("redis_host");
			redis_port = Integer.parseInt(properties.getProperty("redis_port"));
			redis_pass = properties.getProperty("redis_pass");
			redis_index = Integer.parseInt(properties.getProperty("redis_index"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
