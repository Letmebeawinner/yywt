package com.jiaowu.dao.classes;

import com.a_268.base.core.BaseDao;
import com.jiaowu.entity.classes.ClassesStatistic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassesStatisticDao extends BaseDao<ClassesStatistic>{

    //查询各个班次的报名人数
    public List<ClassesStatistic> queryClassPersonNum();


    //查询各个单位的报名人数
    public List<ClassesStatistic> queryUnitPersonNum(@Param("whereSql") String whereSql);


    //查询各个单位的班次报名人数
    public List<ClassesStatistic> queryUnitClassTypeNum(@Param("whereSql") String whereSql);


}
