package com.jiaowu.dao.course;

import com.a_268.base.core.BaseDao;
import com.jiaowu.entity.course.CourseArrange;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CourseArrangeDao extends BaseDao<CourseArrange> {


    public List<CourseArrange> queryCourseArrange(@Param("whereSql") String whereSql);


    public List<CourseArrange> queryCourseInfo(@Param("whereSql") String whereSql, @Param("className") String className, @Param("currentPage") Integer currentPage, @Param("pageSize") Integer pageSize);


    public int queryCountCourseInfo(@Param("whereSql") String whereSql, @Param("className") String className);


    public List<Map<String, Object>> courseArrangePercentOfClass(@Param("whereSql") String whereSql);

    /**
     * 查询各个教研部的课时统计
     * @return
     */
    public int statisticCourseByTeacherResearch(@Param("whereSql") String whereSql);
}