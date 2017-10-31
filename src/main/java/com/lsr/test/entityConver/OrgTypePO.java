package com.lsr.test.entityConver;

import com.lsr.frame.base.entityconver.BasePO;

public class OrgTypePO extends BasePO{
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	private String id;
	private String name;
	private String code;
	
}
