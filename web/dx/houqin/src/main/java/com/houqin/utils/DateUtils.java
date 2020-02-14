package com.houqin.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期相关工具
 *
 * @author lzh
 * @create 2017-02-23-15:14
 */
public class DateUtils {
    /**
     * @Description: 日期比较
     * @author: lzh
     * @Param: [water, fromDate, toDate]
     * @Return: boolean
     * @Date: 15:09
     */
    public static boolean dateCompare(Timestamp timestamp, Date fromDate, Date toDate) {
        if (fromDate == null && toDate == null) {
            return true;
        } else if (fromDate == null && toDate != null) {
            return timestamp.getTime() <= toDate.getTime();
        } else if (fromDate != null && toDate == null) {
            return timestamp.getTime() >= fromDate.getTime();
        } else {
            return timestamp.getTime() >= fromDate.getTime()
                    && timestamp.getTime() <= toDate.getTime();
        }
    }

    public static String getPrevMonth(String dates) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date selected = format.parse(dates);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selected);
        calendar.add(Calendar.MONTH, -1);

        int realMonth = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return year + "-" + String.format("%02d", realMonth);
    }
}
