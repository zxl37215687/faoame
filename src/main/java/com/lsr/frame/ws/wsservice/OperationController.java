package com.lsr.frame.ws.wsservice;

import java.util.Collection;
import java.util.Map;

import com.lsr.frame.base.utils.StringUtils;
import com.lsr.frame.ws.common.WSService;
import com.lsr.frame.ws.config.WSActionConfig;
import com.lsr.frame.ws.config.WSActionConfig.DefaultScope;
import com.lsr.frame.ws.config.WSConfiguration;
import com.lsr.frame.ws.context.WSContext;
import com.lsr.frame.ws.context.WSSession;
import com.lsr.frame.ws.context.WSSessionContext;
import com.lsr.frame.ws.control.DefaultWSActionInvocation;
import com.lsr.frame.ws.control.WSAction;
import com.lsr.frame.ws.control.WSActionInvocation;
import com.lsr.frame.ws.conver.Parameters;
import com.lsr.frame.ws.conver.ParametersBuilder;
import com.lsr.frame.ws.exception.WSRuntimeException;

/**
 * 统一入口
 * @author kuaihuolin
 *
 */
public class OperationController extends WSService implements WSAction {

	public String[] call(String actionId, String methodName, String[] argus, String sessionId)throws Exception {
		
		Map	resultMap = action(actionId, methodName, argus, sessionId);  
			
		return ParametersBuilder.revertToStringArray(resultMap);
		
	}
	
	public byte[] doDownload(String actionId, String methodName, String[] argus, String sessionId)throws Exception{
	
		Map resultMap = action(actionId, methodName, argus, sessionId);  
		if(resultMap == null || resultMap.isEmpty()){
			return null;
		}
		Object object = resultMap.values().iterator().next();
		if(!(object instanceof byte[])){
			throw new WSRuntimeException("文件下载,只能处理byte[]类型数据");
		}
		return (byte[]) object;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	protected Map action(String actionId, String methodName, String[] argus, String sessionId)throws Exception{
		Parameters     parameter    = null;
		WSActionConfig actionConfig = null;
		try{
			//把sessionId放入WSActionContext
			WSContext.getInstance().setSessionValidCode(sessionId);
			//创建参数
			parameter = ParametersBuilder.createParameters(argus);
		
			//获得全局配置
			WSConfiguration configuration = WSContext.getInstance().getWSConfiguration();
		
			//获得WSActionConfig
			actionConfig = configuration.getWSActionConfig(actionId.trim());

			//获得wsAction
			WSActionInvocation invocation = getWSActionInvocation(actionId.trim(), actionConfig.getObject(), methodName, parameter);
		
			//获得全局拦截器配置
			Collection globalInterceptorConfigs = configuration.getAllGlobalWSInterceptorConfig();
			//设定拦截器
			invocation.setInterceptorIterator( actionConfig.getInterceptors( globalInterceptorConfigs ).iterator() );
		
			//执行WSAction的方法调用
			return invocation.invoke();
			
		}finally{
			//更新WSSession操作时间
			updateSessionLastOptTime(sessionId);
			//清除Scope为Request的WSAction
			if(actionConfig != null && actionConfig.getScope().equals(DefaultScope.REQUEST_SCOPE)){
				actionConfig.clearWSAction();
			}
			//清除参数
			parameter.clear();
			//清除当前线程环境
			WSContext.getInstance().clearThreadContext();
		}
	}
	
	/**
	 * 获得WSActionInvocation
	 * @param wsAction
	 * @param methodName
	 * @param parameter
	 * @return
	 */
	protected WSActionInvocation getWSActionInvocation(String wsActionId, Object wsAction, String methodName, Parameters parameter){
		return new DefaultWSActionInvocation(wsActionId, wsAction, methodName, parameter);
	}
	
	/**
	 * 更新Session的最后操作时间
	 * @param sessionId
	 */
	private void updateSessionLastOptTime(String sessionId){
		if(StringUtils.isBlank(sessionId)){
			return;
		}
		if(WSSessionContext.getInstance().getSession(sessionId) != null){
			((WSSession)WSSessionContext.getInstance().getSession(sessionId)).setLastOptTime( System.currentTimeMillis() );
		}
	}
}
