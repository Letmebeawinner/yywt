package com.renshi.entity.job;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 职位
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Job extends BaseEntity {

    private Long jobOrderId;//序列id
    private String name;//岗位名称
    private String number;//
    private String departmentName;
    private Long manageNumber;
    private String leader;
    private Long thisJobNumber;
    private String junior;
    private Date analyzeTime;
    private String description;//岗位职责
}
