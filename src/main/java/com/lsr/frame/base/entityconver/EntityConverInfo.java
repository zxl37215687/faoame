package com.lsr.frame.base.entityconver;

/**
 * 
 * povo转换详细信息
 * @author kuaihuolin
 * 
 */
public class EntityConverInfo {
	/**
	 * po属性名
	 */
	private String[] poPropertyName;
	/**
	 * po属性Getter方法
	 */
	private String[] poPropertyGetMethodName;
	/**
	 * po属性Getter方法index,如a.b.c，则对应getA()方法index,getB()方法index,getC()方法index
	 */
	private int[] poPropertyGetMethodIndex;
	/**
	 * po属性Setter方法
	 */
	private String[] poPropertySetMethodName;
	/**
	 * po属性Setter方法index
	 */
	private int[] poPropertySetMethodIndex;
	
	/**
	 * po属性类型
	 */
	private Class[] poPropertyClassType;
	
	public Class[] getPoPropertyClassType() {
		return poPropertyClassType;
	}

	public void setPoPropertyClassType(Class[] poPropertyClassType) {
		this.poPropertyClassType = poPropertyClassType;
	}

	/**
	 * po属性类型
	 */
	private DataType poPropertyType;
	
	/**
	 * po属性名index
	 */
	private int poPropertyNameIndex;
	
	/**
	 * vo属性名
	 */
	private String voPropertyName;
	/**
	 * vo属性Getter方法
	 */
	private String voPropertyGetMethodName;
	/**
	 * vo属性Getter方法index
	 */
	private int voPropertyGetMethodIndex;
	/**
	 * vo属性Setter方法
	 */
	private String voPropertySetMethodName;
	/**
	 * vo属性Setter方法index
	 */
	private int voPropertySetMethodIndex;
	
	/**
	 * vo属性类型
	 */
	private DataType voPropertyType;

	/**
	 * vo属性名index
	 */
	private int voPropertyNameIndex;
	
	public int getPoPropertyNameIndex() {
		return poPropertyNameIndex;
	}

	public void setPoPropertyNameIndex(int poPropertyNameIndex) {
		this.poPropertyNameIndex = poPropertyNameIndex;
	}

	public int getVoPropertyNameIndex() {
		return voPropertyNameIndex;
	}

	public void setVoPropertyNameIndex(int voPropertyNameIndex) {
		this.voPropertyNameIndex = voPropertyNameIndex;
	}

	public int[] getPoPropertyGetMethodIndex() {
		return poPropertyGetMethodIndex;
	}

	public void setPoPropertyGetMethodIndex(int[] poPropertyGetMethodIndex) {
		this.poPropertyGetMethodIndex = poPropertyGetMethodIndex;
	}


	public int getVoPropertyGetMethodIndex() {
		return voPropertyGetMethodIndex;
	}

	public void setVoPropertyGetMethodIndex(int voPropertyGetMethodIndex) {
		this.voPropertyGetMethodIndex = voPropertyGetMethodIndex;
	}

	public int getVoPropertySetMethodIndex() {
		return voPropertySetMethodIndex;
	}

	public void setVoPropertySetMethodIndex(int voPropertySetMethodIndex) {
		this.voPropertySetMethodIndex = voPropertySetMethodIndex;
	}


	public String getVoPropertyName() {
		return voPropertyName;
	}

	public void setVoPropertyName(String voPropertyName) {
		this.voPropertyName = voPropertyName;
	}

	public String[] getPoPropertyGetMethodName() {
		return poPropertyGetMethodName;
	}

	public void setPoPropertyGetMethodName(String[] poPropertyGetMethodName) {
		this.poPropertyGetMethodName = poPropertyGetMethodName;
	}

	public String getVoPropertyGetMethodName() {
		return voPropertyGetMethodName;
	}

	public void setVoPropertyGetMethodName(String voPropertyGetMethodName) {
		this.voPropertyGetMethodName = voPropertyGetMethodName;
	}

	public String getVoPropertySetMethodName() {
		return voPropertySetMethodName;
	}

	public void setVoPropertySetMethodName(String voPropertySetMethodName) {
		this.voPropertySetMethodName = voPropertySetMethodName;
	}

	public DataType getPoPropertyType() {
		return poPropertyType;
	}

	public void setPoPropertyType(DataType poPropertyType) {
		this.poPropertyType = poPropertyType;
	}

	public DataType getVoPropertyType() {
		return voPropertyType;
	}

	public void setVoPropertyType(DataType voPropertyType) {
		this.voPropertyType = voPropertyType;
	}

	public String[] getPoPropertyName() {
		return poPropertyName;
	}

	public void setPoPropertyName(String[] poPropertyName) {
		this.poPropertyName = poPropertyName;
	}

	public String[] getPoPropertySetMethodName() {
		return poPropertySetMethodName;
	}

	public void setPoPropertySetMethodName(String[] poPropertySetMethodName) {
		this.poPropertySetMethodName = poPropertySetMethodName;
	}

	public int[] getPoPropertySetMethodIndex() {
		return poPropertySetMethodIndex;
	}

	public void setPoPropertySetMethodIndex(int[] poPropertySetMethodIndex) {
		this.poPropertySetMethodIndex = poPropertySetMethodIndex;
	}

}
