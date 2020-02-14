package com.oa.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lzh
 * @create 2017-03-16-15:56
 */
public class NumberUtils {

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
