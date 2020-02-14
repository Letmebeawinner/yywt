package com.keyanzizheng.entity.research;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 班级调研报告管理
 *
 * @author 268
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResearchReport extends BaseEntity {
    private static final long serialVersionUID = 9108313477983681359L;
    /**
     * 调研报告类别ID
     */
    private Long researchId;
    /**
     * 调研报告类别名称
     */
    private String researchName;
    /**
     * 填写人ID
     */
    private Long peopleId;
    /**
     * 填写人名称
     */
    private String peopleName;
    /**
     * 类型teacher代表老师，student代表学员
     */
    private String type;
    /**
     * 内容
     */
    private String content;
    /**
     * 备注
     */
    private String note;

    /**
     * 附件地址
     */
    private String fileUrl;

    /**
     * 是否审核
     */
    private Integer audit;

    /**
     * 班次名称
     */
    private String className;

    /**
     * 手机号吗
     */
    private String mobile;

    /**
     * 班型
     */
    private String classType;

    /**
     * 班级
     */
    private String classStr;

    /**
     * 调研组
     */
    private String agroup;

    /**
     * 指导老师
     */
    private String teacher;

    /**
     * 第一执笔人
     */
    private String zbr;

    /**
     * 课题参与人员
     */
    private String participant;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 等级
     */
    private Integer assessmentLevel;

    /**
     * 审批部门
     */
    private Integer approvalDepartment;

    /**
     * 入库
     *
     * @see com.keyanzizheng.constant.StatusConstants
     */
    private Integer storage;

    /**
     * 是否归档
     */
    private Integer archive;

    /**
     * 归档到OA的档案id
     */
    private Long oaArchiveId;

    /**
     * 初稿查重率
     */
    private String firstDraft;

    /**
     * 修改稿查重率
     */
    private String modifiedDraft;
}
