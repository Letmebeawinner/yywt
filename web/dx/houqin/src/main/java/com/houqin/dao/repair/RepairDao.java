package com.houqin.dao.repair;

import com.a_268.base.core.BaseDao;
import com.houqin.entity.pepairPeople.PepairPeople;
import com.houqin.entity.repair.Repair;
import com.houqin.entity.repair.RepairStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报修管理
 *
 * @author ccl
 * @create 2016-12-10-18:02
 */
public interface RepairDao extends BaseDao<Repair> {

    /**
     * 查询维修统计
     *
     * @param whereSql
     * @return
     */
    List<RepairStatistics> queryMaintenanceStatistics(@Param("whereSql") String whereSql);

    /**
     * 查询按照类型查询维修数量
     * @param month
     * @param year
     * @return
     */
    List<RepairStatistics> queryRepairNumByType(@Param("month") Integer month,@Param("year") Integer year);

}
