package com.lsr.frame.ws.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	/**
	 * 加密字符串
	 * @param str 字符串
	 * @return 加密后的串
	 */
	public static String encrypt(String str){
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(str.getBytes());
			String result = MD5.toHex(messageDigest.digest());
			return result;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
}
