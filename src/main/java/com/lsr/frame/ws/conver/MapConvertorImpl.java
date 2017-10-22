package com.lsr.frame.ws.conver;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import com.lsr.frame.ws.common.WSConstant;
import com.lsr.frame.ws.utils.ClassUtils;
import com.lsr.frame.ws.utils.ConvertUtils;
import com.lsr.frame.ws.utils.ObjectUtils;

public class MapConvertorImpl extends DefaultConvertor implements MapConvertor{
	public String toString(Map map) {
		if(map == null){
			return "";
		}
		StringBuffer strBuffer = new StringBuffer();
		for(Iterator it = map.entrySet().iterator(); it.hasNext(); ){
			Map.Entry entry = (Map.Entry) it.next();
			String key = entry.getKey().toString();
			Object oValue = entry.getValue();
			if(ObjectUtils.isArray(oValue)){
				Object[] array = (Object[]) oValue;
				for(int i = 0; i < array.length; i++){
					strBuffer.append(key)
								.append(WSConstant.Split.GRID_VALUE_SEPARATOR)
								.append(ConvertUtils.castToString(array[i]));
					if(i < array.length - 1){
						strBuffer.append(WSConstant.Split.GRID_RECORD_SEPARATOR);
					}
				}
			}else if(ObjectUtils.isCollection(oValue)){
				Collection collection = (Collection) oValue;
				for(Iterator init = collection.iterator(); init.hasNext(); ){
					strBuffer.append(key)
								.append(WSConstant.Split.GRID_VALUE_SEPARATOR)
								.append(ConvertUtils.castToString(init.next()));
					if(init.hasNext()){
						strBuffer.append(WSConstant.Split.GRID_RECORD_SEPARATOR);
					}
				}
			}else{
				strBuffer.append(key)
							.append(WSConstant.Split.GRID_VALUE_SEPARATOR)
							.append(ConvertUtils.castToString(oValue));
			}
			
			if(it.hasNext()){
				strBuffer.append(WSConstant.Split.GRID_RECORD_SEPARATOR);
			}
		}
		return strBuffer.toString();
	}
	
	public Map toMap(String string, Class mapClass, Class collectionClass) {
		Map retMap = (Map) ClassUtils.newInstance(ClassUtils.getCollectionOrMapImplClass(mapClass));
		String[] fieldAndValuesArray = splitString(string, WSConstant.Split.ARRAY_SIGN, 0);
		for (int i = 0; i < fieldAndValuesArray.length; i++) {
			String[] sepFieldAndValue = splitString(fieldAndValuesArray[i], WSConstant.Split.EDIT_FIELD_VALUE_SEPARATOR, 2);
			String key   = sepFieldAndValue[0] == null ? "" : sepFieldAndValue[0];
			String value = sepFieldAndValue.length < 2 ? null : sepFieldAndValue[1];
			if(collectionClass != null){
				Collection collInMap = (Collection) retMap.get(key);
				if(collInMap == null){
					collInMap = (Collection)ClassUtils.newInstance(collectionClass);
					retMap.put(key, collInMap);
				}
				collInMap.add(value);
			}else{
				retMap.put(key, value);
			}
		}
		return retMap;
	}
}
