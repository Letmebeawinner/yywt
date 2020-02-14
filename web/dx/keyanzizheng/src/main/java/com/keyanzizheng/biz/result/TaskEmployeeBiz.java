package com.keyanzizheng.biz.result;

import com.a_268.base.core.BaseBiz;
import com.keyanzizheng.dao.result.TaskEmployeeDao;
import com.keyanzizheng.entity.result.TaskEmployee;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课题-人员Biz
 *
 * @author 268
 */
@Service
public class TaskEmployeeBiz extends BaseBiz<TaskEmployee, TaskEmployeeDao> {
    /**
     * 条件查询
     *
     * @author 268
     */
    public List<TaskEmployee> getTaskEmployeeList(TaskEmployee taskEmployee){
        String whereSql=" 1=1";
        whereSql+=" and status!=2 ";
        if(taskEmployee.getEmployeeId()!=null && taskEmployee.getEmployeeId()>0){
            whereSql+=" and employeeId="+taskEmployee.getEmployeeId();
        }
        if(taskEmployee.getTaskId()!=null && taskEmployee.getTaskId()>0){
            whereSql+=" and TaskId="+taskEmployee.getTaskId();
        }
        return this.find(null,whereSql);
    }
}
