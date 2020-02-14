package com.oa.common;

import com.a_268.base.core.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 基础系统Hessian接口
 *
 * @author xiayong
 * @create 2016-12-16-16:07
 */
public interface BaseHessianService {

    /**
     * 添加用户
     *
     * @param sysUser 系统用户
     *                <table>
     *                <thead>
     *                <tr>
     *                <td>key</td>
     *                <td>type</td>
     *                <td>description</td>
     *                <td>default</td>
     *                <td>optional</td>
     *                </tr>
     *                </thead>
     *                <tbody>
     *                <tr>
     *                <td>userName</td>
     *                <td>{@link String}</td>
     *                <td>用户名</td>
     *                <td></td>
     *                <td>×</td>
     *                </tr>
     *                <tr>
     *                <td>password</td>
     *                <td>{@link String}</td>
     *                <td>用户密码</td>
     *                <td></td>
     *                <td>×</td>
     *                </tr>
     *                <tr>
     *                <td>email</td>
     *                <td>{@link String}</td>
     *                <td>用户邮箱</td>
     *                <td></td>
     *                <td>√</td>
     *                </tr>
     *                <tr>
     *                <td>mobile</td>
     *                <td>{@link String}</td>
     *                <td>用户手机</td>
     *                <td></td>
     *                <td>√</td>
     *                </tr>
     *                <tr>
     *                <td>userType</td>
     *                <td>{@link Integer}</td>
     *                <td>用户类型 1.管理员 2.教职工 3.学员</td>
     *                <td></td>
     *                <td>×</td>
     *                </tr>
     *                <tr>
     *                <td>linkId</td>
     *                <td>{@link Long}</td>
     *                <td>教职工或学员id</td>
     *                <td>0</td>
     *                <td>√</td>
     *                </tr>
     *                </tbody>
     *                </table>
     * @return {@link com.a_268.base.constants.ErrorCode#SUCCESS} 添加成功；否则失败信息
     */
    String addSysUser(Map<String, String> sysUser);

    /**
     * 根据用户类型或/和linkId查询系统用户
     *
     * @param userType 用户类型
     * @param linkId   教师或学生id
     * @return Map&lt;String, String&gt;，长度可能为0
     */
    List<Map<String, String>> querySysUser(Integer userType, Long linkId);

    /**
     * 获取所有部门
     *
     * @return List<Map   <   String   ,   String>>
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
     * @return List<Map   <   String   ,   String>>
     */
    List<Map<String, String>> queryAllRole();

    /**
     * 通过ID，查询角色数据对象
     *
     * @param roleId 角色ID
     * @return Map<String   ,   String>
     */
    Map<String, String> queryRoleById(Long roleId);

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
     * @return 用户列表List<Map   <   String   ,   String>>
     */
    List<Map<String, String>> querySysUserByIds(List<Long> userIds);

    /**
     * 分页查询用户列表
     *
     * @param pagination 分页条件对象
     * @param where      查询条件
     * @return 用户列表及分页对象Map<String   ,   Object>
     */
    Map<String, Object> querySysUserList(Pagination pagination, String where);

    /**
     * 根据用户查询当前用户的部门信息
     *
     * @param sysUserId
     * @return
     */
    Map<String, String> queryDepartmentBySysuserId(Long sysUserId);

    /**
     * 根据当前系统用户查询他的领导，如果已经为最大领导，那么领导为自己
     *
     * @param sysUserId
     * @return
     */
    List<Long> queryUserLeadersByUserId(Long sysUserId);

    /**
     * 根据角色名查询对应的用户
     *
     * @param roleName
     * @return
     */
    String queryUserIdsByRoleName(String roleName);

    /**
     * 根据用户id查询系统角色
     *
     * @param userId
     * @return
     */
    List<Map<String, String>> queryUserRoleInfoByUserId(Long userId);

    /**
     * 查询部门领导
     *
     * @param pagination
     * @param where
     * @return
     */
    Map<String, Object> queryLeadershipList(Pagination pagination, String where);

    /**
     * 查询部门下人员
     *
     * @param pagination
     * @param sysUserId
     * @return
     */
    Map<String, Object> queryDepartmentList(Pagination pagination, Long sysUserId);

    /**
     * 查询用户角色
     * 判断时调用{@code List.contains}方法
     *
     * @param userId 用户id
     * @return 角色id列表
     */
    public List<Long> queryUserRoleByUserId(Long userId);

    /**
     * @return
     * @Description:查询所有教职工通讯录信息
     * @author: jinshuo
     */
    public List<Map<String, String>> queryAppTelephoneList();

    /**
     * 查询所有分管校领导
     *
     * @return
     */
    public List<Map<String, String>> querySchoolRole();

    /**
     * 获取一级部门id串
     *
     * @return
     */
    public String queryParentDepartment();

    /**
     * 更新系统用户
     *
     * @param sysUser 系统用户.不更新的字段勿传
     */
    String updateSysUser(Map<String, String> sysUser);
    /**
     * 删除系统用户
     * @param userId
     */
    void deleteSysUserById(Long userId);

    /**
     * 根据姓名查询用户信息
     * @return
     */
    public Map<String, String> getSysUserByName(String userName);

    /**
     * 根据部门ids查询用户ids
     * @param departmentIds
     * @return
     */
    public String getUserIdsByDepartmentIds(String departmentIds);
}
