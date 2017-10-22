package com.lsr.frame.base.context;

import java.util.Map;

/**
 * 会话环境接口
 * @author kuaihuolin
 *
 */
public interface SessionContext extends Context {
	
	/**
	 * 获取会话环境SessionContext
	 * @return
	 */
	public Map<String,Session> getSessionContext();
	
	/**
	 * 根据sessionId判断Session是否存在
	 * @param sessionId
	 * @return
	 */
	public boolean isExist(String sessionId);
	
	/**
	 * 创建Session
	 * @param sessionId
	 * @return
	 */
	public Session createSession(String sessionId);
	
	/**
	 * 从会话环境中移除Session
	 * @param sessionId
	 */
	public void removeSession(String sessionId);
	
	/**
	 * 获取Session
	 * @param sessionId
	 * @return
	 */
	public Session getSession(String sessionId);
	
	/**
	 * 获取当前Session
	 * @param sessionId
	 * @return
	 */
	public Session getCurrentSession();
	
}
