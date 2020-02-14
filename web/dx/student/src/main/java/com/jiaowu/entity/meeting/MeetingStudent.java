package com.jiaowu.entity.meeting;

import com.a_268.base.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会议学员中间实体类
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MeetingStudent extends BaseEntity{
	private static final long serialVersionUID = 6195366630961311079L;
	/**
	 * 会议ID
	 */
	private Long meetingId;
	/**
	 * 学员ID
	 */
	private Long studentId;
}
