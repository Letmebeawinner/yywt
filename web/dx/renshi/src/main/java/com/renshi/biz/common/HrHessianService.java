package com.renshi.biz.common;

import com.a_268.base.core.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 人事管理系统Hessian接口
 *
 * @author xiayong
 * @create 2016-12-16-16:07
 */
public interface HrHessianService {
    /**
     * @Description: 通过hessian添加单个教职工
     * @author: xiayong
     * @Param: [employeeJson]
     * @Return: java.lang.Long
     * @Date: 2016/12/26
     */
    Long addEmployee(String employeeJson);

    /**
     * @Description: 通过hessian获取姓名
     * @author: xiayong
     * @Param: [id]
     * @Return: java.lang.String
     * @Date: 2016/12/26
     */
    String queryEmployeeNameById(Long id);

    /**
     * @Description: 通过hessian添加单个教职工
     * @author: xiayong
     * @Param: [employeeJson]
     * @Return: java.lang.String
     * @Date: 2016/12/26
     */
    String saveEmployee(String employeeJson);

    /**
     * @Description: 通过hessian修改教职工
     * @author: xiayong
     * @Param: [employeeJson]
     * @Return: java.lang.String
     * @Date: 2016/12/26
     */
    String updateEmployee(String employeeJson);


    String updateEmployeeType(String employeeJson);

    /**
     * @Description: 通过hessian删除教职工
     * @author: xiayong
     * @Param: [employeeJson]
     * @Return: java.lang.String
     * @Date: 2016/12/26
     */
    String deleteEmployee(String employeeJson);

    /**
     * 获取所有教职工
     *
     * @return List<Map < String , String>>
     */
    List<Map<String, String>> queryAllEmployee();

    /**
     * 获取所有教师
     *
     * @return List<Map < String , String>>
     */
    List<Map<String, String>> queryAllTeacher();

    /**
     * 获取教职工分页查询
     *
     * @return Map<String , Object>
     */
    Map<String, Object> getEmployeeListBySql(Pagination pagination, String whereSql);

    /**
     * 通过ID，查询教职工详情
     *
     * @param employeeId 教职工 ID
     * @return 教职工对Map
     */
    Map<String, String> queryEmployeeById(Long employeeId);

    /**
     * 通过IDs，查询教职工详情
     *
     * @param ids 教职工 IDs
     * @return 教职工对Map
     */
    List<Map<String, String>> queryEmployeeByIds(String ids);

    /**
     * 通过教职工编号，查询教职工详情
     *
     * @param employeeNo 教职工 employeeNo
     * @return 教职工对Map
     */
    Map<String, String> queryEmployeeByEmployeeNo(String employeeNo);


    /**
     * 统计职工人数
     * <table>
     * <thead>
     * <tr>
     * <th>代码</th>
     * <th>含义</th>
     * </tr>
     * </thead>
     * <tbody>
     * <tr>
     * <td>1</td>
     * <td>性别</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>民族</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>学历</td>
     * </tr>
     * <tr>
     * <td>4</td>
     * <td>职务</td>
     * </tr>
     * </tbody>
     * </table>
     *
     * @param condition 统计条件
     * @return {@link Map} 职工人数
     */
    Map<Object, Long> getEmployeeCount(Integer condition);

    /**
     * 上班考勤统计
     *
     * @param year  年份 month 月 day 日
     * @param month 月
     * @param day   日
     * @return 考勤统计
     * @since 2017-03-03
     */
    Map<String, Object> listWorkStatistic(Pagination pagination, String year, String month, String day);

    /**
     * 获取教职工的详情
     *
     * @param longs 教职工id
     * @return 教职工详情
     */
    Map<String, String> getEmployeeListByIds(Pagination pagination, List<Long> longs, String name);

    /**
     * 根据id获取班主任手机号
     *
     * @param teacherId
     * @return
     */
    public String getEmployeeMobileById(Long teacherId);

    /**
     * 取消班主任
     *
     * @param employeeJson
     * @return
     */
    Integer cancelClassLeader(String employeeJson, int cancelType);

    /**
     * 新建教师，从职工选择，修改职工的状态位讲师
     *
     * @param employeeJson
     * @return
     */
    String updateEmployeeTypeBySource(String employeeJson);

    /**
     * 根据sysUserId查出教职工ID对应教务的classes的teacherId
     *
     * @param sysUserId
     * @return
     */
    public Long getEmployeeBySysUserId(Long sysUserId);
}
