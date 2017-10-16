package com.lsr.frame.ws.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsr.frame.base.Session;
import com.lsr.frame.base.SessionContext;

/**
 * WS会话环境
 * @author kuaihuolin
 *
 */
public class WSSessionContext implements SessionContext{
	private static final Log LOG = LogFactory.getLog(WSSessionContext.class);
	private static Map<String,Session> SESSION_CONTEXT = new HashMap<String,Session>();
	private static volatile WSSessionContext instance;
	
	private WSSessionContext(){
	}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static SessionContext getInstance(){
		if(instance == null){
			synchronized (WSSessionContext.class) {
				if(instance == null){
					instance = new WSSessionContext();
				}
			}
		}
		return instance;
	}
	
	@Override
	public Map<String,Session> getSessionContext() {
		return SESSION_CONTEXT;
	}
	
	@Override
	public boolean isExist(String sessionId){
		return getSessionContext().get(sessionId) != null;
	}
	
	@Override
	public Session createSession(String sessionId){
		if(isExist(sessionId)){
			throw new RuntimeException("session is exist!");
		}
		WSSession<String,Object> session = new WSSession<String,Object>(sessionId,true);
		this.getSessionContext().put(sessionId, session);
		LOG.info("创建Session成功，sessionId:"+sessionId);
		return session;
	}

	@Override
	public void removeSession(String sessionId) {
		synchronized (sessionId) {
			if(isExist(sessionId)){
				this.getSessionContext().remove(sessionId);
				LOG.info("移除Session成功，sessionId:"+sessionId);
			}
		}
	}

	@Override
	public Session getSession(String sessionId) {
		return this.getSessionContext().get(sessionId);
	}

	@Override
	public Session getCurrentSession() {
		return this.getSessionContext().get(WSContext.getInstance().getSessionValidCode());
	}
	
}
