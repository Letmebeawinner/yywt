package com.sms.biz.common;

import com.a_268.base.core.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 基础系统Hessian接口
 *
 * @author s.li
 * @since 2016-12-16 16:07
 */
public interface BaseHessianService {

    /**
     * 添加系统用户
     *
     * @param sysUserMap 系统用户
     *                   <table>
     *                   <thead>
     *                   <tr>
     *                   <td>key</td>
     *                   <td>type</td>
     *                   <td>description</td>
     *                   <td>default</td>
     *                   <td>optional</td>
     *                   </tr>
     *                   </thead>
     *                   <tbody>
     *                   <tr>
     *                   <td>userName</td>
     *                   <td>{@link String}</td>
     *                   <td>用户名</td>
     *                   <td></td>
     *                   <td>×</td>
     *                   </tr>
     *                   <tr>
     *                   <td>password</td>
     *                   <td>{@link String}</td>
     *                   <td>用户密码</td>
     *                   <td></td>
     *                   <td>×</td>
     *                   </tr>
     *                   <tr>
     *                   <td>email</td>
     *                   <td>{@link String}</td>
     *                   <td>用户邮箱</td>
     *                   <td></td>
     *                   <td>√</td>
     *                   </tr>
     *                   <tr>
     *                   <td>mobile</td>
     *                   <td>{@link String}</td>
     *                   <td>用户手机</td>
     *                   <td></td>
     *                   <td>√</td>
     *                   </tr>
     *                   <tr>
     *                   <td>userType</td>
     *                   <td>{@link Integer}</td>
     *                   <td>用户类型 1.管理员 2.教职工 3.学员</td>
     *                   <td></td>
     *                   <td>×</td>
     *                   </tr>
     *                   <tr>
     *                   <td>linkId</td>
     *                   <td>{@link Long}</td>
     *                   <td>教职工或学员id</td>
     *                   <td>0</td>
     *                   <td>√</td>
     *                   </tr>
     *                   </tbody>
     *                   </table>
     * @return {@link com.a_268.base.constants.ErrorCode#SUCCESS} 添加成功；否则失败信息
     * @since 2017-02-06
     */
    String addSysUser(Map<String, Object> sysUserMap);

    /**
     * 系统用户添加部门
     *
     * @param sysUserId        系统用户id
     * @param departmentIdList 部门id
     * @since 2017-02-06
     */
    void addSysUserDepartment(Long sysUserId, List<Long> departmentIdList);

    /**
     * 检查邮箱或手机号是否已存在
     *
     * @param emailOrMobile 邮箱或手机
     * @param type          1.邮箱 2.手机
     * @return {@code true} 存在
     * @since 2017-02-06
     */
    boolean isEmailOrMobileExist(String emailOrMobile, Integer type);

    /**
     * 获取所有部门
     *
     * @return List&lt;Map&lt;String,String&gt;&gt;
     */
    List<Map<String, String>> queryAllDepartment();

    /**
     * 通过ID，查询部门详情
     *
     * @param departmentId 部门 ID
     * @return 部门对Map
     */
    Map<String, String> queryDepartemntById(Long departmentId);

    /**
     * 获取所有角色
     *
     * @return List&lt;Map&lt;String,String&gt;&gt;
     */
    List<Map<String, String>> queryAllRole();

    /**
     * 通过ID，查询角色数据对象
     *
     * @param roleId 角色ID
     * @return Map&lt;String,String&gt;
     */
    Map<String, String> queryRoleById(Long roleId);

    /**
     * 根据用户类型或/和linkId查询系统用户
     *
     * @param userType 用户类型
     * @param linkId   教师或学生id
     * @return List&lt;Map&lt;String, String&gt;&gt;
     * 符合条件的系统用户，长度可能为0
     * @since 2017-02-06
     */
    List<Map<String, String>> querySysUser(Integer userType, Long linkId);

    /**
     * 通过管理员ID，查询管理员数据
     *
     * @param userId 用户ID
     * @return 用户Map
     */
    Map<String, String> querySysUserById(Long userId);

    /**
     * 通过ID列表，查询管理员用户列表
     *
     * @param userIds 用户ID列表
     * @return 用户列表List&lt;Map&lt;String,String&gt;&gt;
     */
    List<Map<String, String>> querySysUserByIds(List<Long> userIds);

    /**
     * 查询指定用户的消息列表
     *
     * @param pagination 分页
     * @param receiverId 收件人id
     * @return 用户消息列表及分页.infoList:消息列表; pagination:分页
     * @since 2017-02-06
     */
    Map<String, Object> querySysUserInfoList(Pagination pagination, Long receiverId);

    /**
     * 分页查询用户列表
     *
     * @param pagination 分页条件对象
     * @param where      查询条件
     * @return 用户列表及分页对象Map&lt;String,Object&gt;
     */
    Map<String, Object> querySysUserList(Pagination pagination, String where);


    /**
     * 更新系统用户
     *
     * @param sysUserMap 系统用户.不更新的字段勿传
     * @return {@code null} 更新成功，否则失败信息
     * @since 2017-02-07
     */
    String updateSysUser(Map<String, String> sysUserMap);

    /**
     * 更新系统用户关联的部门
     *
     * @param sysUserId        系统用户id
     * @param departmentIdList 部门id
     */
    void updateSysUserDepartment(Long sysUserId, List<Long> departmentIdList);

    /**
     * 获取一级部门id串
     *
     * @return
     */
    public String queryParentDepartment();


    /**
     * 获取用户手机号串
     * @return
     */
    public String getUserMobiles(List<String> userIdList);
}