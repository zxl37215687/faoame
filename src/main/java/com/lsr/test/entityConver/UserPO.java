package com.lsr.test.entityConver;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import com.lsr.frame.base.entityconver.BasePO;

public class UserPO extends BasePO{

	private static final long serialVersionUID = 8324413165485524165L;

	private String id;
	
	private String account;
	
	private String password;
	
	private Integer age;
	
	private Short shortv;
	private Long longv;
	private Timestamp currTime;
	private Date date;
	private Time time;
	private BigDecimal bigDecimal;
	private boolean flag;
	private Double dou;
	private Float flo;
	
	
	public Short getShortv() {
		return shortv;
	}

	public void setShortv(Short shortv) {
		this.shortv = shortv;
	}

	public Long getLongv() {
		return longv;
	}

	public void setLongv(Long longv) {
		this.longv = longv;
	}

	public Timestamp getCurrTime() {
		return currTime;
	}

	public void setCurrTime(Timestamp currTime) {
		this.currTime = currTime;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public BigDecimal getBigDecimal() {
		return bigDecimal;
	}

	public void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Double getDou() {
		return dou;
	}

	public void setDou(Double dou) {
		this.dou = dou;
	}

	public Float getFlo() {
		return flo;
	}

	public void setFlo(Float flo) {
		this.flo = flo;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public UserPO(){}
	
	public UserPO(String id,String account,String password){
		this.id = id;
		this.account = account;
		this.password = password;
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
