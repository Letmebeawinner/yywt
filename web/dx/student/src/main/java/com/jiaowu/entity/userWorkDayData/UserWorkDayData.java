package com.jiaowu.entity.userWorkDayData;

import com.a_268.base.core.BaseEntity;
import com.a_268.base.util.ObjectUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by 李帅雷 on 2017/10/19.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserWorkDayData extends BaseEntity{
    private static final long serialVersionUID = 4106520316978051531L;
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
    //流水号
    private String cId;
    //说明
    private String cMemo;
    //组别
    private String groupName;
    //1学员2讲师
    private Integer type;

    public UserWorkDayData(){

    }

    public UserWorkDayData(Map<String,Object> workDayData){
        if(workDayData.get("userId")!=null){
            if(ObjectUtils.isNotNull(workDayData.get("userId"))) {
                this.userId = Long.parseLong(workDayData.get("userId").toString());
            }if(ObjectUtils.isNotNull((workDayData.get("classTypeId")))){
                this.classTypeId = Long.parseLong(workDayData.get("classTypeId").toString());
            }if(ObjectUtils.isNotNull(workDayData.get("classId"))) {
                this.classId = Long.parseLong(workDayData.get("classId").toString());
            }if(ObjectUtils.isNotNull(workDayData.get("userName"))) {
                this.userName = workDayData.get("userName").toString();
            }if(ObjectUtils.isNotNull(workDayData.get("classTypeName"))) {
                this.classTypeName = workDayData.get("classTypeName").toString();
            }if(ObjectUtils.isNotNull(workDayData.get("className"))) {
                this.className = workDayData.get("className").toString();
            }if(ObjectUtils.isNotNull(workDayData.get("className"))) {
                this.className = workDayData.get("className").toString();
            }
                this.type =Integer.valueOf(workDayData.get("type").toString());
        }
//        this.perId=workDayData.get("basePerID").toString();
//        this.workDate=workDayData.get("workDate").toString();
//        this.cId=workDayData.get("cID").toString();
        if(workDayData.get("basePerID")!=null){
            this.perId=workDayData.get("basePerID").toString();
        }
        if(workDayData.get("BaseCardNo")!=null){
            this.timeCardNo=workDayData.get("BaseCardNo").toString();
        }
        if(workDayData.get("workDate")!=null){
            this.workDate=workDayData.get("workDate").toString();
        }
        if(workDayData.get("cID")!=null){
            this.cId=workDayData.get("cID").toString();
        }
        if(workDayData.get("cMemo")!=null){
            this.cMemo=workDayData.get("cMemo").toString();
            int morningStart=this.cMemo.indexOf("第一时间段");
            int afternoonStart=this.cMemo.indexOf("第二时间段");
            String morningText=null;
            String afternoonText=null;
            if(morningStart>=0&&afternoonStart>=0){
                morningText=this.cMemo.substring(morningStart+5,afternoonStart);
                afternoonText=this.cMemo.substring(afternoonStart+5,this.cMemo.length());
            }else if(morningStart>=0&&afternoonStart<0){
                morningText=this.cMemo.substring(morningStart+5,this.cMemo.length());
            }else if(morningStart<0&&afternoonStart>=0){
                afternoonText=this.cMemo.substring(afternoonStart+5,this.cMemo.length());
            }else{

            }
            if(morningText!=null&&!morningText.equals("")){
                if(morningText.contains("迟到")){
                    this.morningAttendanceStatus=2;
                }else if(morningText.contains("早退")){
                    this.morningAttendanceStatus=3;
                }else if(morningText.contains("旷工")){
                    this.morningAttendanceStatus=4;
                }else if(morningText.contains("加班")){
                    this.morningAttendanceStatus=5;
                }else{
                    this.morningAttendanceStatus=1;
                }
            }else{
                this.morningAttendanceStatus=1;
            }
            if(afternoonText!=null&&!afternoonText.equals("")){
                if(afternoonText.contains("迟到")){
                    this.afternoonAttendanceStatus=2;
                }else if(afternoonText.contains("早退")){
                    this.afternoonAttendanceStatus=3;
                }else if(afternoonText.contains("旷工")){
                    this.afternoonAttendanceStatus=4;
                }else if(afternoonText.contains("加班")){
                    this.afternoonAttendanceStatus=5;
                }else{
                    this.afternoonAttendanceStatus=1;
                }
            }else{
                this.afternoonAttendanceStatus=1;
            }
        }
    }
}
