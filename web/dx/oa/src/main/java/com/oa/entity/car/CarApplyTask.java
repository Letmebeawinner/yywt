package com.oa.entity.car;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用车申请任务实体
 *
 * @author lzh
 * @create 2017-01-20-16:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CarApplyTask {
    private String processInstanceId;//流程id
    private Long userId;//申请人id
    private String reason;//申请原因
    private Integer peopleNumber;//用车人数
    private String startAddress;//出车地址
    private String endAddress;//结束地址
    private Date applyTime;//申请时间
    private Date startTime;//开始时间
    private Date endTime;//结束时间
    private Date realityStartTime;//实际开始时间
    private Date realityEndTime;//实际结束时间
    private String userName;//用户名
    private String taskId;//任务id
    private String taskName;//任务名
    private String processDefId;//流程定义id
}
