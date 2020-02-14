package com.jiaowu.entity.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 李帅雷 on 2017/10/18.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserCondition implements Serializable{
    private static final long serialVersionUID = 2522344643044165989L;
    //党校一卡通员工编号
    private String perId;
    //班型ID
    private Long classTypeId;
    /*//班型名称
    private String classTypeName;*/
    //班次ID
    private Long classId;
    /*//班次名称
    private String className;*/
    //学员名称
    private String userName;
    //上午出勤状态，1代表正常，2代表迟到，3代表早退，4代表旷工。
    private Integer morningAttendanceStatus;
    //下午出勤状态，1代表正常，2代表迟到，3代表早退，4代表旷工。
    private Integer afternoonAttendanceStatus;
    //日期
    private String workDate;

    private Date startTime;

    private Date endTime;
}
