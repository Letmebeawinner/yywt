package com.base.biz.common;

import com.a_268.base.core.Pagination;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/8 0008.
 */
public interface HrHessianService {

    /**
     * @Description:    通过hessian添加单个教职工
     * @author: xiayong
     * @Param: [employeeJson]

     * @Return: java.lang.Long
     * @Date: 2016/12/26
     */
    Long  addEmployee(String employeeJson);
    /**
     * @Description:    通过hessian添加单个教职工
     * @author: xiayong
     * @Param: [employeeJson]

     * @Return: java.lang.String
     * @Date: 2016/12/26
     */
    String  saveEmployee(String employeeJson);
    /**
     * @Description:    通过hessian修改教职工
     * @author: xiayong
     * @Param: [employeeJson]
     * @Return: java.lang.String
     * @Date: 2016/12/26
     */
    String  updateEmployee(String employeeJson);

    /**
     * @Description:    通过hessian删除教职工
     * @author: xiayong
     * @Param: [employeeJson]
     * @Return: java.lang.String
     * @Date: 2016/12/26
     */
    String  deleteEmployee(String employeeJson);
    /**
     * 获取所有教职工
     * @return List<Map<String,String>>
     */
    List<Map<String,String>> queryAllEmployee();

    /**
     * 获取所有教师
     * @return List<Map<String,String>>
     */
    List<Map<String,String>> queryAllTeacher();

    /**
     * 获取教职工分页查询
     * @return Map<String,Object>
     */
    Map<String,Object> getEmployeeListBySql(Pagination pagination, String whereSql);

    /**
     * 通过ID，查询教职工详情
     * @param employeeId 教职工 ID
     * @return 教职工对Map
     */
    Map<String,String> queryEmployeeById(Long employeeId);

}
