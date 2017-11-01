package com.lsr.frame.ws.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lsr.frame.base.exception.DefaultException;
import com.lsr.frame.ws.control.access.User;

public class WriteLog {
	public final static String ADD = "添加";
	public final static String EDIT = "修改";
	public final static String DEL = "删除";
	public final static String AUDIT = "审核";
	public final static String DISAUDIT = "销审";
	public final static String REAUDIT = "复核";
	public final static String CANCEL_REAUDIT = "取消复核";
	public final static String SUBMIT = "提交";
	public final static String BACK = "退回";
	public final static String CHARGEUP = "登帐";
	public final static String CANCEL_CHARGEUP = "取消登账";
	public final static String	LOGOUT = "退出";
	public final static String	LOGIN = "登录";
	public final static String	BOOK = "记帐";
	public final static String	UNBOOK = "反记帐";
	public final static String	USED = "启用";
	public final static String	UNUSED = "停用";
	/**
	 * 插入一条记录到操作记录表中
	 * @param request
	 * @param optAct 操作的动作
	 * @param optNode 操作说明
	 * @throws DefaultException
	 */
	public static void writeRecord(HttpServletRequest request, String optAct,
			String optNode){
		
	}
	
	
	/**
	 * 插入一条记录到操作记录表中
	 * @param request
	 * @param optAct 操作的动作
	 * @param optNode 操作说明
	 * @throws DefaultException
	 */
	public static void writeRecord(HttpServletRequest request, String optAct,
			List optNode){
		
	}
	
	/**
	 * 插入记录到操作记录表中
	 * @param request
	 * @param optNode 二维数组 [0] : 操作的动作 [1] : 操作说明
	 * @throws DefaultException
	 */
	public static void writeRecord(HttpServletRequest request, String[][] optNode){
		
	}
	
	/**
	 * 插入一条记录到操作记录表中
	 * @param user 当前用户
	 * @param remoteAddr 客户端IP
	 * @param optAct 操作动作
	 * @param optNode 操作说明
	 */
	public static void writeRecord(User user, String remoteAddr, String optAct, String optNode){
	}
	
	/**
	 * 插入多条记录到操作记录表中
	 * @param user 当前用户
	 * @param remoteAddr 客户端IP
	 * @param optAct 操作动作
	 * @param optNode 操作说明List
	 */
	public static void writeRecord(User user, String remoteAddr, String optAct, List optNode){
	}
	
	/**
	 * 插入多条记录到操作记录表中
	 * @param user 当前用户
	 * @param remoteAddr 客户端IP
	 * @param optNode 操作说明 optNode[0]操作动作, optNode[1]操作说明
	 */
	public static void writeRecord(User user, String remoteAddr, String[][] optNode){
	}
	/**
	 * 插入一条记录到操作记录表中
	 * @param ip
	 * @param optPos
	 * @param userid
	 * @param optAct
	 * @param optNode
	 * @throws DefaultException
	 */
	public static void writeRecord(String ip,String optPos,String userid ,String optAct,String optNode)throws DefaultException{

		
	}


	public static void writeRecord(User currentUser, String remoteAddr,
			String optAct, String optNode, String modelName){
	}
}
