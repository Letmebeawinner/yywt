package com.app.biz.common;

import com.app.entity.Resource;
import com.app.entity.SysUser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
*Description:提供外部调用userService
*@author:FangTao
*@date 2020/2/1019:54
*@params
*@return
**/
public interface SysUserService{
    /**
     * 查询系统用户关联的资源列表
     *
     * @param sid    sid
     * @param userId 系统用户id
     * @return 系统用户关联的资源列表
     */
   List<Resource> getSysUserResourceList(String sid, Long userId);

    /**
     * 添加用户，走事务，因为添加成功后要修改该用户编号
     *
     * @param sysUser 用户属性对象
     * @return {@code null}添加成功；否则失败信息
     */
   String tx_createSysUser(SysUser sysUser);

    /**
     * 添加用户，走事务，因为添加成功后要修改该用户编号
     *
     * @param sysUser 用户属性对象
     * @return {@code null}添加成功；否则失败信息
     */
    String createSysUserForOtherSystem(SysUser sysUser);

    /**
     * 批量添加单位用户，走事务，因为添加成功后要修改该用户编号
     *
     * @param sysUser 用户属性对象
     * @return {@code null}添加成功；否则失败信息
     */
    void tx_batchCreateSysUser(SysUser sysUser);
    /**
     * 批量添加用户，走事务，因为添加成功后要修改该用户编号
     *
     * @param sysUser 用户属性对象
     * @return {@code null}添加成功；否则失败信息
     */
    void tx_batchNewSysUser(SysUser sysUser, String rolesId);


    /**
     * 添加系统用户
     *
     * @param sysUser 系统用户
     * @return 添加结果
     */
   String tx_createSysUser(Map<String, String> sysUser);


    /**
     * 判断邮箱是否被使用过
     *
     * @param email  邮箱号
     * @param userId 用户ID，添加时可传0，修改时传用户ID
     * @return true未被使用，false已被使用
     */
    boolean isEmailExist(String email, Long userId);

    /**
     * 判断手机号是否被使用过
     *
     * @param mobile 手机号
     * @param userId 用户ID，添加时可传0，修改时传用户ID
     * @return true未被使用，false已被使用
     */
    boolean isMobileExist(String mobile, Long userId);

    /**
     * @param sysUser  系统用户
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @Description: 设置用户登录
     * @author: s.li
     * @since : 2016/12/13
     */
    void tx_setUserLogin(SysUser sysUser, HttpServletRequest request, HttpServletResponse response);
    /**
     * 获取用户权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
   List<Resource> queryUserResourceList(Long userId);


    /**
     * 更新系统用户关联的部门
     *
     * @param userId           系统用户id
     * @param departmentIdList 部门id
     * @since 2017-02-06
     */
   void tx_updateSysUserDepartment(Long userId, List<Long> departmentIdList);
    /**
     * 更新系统用户关联的部门
     *
     * @param userId           系统用户id
     * @param roleIdList 部门id
     * @since 2017-02-06
     */
   void tx_updateSysUserRole(Long userId, List<Long> roleIdList);



    /**
     * 更新系统用戶关联的部门  先刪除  再添加
     *
     * @param userId        用户id
     * @param departmentIds 部门id.使用英文","分割
     */
    void tx_updateSysUserDepartment(Long userId, String departmentIds);

    /**
     * 更新系统用戶关联的角色 先刪除  再添加
     *
     * @param userId        用户id
     * @param roleIds 部门id.使用英文","分割
     */
   void tx_updateSysUserRole(Long userId, String roleIds);


    /**
     * 更新系统用户
     *
     * @param sysUserMap 系统用户
     * @return {@code null} 更新成功，否则失败信息
     * @since 2017-02-07
     */
    String update(Map<String, String> sysUserMap);


    /**
     * 验证系统用户信息是否合法
     *
     * @param sysUser 系统用户信息
     * @return {@code null} 信息合法；否则不合法信息
     */
    String verify(SysUser sysUser);

    String batchImportSysUser(MultipartFile myFile, HttpServletRequest request) throws Exception;
    /**
     * @param cell
     * @return
     * @Description 获得Hsscell内容
     */
   String getCellValue(HSSFCell cell);

   List<Map<String,String>> findUserList(String sql);
}
