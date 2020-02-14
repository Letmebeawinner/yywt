package com.jiaowu.entity.courseArrangeEvaluate;

import com.a_268.base.core.BaseEntity;
import com.jiaowu.entity.course.CourseArrange;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 每堂课后评价
 * Created by 李帅雷 on 2017/10/16.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CourseArrangeEvaluate extends BaseEntity {
    private static final long serialVersionUID = 8169977093176394646L;
    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    //排课表ID
    private Long courseArrangeId;
    //班次ID
    private Long classId;
    //课程ID
    private Long courseId;
    //课程名称
    private String courseName;
    //上课时间
    private String courseTime;
    //教学计划课程ID
    private Long teachingProgramCourseId;

    public CourseArrangeEvaluate(CourseArrange courseArrange){
        this.courseArrangeId=courseArrange.getId();
        this.classId=courseArrange.getClassId();
        this.courseId=courseArrange.getCourseId();
        this.courseName=courseArrange.getCourseName();
        this.courseTime=sdf.format(courseArrange.getStartTime())+"-"+sdf.format(courseArrange.getEndTime());
        this.teachingProgramCourseId=courseArrange.getTeachingProgramCourseId();
        Date now=new Date();
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        this.setCreateTime(timestamp);
        this.setUpdateTime(timestamp);

    }
    public CourseArrangeEvaluate(){

    }
}
