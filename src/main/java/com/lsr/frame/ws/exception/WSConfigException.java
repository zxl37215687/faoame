package com.lsr.frame.ws.exception;

/**
 * 
 * @author kuaihuolin
 *
 */
public class WSConfigException extends WSRuntimeException {

	private static final long serialVersionUID = 5000406172838302476L;

	public WSConfigException(){
		super();
	}
	
	public WSConfigException(String message,Throwable cause){
		super(message,cause);
	}
	
	public WSConfigException(String message){
		super(message);
	}
	
	public WSConfigException(Throwable cause){
		super(cause);
	}
}
