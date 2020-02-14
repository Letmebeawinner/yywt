package com.renshi.entity.attendance;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 上班考勤统计
 * Created by 268 on 2017/1/3.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WorkStatistics extends BaseEntity {
    private String  workDate;  //日期
    private Integer lateCount; //迟到人数
    private Integer earlyCount; //早退人数
    private Integer absentCount; //旷工人数
    private Integer leaveCount; //请假人数
    private Integer wkCount; //出勤人数
    private Integer overCount; //加班人数
}
