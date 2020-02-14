package com.jiaowu.entity.twentyoneEvaluate;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 李帅雷 on 2017/8/29.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TwentyoneEvaluate extends BaseEntity {
    private static final long serialVersionUID = -5668191563915761322L;
    //班次ID
    private Long classId;
    //用户ID
    private Long userId;
    //用户类型 1学生 2老师
    private Integer userType;
    //用户名称
    private String userName;
    //需求调查的评价
    private Integer index1;
    //课程设置的评价
    private Integer index2;
    //教学活动安排的评价
    private Integer index3;
    //时间长短和进度的评价
    private Integer index4;
    //师资安排的评价
    private Integer index5;
    //方案创造性的评价
    private Integer index6;
    //内容针对性的评价
    private Integer index7;
    //学习资料质量的评价
    private Integer index8;
    //教师教学水平和效果的评价
    private Integer index9;
    //方式方法的评价
    private Integer index10;
    //教学组织、管理的评价
    private Integer index11;
    //运用现代教学手段的评价
    private Integer index12;
    //教学设施设备的评价
    private Integer index13;
    //食宿等卫生情况的评价
    private Integer index14;
    //学习环境、卫生状况的评价
    private Integer index15;
    //培训学风、纪律的评价
    private Integer index16;
    //实现培训目标程度的评价
    private Integer index17;
    //提高政策理论水平的评价
    private Integer index18;
    //提高工作能力和创新能力的评价
    private Integer index19;
    //培训重要性的评价
    private Integer index20;
    //总体满意度的评价
    private Integer index21;
    //收获
    private String gain;
    //建议
    private String advice;
}
