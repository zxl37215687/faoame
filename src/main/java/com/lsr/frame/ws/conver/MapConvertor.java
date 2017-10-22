package com.lsr.frame.ws.conver;

import java.util.Map;

public interface MapConvertor {
	/**
	 * Map转成列用\t分隔,行用\n分隔的字符串,Map的key为第一列, value为第二列, 如果value为集合,可以扩展成多行
	 * 
	 * @param map
	 *            需要转换成字符串的Map
	 * @return
	 */
	String toString(Map map);

	/**
	 * 把格式为名称1::值1##名称2::值2的字符串转换成Map
	 * 
	 * @param string
	 *            需要转换成Map的字符串
	 * @param mapClass
	 *            转换成Map的类型
	 * @param collectionClass
	 *            如果不为空,Map的value为该集合类型的实例, 如果字符串中存在相同的名称, 可以把值放入该集合
	 * @return
	 */
	Map toMap(String string, Class mapClass, Class collectionClass);
}
