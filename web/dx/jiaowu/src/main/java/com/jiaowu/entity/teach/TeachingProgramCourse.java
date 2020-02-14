package com.jiaowu.entity.teach;

import com.a_268.base.core.BaseEntity;

import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.classroom.Classroom;
import com.jiaowu.entity.course.Course;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

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
	/**
	 * 班次开始时间
	 */
	private Date shiftStartTime;
	/**
	 * 班次结束时间
	 */
	private Date shiftEndTime;

	public TeachingProgramCourse(){

	}

	public TeachingProgramCourse(Classes classes, Classroom classroom, Course course){
		this.classId=classes.getId();
		this.className=classes.getName();
		this.classroomId=classroom.getId();
		this.classroomName=classroom.getPosition();
		this.courseId=course.getId();
		this.courseName=course.getName();
		this.totalHour=course.getHour();
//		this.teacherId=course.getTeacherId();
		this.teacherId=Long.parseLong(course.getTeacherId().split(",")[0]);
		this.teacherName=course.getTeacherName();
	}
}
