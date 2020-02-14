package com.jiaowu.entity.reply;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.entity.topic.Topic;

/**
 * 话题回复
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Reply extends BaseEntity{
	private static final long serialVersionUID = -8537049366681742921L;
	//话题ID
	private Long topicId;
	//话题名称
	private String topicTitle;
	//用户ID
	private Long userId;
	//用户名称
	private String userName;
	//创建人的类型,1代表领导,2代表教师,3代表学员.
	private Integer type;
	//对某条回复的回复
	private Long replyId;
	//回复内容
	private String content;
	//点赞数
	private Long praiseNum=0L;
	//回复数
	private Long replyNum=0L;
}
