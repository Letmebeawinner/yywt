package com.jiaowu.entity.knowledge;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.entity.course.Course;

/**
 * 心得
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Knowledge extends BaseEntity{
	private static final long serialVersionUID = -1498241295126790229L;
	/**
	 * 学员ID
	 */
	private Long studentId;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 类型train代表培训心得，meeting代表会议心得
	 */
	private String type;
	/**
	 * 会议ID
	 */
	private Long meetingId;
	/**
	 * 备注
	 */
	private String note;
}
