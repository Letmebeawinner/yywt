package com.houqin.common;

import com.a_268.base.core.Pagination;
import com.houqin.entity.sysuser.SysUser;

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
     * 获取所有部门
     *
     * @return List<Map       <       String       ,       String>>
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
     * @return List<Map       <       String       ,       String>>
     */
    List<Map<String, String>> queryAllRole();

    /**
     * 通过ID，查询角色数据对象
     *
     * @param roleId 角色ID
     * @return Map<String       ,       String>
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
     * @return 用户列表List<Map       <       String       ,       String>>
     */
    List<Map<String, String>> querySysUserByIds(List<Long> userIds);

    /**
     * 分页查询用户列表
     *
     * @param pagination 分页条件对象
     * @param where      查询条件
     * @return 用户列表及分页对象Map<String       ,       Object>
     */
    Map<String, Object> querySysUserList(Pagination pagination, String where);

    Map<String, Object> querySysUserAndRoleForPage(Pagination pagination, String where, Map<String, Object> params);

    /**
     * 根据角色id获取用户
     *
     * @param roleId
     * @return
     */
    public String queryUserIdsByRoleId(Long roleId);

    /**
     * 查询用户角色
     * 判断时调用{@code List.contains}方法
     *
     * @param userId 用户id
     * @return 角色id列表
     */
    List<Long> queryUserRoleByUserId(Long userId);

    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    String queryUserRolesByUserId(Long userId);

    /**
     * 添加系统用户
     */
    public String addSysUser(Map<String, String> sysUser);

    /**
     * 根据id删除用户
     *
     * @return
     */
    public void delSysUserById(Long id);
}
