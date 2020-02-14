package com.jiaowu.entity.advice;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 建议
 * Created by 李帅雷 on 2017/8/25.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Advice extends BaseEntity {

    private static final long serialVersionUID = -5503021898101185556L;
    //活动ID
    private Long activityId;
    //活动标题
    private String activityTitle;
    //创建人的ID
    private Long createUserId;
    //创建人的名称
    private String createUserName;
    //创建人的类型,3代表学员.2代表教师,1代表领导.
    private Integer type;
    //内容
    private String content;
    //班次ID
    private Long classId;
    //是否回复,1代表已回复,0代表未回复.
    private Integer hasReply;
}
