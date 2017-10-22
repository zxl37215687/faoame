package com.lsr.frame.ws.config.scope;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author kuaihuolin
 *
 */
public class RequestScope extends AbstractScope {
	private ThreadLocal requestVirtualActionContext = new ThreadLocal();
	
	protected Map getVirtualActionContext() {
		if(requestVirtualActionContext.get() == null){
			requestVirtualActionContext.set(new HashMap());
		}
		return (Map)requestVirtualActionContext.get();
	}
	
	
	public void remove(String actionId) {
		getVirtualActionContext().clear();
		requestVirtualActionContext.set(null);
	}


	public String getName() {
		return "request";
	}
}
