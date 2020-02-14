package com.yicardtong.entity.attend;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by 李帅雷 on 2017/10/21.
 */
public class WorkLeaveInfo implements RowMapper<Map<String,String>> {
    @Override
    public Map<String, String> mapRow(ResultSet resultSet, int i) throws SQLException {
        Map<String, String> result = new TreeMap<>();

        //流水号
        result.put("cid", String.valueOf(resultSet.getInt("cID")));
        //请假时间
        result.put("leaDate",resultSet.getString("Lea_Date"));
        //请假原因
        result.put("leaName",resultSet.getString("lea_Name"));
        //请假类型
        result.put("leaType",resultSet.getString("Lea_Type"));
        //自定义项
        result.put("define1",resultSet.getString("Define1"));
        //自定义项
        result.put("define2",resultSet.getString("Define2"));
        //修改人
        result.put("modifyUser",resultSet.getString("Modify_User"));
        //修改时间
        result.put("modifyDate",resultSet.getString("Modify_Date"));
        //请假人员
        result.put("basePerId",resultSet.getString("Base_PerID"));
        //开始日期
        result.put("begDate",resultSet.getString("Beg_Date"));
        //终止日期
        result.put("endDate",resultSet.getString("End_Date"));
        /*//开始时间
        result.put("begTime",resultSet.getString("Beg_Time"));
        //终止时间
        result.put("endTime",resultSet.getString("End_Time"));*/
        return result;
    }
}
