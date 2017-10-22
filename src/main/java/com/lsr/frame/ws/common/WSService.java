package com.lsr.frame.ws.common;

import com.lsr.frame.base.context.SpringContextHolder;
import com.lsr.frame.base.service.Service;
import com.lsr.frame.ws.context.WSSession;
import com.lsr.frame.ws.context.WSSessionContext;
import com.lsr.frame.ws.conver.Convertor;
import com.lsr.frame.ws.conver.ConvertorFactory;
import com.lsr.frame.ws.utils.CxfUtil;


/**
 * WebService 
 * 对外发布的接口均需继承该类
 * @author kuaihuolin
 *
 */
public abstract class WSService  {
	
	/**
	 * 获得本地Service
	 * @param beanId
	 * @return
	 */
	protected Service getService(String beanId){
		return SpringContextHolder.getBean(beanId);
	}
	
	/**
	 * 获得数据转换器
	 * @return
	 */
	protected Convertor getConvertor(){ 
		return ConvertorFactory.getDefaultConvertor();
	}
	
	/**
	 * 获得数据转换器
	 * @param clazz
	 * @return
	 */
	protected Convertor getConvertor(Class clazz){ 
		return ConvertorFactory.getConvertor(clazz);
	}
	
	/**
	 * 获得当前会话
	 * @return
	 */
	protected WSSession getSession(){
		return (WSSession) WSSessionContext.getInstance().getCurrentSession();
	}
	
	/**
	 * 获得远程客户端IP
	 * @return
	 */
	protected String getRemoteAddr(){
		return CxfUtil.getRemoteAddr();
	}
}
