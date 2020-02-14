package com.jiaowu.entity.courseArrangeEvaluateRecord;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学员课后评价记录
 * Created by 李帅雷 on 2017/10/17.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CourseArrangeEvaluateRecord extends BaseEntity {
    private static final long serialVersionUID = -5997892988630853413L;
    //每堂课评价记录ID
    private Long courseArrangeEvaluateId;
    //用户ID
    private Long userId;
    //用户名称
    private String userName;
    //课程ID
    private Long courseId;
    //课程名称
    private String courseName;
    //内容
    private String content;
    //上课时间
    private String courseTime;
}
