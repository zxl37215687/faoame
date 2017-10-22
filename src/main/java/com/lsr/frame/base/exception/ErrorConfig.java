package com.lsr.frame.base.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ErrorConfig {
	private ErrorConfig() {}
	private static String configFile = "/error.properties";
	private static Properties properties = null;

	static {
		try {
			loadProperties();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	private static void loadProperties() throws IOException {
		InputStream in = ErrorConfig.class.getResourceAsStream(configFile);
		properties = new Properties();
		properties.load(in);
		in.close();

	}

	public static String getProperty(String name) {
		return properties.getProperty(name);
	}
}
