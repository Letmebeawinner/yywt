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
public class ResearchReportManage extends BaseEntity{
	private static final long serialVersionUID = 9108313477983681359L;
	/**
	 * 调研报告ID
	 */
	private Long researchReportId;
	/**
	 * 填写人ID
	 */
	private Long peopleId;
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
}
