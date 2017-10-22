package com.lsr.frame.ws.control.access;

import java.util.ArrayList;
import java.util.List;

public class MenuItem implements Resource{
	private String resourceId;
	private String resourceCode;
	private String id;
	private String fshowName;
	private String href;
	private String fmenuParent;
	private String fmenuParentName;
	private String level;
	private String type;
	private List childList = new ArrayList();
	private String moduleType;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getFmenuParent() {
		return fmenuParent;
	}

	public void setFmenuParent(String fmenuParent) {
		this.fmenuParent = fmenuParent;
	}

	public String getFmenuParentName() {
		return fmenuParentName;
	}

	public void setFmenuParentName(String fmenuParentName) {
		this.fmenuParentName = fmenuParentName;
	}

	public String getFshowName() {
		return fshowName;
	}

	public void setFshowName(String fshowName) {
		this.fshowName = fshowName;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public List getChildList() {
		return childList;
	}

	public void setChildList(List childList) {
		this.childList = childList;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
}
