package com.oa.entity.workflow;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 已结束流程详细信息实体
 *
 * @author lzh
 * @create 2017-03-21-16:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HisProcessEntity {
    private String activitiName;//活动名
    private Date acitivitiStartTime;//活动开始时间
    private Date acitivitiEndTime;//活动结束时间
    private String assignee;//办理人
    private String option;//审批意见
    private String applyId;//申请人id；
}
