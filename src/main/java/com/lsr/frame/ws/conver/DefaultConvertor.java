package com.lsr.frame.ws.conver;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsr.frame.base.entityconver.VOInterface;
import com.lsr.frame.ws.common.WSConstant;
import com.lsr.frame.ws.config.WSPropertyConfig;
import com.lsr.frame.ws.config.WSVOConfig;
import com.lsr.frame.ws.exception.ConvertException;
import com.lsr.frame.ws.utils.ClassUtils;
import com.lsr.frame.ws.utils.ConvertUtils;
import com.lsr.frame.ws.utils.ObjectUtils;

public class DefaultConvertor extends AbstractConvertor{
private static final Log LOG = LogFactory.getLog(DefaultConvertor.class);
	
	private static final String REPLACE_SIGN = "***";

	public Object stringToVO(String string, Class clazz) {

		WSVOConfig wsVOConfig = getWSConfiguration().getWSVOConfig(clazz);

		Object vo = ClassUtils.newInstance(clazz);

		// 处理从表VO部分,把从表VO的字符串替换成"***"
		StringBuffer voStrBuffer = new StringBuffer(string);
		Map childTableData = convertDetailTableEditData(voStrBuffer);

		Map fieldAndValuesMap = stringToMap(voStrBuffer.toString(), LinkedHashMap.class);
		for (Iterator it = fieldAndValuesMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			String dbField = (String) entry.getKey();
			String value   = (String) entry.getValue();

			WSPropertyConfig pfmappng = wsVOConfig.getPropertyConfigById(dbField);
			
			if(pfmappng == null){
				continue;
			}
			
			Class propertyType = pfmappng.getType();
			Object valueForSet = null;
			if ((pfmappng.isVOCollectionsProperty() || pfmappng.isVOProperty()) && value.equals(REPLACE_SIGN)) {
				String childTableDataStr = (String) childTableData.get(dbField);
					
				String separator = WSConstant.Split.EDIT_DETAIL_RECORD_SEPARATOR;
				if(childTableDataStr.startsWith(WSConstant.Split.SECONDE_DETAIL_TABLE_START)){
					separator = WSConstant.Split.EDIT_DETAIL_DETAIL_RECORD_SEPARATOR;
				}
				childTableDataStr = childTableDataStr.substring(WSConstant.Split.DETAIL_TABLE_SIGN_LENGTH
												, childTableDataStr.length() - WSConstant.Split.DETAIL_TABLE_SIGN_LENGTH);
				if (StringUtils.isNotEmpty(childTableDataStr)) {

					if (ClassUtils.isCollection(propertyType)) {

						valueForSet = toCollection(childTableDataStr, pfmappng.getRealPropertyType(), propertyType, separator);

					} else if (ClassUtils.isArray(propertyType)) {

						valueForSet = toArray(childTableDataStr, propertyType.getComponentType(), separator);

					} else if (ClassUtils.isMap(propertyType)) {

						valueForSet = stringToMap(childTableDataStr, propertyType);

					} else {

						valueForSet = stringToVO(childTableDataStr, propertyType);

					}
				}
			} else {
				valueForSet = ConvertUtils.castToBaseType(value, propertyType);
			}
			if(pfmappng.getPropertySetter() != null){
				invoke(vo, pfmappng.getPropertySetter(), new Object[] { valueForSet });
			}else{
//				LOG.info("缺少" + pfmappng.getPropertyName() + "的set方法");
				continue;
			}
		}
		return vo;
	}

