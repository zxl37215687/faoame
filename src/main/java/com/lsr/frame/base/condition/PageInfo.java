package com.lsr.frame.base.condition;

import java.io.Serializable;

import com.lsr.frame.base.entityconver.VOInterface;

/**
 * 
 * @author kuaihuolin
 *
 */
public class PageInfo implements Serializable {
	private long maxRowCount = 0;

	private long currentPage = 0;

	private int pageCount = 0;

	private int rowsPerPage;

	private VOInterface vo;

	private String sqlWhere;

	private String searchWhere;

	private String orderFld;

	private String orderType;

	private int indexSize;

	private Condition condition;
	private String countSum = null;
	private String countSumValue = null;

	public String getCountSumValue() {
		return countSumValue;
	}

	public void setCountSumValue(String countSumValue) {
		this.countSumValue = countSumValue;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public String getCountSum() {
		return countSum;
	}

	public void setCountSum(String countSum) {
		this.countSum = countSum;
	}

	public int getIndexSize() {
		return indexSize;
	}

	public void setIndexSize(int indexSize) {
		this.indexSize = indexSize;
	}

	public long getMaxPage() {
		return maxRowCount % (long) rowsPerPage != 0L ? maxRowCount
				/ (long) rowsPerPage + 1L : maxRowCount / (long) rowsPerPage;
	}

	/**
	 * @return 返回 vo。
	 */
	public VOInterface getVo() {
		return vo;
	}

	/**
	 * @param vo
	 *            要设置的 vo。
	 */
	public void setVo(VOInterface vo) {
		this.vo = vo;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getMaxRowCount() {
		return maxRowCount;
	}

	public void setMaxRowCount(long maxRowCount) {
		this.maxRowCount = maxRowCount;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * @return 返回 pageCount。
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * @param pageCount
	 *            要设置的 pageCount。
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * @return 返回 orderFld。
	 */
	public String getOrderFld() {
		return orderFld;
	}

	/**
	 * @param orderFld
	 *            要设置的 orderFld。
	 */
	public void setOrderFld(String orderFld) {
		this.orderFld = orderFld;
	}

	/**
	 * @return 返回 orderType。
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType
	 *            要设置的 orderType。
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * @return 返回 searchWhere。
	 */
	public String getSearchWhere() {
		return searchWhere;
	}

	/**
	 * @param searchWhere
	 *            要设置的 searchWhere。
	 */
	public void setSearchWhere(String searchWhere) {
		this.searchWhere = searchWhere;
	}

	/**
	 * @return 返回 sqlWhere。
	 */
	public String getSqlWhere() {
		return sqlWhere;
	}

	/**
	 * @param sqlWhere
	 *            要设置的 sqlWhere。
	 */
	public void setSqlWhere(String sqlWhere) {
		this.sqlWhere = sqlWhere;
	}
}
