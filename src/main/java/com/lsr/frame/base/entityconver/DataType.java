package com.lsr.frame.base.entityconver;

/**
 * 数据类型
 * @author kuaihuolin
 *
 */
public enum DataType {
	STRING("string"),INT("int"),LONG("long"),SHORT("short"),DOUBLE("double"),FLOAT("float"),DATE("date"),TIMESTAMP("timestamp"),BLOB("blob"),BYTES("bytes"),TIME("time"),BOOLEAN("boolean"),
	DECIMAL("decimal"),BIGDECIMAL("bigdecimal"),CLOB("clob"),COLLECTION("collection");
	
	public String dataTypeName;
	
	private DataType(String dataTypeName){
		this.dataTypeName = dataTypeName;
	}
	
	public String getDataTypeName() {
		return dataTypeName;
	}

	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	public String toString() {
		return this.dataTypeName;
	};
}