	public Map stringToMap(String string, Class mapClass) {
		if (!ClassUtils.isMap(mapClass)) {
			throw new ConvertException(mapClass.getName() + " not implement Map");
		}
		Map retMap = (Map) ClassUtils.newInstance(ClassUtils.getCollectionOrMapImplClass(mapClass));
		if (StringUtils.isEmpty(string)) {
			return retMap;
		}
		String[] fieldAndValuesArray = splitString(string, WSConstant.Split.EDIT_FIELD_SEPARATOR, 0);
		for (int i = 0; i < fieldAndValuesArray.length; i++) {
			String[] sepFieldAndValue = splitString(fieldAndValuesArray[i],
					WSConstant.Split.EDIT_FIELD_VALUE_SEPARATOR, 2);
			if (StringUtils.isNotEmpty(sepFieldAndValue[0])) {
				retMap.put(sepFieldAndValue[0], sepFieldAndValue.length < 2 ? null : sepFieldAndValue[1]);
			}
		}
		return retMap;
	}

	public Object[] stringToArray(String string, Class clazz) {
		return toArray(string, clazz, WSConstant.Split.ARRAY_SIGN);
	}

	public Collection stringToCollection(String string, Class clazz, Class collectionsClass) {
		return toCollection(string, clazz, collectionsClass, WSConstant.Split.ARRAY_SIGN);
	}

	public String voToString(Object object, String showFields) {
		Map cacheMap = getCacheMap();
		handleVO(object, showFields, null, cacheMap);
		return convertMapValueToString(cacheMap);
	}

	public String mapToString(Map map, String showFields) {
		Map cacheMap = getCacheMap();
		handleMap(map, showFields, cacheMap, getCacheMapKey());
		return convertMapValueToString(cacheMap);
	}
	
	public String arrayToString(Object[] objectArray, String showFields) {
		Map cacheMap = getCacheMap();
		handleArray(objectArray, showFields, cacheMap);
		return convertMapValueToString(cacheMap);
	}

	
	
	public String collectionToString(Collection collection, String showFields) {
		Map cacheMap = getCacheMap();
		handleCollection(collection, showFields, cacheMap);
		return convertMapValueToString(cacheMap);
	}

	/**
	 * 获得行分隔符号
	 * @return
	 */
	protected String getRowSplitSign(){
		return WSConstant.Split.GRID_RECORD_SEPARATOR;
	}

	/**
	 * 分隔VO字符串
	 * 
	 * @param voStrs
	 * @param recordSplit
	 * @return
	 */
	protected String[] splitString(String voStrs, String recordSplit, int defaultLen) {
		return StringUtils.isEmpty(voStrs) ? new String[defaultLen] : voStrs.split(recordSplit);
	}

	/**
	 * 
	 * @param cacheMap
	 * @param key
	 * @param content
	 */
	private void setInCacheMap(Map cacheMap, String key, StringBuffer content){
		StringBuffer contentInMap = (StringBuffer) cacheMap.get(key);
		if( contentInMap == null ){
			cacheMap.put(key, content);
		}else{
			contentInMap.append(content).append(WSConstant.Split.GRID_RECORD_SEPARATOR);
		}
	}
	

	/**
	 * 
	 * @param string
	 * @param clazz
	 * @param recordSplit
	 *            行分隔符
	 * @return
	 */
	private Object[] toArray(String string, Class clazz, String recordSplit) {
		checkIsWSVoOrMap(clazz);
		String[] voStrArray = splitString(string, recordSplit, 0);
		Object voArray = Array.newInstance(clazz, voStrArray.length);
		for (int i = 0; i < Array.getLength(voArray); i++) {
			Array.set(voArray, i, stringTo(voStrArray[i], clazz));
		}
		return (Object[]) voArray;
	}

	/**
	 * 
	 * @param string
	 * @param clazz
	 * @param collectionsClass
	 * @param recordSplit
	 * @return
	 */
	private Collection toCollection(String string, Class clazz, Class collectionsClass, String recordSplit) {
		checkIsWSVoOrMap(clazz);
		String[] voStrArray = splitString(string, recordSplit, 0);
		Object voCollection = ClassUtils.newInstance(ClassUtils.getCollectionOrMapImplClass(collectionsClass));
		for (int i = 0; i < voStrArray.length; i++) {
			((Collection) voCollection).add(stringTo(voStrArray[i], clazz));
		}
		return (Collection) voCollection;
	}

