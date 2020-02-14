package com.jiaowu.entity.course;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.entity.classes.ClassType;

/**
 * 课程
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Course extends BaseEntity{
	private static final long serialVersionUID = -4609423646027940432L;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 课程类别ID
	 */
	private Long courseTypeId;
	/**
	 * 课程类别名称
	 */
	private String courseTypeName;
	/**
	 * 学时
	 */
	private Float hour;
	/**
	 * 教师ID字符串窜，用,分割
	 */
	private String teacherId;
	/**
	 * 教师名称,根据教师ID从数据库查出
	 */
	private String teacherName;
	/**
	 * 备注
	 */
	private String note;
}
