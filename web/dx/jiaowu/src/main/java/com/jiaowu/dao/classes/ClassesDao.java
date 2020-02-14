package com.jiaowu.dao.classes;

import com.a_268.base.core.BaseDao;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.classes.ClassesStatistic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClassesDao extends BaseDao<Classes>{


    public List<ClassesStatistic> queryClassesStatisticByYear(@Param("whereSql") String whereSql);


}
