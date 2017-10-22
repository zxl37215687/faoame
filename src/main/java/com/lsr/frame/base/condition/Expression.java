package com.lsr.frame.base.condition;

import org.apache.commons.lang3.StringUtils;

public class Expression {
	/**
	 * like查询
	 * @param name 字段名称
	 * @param value 值
	 * @return 字段条件
	 */
	public static FieldCondition like(String name, String value){
		if(StringUtils.isBlank(value) || StringUtils.isBlank(name)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.LIKE);
		fc.setName(name);
		fc.setValue(value);
		return fc;
	}
	
	/**
	 * blike查询
	 * @param name 字段名称
	 * @param value 值
	 * @return 字段条件
	 */
	public static FieldCondition elike(String name, String value){
		if(StringUtils.isBlank(value) || StringUtils.isBlank(name)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.ELIKE);
		fc.setName(name);
		fc.setValue(value);
		return fc;
	}
	/**
	 * elike查询
	 * @param name 字段名称
	 * @param value 值
	 * @return 字段条件
	 */
	public static FieldCondition blike(String name, String value){
		if(StringUtils.isBlank(value) || StringUtils.isBlank(name)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.BLIKE);
		fc.setName(name);
		fc.setValue(value);
		return fc;
	}
	
	/**
	 * 等于条件
	 * @param name 字段名称
	 * @param value 值
	 * @param dbType 数据库类型 
	 * @return 字段条件
	 */
	public static FieldCondition eq( String name, String value,String dbType){
		if(StringUtils.isBlank(value)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.EQ);
		fc.setDbType(dbType);
		fc.setName(name);
		fc.setValue(value);
		return fc;
	}

	/**
	 * 不等于
	 * @param name 字段名称
	 * @param value 值
	 * @param dbType 数据库类型 
	 * @return 字段条件
	 */
	public static FieldCondition ne(String name, String value,String dbType){
		if(StringUtils.isBlank(value)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.NE);
		fc.setDbType(dbType);
		fc.setName(name);
		fc.setValue(value);
		return fc;
	}
	
	/**
	 * 大于条件
	 * @param name 字段名称
	 * @param value 值
	 * @param dbType 
	 * @return 字段条件
	 */
	public static FieldCondition gt(String name, String value,String dbType){
		if(StringUtils.isBlank(value)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.GT);
		fc.setDbType(dbType);
		fc.setName(name);
		fc.setValue(value);
		return fc;
	}

	/**
	 * 大于等于条件
	 * @param name   字段名称
	 * @param value  值
	 * @param dbType 数据库类型 
	 * @return 字段条件
	 */
	public static FieldCondition ge( String name, String value,String dbType){
		if(StringUtils.isBlank(value)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.GE);
		fc.setDbType(dbType);
		fc.setName(name);
		fc.setValue(value);
		return fc;
	}
	/**
	 * 小于条件
	 * @param name   字段名称
	 * @param value  值
	 * @param dbType 数据库类型 
	 * @return 条件对象
	 */
	public static FieldCondition lt( String name, String value,String dbType){
		if(StringUtils.isBlank(value)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.LT);
		fc.setName(name);
		fc.setDbType(dbType);
		fc.setValue(value);
		return fc;
	}
	
	
	/**
	 * 小于等于条件
	 * @param name   字段名称
	 * @param value  值
	 * @param dbType 数据库类型 
	 * @return 条件对象
	 */
	public static FieldCondition le(String name, String value,String dbType){
		if(StringUtils.isBlank(value)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.LE);
		fc.setName(name);
		fc.setDbType(dbType);
		fc.setValue(value);
		return fc;
	}
	
	
	/**
	 * 为空时
	 * @param name   字段名称
	 * @return 条件对象
	 */
	public static FieldCondition isBlank(String name){
		if(StringUtils.isBlank(name)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.ISNULL);
		fc.setName(name);
		return fc;
	}
	
	/**
	 * 不为空的条件
	 * @param name   字段名称
	 * @return 条件对象
	 */
	public static FieldCondition isNotNull(String name){
		if(StringUtils.isBlank(name)){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.ISNOTNULL);
		fc.setName(name);
		return fc;
	}
	
	/**
	 * 不为空的条件
	 * @param name   字段名称
	 * @return 条件对象
	 */
	public static FieldCondition in(String name,String[]values, String dbType){
		if(values  == null || values.length<=0){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.IN);
		fc.setName(name);
		fc.setValueMany(values);
		fc.setDbType(dbType);
		return fc;
	}
	
	public static FieldCondition notIn(String name,String[]values, String dbType){
		if(values  == null || values.length<=0){
			return null;
		}
		FieldCondition fc = new FieldCondition();
		fc.setOperation(ConditionConstant.NOTIN);
		fc.setName(name);
		fc.setValueMany(values);
		fc.setDbType(dbType);
		return fc;
	}
	
	/**
	 * 范围查询
	 * @param name   字段名称
	 * @param startValue 开始值
	 * @param endValue   结束值
	 * @param dbType 数据库类型
	 * @return 条件对象
	 */
	public static FieldCondition between(String name, String startValue, String endValue,String dbType){
		if(StringUtils.isBlank(startValue) && (StringUtils.isBlank( endValue))){
			return null;
		}
		
		FieldCondition fc = new FieldCondition();
		fc.setDbType(dbType);
		fc.setName(name);
		if (StringUtils.isBlank(startValue) && !StringUtils.isBlank(endValue) ){
			fc.setOperation(ConditionConstant.LE);	
			fc.setValue(endValue);
		} else if(!StringUtils.isBlank(startValue) && StringUtils.isBlank(endValue)) {
			fc.setOperation(ConditionConstant.GE);
			fc.setValue(startValue);
		} else {
			fc.setOperation(ConditionConstant.BETWEEN);
			fc.setValue(startValue);
			fc.setEndValue(endValue);
		}
		
		return fc;
	}

}
