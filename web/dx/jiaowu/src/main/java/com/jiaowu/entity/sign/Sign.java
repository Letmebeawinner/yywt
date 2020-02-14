package com.jiaowu.entity.sign;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.entity.meeting.MeetingStudent;

/**
 * 在线报名
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Sign extends BaseEntity{
	private static final long serialVersionUID = 8813126141527902378L;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;
	/**
	 * 单位名称
	 */
	private String companyName;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 科目信息
	 */
	private String info;
}
