package com.lsr.frame.base.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.lsr.frame.ws.utils.SumUtils;

public class StringUtils {
	private StringUtils() {
	}

	/**
	 * null对象转换为空对象
	 * 
	 * @param value
	 *            对象
	 * @return String对象
	 */
	public static String nullToString(Object value) {
		if (value == null) {
			return "";
		} else {
			String strRet = String.valueOf(value);
			if (strRet.length() > 0)
				strRet = strRet.trim();
			return strRet;
		}
	}

	/**
	 * null对象转换为"0"
	 * 
	 * @param value
	 *            对象
	 * @return String对象
	 */
	public static String nullToZeroString(Object value) {
		if (value == null) {
			return "0";
		} else {
			String strRet = String.valueOf(value);
			if (strRet.length() > 0)
				strRet = strRet.trim();
			if (strRet.length() == 0)
				strRet = "0";
			return strRet;
		}
	}

	/**
	 * 字符串替换方法 Wengnb Add 2003-09-09
	 * 
	 * @param strSource
	 *            String:字符串
	 * @param strFrom
	 *            String:源字串
	 * @param strTo
	 *            String:替换的字串
	 * @return String:最终替换后的字串结果
	 */
	public static String replace(String strSource, String strFrom, String strTo) {
		if (strSource == null)
			return "";

		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos = 0;

		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			strDest = strDest + strTo;
			strSource = strSource.substring(intPos + intFromLen);
		}
		strDest = strDest + strSource;
		return strDest;
	}

	/**
	 * Wengnb Add 2003-09-09 将前后空格字符串截去后,将"'"替换成为"''"
	 * 
	 * @param strSource
	 *            String:字符串
	 * @return String:最终替换后的字串结果
	 */
	public static String replaceText(String strSource) {

		if (strSource == null) {
			return "";
		}
		return replace(strSource.trim(), "'", "''");

	}

	/**
	 * 处理自动转行内容
	 * 
	 * @param strContent
	 *            内容
	 * @return 处理内容结果
	 */
	public static String procContent(String strContent) {
		String strRet = "";

		if (getEngLength(strContent) <= maxLength)
			return strContent;

		char c;
		ArrayList ary = new ArrayList();
		boolean bEng = true;
		boolean bPreEng = true;
		StringBuffer strBuf = new StringBuffer();
		HashMap hmap = new HashMap();

		for (int idx = 0; idx < strContent.length(); idx++) {
			c = strContent.charAt(idx);
			bEng = (isWhat(c) > 0) ? false : true;

			if ((bEng != bPreEng && idx > 0)
					|| (idx == strContent.length() - 1)) {
				hmap = new HashMap();
				if (idx == strContent.length() - 1)
					strBuf.append(c);
				hmap.put("content", strBuf.toString());
				hmap.put("isEng", String.valueOf(bPreEng));

				ary.add(hmap);
				hmap = null;

				strBuf.setLength(0);
			}

			strBuf.append(c);
			bPreEng = bEng;
		}

		strBuf.setLength(0);

		String strCur = "";
		int curWidth = 0;
		int iPos = 0;

		for (int idx = 0; idx < ary.size(); idx++) {
			hmap = new HashMap();
			hmap = (HashMap) ary.get(idx);
			strCur = nullToString(hmap.get("content"));
			bEng = nullToString(hmap.get("isEng")).equalsIgnoreCase("true") ? true
					: false;
			curWidth = getEngLength(strCur);

			if (curWidth + iPos >= maxLength) {
				strBuf.append(procWord(iPos, strCur, bEng));
				iPos = curPos;
			} else {
				strBuf.append(strCur);
				iPos += curWidth;
			}
			hmap = null;
		}
		return strBuf.toString();

	}

	private final static int NUM_ENG = 0;

	private final static int OPERTAND = 1;

	private final static int CHINESE = 2;

	private static int curPos = 0;

	private static int maxLength = 74;

	private static boolean isEng(char c) {
		if ((c >= 65 && c < 91) || (c >= 97 && c < 123)) {
			return true;
		} else if (c >= 48 && c <= 57) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 处理中文自动转行
	 * 
	 * @param iPos
	 *            位置
	 * @param strValue
	 *            内容
	 * @param isEng
	 *            是否英文
	 * @return 处理后的结果
	 */
	private static String procWord(int iPos, String strValue, boolean isEng) {
		char c;
		int maxWidth = 0;
		maxWidth = maxLength;
		StringBuffer strBuf = new StringBuffer();
		if (isEng == true && iPos > 0) {
			strBuf.append("\n");
			iPos = 0;
		}

		for (int idx = 0; idx < strValue.length(); idx++) {
			++iPos;
			c = strValue.charAt(idx);
			if (c == '\n' || c == '\r')
				iPos = 0;
			if ((int) c > 255) { // 中文字
				++iPos;
			}
			strBuf.append(c);
			if (iPos >= maxWidth) {
				iPos = 0;
				strBuf.append("\n");
			}
		}
		curPos = iPos;
		return strBuf.toString();
	}

	/**
	 * 属于哪英文或标点符号,中文字
	 * 
	 * @param c
	 *            字符
	 * @return 属于什么
	 */
	private static int isWhat(char c) {
		if ((c >= 65 && c < 91) || (c >= 97 && c < 123)) {
			return NUM_ENG;
		} else if (c >= 48 && c <= 57) {
			return NUM_ENG;
		} else if (c <= 255 && c > 0) {
			return OPERTAND;
		} else {
			return CHINESE;
		}
	}

	/**
	 * 累计多少个字符长度(一个中文字按照两个英文的算法)
	 * 
	 * @param strValue
	 *            内容
	 * @return 最终的长度
	 */
	public static int getEngLength(String strValue) {
		char c;
		int iPos = 0;
		for (int idx = 0; idx < strValue.length(); idx++) {
			++iPos;
			c = strValue.charAt(idx);
			if ((int) c > 255) { // 中文字
				++iPos;
			}
		}
		return iPos + 1;
	}

	public static String filter(String value) {
		if (value == null)
			return null;
		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);
		for (int i = 0; i < content.length; i++)
			switch (content[i]) {
			case 60: // '<'
				result.append("&lt;");
				break;

			case 62: // '>'
				result.append("&gt;");
				break;

			case 38: // '&'
				result.append("&amp;");
				break;

			case 34: // '"'
				result.append("&quot;");
				break;

			default:
				result.append(content[i]);
				break;
			}

		return result.toString();
	}

	public static String unFilter(String value) {
		if (value == null)
			return null;
		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);
		for (int i = 0; i < content.length; i++)
			switch (content[i]) {
			case '\n': // '<'
				result.append("<BR>");
				// System.out.println("----n---");
				break;

			// case '\r': // '>'
			// System.out.println("----r---");
			// result.append("<BR>");
			// break;

			case 32: // '&'
				result.append("&nbsp;");
				break;

			default:
				result.append(content[i]);
				break;
			}

		return result.toString();
	}

	/**
	 * 显示多少个字符出来,(主要用于链接时,不要显示太多的内容)
	 * 
	 * @param strValue
	 *            内容
	 * @return 处理后的结果
	 */
	public static String subStr(String strValue) {
		return subStr(strValue, 30);
	}

	/**
	 * 显示多少个字符出来,(主要用于链接时,不要显示太多的内容)
	 * 
	 * @param strValue
	 *            内容
	 * @param maxWidth
	 *            最大宽度
	 * @return 处理后的结果
	 */
	public static String subStr2(String strValue, int maxWidth) {
		int length = getEngLength(strValue);

		if (length <= maxWidth)
			return strValue;
		int iPos = 0;
		char c;
		StringBuffer strBuf = new StringBuffer(50);
		for (int idx = 0; idx < strValue.length(); idx++) {
			c = strValue.charAt(idx);
			++iPos;
			if ((int) c > 255) { // 中文字
				++iPos;
			}
			strBuf.append(c);
			if (iPos >= (maxWidth - 3)) {
				strBuf.append("...");
				break;
			}

		}
		return strBuf.toString();

	}

	/**
	 * 显示多少个字符出来,(主要用于链接时,不要显示太多的内容)
	 * 
	 * @param strValue
	 *            内容
	 * @param maxWidth
	 *            最大宽度
	 * @return 处理后的结果
	 */
	public static String subStr(String strValue, int maxWidth) {
		int length = getEngLength(strValue);

		if (length <= maxWidth)
			return strValue;
		int iPos = 0;
		char c;
		StringBuffer strBuf = new StringBuffer();
		for (int idx = 0; idx < strValue.length(); idx++) {
			c = strValue.charAt(idx);
			++iPos;
			if ((int) c > 255) { // 中文字
				++iPos;
			}
			strBuf.append(c);
			if (iPos >= (maxWidth - 3)) {
				strBuf.append("...");
				break;
			}

		}
		return "<span title='" + strValue + "'>" + strBuf.toString()
				+ "</span>";

	}

	/**
	 * 根据分格符分离字符串 2004-07-16 cy添加
	 * 
	 * @param list
	 *            分离结果串放到list中
	 * @param s
	 *            要分离的字符串
	 * @param a
	 *            分隔符
	 */
	public static void separate(ArrayList list, String s, String a) {
		if (list == null) {
			return;
		}
		if (s.indexOf(a) < 0) {
			if (!s.equalsIgnoreCase("")) {
				list.add(s);
			}
		} else if (s.indexOf(a) == 0) {
			if (!s.equalsIgnoreCase(a)) {
				separate(list, s.substring(1), a);
			}
		} else {
			list.add(s.substring(0, s.indexOf(a)));
			separate(list, s.substring(s.indexOf(a)), a);
		}
	}

	/**
	 * 2004-07-19 cy添加 替换方法:主要用于表格缩进处理
	 * 
	 * @param strSource
	 *            源字串
	 * @return String html缩进空格
	 */
	public static String replaceLen(String strSource) {
		StringBuffer strRet = new StringBuffer();
		for (int idx = 0; idx <= (strSource.length() - 2); idx++)
			strRet.append("&nbsp;");
		return strRet.toString();
	}

	/**
	 * 2004-07-19 cy添加 替换方法:主要用于表格缩进处理
	 * 
	 * @param strSource
	 *            源字串
	 * @return String html缩进空格
	 */
	public static String replaceLen2(String strSource) {
		StringBuffer strRet = new StringBuffer();

		for (int idx = 0; idx < ((strSource.length() - 2) * 2); idx++)
			strRet.append("&nbsp;");
		return strRet.toString();
	}

	/**
	 * 2004-07-19 cy添加 主要用于处理被选择的单选框
	 * 
	 * @param aryData
	 *            列表数据
	 * @param strCheckID
	 *            被选取的ID
	 * @return String strValue--checked标志
	 */

	public static String getChecked(List aryData, String strCheckID) {
		String strValue = "";
		String strTmp = "";
		for (int idx = 0; idx < aryData.size(); idx++) {
			strTmp = (String) aryData.get(idx);
			if (strCheckID.equals(strTmp)) {
				strValue = "checked";
				break;
			}
		}
		return strValue;
	}

	public static boolean isNull(String value) {
		if ("".equals(value) || null == value || value.equalsIgnoreCase("null")) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {

		System.out.println(StringUtils.giveUpZore("20."));
		// System.out.println((int)'\r');
		// System.out.println((int)'\n');
		// System.out.println((int)' ');
	}

	/**
	 * ---------- 董中光添加 ------------------ /**
	 * 将String[]转换为形如"…，…，…，…"的字符串，用来在删除时使用 董中光
	 * 
	 * @param fid
	 *            fid的String[]
	 * @return
	 */
	public static String arrayToString(String[] fid) {
		String serperater = ",";

		return arrayToString(fid, serperater);
	}

	public static String arrayToString(String[] fid, String serperater) {
		String strfid = "";
		for (int i = 0; i < fid.length; i++) { // 将所有选择的记录删除
			if (i == 0)
				strfid = fid[0];
			else
				strfid += serperater + fid[i];
		}
		return strfid;
	}

	/*
	 * 将String null,empty转化为0 @param str 原字符串 @return String
	 */
	public static String emptyStringToZero(String str) {
		if (str == null || str.equals(""))
			return "0";
		return str;
	}

	/*
	 * 将int 0转化为"",如果不为零,则强制转为String @double zero 零 @return String
	 */
	public static String zeroToEmptyString(int zero) {
		if (zero == 0)
			return "";
		return String.valueOf(zero);
	}

	/*
	 * 将float 0转化为"",如果不为零,则强制转为String @double zero 零 @return String
	 */
	public static String zeroToEmptyString(float zero) {
		if (zero == 0f)
			return "";
		return String.valueOf(zero);
	}

	/**
	 * 字节数字转换 自动转换加kb或Byte
	 * 
	 * @param strSize
	 *            数字字串
	 * @return 字节转换
	 */
	public static String sizeToKB(String strSize) {
		long lngSize = 0l;
		lngSize = SumUtils.strToLong(strSize);
		StringBuffer sBufSize = new StringBuffer();
		if (lngSize < 1024)
			return sBufSize.append(lngSize).append("Byte").toString();
		strSize = SumUtils.sum(strSize + "/1024");
		if (SumUtils.strToDouble(strSize) > 1000)
			strSize = SumUtils.format(strSize, "0,000.0");
		else
			strSize = SumUtils.format(strSize, "0.0");
		sBufSize.append(strSize).append("KB");
		return (sBufSize.toString());
	}

	/*
	 * 将double 0转化为"",如果不为零,则强制转为String @double zero 零 @return String
	 */
	public static String zeroToEmptyString(double zero) {
		if (zero == 0d)
			return "";
		return String.valueOf(zero);
	}

	// ---------- 董中光添加 ------------------

	public static String[] split_old(String source, String delimiter) {
		int iCount, iPos, iLength;
		boolean bEnd; // 判断结束的符号是不是分割符号
		String sTemp; //  
		String[] aSplit = null, t = null; // aSplit结果返回 t临时的

		sTemp = source;
		iCount = 0;
		iLength = delimiter.length();
		bEnd = sTemp.endsWith(delimiter);

		for (;;) {
			iPos = sTemp.indexOf(delimiter);
			if (iPos < 0) // 直到没有分割的字符串，就退出
				break;
			else {
				if (iCount > 0)
					t = aSplit; // 第一次，不用拷贝数组
				iCount++;
				aSplit = new String[iCount]; // 新的数组，

				if (iCount > 1) { // 不是第一次，拷贝数组
					for (int i = 0; i < t.length; i++)
						aSplit[i] = t[i];
				}
				aSplit[iCount - 1] = sTemp.substring(0, iPos);
				sTemp = sTemp.substring(iPos + iLength); // 取余下的字符串
			}
		}

		if ((sTemp.length() >= 0) || bEnd) { // 判断最后剩余的 String，如果最后的字符是分割符号
			if (iCount > 0)
				t = aSplit;
			iCount++;
			aSplit = new String[iCount];
			if (iCount > 1) {
				for (int i = 0; i < t.length; i++)
					aSplit[i] = t[i];
			}

			aSplit[iCount - 1] = sTemp;
		}

		return aSplit;
	}

	public static String addPara(String path, String param) {
		String strSplit = "?";
		if (path.indexOf("?") > 1) {
			strSplit = "&";
		}
		return path + strSplit + param;
	}

	/**
	 * 将字符串解析为字符串数组
	 * 
	 * @param idStr
	 *            指定的字符串
	 * @param split
	 *            指定分隔符
	 * @return String[]
	 */
	public static String[] split(String idStr, String split) {
		String[] idArray = null;
		if (idStr != null && idStr.length() > 0) {
			ArrayList list = new ArrayList();
			idStr = idStr.trim();
			StringTokenizer tokenizer = new StringTokenizer(idStr, split);

			while (tokenizer.hasMoreTokens()) {
				String tempId = tokenizer.nextToken();
				if (tempId != null) {
					list.add(tempId);
				}
			}
			if (list.size() > 0) {
				idArray = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					idArray[i] = (String) list.get(i);
				}
			} else {
				idArray = new String[0];
			}
		}
		return idArray;
	}

	/**
	 * 将数组转换成 以逗号隔开的字符串 用于sql in () 语句 '11','111','111'
	 * 
	 * @param str
	 * @return
	 */
	public static String getInSqlWhere(String[] str) {
		String retStr = "";
		for (int i = 0; i < str.length; i++) {
			retStr += "'" + str[i] + "'" + ",";
		}
		return retStr.length() > 0 ? retStr.substring(0, retStr
				.lastIndexOf(",")) : retStr;
	}

	/**
	 * 去掉小数最后面的无效零 2.00 变成 2
	 * 
	 * @param num
	 * @return
	 */
	public static String giveUpZore(String num) {
		if (StringUtils.isNull(num)) {
			return "";
		}
		String tmpStr = num.substring(num.indexOf(".") + 1, num.length());// 得到小数
		int pos = tmpStr.lastIndexOf("0");
		while (pos != -1) {
			tmpStr = tmpStr.substring(0, pos);
			pos = tmpStr.lastIndexOf("0");
		}
		if (tmpStr.length() > 0) {
			return num.substring(0, num.indexOf(".") + 1) + tmpStr;
		}
		return num.substring(0, num.indexOf(".")) + tmpStr;
	}

	/**
	 * 去掉字符串最后的逗号
	 * 
	 * @param str
	 * @return
	 */
	public static String throwComma(String str) {
		if (isNull(str))
			return "";
		if (str.lastIndexOf(",") == str.length() - 1)
			str = str.substring(0, str.lastIndexOf(","));
		return str;
	}

	/**
	 * 将字符串转换成数组
	 * 
	 * @param string
	 *            String,String,String,
	 * @param isAdd
	 *            TODO
	 * @return
	 */
	public static String[] stringToArray(String string, boolean isAdd) {
		StringTokenizer stString = new StringTokenizer(string, ",");
		String[] array = new String[stString.countTokens()];
		int i = 0;
		while (stString.hasMoreTokens()) {
			if (isAdd) {
				array[i++] = "'" + stString.nextToken() + "'";
			} else {
				array[i++] = stString.nextToken();
			}
		}
		return array;
	}

	/**
	 * 根据字符串数组,生成有关的in SQL 语句.例如: ('id1','id2','id3')包括前后的括号
	 * 
	 * @param Ids
	 * @return String
	 */
	public static String getInSqlFromArray(String[] Ids) {
		StringBuffer conditionSb = new StringBuffer("(  ");
		if (Ids != null && Ids.length > 0) {
			for (int i = 0; i < Ids.length; i++) {
				String tempId = Ids[i];
				if (tempId != null)
					conditionSb.append("'").append(tempId).append("'").append(
							",");
			}
		}
		String returnStr = conditionSb.toString();
		returnStr = returnStr.substring(0, returnStr.length() - 1); // 截掉最后面的逗号
		returnStr = (returnStr + "  )").trim(); // 增加 )
		if (Ids == null || Ids.length == 0)
			return null;
		else
			return returnStr;
	}

	/**
	 * 根据字符串数组,生成有关的in SQL 语句.例如: 'id1','id2','id3' 不包括前后的括号
	 * 
	 * @param Ids
	 * @return String
	 */
	public static String getInSqlFromArrayNotIncludeSign(String[] Ids) {
		StringBuffer conditionSb = new StringBuffer(" ");
		if (Ids != null && Ids.length > 0) {
			for (int i = 0; i < Ids.length; i++) {
				String tempId = Ids[i];
				if (tempId != null)
					conditionSb.append("'").append(tempId).append("'").append(
							",");
			}
		}
		String returnStr = conditionSb.toString();
		returnStr = returnStr.substring(0, returnStr.length() - 1); // 截掉最后面的逗号
		// returnStr=(returnStr+" ").trim(); //增加 )
		if (Ids == null || Ids.length == 0)
			return null;
		else
			return returnStr;
	}

	/**
	 * 将两个字符串 按照decimal 的值 相加,返回和值的String
	 * 
	 * @param sumDecimalStr
	 * @param paramDecimalStr
	 * @return String
	 */
	public static String addTwoStringByBigDecimalValue(String sumDecimalStr,
			String paramDecimalStr) {
		sumDecimalStr = (sumDecimalStr == null || sumDecimalStr.length() == 0) ? "0"
				: sumDecimalStr;
		paramDecimalStr = (paramDecimalStr == null || paramDecimalStr.length() == 0) ? "0"
				: paramDecimalStr;
		BigDecimal sumDecimal = new BigDecimal(sumDecimalStr);
		BigDecimal paramDecimal = new BigDecimal(paramDecimalStr);
		return (sumDecimal.add(paramDecimal)).toString();// 合并金额
	}

	/**
	 * * 判断字符串是否为null 或 空字符串 *
	 * 
	 * @param value *
	 * @return
	 */
	public static boolean isBlank(String value) {
		return (value == null || "".equals(value.trim()));
	}

	/**
	 * * 判断字符串是否不为null 或 空字符串 *
	 * 
	 * @param value *
	 * @return
	 */
	public static boolean isNotBlank(String value) {
		return !(value == null || "".equals(value.trim()));
	}

	/**
	 * 判断前一个字符串是否为null或空 1.为null时输出后一个字符串 2.不为null直接输出第一个字符串
	 * 
	 * @param srcStr
	 *            原字符串
	 * @param newString
	 *            当srcStr为null或空时输出的字符串
	 * @return String
	 */
	public static String isNullThenString(String srcStr, String newString) {
		if (isNull(srcStr)) {
			return newString;
		} else {
			return srcStr;
		}
	}
	
	 /**
	  * 将数字金额转为千分符分隔的金额格式
	  * @param moneyString
	  * @return
	  */
	 public static String FormatStringMoney(String moneyString)
	 {
		 moneyString= nullToZeroString(moneyString);
		  
		  DecimalFormat df = new DecimalFormat("#,###.00");
		  String formatMoney = df.format( new BigDecimal(moneyString).doubleValue());
		  return formatMoney;
	 }

	/**
	 * 把数组转成字符串，用“,”号分隔
	 * 
	 * @param fid
	 * @return
	 */
	public static String arrayToInString(String[] fids) {
		String serperater = ",";
		String tmp = arrayToString(fids, serperater);
		return "'" + tmp.replaceAll(",", "','") + "'";
	}
}
