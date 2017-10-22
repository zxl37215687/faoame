package com.lsr.frame.ws.conver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.lsr.frame.ws.config.WSConfiguration;
import com.lsr.frame.ws.context.WSContext;
import com.lsr.frame.ws.exception.ConvertException;
import com.lsr.frame.ws.utils.ClassUtils;
import com.lsr.frame.ws.utils.MethodUtils;

public abstract class AbstractConvertor implements Convertor{
	private WSConfiguration configuration = WSContext.getInstance().getWSConfiguration();

	protected WSConfiguration getWSConfiguration() {
		return configuration;
	}

	/**
	 * @param clazz
	 */
	protected void checkIsWSVoOrMap(Class clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("arg clazz is null");
		}
		if (ClassUtils.isMap(clazz)) {
			return;
		}
		if (configuration.isWSVO(clazz)) {
			return;
		}
		throw new ConvertException(clazz.getName() + " neither WSVO nor Map");
	}

	/**
	 * 
	 * @param object
	 * @param method
	 * @param argus
	 * @return
	 */
	protected Object invoke(Object object, Method method, Object[] argus) {
		try {
			return MethodUtils.invoke(object, method, argus);
		} catch (InvocationTargetException e) {
			throw new ConvertException(e);
		}
	}
}
