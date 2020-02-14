package com.keyanzizheng.entity.submission;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生态所投稿
 *
 * @author YaoZhen
 * @date 01-08, 11:08, 2018.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Submission extends BaseEntity {

    /**
     * 投稿名称
     */
    private String name;

    /**
     * 类型ID
     */
    private Integer typeId;

    /**
     * 类型Name
     */
    private String typeName;

    /**
     * 申请表
     */
    private String url;

    /**
     * 审批状态
     * 0:未审批 1:已通过审批 2:已拒绝审批
     */
    private Integer audit;

    /**
     * 申请人Id
     */
    private Long applicantId;

    /**
     * 申请人Name
     */
    private String applicantName;

}
