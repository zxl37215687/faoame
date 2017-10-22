package com.lsr.frame.ws.context;

import java.util.HashMap;
import java.util.Map;

import com.lsr.frame.base.context.Context;
import com.lsr.frame.base.utils.StringUtils;
import com.lsr.frame.ws.config.WSConfiguration;
import com.lsr.frame.ws.conver.ResultMap;
import com.lsr.frame.ws.exception.WSConfigException;

/**
 * WS上下文
 * 
 * @author kuaihuolin
 * 
 */
public class WSContext implements Context {
	private static volatile WSContext instance;
	
	private static final ThreadLocal<Map<String, Object>> THREAD_CONTEXT = new ThreadLocal<Map<String, Object>>();

	private static final Map<String, Object> STATIC_CONTEXT = new HashMap<String, Object>();

	private static final String SESSION_VALID_CODE = "sessionValidCode";

	private static final String CONFIG_INFO = "configInfo";

	private static final String CURRENTRESULTMAP = "currentResultMap"; 
	
	public WSContext(){}
	
	public static WSContext getInstance(){
		if(instance == null){
			synchronized (WSContext.class) {
				if(instance == null){
					instance = new WSContext();
				}
			}
		}
		return instance;
	}
	
	public Map<String,Object> getThreadContext(){
		if(THREAD_CONTEXT.get() == null){
			THREAD_CONTEXT.set(new HashMap<String,Object>());
		}
		return THREAD_CONTEXT.get();
	}
	
	public Map<String,Object> getStaticContext(){
		return STATIC_CONTEXT;
	}
	
	public String getSessionValidCode(){
		return (String) this.getThreadContext().get(SESSION_VALID_CODE);
	}
	
	public void setSessionValidCode(String sessionValidCode){
		if(StringUtils.isBlank(sessionValidCode))
			return;
		
		this.getThreadContext().put(SESSION_VALID_CODE,sessionValidCode);
	}
	
	public ResultMap getCurrentResultMap(){
		Map context = getThreadContext();
		if(context.get(CURRENTRESULTMAP) == null){
			context.put(CURRENTRESULTMAP, ResultMap.createResult());
		} 
		return (ResultMap) context.get(CURRENTRESULTMAP);
	}
	
	public WSConfiguration getWSConfiguration(){
		WSConfiguration config = (WSConfiguration) this.getStaticContext().get(CONFIG_INFO);
		if(config == null){
			throw new WSConfigException("ws config info is null,please config!");
		}
		return config;
	}


	public void clearThreadContext(){
		getThreadContext().clear();
		THREAD_CONTEXT.set(null);
	}

	public void setWSConfiguration(WSConfiguration configuration) {
		if(STATIC_CONTEXT.get(CONFIG_INFO) == null){
			STATIC_CONTEXT.put(CONFIG_INFO, configuration);
		}
	}
	
}