	/**
	 * 
	 * @param string
	 * @param clazz
	 * @return
	 */
	private Object stringTo(String string, Class clazz) {
		return ClassUtils.isMap(clazz) ? stringToMap(string, clazz) : stringToVO(string, clazz);
	}
	
	/**
	 * 
	 * @param objectArray
	 * @param showFields
	 * @param cacheMap
	 */
	private void handleArray(Object[] objectArray, String showFields, Map cacheMap) {
		if (objectArray == null) {
			return ;
		}
		WSVOConfig voConfig = null;
		String mapKey = this.getCacheMapKey();
		for (int i = 0; i < objectArray.length; i++) {
			Object vo = objectArray[i];
			if (ObjectUtils.isMap(vo)) {
				handleMap((Map) vo, showFields, cacheMap, mapKey);
			} else {
				if (voConfig == null || !voConfig.getType().equals(vo.getClass())) {
					voConfig = getWSConfiguration().getWSVOConfig(vo.getClass());
				}
				handleVO(vo, showFields, voConfig, cacheMap);
			}
		}
		objectArray = null;
	}
	
	/**
	 * 
	 * @param collection
	 * @param showFields
	 * @param cacheMap
	 */
	private void handleCollection(Collection collection, String showFields, Map cacheMap) {
		if (collection == null) {
			return ;
		}
		WSVOConfig voConfig = null;
		String mapKey = this.getCacheMapKey();
		for (Iterator it = collection.iterator(); it.hasNext(); ) {
			Object vo = it.next();
			if (ObjectUtils.isMap(vo)) {
				handleMap((Map) vo, showFields, cacheMap, mapKey);
			} else if(ObjectUtils.isArray(vo)){
				
				handleNormalArray((Object[])vo, showFields, cacheMap, mapKey);
				
			} else {
				if (voConfig == null || !voConfig.getType().equals(vo.getClass())) {
					voConfig = getWSConfiguration().getWSVOConfig(vo.getClass());
				}
				handleVO(vo, showFields, voConfig, cacheMap);
			}
		}
	}
	
	/**
	 * 
	 * @param normalArray
	 * @param showFields
	 * @param cacheMap
	 * @param cacheMapKey
	 */
	private void handleNormalArray(Object[] normalArray, String showFields, Map cacheMap, String cacheMapKey){
		if(normalArray == null || normalArray.length <= 0){
			return ;
		}
		StringBuffer retString = new StringBuffer();
		if(cacheMap.get(cacheMapKey) == null){
			cacheMap.put(cacheMapKey, new StringBuffer());
		}
		for(int i = 0; i < normalArray.length; i++){
			String value = ConvertUtils.castToString( normalArray[i] );
			if(i == normalArray.length - 1){
				retString.append(value);
			}else{
				retString.append(value).append(WSConstant.Split.GRID_VALUE_SEPARATOR);
			}
		}
		this.setInCacheMap(cacheMap, cacheMapKey, retString);
		normalArray = null;
	}
	
	/**
	 * 
	 * @param map
	 * @param showFields
	 * @param cacheMap
	 * @param cacheMapKey
	 */
	private void handleMap(Map map, String showFields, Map cacheMap, String cacheMapKey){
		if (map == null || map.isEmpty()) {
			return ;
		}
		String[] showFieldsArray = null;
		if (StringUtils.isEmpty(showFields)) {
			showFieldsArray = (String[]) map.keySet().toArray(new String[] {});
		} else {
			showFieldsArray = splitString(showFields, WSConstant.Split.COMMA_SPLIT, 0);
		}
		StringBuffer retString = new StringBuffer();
		if(cacheMap.get(cacheMapKey) == null){
			cacheMap.put(cacheMapKey, new StringBuffer());
		}
		for (int i = 0; i < showFieldsArray.length; i++) {
			String value = ConvertUtils.castToString(map.get(showFieldsArray[i]));
			if (i == showFieldsArray.length - 1) {
				retString.append(value);
			} else {
				retString.append(value).append(WSConstant.Split.GRID_VALUE_SEPARATOR);
			}
		}
		this.setInCacheMap(cacheMap, cacheMapKey, retString);
	}
	
