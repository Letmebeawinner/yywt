package com.base.biz.common;


import com.a_268.base.constants.BaseCommonConstants;
import com.a_268.base.core.Pagination;
import com.a_268.base.redis.RedisCache;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.MD5;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.base.biz.connection.SysUserDepartmentBiz;
import com.base.biz.department.DepartmentBiz;
import com.base.biz.role.RoleBiz;
import com.base.biz.role.SysUserRoleBiz;
import com.base.biz.user.SysUserBiz;
import com.base.entity.connection.SysUserDepartment;
import com.base.entity.department.Department;
import com.base.entity.permission.Resource;
import com.base.entity.role.Role;
import com.base.entity.role.SysUserRole;
import com.base.entity.user.SysUser;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 基础系统对外Hessian的Service实现类
 *
 * @author s.li
 * @since 2016-12-16 16:04
 */
@Service
public class BaseHessianBiz implements BaseHessianService {
    private RedisCache redisCache = RedisCache.getInstance();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Autowired
    private DepartmentBiz departmentBiz;
    @Autowired
    private RoleBiz roleBiz;
    @Autowired
    private SysUserBiz sysUserBiz;
    @Autowired
    private SmsHessianService smsHessianService;
    @Autowired
    private SysUserDepartmentBiz sysUserDepartmentBiz;
    @Autowired
    private SysUserRoleBiz sysUserRoleBiz;
    @Autowired
    private HrHessianService hrHessianService;


    @Override
    public String addSysUser(Map<String, String> sysUser) {
        SysUser user = gson.fromJson(gson.toJson(sysUser), new TypeToken<SysUser>() {
        }.getType());
        return sysUserBiz.createSysUserForOtherSystem(user);
    }

    @Override
    public String addSysUserSignUp(Map<String, String> sysUserMap) {
        SysUser user = gson.fromJson(gson.toJson(sysUserMap), new TypeToken<SysUser>() {
        }.getType());
        List<SysUser> userList = sysUserBiz.find(null, " userType=3 and mobile ='" + user.getMobile() + "' order by id desc");
        if (userList != null && userList.size() > 0) {
            SysUser sysUser = userList.get(0);
            sysUser.setLinkId(user.getLinkId());
            sysUser.setStatus(user.getStatus());
            sysUser.setEmail(user.getEmail());
            sysUser.setPassword(MD5.getMD5(user.getPassword()));
            sysUser.setUserName(user.getUserName());
            sysUserBiz.update(sysUser);
            return sysUser.getUserNo();
        } else {
            return sysUserBiz.createSysUserForOtherSystem(user);
        }
    }

    @Override
    public void addSysUserDepartment(Long sysUserId, List<Long> departmentIdList) {
        sysUserBiz.tx_updateSysUserDepartment(sysUserId, departmentIdList);
    }

    @Override
    public boolean isEmailOrMobileExist(String emailOrMobile, Integer type) {
        if (1 == type)
            return !sysUserBiz.isEmailExist(emailOrMobile, null);
        else if (2 == type)
            return !sysUserBiz.isMobileExist(emailOrMobile, null);
        return true;
    }

    @Override
    public List<Map<String, String>> queryAllDepartment() {
        List<Department> departmentList = departmentBiz.find(null, " departmentAvailable=0 order by sort desc ");
        return ObjectUtils.listObjToListMap(departmentList);
    }

    @Override
    public Map<String, String> queryDepartemntById(Long departmentId) {
        return ObjectUtils.objToMap(departmentBiz.findById(departmentId));
    }

    @Override
    public List<Map<String, String>> queryAllRole() {
        return ObjectUtils.listObjToListMap(roleBiz.find(null, " status=0"));
    }

    @Override
    public Map<String, String> queryRoleById(Long roleId) {
        return ObjectUtils.objToMap(roleBiz.findById(roleId));
    }

    @Override
    public List<Map<String, String>> querySysUser(Integer userType, Long linkId) {
        String where = " 1=1";
        if (ObjectUtils.isNotNull(userType)) {
            where += " and userType=" + userType;
        }
        if (ObjectUtils.isNotNull(linkId)) {
            where += " and linkId=" + linkId;
        }
        List<SysUser> sysUsers = sysUserBiz.find(null, where);
        if (ObjectUtils.isNotNull(sysUsers))
            return ObjectUtils.listObjToListMap(sysUsers);
        return new LinkedList<>();
    }

