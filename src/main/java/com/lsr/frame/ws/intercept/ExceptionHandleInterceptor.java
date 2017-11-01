package com.lsr.frame.ws.intercept;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsr.frame.ws.control.WSActionInvocation;
import com.lsr.frame.ws.exception.ExceptionHandler;
public class ExceptionHandleInterceptor implements Interceptor {

	private static final Log LOG = LogFactory.getLog(ExceptionHandleInterceptor.class);
	
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public Map intercept(WSActionInvocation invocation) throws Exception {
		try{
			return invocation.invoke();
		}catch(Throwable ex){
			Map exceptionMap = ExceptionHandler.convertException(ex);
			ExceptionHandler.logException(ex);
			return exceptionMap;
		}

	}

}
