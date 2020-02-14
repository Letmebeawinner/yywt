package com.renshi.entity.attendance;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会议考勤
 * Created by 268 on 2017/1/3.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryWorkAttendance extends WorkAttendance {

    private static final long serialVersionUID = 5427635130405507165L;
    private String employeeName;  //教职工名称
    public QueryWorkAttendance(){}
    public QueryWorkAttendance(WorkAttendance workAttendance){
        this.setId(workAttendance.getId());
        this.setEmployeeId(workAttendance.getEmployeeId());
        this.setWorkDate(workAttendance.getWorkDate());
        this.setSignInTime(workAttendance.getSignInTime());
        this.setWorkStatus(workAttendance.getWorkStatus());
        this.setWorkStatusType(workAttendance.getWorkStatusType());
        this.setDay(workAttendance.getDay());
        this.setHours(workAttendance.getHours());
        this.setMin(workAttendance.getMin());
        this.setRemark(workAttendance.getRemark());
    }
}