    @Override
    public Map<String, String> querySysUserById(Long userId) {
        return ObjectUtils.objToMap(sysUserBiz.findById(userId));
    }

    @Override
    public List<Map<String, String>> querySysUserByIds(List<Long> userIds) {
        if (!CollectionUtils.isEmpty(userIds)) {
            String ids = "";
            for (int i = 0; i < userIds.size(); i++) {
                if (i < userIds.size() - 1) {
                    ids += userIds.get(i) + ",";
                } else {
                    ids += userIds.get(i);
                }
            }
            return ObjectUtils.listObjToListMap(sysUserBiz.find(null, " id in(" + ids + ")"));
        }
        return null;
    }

    @Override
    public Map<String, Object> querySysUserInfoList(Pagination pagination, Long receiverId) {
        return smsHessianService.findInfoList(pagination, receiverId);
    }

    @Override
    public Map<String, Object> querySysUserList(Pagination pagination, String where) {
        List<SysUser> sysUserList = sysUserBiz.find(pagination, where);
        List<Map<String, String>> listMap = ObjectUtils.listObjToListMap(sysUserList);
        Map<String, String> objMap = ObjectUtils.objToMap(pagination);
        Map<String, Object> map = new HashMap<>();
        map.put("userList", listMap);
        map.put("pagination", objMap);
        return map;
    }

    @Override
    public Map<String, Object> querySysUserAndRoleForPage(Pagination pagination,
                                                          String where, Map<String, Object> params) {
        // 只有名称和用户类型查询时查询时
        List<SysUser> sysUserList = sysUserBiz.find(pagination, where);

        Object roleId = params.get("roleId");
        if (ObjectUtils.isNotNull(roleId)) {
            List<SysUserRole> roles = sysUserRoleBiz.find(null,
                    "roleId = " + roleId);

            String userIds = roles.stream()
                    .map(r -> String.valueOf(r.getUserId()))
                    .distinct()
                    .collect(Collectors.joining(",", "(", ")"));

            pagination = new Pagination();
            sysUserList = sysUserBiz.find(pagination, "id in" + userIds);

            Object userName = params.get("userName");
            if (ObjectUtils.isNotNull(userName) && org.apache.commons.lang3.StringUtils.isNotBlank(String.valueOf(userName))) {
                sysUserList = sysUserList.stream()
                        .filter(su -> userName.equals(su.getUserName()))
                        .collect(Collectors.toList());
            }
            Object userType = params.get("userType");
            if (ObjectUtils.isNotNull(userType) && org.apache.commons.lang3.StringUtils.isNotBlank(String.valueOf(userType))) {
                sysUserList = sysUserList
                        .stream().filter(su -> userType.equals(su.getUserType()))
                        .collect(Collectors.toList());
            }
        }

        List<Map<String, String>> listMap = Lists.newArrayList();
        for (SysUser sysUser : sysUserList) {
            Map<String, String> su = ObjectUtils.objToMap(sysUser);
            su.put("roleName", getRoleName(sysUser.getId()));
            listMap.add(su);
        }
        Map<String, String> objMap = ObjectUtils.objToMap(pagination);
        Map<String, Object> map = new HashMap<>();
        map.put("userList", listMap);
        map.put("pagination", objMap);
        return map;
    }

    private String getRoleName(long userId) {
        List<Long> longs = this.queryUserRoleByUserId(userId);
        if (longs.contains(92L)) {
            return "工程主管";
        } else if (longs.contains(32L)) {
            return "维修人员";
        } else {
            return "--";
        }
    }

    //根据
    @Override
    public List<Map<String, String>> queryDepartmentByTypeAndId(Long userId) {
        List<SysUser> sysUserList = sysUserBiz.find(null, " userType=3 and linkId=" + userId);
        if (sysUserList != null && sysUserList.size() > 0) {
            //根据条件查询部门信息
            List<SysUserDepartment> sysUserDepartmentList = sysUserDepartmentBiz.find(null, " departmentId =78 and userId=" + sysUserList.get(0).getId());
            return ObjectUtils.listObjToListMap(sysUserDepartmentList);
        } else {
            return null;
        }
    }

