package com.lsr.frame.ws.intercept;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsr.frame.ws.context.WSContext;
import com.lsr.frame.ws.context.WSSession;
import com.lsr.frame.ws.context.WSSessionContext;
import com.lsr.frame.ws.control.WSActionInvocation;
import com.lsr.frame.ws.utils.WSSessionUtils;

public class SessionValidateInterceptor implements Interceptor {
	
	private static final Log log = LogFactory.getLog(SessionValidateInterceptor.class);
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public Map intercept(WSActionInvocation invocation) throws Exception {
		String  sessionId = WSContext.getInstance().getSessionValidCode();
		
		WSSession session   = (WSSession) WSSessionContext.getInstance().getSession(sessionId);
		Exception exception  = WSSessionUtils.validate(session);
		if(exception != null){
			throw exception;
		}
		return invocation.invoke();
	}

}
