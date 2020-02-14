package com.jiaowu.entity.teachEvaluate;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 李帅雷 on 2017/8/29.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TeachEvaluate extends BaseEntity {
    private static final long serialVersionUID = 6221651688075940500L;
    //排课记录ID
    private Long courseArrangeId;
    //班次ID
    private Long classId;
    //用户ID
    private Long userId;
    //用户名称
    private String userName;
    //教师ID
    private Long teacherId;
    //课程ID
    private Long courseId;
    //课程名称
    private String courseName;
    //备课认真治学严谨
    private Integer index1;
    //教态端庄为人师表
    private Integer index2;
    //层次分明突出重点
    private Integer index3;
    //联系实际范例恰当
    private Integer index4;
    //语言清晰善用多媒
    private Integer index5;
    //启发教学培养思维
    private Integer index6;
    //教学内容印象深刻
    private Integer index7;
    //个人成长帮助较大
    private Integer index8;
    //总分
    private Integer total;
}
