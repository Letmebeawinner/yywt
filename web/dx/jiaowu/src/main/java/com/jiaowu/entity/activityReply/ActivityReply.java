package com.jiaowu.entity.activityReply;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 活动回复
 * @author Administrator
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ActivityReply extends BaseEntity {

    private static final long serialVersionUID = -1355407458945105491L;
    //活动ID
    private Long activityId;
    //活动名称
    private String activityTitle;
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
