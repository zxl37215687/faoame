package com.lsr.frame.base.entityconver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InvokeCache {
	private static final Log log = LogFactory.getLog(InvokeCache.class);
    public static Map<String,Class> clazzMap = new HashMap<String,Class>();
    public static Map<String,Object> objMap = new HashMap<String,Object>();
    public static Map<String,Method> methodMap = new HashMap<String,Method>();
    public static Map<String,Map<String,String>> typeMap = new HashMap<String,Map<String,String>>();
    
    /**
     * 通过反射+缓存高效的调用某个类里面的某个方法
     * */
    public static Object cacheExce(String clazz,String method,Object[] os,Class[] cs) throws NoSuchMethodException{
            try {
                int size = 0;
                if(cs!=null){
                    size = cs.length;
                }
                
                Method m = methodMap.get(clazz+"_"+method+"_"+size);//用于区分重载的方法
                Object obj = objMap.get(clazz);
                
                if(m==null||obj==null){
                    Class cl =     clazzMap.get(clazz);
                    if(cl==null){
                        cl = Class.forName(clazz);
                        clazzMap.put(clazz, cl);//缓存class对象
                    }
                    
                    if(obj==null){
                        obj = cl.newInstance();
                        objMap.put(clazz, obj);//缓存对象的实例
                    }
                    
                    if(m==null){
                        m = cl.getMethod(method, cs);
                        methodMap.put(clazz+"_"+method+"_"+size, m);//缓存Method对象
                    }
                }
                return  m.invoke(obj , os);//动态调用某个对象中的public声明的方法
            } catch (Exception e) {
                e.printStackTrace();
                throw new NoSuchMethodException();
            }
        }
    
    
    /**
     * 通过反射+缓存获取指定类里面 的指定方法的返回类型
     * */
    public static  String cacheType(String clazz,String method) throws ClassNotFoundException{
        Map<String,String> clazzs =  typeMap.get(clazz);
        if(clazzs==null){
            Map<String,String> mmap = new HashMap<String, String>();
            Class cl =     Class.forName(clazz);
            Method[] ms = cl.getMethods();//获取某个类里面的所有的公共的方法
            for(Method m:ms){
                mmap.put(m.getName(), m.getGenericReturnType().toString());//遍历出所有的方法，将方法名和返回类型存在静态的map中（缓存）
            }
            clazzs = mmap;
            typeMap.put(clazz, mmap);
        }
        return clazzs.get(method);
    }
}
