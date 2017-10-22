package com.lsr.frame.ws.config;

import java.io.InputStream;
import java.util.Collection;

public interface WSConfiguration {
	/**
	 * 解释filePath内容
	 * @param filePath
	 * @return
	 */
	WSConfiguration config(String filePath);
	
	/**
	 * 解释fileSource内容
	 * @param fileSource
	 * @return
	 */
	WSConfiguration config(InputStream fileSource);
	
	/**
	 * 获得WSAction配置
	 * @param id
	 * @return
	 */
	WSActionConfig getWSActionConfig(String id);
	
	/**
	 * 获得全局拦截器
	 * @return
	 */
	Collection getAllGlobalWSInterceptorConfig();
	
	/**
	 * 获得拦截器配置
	 * @param ID
	 * @return
	 */
	WSInterceptorConfig getWSInterceptorConfig(String id);
	
	/**
	 * 获得拦截器配置
	 * @param interceptorClazz
	 * @return
	 */
	WSInterceptorConfig getWSInterceptorConfig(Class interceptorClazz);
	
	/**
	 * 获得WSVOConfig配置
	 * @param id
	 * @return
	 */
	WSVOConfig getWSVOConfig(String id);
	
	/**
	 * 获得WSVOConfig配置
	 * @param voClazz
	 * @return
	 */
	WSVOConfig getWSVOConfig(Class voClazz);
	
	/**
	 * 是否已经配置的WSVO
	 * @param voClazz
	 * @return
	 */
	boolean isWSVO(Class voClazz);
	
	/**
	 * 是否已经配置的WSVO
	 * @param object
	 * @return
	 */
	boolean isWSVO(Object object);
	
	/**
	 * 清除配置内容
	 */
	void clear();
}
