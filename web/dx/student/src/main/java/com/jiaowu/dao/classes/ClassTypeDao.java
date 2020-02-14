package com.jiaowu.dao.classes;

import com.a_268.base.core.BaseDao;
import com.jiaowu.entity.classes.ClassType;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ClassTypeDao extends BaseDao<ClassType>{

    public int queryClassTypeCount(@Param("whereSql") String whereSql);


    public Map<String,Object> queryClassTypeNum();

}
