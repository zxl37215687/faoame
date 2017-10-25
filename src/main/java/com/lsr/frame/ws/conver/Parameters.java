package com.lsr.frame.ws.conver;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lsr.frame.ws.common.WSConstant;
import com.lsr.frame.ws.wsservice.FileService;


public class Parameters implements Serializable {
	private static final long serialVersionUID = 7334708818600462065L;

	private String[][] source;

	private String[] sourceString;
	
	private Map fileMap = new HashMap();

	private Map parameterMap = new LinkedHashMap() {
		private static final long serialVersionUID = 4699252581049830146L;
	};

	public Parameters(String[][] source) {
		if (source != null) {

			this.source = source;

			for (int i = 0; i < source.length; i++) {
				if (StringUtils.isEmpty(source[i][0])) {
					continue;
				}
				setValue(source[i][0].toLowerCase(), source[i][1]);
			}
		}
	}

	public Parameters() {
		this(null);
	}

	public Convertor getConvertor() {
		return ConvertorFactory.getDefaultConvertor();
	}

	public void setSourceString(String[] sourceString) {
		this.sourceString = sourceString;
	}

	String[] getSourceString() {
		return sourceString;
	}

	Object[][] getSource() {
		return source;
	}

	private void setValue(Object key, Object value) {
		parameterMap.put(key, value);
	}

	/**
	 * 获得参数Map
	 * 
	 * @return
	 */
	public Map getParameterMap() {
		return parameterMap;
	}

	/**
	 * 获得参数名称集合
	 * 
	 * @return
	 */
	public Collection getParameterNames() {
		return parameterMap.keySet();
	}

	/**
	 * 获得字符串类型参数
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		if (key == null) {
			return null;
		}
		Object value = parameterMap.get(key.toLowerCase());
		if (value == null) {
			return "";
		}
		return value.toString();
	}

	/**
	 * 获得字符串数组参数
	 * 
	 * @param key
	 * @return
	 */
	public String[] getStringArray(String key) {
		String value = getString(key);
		return (String[]) ConvertUtil.castToBaseTypeArray(value, String.class);
	}

	/**
	 * 获得比特参数
	 * 
	 * @param key
	 * @return
	 */
	public Byte getByte(String key) {
		String value = getString(key);
		return (Byte) ConvertUtil.castToBaseType(value, Byte.class);
	}

	/**
	 * 获得比特数组参数
	 * 
	 * @param key
	 * @return
	 */
	public Byte[] getByteArray(String key) {
		String value = getString(key);
		return (Byte[]) ConvertUtil.castToBaseTypeArray(value, Byte.class);
	}

	/**
	 * 获得短整形参数
	 * 
	 * @param key
	 * @return
	 */
	public Short getShort(String key) {
		String value = getString(key);
		return (Short) ConvertUtil.castToBaseType(value, Short.class);
	}

	/**
	 * 获得整型参数
	 * 
	 * @param key
	 * @return
	 */
	public Integer getInteger(String key) {
		String value = getString(key);
		return (Integer) ConvertUtil.castToBaseType(value, Integer.class);
	}

	/**
	 * 获得整型数组参数
	 * 
	 * @param key
	 * @return
	 */
	public Integer[] getIntegerArray(String key) {
		String value = getString(key);
		return (Integer[]) ConvertUtil.castToBaseTypeArray(value, Integer.class);
	}

	/**
	 * 获得长整型参数
	 * 
	 * @param key
	 * @return
	 */
	public Long getLong(String key) {
		String value = getString(key);
		return (Long) ConvertUtil.castToBaseType(value, Long.class);
	}

	/**
	 * 获得长整型数组参数
	 * 
	 * @param key
	 * @return
	 */
	public Long[] getLongArray(String key) {
		String value = getString(key);
		return (Long[]) ConvertUtil.castToBaseTypeArray(value, Long.class);
	}

	/**
	 * 获得单精度参数
	 * 
	 * @param key
	 * @return
	 */
	public Float getFloat(String key) {
		String value = getString(key);
		return (Float) ConvertUtil.castToBaseType(value, Float.class);
	}

	/**
	 * 获得长整型数组参数
	 * 
	 * @param key
	 * @return
	 */
	public Float[] getFloatArray(String key) {
		String value = getString(key);
		return (Float[]) ConvertUtil.castToBaseTypeArray(value, Float.class);
	}

	/**
	 * 获得双精度参数
	 * 
	 * @param key
	 * @return
	 */
	public Double getDouble(String key) {
		String value = getString(key);
		return (Double) ConvertUtil.castToBaseType(value, Double.class);
	}

	/**
	 * 获得双精度数组参数
	 * 
	 * @param key
	 * @return
	 */
	public Double[] getDoubleArray(String key) {
		String value = getString(key);
		return (Double[]) ConvertUtil.castToBaseTypeArray(value, Double.class);
	}

	/**
	 * 获得字符型参数
	 * 
	 * @param key
	 * @return
	 */
	public Character getCharacter(String key) {
		String value = getString(key);
		return (Character) ConvertUtil.castToBaseType(value, Character.class);
	}

	/**
	 * 获得字符型数组参数
	 * 
	 * @param key
	 * @return
	 */
	public Character[] getCharacterArray(String key) {
		String value = getString(key);
		return (Character[]) ConvertUtil.castToBaseTypeArray(value, Character.class);
	}

