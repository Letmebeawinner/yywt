package com.keyanzizheng.dao.result;

import com.a_268.base.core.BaseDao;
import com.keyanzizheng.entity.result.Result;
import com.keyanzizheng.entity.result.ResultFormStatistic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 成果Dao
 *
 * @author 268
 */
@Component
public interface ResultDao extends BaseDao<Result> {

    /**
     * 统计人员的各个类型的成果占比
     */
    List<ResultFormStatistic> queryStatisticResult(@Param("whereSql") String whereSql, @Param("selectYear") String selectYear);

    /**
     * 统计数量
     */
    List<ResultFormStatistic> queryResultForm(@Param("whereSql") String whereSql);

}
