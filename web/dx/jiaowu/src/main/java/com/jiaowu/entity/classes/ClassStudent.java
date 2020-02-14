package com.jiaowu.entity.classes;

import com.a_268.base.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 班级学生
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ClassStudent extends BaseEntity{
	private static final long serialVersionUID = -6164067928667665670L;
	/**
	 * 班次ID
	 */
	private Long classId;
	/**
	 * 学员ID
	 */
	private Long studentId;
	/**
	 * 学员序号
	 */
	private String serialNumber;
}
