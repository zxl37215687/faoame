package com.lsr.frame.ws.control.access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class User {
	public static boolean ignoreCheck = false;

	/**
	 * 用户标识
	 */
	private String id;

	/**
	 * 用户名称
	 */
	private String name;

	/**
	 * 用户编码
	 */
	private String code;

	/**
	 * 用户口令
	 */
	private String password;

	/**
	 * 用户pin码
	 */
	private String pin;

	/**
	 * 用户是否是超级管理员
	 */
	private int isAdmin;
	/**
	 * 用户类型
	 */
	private String userType;

	/**
	 * 用户的权限列表
	 */
	private List permissions;

	/**
	 * 用户的菜单
	 */
	private List menu;

	private Map menuMap;

	/**
	 * 用户当前的位置
	 */
	private String position;

	/**
	 * 用户当前的子位置
	 */
	private String chrPosotion;

	public String singleOrgId;

	public String singleOrgName;

	public String mulOrgId;

	public String mulOrgName;


	private String sectionIds;

	private String sectionCodes;

	private String sectionID;

	private String sectionName;

	private String sectionCode;

	private int logintype;
	private List hotDeskList = new ArrayList();

	private int adminLevel;
	private String adminLevelName;

	private String stactionIds;

	private Set Roles = new HashSet();

	public String getStactionIds() {
		return stactionIds;
	}

	public void setStactionIds(String stactionIds) {
		this.stactionIds = stactionIds;
	}

	public String getSectionIds() {
		return sectionIds;
	}

	public void setSectionIds(String sectionIds) {
		this.sectionIds = sectionIds;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getMulOrgId() {
		return mulOrgId;
	}

	public void setMulOrgId(String mulOrgId) {
		this.mulOrgId = mulOrgId;
	}

	public String getMulOrgName() {
		return mulOrgName;
	}

	public void setMulOrgName(String mulOrgName) {
		this.mulOrgName = mulOrgName;
	}

	public String getSingleOrgId() {
		return singleOrgId;
	}

	public void setSingleOrgId(String singleOrgId) {
		this.singleOrgId = singleOrgId;
	}

	public String getSingleOrgName() {
		return singleOrgName;
	}

	public void setSingleOrgName(String singleOrgName) {
		this.singleOrgName = singleOrgName;
	}

	public User() {

	}

	public User(String id, String name, String code, String password,
			String pin, int isAdmin, List permissions) {

		this.id = id;
		this.name = name;
		this.code = code;
		this.password = password;
		this.pin = pin;
		this.isAdmin = isAdmin;
		this.permissions = permissions;
	}

	/**
	 * 检查用户是否拥有对URL的访问权限
	 * 
	 * @param rsUrl
	 * @return
	 */
	public boolean checkUrl(String rsUrl) {
		if (isAdmin == 1) {
			return true;
		}
		if (ignoreCheck) {
			return true;
		}
		for (int i = 0; i < this.permissions.size(); i++) {
			Permission perm = (Permission) this.permissions.get(i);
			if (perm.getRsUrl() != null && perm.getRsUrl().indexOf(rsUrl) >= 0) {
				if (perm.getPrivType() > Permission.READ) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检查用户对资源的访问权限
	 * 
	 * @param rsId
	 * @return 权限值
	 */
	public int checkResource(String rsId) {
		int nperm = Permission.NOT_ACCESSABLE;
		if (isAdmin == 1) {
			return Permission.OPERATE;
		}
		if (ignoreCheck) {
			return Permission.OPERATE;
		}
		for (int i = 0; i < this.permissions.size(); i++) {
			Permission perm = (Permission) this.permissions.get(i);
			if (perm.getRsId() != null && perm.getRsId().equals(rsId)
					&& perm.getPrivType() > nperm) {
				nperm = perm.getPrivType();
			}
		}
		return nperm;
	}

	/**
	 * 根据资源编码判断用户是否拥有对该资源的访问权限
	 * 
	 * @param rsCode
	 *            资源编码
	 * @return
	 */
	public int checkResourceByCode(String rsCode) {
		int nperm = Permission.NOT_ACCESSABLE;
		if (isAdmin == 1) {
			return Permission.OPERATE;
		}
		if (ignoreCheck) {
			return Permission.OPERATE;
		}
		String[] arr = rsCode.split("\\.");
		for (int i = 0; i < this.permissions.size(); i++) {
			try {
				Permission perm = (Permission) this.permissions.get(i);
				if (perm.getRsCode() != null && perm.getRsCode().equals(arr[0])
						&& perm.getPrivCode().equals(arr[1])) {

					return 3;
					// nperm = perm.getPrivType();
				}

			} catch (Exception e) {
				throw new NullPointerException();
			}

		}
		return nperm;
	}

	/**
	 * 获取用户当前的功能路由，一般是菜单路径
	 * 
	 * @return
	 */
	public List getPositionRoute() {
		List route = new ArrayList();
		String nodeId = this.position;
		do {
			MenuItem node = (MenuItem) this.menuMap.get(nodeId);
			if (node == null)
				break;

			route.add(0, node);
			nodeId = node.getFmenuParent();
		} while (!nodeId.equals("0"));

		return route;

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPermissions(List permissions) {
		this.permissions = permissions;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public List getMenu() {
		return menu;
	}

	public void setMenu(List omenu) {// 对菜单进行权限过滤,并将有权访问的加入menuMap
		this.menu = new ArrayList();
		if (omenu != null) {
			this.menuMap = new HashMap();
			for (int i = 0; i < omenu.size(); i++) {
				MenuItem item = (MenuItem) omenu.get(i);
				if (ignoreCheck
						|| (isAdmin == 1)
						|| this.checkResource(item.getResourceId()) > Permission.NOT_ACCESSABLE) {
					this.menu.add(item);
					this.menuMap.put(item.getId(), item);
				}
			}
		}
	}

	public List getRightMenu(List menuList) {
		List newList = new ArrayList();
		if (menuList == null) {
			return newList;
		}
		if (ignoreCheck || isAdmin == 1) {
			return menuList;
		}
		for (int i = 0; i < menuList.size(); i++) {
			MenuItem item = (MenuItem) menuList.get(i);
			if (checkResource(item.getResourceId()) > Permission.NOT_ACCESSABLE) {
				newList.add(item);
			}
		}
		return newList;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
		this.chrPosotion = null;
	}

	public String getChrPosotion() {
		return chrPosotion;
	}

	public void setChrPosotion(String chrPosotion) {
		this.chrPosotion = chrPosotion;
	}

	public int getLogintype() {
		return logintype;
	}

	public void setLogintype(int logintype) {
		this.logintype = logintype;
	}

	/**
	 * 
	 * 根据资源操作编码判断用户是否拥有对该资源操作的访问权限
	 * 
	 * 
	 * 
	 * @param rsCode
	 * 
	 *            资源操作编码,以符号"."连接
	 * 
	 * @param moduleType
	 * 
	 *            模块类别
	 * 
	 * @param asid
	 * 
	 *            模块帐套ID
	 * 
	 * @return
	 */

	public int checkResourceByCode(String rsCode, String moduleType, String asid) {
		int nperm = Permission.NOT_ACCESSABLE;
		if (isAdmin == 1) {
			return Permission.OPERATE;
		}
		if (ignoreCheck) {
			return Permission.OPERATE;
		}
		return nperm;
	}

	public String getSectionID() {
		return sectionID;
	}

	public void setSectionID(String sectionID) {
		this.sectionID = sectionID;
	}

	public List getHotDeskList() {
		return hotDeskList;
	}

	public void setHotDeskList(List hotDeskList) {
		this.hotDeskList = hotDeskList;
	}

	public int getAdminLevel() {
		return adminLevel;
	}

	public void setAdminLevel(int adminLevel) {
		this.adminLevel = adminLevel;
	}

	public String getAdminLevelName() {
		return adminLevelName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public List getPermissions() {
		return this.permissions;
	}

	public boolean isAdmin() {
		return 1 == this.getIsAdmin();
	}

	public Set getRoles() {
		return Roles;
	}

	public void setRoles(Set roles) {
		Roles = roles;
	}

	public String getSectionCodes() {
		return sectionCodes;
	}

	public void setSectionCodes(String sectionCodes) {
		this.sectionCodes = sectionCodes;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

}