	/**
	 * 
	 * @param vo
	 * @param showFields
	 * @param voTableMapping
	 * @return
	 */
	private void handleVO(Object vo, String showFields, WSVOConfig voConfig, Map cacheMap) {
		if (voConfig == null) {
			voConfig = getWSConfiguration().getWSVOConfig(vo.getClass());
		}
		StringBuffer showFieldsSB = new StringBuffer(showFields == null ? "" : showFields);
		Map showFieldsMap = convertDetailTableShowFields(showFieldsSB);
		StringBuffer retStringBuffer = new StringBuffer();
		List propertyMappings = voConfig.getShowPropertyConfig(showFieldsSB.toString());
		
		if(cacheMap.get(voConfig.getId()) == null){
			cacheMap.put(voConfig.getId(), new StringBuffer());
		}
		
		for (int i = 0; i < propertyMappings.size(); i++) {
			WSPropertyConfig ptfMapping = (WSPropertyConfig) propertyMappings.get(i);
			Object value = invoke(vo, ptfMapping.getPropertyGetter(), null);
		 
			String tempShowFields = (String) showFieldsMap.get(ptfMapping.getId());
			
			if(StringUtils.isEmpty(tempShowFields)){
				
				String valueStr = ConvertUtils.castToString(value);
				if(valueStr.indexOf(WSConstant.Split.GRID_RECORD_SEPARATOR) >= 0){
					valueStr = valueStr.replaceAll("\n", "");
				}
				if(valueStr.indexOf("\r") >= 0){
					valueStr = valueStr.replaceAll("\r", "");
				}
				retStringBuffer.append(valueStr);
				
			}else{
				
//				String   startSign      = tempShowFields.substring(0, WSConstant.Split.DETAIL_TABLE_SIGN_LENGTH);
//				String   endSign        = tempShowFields.substring(tempShowFields.length() - WSConstant.Split.DETAIL_TABLE_SIGN_LENGTH, tempShowFields.length());
				tempShowFields          = tempShowFields.substring(WSConstant.Split.DETAIL_TABLE_SIGN_LENGTH, tempShowFields.length() - WSConstant.Split.DETAIL_TABLE_SIGN_LENGTH); 
//				retStringBuffer.append(startSign);
				if (ObjectUtils.isCollection(value)) {
						
					handleCollection((Collection) value, tempShowFields, cacheMap);

				} else if (ObjectUtils.isVOArray(value)) {

					handleArray((VOInterface[]) value, tempShowFields, cacheMap);

				} else if (ObjectUtils.isVO(value)) {
					
					handleVO((VOInterface) value, tempShowFields, null, cacheMap);
					
				}
		
//				retStringBuffer.append(endSign);
			}
			if(i < propertyMappings.size() - 1){	
				retStringBuffer.append(WSConstant.Split.GRID_VALUE_SEPARATOR);
			}
		}
		setInCacheMap(cacheMap, voConfig.getId(), retStringBuffer);
	}
	
