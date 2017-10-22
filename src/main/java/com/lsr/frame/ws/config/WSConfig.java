package com.lsr.frame.ws.config;


import com.lsr.frame.base.config.Config;

public interface WSConfig extends Config{
	/**
	 * ID
	 * @return
	 */
	String getId();
	
	/**
	 * 类型
	 * @return
	 */
	Class getType();
	
	/**
	 * 全类名
	 * @return
	 */
	String getTypeName();
	
	/**
	 * 根据该配置获得所属对象
	 * @return
	 */
	Object getObject();
	
	/**
	 * 获得父配置
	 * @return
	 */
	WSConfig getParent();
}