	/**
	 * 获得BigDecimal参数
	 * 
	 * @param key
	 * @return
	 */
	public BigDecimal getBigDecimal(String key) {
		String value = getString(key);
		return (BigDecimal) ConvertUtil.castToBaseType(value, BigDecimal.class);
	}

	/**
	 * 获得BigDecimal数组参数
	 * 
	 * @param key
	 * @return
	 */
	public BigDecimal[] getBigDecimalArray(String key) {
		String value = getString(key);
		return (BigDecimal[]) ConvertUtil.castToBaseTypeArray(value, BigDecimal.class);
	}

	/**
	 * 获得BigInteger参数
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public BigInteger getBigInteger(String key) {
		String value = getString(key);
		return (BigInteger) ConvertUtil.castToBaseType(value, BigInteger.class);
	}

	/**
	 * 获得BigInteger数组参数
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public BigInteger[] getBigIntegerArray(String key) {
		String value = getString(key);
		return (BigInteger[]) ConvertUtil.castToBaseTypeArray(value, BigInteger.class);
	}

	/**
	 * 获得日期参数
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public Date getDate(String key) {
		String value = getString(key);
		return (Date) ConvertUtil.castToBaseType(value, Date.class);
	}

	/**
	 * 获得日期数组参数
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public Date[] getDateArray(String key) {
		String value = getString(key);
		return (Date[]) ConvertUtil.castToBaseTypeArray(value, Date.class);
	}

	/**
	 * 获得布尔型参数
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {
		return "1".equals(this.getString(key)) || "true".equals(this.getString(key));
	}
	
	/**
	 * 获得单个文件
	 * @param key
	 * @return
	 */
	public File getFile(){
		File[] files = this.getFileArray();
		if(files == null || files.length <= 0){
			return null;
		}
		return files[0];
	}
	
	/**
	 * 获得文件数组
	 * @param key
	 * @return
	 */
	public File[] getFileArray(){
		String[] filePathArray = this.getStringArray(WSConstant.PARAMETER_KEY_FILE);
		if(filePathArray == null || filePathArray.length <= 0){
			return null;
		}
		File[] retFiles = new File[filePathArray.length];
		for(int i = 0; i < retFiles.length; i++){
			String filePath = FileService.getTempFileFolder(FileService.FILE_FOLDER) + filePathArray[i];
			if(fileMap.get(filePathArray[i]) == null){
				retFiles[i] = new File(filePath);
				fileMap.put(filePathArray[i], retFiles[i]);
			}else{
				retFiles[i] = (File) fileMap.get(filePathArray[i]);
			}
		}
		return retFiles;
	}
	
	/**
	 * 获得文件名称
	 * @return
	 */
	public String getFileName(){
		String[] fileNames = getFileNameArray();
		if(fileNames == null || fileNames.length <= 0){
			return null;
		}
		return fileNames[0];
	}
	
	/**
	 * 获得文件名称数组
	 * @return
	 */
	public String[] getFileNameArray(){
		return getStringArray(WSConstant.PARAMETER_KEY_FILE_NAME);
	}

	/**
	 * 获得客户端需要显示的VO属性,每个属性以逗号分隔
	 * 
	 * @return
	 */
	public String getShowFields() {
		return getString(WSConstant.PARAMETER_KSY_SHOW_FIELDS);
	}

	/**
	 * 获得ID,参数名称为"id"
	 * 
	 * @return
	 */
	public String getId() {
		return getString(WSConstant.PARAMETER_KEY_ID);
	}

	/**
	 * 获得ID数组,参数名称为"id"
	 * 
	 * @return
	 */
	public String[] getIdArray() {
		return getStringArray(WSConstant.PARAMETER_KEY_ID);
	}

	/**
	 * 获得参数中的VO
	 * 
	 * @param key
	 * @param clazz
	 *            vo的class, type
	 * @return
	 */
	public Object getVO(String key, Class clazz) {
		return getConvertor().stringToVO(getString(key), clazz);
	}

	/**
	 * 获得参数中的Map
	 * 
	 * @param key
	 * @param mapClazz
	 *            Map的class, type
	 * @return
	 */
	public Map getMap(String key, Class mapClazz) {
		return getConvertor().stringToMap(getString(key), mapClazz);
	}

	/**
	 * 获得参数中的数组,数组元素可以是VO或Map
	 * 
	 * @param key
	 * @param clazz
	 *            vo的class type或Map的class type
	 * @return
	 */
	public Object[] getObjectArray(String key, Class clazz) {
		return getConvertor().stringToArray(getString(key), clazz);
	}

	/**
	 * 获得参数中结合,结合元素可以是VO或Map
	 * 
	 * @param key
	 * @param clazz
	 *            vo的class type或Map的 class type
	 * @param collClazz
	 * @return
	 */
	public Collection getCollection(String key, Class clazz, Class collClazz) {
		return getConvertor().stringToCollection(getString(key), clazz, collClazz);
	}

	/**
	 * 清除Parameter所有内容
	 */
	public void clear() {
		for(Iterator it = fileMap.values().iterator(); it.hasNext(); ){
			File file  = (File) it.next();
			if(file != null && file.exists()){
				file.delete();
			}
		}
		parameterMap.clear();
	}
}
