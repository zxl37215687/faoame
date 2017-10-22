package com.lsr.frame.ws.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lsr.frame.base.entityconver.BaseVO;
import com.lsr.frame.base.entityconver.VOInterface;

public class ClassUtils {
	/**
	 * 
	 * @param className
	 * @return
	 */
	public static Class forName(String className){
		try{
			return Class.forName(className);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 
	 * @param className
	 * @param initialize
	 * @param classLoader
	 * @return
	 */
	public static Class forName(String className, boolean initialize, ClassLoader classLoader){
		try{
			return Class.forName(className, initialize, classLoader);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 
	 * @param className
	 * @return
	 */
	public static Object newInstance(String className){
		return newInstance( forName(className) );
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static Object newInstance(Class clazz){
		try{
			return clazz.newInstance();
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 
	 * @param iCollectionClass
	 * @return
	 */
	public static boolean isCollection(Class iCollectionClass){
		if(iCollectionClass == null){
			return false;
		}
		if(Collection.class.isAssignableFrom(iCollectionClass)){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param iMapClass
	 * @return
	 */
	public static boolean isMap(Class iMapClass){
		return Map.class.isAssignableFrom(iMapClass);
	}
	
	/**
	 * 
	 * @param iArrayClass
	 * @return
	 */
	public static boolean isArray(Class iArrayClass){
		if(iArrayClass == null){
			return false;
		}
		return iArrayClass.isArray();
	}
	
	/**
	 * 
	 * @param iCollectionClass
	 * @return
	 */
	public static boolean isVO(Class voClass){
		if(voClass == null){
			return false;
		}
		return VOInterface.class.isAssignableFrom(voClass);
	}
	
	/**
	 * 
	 * @param voClass
	 * @return
	 */
	public static boolean isVOArray(Class voClass){
		if(voClass == null){
			return false;
		}
		return VOInterface[].class.isAssignableFrom(voClass) ;
	}
	
	/**
	 * 
	 * @param voClass
	 * @return
	 */
	public static boolean isVOInterface(Class voClass){
		if(voClass == null){
			return false;
		}
		return voClass.equals( VOInterface.class );
	}
	
	/**
	 * 
	 * @param voClass
	 * @return
	 */
	public static boolean isBaseVO(Class voClass){
		if(voClass == null){
			return false;
		}
		return voClass.equals(BaseVO.class);
	}
	
	/**
	 * 
	 * @param arrayClass
	 * @return
	 */
	public static Class getArrayElementClass(Class arrayClass){
		if(arrayClass == null){
			return null;
		}
		if(!arrayClass.isArray()){
			return arrayClass;
		}
		return arrayClass.getComponentType();
	}
	
	/**
	 * 
	 * @param elementClass
	 * @return
	 */
	public static Class getArrayClass(Class elementClass){
		if(elementClass == null){
			return null;
		}
		if(elementClass.isArray()){
			return elementClass;
		}
		return Array.newInstance(elementClass, 0).getClass();
	}
	
	/**
	 * 
	 * @param iCollectionClass
	 * @return
	 */
	public static Class getCollectionOrMapImplClass(Class iCollectionClass){
		if(iCollectionClass == null){
			throw new IllegalArgumentException("iCollectionClass is null");
		}
		if(!iCollectionClass.isInterface()){
			return iCollectionClass;
		}
		if(iCollectionClass.isAssignableFrom(List.class)){
			return ArrayList.class;
		}
		if(iCollectionClass.isAssignableFrom(Set.class)){
			return HashSet.class;
		}
		if(iCollectionClass.isAssignableFrom(Collection.class)){
			return ArrayList.class;
		}
		if(iCollectionClass.isAssignableFrom(Map.class)){
			return HashMap.class;
		}
		throw new IllegalArgumentException("iCollectionClass is not a map or collection");
	}
}
