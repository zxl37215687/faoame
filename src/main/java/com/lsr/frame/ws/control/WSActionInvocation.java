package com.lsr.frame.ws.control;

import java.util.Iterator;
import java.util.Map;

import com.lsr.frame.base.control.ActionInvocation;
import com.lsr.frame.ws.conver.Parameters;

public interface WSActionInvocation extends ActionInvocation{
	/**
	 * 获得WSAction方法参数
	 * @return
	 */
	Parameters getParameter();
	
	/**
	 * 获得WSAction
	 * @return
	 */
	Object getWSAction();
	
	/**
	 * 获得WSActionID
	 * @return
	 */
	String getWSActionId();
	
	/**
	 * 获得WSAction调用方法
	 * @return
	 */
	String getMethod() ;
	
	/**
	 * 执行WSAction调用方法
	 * @return 执行后返回Map
	 */
	Map invoke()throws Exception;
	
	/**
	 * 
	 * @return
	 */
	public boolean isExecuted();
	
	/**
	 * 获得拦截器
	 * @return
	 */
	Iterator getInterceptorIterator() ;
	
	/**
	 * 设定拦截器
	 * @param interceptorIterator
	 */
	void setInterceptorIterator(Iterator interceptorIterator) ;

	/**
	 * 获得执行时的异常
	 * @return
	 */
	Exception getException();
}
