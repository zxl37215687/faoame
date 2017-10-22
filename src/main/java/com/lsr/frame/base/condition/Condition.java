package com.lsr.frame.base.condition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.lsr.frame.base.utils.FaoameUtils;

/**
 * 条件对象
 * 
 * @author kuaihuolin
 * 
 */
public class Condition implements Serializable {
	private String orderType = ConditionConstant.ASC; 
	private String orderFld = ""; 
	private int rowsPerPage = 30; 
	private long currentPage = 1; 
	private long maxRowCount = 0; 
	private Map others = new HashMap(); 
	private List generalList = new ArrayList(); 
	private List fixList = new ArrayList(); 
	private Map fixOthers = new HashMap();
	private String sqlWhere;

	private Map specConMap = new HashMap();

	public List getGeneralList() {
		return this.generalList;
	}

	public String getString(String name) {
		Object object = others.get(name);
		if (object == null)
			object = this.fixOthers.get(name);

		if (object == null)
			return "";
		return (String) object;
	}

	public Object getValue(String name) {
		Object object = others.get(name);
		if (object == null) {
			object = this.fixOthers.get(name);
		}
		return object;
	}

	public void setValue(String name, Object obj) {
		others.put(name, obj);
	}

	public void setValue(String name, Object obj, boolean isNotFixed) {
		if (isNotFixed) {
			this.others.put(name, obj);
			return;
		}

		this.fixOthers.put(name, obj);
	}

	public List getFixList() {
		return fixList;
	}

	public void or(FieldCondition fc) {
		or(fc, true);
	}

	public void or(FieldCondition fc, boolean isNotFixed) {
		this.sqlWhere = null;
		if (isNotFixed) {
			generalList.add(ConditionConstant.OR);
			generalList.add(fc);
		} else {
			fixList.add(ConditionConstant.OR);
			fixList.add(fc);
		}
	}

	public void add(FieldCondition fc) {
		add(fc, true);
	}

	public void add(FieldCondition fc, boolean isNotFixed) {
		this.sqlWhere = null;
		if (isNotFixed) {

			generalList.add(fc);
		} else {
			fixList.add(fc);
		}
	}

	public void and(FieldCondition fc) {
		and(fc, true);

	}

	public void and(FieldCondition fc, boolean isNotFixed) {
		this.sqlWhere = null;
		if (isNotFixed) {
			if (fc != null) {
				generalList.add(ConditionConstant.AND);
				generalList.add(fc);
			}
		} else {
			fixList.add(ConditionConstant.AND);
			fixList.add(fc);
		}
	}

	public void add(String value) {
		add(value, true);
	}

	public void add(String value, boolean isNotFixed) {
		this.sqlWhere = null;
		if (isNotFixed)
			generalList.add(value);
		else
			fixList.add(value);
	}

	public void and(String value) {
		and(value, true);
	}

	/**
	 * 将sql值and到条件列表当中
	 * 
	 * @param value
	 *            sql值
	 * @param isNotFixed
	 *            是否固定条件
	 */
	public void and(String value, boolean isNotFixed) {
		this.sqlWhere = null;
		if (isNotFixed) {
			generalList.add(ConditionConstant.AND);
			generalList.add(value);
		} else {
			fixList.add(ConditionConstant.AND);
			fixList.add(value);
		}
	}

	public void or(String value) {
		or(value, true);
	}

	/**
	 * 将sql值加在条件列表中
	 * 
	 * @param value
	 *            值
	 * @param isNotFixed
	 *            是否固定条件
	 */
	public void or(String value, boolean isNotFixed) {
		this.sqlWhere = null;
		if (isNotFixed) {
			generalList.add(ConditionConstant.OR);
			generalList.add(value);
		} else {
			fixList.add(ConditionConstant.OR);
			fixList.add(value);
		}
	}

	/**
	 * 清除普通条件的对象
	 */
	public void clear() {
		this.sqlWhere = null;
		this.generalList.clear();
		this.others.clear();
	}

	/**
	 * 清除所有的对象
	 */
	public void clearAll() {
		this.fixList.clear();
		this.clear();
		this.fixOthers.clear();
		this.currentPage = 1;
		this.maxRowCount = 0;
		this.orderFld = "";
		this.orderType = "";
		// 2007年5月16号 莫霖 修改系统管理分页显示rowsPerPage = 10改为30；
		this.rowsPerPage = 30;
		this.sqlWhere = null;

	}

	/**
	 * 将StringTokenizer转换成string对象
	 * 
	 * @param stk
	 *            StringTokenizer对象
	 * @return 值
	 */
	private String stkToString(StringTokenizer stk) {
		String value = null;
		if (stk.hasMoreTokens()) {
			value = stk.nextToken();
		}
		return value;
	}

