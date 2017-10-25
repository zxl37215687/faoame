package com.lsr.frame.ws.wsservice;

import javax.jws.WebService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsr.frame.base.exception.DefaultException;
import com.lsr.frame.ws.common.WSService;
import com.lsr.frame.ws.context.WSContext;
import com.lsr.frame.ws.context.WSSession;
import com.lsr.frame.ws.context.WSSessionContext;
import com.lsr.frame.ws.control.access.CommonInfo;
import com.lsr.frame.ws.control.access.User;
import com.lsr.frame.ws.control.access.ZTThread;
import com.lsr.frame.ws.conver.Parameters;
import com.lsr.frame.ws.conver.ParametersBuilder;
import com.lsr.frame.ws.conver.ResultMap;
import com.lsr.frame.ws.exception.ExceptionHandler;
import com.lsr.frame.ws.utils.MD5Util;
import com.lsr.frame.ws.utils.WSSessionUtils;

@WebService
public class LoginWebService extends WSService{
	protected static final String USER_ID       = "user_id";
	protected static final String USER_CODE     = "userCode";
	protected static final String USER_NAME     = "userName"; 
	protected static final String PASSWORD      = "password";
	protected static final int 	  PASSWORD_LENGTH      = 6;
	protected static final String USER_TYPE      = "userType";
	protected static final String ISADMIN       = "isAdmint";
	protected static final String VALIDATE_CODE = "validateCode";
	
	protected static final String CA_CODE = "caCode";
	
	protected static final String LOGIN_ZT      = "loginZT";
	protected static final String LOGIN_YEAR    = "loginYear";
	
	protected static final String ORG_ID        = "orgId";
	protected static final String ORG_CODE_STD  = "orgCodeStd";
	protected static final String ORG_CODE_CST  = "orgCodeCst";
	protected static final String ORG_NAME      = "orgName";
	protected static final String ORG_ALIGS     = "orgAligs";
	protected static final String MUL_ORG_IDS   = "mulOrgIds";
	protected static final String MUL_ORG_NAME  = "mulOrgNames";
	
	protected static final String SECTION_ID    = "sectionId";
	protected static final String MUL_SECTION_IDS    = "mulSectionIds";
	protected static final String SECTION_NAME  = "sectionName";
	
	protected static final String MENU          = "menu";
	protected static final String PERMISSION    = "permission";
	
	protected static final String LOGIN_TYPE    = "loginType";
	protected static final String YEAR_LIST     = "yearList";
	protected static final String ZT_LIST       = "ztList";
	protected static final String DEFAULT_YEAR  = "defaultYear";
	protected static final String REMINDPORT = "remindPort";
	protected static final String REMINDIP = "remindIp";
	
	protected static final String LOGIN_DATE    = "loginDate";
	
	private static final Log LOG = LogFactory.getLog(LoginWebService.class);
	
	
	/**
	 * 登陆
	 * @param parameterStr
	 * @return
	 * @throws DefaultException
	 */
	public String[] login(String[] parameterStr){
		
		Parameters parameter = ParametersBuilder.createParameters(parameterStr);
		String  validateMessage = validateLoginParameter(parameter);
		
		CommonInfo info = new CommonInfo();
		info.setZtId(parameter.getString(LOGIN_ZT));
		info.setYear(parameter.getString(LOGIN_YEAR));
		ZTThread.set(info);
		
		if(StringUtils.isNotEmpty(validateMessage)){
			return ResultMap.createResult().addWarnMessage(validateMessage).toStringArray();
		}
		try{
			User loginUser = doLogin(parameter);
			validateMessage   = validateUser(loginUser, parameter);
			if( StringUtils.isNotEmpty(validateMessage) ){
				return ResultMap.createResult().addWarnMessage(validateMessage).toStringArray();
			}
		
			String validateCode = WSSessionUtils.createValidateCode();
			WSSession session   = (WSSession)WSSessionContext.getInstance().createSession(validateCode);
		
			ResultMap resultMap = setUserInfoToSession(session, loginUser, parameter);
			
			return resultMap.toStringArray();
			
			
		}catch(Exception ex){
			ExceptionHandler.logException(ex);
			return ExceptionHandler.convertException(ex).toStringArray();
		}finally{
			ZTThread.clear();
		}
		
	}
	
	/**
	 * 注销
	 * @param validateCode
	 * @return
	 */
	public String[] logout(String[] parameterStr){
		try{
			Parameters parameter = ParametersBuilder.createParameters(parameterStr);
			String validateCode  = parameter.getString(VALIDATE_CODE);
			WSSession session = (WSSession) WSSessionContext.getInstance().getSession(validateCode);
			if(session != null){
				session.clear();
				session.inValid();
				session = null;
				WSSessionContext.getInstance().removeSession(validateCode);
			}
			parameter.clear();
			WSContext.getInstance().clearThreadContext();
			
			return ResultMap.createResult().addOperateSucced().toStringArray();
		}catch(Exception ex){
			ExceptionHandler.logException(ex);
			return ExceptionHandler.convertException(ex).toStringArray();
		}
	}
	
	/**
	 * 获得登陆界面选择信息
	 * @param parameterStr
	 * @return
	 */
	public String[] getLoginInfo(String[] parameterStr){
		
		return null;
	}
	
	/**
	 * 基础信息，包含权限，资源
	 * @param session
	 * @param user
	 * @param parameter
	 * @throws DefaultException
	 */
	protected ResultMap setUserInfoToSession(WSSession session, User user, Parameters parameter)throws DefaultException{
		
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	protected String validateLoginParameter(Parameters parameter){
		return null;
	}
	
	/**
	 * 
	 * @param parameter
	 * @return
	 * @throws DefaultException
	 */
	protected User doLogin(Parameters parameter)throws DefaultException{
		
		return null;
	}
	
	/**
	 * 
	 * @param user
	 * @param parameter
	 * @return
	 * @throws DefaultException
	 */
	protected String validateUser(User user, Parameters parameter)throws DefaultException{
		return null;
	} 
	
}
