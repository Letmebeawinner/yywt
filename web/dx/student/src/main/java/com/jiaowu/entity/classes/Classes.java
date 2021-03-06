package com.jiaowu.entity.classes;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.a_268.base.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 班次表
 *
 * @author 李帅雷
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Classes extends BaseEntity {
    private static final long serialVersionUID = -8055865256659553934L;
    /**
     * 班型ID
     */
    private Long classTypeId;
    /**
     * 班型
     */
    private String classType;
    /**
     * 编号
     */
    private String classNumber;
    /**
     * 名称
     */
    private String name;
    /**
     * 班主任ID
     */
    private Long teacherId;
    /**
     * 班主任名称
     */
    private String teacherName;
    /**
     * 学生总人数
     */
    private Long studentTotalNum;
    /**
     * 学生未报到人数,动态从user表中查.
     */
    private Long studentNotReportNum;
    /**
     * 已报名人数
     */
    private Long studentSignNum;
    /**
     * 是否确认教学计划,1代表确认,0代表不确认.
     */
    private Integer confirmTeachingProgram;
    /**
     * 是否已确定共享课程,1代表确定,0代表不确定,供初始化页面时使用.
     */
    private Integer hasShareCourse;
    /**
     * 开班时间
     */
    @DateTimeFormat(pattern = "yyyy-mm-dd hh-mm-ss")
    private Date startTime;
    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-mm-dd hh-mm-ss")
    private Date endTime;
    /**
     * 备注
     */
    private String note;
    /**
     * 最大人数
     */
    private Long maxNum;
    /**
     * 代表该班次是否已结束,1代表已结束,0代表未结束,默认为0.
     */
    private Integer end;

    /**
     * 报名截止时间
     */
    @DateTimeFormat(pattern = "yyyy-mm-dd hh-mm-ss")
    private Date signEndTime;
    /**
     * 副班主任ID
     */
    private Long deputyTeacherId;
    /**
     * 副班主任名称
     */
    private String deputyTeacherName;
    /**
     * 班主任电话
     */
    private String teacherMobile;
}
