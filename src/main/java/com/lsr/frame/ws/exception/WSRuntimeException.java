package com.lsr.frame.ws.exception;

/**
 * 
 * @author kuaihuolin
 *
 */
public class WSRuntimeException extends RuntimeException{
	
	private static final long serialVersionUID = 3932959025529679207L;

	public WSRuntimeException(){
		super();
	}

	public WSRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public WSRuntimeException(String message) {
		this(message, null);
		
	}

	public WSRuntimeException(Throwable cause) {
		super(cause);
	}
	
}