    @Override
    public String updateSysUser(Map<String, String> sysUserMap) {
        Long sysUserId = Long.valueOf(sysUserMap.getOrDefault("id", "0"));
        if (ObjectUtils.isNotNull(sysUserId)) {
            return sysUserBiz.update(sysUserMap);
        }
        return null;
    }

    @Override
    public void updateSysUserLink(String sysUserId, Long linkId) {
        SysUser sysUser = new SysUser();
        sysUser.setId(Long.valueOf(sysUserId));
        sysUser.setLinkId(linkId);
        sysUserBiz.update(sysUser);
    }


    /**
     * 删除系统用户
     *
     * @param userId
     */
    @Override
    public void deleteSysUserById(Long userId) {
        sysUserBiz.deleteById(userId);
    }

    @Override
    public void updateSysUserDepartment(Long sysUserId, List<Long> departmentIdList) {
        sysUserBiz.tx_updateSysUserDepartment(sysUserId, departmentIdList);
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
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return gson.fromJson(json, clazz);
    }

    /**
     * 根据用户ID获取该用户所属部门
     *
     * @param sysuserId
     * @return
     */
    @Override
    public Map<String, String> queryDepartmentBySysuserId(Long sysuserId) {
        List<SysUser> sysUserList = sysUserBiz.find(null, " id=" + sysuserId);
        if (sysUserList != null && sysUserList.size() > 0) {
            //根据条件查询部门信息
            Department department = departmentBiz.findById(sysUserList.get(0).getDepartmentId());
            return Optional.ofNullable(ObjectUtils.objToMap(department)).orElse(null);
        }
        return null;
    }

    /**
     * 获取一级部门id串
     *
     * @param
     * @return
     */
    @Override
    public String queryParentDepartment() {
        List<Department> departmentList = departmentBiz.find(null, "departmentAvailable=0 and parentId=0");
        if (ObjectUtils.isNotNull(departmentList) && departmentList.size() > 0) {
            AtomicReference<String> ids = new AtomicReference<>("");
            departmentList.forEach(e -> {
                ids.updateAndGet(v -> v + e.getId() + ",");
            });
            return ids.get().substring(0, ids.get().length() - 1);
        } else {
            return null;
        }
    }

    /**
     * 根据用户编号获取用户信息
     *
     * @param userno
     * @return
     */
    @Override
    public Map<String, String> querySysuserByUserno(String userno) {
        List<SysUser> sysUserList = sysUserBiz.find(null, " userNo='" + userno + "'");
        if (sysUserList != null && sysUserList.size() > 0) {
            return ObjectUtils.objToMap(sysUserList.get(0));
        } else {
            return null;
        }
    }

    @Override
    public List<Long> queryUserLeadersByUserId(Long sysUserId) {
        Map<String, String> department = this.queryDepartmentBySysuserId(sysUserId);
        if (department != null && department.size() > 0) {
            //根据用户查询的部门，所以部门id不可能为空
            String departmentId = department.get("id");
            Map<String, String> map = this.queryDepartemntById(Long.parseLong(departmentId));
            //如果没有父级部门，那么部门为自己
            if (map != null && map.size() > 0) {
                String parentId = map.get("parentId");
                if (StringUtils.isEmpty(parentId) || parentId.equals("0")) {
                    parentId = map.get("id");
                }
                List<SysUser> sysUsers = sysUserBiz.find(null, " departmentId = " + parentId);
                String userIds = "";
                if (sysUsers != null && sysUsers.size() > 0) {
                    List<Long> ids = sysUsers.stream()
                            .map(sysUser -> {
                                return sysUser.getId();
                            })
                            .collect(Collectors.toList());
                    return ids;
                }
            }
        }
        return null;
    }

    /**
     * 通过ID列表，查询管理员用户列表
     *
     * @param linkIds 用户ID列表
     * @return 用户列表List&lt;Map&lt;String,String&gt;&gt;
     */
    @Override
    public List<Map<String, String>> querySysUserByLinkIds(Integer userType, List<Long> linkIds) {
        if (!CollectionUtils.isEmpty(linkIds)) {
            String ids = "";
            for (int i = 0; i < linkIds.size(); i++) {
                if (i < linkIds.size() - 1) {
                    ids += linkIds.get(i) + ",";
                } else {
                    ids += linkIds.get(i);
                }
            }
            return ObjectUtils.listObjToListMap(sysUserBiz.find(null, " userType=3 and linkId in(" + ids + ")"));
        }
        return null;
    }

