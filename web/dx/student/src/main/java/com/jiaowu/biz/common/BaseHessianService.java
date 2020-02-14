package com.jiaowu.biz.common;

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
     * 添加用户报名 未报名过添加 再次报名修改
     *
     * @param sysUserMap
     * @return
     */
    String addSysUserSignUp(Map<String, Object> sysUserMap);

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
    String updateSysUser(Map<String, Object> sysUserMap);

    /**
     * 更新学员link
     *
     * @param sysUserId 系统用户
     * @param linkId    学员
     * @return {@code null} 更新成功，否则失败信息
     * @since 2017-02-07
     */
    void updateSysUserLink(String sysUserId, Long linkId);

    /**
     * 更新系统用户关联的部门
     *
     * @param sysUserId        系统用户id
     * @param departmentIdList 部门id
     */
    void updateSysUserDepartment(Long sysUserId, List<Long> departmentIdList);


    /**
     * 根据条件查询部门数据
     *
     * @param userId
     * @return
     */
    List<Map<String, String>> queryDepartmentByTypeAndId(Long userId);

    /**
     * 根据用户ID获取该用户所属部门
     *
     * @param sysuserId
     * @return
     */
    public Map<String, String> queryDepartmentBySysuserId(Long sysuserId);

    /**
     * 通过ID列表，查询管理员用户列表
     *
     * @param userIds 用户ID列表
     * @return 用户列表List&lt;Map&lt;String,String&gt;&gt;
     */
    List<Map<String, String>> querySysUserByLinkIds(Integer userType, List<Long> userIds);

    /**
     * 根据手机号查询学员信息
     *
     * @param mobile
     * @return
     */
    public List<Map<String, String>> queryStudentByMobile(String mobile);

    /**
     * 删除系统用户
     *
     * @param id
     */
    public void deleteSysUserId(String id);

    /**
     * 根据用户编号获取用户信息
     *
     * @param userno
     * @return
     */
    public Map<String, String> querySysuserByUserno(String userno);

    /**
     * 添加系统用户-角色中间表记录
     *
     * @param sysUserRole
     * @return
     */
    public void addSysUserRole(Map<String, String> sysUserRole);


    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    public String queryUserRolesByUserId(Long userId);

    /**
     * 删除用户的某个角色
     *
     * @param sysUserRole
     * @return
     */
    public int removeSysUserRole(Map<String, String> sysUserRole);

    /**
     * 修改单位用户的密码
     *
     * @param unitId
     * @param password
     * @return
     */
    public int updateUnitUserPassword(Long unitId, String password);

    /**
     * 查询用户角色
     * 判断时调用{@code List.contains}方法
     *
     * @param userId 用户id
     * @return 角色id列表
     */
    List<Long> queryUserRoleByUserId(Long userId);

    /**
     * 查询一群学员的账号id
     *
     * @param join com.google.common.base.Joiner#join(java.lang.Iterable)
     * @return com.google.common.base.Joiner#join(java.lang.Iterable)
     */
    String queryLinkIdByUserStudentIds(String join);

    /**
     * 删除BASE系统的用户
     *
     * @param result BASE用户的USERNO
     */
    void deleteSysUserByUserNo(String result);

    public String queryUnitByUnitId(Long unitId);

    /**
     * 根据编号修改用户name
     *
     * @param userNo
     * @param userName
     * @return
     */
    public Long querySysuserByUsernoUpdateUserName(String userNo, String userName);

    /**
     * 获取当前登录用户是什么权限
     * 1-教务处录入
     * 2-学员处录入
     * 3-信息处录入
     * @param sysUserId
     * @return
     */
    public Integer getSysUserDepartment(Long sysUserId);
}
