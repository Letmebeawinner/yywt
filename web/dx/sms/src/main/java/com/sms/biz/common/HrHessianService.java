package com.sms.biz.common;

import com.a_268.base.core.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 人事管理系统Hessian接口
 *
 * @author s.li
 * @create 2016-12-16-16:07
 */
public interface HrHessianService {

    /**
     * 获取所有教职工
     * @return List<Map<String,String>>
     */
    List<Map<String,String>> queryAllEmployee();

    /**
     * 获取教职工分页查询
     * @return List<Map<String,String>>
     */
    Map<String,Object> getEmployeeListBySql(Pagination pagination, String whereSql);

    /**
     * 通过ID，查询教职工详情
     * @param employeeId 教职工 ID
     * @return 教职工对Map
     */
    Map<String,String> queryEmployeeById(Long employeeId);

    /**
     * 通过IDs，查询教职工详情
     *
     * @param ids 教职工 IDs
     * @return 教职工对Map
     */
    public  List<Map<String, String>> queryEmployeeByIds(String ids);



}
