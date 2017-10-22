package com.lsr.frame.base.utils;

public class OrderUtils {
	public OrderUtils() {
	}

	public static String procOrderBy(String sql, String orderFld, String type) {
		StringBuffer sb = new StringBuffer(100);
		if (sql.length() < 10 || orderFld == null || "".equals(orderFld))
			return sql;

		sb.append(procAllSql(sql, orderFld, type));
		return sb.toString();
	}

	private static final String ORDER = "order ";
	private static final String BY = "by ";
	private static final String ORDER_BY = " order by ";
	private static final String FROM = " from ";

	/**
	 * 找到最匹配的括号的:因为括号可以无限制的嵌套
	 * 
	 * @param strExpression
	 *            表达式
	 * @return 右边括号的光标位置
	 */
	private static int findRightPos(String strExpression) {

		int rightCount = 1;
		int leftCount = 0;
		int pos = -1;

		for (int idx = strExpression.length() - 1; idx >= 0; idx--) {
			if (strExpression.charAt(idx) == ')') {
				++rightCount;
			} else if (strExpression.charAt(idx) == '(') {
				++leftCount;
			}
			if (leftCount == rightCount) {
				pos = idx;
				break;
			}
		}
		return pos;
	}

	private static String getEndOrderBySql(String sql, String orderFld,
			String type) {
		StringBuffer sb = new StringBuffer();
		sb.append(sql).append(ORDER_BY);
		sb.append(orderFld).append(" ").append(type);
		return sb.toString();
	}

	private static String procAllSql(String sql, String orderFld, String type) {

		StringBuffer sb = new StringBuffer();
		String sub = sql;
		int fromPos = sub.lastIndexOf(FROM);
		int orderPos = sub.lastIndexOf(ORDER);
		int bracketPos = sub.lastIndexOf(")");
		// 没有order by 的处理
		if (orderPos <= fromPos) {
			return getEndOrderBySql(sql, orderFld, type);
		}

		// 没有order by 的处理
		if (orderPos <= bracketPos) {

			int startPos = findRightPos(sql.substring(0, bracketPos - 1));
			sub = sql.substring(startPos + 1, bracketPos);
			return getEndOrderBySql(sql, orderFld, type);
		} else if (bracketPos > 0) {
			int startPos = findRightPos(sql.substring(0, bracketPos));
			sub = sql.substring(startPos, bracketPos);
			if (orderPos < startPos) {
			}
		}

		// 有order by 的处理
		String notOrderSql = sql.substring(0, orderPos);
		return getEndOrderBySql(notOrderSql, orderFld, type);

	}
}
