package com.jiaowu.entity.starEvaluate;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 星级评价
 * Created by 李帅雷 on 2017/9/7.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class StarEvaluate extends BaseEntity {

    private static final long serialVersionUID = -1686390333279677363L;
    //班次ID
    private Long classId;
    //班次名称
    private String className;
    //用户ID
    private Long userId;
    //用户名称
    private String userName;
    //分数
    private Integer score;


}
