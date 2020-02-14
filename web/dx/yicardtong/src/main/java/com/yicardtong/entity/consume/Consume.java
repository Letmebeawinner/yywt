package com.yicardtong.entity.consume;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 考勤数据结果处理映射处理对象
 *
 * @author 268
 * @create 2017-3-2-11:00
 */
public class Consume implements RowMapper<Map<String,String>> {
    @Override
    public Map<String, String> mapRow(ResultSet resultSet, int i) throws SQLException {
        Map<String,String> result = new TreeMap<>();
        result.put("Base_PerID",resultSet.getString("Base_PerID"));
        result.put("Cost_Date",resultSet.getString("Cost_Date"));

        result.put("Cost_Count",String.valueOf(resultSet.getInt("Cost_Count")));
        result.put("Cost_SumMoney",String.valueOf(resultSet.getBigDecimal("Cost_SumMoney")));
        result.put("Base_PerName",resultSet.getString("Base_PerName"));
        result.put("Base_CardNo",resultSet.getString("Base_CardNo"));

        return result;
    }
}
