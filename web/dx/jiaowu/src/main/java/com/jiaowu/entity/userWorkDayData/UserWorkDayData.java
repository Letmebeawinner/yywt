package com.jiaowu.entity.userWorkDayData;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    //上午出勤状态，1代表正常，2代表迟到，3代表早退，4代表旷工。
    private Integer morningAttendanceStatus;
    //下午出勤状态，1代表正常，2代表迟到，3代表早退，4代表旷工。
    private Integer afternoonAttendanceStatus;
    //日期
    private String workDate;
    //流水号
    private String cId;
    //说明
    private String cMemo;
    private String groupName;

    public UserWorkDayData(){

    }

    public UserWorkDayData(Map<String,Object> workDayData){
        if(workDayData.get("userId")!=null){
            this.userId=Long.parseLong(workDayData.get("userId").toString());
            this.classTypeId=Long.parseLong(workDayData.get("classTypeId").toString());
            this.classId=Long.parseLong(workDayData.get("classId").toString());
            this.userName=workDayData.get("userName").toString();
            this.classTypeName=workDayData.get("classTypeName").toString();
            this.className=workDayData.get("className").toString();
        }
        this.perId=workDayData.get("basePerID").toString();
        this.workDate=workDayData.get("workDate").toString();
        this.cId=workDayData.get("cID").toString();
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
            }else{
                this.afternoonAttendanceStatus=1;
            }
        }else{
            this.afternoonAttendanceStatus=1;
        }
    }
}
