package com.jiaowu.entity.information;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.entity.exam.ExamComment;

/**
 * 消息
 * @author 李帅雷
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Information extends BaseEntity{
	private static final long serialVersionUID = -8254476765058556302L;
	/**
	 * 发送人ID
	 */
	private Long fromPeopleId;
	/**
	 * 接收人ID
	 */
	private Long toPeopleId;
	/**
	 * 类型(office_to_teacher代表教务处对教职工，teacher_to_student代表教师对学员,student_to_student代表学员对学员)
	 */
	private String type;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String note;
}
