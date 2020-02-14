package com.renshi.entity.attendance;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 会议考勤
 * Created by 268 on 2017/1/3.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MeetAttendance extends BaseEntity {

    private static final long serialVersionUID = -4124436534919236755L;
    private Long employeeId;  //教职工id
    private String meetName;  //会议名称
    private String meetPlace;  //会议地点
    private Date  signInTime;  //签到时间
    private Date  signOutTime;  //签退时间
    private String  remark;  //备注
}
