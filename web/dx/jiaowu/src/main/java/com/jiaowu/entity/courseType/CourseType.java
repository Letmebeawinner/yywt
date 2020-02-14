/**
 * 
 */
package com.jiaowu.entity.courseType;

import com.a_268.base.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程类别
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CourseType extends BaseEntity{
	private static final long serialVersionUID = 4102440169705341891L;
	/**
	 * 课程类别名称
	 */
	private String name;
}
