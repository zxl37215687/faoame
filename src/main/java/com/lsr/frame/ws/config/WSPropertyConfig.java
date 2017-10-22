package com.lsr.frame.ws.config;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lsr.frame.ws.exception.WSConfigException;
import com.lsr.frame.ws.utils.ClassUtils;
import com.lsr.frame.ws.utils.MethodUtils;

public class WSPropertyConfig implements WSConfig {

private static final Log LOG = LogFactory.getLog(WSPropertyConfig.class);
	
	/**
	 * 字段名称
	 */
	private String id;

	/**
	 * VO属性名称
	 */
	private String propertyName;
	/**
	 * 真实类型,用于制定集合元素类型
	 */
	private Class realType;

	private String realTypeName;
	/**
	 * 属性getter方法
	 */
	private Method propertyGetter;
	/**
	 * 属性setter方法
	 */
	private Method propertySetter;
	/**
	 * VO配置
	 */
	private WSVOConfig parent;
	/**
	 * 是否VO集合属性,一般是明细VO
	 */
	private boolean isVOCollectionsProperty = false;
	/**
	 * 是否VO属性
	 */
	private boolean isVOProperty = false;

	/**
	 * 
	 * @param fieldName
	 * @param fieldType
	 * @param propertyName
	 * @param realPropertyType
	 */
	public WSPropertyConfig(String id, String propertyName, String realTypeName, WSVOConfig parent) {
		super();
		this.id = id;
		this.propertyName = propertyName;
		this.realTypeName = realTypeName;
		if (StringUtils.isNotEmpty(realTypeName)) {
			this.realType = ClassUtils.forName(realTypeName);
		}
		this.setParent(parent);
	}

	/**
	 * 
	 * @param id
	 * @param propertyName
	 * @param realTypeName
	 */
	public WSPropertyConfig(String id, String propertyName, String realTypeName) {
		this(id, propertyName, realTypeName, null);
	}

	/**
	 * 
	 * @param fieldName
	 * @param propertyName
	 */
	public WSPropertyConfig(String id, String propertyName) {
		this(id, propertyName, null);
	}

	public WSConfig getParent() {
		return parent;
	}

	public void setParent(WSVOConfig parent) {
		this.parent = parent;
		if (this.parent != null) {
			initMethod();
		}
	}

	public String getId() {
		return id;
	}

	public void setFieldName(String id) {
		this.id = id;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Class getRealPropertyType() {
		return realType;
	}

	public void setRealPropertyType(Class realPropertyType) {
		this.realType = realPropertyType;
	}

	public Method getPropertyGetter() {
		return propertyGetter;
	}

	public Method getPropertySetter() {
		return propertySetter;
	}

	/**
	 * 初始化该VO属性的getter方法,setter方法
	 */
	private void initMethod() {
		Class voType = parent.getType();
		propertyGetter = MethodUtils.getGetter(voType, propertyName);
		try{
			propertySetter = MethodUtils.getSetter(voType, propertyName, propertyGetter.getReturnType());
		}catch(Exception ex){
			LOG.info(ex);
		}
		if (realType != null) {
			isVOCollectionsProperty = ClassUtils.isVO(realType)
					&& ClassUtils.isCollection(propertyGetter.getReturnType());
		} else {
			isVOCollectionsProperty = ClassUtils.isArray(propertyGetter.getReturnType())
					&& ClassUtils.isVO(propertyGetter.getReturnType().getComponentType());
		}
		isVOProperty = ClassUtils.isVO(propertyGetter.getReturnType());
	}

	public boolean isVOCollectionsProperty() {
		return isVOCollectionsProperty;
	}

	public boolean isVOProperty() {
		return isVOProperty;
	}

	public Object getObject() {
		try {
			return parent.getType().getDeclaredField("propertyName");
		} catch (Exception ex) {
			throw new WSConfigException(ex);
		}
	}

	public Class getType() {
		return propertyGetter.getReturnType();
	}

	public String getTypeName() {
		return propertyGetter.getReturnType().getName();
	}

	public String getRealTypeName() {
		return realTypeName;
	}

}
