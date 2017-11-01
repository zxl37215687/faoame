package com.lsr.frame.ws.intercept;

import java.util.Map;

import com.lsr.frame.base.utils.HiberateUtil;
import com.lsr.frame.ws.control.WSActionInvocation;

public class HibernateSessionOpenInterceptor implements Interceptor {

	public void destroy() {
		// TODO Auto-generated method stub
		
	}


	public void init() {
		// TODO Auto-generated method stub
		
	}


	public Map intercept(WSActionInvocation invocation) throws Exception {
		/*try{	
			
			TransHandlerFactory.getInstance().setCurrentSession(HiberateUtil.getSession());
			
			return invocation.invoke();
		}finally{
			SessionProxy session = TransHandlerFactory.getInstance().getSessionProxy();
			if(session.getSession() != null && session.getSession().isOpen()){
				session.setCloseFlag(true);
				session.getSession().close();
			}
			TransHandlerFactory.clear();
		}*/
		return null;
	}
	

}
