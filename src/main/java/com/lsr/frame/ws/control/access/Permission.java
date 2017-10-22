package com.lsr.frame.ws.control.access;

public class Permission {
	/**
	 * 资源ID
	 */
	private String rsId;
	
	/**
	 * 资源编码
	 */
	private String rsCode;

	/**
	 * 资源名称
	 */
	private String rsSign;

	/**
	 * 资源URL
	 */
	private String rsUrl;

	/**
	 * 资源类型
	 */
	private int rsType;

	/**
	 * 操作权限类型
	 */
	private int privType;
	
	/**
	 * 模块类别
	 */
	private String moduleType;
	
	private String asId;
	public static final int READ = 2;
	public static final int OPERATE = 3;
	public static final int NOT_ACCESSABLE = -1;
	
	
	/**
	 * 操作编号
	 */
	private String privCode;
	
	private String priName;

	public Permission() {

	}

	public Permission(String rsId, String rsSign, String rsUrl, int rsType,
			int privType, String privCode) {
		this.rsId = rsId;
		this.rsSign = rsSign;
		this.rsUrl = rsUrl;
		this.rsType = rsType;
		this.privType = privType;
		this.privCode = privCode;
	}

	public String getPrivCode() {
		return privCode;
	}

	public void setPrivCode(String privCode) {
		this.privCode = privCode;
	}

	public int getPrivType() {
		return privType;
	}

	public void setPrivType(int privType) {
		this.privType = privType;
	}

	public String getRsId() {
		return rsId;
	}

	public void setRsId(String rsId) {
		this.rsId = rsId;
	}

	public String getRsSign() {
		return rsSign;
	}

	public void setRsSign(String rsSign) {
		this.rsSign = rsSign;
	}

	public int getRsType() {
		return rsType;
	}

	public void setRsType(int rsType) {
		this.rsType = rsType;
	}

	public String getRsUrl() {
		return rsUrl;
	}

	public void setRsUrl(String rsUrl) {
		this.rsUrl = rsUrl;
	}

	public String getRsCode() {
		return rsCode;
	}

	public void setRsCode(String rsCode) {
		this.rsCode = rsCode;
	}
	
	public String getPriName() {
		return priName;
	}

	public void setPriName(String priName) {
		this.priName = priName;
	}

	public String getAsId() {
		return asId;
	}

	public void setAsId(String asId) {
		this.asId = asId;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
}
