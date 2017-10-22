package com.lsr.frame.ws.common;

import com.lsr.frame.base.common.Constant;
import com.lsr.frame.ws.context.DefaultConfiguraction;
import com.lsr.frame.ws.control.DefaultWSActionInvocation;
import com.lsr.frame.ws.conver.DefaultConvertor;
import com.lsr.frame.ws.conver.MapConvertorImpl;

/**
 * WS常量类
 * @author kuaihuolin
 *
 */
public class WSConstant implements Constant {

	/**
	 * 分割符
	 */
	public class Split {
		
		/**
		 * 逗号 ,
		 */
		public static final String COMMA_SPLIT = ",";
		/**
		 * 参数名与值分隔符 ==
		 */
		public static final String PARAMETER_SPLIT = "==";
		/**
		 * 数组标志符号 @`@, 拥有该标志的字符串的可以转换成数组或集合
		 */
		public static final String ARRAY_SIGN = "@`@";
		/** 
		 * Grid值分隔符 \t
		 */
		public static final String GRID_VALUE_SEPARATOR = "\t";
		/**
		 * Grid行分隔符 \n
		 */
		public static final String GRID_RECORD_SEPARATOR = "\n";
		/**
		 * EDIT 属性名与数值分隔符 :`:
		 */
		public static final String EDIT_FIELD_VALUE_SEPARATOR = ":`:";
		/**
		 * EDIT 属性之间分隔符 #`#
		 */
		public static final String EDIT_FIELD_SEPARATOR = "#`#";
		
		/**
		 * EDIT记录之间分隔符 @`@
		 */
		public static final String EDIT_RECORD_SEPARATOR = ARRAY_SIGN;
		
		/**
		 * EDIT 从表记录分隔符 @`@
		 */
		public static final String EDIT_DETAIL_RECORD_SEPARATOR = ARRAY_SIGN;
		
		/**
		 * EDIT 从从表记录分隔符 @.@
		 */
		public static final String EDIT_DETAIL_DETAIL_RECORD_SEPARATOR = "@.@";
		
		/**
		 * 第一层从表开始符 `{
		 */
		public static final String DETAIL_TABLE_START = "`{";
		/**
		 * 的一层从表结束符 }`
		 */
		public static final String DETAIL_TABLE_END = "}`";
		/**
		 * 第二层从表开始符 `[
		 */
		public static final String SECONDE_DETAIL_TABLE_START = "`[";
		/**
		 * 第二层从表结束符 ]`
		 */
		public static final String SECONDE_DETAIL_TABLE_END = "]`";
		/**
		 * 从表符号长度 2
		 */
		public static final int DETAIL_TABLE_SIGN_LENGTH = 2;
	}
	
	/**
	 * 默认配置
	 */
	public static final Class DEFAULT_CONFIGURACTION = DefaultConfiguraction.class;
	
	/**
	 * 默认wsaction调用器
	 */
	public static final Class DEFAULT_WSACTION_INVOCAION = DefaultWSActionInvocation.class;
	
	/**
	 * 默认转换器
	 */
	public static final Class DEFAUT_CONVERTOR = DefaultConvertor.class;
	
	/**
	 * Map转换器
	 */
	public static final Class MAP_CONVERTOR    = MapConvertorImpl.class;
	
	/**
	 * 压缩字隔集
	 */
	public static final String COMPRESS_CHARSET = "UTF-8";
	
	/**
	 * 参数显示字段参数KEY
	 */
	public static final String PARAMETER_KSY_SHOW_FIELDS = "showFields";
	
	/**
	 * 上传文件
	 */
	public static final String PARAMETER_KEY_FILE = "file";
	
	/**
	 * 文件名称
	 */
	public static final String PARAMETER_KEY_FILE_NAME = "fileName";
	
	/**
	 * 参数IDKEY
	 */
	public static final String PARAMETER_KEY_ID = "id";
	
	
	/**
	 * 失败代码
	 */
	public static final String RESULT_KEY_FAIL_CODE   = "failCode";
	/**
	 * 成功代码
	 */
	public static final String RESULT_KEY_SUCCED_CODE   = "succedCode";
	
	/**
	 * 操作成功
	 */
	public static final int    RESULT_VALUE_OPERATE_SUCCED = 0;
	/**
	 * 操作失败
	 */
	public static final int    RESULT_VALUE_OPERATE_FAIL   = 1;
	
	/**
	 * 操作代码
	 */
	public static final String RESULT_KEY_OPERATE_CODE = "operateCode";
	
	/**
	 * 操错出错提示
	 */
	public static final String RESULT_KEY_ERROR_MESSAGE = "errorMessage";
	
	/**
	 * 错误提示编号
	 */
	public static final String RESULT_KEY_ERROR_CODE    = "errorCode";
	
	/**
	 * 警告提示
	 */
	public static final String RESULT_KEY_WARN_MESSAGE = "warnMessage";
	/**
	 * 警告编号
	 */
	public static final String RESULT_KEY_WARN_CODE = "warnCode";
	
	/**
	 * 正常操作提示
	 */
	public static final String RESULT_KEY_NORMAL_MESSAGE = "normalMessge";
	
	/**
	 * SQL错误码
	 */
	public static final String RESULT_KEY_SQL_ERROR_CODE = "sqlErrorCode";
	
	/**
	 * 列表数据窗口
	 */
	public static final String RESULT_KEY_LIST_DATA = "listData";
	
	/**
	 * 修改数据窗口或查看数据窗口
	 */
	public static final String RESULT_KEY_VIEW_DATA = "viewData";

}
