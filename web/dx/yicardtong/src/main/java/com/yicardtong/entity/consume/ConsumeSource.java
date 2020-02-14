package com.yicardtong.entity.consume;

import com.a_268.base.util.DateUtils;
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
public class ConsumeSource implements RowMapper<Map<String,String>> {
    @Override
    public Map<String, String> mapRow(ResultSet resultSet, int i) throws SQLException {
        Map<String,String> result = new TreeMap<>();
        //流水号
        result.put("Base_PerID",resultSet.getString("Base_PerID"));
        result.put("Base_CardNo",resultSet.getString("Base_CardNo"));
        result.put("Cost_DevID",resultSet.getString("Cost_DevID"));
        result.put("Cost_Money",String.valueOf(resultSet.getBigDecimal("Cost_Money")));
        result.put("Cost_Remain",String.valueOf(resultSet.getBigDecimal("Cost_Remain")));
        result.put("Cost_List",String.valueOf(resultSet.getInt("Cost_List")));

        String Cost_DateTime= null;
        if(resultSet.getDate("Cost_DateTime")!=null){
            //刷卡时间
            Cost_DateTime = DateUtils.format(resultSet.getDate("Cost_DateTime"),"HH:mm:ss");
        }
        result.put("Cost_DateTime",Cost_DateTime);
        result.put("Cost_DataOne",resultSet.getString("Cost_DataOne"));
        result.put("Cost_DataTwo",resultSet.getString("Cost_DataTwo"));
        result.put("Cost_DataThr",resultSet.getString("Cost_DataThr"));
        result.put("Base_Modify_User",resultSet.getString("Base_Modify_User"));
        String Base_Modify_DateTime= null;
        if(resultSet.getDate("Base_Modify_DateTime")!=null){
            //刷卡时间
            Base_Modify_DateTime = DateUtils.format(resultSet.getDate("Base_Modify_DateTime"),"HH:mm:ss");
        }
        result.put("Base_Modify_DateTime",Base_Modify_DateTime);
        result.put("Cost_MemAddr",resultSet.getString("Cost_MemAddr"));
        result.put("Base_GroupID",resultSet.getString("Base_GroupID"));
        result.put("Base_GroupName",resultSet.getString("Base_GroupName"));
        return result;
    }
}
