package com.lock.dao;

import com.a_268.base.core.BaseDao;
import com.lock.entity.PassInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PassInfoDao extends BaseDao<PassInfo> {


    public List<PassInfo> passInfoList(@Param("whereSql") String whereSql);

}



