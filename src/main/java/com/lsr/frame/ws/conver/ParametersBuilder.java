package com.lsr.frame.ws.conver;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lsr.frame.ws.common.WSConstant;
import com.lsr.frame.ws.utils.ConvertUtils;
import com.lsr.frame.ws.utils.ObjectUtils;

/**
 * 参数解析器
 * @author kuaihuolin
 *
 */
public class ParametersBuilder {
	public static Parameters createParameters(String[] source){
		
		if(source == null || source.length == 0){
			return new Parameters();
		}
		
		String[][] sourceArray = new String[source.length][2];
		for(int i = 0; i < source.length; i++){
			String parameter = source[i];
			if(StringUtils.isEmpty(parameter)){
				continue;
			}
			String[] nameAndValue = parameter.split(WSConstant.Split.PARAMETER_SPLIT);
			sourceArray[i][0] = nameAndValue[0];
			sourceArray[i][1] = nameAndValue.length <= 1 ? "" : nameAndValue[1];
			if(StringUtils.isNotBlank(sourceArray[i][1])){
				//把参数值中的tab键\t换成两个空格
				sourceArray[i][1] = sourceArray[i][1].replaceAll(WSConstant.Split.GRID_VALUE_SEPARATOR, "  ");
			}
		}
		Parameters parameter = createParameters(sourceArray);
		parameter.setSourceString(source);
		return null;
	}
	
	/**
	 * 创建参数
	 * 
	 * @param sourceArray
	 * @param convertor
	 * @return
	 */
	public static Parameters createParameters(String[][] sourceArray) {
		return new Parameters(sourceArray);
	}
	
	/**
	 * 还原参数
	 * 
	 * @param parameter
	 * @return
	 */
	public static String[] revertToStringArray(Map parameters) {
		if (parameters == null || parameters.size() <= 0) {
			return new String[]{};
		}
		String[] retStrArray = new String[parameters.size()];
		int i = 0;
		for (Iterator it = parameters.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			Object oKey = entry.getKey();
			Object oValue = entry.getValue() == null ? "" : entry.getValue();
			if (ObjectUtils.isArray(oValue)) {
				oValue = ConvertUtils.castArrayToString((Object[])oValue);
			}else if(ObjectUtils.isCollection(oValue)){
				oValue = ConvertUtils.castCollectionToString((Collection)oValue);
			}
			retStrArray[i++] = oKey.toString() + WSConstant.Split.PARAMETER_SPLIT + oValue.toString();
		}
		parameters.clear();
		return retStrArray;
	}
	
	/**
	 * 还原参数
	 * 
	 * @param parameters
	 * @return
	 */
	public static String[][] revertToPlanarStringArray(Map parameters) {
		String[] strArray = revertToStringArray(parameters);
		String[][] retStrArray = new String[strArray.length][2];
		if(strArray.length <= 0){
			return retStrArray;
		}
		for(int i = 0; i < retStrArray.length; i++){
			String value = strArray[i];
			String[] valueArray = value.split(WSConstant.Split.PARAMETER_SPLIT);
			retStrArray[i][0] = valueArray[0];
			retStrArray[i][1] = valueArray[1];
		}
		return retStrArray;
	}
}
