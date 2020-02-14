package com.jiaowu.entity.teach;

import com.a_268.base.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教学计划课程
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TeachingProgramCourse extends BaseEntity{
	private static final long serialVersionUID = 6885489156075553910L;
	/**
	 * 课程ID
	 */
	private Long courseId;
	/**
	 * 课程名称
	 */
	private String courseName;
	/**
	 * 班次ID
	 */
	private Long classId;
	/**
	 * 班次名称
	 */
	private String className;
	/**
	 * 教师ID
	 */
	private Long teacherId;
	/**
	 * 教师名称
	 */
	private String teacherName;
	/**
	 * 教室ID
	 */
	private Long classroomId;
	/**
	 * 教室名称
	 */
	private String classroomName;
	/**
	 * 总学时
	 */
	private Float totalHour;
	/**
	 * 该班次已学的学时
	 */
	private Float learnedHour;
	/**
	 * 该教学计划课程所属班次是否已确认教学计划
	 */
	private Integer confirmTeachingProgram;
	/**
	 * 备注
	 */
	private String note;
}