	/**
	 * 把从表的字符串放入Map, 并把原字符串的从表部分替换成"***"
	 * 
	 * @param voStrBuffer
	 * @return
	 */
	private static Map convertDetailTableEditData(StringBuffer voStrBuffer) {
		Map data = new HashMap();
		String startSign = voStrBuffer.indexOf(WSConstant.Split.DETAIL_TABLE_START) > 0 
									? WSConstant.Split.DETAIL_TABLE_START : WSConstant.Split.SECONDE_DETAIL_TABLE_START; 
		String endSign   = voStrBuffer.indexOf(WSConstant.Split.DETAIL_TABLE_END) > 0
									? WSConstant.Split.DETAIL_TABLE_END : WSConstant.Split.SECONDE_DETAIL_TABLE_END;
		while (voStrBuffer.indexOf(startSign) > 0 && voStrBuffer.indexOf(endSign) > 0) {
			int startChildSignIndex = voStrBuffer.indexOf(startSign);
			int endChildSignIndex = voStrBuffer.indexOf(endSign);

			String frontPart = voStrBuffer.substring(0, startChildSignIndex);
			//包含{}
			String behindPart = voStrBuffer.substring(startChildSignIndex , endChildSignIndex + WSConstant.Split.DETAIL_TABLE_SIGN_LENGTH);

			String childTableName = frontPart.substring(
					frontPart.lastIndexOf(WSConstant.Split.EDIT_FIELD_SEPARATOR) + WSConstant.Split.DETAIL_TABLE_SIGN_LENGTH + 1, startChildSignIndex - WSConstant.Split.DETAIL_TABLE_SIGN_LENGTH - 1);

			data.put(childTableName, behindPart);
			// 替换时包含{}
			voStrBuffer.replace(startChildSignIndex, endChildSignIndex + WSConstant.Split.DETAIL_TABLE_SIGN_LENGTH, REPLACE_SIGN);
		}
		return data;
	}
	
	/**
	 * 
	 * @param voStrBuffer
	 * @return
	 */
	private Map convertDetailTableShowFields(StringBuffer voStrBuffer){
		Map retMap = new HashMap();
		String startSign = voStrBuffer.indexOf(WSConstant.Split.DETAIL_TABLE_START) > 0 
								? WSConstant.Split.DETAIL_TABLE_START : WSConstant.Split.SECONDE_DETAIL_TABLE_START; 
		String endSign   = voStrBuffer.indexOf(WSConstant.Split.DETAIL_TABLE_END) > 0
								? WSConstant.Split.DETAIL_TABLE_END : WSConstant.Split.SECONDE_DETAIL_TABLE_END;
		while(voStrBuffer.indexOf(startSign) > 0 && voStrBuffer.indexOf(endSign) > 0){
			int startSignIndex = voStrBuffer.indexOf(startSign);
			int endSignIndex   = voStrBuffer.indexOf(endSign) ;
			
			String beforeStartSign = voStrBuffer.substring(0, startSignIndex);
			String endStartSign = voStrBuffer.substring(startSignIndex, voStrBuffer.length()) ;
			
			String key =  beforeStartSign.substring(beforeStartSign.lastIndexOf(WSConstant.Split.COMMA_SPLIT) + 1, beforeStartSign.length());

			String value = endStartSign.substring(0, endStartSign.indexOf(endSign) + WSConstant.Split.DETAIL_TABLE_SIGN_LENGTH);
			voStrBuffer.delete(startSignIndex, endSignIndex + WSConstant.Split.DETAIL_TABLE_SIGN_LENGTH);
			retMap.put(key, value);
		}
		return retMap;
	}
	
	private String convertMapValueToString(Map map){
		Collection mapValues = map.values();
		StringBuffer retBuffer = new StringBuffer();
		for(Iterator it = mapValues.iterator(); it.hasNext(); ){
			StringBuffer content = (StringBuffer) it.next();
			if(content.toString().endsWith(WSConstant.Split.GRID_RECORD_SEPARATOR)){
				content.delete(content.length() - 1, content.length()); 
			}
			if(it.hasNext()){
				retBuffer.append(content).append(WSConstant.Split.ARRAY_SIGN);
			}else{
				retBuffer.append(content);
			}
		}
		return retBuffer.toString();
	}
	
	private Map getCacheMap(){
		return new LinkedHashMap();
	}
	
	private String getCacheMapKey(){
		return String.valueOf(new Random().nextLong());
	}
}
