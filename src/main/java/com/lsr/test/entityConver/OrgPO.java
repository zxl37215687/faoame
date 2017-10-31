package com.lsr.test.entityConver;

import com.lsr.frame.base.entityconver.BasePO;

public class OrgPO extends BasePO{
	private String id;
	private String name;
	private OrgTypePO type;
	
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
	public OrgTypePO getType() {
		return type;
	}
	public void setType(OrgTypePO type) {
		this.type = type;
	}
}
