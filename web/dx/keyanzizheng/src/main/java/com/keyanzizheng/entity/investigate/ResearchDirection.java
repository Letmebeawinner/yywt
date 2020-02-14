package com.keyanzizheng.entity.investigate;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 调研方向
 * Created by tiger on 2016/12/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResearchDirection  extends BaseEntity {
    private static final long serialVersionUID = -7487411164627113572L;
    private Long sysUserId; //申请人id
    private String departmentName; //部门id
    private String name; //调研方向名称
    private String info; //相关简介
    private Integer ifReport; //提交报告状态 1:否 3：是

}
