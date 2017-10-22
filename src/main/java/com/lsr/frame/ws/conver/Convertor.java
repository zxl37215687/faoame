package com.lsr.frame.ws.conver;

import java.util.Collection;
import java.util.Map;

import com.lsr.frame.ws.exception.ConvertException;

public interface Convertor {

	/**
	 * 字符串转换成WSVO
	 * 
	 * @param string
	 *            需转换的字符串, 必须具有一定的格式
	 * @param clazz
	 *            转换后的类型, 必须是WSVO类型
	 * @return
	 * @throws ConvertException
	 */
	Object stringToVO(String string, Class clazz) throws ConvertException;

	/**
	 * 字符串转换成Map
	 * 
	 * @param string
	 *            需转换的字符串, 必须具有一定的格式
	 * @param clazz
	 *            转换后的类型, 必须是Map类型
	 * @return
	 * @throws ConvertException
	 */
	Map stringToMap(String string, Class clazz) throws ConvertException;

	/**
	 * 字符串转换成Collection
	 * 
	 * @param string
	 *            需转换的字符串, 必须具有一定的格式
	 * @param clazz
	 *            转换后的集合的元素类型(WSVO类型或Map类型)
	 * @param collectionsClass
	 *            转换后的集合类型
	 * @return
	 * @throws ConvertException
	 */
	Collection stringToCollection(String string, Class clazz, Class collectionsClass) throws ConvertException;

	/**
	 * 字符串转换成Object[]
	 * 
	 * @param string
	 *            需转换的字符串, 必须具有一定的格式
	 * @param clazz
	 *            转换后的数组类型(WSVO类型或Map类型)
	 * @return
	 * @throws ConvertException
	 */
	Object[] stringToArray(String string, Class clazz) throws ConvertException;

	/**
	 * WSVO转换成字符串
	 * 
	 * @param object
	 *            需要转换的WSVO
	 * @param showFields
	 *            转换后的字符串包含的VO属性, 每个属性采用逗号分隔
	 * @return
	 * @throws ConvertException
	 */
	String voToString(Object object, String showFields) throws ConvertException;

	/**
	 * Map转换成字符串
	 * 
	 * @param map
	 *            需要转换的Map
	 * @param showFields
	 *            map的key, 每个属性使用逗号分隔
	 * @return
	 * @throws ConvertException
	 */
	String mapToString(Map map, String showFields) throws ConvertException;

	/**
	 * Object[]转换成字符串
	 * 
	 * @param objectArray
	 *            需要转换的数组(元素可以是WSVO或Map)
	 * @param showFields
	 *            WSVO: 转换后的字符串包含的VO属性, 每个属性采用逗号分隔; Map: map的key, 每个属性使用逗号分隔
	 * @return
	 * @throws ConvertException
	 */
	String arrayToString(Object[] objectArray, String showFields) throws ConvertException;

	/**
	 * Collection转换成字符串
	 * 
	 * @param collection
	 *            需要转换的集合(元素可以是WSVO或Map)
	 * @param showFields
	 *            WSVO: 转换后的字符串包含的VO属性, 每个属性采用逗号分隔; Map: map的key, 每个属性使用逗号分隔
	 * @return
	 * @throws ConvertException
	 */
	String collectionToString(Collection collection, String showFields) throws ConvertException;

}
