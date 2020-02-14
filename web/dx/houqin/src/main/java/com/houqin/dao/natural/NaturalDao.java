package com.houqin.dao.natural;

import com.a_268.base.core.BaseDao;
import com.houqin.entity.natural.Natural;
import com.houqin.entity.natural.NaturalStatistic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 天燃气
 *
 * @author ccl
 * @create 2017-05-18-17:18
 */
public interface NaturalDao extends BaseDao<Natural> {

    /**
     * 查询个数
     *
     * @return
     */
    Integer queryNaturalCount();

    /**
     * 查询按月统计
     *
     * @param whereSql
     * @return
     */
    List<NaturalStatistic> queryStatisticByYearAndMonth(@Param("whereSql") String whereSql);

    /**
     * 查询通过年查询总数
     *
     * @param whereSql
     * @return
     */
    Integer queryCountByYear(@Param("whereSql") String whereSql);

    /**
     * 查询用气占比
     *
     * @param whereSql
     * @return
     */
    List<NaturalStatistic> queryNaturalPurposeByYear(@Param("whereSql") String whereSql);

}
