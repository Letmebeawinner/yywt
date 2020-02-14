package com.oa.entity.leave;

import com.oa.entity.BaseAuditEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 请假流程
 *
 * @author lzh
 * @create 2017-12-28-15:11
 */
@Data
public class OaLeave extends BaseAuditEntity{
    private String workTime; //参加工作时间
    private Integer leaveType; //请假种类  0公务假   1事假 2 婚假 3 病假 4 产假 5 丧假 6 探亲假
    private String position;//职务
    private String departmentOption;//部门领导意见
    private String schoolOption;//分管校领导意见
    private String schoolLeaderOption;//常务副校长意见
    private String leaveRecordSituation;//请假备案情况
    private String cancelLeaveRecordSituation;//销假备案情况
    private Float leaveDays; //请假天数
    private Date departAuditTime; //部门领导签字日期
    private Date schoolAuditTime; //分管校领导签字日期
    private Date schoolLeaderAuditTime; //常务副校长签字日期
    private Date leaveRecordTime; //请假备案日期
    private Date cancelLeaveRecordTime; //销假备案日期
    private String applySign; //请假人签名
    private String leaveRecordSign; //请假备案人签名
    private String cancelLeaveRecordSign; //销假备案人签名
}
