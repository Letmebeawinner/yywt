package com.renshi.biz.common;

import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.renshi.common.BaseHessianService;
import com.renshi.entity.department.DepartMent;
import com.renshi.entity.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基础系统接口调用Biz
 *
 * @author 268
 */
@Service
public class BaseHessianBiz {
    @Autowired
    private BaseHessianService baseHessianService;

    /**
     * 添加系统用户
     */
    public String addSysUser(SysUser sysUser) {
        Map<String, String> map = ObjectUtils.objToMap(sysUser);
        return baseHessianService.addSysUser(map);
    }

    /**
     * 更新系统用户
     *
     * @param sysUser 系统用户.不更新的字段勿传
     */
    public String updateSysUser(SysUser sysUser) {
        Map<String, String> map = ObjectUtils.objToMap(sysUser);
        return baseHessianService.updateSysUser(map);
    }


    /**
     * 删除系统用户
     * @param userId
     */
    public void deleteSysUserById(Long userId){
        baseHessianService.deleteSysUserById(userId);
    }

    /**
     * 根据用户类型或/和linkId查询系统用户
     */
    public SysUser querySysUser(Integer userType, Long linkId) {
        List<Map<String, String>> maps = baseHessianService.querySysUser(userType, linkId);
        if (!CollectionUtils.isEmpty(maps)) {
            Map<String, String> map = maps.get(0);
            return mapToEntity(map, SysUser.class);
        }
        return null;
    }

    /**
     * 根据用户id查询系统用户
     */
    public SysUser querySysUserById(Long userId) {
        Map<String, String> map = baseHessianService.querySysUserById(userId);
        SysUser sysUser = new SysUser();
        if (ObjectUtils.isNotNull(map)) {
            sysUser = mapToEntity(map, SysUser.class);
        } else {
            return null;
        }
        return sysUser;
    }

    /**
     * 所有部门
     */
    public List<DepartMent> getDepartMentList(DepartMent _departMent) {
        List<DepartMent> departMentList = new ArrayList<>();
        List<Map<String, String>> maps = baseHessianService.queryAllDepartment();
        if (!CollectionUtils.isEmpty(maps)) {
            for (Map<String, String> map : maps) {
                DepartMent departMent = mapToEntity(map, DepartMent.class);
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
        Map<String, String> map = baseHessianService.queryDepartemntById(id);
        DepartMent departMent = new DepartMent();
        if (ObjectUtils.isNotNull(map)) {
            departMent = mapToEntity(map, DepartMent.class);
        } else {
            return null;
        }
        return departMent;
    }


    /**
     * 删除部门
     *
     * @param departmentId
     */
    public void deleteDepartment(Long departmentId) {
        baseHessianService.deleteDepartment(departmentId);
    }


    /**
     * 添加部门
     */
    public String addDepartment(DepartMent department) {
        Map<String, String> map = ObjectUtils.objToMap(department);
        return  baseHessianService.addDepartment(map);
    }

    /**
     * 修改部门
     */
    public String updateDepartment(DepartMent department) {
        Map<String, String> map = ObjectUtils.objToMap(department);
        return baseHessianService.updateDepartment(map);
    }

    public List<SysUser> querySysUserList(SysUser sysUser) {
        List<SysUser> sysUserList = new ArrayList<>();
        String whereSql = " 1=1";
        whereSql += " and status!=2";
        if (!StringUtils.isTrimEmpty(sysUser.getMobile())) {
            whereSql += " and mobile like '%" + sysUser.getMobile() + "%'";
        }
        Map<String, Object> map = baseHessianService.querySysUserList(null, whereSql);
        List<Map<String, String>> list = (List<Map<String, String>>) map.get("userList");
        if (!CollectionUtils.isEmpty(list)) {
            for (Map<String, String> _map : list) {
                SysUser _sysUser = mapToEntity(_map, SysUser.class);
                sysUserList.add(_sysUser);
            }
        } else {
            return null;
        }
        return sysUserList;
    }

    /**
     * map集合转实体类
     *
     * @param map   map集合
     * @param clazz 实体类类对象
     * @param <T>   实体类
     * @return 实体类对象
     */
    private <T> T mapToEntity(Map<String, String> map, Class<T> clazz) {
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String json = gson.toJson(map);
        return gson.fromJson(json, clazz);
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
        String whereSql = " 1=1";
        if(ObjectUtils.isNotNull(sysUser.getId())){
            whereSql += " and id=" + sysUser.getId();
        }
        Map<String, Object> maps = baseHessianService.querySysUserList(null, whereSql);
        if (maps != null && maps.get("userList") != null) {
            List<Map<String, String>> users = (List<Map<String, String>>) maps.get("userList");
            sysUsers = users.stream()
                    .map(SysUser::new)
                    .filter(sysUser1 -> {
                        return name == null || (!StringUtils.isEmpty(sysUser1.getUserName()) && sysUser1.getUserName().indexOf(name) > -1);
                    })
                    .collect(Collectors.toList());
        }
        return sysUsers;
    }

    /**
     * 查询用户角色
     * 判断时调用{@code List.contains}方法
     *
     * @param userId 用户id
     * @return 角色id列表
     */
    public List<Long> getUserRoleByUserId(Long userId){
        return baseHessianService.queryUserRoleByUserId(userId);
    }

}
