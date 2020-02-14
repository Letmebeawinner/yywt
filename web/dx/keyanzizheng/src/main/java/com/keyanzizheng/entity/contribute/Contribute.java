package com.keyanzizheng.entity.contribute;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 投稿
 * Created by tiger on 2016/12/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Contribute extends BaseEntity {

    private static final long serialVersionUID = -2527797606411769366L;
    private Long employeeId; //教职工id
    private String name; //稿件名称
    private String formUrl; //审批表上传地址
    private Integer ifPass; //是否通过审批
}
