package com.lsr.frame.ws.config.scope;

import com.lsr.frame.ws.control.WSAction;

public interface Scope {

	/**
	 * 获得该范围内WSAction
	 * @param actionId WSAction ID
	 * @param actionClass WSAction class
	 * @return
	 */
	WSAction get(String actionId, Class actionClass);
	
	/**
	 * 获得该范围内WSAction
	 * @param actionId WSAction ID
	 * @return
	 */
	WSAction get(String actionId);
	
	/**
	 * 删除该周期内的WSAction
	 * @param actionId VirtualAction ID 
	 */
	void remove(final String actionId);
	
	/**
	 * 获得该范围的名称
	 * @return
	 */
	String getName();
}
