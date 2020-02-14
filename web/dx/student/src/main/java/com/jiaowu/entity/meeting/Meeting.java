package com.jiaowu.entity.meeting;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.entity.material.MaterialAnalysis;

/**
 * 会议
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Meeting extends BaseEntity{
	private static final long serialVersionUID = 5998418696964306299L;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 备注
	 */
	private String note;
}
