package com.houqin.dao.oil;

import com.a_268.base.core.BaseDao;
import com.houqin.entity.natural.NaturalStatistic;
import com.houqin.entity.oil.Oil;
import com.houqin.entity.oil.OilStatistic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 柴油表
 * Created by Administrator on 2017/6/13 0013.
 */
public interface OilDao extends BaseDao<Oil>{

    /**
     * 每年的总数
     * @param whereSql
     * @return
     */
    Integer queryCountByYear(@Param("whereSql") String whereSql);

    /**
     * 按照月查询每年的数据
     * @param whereSql
     * @return
     */

    List<OilStatistic> queryOilStatisticByYear(@Param("whereSql") String whereSql);

}