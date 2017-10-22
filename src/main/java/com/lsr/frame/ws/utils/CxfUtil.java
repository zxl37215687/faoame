package com.lsr.frame.ws.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

/**
 * Cxf工具类（主要用于获取Http对象）
 * @author kuaihuolin
 *
 */
public class CxfUtil {
	
	/**
	 * 获取CXF Message
	 * @return
	 */
	public static Message getMessage(){
		return PhaseInterceptorChain.getCurrentMessage();  
	}
	
	/**
	 * 获取HttpServletRequest
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest(){
		return (HttpServletRequest) getMessage().get(AbstractHTTPDestination.HTTP_REQUEST);
	}
	
	/**
	 * 获取HttpSession
	 * @return
	 */
	public static HttpSession getHttpSession(){
		return getHttpServletRequest().getSession();
	}
	
	/**
	 * 获取HttpServletResponse
	 * @return
	 */
	public static HttpServletResponse getHttpServletResponse(){
		return (HttpServletResponse) getMessage().get(AbstractHTTPDestination.HTTP_RESPONSE);
	}
	
	/**
	 * 获取ServletContext
	 * @return
	 */
	public static ServletContext getServletContext(){
		 return (ServletContext)getMessage().get(AbstractHTTPDestination.HTTP_CONTEXT);
	}
	
	/**
	 * 获取服务器真实路径
	 * @return
	 */
	public static String getRealPath(){
		return getHttpServletRequest().getServletContext().getRealPath("/");
	}
	
	/**
	 * 获取客户端IP
	 * @return
	 */
	public static String getRemoteAddr(){
		return getHttpServletRequest().getRemoteAddr();
	}
	
	/**
	 * 获取客户端端口
	 * @return
	 */
	public static int getRemotePort(){
		return getHttpServletRequest().getRemotePort();
	}
	
	public static String getRequestedSessionId(){
		return getHttpServletRequest().getRequestedSessionId();
	}
	
}
