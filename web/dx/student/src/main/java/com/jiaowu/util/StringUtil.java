package com.jiaowu.util;

import com.a_268.base.util.CollectionUtils;

import java.util.Collection;
import java.util.Random;

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

	public static String getRandomString(int strLength) {
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < strLength; i++) {
			if (random.nextBoolean()) {
				int charInt = 48 + random.nextInt(10);
				char c = (char) charInt;
				buffer.append(c);
			} else {
				int charInt = 65;
				if (random.nextBoolean())
					charInt = 65 + random.nextInt(26);
				else
					charInt = 97 + random.nextInt(26);
				if (charInt == 79)
					charInt = 111;
				char c = (char) charInt;
				buffer.append(c);
			}
		}
		return buffer.toString();
	}

	public static boolean isNotNumeric(String str){
		return !isNumeric(str);
	}


	/**
	 * 判断字符穿是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		if(str==null||str.equals("")){
			return false;
		}
		for (int i = str.length();--i>=0;){
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}

	/**
	 * 给每个元素加上单引号
	 * 去掉最后一个逗号
	 * @param list 请确保参数不为空, 否则会报NPE
	 */
	public static String listToString(Collection<?> list) {
	    if (CollectionUtils.isEmpty(list)) {
	        return "";
        }

		StringBuilder sb = new StringBuilder();
		Object[] temp = list.toArray();
		for (Object aTemp : temp) {
			if (!"".equals(aTemp) && aTemp != null) {
				sb.append("'").append(aTemp).append("',");
			}
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
	}
}
