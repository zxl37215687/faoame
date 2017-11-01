package com.lsr.frame.ws.intercept;

import java.util.Map;

import com.lsr.frame.ws.control.WSActionInvocation;
/**
 * »·ÈÆÀ¹½ØÆ÷
 */
public abstract class AroundInterceptor implements Interceptor{

	public void destroy() {
		
	}

	public void init() {
		
	}

	public Map intercept(WSActionInvocation invocation) throws Exception{
		
		before(invocation);
		
		Map resultMap = invocation.invoke();
		
		after(invocation, resultMap);
		
		return resultMap;
	}
	
	/**
	 * 
	 * @param invocation
	 */
	protected abstract void before(WSActionInvocation invocation) throws Exception;
	
	/**
	 * 
	 * @param invocation
	 * @param resultMap
	 */
	protected abstract void after(WSActionInvocation invocation, Map resultMap) throws Exception;
	
	

}
