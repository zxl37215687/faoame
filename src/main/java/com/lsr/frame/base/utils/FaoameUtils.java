package com.lsr.frame.base.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.lsr.frame.ws.utils.PropertyUtils;

/**
 * faoame属性工具类
 * @author kuaihuolin
 *
 */
public class FaoameUtils {
	private static String FILE_NAME = "/faoame.properties";
	private static Properties properties;

	static {
		properties = getProperties(FILE_NAME);
	}

	private FaoameUtils() {
	}
	
	public static String getSystemName(){
		String systemName = "";
		return systemName;
	}
	
	public static String getMaxAndMinYear(){
		String yearString = "";
		return yearString;
	}
	
	public static List getYearList(List years){
		String yearString = getMaxAndMinYear();
		int minYear = Integer.parseInt(yearString.split(",")[1]);
		int maxYear = Integer.parseInt(yearString.split(",")[0]);
		int falgYear = 0;
		Iterator it =  years.iterator();
		List list = new ArrayList(); 
		while(it.hasNext()){
			falgYear = Integer.parseInt((String)it.next());
			if(falgYear>=minYear&&falgYear<=maxYear){
				list.add(String.valueOf(falgYear));
			}
		}
		
		return list;
	}
	
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
	
	public static Properties getProperties() {
		return properties;
	}
	
	public static String getProperty(String name) {
		return properties.getProperty(name);
	}

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
	
	public final static class YesOrNo{
		public final static String VALUE_YES = "1";
		public final static String VALUE_NO = "0";
		
		public static boolean isYes(String name){
			return VALUE_YES.equals(getProperty(name));
		}
	}
}
