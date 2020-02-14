package com.jiaowu.entity.teach;

import com.a_268.base.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教学任务
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TeachingTask extends BaseEntity{
	private static final long serialVersionUID = -8371527110308553577L;
	/**
	 * 班型ID
	 */
	private Long classTypeId;
	/**
	 * 班型名称
	 */
	private String classTypeName;
	/**
	 * 班次ID
	 */
	private Long classId;
	/**
	 * 班次名称
	 */
	private String className;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String note;
}
