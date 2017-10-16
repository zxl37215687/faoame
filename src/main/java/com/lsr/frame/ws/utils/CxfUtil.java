package com.lsr.frame.ws.utils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.jaxws.context.WebServiceContextImpl;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;


public class CxfUtil {
	
	public static Message getMessage(){
		Message message = PhaseInterceptorChain.getCurrentMessage();  
		return message;
	}
	
	public static MessageContext getMessageContext(){
		WebServiceContext wsContext = new WebServiceContextImpl();  
		return wsContext.getMessageContext();
	}
	
	public static HttpServletRequest getHttpServletRequest(){
		return null;
	}
}
