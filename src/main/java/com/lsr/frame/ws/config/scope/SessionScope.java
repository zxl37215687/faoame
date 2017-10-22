package com.lsr.frame.ws.config.scope;

import java.util.HashMap;
import java.util.Map;

import com.lsr.frame.ws.context.WSContext;

public class SessionScope extends AbstractScope {
	private Map sessionVirtualActionContext = new HashMap();

	protected Map getVirtualActionContext() {
		String sessionId = WSContext.getInstance().getSessionValidCode();
		if(sessionVirtualActionContext.get(sessionId) == null){
			sessionVirtualActionContext.put(sessionId, new HashMap());
		}
		return (Map) sessionVirtualActionContext.get(sessionId);
	}

	public String getName() {
		return "session";
	}
	
	/**
	 * 删除该session范围内的所有WSAction
	 * @param sessionId
	 */
	public void clearAllWSAction(String sessionId){
		sessionVirtualActionContext.remove(sessionId);
	}
}
