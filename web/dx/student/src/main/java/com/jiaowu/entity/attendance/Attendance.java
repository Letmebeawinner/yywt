package com.jiaowu.entity.attendance;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;

/**
 * 考勤
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Attendance extends BaseEntity{
	private static final long serialVersionUID = -5670362569473979351L;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户类型,1代表管理员2,代表教职工,3代表学员.
	 */
	private Integer userType;
	/**
	 * 排课表ID
	 */
	private Long courseArrangeId;
	/**
	 * 是否出勤,1代表出勤,0代表缺勤.
	 */
	private Integer attend;
	/**
	 * 缺勤类型,1代表公假,2代表事假,3代表病假.
	 */
	private Integer absenceType;
}
