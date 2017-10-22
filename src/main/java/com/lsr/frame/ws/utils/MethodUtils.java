package com.lsr.frame.ws.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.lsr.frame.ws.exception.WSRuntimeException;

/**
 * 
 * @author kuaihuolin
 *
 */
public class MethodUtils {

	public static Object invoke(Object object, String methodName, Class[] parameterTypes, Object[] parameterValues) throws InvocationTargetException{
		Class serviceClazz = object.getClass();
		Method method = getPublicMethod(serviceClazz ,methodName, parameterTypes);
		return invoke(object, method, parameterValues);
	}
	
	public static Object forceInvoke(Object object, String methodName, Class[] parameterTypes, Object[] parameterValues)throws InvocationTargetException{
		Class serviceClazz = object.getClass();
		Method method = null;
		try {
			method = serviceClazz.getDeclaredMethod(methodName, parameterTypes);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		method.setAccessible(true);
		Object o = invoke(object, method, parameterValues);
		method.setAccessible(false);
		return o;
	}

	public static Object invoke(Object object, Method method, Object[] paras) throws InvocationTargetException{
		try {
			return method.invoke(object, paras);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new WSRuntimeException(e);
		} 
	}

	public static Method getPublicMethod(Class clazz, String methodName) {
		Method[] allMethods = clazz.getMethods();
		for (int i = 0; i < allMethods.length; i++) {
			if (allMethods[i].getName().equals(methodName)) {
				return allMethods[i];
			}
		}
		throw new WSRuntimeException(clazz.getName() + " no such method " + methodName);
	}
	public static Method getPublicMethod(Class clazz, String methodName, Class[] argusTypes) {
		try{
			return clazz.getMethod(methodName, argusTypes);
		}catch (Exception e) {
			StringBuffer argusTypesName = new StringBuffer();
			if(argusTypes != null){
				for(int i = 0; i < argusTypes.length; i++){
					argusTypesName.append(argusTypes[i].getName()).append(",");
				}
				if(argusTypesName.length() > 0){
					argusTypesName.delete(argusTypesName.length() - 1, argusTypesName.length());
				}
			}
			throw new WSRuntimeException(clazz.getName() + " not find method " + methodName + " with arg " + argusTypesName.toString());
		}
	}
	
	public static String getGetterName(String fieldName){
		String changePropertyName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
		String getterName = "get" + changePropertyName;
		return getterName;
	}
	
	public static String getSetterName(String fieldName){
		String changePropertyName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
		String setterName = "set" + changePropertyName;
		return setterName;
	}
	
	public static Method getGetter(Class clazz, String fieldName){
		return getPublicMethod(clazz, getGetterName(fieldName), null);
	}
	
	public static Method getSetter(Class clazz, String fieldName, Class argusTypes){
		if(argusTypes == null){
			argusTypes = getGetter(clazz, fieldName).getReturnType() ;
		}
		return getPublicMethod(clazz, getSetterName(fieldName), new Class[]{argusTypes});
	}
}
