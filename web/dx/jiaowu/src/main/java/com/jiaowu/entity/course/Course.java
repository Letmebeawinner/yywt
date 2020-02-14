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
	 * 教师ID字符串，ID用，分割
	 */
	private String teacherId;
	/**
	 * 根据teacherId从数据库查
	 */
	private String teacherName;
	/**
	 * 备注
	 */
	private String note;

	//课程数量

	private Integer courseNum;
	/*//课时
	private Integer courseArrangeNum;*/
	private String year;//年份
	private String season;//季节
}
