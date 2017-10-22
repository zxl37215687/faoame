package com.lsr.frame.ws.control.access;

public class ZTThread {
	private static ThreadLocal ztThread = new ThreadLocal() {

		protected synchronized Object initialValue() {

			return null;

		}

	};

	

	public static String getZtId() {

		return ((CommonInfo)ztThread.get()).getZtId();

	}

	public static String getYear() {

		return ((CommonInfo)ztThread.get()).getYear();

	}
	
	public static String getOrgId(){
		return ((CommonInfo)(ztThread.get())).getOrgId();
	}
	
	public static String getSectionId(){
		return ((CommonInfo)(ztThread.get())).getSectionId();
	}
	
	public static String getUserId(){
		return ((CommonInfo)(ztThread.get())).getUserId();
	}
	
	public static String getUserName(){
		return ((CommonInfo)(ztThread.get())).getUserName();
	}
	
	public static String getLoginIp(){
		return ((CommonInfo)(ztThread.get())).getLoginIP();
	}

	public static CommonInfo get() {

		return (CommonInfo)ztThread.get();

	}

	public static void set(CommonInfo commonInfo) {

		ztThread.set(commonInfo);

	}	

	public static void clear(){
		ztThread.set(null);
	}
}
