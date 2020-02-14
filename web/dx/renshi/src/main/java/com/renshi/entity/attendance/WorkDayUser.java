package com.renshi.entity.attendance;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Created by xiangdong on 2018/5/30 0030.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WorkDayUser {

    //班型ID
    private Long classTypeId;
    //班型名称
    private String classTypeName;
    //班次ID
    private Long classId;
    //班次名称
    private String className;
    //用户ID
    private Long userId;
    //用户名称
    private String userName;
    //员工编号
    private String perId;
    //一卡通编号
    private String timeCardNo;
    //上午出勤状态，1代表正常，2代表迟到，3代表早退，4代表旷工，5代表加班。
    private Integer morningAttendanceStatus;
    //下午出勤状态，1代表正常，2代表迟到，3代表早退，4代表旷工，5代表加班。
    private Integer afternoonAttendanceStatus;
    //日期
    private String workDate;

    private Date startTime;

    private Date endTime;
    private  String groupName;
}
