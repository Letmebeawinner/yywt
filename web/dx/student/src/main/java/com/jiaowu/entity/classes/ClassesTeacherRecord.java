package com.jiaowu.entity.classes;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 班级学生
 *
 * @author 李帅雷
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ClassesTeacherRecord extends BaseEntity {
    private static final long serialVersionUID = -6164067928667665670L;
    /**
     * 班主任ID
     */
    private Long teacherId;
    /**
     * 班主任姓名
     */
    private String teacherName;
    /**
     * 班次ID
     */
    private Long classesId;
    /**
     * 班次名称
     */
    private String classesName;
    /**
     * 类型 1班主任 2副班主任
     */
    private Integer type;

    /**
     * 开班时间
     */

    private Date startTime;

    /**
     * 结束时间
     **/
    private Date endTime;
}