    /**
     * 根据手机号查询学员信息
     *
     * @param mobile
     * @return
     */
    @Override
    public List<Map<String, String>> queryStudentByMobile(String mobile) {
        /*List<SysUser> sysUserList = sysUserBiz.find(null, " userType=3 and mobile ='" + mobile + "'");
        if (sysUserList != null && sysUserList.size() > 0) {
            return ObjectUtils.objToMap(sysUserList.get(0));
        } else {
            return null;
        }*/
        return ObjectUtils.listObjToListMap(sysUserBiz.find(null, " userType=3 and mobile ='" + mobile + "'"));
    }

    /**
     * 删除系统用户
     *
     * @param id
     */
    @Override
    public void deleteSysUserId(String id) {
        sysUserBiz.deleteById(Long.parseLong(id));
    }


    /**
     * 添加部门
     *
     * @param department 系统用户
     * @return {@code null} 更新成功，否则失败信息
     * @since 2017-02-07
     */
    @Override
    public String addDepartment(Map<String, String> department) {
        Department department1 = gson.fromJson(gson.toJson(department), new TypeToken<Department>() {
        }.getType());
        departmentBiz.save(department1);
        return null;
    }

    /**
     * 修改部门
     */
    @Override
    public String updateDepartment(Map<String, String> department) {
        Department department1 = gson.fromJson(gson.toJson(department), new TypeToken<Department>() {
        }.getType());
        departmentBiz.update(department1);
        return null;
    }

