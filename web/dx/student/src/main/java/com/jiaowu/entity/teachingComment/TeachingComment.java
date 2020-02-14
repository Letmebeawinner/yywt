package com.jiaowu.entity.teachingComment;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;

/**
 * 教学工作评价
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TeachingComment extends BaseEntity{
	private static final long serialVersionUID = 7144360048964454004L;
	/**
	 * 课程ID
	 */
	private Long courseId;
	/**
	 * 课程名称
	 */
	private String courseName;
	/**
	 * 教学计划课程ID
	 */
	private Long teachingProgramCourseId;
	/**
	 * 班次ID
	 */
	private Long classId;
	/**
	 * 班次名称
	 */
	private String className;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date EndTime;
	/**
	 * 备注
	 */
//	private String note;
}
