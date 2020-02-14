package com.oa.biz.common;

import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oa.common.BaseHessianService;
import com.oa.entity.department.DepartMent;
import com.oa.entity.sysuser.SysUser;
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
    private BaseHessianService hessianService;

    @Autowired
    private SmsHessianService smsHessianService;
    @Autowired
    private BaseHessianService baseHessianService;

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
     * 更新系统用户
     *
     * @param sysUser 系统用户.不更新的字段勿传
     */
    public String updateSysUser(SysUser sysUser) {
        Map<String, String> map = ObjectUtils.objToMap(sysUser);
        return baseHessianService.updateSysUser(map);
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
            sysUsers = users.stream()
                    .map(SysUser::new)
                    .filter(sysUser1 -> {
                        return name == null || (!StringUtils.isEmpty(sysUser1.getUserName()) && sysUser1.getUserName().indexOf(name) > -1);
                    })
                    .collect(Collectors.toList());
        }
        return sysUsers;
    }

    public List<SysUser> getSysUserList(Long userId,String name) {
        List<SysUser> sysUsers = null;
        Map<String, Object> maps = hessianService.queryDepartmentList(null, userId);
        if (maps != null && maps.get("userList") != null) {
            List<Map<String, String>> users = (List<Map<String, String>>) maps.get("userList");
            if(ObjectUtils.isNotNull(name)){
                sysUsers = users.stream().filter(e->e.get("userName")!=null && e.get("userName").indexOf(name)>-1)
                        .map(SysUser::new)
                        .collect(Collectors.toList());
            }else {
                sysUsers = users.stream()
                        .map(SysUser::new)
                        .collect(Collectors.toList());
            }
        }
        return sysUsers;
    }

    public SysUser getSysUserById(Long id) {
        Map<String, String> map = hessianService.querySysUserById(id);
        return map == null ? null : new SysUser(map);
    }

    /**
     * @Description:消息管理,收件箱
     * @author: ccl
     * @Param: [pagination, receiverId, status]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-02-20
     */
    public Map<String, Object> querySysUserInfoList(Pagination pagination, Long receiverId, Integer status) {
        return smsHessianService.findInfoList(pagination, receiverId, status);
    }


    /**
     * @Description:消息管理,发件箱
     * @author: ccl
     * @Param: [pagination, receiverId, status]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-02-20
     */

    public Map<String, Object> querySysUserSendList(Pagination pagination, Long receiverId) {
        return smsHessianService.findSendInfoList(pagination, receiverId);
    }


    /**
     * @Description:撒谎从农户
     * @author: ccl
     * @Param: [infoUserReceiveId, downright]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-02-20
     */
    public Map<String, Object> deleteInfo(String infoUserReceiveId, Boolean downright) {
        return smsHessianService.deleteInfo(infoUserReceiveId, downright);
    }


    public Map<String, Object> recoverInfoInRecycleBin(Long infoUserReceiveId) {
        return smsHessianService.recoverInfoInRecycleBin(infoUserReceiveId);
    }


    public Map<String, Object> findSendInfoDetail(Long infoId) {
        return smsHessianService.findSendInfoDetail(infoId);
    }


    public Map<String, String> findInfoDetail(Long id) {
        return smsHessianService.findInfoDetail(id);
    }

    /**
     * 根据用户id查询用户所在的部门
     * @param sysUserId
     * @return
     */
    public Map<String, String> queryDepartmentBySysUserId(Long sysUserId) {
        return hessianService.queryDepartmentBySysuserId(sysUserId);
    }

    /**
     * 根据当前系统用户查询他的领导，如果已经为最大领导，那么领导为自己
     * @param sysUserId
     * @return
     */
    public List<Long> queryUserLeadersByUserId(Long sysUserId) {
        return hessianService.queryUserLeadersByUserId(sysUserId);
    }

    /**
     * 根据角色名查询对应的用户
     * @param roleName
     * @return
     */
    public String queryUserIdsByRoleName(String roleName) {
        return hessianService.queryUserIdsByRoleName(roleName);
    }

    public List<Map<String, String>> queryUserRoleInfoByUserId(Long userId) {
        return hessianService.queryUserRoleInfoByUserId(userId);
    }


    public List<SysUser> queryLeadershipList(String whereSql,String name) {
        List<SysUser> sysUsers = null;
        Map<String, Object> maps = hessianService.queryLeadershipList(null, whereSql);
        if (maps != null && maps.get("userList") != null) {
            List<Map<String, String>> users = (List<Map<String, String>>) maps.get("userList");
            if(ObjectUtils.isNotNull(name)){
                sysUsers = users.stream().filter(e->e.get("userName")!=null && e.get("userName").indexOf(name)>-1)
                        .map(SysUser::new)
                        .collect(Collectors.toList());
            }else {
                sysUsers = users.stream()
                        .map(SysUser::new)
                        .collect(Collectors.toList());
            }
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
        return hessianService.queryUserRoleByUserId(userId);
   }


    /**
     * 分页查询用户列表
     * @param pagination 分页条件对象
     * @param where 查询条件
     * @return  用户列表及分页对象Map<String,Object>
     */
    public Map<String,Object> querySysUserList(Pagination pagination, String where){
        return hessianService.querySysUserList(pagination,where);
    }


    /**
     * @Description:查询所有教职工通讯录信息
     * @author: jinshuo
     * @return
     */
    public List<Map<String, String>> queryAppTelephoneList(){
        return hessianService.queryAppTelephoneList();
    }

    /**
     * 查询所有分管校领导
     * @return
     */
    public List<Map<String, String>> querySchoolRole() {
        return hessianService.querySchoolRole();
    }

    /**
     * 获取一级部门id串
     * @return
     */
    public String queryParentDepartment() {
        return hessianService.queryParentDepartment();
    }

    /**
     * 添加系统用户
     */
    public String addSysUser(SysUser sysUser) {
        Map<String, String> map = ObjectUtils.objToMap(sysUser);
        return baseHessianService.addSysUser(map);
    }

    /**
     * 删除系统用户
     * @param userId
     */
    public void deleteSysUserById(Long userId){
        baseHessianService.deleteSysUserById(userId);
    }

    /**
     * 根据姓名查询用户信息
     * @return
     */
    public Map<String, String> getSysUserByName(String userName) {
        return hessianService.getSysUserByName(userName);
    }


    /**
     * 根据部门ids查询用户ids
     * @param departmentIds
     * @return
     */
    public String getUserIdsByDepartmentIds(String departmentIds){
        return hessianService.getUserIdsByDepartmentIds(departmentIds);
    }
}
