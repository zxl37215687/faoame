package com.lsr.frame.ws.control;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsr.frame.ws.config.WSActionConfig;
import com.lsr.frame.ws.config.WSConfiguration;
import com.lsr.frame.ws.config.WSInterceptorConfig;
import com.lsr.frame.ws.context.WSContext;
import com.lsr.frame.ws.conver.Parameters;
import com.lsr.frame.ws.exception.WSRuntimeException;
import com.lsr.frame.ws.intercept.Interceptor;
import com.lsr.frame.ws.utils.CxfUtil;
import com.lsr.frame.ws.utils.MethodUtils;
import com.lsr.frame.ws.utils.StringParser;

/**
 * 调用执行器
 * @author kuaihuolin
 *
 */
public class DefaultWSActionInvocation implements WSActionInvocation {
	private static final Log LOG = LogFactory.getLog(DefaultWSActionInvocation.class);
	
	private Parameters parameter;
	private Object wsAction;
	private String wsActionId;
	private String method;
	private Iterator   interceptorIterator = null;
	private boolean isExecuted = false;
	private Map     resultMap  = null;
	private Exception exception;
	
	/**
	 * 
	 * @param wsAction
	 * @param method
	 * @param parameterMap
	 * @param wsActionContext
	 */
	public DefaultWSActionInvocation(String wsActionId, Object wsAction, String method, Parameters parameter) {
		super();
		this.parameter = parameter;
		this.wsActionId = wsActionId;
		this.wsAction      = wsAction;
		this.method        = method;
	}

	public Map invoke()throws Exception{
		if(isExecuted){
			throw new WSRuntimeException("wsaction already invoked!");
		}
		if(interceptorIterator != null && interceptorIterator.hasNext()){
			Interceptor interceptor = (Interceptor) interceptorIterator.next();
			if(checkNeedIntercept(interceptor) ){
				resultMap = interceptor.intercept(this);
			}else{
				invoke();
			}
		}else{
			resultMap = invokeAction();
			isExecuted = true;
		}
		return resultMap;
	}
	
	/**
	 * invoke action
	 * @return
	 * @throws InvocationTargetException
	 */
	private Map invokeAction() throws InvocationTargetException{
		LOG.info("requestURI:'" + CxfUtil.getHttpServletRequest().getRequestURI() + "', wsAction:'" + wsAction.getClass().getName() + "', method:'" + method + "'");
		Method invokeMethod = MethodUtils.getPublicMethod(wsAction.getClass(), method, new Class[]{Parameters.class});
		return (Map) MethodUtils.invoke(wsAction, invokeMethod, new Object[]{parameter});
	}
	
	/**
	 * check method is need intercept
	 * @param interceptor
	 * @return
	 */
	protected boolean checkNeedIntercept(Interceptor interceptor){
		WSConfiguration mainConfig   = WSContext.getInstance().getWSConfiguration();
		WSActionConfig  actionConfig = mainConfig.getWSActionConfig(wsActionId);
		WSInterceptorConfig interceptorConfig = mainConfig.getWSInterceptorConfig(interceptor.getClass());
		String inoreMethod = actionConfig.getInterceptIgnoreMethod(interceptorConfig.getId());
		if(StringUtils.isEmpty(inoreMethod)){
			return true;
		}
		return !StringParser.matches(method, inoreMethod);
	}
	
	public Parameters getParameter() {
		return parameter;
	}

	public Object getWSAction() {
		return wsAction;
	}

	public boolean isExecuted() {
		return isExecuted;
	}

	public String getMethod() {
		return method;
	}

	public Iterator getInterceptorIterator() {
		return interceptorIterator;
	}

	public void setInterceptorIterator(Iterator interceptorIterator) {
		this.interceptorIterator = interceptorIterator;
	}

	public Map getResultMap() {
		return resultMap;
	}

	public Exception getException() {
		return exception;
	}

	public String getWSActionId() {
		return wsActionId;
	}

}
