package com.renshi.entity.job;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class JobLevel extends BaseEntity {
    //岗位名称
    private String name;
    //岗位排序
    private Long jobOrderId;
    //岗位级别
    private String jobOrderName;

}
