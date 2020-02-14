package com.houqin.biz.common;

import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.houqin.common.BaseHessianService;
import com.houqin.entity.department.DepartMent;
import com.houqin.entity.sysuser.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 基础系统接口调用Biz
 *
 * @author 268
 */
@Service
public class BaseHessianBiz {
    @Autowired
    private BaseHessianService hessianService;
    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;

    /**
     * 所有部门
     */
    public List<DepartMent> getDepartMentList(DepartMent _departMent) {
        List<DepartMent> departMentList = new ArrayList<>();
        List<Map<String, String>> maps = hessianService.queryAllDepartment();
        if (!CollectionUtils.isEmpty(maps)) {
            for (Map<String, String> map : maps) {
                DepartMent departMent = new DepartMent(map);
                if (!StringUtils.isTrimEmpty(_departMent.getDepartmentName())) {
                    if (departMent.getDepartmentName().indexOf(_departMent.getDepartmentName()) < 0) {
                        continue;
                    }
                }
                departMentList.add(departMent);
            }
        } else {
            return null;
        }
        return departMentList;
    }

    /**
     * 部门详情
     */
    public DepartMent queryDepartemntById(Long id) {
        Map<String, String> map = hessianService.queryDepartemntById(id);
        DepartMent departMent = new DepartMent();
        if (ObjectUtils.isNotNull(map)) {
            departMent = new DepartMent(map);
        } else {
            return null;
        }
        return departMent;
    }

    /**
     * @Description:查询系统用户列表
     * @author: lzh
     * @Param: [sysUser]
     * @Return: java.util.List<com.oa.entity.sysuser.SysUser>
     * @Date: 11:42
     */
    public List<SysUser> getSysUserList(SysUser sysUser) {
        List<SysUser> sysUsers = null;
        String name = sysUser.getUserName();
        Map<String, Object> maps = hessianService.querySysUserList(null, "1 = 1");
        if (maps != null && maps.get("userList") != null) {
            List<Map<String, String>> users = (List<Map<String, String>>) maps.get("userList");
            sysUsers = users
                    .stream()
                    .map(user -> new SysUser(user))
                    .filter(sysUser1 -> {
                        return
                                name == null
                                        || (!StringUtils.isEmpty(sysUser1.getUserName())
                                        && sysUser1.getUserName().indexOf(name) > -1);
                    })
                    .collect(Collectors.toList());
        }
        return sysUsers;
    }

    public SysUser getSysUserById(Long id) {
        Map<String, String> map = hessianService.querySysUserById(id);
        return map == null ? null : new SysUser(map);
    }


    public List<Map<String, String>> queryUserByIds(String userIds) {
        List<Long> ids = Stream.of(userIds.replaceAll("\\s", "").split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        List<Map<String, String>> Users = jiaoWuHessianService.queryStudentByIds(ids);
        return Users;
    }

    /**
     * 根据角色id获取用户
     *
     * @param roleId
     * @return
     */
    public List<Long> queryUserIdsByRoleId(Long roleId) {
        String userIds = hessianService.queryUserIdsByRoleId(roleId);
        List<Long> ids = Stream.of(userIds.replaceAll("\\s", "").split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        return ids;
    }

    /**
     * 查询用户角色
     * 判断时调用{@code List.contains}方法
     *
     * @param userId 用户id
     * @return 角色id列表
     */
    public List<Long> queryUserRoleByUserId(Long userId) {
        List<Long> roleIds = hessianService.queryUserRoleByUserId(userId);
        return roleIds;
    }

    /**
     * 查询用户角色
     *
     * @param userId
     * @return
     */
    public String queryUserRolesByUserId(Long userId) {
        String roles = hessianService.queryUserRolesByUserId(userId);
        return roles;
    }


    /**
     * 添加系统用户
     */
    public String addSysUser(SysUser sysUser) {
        Map<String, String> map = ObjectUtils.objToMap(sysUser);
        return hessianService.addSysUser(map);
    }

    /**
     * 查询用户列表
     * @param pagination
     * @param where
     * @return
     */
    public Map<String, Object> querySysUserList(Pagination pagination, String where){
        return hessianService.querySysUserList(pagination,where);
    }
}
