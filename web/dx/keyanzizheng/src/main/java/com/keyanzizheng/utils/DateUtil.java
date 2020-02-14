package com.keyanzizheng.utils;

import java.util.Calendar;

/**
 * 时间工具类
 *
 * @author YaoZhen
 * @date 01-16, 15:29, 2018.
 */
public class DateUtil {

    /**
     * 获取年份
     *
     * @return 系统年份
     */
    public static String getSysYear() {
        Calendar date = Calendar.getInstance();
        return String.valueOf(date.get(Calendar.YEAR));
    }
}
