package com.tongji.biz.common;

import com.a_268.base.core.Pagination;

import java.util.Date;
import java.util.Map;

/**
 * @author lizhenhui
 * @create 2017-02-23 15:27
 */
public interface HqHessianService {

    /**
     * 年度收支统计之宿舍收入统计
     *
     * @param year 年份
     * @return 年度宿舍收入
     * @since 2017-03-03
     */
    Double getAnnualDormEarning(String year);

    /**
     * 年度收支统计之能源支出统计
     *
     * @param year 年份
     * @return 年度能源支出
     * @since 2017-03-03
     */
    Double getAnnualEnergyExpense(String year);

    /**
     * 获取用水量统计根据用户id分组
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    Map<String, Object> getWaterStatisticsGroupByUserId(Date fromDate, Date toDate, Pagination pagination);

    /**
     * 用水总量，所有的记录
     *
     * @param start
     * @param end
     * @param pagination
     * @return
     */

    Map<String, Object> getWaterStatistics(Date start, Date end, Pagination pagination);

    /**
     * 获取用电量统计根据用户id分组
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    Map<String, Object> getElectricityStatisticsGroupByUserId(Date fromDate, Date toDate, Pagination pagination);

    /**
     * 用电总量，所有的用户记录
     *
     * @param start
     * @param end
     * @param pagination
     * @return
     */
    Map<String, Object> getElectricityStatistics(Date start, Date end, Pagination pagination);
}