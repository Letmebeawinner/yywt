package com.jiaowu.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */
public class RegExpressionUtil {

    public static boolean isEmail(String email) {
        Pattern emailRes = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher mat = emailRes.matcher(email.toLowerCase());
        return mat.matches();
    }

    public static boolean isMobile(String number) {
        /*Pattern emailRes = Pattern.compile("^1[3,4,5,7,8][0-9]{9}$");*/
        String mobile = "^(13[0-9]|14[5,7]|15[^4]|17[0,3,6-8]|18[0-9])[0-9]{8}$";
        Pattern pattern = Pattern.compile(mobile);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

}
