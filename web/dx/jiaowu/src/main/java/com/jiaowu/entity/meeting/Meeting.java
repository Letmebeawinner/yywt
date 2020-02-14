package com.jiaowu.entity.meeting;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

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
	/**
	 * 移交时间
	 */
	private Date turnTime;
	/**
	 * 投入使用时间
	 */
	private Date useTime;
	/**
	 * 可容纳人数
	 */
	private Long peopleNo;
}
