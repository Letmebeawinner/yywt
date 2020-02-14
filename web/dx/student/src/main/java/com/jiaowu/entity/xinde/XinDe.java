package com.jiaowu.entity.xinde;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.entity.course.Course;

/**
 * 心得
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class XinDe extends BaseEntity{
	private static final long serialVersionUID = -1498241295126790229L;
	/**
	 * 班次ID
	 */
	private Long classId;
	/**
	 * 学员ID
	 */
	private Long studentId;
	/**
	 * 学员名称
	 */
	private String studentName;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 类型train代表培训心得，meeting代表会议心得
	 */
	private String type;
	/**
	 * 会议ID
	 */
	private Long meetingId;
	/**
	 * 会议名称
	 */
	private String meetingName;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 附件地址
	 */
	private String fileUrl;
}
