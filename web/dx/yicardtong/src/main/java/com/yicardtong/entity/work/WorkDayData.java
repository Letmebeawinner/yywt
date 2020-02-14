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
public class WorkDayData implements RowMapper<Map<String,String>> {
    @Override
    public Map<String, String> mapRow(ResultSet resultSet, int i) throws SQLException {
        Map<String,String> result = new TreeMap<>();
        //流水号
        result.put("cID",resultSet.getString("cID"));
        result.put("workDate",resultSet.getString("Work_Date"));
        //员工编号
        result.put("basePerID",resultSet.getString("Base_PerID"));
        //员工卡号
        result.put("BaseCardNo",resultSet.getString("Base_CardNo"));
        //员工名称
        result.put("BasePerName",resultSet.getString("Base_PerName"));
        //早上开始时间
        result.put("morBeg",resultSet.getString("Mor_Beg"));
        //早上结束时间
        result.put("morEnd",resultSet.getString("Mor_End"));
        //下午开始时间
        result.put("aftBeg",resultSet.getString("Aft_Beg"));
        //下午结束时间
        result.put("aftEnd",resultSet.getString("Aft_End"));
        //晚上开始时间
        result.put("eveBeg",resultSet.getString("Eve_Beg"));
        //晚上结束时间
        result.put("eveEnd",resultSet.getString("Eve_End"));
        //自定义项
        result.put("define1",resultSet.getString("Define1"));
        result.put("define2",resultSet.getString("Defin2"));
        result.put("cMemo",resultSet.getString("cMemo"));
        result.put("workCode",resultSet.getString("Work_Code"));
        result.put("iFlag",resultSet.getString("iFlag"));
        return result;
    }
}
