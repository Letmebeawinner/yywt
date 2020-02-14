package com.jiaowu.entity.teachingInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;

/**
 * 教学动态
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TeachingInfo extends BaseEntity{
	private static final long serialVersionUID = 2970124316566344608L;
	/**
	 * 类别,2代表教学计划信息,1代表排课信息.
	 */
	private Integer type;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 点击次数
	 */
	private Long clickTimes;
}
