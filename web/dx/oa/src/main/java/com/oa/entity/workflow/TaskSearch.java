package com.oa.entity.workflow;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 任务查询条件等
 *
 * @author lzh
 * @create 2017-11-29 18:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskSearch{
    //流程定义key
    private String processDefKey;
    //流程定义名
    private String processDefName;
    //申请人
    private String applyName;
    //开始时间
    private Date auditStartTime;
    //结束时间
    private Date auditEndTime;
    //申请开始时间
    private Date applyStartTime;
    //申请结束时间
    private Date applyEndTime;
}
