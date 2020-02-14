package com.jiaowu.entity.classes;

import com.a_268.base.core.BaseEntity;

import com.jiaowu.annotation.Cut;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 班型
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ClassType extends BaseEntity{
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 备注
	 */
	private String note;

	@Cut
	private  int num;
}
