package com.jiaowu.entity.adviceReply;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 回复建议
 * Created by 李帅雷 on 2017/8/25.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AdviceReply extends BaseEntity {
    private static final long serialVersionUID = -5351052396859314181L;
    //建议ID
    private Long adviceId;
    //创建人的ID
    private Long createUserId;
    //创建人的名称
    private String createUserName;
    //创建人的类型,3代表学员.2代表教师,1代表领导.
    private Integer type;
    //内容
    private String content;
}
