package com.jiaowu.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 时间
 *
 * @author YaoZhen
 * @date 04-03, 11:34, 2018.
 */
@Slf4j
public class DateUtil {

    /**
     * 学员的到期时间加一天才是删除wifi用户的时间
     * 设置时区为东八区 转不转都一样, 因为Unix时间不受时区影响
     * 但日历Calendar会受到时区影响,虽然不影响最终返回值
     */
    public static long getOneMoreDay(Timestamp timestamp) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(timestamp);
        calendar.add(Calendar.DATE,1);

        long rs = calendar.getTime().getTime();
        log.info(String.valueOf(rs));
        return rs;
    }

}
