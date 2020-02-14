package com.jiaowu.dao.classes;

import com.a_268.base.core.BaseDao;
import com.jiaowu.entity.classes.ClassTypeStatistic;
import com.jiaowu.entity.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassTypeStatisticDao extends BaseDao<ClassTypeStatistic>{

    public List<ClassTypeStatistic> queryClassTypeCount(@Param("classTypeStatistic") ClassTypeStatistic classTypeStatistic);

}
