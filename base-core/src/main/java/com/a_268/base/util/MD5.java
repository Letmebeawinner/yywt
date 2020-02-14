package com.a_268.base.util;

import java.security.MessageDigest;
/**
 * 32位MD5加密工具类
 * @author s.li
 */
public class MD5 {
	/**
	 * 获取MD5加密串
	 * @param strVal 要进行MD5加密的字符
	 * @return 返回加密过的字符
	 */
	public static final String getMD5(String strVal) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = strVal.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				str[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			return new String(str);
		}catch(Exception e){}
		return null;
	}

	public static void main(String args[]){
		System.out.println(getMD5("admin"));
	}
}
