package com.lsr.frame.ws.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lsr.frame.ws.common.WSConstant;
import com.lsr.frame.ws.context.WSContext;
import com.lsr.frame.ws.exception.WSConfigException;
import com.lsr.frame.ws.utils.ClassUtils;

/**
 * 监听器启动类
 * @author kuaihuolin
 *
 */
public class WSContextLoadedListener implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event) {
		String wSConfigLocation = event.getServletContext().getInitParameter(WSContextConfigConstants.PARA_NAME_WSCONFIGLOCATION);
		if(wSConfigLocation == null){
			wSConfigLocation = WSContextConfigConstants.DEFAULT_WSCONFIGLOCATION;
		}
		String wsConfigLocation = event.getServletContext().getInitParameter(WSContextConfigConstants.PARA_NAME_WSCONFIGLOCATION);
		if(wsConfigLocation == null){
			wsConfigLocation = WSConstant.DEFAULT_CONFIGURACTION.getName(); 
		}
		Object configuractionObject = ClassUtils.newInstance(wsConfigLocation);
		if(!(configuractionObject instanceof WSConfiguration)){
			throw new WSConfigException("wsConfigLocation not implement " + WSConfiguration.class.getName());
		}
		
		WSConfiguration configuration = ((WSConfiguration) configuractionObject).config(wSConfigLocation);
		WSContext.getInstance().setWSConfiguration( configuration );
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		if(WSContext.getInstance().getWSConfiguration() != null){
			WSContext.getInstance().getWSConfiguration().clear();
		}
	}
}
