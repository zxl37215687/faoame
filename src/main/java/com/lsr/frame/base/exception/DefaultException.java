package com.lsr.frame.base.exception;

import java.sql.SQLWarning;

import org.apache.commons.lang3.StringUtils;


public class DefaultException extends Exception {

	private static final long serialVersionUID = 7183226148300917707L;

	public final static String DEBUG = "debug";

	public final static String INFO = "info";

	public final static String WARN = "warn";

	public final static String ERROR = "error";

	private String errorCode; 

	private String errorDescription = "";

	private String errorLevel = ERROR; 

	public DefaultException() {
	}

	public DefaultException(String errorCode) {
		this.errorCode = errorCode;
		String strMsg = ErrorConfig.getProperty(errorCode);
		if (!StringUtils.isBlank(strMsg)) {
			String[] arr = StringUtils.split(strMsg, "::");
			if (arr.length == 2) {
				this.errorLevel = arr[0];
				this.errorDescription = arr[1];
			}
		}

	}

	public DefaultException(Throwable eThrow) {
		super(eThrow);
		if (eThrow instanceof DefaultException) {
			DefaultException de = (DefaultException) eThrow;
			this.errorCode = de.errorCode;
			this.errorLevel = de.errorLevel;
			this.errorDescription=de.errorDescription;
		} else if (eThrow instanceof SQLWarning) {
			this.errorCode = (ErrorConstant.SQLWarning);
			this.errorDescription = eThrow.getMessage();
			this.errorLevel = WARN;
		} else {
			this.errorCode = eThrow.getClass().getName();
			this.errorDescription = eThrow.getMessage();
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorLevel() {
		return errorLevel;
	}

	public void setErrorLevel(String errorLevel) {
		this.errorLevel = errorLevel;
	}

	public String toString() {
		return new StringBuffer().append("Error code:").append(errorCode)
				.append(" Error level:").append(errorLevel).append(
						" Error description:").append(errorDescription)
				.toString();
	}
}