    /**
     * 查询用户角色通过用户id
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public List<Long> queryUserRoleByUserId(Long userId) {
        List<SysUserRole> sysUserRoleList = sysUserRoleBiz.find(null, " userId = " + userId);
        List<Long> integerList = new ArrayList<>();
        sysUserRoleList.forEach(s -> integerList.add(s.getRoleId()));
        return integerList;
    }

    /**
     * 查询用户角色通过用户id
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public String queryUserRolesByUserId(Long userId) {
        List<SysUserRole> sysUserRoleList = sysUserRoleBiz.find(null, " userId = " + userId);
        List<Long> integerList = new ArrayList<>();
        sysUserRoleList.forEach(s -> integerList.add(s.getRoleId()));

        String roles = "";
        if (ObjectUtils.isNotNull(integerList)) {
            for (Long integer : integerList) {
                roles += integer.longValue() + ",";
            }
            if (roles != "" || roles != null) {
                roles = roles.substring(0, roles.length() - 1);
            }
        }
        return roles;
    }


    @Override
    public List<Map<String, String>> queryUserRoleInfoByUserId(Long userId) {
        List<SysUserRole> sysUserRoleList = sysUserRoleBiz.find(null, " userId = " + userId);
        if (sysUserRoleList != null && sysUserRoleList.size() > 0) {
            String roleIds = sysUserRoleList.stream()
                    .map(sysUserRole -> {
                        return sysUserRole.getRoleId() + "";
                    })
                    .collect(Collectors.joining(",", "(", ")"));
            List<Role> roles = roleBiz.find(null, " id in" + roleIds);
            return ObjectUtils.listObjToListMap(roles);
        }
        return null;
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        departmentBiz.deleteById(departmentId);
    }

    @Override
    public String queryUserIdsByRoleName(String roleName) {
        String sql = " roleName = '" + roleName + "'";
        List<Role> roles = roleBiz.find(null, sql);
        if (roles == null || roles.size() == 0) {
            return null;
        }
        String roleIds = roles
                .stream()
                .map(role -> {
                    return role.getId().toString();
                })
                .collect(Collectors.joining(",", "(", ")"));
        String roleSql = " roleId in " + roleIds;
        List<SysUserRole> sysUserRoles = sysUserRoleBiz.find(null, roleSql);
        if (sysUserRoles == null || sysUserRoles.size() == 0) {
            return null;
        }
        String userIds = sysUserRoles.stream()
                .map(sysUserRole -> {
                    return sysUserRole.getUserId().toString();
                })
                .collect(Collectors.joining(",", "", ""));
        return userIds;
    }

    @Override
    public String queryUserIdsByRoleId(Long roleId) {
        String sql = " roleId=" + roleId;
        List<SysUserRole> sysUserRoles = sysUserRoleBiz.find(null, sql);
        String userIds = sysUserRoles.stream().map(sysUserRole -> {
            return sysUserRole.getUserId().toString();
        }).collect(Collectors.joining(","));
        return userIds;
    }


    /**
     * 添加系统用户-角色中间表记录
     *
     * @param sysUserRoleMap
     * @return
     */
    @Override
    public void addSysUserRole(Map<String, String> sysUserRoleMap) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(Long.parseLong(sysUserRoleMap.get("roleId")));
        sysUserRole.setUserId(Long.parseLong(sysUserRoleMap.get("userId")));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        sysUserRole.setCreateTime(timestamp);
        sysUserRole.setUpdateTime(timestamp);
        sysUserRoleBiz.save(sysUserRole);
    }

    /**
     * 删除用户的某个角色
     *
     * @param sysUserRole
     * @return
     */
    @Override
    public int removeSysUserRole(Map<String, String> sysUserRole) {
        List<SysUserRole> sysUserRoleList = sysUserRoleBiz.find(null, " roleId=" + sysUserRole.get("roleId") + " and userId=" + sysUserRole.get("userId"));
        if (sysUserRoleList != null && sysUserRoleList.size() > 0) {
            sysUserRoleBiz.deleteById(sysUserRoleList.get(0).getId());
        }
        return 1;
    }

    /**
     * 修改单位用户的密码
     *
     * @param unitId
     * @param password
     * @return
     */
    @Override
    public int updateUnitUserPassword(Long unitId, String password) {
        List<SysUser> sysUserList = sysUserBiz.find(null, " status=0 and userType=5 and unitId=" + unitId);
        if (sysUserList != null && sysUserList.size() > 0) {
            for (SysUser sysUser : sysUserList) {
                sysUser.setPassword(MD5.getMD5(password));
                sysUserBiz.update(sysUser);
            }
        }
        return 1;
    }

    @Override
    public String queryLinkIdByUserStudentIds(String join) {
        List<SysUser> sysUsers = sysUserBiz.find(null, "linkId in (" + join + ")");
        List<Long> ids = new ArrayList<>();
        sysUsers.forEach(s -> ids.add(s.getId()));
        return Joiner.on(",").join(ids);
    }

    @Override
    public void deleteSysUserByUserNo(String result) {
        List<SysUser> sysUserList = sysUserBiz.find(null, "userNo=" + result);
        List<Long> ids = new ArrayList<>();
        sysUserList.forEach(s -> ids.add(s.getId()));
        sysUserBiz.deleteByIds(ids);
    }

    @Override
    public Map<String, Object> queryDepartmentList(Pagination pagination, Long sysUserId) {
        List<SysUser> userList = new ArrayList<>();
        SysUser sysUser = sysUserBiz.findById(sysUserId);
        List<Department> departmentList = departmentBiz.find(null, "1 = 1 and parentId =" + sysUser.getDepartmentId());
        if (ObjectUtils.isNotNull(departmentList)) {
            for (Department department : departmentList) {
                userList.addAll(sysUserBiz.find(null, "1 = 1 and status = 0 and departmentId =" + department.getId()));
            }
        }
        List<Map<String, String>> listMap = ObjectUtils.listObjToListMap(userList);
        Map<String, String> objMap = ObjectUtils.objToMap(pagination);
        Map<String, Object> map = new HashMap<>();
        map.put("userList", listMap);
        map.put("pagination", objMap);
        return map;
    }

    @Override
    public Map<String, Object> queryLeadershipList(Pagination pagination, String where) {
        List<SysUser> sysUserList = new ArrayList<>();
        List<SysUser> List = sysUserBiz.find(pagination, where);
        for (SysUser sysUser : List) {
            Department department = departmentBiz.findById(sysUser.getDepartmentId());
            if (ObjectUtils.isNotNull(department) && department.getParentId() < 1) {
                sysUserList.add(sysUser);
            }
        }
        List<Map<String, String>> listMap = ObjectUtils.listObjToListMap(sysUserList);
        Map<String, String> objMap = ObjectUtils.objToMap(pagination);
        Map<String, Object> map = new HashMap<>();
        map.put("userList", listMap);
        map.put("pagination", objMap);
        return map;
    }

    /**
     * @return
     * @Description:查询所有教职工通讯录信息
     * @author: jinshuo
     */
    @Override
    public List<Map<String, String>> queryAppTelephoneList() {
        List<SysUser> userList = sysUserBiz.find(null, " status=0 and userType=2 and mobile is not null and mobile!=''");
        List<Map<String, String>> dataList = Lists.newArrayList();
        if (ObjectUtils.isNotNull(userList)) {
            List<Map<String, String>> teacherList = hrHessianService.queryAllEmployee();
            Map<String, Object> teacherMap = new HashMap<>();
            List<Department> departmentList = departmentBiz.find(null, "1=1");
            Map<Long, Department> departmentMap = new HashMap<>();
            if (ObjectUtils.isNotNull(teacherList)) {
                for (Map<String, String> m : teacherList) {
                    teacherMap.put(m.get("id"), m);
                }
            }
            if (ObjectUtils.isNotNull(departmentList)) {
                for (Department d : departmentList) {
                    departmentMap.put(d.getId(), d);
                }
            }
            userList.forEach(e -> {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", e.getUserName());
                map.put("telephone", e.getMobile());
                map.put("email", e.getEmail());

                Department department = departmentMap.get(e.getDepartmentId());
                if (ObjectUtils.isNotNull(department)) {
                    map.put("unit", department.getDepartmentName());
                }

                Map<String, String> teacher = (Map<String, String>) teacherMap.get(e.getLinkId().toString());
                if (ObjectUtils.isNotNull(teacher)) {
                    map.put("position", teacher.get("presentPost"));
                    map.put("sex", teacher.get("sex"));
                }
                dataList.add(map);
            });
        }
        return dataList;
    }

    @Override
    public List<Map<String, String>> querySchoolRole() {
        String sql = " roleName = '分管校领导'";
        List<Role> roles = roleBiz.find(null, sql);
        if (roles == null || roles.size() == 0) {
            return null;
        }
        String roleIds = roles
                .stream()
                .map(role -> {
                    return role.getId().toString();
                })
                .collect(Collectors.joining(",", "(", ")"));
        String roleSql = " roleId in " + roleIds;
        List<SysUserRole> sysUserRoles = sysUserRoleBiz.find(null, roleSql);
        if (sysUserRoles == null || sysUserRoles.size() == 0) {
            return null;
        }
        List<SysUser> userList = Lists.newArrayList();
        sysUserRoles.forEach(e -> userList.add(sysUserBiz.findById(e.getUserId())));
        return ObjectUtils.listObjToListMap(userList);
    }

    @Override
    public String queryUnitByUnitId(Long unitId) {
        List<SysUser> sysUsers = sysUserBiz.find(null, " unitId=" + unitId);
        if (ObjectUtils.isNotNull(sysUsers)) {
            return sysUsers.get(0).getUserNo();
        }
        return "暂无";
    }

    @Override
    public void delSysUserById(Long id) {
        sysUserBiz.deleteById(id);
    }

    /**
     * 根据姓名查询用户信息
     *
     * @return
     */
    @Override
    public Map<String, String> getSysUserByName(String userName) {
        List<SysUser> sysUsers = sysUserBiz.find(null, " userName = '" + userName + "' and userType=2 order by id desc");
        if (sysUsers == null || sysUsers.size() == 0) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        map.put("userId", sysUsers.get(0).getId().toString());
        map.put("mobile", sysUsers.get(0).getMobile());
        return map;
    }

    /**
     * 获取用户手机号串
     *
     * @return
     */
    @Override
    public String getUserMobiles(List<String> userIdList) {
        List<SysUser> userList = sysUserBiz.find(null, " id in ( " + String.join(",", userIdList) + " ) ");
        if (userList != null && userList.size() > 0) {
            return userList.stream().filter(e -> e.getMobile() != null && !"".equals(e.getMobile())).map(e -> e.getMobile()).collect(Collectors.joining(","));
        } else {
            return null;
        }
    }

    /**
     * 根据编号修改用户name
     *
     * @param userNo
     * @param userName
     * @return
     */
    @Override
    public Long querySysuserByUsernoUpdateUserName(String userNo, String userName) {
        List<SysUser> sysUserList = sysUserBiz.find(null, " userNo='" + userNo + "'");
        if (sysUserList != null && sysUserList.size() > 0) {
            SysUser sysUser = new SysUser();
            sysUser.setId(sysUserList.get(0).getId());
            sysUser.setUserName(userName);
            sysUserBiz.update(sysUser);
            return sysUserList.get(0).getUnitId();
        } else {
            return 0l;
        }
    }

    /**
     * 获取当前登录用户是什么权限
     * 1-教务处录入
     * 2-学员处录入
     * 3-信息处录入
     *
     * @param sysUserId
     * @return
     */
    @Override
    public Integer getSysUserDepartment(Long sysUserId) {
        SysUser sysUser = sysUserBiz.findById(sysUserId);
        if (ObjectUtils.isNotNull(sysUser.getDepartmentId())) {
            Department department = departmentBiz.findById(sysUser.getDepartmentId());
            if (department.getDepartmentName().contains("信息")) {
                return 3;
            }
            if (department.getDepartmentName().contains("学员")) {
                return 2;
            }
            if (department.getDepartmentName().contains("教务")) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public String getUserIdsByDepartmentIds(String departmentIds) {
        List<SysUser> userList=sysUserBiz.find(null,"departmentId in ("+departmentIds+")");
        return userList.stream().map(e -> e.getId().toString()).collect(Collectors.joining(","));
    }

    /**
     * 单纯对用户权限进行存储
     *
     * @param sid    sid
     * @param userId 系统用户id
     * @return 系统用户关联的资源列表
     */
    public void setSysUserResourceList(String sid, Long userId) {
        if (StringUtils.isTrimEmpty(sid)) {
            return;
        }
        //从缓存中获取用户权限列表
        String stringResourceList = (String) redisCache.get(BaseCommonConstants.LOGIN_USER_AUTHORITY_KEY_PX + sid + userId);
        //获取用户权限列表
        List<Resource> resourceList = null;

        if (stringResourceList == null || stringResourceList.equals("null")) {
            stringResourceList = null;
        } else {
            //转换从缓存中获取的权限
            resourceList = gson.fromJson(stringResourceList, new TypeToken<List<Resource>>() {
            }.getType());
        }
        //如果缓存中没有用户权限存在，则从数据库中获取
        if (resourceList == null || resourceList.size() <= 0) {
            resourceList = sysUserBiz.queryUserResourceList(userId);
        }
        //再把用户权限列表转换成String
        stringResourceList = gson.toJson(resourceList);
        //把用户的权限放入缓存中（缓存24小时）
        redisCache.set(BaseCommonConstants.LOGIN_USER_AUTHORITY_KEY_PX + sid + userId, stringResourceList, 60 * 60 * 24);
    }

    /**
     * 获取权限资源列表并返回
     *
     * @param sid    sid
     * @param userId 系统用户id
     * @return 系统用户关联的资源列表
     */
    @Override
    public List<Map<String,String>> getSysUserResourceList(String sid, Long userId) {
        if (StringUtils.isTrimEmpty(sid)) {
            return null;
        }
        //从缓存中获取用户权限列表
        String stringResourceList = (String) redisCache.get(BaseCommonConstants.LOGIN_USER_AUTHORITY_KEY_PX + sid + userId);
        //获取用户权限列表
        List<Resource> resourceList = null;

        if (stringResourceList == null || stringResourceList.equals("null")) {
            stringResourceList = null;
        } else {
            //转换从缓存中获取的权限
            resourceList = gson.fromJson(stringResourceList, new TypeToken<List<Resource>>() {
            }.getType());
        }
        //如果缓存中没有用户权限存在，则从数据库中获取
        if (resourceList == null || resourceList.size() <= 0) {
            resourceList = sysUserBiz.queryUserResourceList(userId);
        }
        List<Map<String,String>> list = ObjectUtils.listObjToListMap(resourceList);
        return list;
    }
}


