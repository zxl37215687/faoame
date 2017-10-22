package com.lsr.frame.ws.conver;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lsr.frame.ws.common.WSConstant;
import com.lsr.frame.ws.exception.WSRuntimeException;
import com.lsr.frame.ws.utils.DateUtils;

public class ResultMap extends HashMap{
	private static final long serialVersionUID = -5281434478295562618L;

	private Convertor convertor = ConvertorFactory.getDefaultConvertor();

	private ResultMap(Convertor convertor) {
		this.convertor = convertor;
	}

	public Convertor getConvertor() {
		return convertor;
	}

	/**
	 * 把ResultMap内容以"name==value"的形式输出字符串
	 */
	public String toSingleString() {
		if (this.size() > 1) {
			throw new WSRuntimeException("结果数量大于1");
		}
		return toStringArray()[0];
	}

	/**
	 * 把ResultMap内容以"name==value"的形式输出一维数组字符串
	 * 
	 * @param parameter
	 * @return
	 */
	public String[] toStringArray() {
		return ParametersBuilder.revertToStringArray(this);
	}

	/**
	 * 把ResultMap内容以String[n][0]=name,String[n][1]=value的形式输出二维数组字符串
	 * 
	 * @param parameters
	 * @return
	 */
	public String[][] toPlanarStringArray() {
		return ParametersBuilder.revertToPlanarStringArray(this);
	}

	/**
	 * 创建一个ResultMap
	 * 
	 * @return
	 */
	public static ResultMap createResult() {
		return new ResultMap(ConvertorFactory.getDefaultConvertor());
	}

	/**
	 * 创建一个ResultMap
	 * 
	 * @param convertor
	 * @return
	 */
	public static ResultMap createResult(Convertor convertor) {
		return new ResultMap(convertor);
	}

	/**
	 * 为ResultMap增加一个结果值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap add(String key, Object value) {
		this.put(key, value);
		return this;
	}

	/**
	 * 为ResultMap增加一个数组结果值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap addArray(String key, Object[] value) {
		this.add(key, value);
		return this;
	}

	/**
	 * 为ResultMap增加一个字符串结果值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap addString(String key, String value) {
		this.put(key, value);
		return this;
	}

	/**
	 * 为ResultMap增加一个比特结果值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap addByte(String key, byte value) {
		this.put(key, String.valueOf(value));
		return this;
	}
	
	/**
	 * 为ResultMap增加一个比特数组结果值
	 * @param key
	 * @param value
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ResultMap addByteArray(String key, byte[] value){
		this.add(key, value);
		return this;
	}

	/**
	 * 为ResultMap增加一个短整形结果值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap addShort(String key, short value) {
		this.put(key, String.valueOf(value));
		return this;
	}

	/**
	 * 为ResultMap增加一个整型结果值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap addInt(String key, int value) {
		this.put(key, String.valueOf(value));
		return this;
	}

	/**
	 * 为ResultMap增加一个长整型结果值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap addLong(String key, long value) {
		this.put(key, String.valueOf(value));
		return this;
	}

	/**
	 * 为ResultMap增加一个单精度结果值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap addFloat(String key, float value) {
		this.put(key, String.valueOf(value));
		return this;
	}

	/**
	 * 为ResultMap增加一个双精度结果值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap addDouble(String key, double value) {
		this.put(key, String.valueOf(value));
		return this;
	}

	/**
	 * 为ResultMap增加一个字符结果值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap addChar(String key, char value) {
		this.put(key, String.valueOf(value));
		return this;
	}

	/**
	 * 为ResultMap增加一个BigDecimal结果值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap addBigDecimal(String key, BigDecimal value) {
		this.put(key, String.valueOf(value));
		return this;
	}

	/**
	 * 为ResultMap增加一个BigInteger结果值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap addBigInteger(String key, BigInteger value) {
		this.put(key, String.valueOf(value));
		return this;
	}

	/**
	 * 为ResultMap增加一个布尔结果值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public ResultMap addBoolean(String key, boolean value) {
		this.addInt(key, value ? 1 : 0);
		return this;
	}

	/**
	 * 为ResultMap增加一个日期结果值
	 * 
	 * @param key
	 * @param date
	 * @return
	 */
	public ResultMap addDate(String key, Date date) {
		this.addString(key, DateUtils.DateTOStr(date));
		return this;
	}

