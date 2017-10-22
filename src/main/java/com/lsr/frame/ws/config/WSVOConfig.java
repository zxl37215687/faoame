package com.lsr.frame.ws.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lsr.frame.ws.common.WSConstant;
import com.lsr.frame.ws.utils.ClassUtils;

/**
 * 
 * @author kuaihuolin
 *
 */
public class WSVOConfig implements WSConfig {

	/**
	 * ID
	 */
	private String id;
	/**
	 * VO class
	 */
	private Class  type;
	/**
	 * VO名称
	 */
	private String typeName;
	/**
	 * 该VO的属性配置
	 */
	private Map    propertyConfigMap         = new LinkedHashMap(); 
	
	private Map    commonPropertyConfigCache = new HashMap();
	
	/**
	 * 
	 * @param id
	 * @param voType
	 */
	public WSVOConfig(String id, String typeName) {
		super();
		this.id = id;
		this.typeName = typeName;
		this.type = ClassUtils.forName(typeName);
	}

	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public Class getType() {
		return type;
	}
	public void setType(Class type) {
		this.type = type;
	}
	
	/**
	 * 增加该VO的属性配置
	 * @param propertyTableFieldMapping
	 */
	public void addPropertyConfig(WSPropertyConfig propertyTableFieldMapping) {
		propertyTableFieldMapping.setParent( this );
		propertyConfigMap.put(propertyTableFieldMapping.getId(), propertyTableFieldMapping);
	}
	
	/**
	 * 获得属性配置
	 * @param fieldName 属性配置的字段名称
	 * @return 
	 */
	public WSPropertyConfig getPropertyConfigById(String fieldName){
		return (WSPropertyConfig)propertyConfigMap.get(fieldName);
	}
	
	/**
	 * 获得所有属性配置
	 * @return
	 */
	public Collection getAllPropertyConfigs() {
		return propertyConfigMap.values();
	}
	
	/**
	 * 
	 * @return
	 */
	public List getVOPropertyConfigs(){
		List voMapping = new ArrayList();
		Collection allPropertyMappings = this.getAllPropertyConfigs();
		for(Iterator it = allPropertyMappings.iterator(); it.hasNext(); ){
			WSPropertyConfig p = (WSPropertyConfig) it.next();
			if(p.isVOProperty()){
				voMapping.add(p);
			}
		}
		return voMapping;
	}
	
	/**
	 * 
	 * @return
	 */
	public List getVOCollectionsPropertyConfigs(){
		List voMapping = new ArrayList();
		Collection allPropertyMappings = this.getAllPropertyConfigs();
		for(Iterator it = allPropertyMappings.iterator(); it.hasNext(); ){
			WSPropertyConfig p = (WSPropertyConfig) it.next();
			if(p.isVOCollectionsProperty()){
				voMapping.add(p);
			}
		}
		return voMapping;
	}
	
	/**
	 * 
	 * @return
	 */
	public List getDetailVOPropertyConfigs(){
		List voMapping = new ArrayList();
		Collection allPropertyMappings = this.getAllPropertyConfigs();
		for(Iterator it = allPropertyMappings.iterator(); it.hasNext(); ){
			WSPropertyConfig p = (WSPropertyConfig) it.next();
			if(p.isVOCollectionsProperty() || p.isVOProperty()){
				voMapping.add(p);
			}
		}
		return voMapping;
	}
	
	/**
	 * 
	 * @return
	 */
	public List getNormalPropertyConfigs(){
		List voMapping = new ArrayList();
		Collection allPropertyMappings = this.getAllPropertyConfigs();
		for(Iterator it = allPropertyMappings.iterator(); it.hasNext(); ){
			WSPropertyConfig p = (WSPropertyConfig) it.next();
			if( !p.isVOCollectionsProperty() && !p.isVOProperty() ){
				voMapping.add(p);
			}
		}
		return voMapping;
	}
	
	/**
	 * 
	 * @param cacheKey
	 * @return
	 */
	private List getCommonPropertyConfigs(String cacheKey){
		if(cacheKey == null){
			return null;
		}
		return (List) commonPropertyConfigCache.get(cacheKey);
	}
	
	/**
	 * 
	 * @param cacheKey
	 * @param propertyMappings
	 */
	private void cacheCommonPropertyConfigs(String cacheKey, List propertyMappings){
		if(cacheKey == null){
			return;
		}
		commonPropertyConfigCache.clear();
		commonPropertyConfigCache.put(cacheKey, propertyMappings);
	}
	
	/**
	 * 
	 * @param showField
	 * @return
	 */
	public List getShowPropertyConfig(String showField){
		//根据tempShowField在voTableMapping中找propertyMappings
		List propertyMappings = getCommonPropertyConfigs(showField);
		if(propertyMappings == null) {
			//如果tempShowField为空处理所有普通数据
			propertyMappings = new ArrayList();
		}
		if(propertyMappings.isEmpty()){
			if(StringUtils.isEmpty(showField)){
				propertyMappings = getNormalPropertyConfigs();
			}else{
				//不为空处理tempShowField中的字段
				String[] tempShowFieldArray = showField.split( WSConstant.Split.COMMA_SPLIT );
				for(int j = 0; j < tempShowFieldArray.length; j++){
					WSPropertyConfig propertyMapping = getPropertyConfigById( tempShowFieldArray[j] );
					if(propertyMapping != null && !propertyMapping.isVOProperty()){
						propertyMappings.add( propertyMapping );
					}
				}
			}
			cacheCommonPropertyConfigs(showField, propertyMappings);
		}
		return propertyMappings;
	}

	public Object getObject() {
		return ClassUtils.newInstance(type);
	}

	public WSConfig getParent() {
		return null;
	}

}
