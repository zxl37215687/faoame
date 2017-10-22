package com.lsr.frame.ws.utils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.lsr.frame.base.entityconver.VOInterface;

public class ObjectUtils {
	/**
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isCollection(Object o){
		if(o == null){
			return false;
		}
		if(o instanceof Collection ){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isMap(Object o){
		return o instanceof Map;
	}
	
	/**
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isArray(Object o){
		if(o == null){
			return false;
		}
		return o.getClass().isArray();
	}
	
	/**
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isVO(Object o){
		if(o == null){
			return false;
		}
		return o instanceof VOInterface;
	}
	
	/**
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isVOArray(Object o){
		if(o == null){
			return false;
		}
		return o instanceof VOInterface[];
	}
	
	/**
	 * 如果属于Number,String,Date,Boolean都认为是基础类型
	 * @param o
	 * @return
	 */
	public static boolean isBaseType(Object o){
		if(o == null){
			return false;
		}
		return (o instanceof Number) || (o instanceof String) || (o instanceof Date) || (o instanceof Boolean);
	}
}
