package com.lsr.frame.ws.config;

/**
 * 
 * @author kuaihuolin
 *
 */
public class WSContextConfigConstants {

	/**
	 * 默认总config文件
	 */
	public static final String DEFAULT_WSCONFIGLOCATION = "wsconfig/ws.config.xml";
	
	/**
	 * servlet context参数wsConfigLocation
	 */
	public static final String PARA_NAME_WSCONFIGLOCATION = "wsConfigLocation";
	
	/**
	 * servlet context参数wsSessionTimeout
	 */
	public static final String PARA_NAME_WSSESSIONTIMEOUT = "wsSessionTimeout";
	
	/**
	 * <wsconfig>
	 */
	public static final String TAG_WSCONFIG = "wsconfig";
	
	/**
	 * <interceptors-define>
	 */
	public static final String TAG_INTERCEPTORS_DEFINE = "interceptors-define";
	
	/**
	 * <interceptor>
	 */
	public static final String TAG_INTERCEPTOR = "interceptor";
	
	/**
	 * <interceptors-ref>
	 */
	public static final String TAG_INTERCEPTORS_REF = "interceptors-ref";
	
	/**
	 * <global>
	 */
	public static final String TAG_GLOBAL_DEFINE = "global-define";
	
	/**
	 * <wsvos-define>
	 */
	public static final String TAG_WSVOS_DEFINE = "wsvos-define";
	
	/**
	 * <wsvo>
	 */
	public static final String TAG_WSVO   = "wsvo";
	
	/**
	 * <wsvoproperty>
	 */
	public static final String TAG_WSVOPROPERTY = "wsvoproperty";
	
	/**
	 * <wsactions-define>
	 */
	public static final String TAG_WSACTIONS_DEFINE = "wsactions-define";
	
	/**
	 * <wsaction>
	 */
	public static final String TAG_WSACTION  = "wsaction";
	
	/**
	 * <intercept-ignore-set>
	 */
	public static final String TAG_INTERCEPT_IGNORE_SET = "intercept-ignore-set";
	
	/**
	 * <intercept-ignore>
	 */
	public static final String TAG_INTERCEPT_IGNORE = "intercept-ignore";
	
	/**
	 * <config-include>
	 */
	public static final String TAG_CONFIG_INCLUDE = "config-include";
	
	
	/**
	 * <resource>
	 */
	public static final String TAG_RESOURCE = "resource";
	
	/**
	 * 标签属性 package
	 */
	public static final String ATT_PACKAGE = "package";
	
	/**
	 * 标签属性 id
	 */
	public static final String ATT_ID = "id";
	
	/**
	 * 标签属性 type
	 */
	public static final String ATT_TYPE = "type";
	
	/**
	 * 标签属性 property
	 */
	public static final String ATT_PROPERTY = "property";
	
	/**
	 * 标签属性 realtype
	 */
	public static final String ATT_REALTYPE = "realtype";
	
	/**
	 * 标签属性 scope
	 */
	public static final String ATT_SCOPE = "scope";
	
	/**
	 * 标签属性 path
	 */
	public static final String ATT_PATH = "path";
	
	/**
	 * 标签属性 references
	 */
	public static final String ATT_REFERENCES = "references";
	
	/**
	 * 标签属性 interceptorId
	 */
	public static final String ATT_INTERCEPTORID = "interceptorId";
	
	/**
	 * 标签属性 actionMethods
	 */
	public static final String ATT_ACTIONMETHODS = "actionMethods";
	
	/**
	 * 标签属性分隔符:逗号
	 */
	public static final String ATT_VALUE_SPLIT = ",";
	
	/**
	 * 包名称分隔符:句号
	 */
	public static final String PACKAGE_SPLIT   = ".";
}
