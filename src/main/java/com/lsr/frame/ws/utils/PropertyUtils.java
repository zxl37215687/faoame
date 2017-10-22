package com.lsr.frame.ws.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
	private static String FILE_NAME = "/wsconfig/wsconfig.properties";
	private static Properties properties;

	static {
		properties = getProperties(FILE_NAME);
	}

	private PropertyUtils() {
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties getProperties(String fileName) {
		Properties result = new Properties();
		try {
			result.load(PropertyUtils.class.getResourceAsStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e ){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取Properties
	 * @return
	 */
	public static Properties getProperties() {
		
		return properties;
	}
	
	/**
	 * 获取字符串类型的属性
	 * @param name 属性名
	 * @return
	 */
	public static String getProperty(String name) {
		return properties.getProperty(name);
	}

	/**
	 * 获取整型属性
	 * @param name 属性名
	 * @param defaultValue 默认值
	 * @return
	 */
	public static int getIntProperty(String name, int defaultValue) {
		try {
			return Integer.parseInt(getProperty(name));
		} catch (NumberFormatException e) {
			System.err.println(name + "格式错误");
			return defaultValue;
		} catch (NullPointerException e) {
			System.err.println("未找到属性" + name);
			return defaultValue;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return defaultValue;
		}
	}

	/**
	 * 获取长整型属性
	 * @param name 属性名
	 * @param defaultValue 默认值
	 * @return
	 */
	public static long getLongProperty(String name, long defaultValue) {
		try {
			return Long.parseLong(getProperty(name));
		} catch (NumberFormatException e) {
			System.err.println(name + "格式错误");
			return defaultValue;
		} catch (NullPointerException e) {
			System.err.println("未找到属性" + name);
			return defaultValue;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return defaultValue;
		}
	}
}
