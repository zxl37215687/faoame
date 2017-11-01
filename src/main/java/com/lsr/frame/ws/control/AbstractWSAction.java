package com.lsr.frame.ws.control;

import com.lsr.frame.ws.common.WSService;
import com.lsr.frame.ws.context.WSContext;
import com.lsr.frame.ws.control.access.User;
import com.lsr.frame.ws.conver.ResultMap;
import com.lsr.frame.ws.utils.WSSessionUtils;

public abstract class AbstractWSAction extends WSService implements WSAction{
	/**
	 * 创建返回结果
	 * 
	 * @return
	 */
	protected ResultMap getResult() {
		return WSContext.getInstance().getCurrentResultMap();
	}

	/**
	 * 获得当前用户
	 * 
	 * @return
	 */
	protected User getCurrentUser() {
		return WSSessionUtils.getLoginUser(getSession());
	}

	/**
	 * 获得登陆账套
	 * 
	 * @return
	 */
	protected String getLoginZT() {
		return WSSessionUtils.getZT(getSession());
	}

	/**
	 * 获得登陆年份
	 * 
	 * @return
	 */
	protected String getLoginYear() {
		return WSSessionUtils.getLoginYear(getSession());
	}
	
	/**
	 * 获得客户端IP
	 * @return
	 */
	protected String getLoginIP(){
		return WSSessionUtils.getLoginIP(getSession());
	}
}
