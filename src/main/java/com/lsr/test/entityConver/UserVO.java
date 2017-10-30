package com.lsr.test.entityConver;

import com.lsr.frame.base.entityconver.BaseVO;
import com.lsr.frame.base.entityconver.BindEntity;
import com.lsr.frame.base.entityconver.BindFieldName;
import com.lsr.frame.base.entityconver.DataType;

@BindEntity(UserPO.class)
public class UserVO extends BaseVO{

	private static final long serialVersionUID = -6652657649207328694L;

	@BindFieldName("id")
	private String id;
	
	@BindFieldName("account")
	private String account;
	
	@BindFieldName("password")
	private String password;

	@BindFieldName("age")
	private String age;
	
	@BindFieldName(value="shortv",dataType=DataType.SHORT)
	private String shortv;
	
	@BindFieldName(value="longv",dataType=DataType.LONG)
	private String longs;
	
	@BindFieldName(value="currTime",dataType=DataType.TIMESTAMP)
	private String curTime;
	
	@BindFieldName(value="date",dataType=DataType.DATE)
	private String date;
	
	@BindFieldName(value="time",dataType=DataType.TIME)
	private String time;
	
	@BindFieldName(value="bigDecimal",dataType=DataType.BIGDECIMAL)
	private String bigDecimal;
	
	@BindFieldName(value="flag",dataType=DataType.BOOLEAN)
	private String flag;
	
	@BindFieldName(value="dou",dataType=DataType.DOUBLE)
	private String dou;
	
	@BindFieldName(value="flo",dataType=DataType.FLOAT)
	private String flo;
	
	
	
	public String getShortv() {
		return shortv;
	}

	public void setShortv(String shortv) {
		this.shortv = shortv;
	}

	public String getLongs() {
		return longs;
	}

	public void setLongs(String longs) {
		this.longs = longs;
	}

	public String getCurTime() {
		return curTime;
	}

	public void setCurTime(String curTime) {
		this.curTime = curTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBigDecimal() {
		return bigDecimal;
	}

	public void setBigDecimal(String bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDou() {
		return dou;
	}

	public void setDou(String dou) {
		this.dou = dou;
	}

	public String getFlo() {
		return flo;
	}

	public void setFlo(String flo) {
		this.flo = flo;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public UserVO(){}
	
	public UserVO(String id,String account,String password){
		this.id = id;
		this.account = account;
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "id==>" + this.id +"\n" 
				+"account==>"+account+"\n" 
				+"password==>"+password+"\n" 
				+"age==>"+age+"\n" 
				+"shortv==>"+this.shortv+"\n" 
				+"longs==>"+this.longs+"\n" 
				+"curTime==>"+this.curTime+"\n" 
				+"date==>"+this.date+"\n" 
				+"time==>"+this.time+"\n" 
				+"bigDecimal==>"+this.bigDecimal+"\n" 
				+"flag==>"+this.flag+"\n" 
				+"dou==>"+this.dou+"\n" 
				+"flo==>"+this.flo
				;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
