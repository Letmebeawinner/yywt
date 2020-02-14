package com.yicardtong.entity.work;

import com.a_268.base.util.DateUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

/**
 * 考勤明细处理映射处理对象
 *
 * @author 268
 * @create 2017-3-2-15:00
 */
public class WorkDayDetail implements RowMapper<Map<String,String>> {
    @Override
    public Map<String, String> mapRow(ResultSet resultSet, int i) throws SQLException {
        Map<String,String> result = new TreeMap<>();
        //流水号
        result.put("cID",resultSet.getString("cID"));
        //员工编号
        result.put("Source_Date",resultSet.getString("Source_Date"));
        //早上开始时间
        result.put("Per_ID",resultSet.getString("Per_ID"));
        //早上结束时间
        result.put("Work_Code",resultSet.getString("Work_Code"));
        //下午开始时间
        result.put("LateMins",String.valueOf(resultSet.getFloat("LateMins")));
        //下午结束时间
        result.put("LateNum",String.valueOf(resultSet.getFloat("LateNum")));
        //晚上开始时间
        result.put("EarlyMins",String.valueOf(resultSet.getFloat("EarlyMins")));
        //晚上结束时间
        result.put("EarlyNum",String.valueOf(resultSet.getFloat("EarlyNum")));
        //自定义项
        result.put("AbsentDay",String.valueOf(resultSet.getFloat("AbsentDay")));
        result.put("AbsentHr",String.valueOf(resultSet.getFloat("AbsentHr")));
        result.put("OverMins",String.valueOf(resultSet.getInt("OverMins")));
        result.put("OverHr",String.valueOf(resultSet.getFloat("OverHr")));
        result.put("HolOverDs",String.valueOf(resultSet.getFloat("HolOverDs")));
        result.put("HolOverHs",String.valueOf(resultSet.getFloat("HolOverHs")));
        result.put("SatOverDs",String.valueOf(resultSet.getFloat("SatOverDs")));
        result.put("SatOverHS",String.valueOf(resultSet.getFloat("SatOverHS")));
        result.put("NormalOverDs",String.valueOf(resultSet.getFloat("NormalOverDs")));
        result.put("NormalOverHs",String.valueOf(resultSet.getFloat("NormalOverHs")));
        result.put("NormalOverMins",String.valueOf(resultSet.getFloat("NormalOverMins")));
        result.put("LeaveHs",String.valueOf(resultSet.getFloat("LeaveHs")));
        result.put("LeaveHs",String.valueOf(resultSet.getFloat("LeaveHs")));
        result.put("ShwkDys",String.valueOf(resultSet.getFloat("ShwkDys")));
        result.put("WkHrs",String.valueOf(resultSet.getFloat("WkHrs")));
        result.put("Define1",resultSet.getString("Define1"));
        result.put("Define2",resultSet.getString("Define2"));
        result.put("Modify_User",resultSet.getString("Modify_User"));

//        String Modify_Date= null;
//        if(resultSet.getDate("Modify_Date")!=null){
//            //刷卡时间
//            Modify_Date = DateUtils.format(resultSet.getTimestamp("Modify_Date"),"HH:mm:ss");
//        }
//        result.put("Modify_Date",Modify_Date);
        result.put("iType",String.valueOf(resultSet.getInt("iType")));
        result.put("Memo",resultSet.getString("Memo"));
        result.put("iHoliday",String.valueOf(resultSet.getFloat("iHoliday")));
        result.put("Hol_Name",resultSet.getString("Hol_Name"));
        result.put("Lea_Name",resultSet.getString("Lea_Name"));
        result.put("WkDays",String.valueOf(resultSet.getFloat("WkDays")));
        result.put("ShwkHrs",String.valueOf(resultSet.getFloat("ShwkHrs")));
        result.put("iFlag",resultSet.getString("iFlag"));
        return result;
    }
}
