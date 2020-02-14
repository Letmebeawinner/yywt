package com.oa.entity;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 审批流程base实体
 *
 * @author lzh
 * @create 2017-10-27 10:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseAuditEntity extends BaseEntity{
    private String processInstanceId;//流程id
    private Long applyId;//申请人id
    private Integer audit; //审核状态 0 审核中，1已通过，2已拒绝
    private String reason; //申请理由
    private Date startTime;//开始时间
    private Date endTime; //结束时间
    private Date realityStartTime;//实际开始时间
    private Date realityEndTime;//实际结束时间
    private Date applyTime;//申请时间
}
