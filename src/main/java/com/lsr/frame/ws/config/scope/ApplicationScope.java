package com.lsr.frame.ws.config.scope;

import java.util.HashMap;
import java.util.Map;

import com.lsr.frame.ws.control.WSAction;

/**
 * 
 * @author kuaihuolin
 *
 */
public class ApplicationScope extends AbstractScope {
	private Map singleVirtualActionContext = new HashMap();

	public WSAction get(String actionId, Class actionClass) {
		synchronized (this) {
			return super.get(actionId, actionClass);
		}
	}
	
	protected Map getVirtualActionContext() {
		return singleVirtualActionContext;
	}

	public String getName() {
		return "application";
	}

}
