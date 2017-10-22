package com.lsr.frame.ws.utils;

import org.apache.commons.lang3.StringUtils;

import com.lsr.frame.ws.common.WSConstant;

/**
 * 
 * @author kuaihuolin
 *
 */
public class StringParser {
	public static boolean matches(String str, String regex){
		if(StringUtils.isEmpty(str)){
			throw new IllegalArgumentException("str is null");
		}
		if(StringUtils.isEmpty(regex)){
			throw new IllegalArgumentException("regex is null");
		}
		final String all_sign = "*";
		final String all_sign_split = "\\" + all_sign;
		
		String[] regexArray = regex.split(all_sign_split);
		if(regexArray.length > 2){
			throw new IllegalArgumentException("regex format error");
		}
		
		if(regex.indexOf(all_sign) >= 0){
			if(regex.equals(all_sign)){
				return true;
			}
			if(regex.startsWith(all_sign)){
				return str.endsWith(regexArray[1]);
			}
			if(regex.endsWith(all_sign)){
				return str.startsWith(regexArray[0]);
			}
			return str.startsWith(regexArray[0]) && str.endsWith(regexArray[1]);
		}
		
		return str.equals(regex);
	}
	
	public static boolean isArrayString(String str){
		return str.indexOf(WSConstant.Split.ARRAY_SIGN) >= 0;
	}
	
	public static boolean isVOString(String str){
		return str.indexOf(WSConstant.Split.EDIT_FIELD_SEPARATOR) >= 0 
					&& str.indexOf(WSConstant.Split.EDIT_FIELD_VALUE_SEPARATOR) >= 0;  
	}
	
	public static boolean isVOArrayString(String str){
		return isVOString(str) && str.equals( WSConstant.Split.ARRAY_SIGN);
	}
}
