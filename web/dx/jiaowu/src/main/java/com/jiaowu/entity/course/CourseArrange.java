package com.jiaowu.entity.course;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
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
	 * 教师ID,只保存了第一个，没有包含全部。全部教师ID为teacherIds
	 */
	private Long teacherId;
	/**
	 * 教师ID字符串，保存了全部教师ID，用,分割
	 */
	private String teacherIds;
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
	//教师备注
	private String teacherNote;

	private Long sum;

	//教研部id
	private Integer teacherResearchId;//teacherResearchDepartment

	//教研部名称
	private String teacherResearchName;

	private int totalNum;

	private String discussId;//讨论室id
	private String discussName;//讨论室名称

}
