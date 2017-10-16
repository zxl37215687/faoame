package com.lsr.frame.ws.context;

import java.util.HashMap;

import com.lsr.frame.base.Session;

/**
 * WS会话
 * @author kuaihuolin
 *
 */
public class WSSession<K,V> extends HashMap<K,V> implements Session{

	private static final long serialVersionUID = 2426040501018129551L;
	
	private String id;
	
	private long lastOptTime = System.currentTimeMillis();
	
	private boolean isValid = true;
	
	public WSSession(String id, boolean isValid) {
		super();
		this.id = id;
		this.isValid = isValid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getLastOptTime() {
		return lastOptTime;
	}

	public void setLastOptTime(long lastOptTime) {
		this.lastOptTime = lastOptTime;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
}
