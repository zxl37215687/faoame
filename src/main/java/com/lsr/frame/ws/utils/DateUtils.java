package com.lsr.frame.ws.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.lsr.frame.base.exception.DefaultException;

public class DateUtils {
		private final static DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		private static final DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd"); 
		private static final DateFormat df3 = new SimpleDateFormat("yyyyMMdd"); 
		private static final DateFormat df4 = new SimpleDateFormat("yyyy-MM"); 
		private static final DateFormat df5 = new SimpleDateFormat("yyyyMM");
		   
		  
		/**
		 * 日期转字符串，格式为yyyyMM,如201311
		 * @param date
		 * @return
		 */ 
		public static String DateTOStrYearMon(Date date){
			return df5.format(date);
		}
		 
		/**
		 * 日期转字符串，格式为yyyy-MM-dd kk:mm:ss
		 * @param date
		 * @return
		 */
		public static String DateTOStr(Date date) {
			return df.format(date);
		}
		 
		/**
		 * 日期转字符串，格式为yyyyMMdd
		 * @param date
		 * @return
		 */
		public static String DateTOStr2(Date date){
			return df3.format(date);
		}
		
		/**
		 * 日期转字符串，格式为yyyy-MM-dd
		 * @param date
		 * @return
		 */
		public static String dateToStrNoTime(Date date){
			return df2.format(date);
		}

		/**
		 * 日期转字符串，格式为yyyy-MM 
		 * @param date
		 * @return
		 */
		public static String dateTOStrNoDay(Date date){
			return df4.format(date);
		}
		
		public static Date StrToDate(String str) throws ParseException {
			if(StringUtils.isBlank(str)){
				return null;
			}else{
				if(str.length() > 10){
					return df.parse(str);
				}else if(str.length() == 10){
					return df2.parse(str);
				}else if(str.length() == 8){
					return df3.parse(str);
				}else if(str.length() == 7){
					return df4.parse(str);
				}else {
					return df5.parse(str);
				}
			}
		}
		
		public static Date parseDateStr(String str, String formate){
			if(StringUtils.isBlank(formate)){
				formate = "yyyy-MM-dd";
			}
			if(StringUtils.isBlank(str)){
				return null;
			}
			try{
				return new SimpleDateFormat(formate).parse(str);
			}catch(ParseException ex){
				throw new RuntimeException(ex);
			}

		}

		public static String getNowDate() {
			Date d1 = new Date();
			String str = df.format(d1);
			return str;
		}

