package com.renshi.common;

import java.util.List;
import java.util.Map;

/**
 * 考勤数据Service
 *
 * @author s.li
 * @create 2017-02-24-13:39
 */
public interface WorkSourceService {

    /**
     * 查询考勤记录列表
     * @param sql 查询Sql语句
     * @return 考勤记录列表
     */
    List<Map<String,String>> queryWorkSourceList(String sql);

    /**
     * 查询每日打卡记录列表
     * @param sql 查询Sql语句
     * @return 每日打卡记录列表
     */
    List<Map<String,String>> queryWorkDayDataList(String sql);

    /**
     * 查询考勤明细与打卡记录结合列表
     * @param sql 查询Sql语句
     * @return 每日打卡记录列表
     */
    List<Map<String,String>> queryWorkDayDetailList(String sql);

    /**
     * 所有的打卡记录
     */
    String queryAllWordData(String sql, String strStart, String strEnd);
}
