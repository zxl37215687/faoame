package com.lsr.frame.ws.intercept;

import java.util.Map;

import com.lsr.frame.ws.control.WSActionInvocation;
import com.lsr.frame.ws.conver.Parameters;

public class SecurityInterceptor extends AroundInterceptor {

	protected void before(WSActionInvocation invocation) throws Exception {
		Parameters para = invocation.getParameter();
	}
	
	protected void after(WSActionInvocation invocation, Map resultMap) throws Exception {
//		System.out.println(resultMap);
	}

}
