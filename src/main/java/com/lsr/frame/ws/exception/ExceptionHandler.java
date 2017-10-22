package com.lsr.frame.ws.exception;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsr.frame.base.exception.DefaultException;
import com.lsr.frame.ws.common.WSConstant;
import com.lsr.frame.ws.conver.ResultMap;

public class ExceptionHandler {
	private static final Log LOG = LogFactory.getLog(ExceptionHandler.class);

	/**
	 * 处理异常,按照异常的类型转换成ResultMap内容
	 * 
	 * @param ex
	 * @return
	 */
	public static ResultMap convertException(Throwable ex) {
		if (ex == null) {
			throw new WSRuntimeException("arg ex is null");
		}
		if (ex.getCause() != null) {
			return convertException(ex.getCause());
		}
		if (ex instanceof DefaultException) {
			DefaultException defaultException = (DefaultException) ex;
			return ResultMap.createResult().addWarnMessage(
					StringUtils.isEmpty(defaultException.getErrorDescription()) ? defaultException.getErrorCode()
							: defaultException.getErrorDescription()).addString(WSConstant.RESULT_KEY_WARN_CODE, defaultException.getErrorCode());
		}
		//db exception
//		if (ex instanceof DB2Exception) {
//			DB2Exception db2Exception = (DB2Exception) ex;
//			return ResultMap.createResult().addErrorMessge(db2Exception.getMessage()).addInt(
//					WSConstant.RESULT_KEY_SQL_ERROR_CODE, db2Exception.getErrorCode());
//		}
		if (ex instanceof Exception) {
			Exception exception = (Exception) ex;
			return ResultMap.createResult().addErrorMessge(exception.getMessage());
		}
		LOG.error(ex.getMessage(), ex);
		throw new WSRuntimeException("ERROR:" + ex.getMessage());
	}

	/**
	 * 输出异常
	 * 
	 * @param ex
	 */
	public static void logException(Throwable ex) {
		if (ex instanceof DefaultException) {

			DefaultException defaultException = (DefaultException) ex;

			if ("trace".equalsIgnoreCase(defaultException.getErrorLevel())) {
				LOG.trace(defaultException.getMessage(), defaultException);
			} else if ("debug".equalsIgnoreCase(defaultException.getErrorLevel())) {
				LOG.debug(defaultException.getMessage(), defaultException);
			} else if ("info".equalsIgnoreCase(defaultException.getErrorLevel())) {
				LOG.info(defaultException.getMessage(), defaultException);
			} else if ("warn".equalsIgnoreCase(defaultException.getErrorLevel())) {
				LOG.warn(defaultException.getMessage(), defaultException);
			} else if ("error".equalsIgnoreCase(defaultException.getErrorLevel())) {
				LOG.error(defaultException.getMessage(), defaultException);
			} else if ("fatal".equalsIgnoreCase(defaultException.getErrorLevel())) {
				LOG.fatal(defaultException.getMessage(), defaultException);
			} else {
				LOG.info(defaultException.getMessage(), defaultException);
			}

		} else {
			LOG.error(ex.getMessage(), ex);
		}
	}
}
