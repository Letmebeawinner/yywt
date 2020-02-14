package com.keyanzizheng.common;

import com.a_268.base.core.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 基础系统Hessian接口
 *
 * @author s.li
 * @create 2016-12-16-16:07
 */
public interface BaseHessianService {

    /**
     * 获取所有部门
     * @return List<Map<String,String>>
     */
    List<Map<String,String>> queryAllDepartment();

    /**
     * 通过ID，查询部门详情
     * @param departmentId 部门 ID
     * @return 部门对Map
     */
    Map<String,String> queryDepartemntById(Long departmentId);

    /**
     * 获取所有角色
     * @return List<Map<String,String>>
     */
    List<Map<String,String>> queryAllRole();

    /**
     * 通过ID，查询角色数据对象
     * @param roleId 角色ID
     * @return  Map<String,String>
     */
    Map<String,String> queryRoleById(Long roleId);

    /**
     * 通过管理员ID，查询管理员数据
     * @param userId 用户ID
     * @return 用户Map
     */
    Map<String,String> querySysUserById(Long userId);

    /**
     * 通过ID列表，查询管理员用户列表
     * @param userIds 用户ID列表
     * @return  用户列表List<Map<String,String>>
     */
    List<Map<String,String>> querySysUserByIds(List<Long> userIds);

    /**
     * 分页查询用户列表
     * @param pagination 分页条件对象
     * @param where 查询条件
     * @return  用户列表及分页对象Map<String,Object>
     */
    Map<String,Object> querySysUserList(Pagination pagination, String where);

    /**
     * 根据用户查询当前用户的部门信息
     *
     * @param sysUserId
     * @return
     */
    Map<String, String> queryDepartmentBySysuserId(Long sysUserId);

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
     * 查询用户角色
     * 判断单个角色时调用{@code List.contains}方法
     * 判断单多个角色时调用{@code List.retainAll}方法
     *
     * @param userId 用户id
     * @return 角色id列表
     */
    List<Long> queryUserRoleByUserId(Long userId);
}
