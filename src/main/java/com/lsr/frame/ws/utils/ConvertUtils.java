package com.lsr.frame.ws.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lsr.frame.ws.common.WSConstant;

public class ConvertUtils extends org.apache.commons.beanutils.ConvertUtils{
private static final String ARRAY_SIGN = WSConstant.Split.ARRAY_SIGN;
	
	/**
	 * 
	 * @param o
	 * @return
	 */
	public static String castToString(Object o){
		if( o instanceof Date){
			String dateString = DateUtils.DateTOStr((Date)o);
			if(dateString == null){
				return dateString;
			}
			return dateString;
		}
		if( o == null){
			return "";
		}
		return o.toString();
	}
	
	/**
	 * 
	 * @param o
	 * @return
	 */
	public static String castArrayToString(Object[] o){
		StringBuffer strBuffer = null;
		if( o instanceof Object[]){
			strBuffer = new StringBuffer();
			Object[]     array = (Object[]) o;
			for(int i = 0; i < array.length; i++){
				strBuffer.append( castToString(array[i]) ).append(ARRAY_SIGN);
			}
			if(strBuffer.length() > 0){
				strBuffer.delete(strBuffer.length() - ARRAY_SIGN.length(), strBuffer.length());
			}
		}
		return strBuffer.toString();
	}
	
	/**
	 * 
	 * @param coll
	 * @return
	 */
	public static String castCollectionToString(Collection coll){
		return castArrayToString( coll.toArray() );
	}
	
	/**
	 * 
	 * @param value
	 * @param clazz
	 * @return
	 */
	public static Object[] castToBaseTypeArray(String value, Class clazz){
		if(StringUtils.isEmpty(value)){
			return null;
		}
		String[] valueStrArray = value.split(ARRAY_SIGN);
		Object[] objArray = (Object[]) Array.newInstance(clazz, valueStrArray.length);
		for(int i = 0; i < Array.getLength(objArray); i++){
			objArray[i] = castToBaseType( valueStrArray[i], clazz);
		}
		return objArray;
	}
	
	/**
	 * 
	 * @param value
	 * @param collectionClazz
	 * @return
	 */
	public static Object castToBaseTypeCollection(String value, Class collectionClazz, Class clazz){
		if(clazz == null){
			clazz = String.class;
		}
		Object objCollection = ClassUtils.newInstance(ClassUtils.getCollectionOrMapImplClass(collectionClazz));
		if(StringUtils.isEmpty(value)){
			return objCollection;
		}
		String[] valueStrArray = value.split(ARRAY_SIGN);
		for(int i = 0; i < valueStrArray.length; i++){
			Object oValue = castToBaseType( valueStrArray[i], clazz);
			((Collection) objCollection).add( oValue );
		}
		return objCollection;
	}
	
	/**
	 * 
	 * @param value
	 * @param clazz
	 * @return
	 */
	public static Object castToBaseType(String value, Class clazz){
		if(clazz.equals(Date.class)){
			try{
				return DateUtils.StrToDate(value);
			}catch(Exception ex){
				throw new RuntimeException(ex);
			}
		}
		return ConvertUtils.convert(value, clazz);
	}
}
