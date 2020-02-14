package com.renshi.biz.attendance;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.common.WorkSourceService;
import com.renshi.dao.attendance.WorkAttendanceDao;
import com.renshi.entity.attendance.QueryWorkAttendance;
import com.renshi.entity.attendance.WorkAttendance;
import com.renshi.entity.attendance.WorkDayDetail;
import com.renshi.entity.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 上班考勤Biz
 *
 * @author 268
 */
@Service
public class WorkAttendanceBiz extends BaseBiz<WorkAttendance, WorkAttendanceDao> {

    /**
     * map集合转实体类
     *
     * @param map   map集合
     * @param clazz 实体类类对象
     * @param <T>   实体类
     * @return 实体类对象
     */
    private <T> T mapToEntity(Map<String, String> map, Class<T> clazz) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return gson.fromJson(json, clazz);
    }
}
