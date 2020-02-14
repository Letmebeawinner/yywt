package com.renshi.entity.attendance;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 上班考勤
 * Created by 268 on 2017/1/3.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WorkAttendance extends BaseEntity {

    private static final long serialVersionUID = -7218206496447523499L;
    private String employeeId;  //教职工编号
    private String  workDate;  //日期
    private String  signInTime;  //签到时间
    private String  signOutTime;  //签退时间
    private Integer workStatus; //上班状态 0 正常1缺勤  2加班
    /*缺勤 ：1迟到 2早退  3旷工 4请假  5出勤  6节假日
    * 加班：1平时加班 2 周末加班 3 节假日加班
    * */
    private Integer workStatusType;
    private String day; //天
    private String hours; //小时
    private String min; //分钟
    private String  remark;  //备注
}
