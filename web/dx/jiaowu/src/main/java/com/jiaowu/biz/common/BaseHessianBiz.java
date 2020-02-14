package com.jiaowu.biz.common;

import com.a_268.base.util.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiaowu.entity.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 处理Base数据
 *
 * @author YaoZhen
 * @create 11-22, 10:10, 2017.
 */
@Service
public class BaseHessianBiz {

    /**
     * 教职工账号的userType
     */
    public static final int employee = 2;
    /**
     * 科研处三个角色
     */
    private static final Long[] LONGS = {36L, 37L, 38L};

    @Autowired
    private BaseHessianService baseHessianService;

    /**
     * 根据用户id查询用户所在的部门
     * 这里返回的是用户部门中间表
     *
     * @param sysUserId
     * @return
     */
    public Map<String, String> queryDepartmentBySysUserId(Long sysUserId) {
        return baseHessianService.queryDepartmentBySysuserId(sysUserId);
    }

    /**
     * 根据系统用户id 查询对应的教职工ID
     *
     * @param userId 系统用户id
     * @return 人事系统教职工的id or null
     */
    public Long queryEmployeeIdBySysUserId(Long userId) {
        Map<String, String> user = baseHessianService.querySysUserById(userId);
        if (ObjectUtils.isNotNull(user)) {
            SysUser sysUser = mapToEntity(user, SysUser.class);
            if (sysUser.getUserType() == employee) {
                return sysUser.getLinkId();
            }
        }
        return null;
    }

    /**
     * 部门详情
     */
    public Map<String, String> queryDepartemntById(Long id) {
        Map<String, String> map = baseHessianService.queryDepartemntById(id);
        return map;
    }

    public Long queryUserRoleByUserId(Long userId) {
        List<Long> roleIds = baseHessianService.queryUserRoleByUserId(userId);
        return leadLoop(LONGS, roleIds);
    }

    /**
     * 根据用户的角色列表 判断属于哪个科研处角色
     *
     * @param longs    base中创建的科研处审批角色id
     * @param longList 当前登陆用户的角色id
     * @return 系统允许分配多个角色 但逻辑上一个用户应该只能有一个属于科研处的角色
     */
    private Long leadLoop(Long[] longs, List<Long> longList) {
        longList.retainAll(Arrays.asList(longs));

        if (longList.size() > 0) {
            return longList.get(0);
        } else {
            return 0L;
        }
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
}
