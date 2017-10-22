package com.lsr.frame.ws.config;

import java.util.HashMap;
import java.util.Map;

import com.lsr.frame.ws.exception.WSConfigException;
import com.lsr.frame.ws.intercept.Interceptor;
import com.lsr.frame.ws.utils.ClassUtils;

public class WSInterceptorConfig implements WSConfig{
	private String id ;
	private Class  type;
	private String typeName;
	private boolean isGlobal = false;
	private static final Map INTERCEPTOR_CONTEXT = new HashMap();
	
	public WSInterceptorConfig(String id, String typeName) {
		super();
		this.id = id;
		this.typeName = typeName;
		this.type = ClassUtils.forName(typeName);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Class getType() {
		return type;
	}
	public void setType(Class type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public synchronized Object getObject(){
		Object interceptor = INTERCEPTOR_CONTEXT.get(id);
		if(interceptor == null){
			interceptor = ClassUtils.newInstance(type);
			INTERCEPTOR_CONTEXT.put(id, interceptor);
		}
		if(!(interceptor instanceof Interceptor)){
			throw new WSConfigException(interceptor.getClass().getName() + " must implement " + Interceptor.class.getName());
		}
		return interceptor;
	}
	
	public WSConfig getParent() {
		return null;
	}

	public boolean isGlobal() {
		return isGlobal;
	}

	public void setGlobal(boolean isGlobal) {
		this.isGlobal = isGlobal;
	}
}
