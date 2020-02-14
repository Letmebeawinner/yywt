package com.houqin.dao.water;

import com.a_268.base.core.BaseDao;
import com.houqin.entity.water.Water;
import com.houqin.entity.water.WaterStatistic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 水表
 * @author lianyuchao
 * Created by Administrator on 2016/12/15.
 */
public interface WaterDao extends BaseDao<Water> {

    /**
     * 每年的总数
     * @param whereSql
     * @return
     */
    Integer queryWaterCountByYear(@Param("whereSql") String whereSql);

    /**
     * 按照月查询每年的数据
     * @param whereSql
     * @return
     */

    List<WaterStatistic> queryWaterStatisticByYear(@Param("whereSql") String whereSql);

    /**
     * 按照水区域比例查询
     * @param whereSql
     * @return
     */
    List<WaterStatistic>  queryWaterPurposeByYear(@Param("whereSql") String whereSql);

}
