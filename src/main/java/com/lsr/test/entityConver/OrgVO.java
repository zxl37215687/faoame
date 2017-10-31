package com.lsr.test.entityConver;

import com.lsr.frame.base.entityconver.BaseVO;

public class OrgVO extends BaseVO{
	private String id;
	private String name;
	private String orgTypeId;
	
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
	public String getOrgTypeId() {
		return orgTypeId;
	}
	public void setOrgTypeId(String orgTypeId) {
		this.orgTypeId = orgTypeId;
	}
}
