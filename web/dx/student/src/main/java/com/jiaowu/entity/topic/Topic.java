package com.jiaowu.entity.topic;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;

/**
 * 话题
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Topic extends BaseEntity{
	private static final long serialVersionUID = -3955342105501282914L;
	//标题
	private String title;
	//内容
	private String content;
	//创建人的ID
	private Long createUserId;
	//创建人的名称
	private String createUserName;
	//创建人的类型,3代表学员.2代表教师,1代表领导.
	private Integer type;
	//回复数
	private Long replyNum;
	//点赞数
	private Long praiseNum;
	//查看数
	private Long viewNum;
}
