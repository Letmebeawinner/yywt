package com.yicardtong.entity.work;

import com.a_268.base.util.DateUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 考勤数据结果处理映射处理对象
 *
 * @author s.li
 * @create 2017-02-24-13:43
 */
public class WorkSourceMapper implements RowMapper<Map<String,String>> {
    @Override
    public Map<String, String> mapRow(ResultSet resultSet, int i) throws SQLException {
        Map<String,String> result = new TreeMap<>();
        //流水号
        result.put("cID",String.valueOf(resultSet.getInt("cID")));
        String Source_Date= null;
        if(resultSet.getDate("Source_Date")!=null){
            //刷卡时间
            Source_Date = DateUtils.format(resultSet.getDate("Source_Date"),"HH:mm:ss");
        }
        result.put("Source_Date",Source_Date);
        //员工编号
        result.put("Per_ID",resultSet.getString("Per_ID"));
        //刷卡时间
        result.put("Source_Data",resultSet.getString("Source_Data"));
        //修改人
        result.put("Modify_User",resultSet.getString("Modify_User"));
        //自定义项
        result.put("Define1",resultSet.getString("Define1"));
        //记录设备编号
        result.put("Define2",resultSet.getString("Define2"));
        //自定义项
        result.put("Define3",resultSet.getString("Define3"));
        //处理标志(暂时没用)
        result.put("iFlag",resultSet.getString("iFlag"));
        result.put("Date",resultSet.getString("Date"));
        //数据来源,0为脱机数据 1为实时监控暂时没用
        result.put("iSource",String.valueOf(resultSet.getInt("iSource")));
        //员工编号
        result.put("Base_PerNo",resultSet.getString("Base_PerNo"));
        return result;
    }
}
