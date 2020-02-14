package com.jiaowu.entity.research;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 调研报告管理
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ResearchReport extends BaseEntity{
	private static final long serialVersionUID = 9108313477983681359L;
	/**
	 * 调研报告类型ID
	 */
	private Long researchId;
	/**
	 * 调研报告类型名称
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
     */
    private Integer storage;
}
