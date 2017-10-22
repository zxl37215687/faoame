package com.lsr.frame.base.entityconver;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsr.frame.ws.conver.ConvertorFactory;
import com.lsr.frame.ws.utils.ClassUtils;

/**
 * 
 * @author kuaihuolin
 *
 */
public class EntityConverFactory {
	private static final Log log = LogFactory.getLog(EntityConverFactory.class);
	private static final Class DEFAUT_CONVERTOR = DefaultEntityConvertor.class;
	
	
	public static Map convertorPool = new HashMap();
	
	/**
	 * 
	 * @param convertorClass
	 * @return
	 */
	public static EntityConver getConvertor(Class convertorClass){
		if(!EntityConver.class.isAssignableFrom(convertorClass)){
			throw new ClassCastException(ConvertorFactory.class.getName() + " not assignable from " + convertorClass.getName());
		}
		synchronized (convertorClass) {
			EntityConver convertor = (EntityConver) convertorPool.get(convertorClass);
			if(convertor == null){
				convertor = (EntityConver) ClassUtils.newInstance(convertorClass);
				convertorPool.put(convertorClass, convertor);
			}
			return convertor;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static EntityConver getDefaultConvertor(){
		return getConvertor(DEFAUT_CONVERTOR);
	}
	
	
}
