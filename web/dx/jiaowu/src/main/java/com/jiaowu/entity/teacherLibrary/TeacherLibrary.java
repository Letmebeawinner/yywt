package com.jiaowu.entity.teacherLibrary;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 教师专题库
 * Created by MaxWe on 2017/10/18.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TeacherLibrary extends BaseEntity {

    /**
     * 教师ID
     */
    private Long teacherId;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 题库分类
     */
    private Integer classification;

    /**
     * 专题名称
     */
    private String projectName;


}