		public static String addMinute(String timeSrc, int addminute)
				throws ParseException {
			Date d1 = StrToDate(timeSrc);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d1);
			cal.add(Calendar.MINUTE, addminute);
			return DateTOStr(cal.getTime());
		}

		/* 比较大小 第一个比等二小就是True */
		public static boolean isLess(String src, String dest) throws ParseException {
			Date d1 = StrToDate(src);
			Date d2 = StrToDate(dest);
			if (d1.getTime() < d2.getTime())
				return true;
			else
				return false;
		}

		// 语言常量定义
		private static final String ENGLISH = "english";

		private static final String CHINESE = "chinese";

		// 星期常量定义
		private static final String ESUNDAY = "Sunday";

		private static final String CSUNDAY = "星期日";

		private static final String EMONDAY = "Monday";

		private static final String CMONDAY = "星期一";

		private static final String ETUESDAY = "Tuesday";

		private static final String CTUESDAY = "星期二";

		private static final String EWEDNESDAY = "Wednesday";

		private static final String CWEDNESDAY = "星期三";

		private static final String ETHURSDAY = "Thursday";

		private static final String CTHURSDAY = "星期四";

		private static final String EFRIDAY = "Friday";

		private static final String CFRIDAY = "星期五";

		private static final String ESATURDAY = "Saturday";

		private static final String CSATURDAY = "星期六";

		private static final String EDEFAULT = "Dimness";

		private static final String CDEFAULT = "不详";

		/**
		 * 得到当前给定日期的年或月或日
		 * 
		 * @param strDate
		 *            字符串型日期（格式：2004-05-20）
		 * @param intFlag
		 *            int 类型(0代表年,1代表月,2代表日)
		 * @return int 成功：年月日的整型值 失败：-1
		 */
		public static int getNumberOfDate(String strDate, int intFlag) {
			StringTokenizer st = new StringTokenizer(strDate, "-");
			// 容错处理
			if (st.countTokens() != 3) {
				return 0;
			}
			int year = Integer.parseInt(st.nextToken());
			int month = Integer.parseInt(st.nextToken());
			int day = Integer.parseInt(st.nextToken());
			if (intFlag == 0) {
				return year;
			} else if (intFlag == 1) {
				return month;
			} else if (intFlag == 2) {
				return day;
			} else {
				return -1;
			}
		}

		/**
		 * 获得当前字符串类型的日期时间值（格式：2004-05-20 00:00:00）
		 * 
		 * @return 日期时间值
		 */
		public static String getCurrentDateTime() {
			Date d = new Date();
			Timestamp t = new Timestamp(d.getTime());
			return t.toString().substring(0, 19);
		}

		/**
		 * 获得当前字符串类型的日期值（格式：2004-05-20）
		 * 
		 * @return 日期值
		 */
		public static String getCurrentDate() {
			Date d = new Date();
			Timestamp t = new Timestamp(d.getTime());
			return t.toString().substring(0, 10);
		}

		/**
		 * 获得当前字符串类型的时间值
		 * 
		 * @return 时间值（格式为"00:00:00"）
		 */
		public static String getCurrentTime() {
			Date d = new Date();
			Timestamp t = new Timestamp(d.getTime());
			return t.toString().substring(11, 19);
		}

		/**
		 * 得到指定的日期
		 * 
		 * @param calendar
		 * @return
		 */
		public static String getCurrentDate(Calendar calendar) {
			String year = String.valueOf(calendar.get(Calendar.YEAR));
			String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
			String date = String.valueOf(calendar.get(Calendar.DATE));
			String retDate = year + "-"
					+ (month.length() > 1 ? month : "0" + month) + "-"
					+ (date.length() > 1 ? date : "0" + date);
			return retDate;
		}

		/**
		 * 获得用某种语言描述的星期
		 * 
		 * @param intWeek
		 *            整型星期值
		 * @param language
		 *            语言类型（目前只支持中文和英文）
		 * @return 用某种语言描述的星期
		 */
		private static String selectWeek(int intWeek, String language) {
			String week = CDEFAULT;

			switch (intWeek) {
			case 1:
				if (language.equals(ENGLISH)) {
					week = EMONDAY;
				} else if (language.equals(CHINESE)) {
					week = CMONDAY;
				}
				break;
			case 2:
				if (language.equals(ENGLISH)) {
					week = ETUESDAY;
				} else if (language.equals(CHINESE)) {
					week = CTUESDAY;
				}
				break;
			case 3:
				if (language.equals(ENGLISH)) {
					week = EWEDNESDAY;
				} else if (language.equals(CHINESE)) {
					week = CWEDNESDAY;
				}
				break;
			case 4:
				if (language.equals(ENGLISH)) {
					week = ETHURSDAY;
				} else if (language.equals(CHINESE)) {
					week = CTHURSDAY;

				}
				break;
			case 5:
				if (language.equals(ENGLISH)) {
					week = EFRIDAY;
				} else if (language.equals(CHINESE)) {
					week = CFRIDAY;

				}
				break;
			case 6:
				if (language.equals(ENGLISH)) {
					week = ESATURDAY;
				} else if (language.equals(CHINESE)) {
					week = CSATURDAY;
				}
				break;
			case 0:
				if (language.equals(ENGLISH)) {
					week = ESUNDAY;
				} else if (language.equals(CHINESE)) {
					week = CSUNDAY;
					// System.out.println("-----------7 chinese
					// week----------"+week);
				}
				break;
			default:
				if (language.equals(ENGLISH)) {
					week = EDEFAULT;
				} else if (language.equals(CHINESE)) {
					week = CDEFAULT;
				}
			}

			return week;
		}

		/**
		 * 获得给定日期的星期
		 * 
		 * @param calendar
		 *            含有给定日期的Calendar对象
		 * @return 成功：整型星期 失败：-1
		 */
		public static int getWeek(Calendar calendar) {
			if (calendar == null) {
				return -1;
			}

			return calendar.get(Calendar.DAY_OF_WEEK) - 1;
		}

		/**
		 * 获得给定日期的中文星期
		 * 
		 * @param calendar
		 *            含有给定日期的Calendar对象
		 * @return 成功：中文星期 失败：null
		 */
		public static String getChineseWeek(Calendar calendar) {
			if (calendar == null) {
				return null;
			}

			int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			// System.out.println("---------------intWeek--------------"+intWeek);
			return selectWeek(intWeek, CHINESE);
		}

		/**
		 * 获得给定日期的英文星期
		 * 
		 * @param calendar
		 *            含有给定日期的Calendar对象
		 * @return 英文星期
		 */
		public static String getEnglishWeek(Calendar calendar) {
			if (calendar == null) {
				return null;
			}

			int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			return selectWeek(intWeek, ENGLISH);
		}

		/**
		 * 获得当前星期
		 * 
		 * @return 整型日期
		 */
		public static String getCurrentWeek() {
			int week = -1;
			Calendar c = SumUtils.stringToCalendar(getCurrentDate());
			week = c.get(Calendar.WEEK_OF_MONTH);
			System.out.println("The current week is:***" + week);
			return (String.valueOf(week));
		}

		/**
		 * 获得当前中文星期
		 * 
		 * @return 中文星期
		 */
		public static String getCurrentChineseWeek() {
			Calendar c = SumUtils.stringToCalendar(getCurrentDate());
			int intWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
			return selectWeek(intWeek, CHINESE);
		}

		/**
		 * 获得当前英文星期
		 * 
		 * @return 英文星期
		 */
		public static String getCurrentEnglishWeek() {
			Calendar c = SumUtils.stringToCalendar(getCurrentDate());
			int intWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
			return selectWeek(intWeek, ENGLISH);
		}

		/**
		 * 得到当前星期(1表示星期一,2为星期二,以此类推) Wengnb Add 2003-09-09'
		 * 
		 * @param calendar
		 *            Calendar
		 * @return int
		 */
		public static int getCurWeek(Calendar calendar) {
			return calendar.get(Calendar.DAY_OF_WEEK) - 1;
		}

		/**
		 * Wengnb Add 2003-09-09'
		 * 
		 * @return
		 */
		public static int getCurWeek() {
			Calendar calendar = Calendar.getInstance();
			return calendar.get(Calendar.DAY_OF_WEEK) - 1;
		}

		/**
		 * Wengnb Add 2003-09-09'
		 * 
		 * @return
		 */
		public static String getCurWeek(boolean toChinese) {
			Calendar calendar = Calendar.getInstance();
			return getCurWeek(calendar, true);
		}

		/**
		 * 得到当前星期(星期期一,星期二,以此类推 Wengnb Add 2003-09-09
		 * 
		 * @param calendar
		 *            Calendar
		 * @param toChinese
		 *            boolean
		 * @return String
		 */
		public static String getCurWeek(Calendar calendar, boolean toChinese) {

			String strRet = "";
			int intWeek = 0;
			// System.out.println("-------calendar.date----------" + calendar.DATE);
			// System.out.println("-------calendar.DayOfWeek-----" +
			// calendar.DAY_OF_WEEK);
			// System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 获取本周的第几天
			// 外国是星期天为每周第一天，所以减一
			// System.out.println("-----------intWeek----------------" + intWeek);
			calendar = null; // 回收
			if (!toChinese) {
				return (String.valueOf(intWeek));
			}

			switch (intWeek) {
			case 1:
				strRet = "星期一";
				break;
			case 2:
				strRet = "星期二";
				break;
			case 3:
				strRet = "星期三";
				break;
			case 4:
				strRet = "星期四";
				break;
			case 5:
				strRet = "星期五";
				break;
			case 6:
				strRet = "星期六";
				break;
			case 0:
				strRet = "星期日";
				break;
			default:
				strRet = "不详";
				break;
			}
			return strRet;
		}

		/**
		 * 取得相差Minutes的日期数，日期格式（2004-8-2 07:20:00）
		 * 
		 * @param strDateTime
		 *            String
		 * @param addMinutes
		 *            int
		 * @return String
		 */
		public static String getDateTimeByAddMinute(String strDateTime,
				int addMinutes) {

			try {
				SimpleDateFormat sdf = null;
				if (strDateTime.length() < 17)
					sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
				else
					sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				StringBuffer sumDate = new StringBuffer();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sdf.parse(strDateTime, new ParsePosition(0)));
				calendar.add(Calendar.MINUTE, addMinutes);
				sdf.format(calendar.getTime(), sumDate, new FieldPosition(0));
				return sumDate.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		/**
		 * 得到相应的下几天的日期 , 例:getDate("2003-03-01",2)结果返回"2003-03-03"
		 * 
		 * @param strDate
		 *            指定的日期
		 * @param intDays
		 *            数字
		 * @return String 最终日期
		 */
		public String getDate(String strDate, int intDays) {
			StringTokenizer st = new java.util.StringTokenizer(strDate, "-");
			String strNewYear = new String("");
			String strNewMonth = new String("");
			String strNewDay = new String("");

			if (st.countTokens() != 3) { // 容错处理-如果分割数不为3，说明日期有误
				return "";
			}
			Calendar calendar = Calendar.getInstance();
			int year = Integer.parseInt(st.nextToken());
			int month = Integer.parseInt(st.nextToken());
			int day = Integer.parseInt(st.nextToken());
			calendar.set(year, month - 1, day);
			calendar.add(Calendar.DAY_OF_MONTH, intDays); // 下一个日期
			// 得到年
			strNewYear = String.valueOf(calendar.get(Calendar.YEAR));
			// 得到月
			strNewMonth = (calendar.get(Calendar.MONTH) + 1 < 10) ? "0"
					+ String.valueOf(calendar.get(Calendar.MONTH) + 1) : String
					.valueOf(calendar.get(Calendar.MONTH) + 1);
			// 得到日
			strNewDay = (calendar.get(Calendar.DAY_OF_MONTH) < 10) ? "0"
					+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) : String
					.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			strDate = strNewYear + "-" + strNewMonth + "-" + strNewDay;
			// 得到当前日期为星期几
			return strDate;
		}

		/**
		 * 实现日期增加功能
		 * 
		 * @param strDate
		 *            参照日期（格式：2004-05-20）
		 * @param dateNumber
		 *            日期增加值
		 * @return 成功：增加后的日期值（格式：2004-05-20） 失败：null
		 */
		public static String getDateSum(String strDate, int dateNumber) {
			Calendar c = null;
			try {
				c = SumUtils.stringToCalendar(strDate);
				if (c == null) {
					return null;
				}
				c.add(Calendar.DATE, dateNumber);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				StringBuffer sumDate = new StringBuffer();
				sdf.format(c.getTime(), sumDate, new FieldPosition(0));
				return sumDate.toString();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		/**
		 * 实现时间相加
		 * 
		 * @param beginDateTime
		 *            2005-07-12 17:56:00
		 * @param time
		 * @return
		 */
		public static String getDateTimeSum(String beginDateTime, String time) {
			System.out.println("");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.parseInt(beginDateTime.substring(0,
					4)));
			calendar.set(Calendar.MONTH, Integer.parseInt(beginDateTime.substring(
					5, 7)) - 1);
			calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(beginDateTime
					.substring(8, 10)));
			calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(beginDateTime
					.substring(11, 13)));
			calendar.set(Calendar.MINUTE, Integer.parseInt(beginDateTime.substring(
					14, 16)));
			calendar.set(Calendar.SECOND, Integer.parseInt(beginDateTime.substring(
					17, 19)));
			calendar.add(Calendar.MINUTE, Integer.parseInt(time));
			return calendar.getTime().toLocaleString();
		}

		/**
		 * 实现日期减少功能
		 * 
		 * @param strDate
		 *            参照日期（格式：2004-05-20）
		 * @param dateNumber
		 *            日期减少值
		 * @return 成功：减少后的日期值（格式：2004-05-20） 失败：null
		 */
		public static String getDateSubtract(String strDate, int dateNumber) {
			try {
				dateNumber = Integer.parseInt("-" + String.valueOf(dateNumber));
				return getDateSum(strDate, dateNumber);
			} catch (Exception e) {
				// e.printStackTrace();
				return null;
			}
		}

		/**
		 * 实现日期相减功能
		 * 
		 * @param strBeginDate
		 *            作为被减数的日期（格式：2004-05-20）
		 * @param strEndDate
		 *            作为减数的日期（格式：2004-05-20）
		 * @return 成功：日期差值 失败：0
		 */
		public static long getDateSubtract(String strBeginDate, String strEndDate) {
			String strTime = "00:00:00";
			strBeginDate += " " + strTime;
			strEndDate += " " + strTime;
			Date beginDate = null;
			Date endDate = null;
			long subtractDate = 0;

			try {
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				ParsePosition pp1 = new ParsePosition(0);
				ParsePosition pp2 = new ParsePosition(0);
				beginDate = formatter.parse(strBeginDate, pp1);
				endDate = formatter.parse(strEndDate, pp2);
				subtractDate = (beginDate.getTime() - endDate.getTime())
						/ (24 * 60 * 60 * 1000);
			} catch (Exception e) {
				// e.printStackTrace();
				return 0;
			}

			return subtractDate;
		}

		/**
		 * 得到日期差 Wengnb Add 2003-09-09 如getDateDiff("2004-02-01"
		 * ,"2004-03-01")结果会等于29天(因为润年)
		 * 
		 * @param beginDate
		 *            String: "2003-09-01"
		 * @param endDate
		 *            String :'2003-09-01'
		 * @return int
		 */
		public static int getDateDiff(String strBeginDate, String strEndDate) {
			int intDays = 0;
			Calendar now = Calendar.getInstance();
			int iBeginYear = getDateNumber(strBeginDate, 0);
			int iEndYear = getDateNumber(strEndDate, 0);
			int intTmp = 0;
			// 容错处理
			if (iBeginYear > iEndYear) {
				return 0;
			}
			for (int i = iBeginYear; i < iEndYear; i++) {
				now.set(i, 11, 31);
				intTmp += now.get(Calendar.DAY_OF_YEAR);
			}
			now.set(iBeginYear, getDateNumber(strBeginDate, 1) - 1, getDateNumber(
					strBeginDate, 2));
			int iStartDays = now.get(Calendar.DAY_OF_YEAR); // 得到开始年份日期xxxx-01-01至现在的日期的总天数
			now.set(iEndYear, getDateNumber(strEndDate, 1) - 1, getDateNumber(
					strEndDate, 2));
			int iEndDays = now.get(Calendar.DAY_OF_YEAR);
			intDays = intTmp + iEndDays - iStartDays;
			return intDays;
		}

		/**
		 * 得到当前给定日期的年或月或日(该函数是给那个日期用的) Wengnb Add 2003-09-09
		 * 
		 * @param strDate
		 *            String :"2003-08-01"
		 * @param intFlag
		 *            int: 类型(0代表年,1代表月,2代表日)
		 * @return int
		 */
		private static int getDateNumber(String strDate, int intFlag) {
			StringTokenizer st = new StringTokenizer(strDate, "-");
			// 容错处理
			if (st.countTokens() != 3) {
				return 0;
			}
			int year = Integer.parseInt(st.nextToken());
			int month = Integer.parseInt(st.nextToken());
			int day = Integer.parseInt(st.nextToken());
			if (intFlag == 0) { // 得到当前年
				return year;
			} else if (intFlag == 1) { // 得到当前月
				return month;
			} else { // 得到当前日
				return day;
			}
		}

		/**
		 * 通过当前日期获取的当前编号,可以在数据库中作为主键使用 当然只能用在每天对应一条记录的情况下 eg: 2003-09-09 得到20031009
		 * 
		 * @return String
		 */

		public static String getNowBh() {
			Calendar cal = Calendar.getInstance();
			int y = cal.get(Calendar.YEAR);
			int m = cal.get(Calendar.MONTH) + 1;
			int d = cal.get(Calendar.DATE);
			int h = cal.get(Calendar.HOUR_OF_DAY);
			int mi = cal.get(Calendar.MINUTE);
			return y + toStr(m) + toStr(d) + toStr(h) + toStr(mi);
		}

		private static String toStr(int i) {
			return i < 10 ? "0" + i : String.valueOf(i);
		}

		/**
		 * 得到现在是当前月的第几周 CY Add 2004-08-04'
		 * 
		 * @return String
		 */
		public static String getWeekNumOfThisMonth() {
			String weekOfMonth = "";
			Calendar calendar = Calendar.getInstance();
			String curDate = getCurrentDate(); // 取得当前日期--对应创建日期
			// System.out.println("-------------curDate-----------" + curDate);
			int year = Integer.parseInt(curDate.substring(0, 4));
			int month = Integer.parseInt(curDate.substring(5, 7));
			int day = Integer.parseInt(curDate.substring(8, 10));
			// System.out.println("----+++++----" + String.valueOf(year) + "-" +
			// String.valueOf(month) + "-" +
			// String.valueOf(day));
			calendar.set(year, month, day);
			weekOfMonth = String.valueOf(Calendar.WEEK_OF_MONTH);
			// 通过WEEK_OF_MONTH获取的当前月拥有几个星期的数值，所以还需要转化取得当前是第几周才行
			// System.out.println("----------weekOfMonth-----------" + weekOfMonth);
			return weekOfMonth;
		}

		/**
		 * 得到当前日期是星期几 CY Add 2004-08-28'
		 * 
		 * @return String
		 */

		public static String getWeek(String strDate, int intDays) {
			java.util.StringTokenizer st = new java.util.StringTokenizer(strDate,
					"-");
			String strNewYear = new String("");
			String strNewMonth = new String("");
			String strNewDay = new String("");

			if (st.countTokens() != 3) { // 容错处理
				return "";
			}
			java.util.Calendar calendar = null;
			calendar = java.util.Calendar.getInstance();
			int year = Integer.parseInt(st.nextToken());
			int month = Integer.parseInt(st.nextToken());
			int day = Integer.parseInt(st.nextToken());
			calendar.set(year, month - 1, day);
			calendar.add(Calendar.DAY_OF_MONTH, intDays); // 下一个日期
			// 得到年
			strNewYear = String.valueOf(calendar.get(Calendar.YEAR));
			// 得到月
			strNewMonth = (calendar.get(Calendar.MONTH) + 1 < 10) ? "0"
					+ String.valueOf(calendar.get(Calendar.MONTH) + 1) : String
					.valueOf(calendar.get(Calendar.MONTH) + 1);
			// 得到日
			strNewDay = (calendar.get(Calendar.DAY_OF_MONTH) < 10) ? "0"
					+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) : String
					.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
			strDate = strNewYear + "-" + strNewMonth + "-" + strNewDay;
			// 得到当前日期为星期几
			String myWeek = getCurWeek(calendar, true);

			return myWeek;
		}

		/**
		 * 得到月份的第一天是礼拜几 如：2005-03-01，则返回 2
		 * 
		 * @param date
		 *            String 格式为2005-03-01
		 * @return int add by lhd
		 */
		public static int getFirstDayOfWeekInMonth(String date) {
			StringTokenizer st = new StringTokenizer(date, "-");
			if (st.countTokens() != 3) { // 容错处理
				return -1;
			}
			Calendar calendar = Calendar.getInstance();
			int year = Integer.parseInt(st.nextToken());
			int month = Integer.parseInt(st.nextToken());
			calendar.set(year, month - 1, 1);
			int iWeek = getCurWeek(calendar);
			if (iWeek == 0) {
				iWeek = 7;
			}
			return iWeek;
		}

		/**
		 * 得到给定日期月份的天数
		 * 
		 * @param date
		 *            String
		 * @return int add by lhd
		 */
		public static int getDayNumInMonth(String date) {
			int month[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
			StringTokenizer st = new StringTokenizer(date, "-");
			if (st.countTokens() != 3) { // 容错处理
				return -1;
			}
			int year = Integer.parseInt(st.nextToken());
			int tmpMonth = Integer.parseInt(st.nextToken());
			int dayNum = month[tmpMonth - 1];
			if (tmpMonth == 2 && year % 4 == 0) {// 如果为闰年的，二月的天数为29天
				++dayNum;
			}
			return dayNum;
		}

		/**
		 * 根据所给日期得到所给日期所在周的星期一到星期天的日期
		 * 
		 * @param date
		 *            String
		 * @return String[]
		 */
		public static String[] getDateByWeek(String date) {
			StringTokenizer st = new StringTokenizer(date, "-");
			Calendar calendar = Calendar.getInstance();
			String[] dateOfWeek = new String[7];
			int year = Integer.parseInt(st.nextToken());
			int month = Integer.parseInt(st.nextToken());
			int day = Integer.parseInt(st.nextToken());
			calendar.set(year, month - 1, day);
			int dayOfWeek = getCurWeek(calendar);
			if (dayOfWeek == 0) {// 国外从礼拜天开始
				dayOfWeek = 7;
			}
			System.out.println("The dayOfWeek is:-->" + dayOfWeek);
			String tmpDate = getDateSum(date, -(dayOfWeek - 1));
			System.out.println("The tmpDate is:" + tmpDate);
			for (int i = 0; i < dateOfWeek.length; i++) {
				dateOfWeek[i] = getDateSum(tmpDate, i);
			}
			return dateOfWeek;
		}

		/**
		 * 把日期格式化为中文的年月日
		 * 
		 * @param sDate
		 *            String

		 */
		public static String toChineseDate(String sDate) {
			String[] arrStr = sDate.split("-");
			String str = sDate;
			if (arrStr.length == 3) {
				str = arrStr[0] + "年" + arrStr[1] + "月" + arrStr[2] + "日";
			}
			return str;
		}

		/**
		 * 把日期格式化为中文的年月日
		 * 
		 * @param date
		 *            Date

		 */
		public static String toChineseDate(Date date) {
			String str;
			Format ft = new SimpleDateFormat("yyyy年MM月dd日");
			str = ft.format(date);
			return str;
		}

		/**
		 * 把日期格式化为中文的年月
		 * 
		 * @param sDate
		 *            String

		 */
		public static String toChineseMonth(String sDate) {
			String[] arrStr = sDate.split("-");
			String str = sDate;
			if (arrStr.length >= 2) {
				str = arrStr[0] + "年" + arrStr[1] + "月";
			}
			return str;
		}

		/**
		 * 把日期格式化为中文的年
		 * 
		 * @param sDate
		 *            String

		 */
		public static String toChineseYear(String sDate) {
			String[] arrStr = sDate.split("-");
			String str = arrStr[0] + "年";

			return str;
		}

		/**
		 * 对于查询期间的处理，如年月之间的 例如查询2005-2到2005-3的情况 主要思路：between '2005-2-1' and
		 * '2005-4-1' 减1
		 * 
		 * @param sDate1
		 *            String
		 * @param sDate2
		 *            String

		 */
		public static String getBetweenPeriodSQL(String sDate1, String sDate2) {
			String sSql = " between Date('" + sDate1 + "' and Date('" + sDate2
					+ "') ";
			String[] arrStr;

			try {
				arrStr = sDate1.split("-");
				sDate1 = " Date('" + arrStr[0] + "-" + arrStr[1] + "-01') ";

				arrStr = sDate2.split("-");
				int ii = Integer.parseInt(arrStr[1]);
				if (ii == 12) {
					sDate2 = arrStr[0] + "-" + arrStr[1] + "-31";
				} else {// 日期减1
					sDate2 = " Date( Days('" + arrStr[0] + "-" + (ii + 1)
							+ "-01') - 1 ) ";
				}

				sSql = " between " + sDate1 + " and " + sDate2 + " ";
			} catch (Exception ex) {
				System.out
						.println(" error : "
								+ ex.getMessage());
			}
			return sSql;
		}

		// public static String toDate(String strDate) {
		// ActiveUsers au = ActiveUsers.getInstance();
		// String strRet = strDate;
		// if (au.isOracle()) {
		// strRet = "to_date('" + strRet + "','yyyy-mm-dd')";
		// }else {
		// strRet = "'" + strDate + "'";
		// }
		//
		// return strRet;
		// }
		//
		// public static String toDateTime(String strDate) {
		// ActiveUsers au = ActiveUsers.getInstance();
		// String strRet = strDate;
		// if (au.isOracle()) {
		// strRet = "to_date('" + strRet + "','yyyy-mm-dd hh24:mi:ss')";
		// } else {
		// strRet = "'" + strDate + "'";
		// }
		// return strRet;
		// }

		public static String[] getCurrentMonthBound() {
			Calendar cal = Calendar.getInstance();
			int y = cal.get(Calendar.YEAR);
			int m = cal.get(Calendar.MONTH) + 1;
			String strMonth = y + "-" + toStr(m);
			String beginDate = strMonth + "-01";

			cal.set(Calendar.DATE, 1);
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DATE, -1);

			String endDate = strMonth + "-" + cal.get(Calendar.DATE);

			return new String[] { beginDate, endDate };
		}

		public static String getNextMonth(String curDate, int monthNum) {
			Calendar calendar = Calendar.getInstance();
			int year = getDateNumber(curDate, 0);
			int month = getDateNumber(curDate, 1);
			int day = getDateNumber(curDate, 2);
			calendar.set(year, month - 1, day);
			calendar.add(Calendar.MONTH, monthNum);
			return getCurrentDate(calendar);
		}

		private static String strCh[] = { "〇", "一", "二", "三", "四", "五", "六", "七",
				"八", "九" };

		private static String strUnit[] = { "", "十", "百", "千", "万", "年", "月", "日" };

		public static String getChineseDate(Date date) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = format.format(date);
			StringBuffer retStr = new StringBuffer();
			String tempStr = dateStr.substring(0, 4);
			for (int i = 0; i < tempStr.length(); i++) {
				retStr.append(strCh[Integer.parseInt(tempStr.charAt(i) + "")]);
			}
			retStr.append(strUnit[5]);

			if (dateStr.charAt(5) == '0') {

			} else if (dateStr.charAt(5) == '1') {
				retStr.append(strUnit[1]);
			} else {
				retStr.append(strCh[Integer.parseInt(dateStr.charAt(5) + "")]);
				retStr.append(strUnit[1]);
			}
			if (dateStr.charAt(6) == '0') {

			} else {
				retStr.append(strCh[Integer.parseInt(dateStr.charAt(6) + "")]);
			}
			retStr.append(strUnit[6]);

			if (dateStr.charAt(8) == '0') {

			} else if (dateStr.charAt(8) == '1') {
				retStr.append(strUnit[1]);
			} else {
				retStr.append(strCh[Integer.parseInt(dateStr.charAt(8) + "")]);
				retStr.append(strUnit[1]);
			}
			if (dateStr.charAt(9) == '0') {

			} else {
				retStr.append(strCh[Integer.parseInt(dateStr.charAt(9) + "")]);
			}
			retStr.append(strUnit[7]);
			return retStr.toString();
		}

		public static String convertDigToCh(int intDig) {
			String str0 = "";
			int temp;
			String strdig = String.valueOf(intDig);
			strdig = new StringBuffer(strdig).reverse().toString();
			if (intDig < 10000) {
				for (int i = 0; i < strdig.trim().length(); i++) {
					if (strdig.charAt(i) == '0') {
						str0 += "0";
					} else {
						if (i == 1 && strdig.charAt(i) == '1'
								&& strdig.trim().length() == 2) {
							str0 += strUnit[i];
						} else {
							str0 += strUnit[i] + strCh[strdig.charAt(i) - 48];
						}
					} 
				}
				strdig = new StringBuffer(str0).reverse().toString();
				int index = strdig.indexOf("00");
				while (index != -1) {
					strdig = strdig.substring(0, index) + "0"
							+ strdig.substring(index + 2, strdig.length());
					index = strdig.indexOf("00");
				}
				if (strdig.endsWith("0")) {
					strdig = strdig.substring(0, strdig.length() - 1);
				}
				strdig = strdig.replace('0', '零');
			}
			return strdig;
		}
		
		/**
		 * @param date
		 * @return
		 */
		public static int getMonth(Date date){
			if(date == null){
				date = new Date();
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar.get(Calendar.MONTH) + 1;
		}
		
		/**
		 * @param date
		 * @return
		 */
		public static int getMonth(String dateStr,String formate){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.parseDateStr(dateStr, formate));
			return calendar.get(Calendar.MONTH) + 1;
		}
		
		/**
		 * 获取某年某月的最后一天
		 * @param year
		 * @param month 1月从1开始
		 * @return
		 */
		 public static String getLastDayOfMonth(int year, int month) {   
			 Calendar cal = Calendar.getInstance();   
	         cal.set(Calendar.YEAR, year);   
	         cal.set(Calendar.MONTH, month-1);   
	         cal.set(Calendar.DAY_OF_MONTH, 1);  
	         int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
	         cal.set(Calendar.DAY_OF_MONTH, value);   
	        return  new   SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
	     } 
		 
		 /**
		  * 获取某年某月的第一天
		  * @param year
		  * @param month
		  * @return
		  */
		 public static String getFirstDayOfMonth(int year, int month) {   
	         Calendar cal = Calendar.getInstance();   
	         cal.set(Calendar.YEAR, year);   
	         cal.set(Calendar.MONTH, month-1);
	         cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
	        return   new   SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
	     } 
	 
		 
		 /**
		 * 获取某年某月的最后一天
		 * @param year
		 * @param month 1月从1开始
		 * @return
		 */
		 public static String getLastDayOfMonth(String dateStr) throws DefaultException { 
			try{
				int year = getYearByDateStr(dateStr);
				int month = getMonthByDateStr(dateStr); 
				return getLastDayOfMonth(year,month);
			}catch(Exception ex	){
				ex.printStackTrace();
				throw new DefaultException(ex);
			}
	     } 
		 
		 /**
		  * 获取某年某月的第一天
		  * @param year
		  * @param month
		  * @return
		  */
		 public static String getFirstDayOfMonth(String dateStr) throws DefaultException {   
			 try{
				 int year = getYearByDateStr(dateStr);
				 int month = getMonthByDateStr(dateStr); 
				 return getFirstDayOfMonth(year,month);
			 }catch(Exception ex){
					ex.printStackTrace();
					throw new DefaultException(ex);
			}
	     } 
		 
		 public static String[] getFirstLastDateStrOfMonth(int year,int month){
			 String firstDate = getFirstDayOfMonth(year, month);
			 String lastDate = getLastDayOfMonth(year, month);
			 return new String[]{firstDate,lastDate};
		 }
		 
		 /**
		  * 根据日期字符串获取年份
		  * @param dateStr
		  * @return
		  */
		 public static int getYearByDateStr(String dateStr)throws ParseException{
			
			 try{
				 Calendar cal = Calendar.getInstance();
				 cal.setTime(StrToDate(dateStr));
				 int year = cal.get(Calendar.YEAR);
				 return year;
			 }catch(ParseException ex){
				 ex.printStackTrace();
				 throw ex;
			 }
		 }
		 
		 
		 
		 /**
		  * 根据日期字符才获取月份，注：1代表一月份
		  * @param dateStr
		  * @return
		  */
		 public static int getMonthByDateStr(String dateStr)throws ParseException{
			 try{
				 Calendar cal = Calendar.getInstance();
				 cal.setTime(StrToDate(dateStr));
				 int month = cal.get(Calendar.MONTH)+1;
				 return month;
			 }catch(ParseException ex){
				 ex.printStackTrace();
				 throw ex;
			 }
		 }
		 
}
