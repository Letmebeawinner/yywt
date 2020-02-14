package com.tongji.biz.common;

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
    /* employee具备属性 (姓名，手机，邮箱及系统登录密码必须要有)
    String employeeNo;//编号
    String identityCard;//身份证
    String name;//姓名
    Long age;//年龄
    Integer sex;//性别 1 男 0 女
    String nationality;//民族
    String education;//学历
    String profession;//专业
    String position;//职务
    BigDecimal baseMoney;//基本工资
    Integer ifCripple;//是否残疾
    String resumeInfo;//履历信息
    Long employeeType;//教职工类别
    String mobile;//电话
    String email;//邮箱
    String password;//系统登录密码
    Long sysUserId;   //系统用户id*/
    /* ************************************************************************ */

    /**
     * @Description: 通过hessian添加单个教职工
     * @author: xiayong
     * @Param: [employeeJson]
     * @Return: java.lang.Long
     * @Date: 2016/12/26
     */
    Long addEmployee(String employeeJson);

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
     * @return List<Map<String,String>>
     */
    List<Map<String, String>> queryAllEmployee();

    /**
     * 获取所有教师
     *
     * @return List<Map<String,String>>
     */
    List<Map<String, String>> queryAllTeacher();

    /**
     * 获取教职工分页查询
     *
     * @return Map<String,Object>
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
     * 通过教职工编号，查询教职工详情
     *
     * @param employeeNo 教职工 employeeNo
     * @return 教职工对Map
     */
    Map<String, String> queryEmployeeByEmployeeNo(String employeeNo);

    /**
     * 年度收支统计之薪资支出统计
     *
     * @param year 年份
     * @return 薪资统计
     * @since 2017-03-03
     */
    Double getAnnualSalaryExpense(String year);

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
}
