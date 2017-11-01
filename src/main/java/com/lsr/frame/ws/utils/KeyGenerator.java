package com.lsr.frame.ws.utils;

import java.net.InetAddress;
import java.util.Random;
import java.util.UUID;

/**
 * 
 * @author kuaihuolin
 *
 */
public class KeyGenerator {
	
	/**
	 * 生成32位编码
	 * 
	 * @return string
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
private static LongIDGenerator longIdGenerator = new LongIDGenerator();
	
	private static StringIDGenerator stringIdGenerator = new StringIDGenerator();

	public static final int LONG = 0;

	public static final int STRING = 1;

	/**
	 * 根据类别生成ID
	 * 
	 * @param type
	 *            LONG表示生成长整形ID STRING表示生成字符串ID
	 * @return
	 */
	public static Object generate(int type) {
		switch (type) {
		case 1:
			return generateStringID();
		default:
			return generateLongID();
		}
	}

	public static String generateStringID() {
		return stringIdGenerator.generateID();
	}

	public static Long generateLongID() {
		return longIdGenerator.generateID();
	}
	
	public static class LongIDGenerator{
		/**
		 * Long共64位 分布如下： 符号1位 时间42位 统计16位 随机数5位
		 */
		private static final int TIME_SIZE = 42;

		private static final int RANDOM_SIZE = 5;

		private static final int COUNT_SIZE = 16;

		static final long MAX_TIME = 1L << TIME_SIZE;

		static final int MAX_COUNT = 1 << COUNT_SIZE;

		static final int MAX_RANDOM = 1 << RANDOM_SIZE;

		public LongIDGenerator() {
		}

		private long genLongID() {
			/**
			 * Long共64位 分布如下： 符号 时间 统计 随机数
			 */
			return (getTime() << (COUNT_SIZE + RANDOM_SIZE))
					| (getCount() << RANDOM_SIZE) | getRandom();
		}

		private long getTime() {
			return System.currentTimeMillis() % MAX_TIME;
		}

		private Random random = new Random();

		private int getRandom() {
			return Math.abs(random.nextInt()) % MAX_RANDOM;
		}

		private static int count = 0;

		private static synchronized int getCount() {
			return (count++) % MAX_COUNT;
		}

		public Long generateID() {
			return new Long(genLongID());
		}

	}
	
	public static class StringIDGenerator{
		private static short counter = (short) 0;

		private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

		private static final String areaCode;
		static {
			areaCode = PropertyUtils.getProperty("area_code");
		}

		private static final int IP;
		static {
			int ipadd;
			try {
				ipadd = toInt(InetAddress.getLocalHost().getAddress());
			} catch (Exception e) {
				ipadd = 0;
			}
			IP = ipadd;
		}

		private static int toInt(byte[] bytes) {
			int result = 0;
			for (int i = 0; i < 4; i++) {
				result = (result << 8) - Byte.MIN_VALUE + bytes[i];
			}
			return result;
		}

		protected int getIP() {
			return IP;
		}

		/**
		 * Unique across JVMs on this machine (unless they load this class in the
		 * same quater second - very unlikely)
		 */
		protected int getJVM() {
			return JVM;
		}

		/**
		 * Unique in a millisecond for this JVM instance (unless there are >
		 * Short.MAX_VALUE instances created in a millisecond)
		 */
		private static synchronized short getCount() {
			if (counter < 0)
				counter = 0;
			return counter++;
		}

		/**
		 * Unique down to millisecond
		 */
		protected short getHiTime() {
			return (short) (System.currentTimeMillis() >>> 32);
		}

		protected int getLoTime() {
			return (int) System.currentTimeMillis();
		}

		/**
		 * 格式化成8位
		 */
		protected String format(int intval) {
			String formatted = Integer.toHexString(intval);
			StringBuffer buf = new StringBuffer("00000000");
			buf.replace(8 - formatted.length(), 8, formatted);
			return buf.toString();
		}

		/**
		 * 格式化成4位
		 */
		protected String format(short shortval) {
			String formatted = Integer.toHexString(shortval);
			StringBuffer buf = new StringBuffer("0000");
			buf.replace(4 - formatted.length(), 4, formatted);
			return buf.toString();
		}

		/**
		 * 行政区号 6位
		 */
		protected String getAreaCode() {
			return areaCode;
		}

		/**
		 * 以行政区号为前缀 如果行政区号没有设置 则以IP为前缀
		 */
		protected String getPrefix() {
			String prefix = getAreaCode();
			if (prefix == null || prefix.equals("")) {
				prefix = format(getIP());
			}else{
				if(prefix.length()>8){
					prefix = prefix.substring(0, 8);
				}
			}
			return prefix;
		}
		
		/**
		 * 生成ID 
		 */
		public String generateID() {
			return new StringBuffer(32)
					.append(getPrefix())
					.append(format(getJVM()))
					.append(format(getHiTime()))
					.append(format(getLoTime()))
					.append(format(getCount()))
					.toString();
		}
		
	}
}
