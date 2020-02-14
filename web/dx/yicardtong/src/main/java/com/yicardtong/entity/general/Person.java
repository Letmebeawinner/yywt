package com.yicardtong.entity.general;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by caichenglong on 2017/10/17.
 */
public class Person implements RowMapper<Map<String,String>> {
    @Override
    public Map<String, String> mapRow(ResultSet resultSet, int i) throws SQLException {
        Map<String,String> result = new TreeMap<>();
        //流水号
        result.put("Base_PerID",resultSet.getString("Base_PerID"));
        result.put("Base_PerNo",resultSet.getString("Base_PerNo"));
        result.put("Base_PerName",resultSet.getString("Base_PerName"));
        result.put("Base_CardNo",resultSet.getString("Base_CardNo"));
        result.put("Base_Money",String.valueOf(resultSet.getBigDecimal("Base_Money")));
        result.put("Base_Deposit",String.valueOf(resultSet.getBigDecimal("Base_Deposit")));
        return result;
    }
}