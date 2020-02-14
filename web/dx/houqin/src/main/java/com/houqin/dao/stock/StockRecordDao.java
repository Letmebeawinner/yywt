package com.houqin.dao.stock;

import com.a_268.base.core.BaseDao;
import com.houqin.entity.stock.StockRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author lzh
 * @create 2018-01-08-17:35
 */
public interface StockRecordDao extends BaseDao<StockRecord>{

   List<Map<String, Object>> getJoinStockRecordByYear(@Param("time") String time);


   List<Map<String, Object>> getOutStockRecordByYear(@Param("time") String time);

   List<Map<String, String>> getJoinStockRecordDetailByYearAndMonth(@Param("year") String year, @Param("month") String month);

   List<Map<String, String>> getOutStockRecordDetailByYearAndMonth(@Param("year") String year, @Param("month") String month);

}
