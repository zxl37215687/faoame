package com.lsr.frame.ws.common;

import com.lsr.frame.base.Service;
import com.lsr.frame.base.Session;
import com.lsr.frame.ws.context.WSContext;
import com.lsr.frame.ws.context.WSSessionContext;


/**
 * WebService
 * @author kuaihuolin
 *
 */
public abstract class WSService implements Service {
	
	/**
	 * 获取会话
	 * @return
	 */
	public Session getSession(){
		return WSSessionContext.getInstance().getCurrentSession();
	}
	
	/**
	 * 获取客户端IP
	 * @return
	 */
	public String getClientAddr(){
		return "";
	}
}
