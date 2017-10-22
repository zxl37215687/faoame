package com.lsr.frame.base.condition;

public class DataTypes {
	public final static String STRING = "String";       // 字串型
    public final static String INT = "int";             // int型
    public final static String LONG = "long";           // long型
    public final static String SHORT = "short";
    public final static String DOUBLE = "double";       // double型
    public final static String FLOAT = "float";         // float型
    public final static String DATE = "date";           // date型
    public final static String TIMESTMAP = "timestamp";  // 时间型
    public final static String BLOB = "blob";  // 时间型
    public final static String BYTES = "bytes";  // 时间型
    public final static String TIME = "time";  // 时间型
    public final static String BOOLEAN = "boolean";  // 时间型
    public final static String DECIMAL = "decimal";  // 时间型
    public final static String BIGDECIMAL = "bigdecimal";  //金额类型
    public final static String CLOB = "clob";
    public final static String COLLECTION = "Collection"; //集合型

    public final static int RS_LIST_VO = 0;          // 以ArrayList->vo方式查询
    public final static int RS_LIST_MAP = 1;         // 以ArrayList->HashMap方式查询
    public final static int RS_SINGLE_VO = 2;        // 以vo方式查询
    //public final static int RS_SINGLE_LIST = 3;      // 以ArrayList查询
    public final static int RS_SINGLE_MAP = 4;       // 以HashMap查询
    public final static int RS_META_LIST = 5;        // rsMeta以List查询
}
