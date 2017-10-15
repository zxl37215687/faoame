package com.lsr.frame.ws;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsr.frame.SessionContext;

/**
 * WS会话环境
 * @author kuaihuolin
 *
 */
public class WSSessionContext implements SessionContext{
	private static final Log LOG = LogFactory.getLog(WSSessionContext.class);
	
	private static Map<String,WSSession> SESSION_CONTEXT = new HashMap<String,WSSession>();

	/**
	 * 获取WS会话
	 * @return
	 */
	public static Map<String,WSSession> getSessionContext() {
		return SESSION_CONTEXT;
	}

	public static boolean isExist(String sessionId){
		return false;
	}
	
	public static void createWSSession(String sessionId){
		
	}
	
	
	
	
}
