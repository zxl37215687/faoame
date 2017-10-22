package com.lsr.frame.ws.config.scope;

import java.util.Map;

import com.lsr.frame.ws.config.WSActionConfig;
import com.lsr.frame.ws.context.WSContext;
import com.lsr.frame.ws.control.WSAction;
import com.lsr.frame.ws.utils.ClassUtils;

public abstract class AbstractScope implements Scope{
	public WSAction get(String actionId) {
		WSActionConfig vaConfig = WSContext.getInstance().getWSConfiguration().getWSActionConfig(actionId);
		return get(actionId, vaConfig.getType());
	}
	
	public WSAction get(String actionId, Class actionClass){
		Map context = getVirtualActionContext();
		WSAction virtualAction = (WSAction) context.get(actionId);
		if (virtualAction == null) {
			virtualAction = (WSAction) ClassUtils.newInstance(actionClass);
			context.put(actionId, virtualAction);
		}
		return virtualAction;
	}
	
	public void remove(final String actionId){
		getVirtualActionContext().remove(actionId);
	}
	
	protected abstract Map getVirtualActionContext();

}
