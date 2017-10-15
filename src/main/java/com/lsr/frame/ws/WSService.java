package com.lsr.frame.ws;

import com.lsr.frame.Service;


/**
 * WebService
 * @author kuaihuolin
 *
 */
public abstract class WSService implements Service {
	
	/**
	 * 获取WS会话
	 * @return
	 */
	protected WSSession getSession(){
		return null;
	}
	
	/**
	 * 获取客户端IP
	 * @return
	 */
	protected String getClientAddr(){
		return "";
	}
}
