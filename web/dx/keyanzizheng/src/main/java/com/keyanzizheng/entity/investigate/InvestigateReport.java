package com.keyanzizheng.entity.investigate;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 调研报告
 * Created by tiger on 2016/12/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InvestigateReport extends BaseEntity {

    private static final long serialVersionUID = 3246175817691319126L;
    private Long employeeId; //教职工id
    private Long researchId; //调研方向id
    private String name; //调研报告名称
    private String relatedUrl; //相关资料上传地址
    private String info; //调研相关简介
    private Integer ifPass; //审核状态
}