	/**
	 * 通过Expression构造器来构造条件对象
	 * 
	 * @param name
	 *            名字
	 * @param dbType
	 *            数据类型
	 * @param opt
	 *            操作符
	 * @param value
	 *            开始值
	 * @param endValue
	 *            结束值
	 * @return 条件对象
	 */
	private FieldCondition express(String name, String dbType, String opt,
			String value, String endValue) {
		FieldCondition fc = null;

		if (ConditionConstant.LE.equalsIgnoreCase(opt)) {
			fc = Expression.le(name, value, dbType);
		} else if (ConditionConstant.LT.equalsIgnoreCase(opt)) {
			fc = Expression.lt(name, value, dbType);
		} else if (ConditionConstant.GE.equalsIgnoreCase(opt)) {
			fc = Expression.ge(name, value, dbType);
		} else if (ConditionConstant.GT.equalsIgnoreCase(opt)) {
			fc = Expression.gt(name, value, dbType);
		} else if (ConditionConstant.EQ.equalsIgnoreCase(opt)) {
			fc = Expression.eq(name, value, dbType);
		} else if (ConditionConstant.NE.equalsIgnoreCase(opt)) {
			fc = Expression.ne(name, value, dbType);
		} else if (ConditionConstant.LIKE.equalsIgnoreCase(opt)) {
			fc = Expression.like(name, value);
		} else if (ConditionConstant.BLIKE.equalsIgnoreCase(opt)) {
			fc = Expression.blike(name, value);
		} else if (ConditionConstant.BLIKE.equalsIgnoreCase(opt)) {
			fc = Expression.elike(name, value);
		} else if (ConditionConstant.BETWEEN.equalsIgnoreCase(opt)) {
			fc = Expression.between(name, value, endValue, dbType);
		} else if (ConditionConstant.IN.equalsIgnoreCase(opt)) {
			if (value != null) {
				String[] values = value.split(",");
				fc = Expression.in(name, values, dbType);
			}
		} else if (ConditionConstant.NOTIN.equals(opt)) {
			if (value != null) {
				String[] values = value.split(",");
				fc = Expression.notIn(name, values, dbType);
			}
		}

		return fc;
	}

	/**
	 * 构造条件对象(一般来自页面)
	 * 
	 * @param strCond
	 *            对象值
	 * @param isNotFixed
	 *            是否为固定条件
	 */
	public void constructCond(String strCond, boolean isNotFixed) {
		StringTokenizer stk = new StringTokenizer(strCond, "##");
		String fld;

		if (isNotFixed)
			this.clear();
		else
			this.clearAll();

		while (stk.hasMoreTokens()) {

			fld = stk.nextToken();
			StringTokenizer stkFld = new StringTokenizer(fld, "::");
			String name = stkToString(stkFld);
			String fldName = stkToString(stkFld);
			String dbType = stkToString(stkFld);

			if (dbType == null) { // 如果不是数据库条件的
				this.setValue(name, fldName, isNotFixed);
				continue;
			}

			String opt = stkToString(stkFld);
			String value = stkToString(stkFld);
			String endValue = stkToString(stkFld);
			this.setValue(name, value);
			if (endValue != null) {
				this.setValue(name, value + "::" + endValue);
			}
			this.setValue(name, value);

			this.and(express(fldName, dbType, opt, value, endValue), isNotFixed);
		}
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		if (currentPage <= 0)
			currentPage = 1;
		this.currentPage = currentPage;
	}

	public String getOrderFld() {
		return orderFld;
	}

	public void setOrderFld(String orderFld) {
		this.orderFld = orderFld;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public int getRowsPerPage() {
		if (rowsPerPage == 30) {
			String propertyName = "rowsperpage";
			String propertyValue = FaoameUtils.getProperty(propertyName); // 通过配置文件取得设置每页显示的记录数
			propertyValue = (propertyValue == null || propertyValue.length() == 0) ? "30"
					: propertyValue;
			rowsPerPage = (new Integer(propertyValue)).intValue();
		}
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		if (rowsPerPage == 0)
			rowsPerPage = 10;
		this.rowsPerPage = rowsPerPage;
	}

	public long getMaxRowCount() {
		return maxRowCount;
	}

	public void setMaxRowCount(long maxRowCount) {
		this.maxRowCount = maxRowCount;
	}

	public long getMaxPage() {
		return maxRowCount % rowsPerPage != 0L ? maxRowCount / rowsPerPage + 1L
				: maxRowCount / rowsPerPage;
	}

	public int getStartRow() {
		long curPage = this.getCurrentPage();
		long maxPage = this.getMaxPage();
		if (curPage == -1) {
			curPage = maxPage;
		}
		curPage = curPage < 1 ? 1 : curPage;
		curPage = curPage > maxPage ? maxPage : curPage;
		this.currentPage = curPage;
		--curPage;
		return (int) curPage * this.getRowsPerPage();
	}

	public String getSQLWhere() {
		if (this.sqlWhere == null) {
			ConditionParse parse = ConditionParseFactory.createParse();
			this.sqlWhere = parse.parse(this);
		}
		return this.sqlWhere;
	}

	public Map getSpecConMap() {
		return specConMap;
	}

	public void setSpecConMap(Map specConMap) {
		this.specConMap = specConMap;
	}
}
