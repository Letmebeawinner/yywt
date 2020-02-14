package com.houqin.dao.electricity;

import com.a_268.base.core.BaseDao;
import com.houqin.entity.electricity.Electricity;
import com.houqin.entity.electricity.EletricityStatistic;
import com.houqin.entity.electricityType.ElectricityTypeDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 电表
 * @author lianyuchao
 * Created by Administrator on 2016/12/15.
 */
@Component
public interface ElectricityDao extends BaseDao<Electricity> {

    List<EletricityStatistic> queryEleStatisticByYear(@Param("whereSql") String whereSql);

    List<EletricityStatistic> queryElePowerByYear(@Param("whereSql") String sql);


    /**
     * 查询通过年查询总数
     * @param whereSql
     * @return
     */
    Integer queryCountByYear(@Param("whereSql") String whereSql);

    /**
     * 根据年份和区域id统计该区域的用电量
     *
     * @param electricityTypeDTO 年份和区域id
     * @return 该区域的用电量
     */
    ElectricityTypeDTO getEnergyUsedById(ElectricityTypeDTO electricityTypeDTO);


}
