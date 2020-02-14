package com.base.controller.user;


import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.enums.StateEnum;
import com.a_268.base.util.*;
import com.base.biz.common.HrHessianService;
import com.base.biz.common.JiaoWuHessianService;
import com.base.biz.connection.SysUserDepartmentBiz;
import com.base.biz.department.DepartmentBiz;
import com.base.biz.unit.UnitBiz;
import com.base.biz.user.SysUserBiz;
import com.base.entity.department.Department;
import com.base.entity.unit.Unit;
import com.base.entity.user.SysUser;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员用户Controller
 *
 * @author s.li
 */
@Controller
@RequestMapping("/admin/base/sysuser")
public class SysUserController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserBiz sysUserBiz;
    @Autowired
    private DepartmentBiz departmentBiz;
    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;
    @Autowired
    private HrHessianService hrHessianService;
    @Autowired
    private SysUserDepartmentBiz sysuserdepartmentBiz;
    @Autowired
    private UnitBiz unitBiz;


    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"syUser"})
    public void initSysUser(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("syUser.");
    }


    /**
     * 查询用户列表
     *
     * @param request    HttpServletRequest
     * @param pagination 分页条件
     * @return 用户列表页面
     */
    @RequestMapping("/queryUserList")
    public String queryUserList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = " 1=1";
            // 状态
            String status = request.getParameter("status");
            request.setAttribute("status", status);
            if (!StringUtils.isTrimEmpty(status)) {
                whereSql += " and status='" + status + "'";
            }
            // 用户类型
            String userType = request.getParameter("userType");
            if (!StringUtils.isTrimEmpty(userType)) {
                whereSql += " and userType=" + userType;
            }
            request.setAttribute("userType", userType);
            // 查询条件
            String check = request.getParameter("check");
            request.setAttribute("check", check);
            if (!StringUtils.isTrimEmpty(check)) {
                whereSql += " and userName like '%" + check + "%' or email = '" + check + "' or mobile ='" + check + "'";
            }
            // 查询部门
            List<Department> departmentList = departmentBiz.find(null, " 1=1 and departmentAvailable=0");
            request.setAttribute("departmentList", gson.toJson(departmentList));
            pagination.setRequest(request);
            // 管理员用户列表
            List<SysUser> userList = sysUserBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(userList)) {
                for (SysUser sysUser : userList) {
                    if (sysUser.getDepartmentId() != null) {
                        Department department = departmentBiz.findById(sysUser.getDepartmentId());
                        sysUser.setDepartmentName(department.getDepartmentName());
                    }
                }
            }
            request.setAttribute("userList", userList);
        } catch (Exception e) {
            logger.error("queryUserList()--error", e);
        }
        return "/sysuser/sysuser-list";
    }


    /**
     * 查询本部门用户列表
     *
     * @param request    HttpServletRequest
     * @param pagination 分页条件
     * @return 用户列表页面
     */
    @RequestMapping("/queryCurDepartUserList")
    public String queryCurDepartUserList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = " 1=1 and departmentId!=''";
            String check = request.getParameter("check");//查询条件
            request.setAttribute("check", check);
            if (!StringUtils.isTrimEmpty(check)) {
                whereSql += " and userName like '%" + check + "%' or email = '" + check + "' or mobile ='" + check + "'";
            }
            String status = request.getParameter("status");//状态
            request.setAttribute("status", status);
            if (!StringUtils.isTrimEmpty(status)) {
                whereSql += " and status='" + status + "'";
            }

            String userType = request.getParameter("userType");
            if (!StringUtils.isTrimEmpty(userType)) {
                whereSql += " and userType=" + userType;
            }

            //获取当前登录人id
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (ObjectUtils.isNotNull(userMap)) {
                String departmentId = userMap.get("departmentId").toString();
                if (!StringUtils.isTrimEmpty(departmentId)) {
                    List<Department> departmentList = departmentBiz.find(null, " id=" + departmentId + " or parentId=" + departmentId);
                    if (ObjectUtils.isNotNull(departmentList)) {
                        String departmentIds = "";
                        for (Department department : departmentList) {
                            departmentIds += department.getId() + ",";
                        }
                        if (!StringUtils.isTrimEmpty(departmentIds)) {
                            departmentIds = departmentIds.substring(0, departmentIds.length() - 1);
                            whereSql += " and departmentId in (" + departmentIds + ")";
                        }
                    }
                }
            }
            request.setAttribute("userType", userType);

            pagination.setRequest(request);
            List<SysUser> userList = sysUserBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(userList)) {
                for (SysUser sysUser : userList) {
                    if (sysUser.getDepartmentId() != null) {
                        Department department = departmentBiz.findById(sysUser.getDepartmentId());
                        sysUser.setDepartmentName(department.getDepartmentName());
                    }
                }
            }
            request.setAttribute("userList", userList);
        } catch (Exception e) {
            logger.error("queryUserList()--error", e);
        }
        return "/sysuser/current-sysuser-list";
    }


    /**
     * 查询用户列表
     *
     * @param request    HttpServletRequest
     * @param pagination 分页条件
     * @return 用户列表页面
     */
    @RequestMapping("/queryEmployeeList")
    public String queryEmployeeList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = " 1=1 and departmentId!=''";
            String check = request.getParameter("check");//查询条件
            request.setAttribute("check", check);
            if (!StringUtils.isTrimEmpty(check)) {
                whereSql += " and ( userName like '%" + check + "%' or email = '" + check + "' or mobile ='" + check + "' )";
            }
            String status = request.getParameter("status");//状态
            request.setAttribute("status", status);
            if (!StringUtils.isTrimEmpty(status)) {
                whereSql += " and status='" + status + "'";
            }
            String departmentId = request.getParameter("department");
            if (!StringUtils.isTrimEmpty(departmentId)) {
                whereSql += " and departmentId='" + departmentId + "'";
            }
            request.setAttribute("departmentId", departmentId);
            List<Department> departmentList = departmentBiz.find(null, "departmentAvailable=0 ORDER BY CONVERT(departmentName USING gbk)");
            request.setAttribute("departmentList", departmentList);


            pagination.setRequest(request);
            List<SysUser> userList = sysUserBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(userList)) {
                for (SysUser sysUser : userList) {
                    if (sysUser.getDepartmentId() != null) {
                        Department department = departmentBiz.findById(sysUser.getDepartmentId());
                        sysUser.setDepartmentName(department.getDepartmentName());
                    }
                }
            }
            request.setAttribute("userList", userList);
        } catch (Exception e) {
            logger.error("queryUserList()--error", e);
        }
        return "/sysuser/employee-list";
    }


    /**
     * ajax查询学员列表,教师列表
     * 2.Teacher 3.Student
     */
    @RequestMapping("/queryTeacherStudentList")
    @SuppressWarnings("unchecked")
    public String queryTeacherStudentList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, Integer userType) {
        try {
            pagination.setPageSize(10);
            List<Map<String, String>> teacherStudentList = new ArrayList<>();
            Map<String, Object> userMap = new HashMap<>();
            if (userType == 2) {
                userMap = hrHessianService.getEmployeeListBySql(pagination, "1=1 and status !=2");
                teacherStudentList = (List<Map<String, String>>) userMap.get("employeeList");
            } else if (userType == 3) {
                userMap = jiaoWuHessianService.userList(pagination, "1=1 and status !=0");
                teacherStudentList = (List<Map<String, String>>) userMap.get("userList");
            }
            request.setAttribute("teacherStudentList", teacherStudentList);
            request.setAttribute("userType", userType);
            Map<String, String> _pagination = (Map<String, String>) userMap.get("pagination");
            if (_pagination != null && _pagination.size() > 0) {
                Pagination page = gson.fromJson(gson.toJson(_pagination), new TypeToken<Pagination>() {
                }.getType());
                page.setRequest(request);
                request.setAttribute("_pagination", page);
            }
            String currentPath = request.getRequestURI();
            request.setAttribute("currentPath", currentPath);
        } catch (Exception e) {
            logger.error("queryTeacherStudentList()--error", e);
            return setErrorPath(request, e);
        }
        return "sysuser/teacher-student-list";
    }


    /**
     * 初始化添加管理员用户页面
     *
     * @return 添加页面
     */
    @RequestMapping(value = "/initAddUser")
    public String initAddUser() {
        return "/sysuser/add-sysuser";
    }

    /**
     * 添加管理员用户
     *
     * @param request HttpServletRequest
     * @param sysUser 用户属性对象
     * @return JSON数据
     */
    @RequestMapping("/addSysUser")
    @ResponseBody
    public Map<String, Object> addSysUser(HttpServletRequest request,
                                          @ModelAttribute("sysUser") SysUser sysUser) {
        try {
            // 验证两次密码是否一致
            String result = vPassword(sysUser.getPassword(), request.getParameter("rpassword"));
            if (result != null) return resultJson(ErrorCode.ERROR_PARAMETER, result, null);
            // 执行添加用户
            result = sysUserBiz.tx_createSysUser(sysUser);
            if (result != null) return resultJson(ErrorCode.ERROR_PARAMETER, result, null);
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("addSysUser()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 去批量开通
     *
     * @return
     */
    @RequestMapping("/toBatchUnitSysUser")
    public String toBatchUnitSysUser() {
        return "/sysuser/batch-sysuser";
    }

    /**
     * 批量开通单位账号
     *
     * @return
     */
    @RequestMapping("/batchAddSysUser")
    @ResponseBody
    public Map<String, Object> batchAddSysUser(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            List<Unit> unitList = unitBiz.find(null, "1=1");
            if (ObjectUtils.isNotNull(unitList)) {
                for (int i = 0; i < unitList.size(); i++) {
                    SysUser sysUser = new SysUser();
                    sysUser.setUserName(unitList.get(i).getName().trim());
                    sysUser.setPassword("111111");
                    sysUserBiz.tx_batchCreateSysUser(sysUser);
                }
            }
            json = resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("batchAddSysUser()--error", e);
            json = resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * 删除管理员用户  即修改管理员用户状态(status为0正常  为1冻结）
     *
     * @param id 用户id
     * @return 删除系统用户结果
     */
    @RequestMapping("/deleteSysUser")
    @ResponseBody
    public Map<String, Object> deleteSysUser(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        try {
            SysUser _sysUser = sysUserBiz.findById(id);
            if (_sysUser != null) {
                _sysUser.setStatus(status);
                sysUserBiz.update(_sysUser);
            }
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, _sysUser);
        } catch (Exception e) {
            logger.error("updateUserStatus()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 初始化修改管理员用户页面
     *
     * @param request HttpServletRequest
     * @param id      用户id
     * @return 用户属性对象
     */
    @RequestMapping("/toUpdateSysUser")
    public String toUpdateSysUser(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            SysUser _sysUser = sysUserBiz.findById(id);
            Map<String, String> teacherStudengMap = new HashMap<>();
            if (_sysUser.getUserType() == 2) {
                teacherStudengMap = hrHessianService.queryEmployeeById(_sysUser.getLinkId());
            }
            if (_sysUser.getUserType() == 3) {
                teacherStudengMap = jiaoWuHessianService.getUserById(_sysUser.getLinkId());
            }
            request.setAttribute("teacherStudengMap", teacherStudengMap);
            request.setAttribute("_sysUser", _sysUser);
        } catch (Exception e) {
            logger.error("updateSysUser()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/sysuser/update-sysuser";
    }

    /**
     * 初始化当前登录管理员用户修改页面
     *
     * @param request HttpServletRequest
     * @return 用户属性对象
     */
    @RequestMapping("/toUpdateThisSysUser")
    public String toUpdateThisSysUser(HttpServletRequest request) {
        try {
            Long id = SysUserUtils.getLoginSysUserId(request);
            SysUser _sysUser = sysUserBiz.findById(id);
            request.setAttribute("_sysUser", _sysUser);
        } catch (Exception e) {
            logger.error("toUpdateThisSysUser()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/sysuser/update-this-sysuser";
    }

//    /**
//     * 修改审批密码
//     * @return
//     */
//    @RequestMapping("/updateAuditPassword")
//    @ResponseBody
//    public Map<String, Object> updateAuditPassword(@ModelAttribute("sysUser") SysUser sysUser) {
//        Map<String, Object> json = null;
//        try {
//            String message = verifyAuditPassword(sysUser);
//            if (message.equals("")) {
//                sysUser.setPassword(null);
//                sysUser.setAuditPassword(MD5.getMD5(sysUser.getAuditPassword()));
//                sysUserBiz.update(sysUser);
//                message = "修改成功";
//            }
//            json = this.resultJson(ErrorCode.SUCCESS, message, null);
//        } catch(Exception e) {
//            logger.error("SysUserController.updateAuditPassword", e);
//            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
//        }
//        return json;
//    }
//
//    private String verifyAuditPassword(SysUser sysUser) {
//        String password = sysUser.getPassword();
//        String sql = " id = " + sysUser.getId() + " and password = '" + MD5.getMD5(password) + "'";
//        List<SysUser> sysUsers = sysUserBiz.find(null, sql);
//        if (sysUsers == null || sysUsers.size() == 0) {
//            return "原密码错误";
//        }
//        if (StringUtils.isTrimEmpty(sysUser.getAuditPassword())) {
//            return "审批密码不能为空";
//        }
//        if (sysUser.getAuditPassword().length() < 6) {
//            return "审批密码至少6位";
//        }
//        return "";
//    }

    /**
     * 修改用户基本信息
     *
     * @param sysUser 用户属性对象
     * @return json数据
     */
    @RequestMapping("/updateSysUser")
    @ResponseBody
    public Map<String, Object> updateSysUser(@ModelAttribute("sysUser") SysUser sysUser) {
        try {
            // 验证用户数据
            String verify = sysUserBiz.verify(sysUser);
            if (verify != null) return resultJson(ErrorCode.ERROR_PARAMETER, verify, null);
            sysUserBiz.update(sysUser);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("updateSysUser()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }

    }

    /**
     * 修改当前登录的用户基本信息
     *
     * @param sysUser 用户属性对象
     * @return json数据
     */
    @RequestMapping("/updateThisSysUser")
    @ResponseBody
    public Map<String, Object> updateThisSysUser(HttpServletRequest request,
                                                 @ModelAttribute("sysUser") SysUser sysUser) {
        try {
            sysUser.setId(SysUserUtils.getLoginSysUserId(request));

            if (ObjectUtils.isNotNull(sysUser.getAnotherName())) {
                String whereSql = " 1=1 and anotherName='" + sysUser.getAnotherName().trim() + "' limit 1";
                List<SysUser> sysUsers = sysUserBiz.find(null, whereSql);
                if (sysUsers != null && sysUsers.size() > 0) {
                    if (!sysUsers.get(0).getId().equals(sysUser.getId())) {
                        return this.resultJson(ErrorCode.ERROR_SYSTEM, "已经有这个别名了！", null);
                    }
                }
            }
            return _updateSysUser(sysUser);
        } catch (Exception e) {
            logger.error("updateThisSysUser()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }

    }

    /**
     * 执行修改用户信息
     *
     * @param sysUser 用户属性对象
     * @return Map<String       ,       Object>
     */
    private Map<String, Object> _updateSysUser(SysUser sysUser) {
        // 验证用户数据
        String verify = sysUserBiz.verify(sysUser);
        if (verify != null) return resultJson(ErrorCode.ERROR_PARAMETER, verify, null);
        sysUserBiz.update(sysUser);
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 修改当前登录用户密码
     *
     * @param nowPassword  用户原密码
     * @param newPassword  用户新密码
     * @param rnewPassword 用户确认新密码
     * @return JSON数据
     */
    @RequestMapping("/updateThisSysUserPsw")
    @ResponseBody
    public Map<String, Object> updateThisSysUserPsw(
            HttpServletRequest request,
            @RequestParam(value = "nowPassword", required = true) String nowPassword,
            @RequestParam(value = "newPassword", required = true) String newPassword,
            @RequestParam(value = "rnewPassword", required = true) String rnewPassword) {
        try {
            Long id = SysUserUtils.getLoginSysUserId(request);
            SysUser sysUser = sysUserBiz.findById(id);
            if (!sysUser.getPassword().equals(MD5.getMD5(nowPassword))) {
                return resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "原密码错误", null);
            }
            return updatePwd(sysUser, newPassword, rnewPassword);
        } catch (Exception e) {
            logger.error("updateThisSysUserPsw()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 修改用户密码
     *
     * @param id           用户ID
     * @param newPassword  用户新密码
     * @param rnewPassword 用户确认新密码
     * @return JSON数据
     */
    @RequestMapping("/updateSysUserPsw")
    @ResponseBody
    public Map<String, Object> updateSysUserPsw(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "newPassword") String newPassword,
            @RequestParam(value = "rnewPassword") String rnewPassword) {
        try {
            return updatePwd(sysUserBiz.findById(id), newPassword, rnewPassword);
        } catch (Exception e) {
            logger.error("updateSysUser()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 执行修改用户密码
     *
     * @param _sysUser     用户对象
     * @param newPassword  新密码
     * @param rnewPassword 确认密码
     * @return Map<String       ,       Object>
     */
    private Map<String, Object> updatePwd(SysUser _sysUser, String newPassword, String rnewPassword) {
        if (_sysUser == null) {
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        //验证密码
        if (StringUtils.isTrimEmpty(newPassword)) {
            return this.resultJson(ErrorCode.ERROR_DATA_NULL, "新密码不能为空", null);
        }
        if (!newPassword.equals(rnewPassword)) {
            return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "两次密码不匹配", null);
        }
        String _password = MD5.getMD5(newPassword);
        _sysUser.setPassword(_password);
        sysUserBiz.update(_sysUser);
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 加载部门树形结构
     *
     * @param sysUserId 用户id
     * @return 部门树的map数据
     */
    @RequestMapping("/getDepartmentTree")
    @ResponseBody
    public Map<String, Object> getDepartmentTree(@RequestParam("sysUserId") Long sysUserId) {
        try {
            SysUser sysUser = sysUserBiz.findById(sysUserId);
            String whereSql = " departmentAvailable!=2";
            List<Department> departments = departmentBiz.find(null, whereSql);
            if (!(ObjectUtils.isNull(sysUser) || CollectionUtils.isEmpty(departments))) {
                for (Department department : departments) {
                    if (sysUser.getDepartmentId() != null && sysUser.getDepartmentId().equals(department.getId())) {
                        department.setIsHave(StateEnum.AVAILABLE.getState());
                    }
                }
            }
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, departments);
        } catch (Exception e) {
            logger.error("getDepartmentTree()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 更新用户不部门信息
     *
     * @param userId        用户id
     * @param departmentIds 部门id  字符串
     * @return json数据
     */
    @RequestMapping("/updateUserDepartment")
    @ResponseBody
    public Map<String, Object> updateUserDepartment(@RequestParam("userId") Long userId,
                                                    @RequestParam("departmentIds") String departmentIds) {
        try {
            departmentIds = departmentIds.substring(0, departmentIds.length() - 1);
            SysUser sysUser = new SysUser();
            sysUser.setId(userId);
            sysUser.setDepartmentId(Long.parseLong(departmentIds));
            sysUserBiz.update(sysUser);
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("addUserDepartment()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 验证密码
     *
     * @param password  密码
     * @param rPassword 确认密码
     * @return Map&lt;String,Object&gt;
     */
    private String vPassword(String password, String rPassword) {
        //验证密码
        if (StringUtils.isTrimEmpty(password)) return "密码不能为空";
        if (!password.equals(rPassword)) return "两次密码不一致";
        return null;
    }

    /**
     * 去批量导入
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddBatchSysUser")
    public String toAddBatchSysUser(HttpServletRequest request) {
        return "/sysuser/batch_import_user";
    }

    /**
     * 批量导入系统用户
     *
     * @param request
     * @return
     */
    @RequestMapping("/batchSysUser")
    public String batchSysUser(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        try {
            String errorInfo = sysUserBiz.batchImportSysUser(myFile, request);
            if (StringUtils.isTrimEmpty(errorInfo)) {
                errorInfo = "导入成功";
            }
            request.setAttribute("errorInfo", errorInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/sysuser/batch_import_user";
    }

    /**
     * 删除系统用户
     *
     * @return
     */
    @RequestMapping("/deleteSysUserById")
    @ResponseBody
    public Map<String, Object> deleteSysUserById(@RequestParam("id") Long id) {
        try {
            sysUserBiz.deleteById(id);
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SUCCESS_MSG, "");
    }


}
