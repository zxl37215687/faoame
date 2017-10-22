package com.lsr.frame.ws.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lsr.frame.ws.config.scope.ApplicationScope;
import com.lsr.frame.ws.config.scope.RequestScope;
import com.lsr.frame.ws.config.scope.Scope;
import com.lsr.frame.ws.config.scope.SessionScope;
import com.lsr.frame.ws.utils.ClassUtils;

public class WSActionConfig implements WSConfig{
	private String id ;
	
	private Class  type ;
	
	private String typeName;
	/**
	 * 生命周期
	 */
	private Scope  scope = DefaultScope.APPLICATION_SCOPE;
	/**
	 * 是否延迟加载
	 */
	private boolean initLazy = true;
	/**
	 * 拦截器ID数组
	 */
	private Collection  interceptorConfigs = new LinkedHashSet();
	/**	
	 * WSACTION拦截忽略设置
	 */
	private Map interceptIgnoreMap = new HashMap();
	
	/**
	 * 
	 * @param id
	 * @param type
	 */
	public WSActionConfig(String id, String typeName) {
		this(id, typeName, DefaultScope.APPLICATION);
	}
	
	/**
	 * 
	 * @param id
	 * @param typeName
	 * @param scopeName
	 */
	public WSActionConfig(String id, String typeName, String scopeName) {
		this(id, typeName, scopeName, true);
	}

	
	/**
	 * 
	 * @param id
	 * @param type
	 * @param scope
	 * @param initLazy
	 * @param interceptorIds
	 */
	private WSActionConfig(String id, String typeName, String scopeName, boolean initLazy) {
		super();
		this.id = id;
		this.typeName = typeName;
		this.type = ClassUtils.forName(typeName);
		this.scope = DefaultScope.getScope(scopeName);
		this.initLazy = initLazy;
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
	public Scope getScope() {
		return scope;
	}
	public void setScope(Scope scope) {
		this.scope = scope;
	}
	public boolean isInitLazy() {
		return initLazy;
	}
	public void setInitLazy(boolean initLazy) {
		this.initLazy = initLazy;
	}

	/**
	 * 获得该WSActionConfig的拦截器
	 * @param globalInterceptorConfigs全局拦截器配置
	 * @return
	 */
	public Collection getInterceptors(Collection globalInterceptorConfigs){
		Collection collection = new LinkedHashSet();
		if(globalInterceptorConfigs != null && !globalInterceptorConfigs.isEmpty()){
			for(Iterator it = globalInterceptorConfigs.iterator(); it.hasNext(); ){
				WSInterceptorConfig config = (WSInterceptorConfig) it.next();
				collection.add(config.getObject());
			}
		}
		if(interceptorConfigs != null && !interceptorConfigs.isEmpty()){
			for(Iterator it = interceptorConfigs.iterator(); it.hasNext(); ){
				WSInterceptorConfig config = (WSInterceptorConfig) it.next();
				collection.add(config.getObject());
			}
		}
		return collection;
	}

	public WSConfig getParent() {
		return null;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	/**
	 * 获得WSAction
	 * @return
	 */
	public Object getObject(){
		return scope.get(id, type);
	}

	public Collection getInterceptorConfigs() {
		return interceptorConfigs;
	}

	public void setInterceptorConfigs(Collection interceptorConfigs) {
		this.interceptorConfigs = interceptorConfigs;
	}
	
	/**
	 * 默认生命周期 		
	 */
	public static abstract class DefaultScope{
		
		public static final String APPLICATION = "application";
		public static final String SESSION     = "session";
		public static final String REQUEST     = "request";
		
		private DefaultScope(){super();}
		/**
		 * 单列生命周期(所有请求共享一个实例)
		 */
		public static final Scope APPLICATION_SCOPE = new ApplicationScope();
		/**
		 * 会话生命周期(同一个客户端共享一个实例)
		 */
		public static final Scope SESSION_SCOPE = new SessionScope();
		/**
		 * 请求生命周期(一此请求一个新实例)
		 */
		public static final Scope REQUEST_SCOPE = new RequestScope();
		
		/**
		 * 
		 * @param scopeName
		 * @return
		 */
		public static Scope getScope(String scopeName){
			if(StringUtils.isEmpty(scopeName)){
				return APPLICATION_SCOPE;
			}
			if(APPLICATION.equals(scopeName)){
				return APPLICATION_SCOPE;
			}
			if(SESSION.equals(scopeName)){
				return SESSION_SCOPE;
			}
			if(REQUEST.equals(scopeName)){
				return REQUEST_SCOPE;
			}
			throw new IllegalArgumentException("不提供" + scopeName + "范围");
		}
	}

	/**
	 * 
	 * @param inteceptorId
	 * @return
	 */
	public String getInterceptIgnoreMethod(String inteceptorId) {
		return (String) interceptIgnoreMap.get(inteceptorId);
	}

	/**
	 * 
	 * @param inteceptorId
	 * @param actionMethods
	 */
	public void addInterceptIgnoreMethod(String inteceptorId, String actionMethods) {
		this.interceptIgnoreMap.put(inteceptorId, actionMethods);
	}
	
	/**
	 * 清除WSAction
	 */
	public void clearWSAction(){
		getScope().remove( getId() );
	}
}
