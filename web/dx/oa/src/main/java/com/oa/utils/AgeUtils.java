package com.oa.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AgeUtils {


    // 根据年月日计算年龄,birthTimeString:"1994-11-14"
    public static int getAgeFromBirthTime(String birthTimeString) {
        if (StringUtils.isEmpty(birthTimeString)) {
            return 1;
        }


        // 先截取到字符串中的年、月、日
        String strs[] = birthTimeString.trim().split("-");
        int selectYear = Integer.parseInt(strs[0]);
        int selectMonth = Integer.parseInt(strs[1]);
        int selectDay = 1;
        // 得到当前时间的年、月、日
        Calendar cal = Calendar.getInstance();
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayNow = cal.get(Calendar.DATE);

        // 用当前年月日减去生日年月日
        int yearMinus = yearNow - selectYear;
        int monthMinus = monthNow - selectMonth;
        int dayMinus = dayNow - selectDay;

        int age = yearMinus;// 先大致赋值
        if (yearMinus < 0) {// 选了未来的年份
            age = 0;
        } else if (yearMinus == 0) {// 同年的，要么为1，要么为0
            if (monthMinus < 0) {// 选了未来的月份
                age = 0;
            } else if (monthMinus == 0) {// 同月份的
                if (dayMinus < 0) {// 选了未来的日期
                    age = 0;
                } else if (dayMinus >= 0) {
                    age = 1;
                }
            } else if (monthMinus > 0) {
                age = 1;
            }
        } else if (yearMinus > 0) {
            if (monthMinus < 0) {// 当前月>生日月
            } else if (monthMinus == 0) {// 同月份的，再根据日期计算年龄
                if (dayMinus < 0) {
                } else if (dayMinus >= 0) {
                    age = age + 1;
                }
            } else if (monthMinus > 0) {
                age = age + 1;
            }
        }
        return age - 1;
    }

    // 根据时间戳计算年龄
    public static int getAgeFromBirthTime(long birthTimeLong) {
        Date date = new Date(birthTimeLong * 1000l);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String birthTimeString = format.format(date);
        return getAgeFromBirthTime(birthTimeString);
    }

    public static int getAgeByIDcard(String idNumber){
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
        int year = c.get(Calendar.YEAR);    //获取年
        int len = idNumber.length();
        int userAge = 0;
        //18位身份证
        if (len == 18) {
            String idNumber1 = idNumber.substring(6, 10); //截取身份证的年
            int b = Integer.valueOf(idNumber1).intValue();
            userAge = year - b; //实际年龄
        } else {
            //15位身份证
            String idNumber1 = idNumber.substring(6, 8); //截取身份证的年
            int b = Integer.valueOf("19" + idNumber1).intValue();
            userAge = year - b; //实际年龄
        }
        return userAge;
    }


}
