package com.renshi.entity.workdaydata;

import lombok.Data;

/**
 * 考勤记录
 *
 * @author YaoZhen
 * @date 04-04, 16:26, 2018.
 */
@Data
public class WorkDayDataNode {

    /**
     * 打卡日期
     */
    private String workDate;

    /**
     * 打卡记录
     */
    private String memo;
}
