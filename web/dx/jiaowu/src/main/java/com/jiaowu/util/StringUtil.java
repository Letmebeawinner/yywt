package com.jiaowu.util;

public class StringUtil {
	/**
	 * 如果s是数字且大于0,则返回true.其它返回false.
	 * @param s
	 * @return
	 */
	public static boolean biggerThanZero(String s){
		if(s==null||s.equals("")){
			return false;
		}else{
			if(Long.parseLong(s)>0){
				return true;
			}else{
				return false;
			}
		}
	}
	
	/**
	 * 如果l是数字且大于0,则返回true.其它返回false.
	 * @param l
	 * @return
	 */
	public static boolean biggerThanZero(Long l){
		if(l!=null&&l>0){
			return true;
		}else{
			return false;
		}
	}
}
