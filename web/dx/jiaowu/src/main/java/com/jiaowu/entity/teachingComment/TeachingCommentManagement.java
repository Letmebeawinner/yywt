package com.jiaowu.entity.teachingComment;

import java.util.Date;

import com.a_268.base.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教学工作评价管理
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TeachingCommentManagement extends BaseEntity{
	private static final long serialVersionUID = 6274572405210236285L;
	/**
	 * 教学评价ID
	 */
	private Long teachingCommentId;
	/**
	 * 课程ID
	 */
	private Long courseId;
	/**
	 * 课程名称
	 */
	private String courseName;
	/**
	 * 评价人ID
	 */
	private Long fromPeopleId;
	/**
	 * 评价人姓名
	 */
	private String fromPeopleName;
	/**
	 * 被评价人ID
	 */
	private Long toPeopleId;
	/**
	 * 被评价人姓名
	 */
	private String toPeopleName;
	/**
	 * 类型(student_to_teacher代表学生对老师 teacher_to_teacher代表同行对教师 leader_to_teacher代表领导对教师)
	 */
	private String type;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 备注
	 */
//	private String note;
	
	private Date startTime;
	
	private Date endTime;
	
	private boolean overdue;
}
