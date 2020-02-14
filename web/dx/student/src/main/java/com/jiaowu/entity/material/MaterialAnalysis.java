package com.jiaowu.entity.material;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;

/**
 * 党性材料分析
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MaterialAnalysis extends BaseEntity{
	private static final long serialVersionUID = 1917773807445273609L;
	/**
	 * 学员ID
	 */
	private Long studentId;
	/**
	 * 学员名称
	 */
	private String studentName;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 类型(train代表培训心得，meeting代表会议心得)
	 */
	private String type;
	/**
	 * 会议ID
	 */
	private Long meetingId;
	/**
	 * 会议名称
	 */
	private String meetingName;
	/**
	 * 备注
	 */
	private String note;
}
