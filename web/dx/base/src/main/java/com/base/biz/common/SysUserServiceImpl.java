package com.base.biz.common;

import com.a_268.base.constants.BaseCommonConstants;
import com.a_268.base.core.BaseBiz;
import com.a_268.base.redis.RedisCache;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.MD5;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.WebUtils;
import com.base.biz.connection.DepartmentRoleBzi;
import com.base.biz.connection.RoleResourceBiz;
import com.base.biz.connection.SysUserDepartmentBiz;
import com.base.biz.department.DepartmentBiz;
import com.base.biz.permission.ResourceBiz;
import com.base.biz.role.RoleBiz;
import com.base.biz.role.SysUserRoleBiz;
import com.base.biz.user.SysUserBiz;
import com.base.dao.user.SysUserDao;
import com.base.entity.connection.RoleResource;
import com.base.entity.connection.SysUserDepartment;
import com.base.entity.permission.Resource;
import com.base.entity.role.Role;
import com.base.entity.role.SysUserRole;
import com.base.entity.user.SysUser;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 管理员用户Biz
 *
 * @author s.li
 */
@Service
public class SysUserServiceImpl implements SysUserService{
    private RedisCache redisCache = RedisCache.getInstance();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Autowired
    private ResourceBiz resourceBiz;
    @Autowired
    private SysUserDepartmentBiz sysUserDepartmentBiz;
    @Autowired
    private DepartmentRoleBzi departmentRoleBzi;
    @Autowired
    private DepartmentBiz departmentBiz;
    @Autowired
    private SysUserRoleBiz sysUserRoleBiz;
    @Autowired
    private RoleResourceBiz roleResourceBiz;
    @Autowired
    private RoleBiz roleBiz;
    @Autowired
    private SysUserBiz sysUserBiz;

