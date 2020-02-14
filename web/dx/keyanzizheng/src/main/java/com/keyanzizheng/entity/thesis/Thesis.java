package com.keyanzizheng.entity.thesis;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 成果形式 -> 论文
 * <p>增加冗余字段,避免联合查询<p>
 *
 * @author YaoZhen
 * @date 12-21, 15:50, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Thesis extends BaseEntity {

    /**
     * 论文名称
     */
    private String name;

    /**
     * 所属处室id
     */
    private Long subsectionId;

    /**
     * 所属处室name
     */
    private String subsectionName;

    /**
     * 是否公开 <br>
     * 1: 是 <br>
     * 0 :否
     */
    private Integer whetherOpen;

    /**
     * 类型id
     */
    private Long categoryId;

    /**
     * 类型name
     */
    private String categoryName;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 发表刊物
     */
    private Integer publication;

    /**
     * 刊号
     */
    private Integer issueNo;

    /**
     * 发表时间
     */
    private Date issuingTime;

    /**
     * 参编组成员
     */
    private String groupMembers;

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 字数
     */
    private String wordCount;

    /**
     * 申请书
     */
    private String applicationUrl;

    /**
     * 备注
     */
    private String remarks;
}
