package com.jiaowu.entity.activity;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 李帅雷 on 2017/8/24.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Activity extends BaseEntity {
    private static final long serialVersionUID = -8607177418315214622L;
    //标题
    private String title;
    //内容
    private String content;
    //创建人的ID
    private Long createUserId;
    //创建人的名称
    private String createUserName;
    //创建人类型,1代表管理员2,代表教师
    private Integer type;
    //回复数
    private Long replyNum;
    //点赞数
    private Long praiseNum;
    //查看数
    private Long viewNum;
    //备注
    private String note;
}
