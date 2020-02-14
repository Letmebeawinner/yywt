package com.houqin.dao.lot;

import com.a_268.base.core.BaseDao;
import com.houqin.entity.lot.Lot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 食材批次
 *
 * @author YaoZhen
 * @create 10-24, 10:11, 2017.
 */
public interface LotDao extends BaseDao<Lot> {

    /**
     * 通过物品查询批次的内容
     *
     * @param whereSql
     * @return
     */
    public List<Lot> queryCountByLot(@Param("whereSql") String whereSql);


}
