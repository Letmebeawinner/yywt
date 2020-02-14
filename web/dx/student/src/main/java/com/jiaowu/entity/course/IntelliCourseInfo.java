package com.jiaowu.entity.course;

import java.util.Date;

import com.a_268.base.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 智能排课用
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class IntelliCourseInfo extends BaseEntity{
	private static final long serialVersionUID = -7471595697203784834L;
	/**
	 * 教学计划课程ID
	 */
	private Long teachingProgramCourseId;
	/**
	 * 课程ID
	 */
	private Long courseId;
	/**
	 * 课程持续时间
	 */
	private Float duration;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
}
