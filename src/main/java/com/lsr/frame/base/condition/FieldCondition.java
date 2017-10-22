package com.lsr.frame.base.condition;

import java.io.Serializable;

/**
 * 
 * @author kuaihuolin
 *
 */
public class FieldCondition implements Serializable{
	private static final long serialVersionUID = -4097770604253353301L;
	private String operation ;
	private String name;
	private String value;
	private String endValue;
	private String dbType;
	private String[]  valueMany;
	public String[] getValueMany() {
		return valueMany;
	}
	public void setValueMany(String[] valueMany) {
		this.valueMany = valueMany;
	}
	public String getDbType() {
		return dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public String getEndValue() {
		return endValue;
	}
	public void setEndValue(String endValue) {
		this.endValue = endValue;
	}
	public FieldCondition(){
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
