package com.lsr.frame.ws.control.access;

import java.util.HashMap;

public class CommonInfo {
	private String year;

	private String ztId;

	private String userId;

	private String sectionId;

	private String orgId;

	private String userName;

	private String loginIP;

	private String viewtype;

	private HashMap map = new HashMap();

	private HashMap isValidate = new HashMap();

	public String getSectionId() {

		return sectionId;

	}

	public void setSectionId(String sectionId) {

		this.sectionId = sectionId;

		map.put(BaseConditionConstant.CONDITION_SEARCH_SECTIONID, sectionId);

	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
		map.put(BaseConditionConstant.CONDITION_SEARCH_ORG, orgId);
	}

	public String getUserId() {

		return userId;

	}

	public void setUserId(String userId) {

		map.put(BaseConditionConstant.CONDITION_SEARCH_USERID, userId);

		this.userId = userId;

	}

	public String getYear() {

		return year;

	}

	public void setYear(String year) {

		map.put(BaseConditionConstant.CONDITION_SEARCH_YEAR, year);

		this.year = year;

	}

	public String getZtId() {

		return ztId;

	}

	public void setZtId(String ztId) {

		map.put(BaseConditionConstant.CONDITION_SEARCH_ZTID, ztId);

		this.ztId = ztId;

	}

	public HashMap getMap() {

		return map;

	}

	public void setMap(HashMap map) {

		this.map = map;

	}

	public HashMap getIsValidate() {

		return isValidate;

	}

	public void setIsValidate(HashMap isValidate) {

		this.isValidate = isValidate;

	}

	public String getViewtype() {

		return viewtype;

	}

	public void setViewtype(String viewtype) {

		map.put(BaseConditionConstant.CONDITION_SEARCH_VIEWTYPE, viewtype);

		this.viewtype = viewtype;

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		map.put(BaseConditionConstant.CONDITION_SEARCH_USERNAME, userName);
		this.userName = userName;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		map.put(BaseConditionConstant.CONDITION_SEARCH_LOGINIP, loginIP);
		this.loginIP = loginIP;
	}

}
