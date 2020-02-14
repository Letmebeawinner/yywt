package com.jiaowu.entity.course;

import java.util.Date;

import com.a_268.base.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 排课
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CourseArrange extends BaseEntity{
	private static final long serialVersionUID = -2239381283756099840L;
	/**
	 * 教学计划课程ID
	 */
	private Long teachingProgramCourseId;
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
	 * 上课开始时间
	 */
	private Date startTime;
	/**
	 * 上课结束时间
	 */
	private Date endTime;
	/**
	 * 上课开始时间(GMT格式)
	 */
	private String startTimeForJs;
	/**
	 * 上课结束(GMT格式)
	 */
	private String endTimeForJs;
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
	 * 表明是否是其他班级分享的课程,0代表不是1,代表是.
	 */
	private Integer shareCourse;
	/**
	 * 分享课程的排课表ID
	 */
	private Long shareCourseArrangeId;
	/**
	 * 如果课程的开始时间小于当前时间的话,则不可编辑.
	 */
	private boolean editable;
	/**
	 * 备注
	 */
	private String note;


	private boolean flag = false;

}
