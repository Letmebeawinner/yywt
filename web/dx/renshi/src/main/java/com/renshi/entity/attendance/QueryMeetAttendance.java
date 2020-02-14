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
public class QueryMeetAttendance extends MeetAttendance {

    private static final long serialVersionUID = 5427635130405507165L;
    private String employeeName;  //教职工名称
    public QueryMeetAttendance(){}
    public QueryMeetAttendance(MeetAttendance meetAttendance){
        this.setId(meetAttendance.getId());
        this.setEmployeeId(meetAttendance.getEmployeeId());
        this.setSignInTime(meetAttendance.getSignInTime());
        this.setSignOutTime(meetAttendance.getSignOutTime());
        this.setRemark(meetAttendance.getRemark());
        this.setMeetName(meetAttendance.getMeetName());
        this.setMeetPlace(meetAttendance.getMeetPlace());
    }
}
