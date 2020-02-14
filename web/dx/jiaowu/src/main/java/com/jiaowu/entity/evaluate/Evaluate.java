package com.jiaowu.entity.evaluate;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 心得评价
 *
 * @author YaoZhen
 * @create 10-19, 19:21, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Evaluate extends BaseEntity {
    /**
     * 心得ID
     */
    private Long tipsId;

    /**
     * 评价标题
     */
    private String title;

    /**
     * 评价正文
     */
    private String text;

    /**
     * 学员ID
     */
    private Long studentId;

    /**
     * 学员名称
     */
    private String studentName;

    /**
     * 评价人ID
     */
    private Long evaluatorId;

    /**
     * 评价人名称
     */
    private String evaluatorName;
}
