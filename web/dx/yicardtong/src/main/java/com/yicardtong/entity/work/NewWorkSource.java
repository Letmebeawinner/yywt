package com.yicardtong.entity.work;

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
public class NewWorkSource implements RowMapper<Map<String,String>> {
    @Override
    public Map<String, String> mapRow(ResultSet resultSet, int i) throws SQLException {
        Map<String,String> result = new TreeMap<>();
        //流水号
        result.put("cID",resultSet.getString("cID"));
        result.put("SourceDate",resultSet.getString("Source_Date"));
        result.put("PerID",resultSet.getString("Per_ID"));
        //打卡时间
        result.put("SourceDate",resultSet.getString("Source_Date"));
        //日期时间
        result.put("Date",resultSet.getString("Date"));
        //考勤卡号
        result.put("cCardNO",resultSet.getString("cCardNO "));
        return result;
    }
}
