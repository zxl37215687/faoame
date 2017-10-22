package com.lsr.frame.ws.conver;

import java.util.HashMap;
import java.util.Map;

import com.lsr.frame.ws.common.WSConstant;
import com.lsr.frame.ws.utils.ClassUtils;

/**
 * 转换器工厂类
 * @author kuaihuolin
 *
 */
public abstract class ConvertorFactory implements Convertor{
	public static Map convertorPool = new HashMap();
	
	/**
	 * 
	 * @param convertorClass
	 * @return
	 */
	public static Convertor getConvertor(Class convertorClass){
		if(!Convertor.class.isAssignableFrom(convertorClass)){
			throw new ClassCastException(ConvertorFactory.class.getName() + " not assignable from " + convertorClass.getName());
		}
		synchronized (convertorClass) {
			Convertor convertor = (Convertor) convertorPool.get(convertorClass);
			if(convertor == null){
				convertor = (Convertor) ClassUtils.newInstance(convertorClass);
				convertorPool.put(convertorClass, convertor);
			}
			return convertor;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public static Convertor getDefaultConvertor(){
		return getConvertor(WSConstant.DEFAUT_CONVERTOR);
	}
}