	/**
	 * 为ResultMap增加一个VO
	 * 
	 * @param key
	 * @param object
	 *            必须是WSVO
	 * @param showFields
	 *            客户端需要显示的属性
	 * @return
	 */
	public ResultMap addVO(String key, Object object, String showFields) {
		this.put(key, convertor.voToString(object, showFields));
		return this;
	}

	/**
	 * 为ResultMap增加一个数组
	 * 
	 * @param key
	 * @param objectArray
	 *            元素可以是WSVO或Map
	 * @param showFields
	 *            客户端需要显示的属性
	 * @return
	 */
	public ResultMap addObjectArray(String key, Object[] objectArray, String showFields) {
		this.put(key, convertor.arrayToString(objectArray, showFields));
		return this;
	}

	/**
	 * 为ResultMap增加一个集合
	 * 
	 * @param key
	 * @param collection
	 *            元素可以是WSVO或Map
	 * @param showFields
	 *            客户端需要显示的属性
	 * @return
	 */
	public ResultMap addCollection(String key, Collection collection, String showFields) {
		this.put(key, convertor.collectionToString(collection, showFields));
		return this;
	}

	/**
	 * 为ResultMap增加一个Map
	 * 
	 * @param key
	 * @param map
	 * @param showFields
	 *            Map的Key
	 * @return
	 */
	public ResultMap addMap(String key, Map map, String showFields) {
		this.put(key, convertor.mapToString(map, showFields));
		return this;
	}

	/**
	 * 为ResultMap增加一个客户端的列表数据, 结果名称为"listData"
	 * 
	 * @param collection
	 *            元素可以是WSVO或Map
	 * @param showFields
	 *            客户端需要显示的属性
	 * @return
	 */
	public ResultMap addListData(Collection collection, String showFields) {
		this.addCollection(WSConstant.RESULT_KEY_LIST_DATA, collection, showFields);
		return this;
	}

	/**
	 * 为ResultMap增加一个客户端的查看数据, 结果名称为"viewData"
	 * 
	 * @param object
	 *            必须是WSVO
	 * @param showFields
	 *            客户端需要显示的属性
	 * @return
	 */
	public ResultMap addViewData(Object object, String showFields) {
		this.addVO(WSConstant.RESULT_KEY_VIEW_DATA, object, showFields);
		return this;
	}

	/**
	 * 为ResultMap增加一个客户端正常提示信息,一般是一个操作成功提示
	 * 
	 * @param message
	 * @return
	 */
	public ResultMap addNormalMessage(String message) {
		this.put(WSConstant.RESULT_KEY_NORMAL_MESSAGE, message);
		return this;
	}

	/**
	 * 为ResultMap增加一个客户端警告提示信息
	 * 
	 * @param message
	 * @return
	 */
	public ResultMap addWarnMessage(String message) {
		this.put(WSConstant.RESULT_KEY_WARN_MESSAGE, message);
		return this;
	}

	/**
	 * 为ResultMap增加一个客户端错误提示信息
	 * 
	 * @param message
	 * @return
	 */
	public ResultMap addErrorMessge(String message) {
		this.put(WSConstant.RESULT_KEY_ERROR_MESSAGE, message);
		return this;
	}

	/**
	 * 为ResultMap增加一个客户端成功的操作代码
	 * 
	 * @return
	 */
	public ResultMap addOperateSucced() {
		this.addInt(WSConstant.RESULT_KEY_OPERATE_CODE, WSConstant.RESULT_VALUE_OPERATE_SUCCED);
		return this;
	}

	/**
	 * 为ResultMap增加一个客户端失败的操作代码
	 * 
	 * @return
	 */
	public ResultMap addOperateFail() {
		this.addInt(WSConstant.RESULT_KEY_OPERATE_CODE, WSConstant.RESULT_VALUE_OPERATE_FAIL);
		return this;
	}
}
