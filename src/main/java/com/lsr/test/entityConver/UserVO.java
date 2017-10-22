package com.lsr.test.entityConver;

import com.lsr.frame.base.entityconver.BaseVO;
import com.lsr.frame.base.entityconver.BindEntity;
import com.lsr.frame.base.entityconver.BindFileldName;

@BindEntity(UserPO.class)
public class UserVO extends BaseVO{

	private static final long serialVersionUID = -6652657649207328694L;

	@BindFileldName("id")
	private String id;
	
	@BindFileldName("account")
	private String account;
	
	@BindFileldName("password")
	private String password;

	public UserVO(String id,String account,String password){
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
