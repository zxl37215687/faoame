package com.lsr.frame.ws.intercept;

import java.util.Map;

import com.lsr.frame.ws.context.SessionConstant;
import com.lsr.frame.ws.context.WSContext;
import com.lsr.frame.ws.context.WSSession;
import com.lsr.frame.ws.context.WSSessionContext;
import com.lsr.frame.ws.control.WSActionInvocation;
import com.lsr.frame.ws.control.access.CommonInfo;
import com.lsr.frame.ws.control.access.User;
import com.lsr.frame.ws.control.access.ZTThread;
import com.lsr.frame.ws.utils.WSSessionUtils;

public class ThreadInterceptor implements Interceptor {


	public void destroy() {
		
	}

	public void init() {
		
	}

	public Map intercept(WSActionInvocation invocation) throws Exception {
		try{
			String  sessionId = WSContext.getInstance().getSessionValidCode();
			WSSession session   = (WSSession) WSSessionContext.getInstance().getSession(sessionId);
			
			String ztId = (String) session.get(SessionConstant.SESSION_ZT);
			String year = (String) session.get(SessionConstant.SESSION_YEAR);
			String sectionId = (WSSessionUtils.getLoginUser(session)).getSectionID();
			String orgId = (WSSessionUtils.getLoginUser(session)).getSingleOrgId();
			String userName = (WSSessionUtils.getLoginUser(session)).getName();
			String loginIP = (String) session.get(SessionConstant.SESSION_LOGIN_IP);
			String userId =(WSSessionUtils.getLoginUser(session)).getId();
			CommonInfo info = new CommonInfo();
			if (ztId != null) {
				info.setZtId(ztId);
				info.setYear(year);
				info.setSectionId(sectionId);
				info.setOrgId(orgId);
				info.setUserName(userName);
				info.setLoginIP(loginIP);
				info.setUserId(userId);
			}
			ZTThread.set(info);

			User loginUser = WSSessionUtils.getLoginUser(session);
			
			String menuId   = invocation.getParameter().getString("menuId");
			loginUser.setChrPosotion(menuId);
			
			return invocation.invoke();
		
		}finally{
			ZTThread.clear();
		}
	}

}
