package com.lsr.frame.ws.utils;

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

}
