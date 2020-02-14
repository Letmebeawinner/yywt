package com.jiaowu.entity.praise;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.entity.reply.Reply;

/**
 * 对回复赞的实体
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Praise extends BaseEntity{
	private static final long serialVersionUID = -5937648169769052344L;
	//回复ID
	private Long replyId;
	//用户ID
	private Long userId;
	//创建人的类型,1代表学员.2代表管理员.
	private Integer type;
	//不同模块的区分,1代表交流空间,2代表活动.
	private Integer belong;
}