    /**
     * 查询系统用户关联的资源列表
     *
     * @param sid    sid
     * @param userId 系统用户id
     * @return 系统用户关联的资源列表
     */
    @Override
    public List<Resource> getSysUserResourceList(String sid, Long userId) {
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
            resourceList = this.queryUserResourceList(userId);
        }
        //再把用户权限列表转换成String
        stringResourceList = gson.toJson(resourceList);
        //把用户的权限放入缓存中（缓存24小时）
        redisCache.set(BaseCommonConstants.LOGIN_USER_AUTHORITY_KEY_PX + sid + userId, stringResourceList, 60 * 60 * 24);
        return resourceList;
    }

    /**
     * 添加用户，走事务，因为添加成功后要修改该用户编号
     *
     * @param sysUser 用户属性对象
     * @return {@code null}添加成功；否则失败信息
     */
    @Override
    public String tx_createSysUser(SysUser sysUser) {
        String verify = verify(sysUser);
        if (verify != null) return verify;
        if(Integer.valueOf(5).equals(sysUser.getUserType())){
            if(sysUser.getUnitId()==null||Long.valueOf(0).equals(sysUser.getUnitId())){
                return "请填写单位ID";
            }
        }
        sysUser.setStatus(0);// 默认为1
        String _password = MD5.getMD5(sysUser.getPassword());
        sysUser.setPassword(_password);
//        sysUser.setUserType(1);
        if (sysUser.getUserType() == null || sysUser.getUserType().equals(0)) {
            sysUser.setUserType(1);
        }
        //添加用户
        sysUserBiz.save(sysUser);
        DecimalFormat df = new DecimalFormat("000000");
        String userNo = df.format(sysUser.getId());
        sysUser.setAnotherName(sysUser.getUserName());
        sysUser.setUserNo(userNo);
        //修改用户编号
        sysUserBiz.update(sysUser);
        return "操作成功!";
    }

    /**
     * 添加用户，走事务，因为添加成功后要修改该用户编号
     *
     * @param sysUser 用户属性对象
     * @return {@code null}添加成功；否则失败信息
     */
    @Override
    public String createSysUserForOtherSystem(SysUser sysUser) {
        String verify = verify(sysUser);
        if (verify != null) return verify;
        if(Integer.valueOf(4).equals(sysUser.getUserType())){
            if(sysUser.getUnitId()==null||Long.valueOf(0).equals(sysUser.getUnitId())){
                return "请填写单位ID";
            }
        }
        sysUser.setStatus(0);// 默认为1
        String _password = MD5.getMD5(sysUser.getPassword());
        sysUser.setPassword(_password);
//        sysUser.setUserType(1);
        if (sysUser.getUserType() == null || sysUser.getUserType().equals(0)) {
            sysUser.setUserType(1);
        }
        //添加用户
        sysUserBiz.save(sysUser);
        DecimalFormat df = new DecimalFormat("000000");
        String userNo = df.format(sysUser.getId());
        sysUser.setAnotherName(sysUser.getUserName());
        sysUser.setUserNo(userNo);
        //修改用户编号
        sysUserBiz.update(sysUser);
        return sysUser.getUserNo();
    }

    /**
     * 批量添加单位用户，走事务，因为添加成功后要修改该用户编号
     *
     * @param sysUser 用户属性对象
     * @return {@code null}添加成功；否则失败信息
     */
    @Override
    public void tx_batchCreateSysUser(SysUser sysUser) {
        sysUser.setStatus(0);// 默认为1
        String _password = MD5.getMD5(sysUser.getPassword());
        sysUser.setPassword(_password);
        if (sysUser.getUserType() == null || sysUser.getUserType().equals(0)) {
            sysUser.setUserType(1);
        }
        //添加用户
        sysUserBiz.save(sysUser);
        DecimalFormat df = new DecimalFormat("000000");
        String userNo = df.format(sysUser.getId());
        sysUser.setUserNo(userNo);
        //修改用户编号
        sysUserBiz.update(sysUser);
        this.tx_updateSysUserDepartment(sysUser.getId(), String.valueOf(80));
    }


    /**
     * 批量添加用户，走事务，因为添加成功后要修改该用户编号
     *
     * @param sysUser 用户属性对象
     * @return {@code null}添加成功；否则失败信息
     */
    @Override
    public void tx_batchNewSysUser(SysUser sysUser, String rolesId) {
        sysUser.setStatus(0);// 默认为1
        String _password = MD5.getMD5(sysUser.getPassword());
        sysUser.setPassword(_password);
        if (sysUser.getUserType() == null || sysUser.getUserType().equals(0)) {
            sysUser.setUserType(1);
        }
        //添加用户
        sysUserBiz.save(sysUser);
        DecimalFormat df = new DecimalFormat("000000");
        String userNo = df.format(sysUser.getId());
        sysUser.setUserNo(userNo);
        //修改用户编号
        sysUserBiz.update(sysUser);
        this.tx_updateSysUserRole(sysUser.getId(), rolesId);
    }


    /**
     * 添加系统用户
     *
     * @param sysUser 系统用户
     * @return 添加结果
     */
    @Override
    public String tx_createSysUser(Map<String, String> sysUser) {
        SysUser user = gson.fromJson(gson.toJson(sysUser), new TypeToken<SysUser>() {
        }.getType());
        return tx_createSysUser(user);
    }


    /**
     * 判断邮箱是否被使用过
     *
     * @param email  邮箱号
     * @param userId 用户ID，添加时可传0，修改时传用户ID
     * @return true未被使用，false已被使用
     */
    @Override
    public boolean isEmailExist(String email, Long userId) {
        String whereSql_1 = " email='" + email.trim() + "'";
        if (userId != null && userId > 0) {
            whereSql_1 += "and id !=" + userId;
        }
        List<SysUser> userList = sysUserBiz.find(null, whereSql_1);
        return CollectionUtils.isEmpty(userList);
    }

    /**
     * 判断手机号是否被使用过
     *
     * @param mobile 手机号
     * @param userId 用户ID，添加时可传0，修改时传用户ID
     * @return true未被使用，false已被使用
     */
    @Override
    public boolean isMobileExist(String mobile, Long userId) {
        String whereSql_1 = " mobile='" + mobile.trim() + "'";
        if (userId != null && userId > 0) {
            whereSql_1 += "and id !=" + userId;
        }
        List<SysUser> userList = sysUserBiz.find(null, whereSql_1);
        return CollectionUtils.isEmpty(userList);
    }

    /**
     * @param sysUser  系统用户
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @Description: 设置用户登录
     * @author: s.li
     * @since : 2016/12/13
     */
    @Override
    public void tx_setUserLogin(SysUser sysUser, HttpServletRequest request, HttpServletResponse response) {
        //把用户信息转换成String
        String stringUser = gson.toJson(sysUser);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //设置用户信息到缓存中（缓存24小时）
        redisCache.set(uuid, stringUser, 60 * 60 * 24);
        //设置登录Key
        WebUtils.setCookie(response, BaseCommonConstants.LOGIN_KEY, uuid, 1);//缓存一天
        //设置用户权限到缓存中
        getSysUserResourceList(uuid, sysUser.getId());
    }

    /**
     * 获取用户权限列表
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public List<Resource> queryUserResourceList(Long userId) {
        //通过部门ID串，查询部门角色关联数据
        List<SysUserRole> drList = sysUserRoleBiz.find(null, "userId="+userId);
        if (drList != null && drList.size() > 0) {
            String roleIds = "";
            for (int i = 0; i < drList.size(); i++) {
                if (i < drList.size() - 1) {
                    roleIds += drList.get(i).getRoleId() + ",";
                } else {
                    roleIds += drList.get(i).getRoleId();
                }
            }
            List<Role> roleList = roleBiz.find(null, " id in (" + roleIds + ") and status=1");
            roleIds = "";
            if (!CollectionUtils.isEmpty(roleList)) {
                for (int i = 0; i < roleList.size(); i++) {
                    if (i < roleList.size() - 1) {
                        roleIds += roleList.get(i).getId() + ",";
                    } else {
                        roleIds += roleList.get(i).getId();
                    }
                }
                //查询角色相关的权限数据
                List<RoleResource> rrList = roleResourceBiz.find(null, " roleId in (" + roleIds + ")");
                if (rrList != null && rrList.size() > 0) {
                    String resourceIds = "";
                    for (int i = 0; i < rrList.size(); i++) {
                        if (i < rrList.size() - 1) {
                            resourceIds += rrList.get(i).getResourceId() + ",";
                        } else {
                            resourceIds += rrList.get(i).getResourceId();
                        }
                    }
                    //通过权限ID串，查询权限列表
                    return resourceBiz.find(null, " id in (" + resourceIds + ") order by resourceOrder desc");
                }
            }
        }
        return null;
    }


    /**
     * 更新系统用户关联的部门
     *
     * @param userId           系统用户id
     * @param departmentIdList 部门id
     * @since 2017-02-06
     */
    @Override
    public void tx_updateSysUserDepartment(Long userId, List<Long> departmentIdList) {
        String where = " userId = " + userId;
        List<SysUserDepartment> list = sysUserDepartmentBiz.find(null, where);
        if (ObjectUtils.isNotNull(list)) {
            List<Long> ids = list.stream()
                    .map(SysUserDepartment::getId)
                    .collect(Collectors.toList());
            sysUserDepartmentBiz.deleteByIds(ids);
        }
        if (ObjectUtils.isNotNull(departmentIdList)) {
            list = departmentIdList.stream().map(i -> {
                SysUserDepartment department = new SysUserDepartment();
                department.setDepartmentId(i);
                department.setUserId(userId);
                return department;
            }).collect(Collectors.toList());
            sysUserDepartmentBiz.saveBatch(list);
        }
    }


    /**
     * 更新系统用户关联的部门
     *
     * @param userId           系统用户id
     * @param roleIdList 部门id
     * @since 2017-02-06
     */
    @Override
    public void tx_updateSysUserRole(Long userId, List<Long> roleIdList) {
        String where = " userId = " + userId;
        List<SysUserRole> list = sysUserRoleBiz.find(null, where);
        if (ObjectUtils.isNotNull(list)) {
            List<Long> ids = list.stream()
                    .map(SysUserRole::getId)
                    .collect(Collectors.toList());
            sysUserRoleBiz.deleteByIds(ids);
        }
        if (ObjectUtils.isNotNull(roleIdList)) {
            list = roleIdList.stream().map(i -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(i);
                sysUserRole.setUserId(userId);
                return sysUserRole;
            }).collect(Collectors.toList());
            sysUserRoleBiz.saveBatch(list);
        }
    }




    /**
     * 更新系统用戶关联的部门  先刪除  再添加
     *
     * @param userId        用户id
     * @param departmentIds 部门id.使用英文","分割
     */
    @Override
    public void tx_updateSysUserDepartment(Long userId, String departmentIds) {
        if (StringUtils.isTrimEmpty(departmentIds)) {
            return;
        }
        List<Long> departmentIdList = Stream.of(departmentIds.split(","))
                .map(Long::valueOf)
                .filter(id -> id > 0)
                .distinct().collect(Collectors.toList());
        tx_updateSysUserDepartment(userId, departmentIdList);
    }

    /**
     * 更新系统用戶关联的角色 先刪除  再添加
     *
     * @param userId        用户id
     * @param roleIds 部门id.使用英文","分割
     */
    @Override
    public void tx_updateSysUserRole(Long userId, String roleIds) {
        if (StringUtils.isTrimEmpty(roleIds)) {
            return;
        }
        List<Long> roleList = Stream.of(roleIds.split(","))
                .map(Long::valueOf)
                .filter(id -> id > 0)
                .distinct().collect(Collectors.toList());
        tx_updateSysUserRole(userId, roleList);
    }




    /**
     * 更新系统用户
     *
     * @param sysUserMap 系统用户
     * @return {@code null} 更新成功，否则失败信息
     * @since 2017-02-07
     */
    @Override
    public String update(Map<String, String> sysUserMap) {
        SysUser sysUser = gson.fromJson(gson.toJson(sysUserMap), new TypeToken<SysUser>() {
        }.getType());
        String verify = verify(sysUser);
        if (verify != null) return verify;
        sysUserBiz.update(sysUser);
        return null;
    }


    /**
     * 验证系统用户信息是否合法
     *
     * @param sysUser 系统用户信息
     * @return {@code null} 信息合法；否则不合法信息
     */
    @Override
    public String verify(SysUser sysUser) {
        //验证用户名
        String username = sysUser.getUserName().trim();
        if (StringUtils.isTrimEmpty(username)) return "用户名不能为空";
        if (username.length() < 2 || username.length() > 20) return "用户名长度为2~20个字符";

        String where = "";
        List<SysUser> sysUsers = null;
        // 验证邮箱
        String email = sysUser.getEmail();
        //邮箱可以为空
        if (!StringUtils.isTrimEmpty(email)) {
            if (!StringUtils.isEmail(email)) return "邮箱格式不正确";
            where = " email='" + email + "'";
            sysUsers = sysUserBiz.find(null, where);
            if (!sysUser.getUserType().equals(3)) {
                if (!isSame(sysUsers, sysUser.getId())) return "该邮箱已注册";
            }
        }
        // 验证手机
        String mobile = sysUser.getMobile();
//        if (StringUtils.isTrimEmpty(mobile)) return "手机不能为空";
//        if (!StringUtils.isMobile(mobile)) return "手机格式不正确";
        return null;
    }

    /**
     * 判断两个系统用户是否相同
     *
     * @param list 查询出的系统用户，只取第一个
     * @param id   另一个系统用户的id
     * @return {@code true} 相同, 否则不同
     */
    private boolean isSame(List<SysUser> list, Long id) {
        if (ObjectUtils.isNotNull(list)) {
            if (list.get(0).getId().equals(id))
                return true;
        } else return true;
        return false;
    }


    @Override
    public String batchImportSysUser(MultipartFile myFile, HttpServletRequest request) throws Exception {

        StringBuffer msg = new StringBuffer();
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        for (int i = 1; i < rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                String userName = getCellValue(row.getCell((short) 0));//用户名
                if (StringUtils.isTrimEmpty(userName)) {
                    msg.append("第" + i + "行用户名不能为空;");
                    continue;
                }
                String password = getCellValue(row.getCell((short) 1));//密码
                if (StringUtils.isTrimEmpty(password)) {
                    password = "111111";
                }
                String rolesId=getCellValue(row.getCell((short)2));
                String userType=getCellValue(row.getCell((short)3));
                if(userType!=null){
                    Pattern pattern = Pattern.compile("[0-9]*");
                    Matcher isNum = pattern.matcher(userType);
                    if( !isNum.matches() || Integer.parseInt(userType)<1 || Integer.parseInt(userType)>6){
                        msg.append("第" + i + "行用户类型不正确;");
                        continue;
                    }
                }else {
                    userType="3";
                }
                SysUser sysUser = new SysUser();
                sysUser.setUserName(userName.trim());
                sysUser.setPassword(password);
                sysUser.setUserType(Integer.parseInt(userType));
                this.tx_batchNewSysUser(sysUser,rolesId);
            }
        }
        return msg.toString();
    }


    /**
     * @param cell
     * @return
     * @Description 获得Hsscell内容
     */
    @Override
    public String getCellValue(HSSFCell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    DecimalFormat df = new DecimalFormat("0");
                    value = df.format(cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue().trim();
                    break;
                default:
                    value = "";
                    break;
            }
        }
        return value.trim();
    }

    @Override
    public  List<Map<String,String>> findUserList(String sql){
        List<SysUser> sysUser = sysUserBiz.find(null, sql);
        List<Map<String,String>> map = ObjectUtils.listObjToListMap(sysUser);
        return map;
    };
}
