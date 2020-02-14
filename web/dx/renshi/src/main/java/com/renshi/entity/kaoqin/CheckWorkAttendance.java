package com.renshi.entity.kaoqin;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * oa考勤记录
 *
 * @param
 * @author: xiangdong.chang
 * @create: 2018/5/16 0016 15:14
 * @return:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CheckWorkAttendance extends BaseEntity {
    private Long userId;//用户id
    private String userName;//用户名
    //上午：出勤用“1”表示，迟到、早退用“2”表示，旷工用“3”表示，病假用“4”表示，事假用“5”表示，婚假、产假、丧假和探亲假用“6”表示，法定节假日、法定休息日用“7”表示，加班日用“8”，调休用“9”
    private Integer amAttendanceStatus;
    //下午：出勤用“1”表示，迟到、早退用“2”表示，旷工用“3”表示，病假用“4”表示，事假用“5”表示，婚假、产假、丧假和探亲假用“6”表示，法定节假日、法定休息日用“7”表示，加班日用“8”，调休用“9”
    private Integer pmAttendanceStatus;
    //添加时间
    private String addTime;
    //部门id
    private Long departmentId;
    //考勤人姓名
    private String attendanceName;
    //部门名称
    private String departmentName;
    private Long roleId;

    private Integer amOutTime;//上午全勤
    private Integer amRetreat;//上午迟到早退
    private Integer amLeave;//上午缺勤
    private Integer amAbsenteeism;//上午加班

    private Integer pmOutTime;//下午全勤
    private Integer pmRetreat;//下午迟到早退
    private Integer pmLeave;//下午缺勤
    private Integer pmAbsenteeism;//下午加班 


}

