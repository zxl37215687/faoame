package com.lsr.test.entityConver;

import com.lsr.frame.base.entityconver.BasePO;

public class UserPO extends BasePO{

	private static final long serialVersionUID = 8324413165485524165L;

	private String id;
	
	private String account;
	
	private String password;
	
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
