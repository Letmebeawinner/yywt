package com.jiaowu.entity.exam;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.entity.course.CourseArrange;

/**
 * 考试评价
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ExamComment extends BaseEntity{
	private static final long serialVersionUID = 2671321349669766688L;
	/**
	 * 评价人ID
	 */
	private Long fromPeopleId;
	/**
	 * 评价人名称
	 */
	private String fromPeopleName;
	/**
	 * 学员ID
	 */
	private Long studentId;
	/**
	 * 学员名称
	 */
	private String studentName;
	/**
	 * 类型(teacher_to_student代表班主任对学员的考评 leader_to_student代表教员对学员的考评 monitor_to_student代表班长对学员的评价)
	 */
//	private String type;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String note;
}
