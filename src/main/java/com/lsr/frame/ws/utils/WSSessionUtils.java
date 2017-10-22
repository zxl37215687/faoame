package com.lsr.frame.ws.utils;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lsr.frame.base.exception.DefaultException;
import com.lsr.frame.ws.context.SessionConstant;
import com.lsr.frame.ws.context.WSSession;
import com.lsr.frame.ws.context.WSSessionContext;
import com.lsr.frame.ws.control.access.OrganizationVO;
import com.lsr.frame.ws.control.access.User;

public class WSSessionUtils {
	/**
	 * 
	 * @param userCode
	 * @return
	 */
	public static User getLoginUserAllSession(String userCodeOrId){
		Map sessionContext = WSSessionContext.getInstance().getSessionContext();
		for(Iterator it = sessionContext.values().iterator(); it.hasNext(); ){
			WSSession wsSession = (WSSession) it.next();
			User user = (User) wsSession.get(SessionConstant.SESSION_USER_VO);
			if(user == null){
				continue;
			}
			if(StringUtils.equals(user.getCode(), userCodeOrId) || StringUtils.equals(user.getId(), userCodeOrId)){
				return user;
			}
		}
		return null;
	}
	
	/**
	 * 获得登陆用户
	 * @return
	 */
	public static User getLoginUser(WSSession session){
		return (User) session.get(SessionConstant.SESSION_USER_VO);
	}
	
	/**
	 * 获得登陆用户组织机构
	 * @return
	 */
	public static OrganizationVO getOrganization(WSSession session){
		return (OrganizationVO) session.get(SessionConstant.SESSION_ORG_VO);
	}
	
	/**
	 * 获得登陆年份
	 * @return
	 */
	public static String getLoginYear(WSSession session){
		return (String) session.get(SessionConstant.SESSION_YEAR);
	}
	
	/**
	 * 获得账套
	 * @return
	 */
	public static String getZT(WSSession session){
		return (String) session.get(SessionConstant.SESSION_ZT);
	}
	
	/**
	 * 获得客户端IP
	 * @return
	 */
	public static String getLoginIP(WSSession session){
		return (String) session.get(SessionConstant.SESSION_LOGIN_IP);
	}
	
	/**
	 * 验证WSSession
	 * @param session
	 * @return
	 */
	public static DefaultException validate(WSSession session){
		if(session == null){
			return new DefaultException("会话已丢失，请重新登录！");
		}
		if(!session.isValid()){
			return new DefaultException("会话已过期，请重新登录！");
		}
		return null;
	}
	
	/**
	 * 创建会话验证码
	 * @return
	 */
	public static String createValidateCode(){
		return KeyGenerator.getUUID();
	}
	
}
