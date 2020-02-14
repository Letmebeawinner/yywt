package com.jiaowu.controller.teacher;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.google.gson.Gson;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.common.JiaoWuHessianService;
import com.jiaowu.biz.teacher.TeacherBiz;
import com.jiaowu.common.CommonConstants;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.teacher.Teacher;
import com.jiaowu.entity.userInfo.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 教师Controller
 *
 * @author 李帅雷
 */
@Controller
public class TeacherController extends BaseController {

    private static final String ADMIN_PREFIX = "/admin/jiaowu/teacher";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/teacher";
    private static Logger logger = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    BaseHessianService baseHessianService;
    @Autowired
    JiaoWuHessianService jiaoWuHessianService;
    @Autowired
    TeacherBiz teacherBiz;
    @Autowired
    private HrHessianService hrHessianService;
    @Autowired
    private ClassesBiz classesBiz;

    @InitBinder({"teacher"})
    public void initTeacher(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("teacher.");
    }

    @InitBinder({"userInfo"})
    public void initUserInfo(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("userInfo.");
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * @return java.lang.String
     * @Description 跳转到新建教师的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/toCreateTeacher")
    public String toCreateTeacher() {
        return "/admin/teacher/create_teacher";
    }

    /**
     * @param teacher
     * @return java.util.Map
     * @Description 创建教师
     */
    @RequestMapping(ADMIN_PREFIX + "/createTeacher")
    @ResponseBody
    public Map<String, Object> createTeacher(@ModelAttribute("teacher") Teacher teacher,
                                             @ModelAttribute("userInfo") UserInfo userInfo) {
        Map<String, Object> json = null;
        try {
            Integer source = teacher.getSource();
            if (source == 1) {
                String employeeNo [] = teacher.getEmployeeNo().split(",");
                for(String str : employeeNo){
                    Map<String, Object> map = new HashMap<String, Object>();
                    List<Map<String, String>> baseTeacherList = baseHessianService.querySysUser(2, Long.parseLong(str));
                    map.put("sysUserId", baseTeacherList.get(0).get("id"));
                    map.put("id", str);
                    map.put("type", "2");
                    map.put("unitDepartment", null);
                    Gson gson = new Gson();
                    String errorInfo = hrHessianService.updateEmployeeTypeBySource(gson.toJson(map));
                    json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, errorInfo);
                }
            } else {
                json = validate(teacher, userInfo);
                if (json != null) {
                    return json;
                }
                teacher.setTeachingResearchDepartment(0);
                teacher.setType(2L);
                teacher.setAge(teacherBiz.calculateAge(teacher));
                String errorInfo = addTeacher(teacher, userInfo);
                if (!StringUtils.isTrimEmpty(errorInfo) && !com.jiaowu.common.StringUtils.isNumeric(errorInfo) && !errorInfo.contains("成功")) {
                    json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
                } else {
                /*//添加用户-部门中间表记录
                List<Long> departmentIdList = new ArrayList<>();
                departmentIdList.add(59L);//教务处
                Map<String, String> teacherMap = baseHessianService.querySysuserByUserno(errorInfo);
                if (teacherMap != null) {
                    baseHessianService.updateSysUserDepartment(Long.parseLong(teacherMap.get("id")), departmentIdList);
                }*/
                    Map<String, String> teacherMap = baseHessianService.querySysuserByUserno(errorInfo);
                    //添加用户-角色中间表记录
                    Map<String, String> sysUserRole = new HashMap<String, String>();
                    sysUserRole.put("roleId", CommonConstants.CLASS_LEADER_ROLE_ID + "");
                    sysUserRole.put("userId", teacherMap.get("id"));
                    baseHessianService.addSysUserRole(sysUserRole);
                    json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    public Map<String, Object> validate(Teacher teacher, UserInfo userInfo) {
        Map<String, Object> error = null;
        String errorInfo = teacherBiz.validateTeacher(teacher);
        if (!StringUtils.isTrimEmpty(errorInfo)) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            return error;
        }

        // 外校教师不验证邮箱    20181010注释掉了  新建教师除了姓名外，所有的必填项取消，“教研部”改为“单位（部门）”选择“本校”时，应该为本校的所有部门进行选择，选择“外校”时，手动输入单位（部门），“密码”选项起什么作用？（取消密码、邮箱、年龄等必选，不生成账号，增加单位字段）
//        if (teacher.getSource() == 1) {
//            errorInfo = teacherBiz.validateEmail(userInfo.getEmail());
//            if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
//                error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
//                return error;
//            }
//        }

        /*errorInfo = teacherBiz.validateMobile(userInfo.getMobile());
        if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            return error;
        }*/
        //默认密码111111  10、新建教师除了姓名外，所有的必填项取消，“教研部”改为“单位（部门）”选择“本校”时，应该为本校的所有部门进行选择，选择“外校”时，手动输入单位（部门），“密码”选项起什么作用？（取消密码、邮箱、年龄等必选，不生成账号，增加单位字段）
        userInfo.setPassword("111111");
        userInfo.setConfirmPassword("111111");
        errorInfo = teacherBiz.validatePassword(userInfo);
        if (!StringUtils.isTrimEmpty(errorInfo)) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            return error;
        }
        return null;
    }

    public String addTeacher(Teacher teacher, UserInfo userInfo) {
        teacher.setEmployeeType(1L);
        Map<String, String> map = ObjectUtils.objToMap(teacher);
        map.put("email", userInfo.getEmail());
        map.put("mobile", userInfo.getMobile());
        map.put("password", userInfo.getPassword());
//        map.remove("from");
        Gson gson = new Gson();
        return hrHessianService.saveEmployee(gson.toJson(map));
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 教师列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/teacherList")
    public String teacherList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("teacher") Teacher teacher) {
        try {
            String whereSql = " status!=2 and ( type=2 or type=3 ) and source = 1";
            if (!StringUtils.isTrimEmpty(teacher.getName())) {
                whereSql += " and name like '%" + teacher.getName() + "%'";
            }
            Map<String, Object> map = hrHessianService.getEmployeeListBySql(pagination, whereSql);
            List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
            Map<String, String> paginationFromBase = (Map<String, String>) map.get("pagination");
            pagination.setCurrentPage(Integer.parseInt(paginationFromBase.get("currentPage")));
            pagination.setTotalPages(Integer.parseInt(paginationFromBase.get("totalPages")));
            pagination.setTotalCount(Integer.parseInt(paginationFromBase.get("totalCount")));
            pagination.setRequest(request);
            request.setAttribute("teacherList", teacherList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teacher", teacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teacher/teacher_list";
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 教师列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/jwTeacherList")
    public String jwTeacherList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("teacher") Teacher teacher) {
        try {
            StringBuffer sb = request.getRequestURL().append("?pagination.currentPage=" + pagination.getCurrentPage());
            String whereSql = " status!=2";
            if (!StringUtils.isTrimEmpty(teacher.getName())) {
                whereSql += " and name like '%" + teacher.getName() + "%'";
                sb.append("&teacher.name=" + teacher.getName());
            }
            pagination.setCurrentUrl(sb.toString());
            Map<String, Object> map = hrHessianService.getEmployeeListBySql(pagination, whereSql);
            List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
            Map<String, String> paginationFromBase = (Map<String, String>) map.get("pagination");
            pagination.setCurrentPage(Integer.parseInt(paginationFromBase.get("currentPage")));
            pagination.setTotalPages(Integer.parseInt(paginationFromBase.get("totalPages")));
            pagination.setTotalCount(Integer.parseInt(paginationFromBase.get("totalCount")));
            request.setAttribute("teacherList", teacherList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teacher", teacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teacher/jw_teacher_list";
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 班主任列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/leaderList")
    public String LeaderList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("teacher") Teacher teacher) {
        try {
            String whereSql = " status!=2 and ( type=1 or type=3 )";
            if (!StringUtils.isTrimEmpty(teacher.getName())) {
                whereSql += " and name like '%" + teacher.getName() + "%'";
            }
            Map<String, Object> map = hrHessianService.getEmployeeListBySql(pagination, whereSql);
            List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
            Map<String, String> paginationFromBase = (Map<String, String>) map.get("pagination");
            pagination.setCurrentPage(Integer.parseInt(paginationFromBase.get("currentPage")));
            pagination.setTotalPages(Integer.parseInt(paginationFromBase.get("totalPages")));
            pagination.setTotalCount(Integer.parseInt(paginationFromBase.get("totalCount")));
            pagination.setRequest(request);

            String classType = "主体班";
            for(Map<String, String> data : teacherList){
                whereSql = "status = 1 and teacherId = "+data.get("id")+" and classType like '%" +classType+ "%'";
                List<Classes> classesList = classesBiz.find(null,whereSql);
                if(ObjectUtils.isNotNull(classesList)){
                    data.put("isZhuTi","1");
                }else{
                    data.put("isZhuTi","2");
                }
            }

            List<Map<String, String>> resultList = new ArrayList<>();
            String zhutiban = request.getParameter("zhutiban");
            if(!StringUtils.isTrimEmpty(zhutiban)){
                for(Map<String, String> data : teacherList){
                    if(data.get("isZhuTi").equals(zhutiban)){
                        resultList.add(data);
                    }
                }
                teacherList = resultList;
                pagination.setTotalCount(resultList.size());
                if(teacherList .size() > 0 && resultList.size() <= pagination.getPageSize()){
                    pagination.setTotalPages(1);
                }
                if(teacherList .size() > 0 && resultList.size() > pagination.getPageSize()){
                    if(resultList.size()%pagination.getPageSize()==0){
                        pagination.setTotalPages(resultList.size()/pagination.getPageSize());
                        teacherList = teacherList.subList((pagination.getCurrentPage()-1)*pagination.getPageSize(),pagination.getCurrentPage()*pagination.getPageSize());
                    }
                    if(resultList.size()%pagination.getPageSize()>0){
                        pagination.setTotalPages(resultList.size()/pagination.getPageSize()+1);
                        if(pagination.getCurrentPage() == pagination.getTotalPages()){
                            teacherList = teacherList.subList((pagination.getCurrentPage()-1)*pagination.getPageSize(),teacherList.size());
                        }else{
                            teacherList = teacherList.subList((pagination.getCurrentPage()-1)*pagination.getPageSize(),pagination.getCurrentPage()*pagination.getPageSize());
                        }
                    }
                }
            }

            request.setAttribute("zhutiban", zhutiban);
            request.setAttribute("teacherList", teacherList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teacher", teacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teacher/leader_list";
    }

    /**
     * 单选，弹出框形式教师列表
     *
     * @param request
     * @param pagination
     * @param teacher
     * @return
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/teacherListForSelect")
    public String teacherListForSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("teacher") Teacher teacher) {
        try {
            StringBuffer sb = request.getRequestURL().append("?pagination.currentPage=" + pagination.getCurrentPage());
            String whereSql = " status!=2 and ( type=2 or type=3 )";
            if (!StringUtils.isTrimEmpty(teacher.getName())) {
                whereSql += " and name like '%" + teacher.getName() + "%'";
                sb.append("&teacher.name=" + teacher.getName());
            }
            if (!StringUtils.isTrimEmpty(teacher.getEmployeeNo())) {
                whereSql += " and employeeNo like '%" + teacher.getEmployeeNo() + "%'";
                sb.append("&teacher.employeeNo=" + teacher.getEmployeeNo());
            }
            pagination.setCurrentUrl(sb.toString());
            Map<String, Object> map = hrHessianService.getEmployeeListBySql(pagination, whereSql);
            List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
            Map<String, String> paginationFromBase = (Map<String, String>) map.get("pagination");
            pagination.setCurrentPage(Integer.parseInt(paginationFromBase.get("currentPage")));
            pagination.setTotalPages(Integer.parseInt(paginationFromBase.get("totalPages")));
            pagination.setTotalCount(Integer.parseInt(paginationFromBase.get("totalCount")));
            request.setAttribute("teacherList", teacherList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teacher", teacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teacher/teacher_list_forSelect";
    }

    /**
     * 多选，弹出框形式的教师列表
     *
     * @param request
     * @param pagination
     * @param teacher
     * @return
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/teacherListForMultiSelect")
    public String teacherListForMultiSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("teacher") Teacher teacher) {
        try {
            StringBuffer sb = request.getRequestURL().append("?pagination.currentPage=" + pagination.getCurrentPage());
//            String whereSql = " employeeType=1 and status=1 and type=1";
            String whereSql = " status!=2 and type=2";
//			String name=request.getParameter("name");
            if (!StringUtils.isTrimEmpty(teacher.getName())) {
                whereSql += " and name like '%" + teacher.getName() + "%'";
                sb.append("&teacher.name=" + teacher.getName());
            }
            if (!StringUtils.isTrimEmpty(teacher.getEmployeeNo())) {
                whereSql += " and employeeNo like '%" + teacher.getEmployeeNo() + "%'";
                sb.append("&teacher.employeeNo=" + teacher.getEmployeeNo());
            }
            pagination.setCurrentUrl(sb.toString());
            Map<String, Object> map = hrHessianService.getEmployeeListBySql(pagination, whereSql);
            List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
            Map<String, String> paginationFromBase = (Map<String, String>) map.get("pagination");
            pagination.setCurrentPage(Integer.parseInt(paginationFromBase.get("currentPage")));
            pagination.setTotalPages(Integer.parseInt(paginationFromBase.get("totalPages")));
            pagination.setTotalCount(Integer.parseInt(paginationFromBase.get("totalCount")));
            request.setAttribute("teacherList", teacherList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teacher", teacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teacher/teacher_list_forMultiSelect";
    }

    /**
     * 查询教师和班主任
     *
     * @param request    多线程请求
     * @param pagination 分页
     * @param teacher    教师
     * @return
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/allTeacherListForSelect")
    public String allTeacherListForSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("teacher") Teacher teacher) {
        try {
            StringBuffer sb = request.getRequestURL().append("?pagination.currentPage=" + pagination.getCurrentPage());
            String whereSql = " employeeType=1 and status!=2";
//			String name=request.getParameter("name");
            if (!StringUtils.isTrimEmpty(teacher.getName())) {
                whereSql += " and name like '%" + teacher.getName() + "%'";
                sb.append("&teacher.name=" + teacher.getName());
            }
            if (!StringUtils.isTrimEmpty(teacher.getEmployeeNo())) {
                whereSql += " and employeeNo like '%" + teacher.getEmployeeNo() + "%'";
                sb.append("&teacher.employeeNo=" + teacher.getEmployeeNo());
            }
            pagination.setCurrentUrl(sb.toString());
            Map<String, Object> map = hrHessianService.getEmployeeListBySql(pagination, whereSql);
            List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
            Map<String, String> paginationFromBase = (Map<String, String>) map.get("pagination");
            pagination.setCurrentPage(Integer.parseInt(paginationFromBase.get("currentPage")));
            pagination.setTotalPages(Integer.parseInt(paginationFromBase.get("totalPages")));
            pagination.setTotalCount(Integer.parseInt(paginationFromBase.get("totalCount")));
            request.setAttribute("teacherList", teacherList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teacher", teacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teacher/teacher_list_forSelect";
    }

    /**
     * @param request
     * @param id
     * @return java.lang.String
     * @Description 跳转到修改教师的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdateTeacher")
    public String toUpdateTeacher(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            Map<String, String> teacher = hrHessianService.queryEmployeeById(id);
            request.setAttribute("teacher", teacher);
            List<Map<String, String>> baseTeacherList = baseHessianService.querySysUser(2, id);
            if (baseTeacherList != null && baseTeacherList.size() > 0) {
                request.setAttribute("email", baseTeacherList.get(0).get("email"));
                request.setAttribute("mobile", baseTeacherList.get(0).get("mobile"));
            }
            //获取用户的身份信息
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            //获取当前用户的管理类型
            String userType = userMap.get("userType");
            System.out.println(userType);
            request.setAttribute("userType", userType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teacher/update_teacher";
    }

    /**
     * @param teacher
     * @return java.util.Map
     * @Description 修改教师
     */
    @RequestMapping(ADMIN_PREFIX + "/updateTeacher")
    @ResponseBody
    public Map<String, Object> updateTeacher(@ModelAttribute("teacher") Teacher teacher, @ModelAttribute("userInfo") UserInfo userInfo) {
        Map<String, Object> json = null;
        try {
            String errorInfo = teacherBiz.validateTeacher(teacher);
            if (ObjectUtils.isNotNull(errorInfo)) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
                return json;
            }
            teacher.setAge(teacherBiz.calculateAge(teacher));
            Map<String, String> map = ObjectUtils.objToMap(teacher);
            map.put("mobile", userInfo.getMobile());
            map.put("email", userInfo.getEmail());
            List<Map<String, String>> baseTeacherList = baseHessianService.querySysUser(2, Long.parseLong(map.get("id")));
            map.put("sysUserId", baseTeacherList.get(0).get("id"));
            Gson gson = new Gson();
            errorInfo = hrHessianService.updateEmployee(gson.toJson(map));
            if (!StringUtils.isTrimEmpty(errorInfo)) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            } else {
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 设为班主任
     *
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/updateTeacherType")
    @ResponseBody
    public Map<String, Object> updateTeacherType(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            List<Map<String, String>> baseTeacherList = baseHessianService.querySysUser(2, id);
            map.put("sysUserId", baseTeacherList.get(0).get("id"));
            map.put("id", id);
            Gson gson = new Gson();
            String errorInfo = hrHessianService.updateEmployeeType(gson.toJson(map));
            if (StringUtils.isTrimEmpty(errorInfo)) {
                Map<String, String> sysUserRoleMap = new HashMap<String, String>();
                sysUserRoleMap.put("userId", baseTeacherList.get(0).get("id"));
                sysUserRoleMap.put("roleId", CommonConstants.CLASS_LEADER_ROLE_ID + "");
                baseHessianService.addSysUserRole(sysUserRoleMap);
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } else {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 取消班主任
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/cancelLeaderType")
    @ResponseBody
    public Map<String, Object> cancelLeaderType(Long employeeId) {
        try {
            List<Classes> classesList = classesBiz.find(null, " status!=2 and ( teacherId=" + employeeId + " or deputyTeacherId=" + employeeId + " ) and endTime > '" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "'");
            if (classesList != null && classesList.size() > 0) {
                return this.resultJson(ErrorCode.ERROR_SYSTEM, "该班主任正在带班，不能取消。", null);
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", employeeId);
            Integer result = hrHessianService.cancelClassLeader(gson.toJson(map),1);
            if (Integer.valueOf(1).equals(result)) {
                //删除班主任角色的记录
                List<Map<String, String>> teaccherList = baseHessianService.querySysUser(2, employeeId);
                if (teaccherList != null && teaccherList.size() > 0) {
                    Map<String, String> sysUserRoleMap = new HashMap<String, String>();
                    sysUserRoleMap.put("roleId", CommonConstants.CLASS_LEADER_ROLE_ID + "");
                    sysUserRoleMap.put("userId", teaccherList.get(0).get("id"));
                    baseHessianService.removeSysUserRole(sysUserRoleMap);
                }
                return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } else {
                return this.resultJson(ErrorCode.ERROR_SYSTEM, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 取消讲师
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/cancelClassTeacher")
    @ResponseBody
    public Map<String, Object> cancelClassTeacher(Long employeeId) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", employeeId);
            Integer result = hrHessianService.cancelClassLeader(gson.toJson(map),2);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, result);
        } catch (Exception e) {
            e.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * @param id
     * @return java.util.Map
     * @Description 删除教师
     */
    @RequestMapping(ADMIN_PREFIX + "/delTeacher")
    @ResponseBody
    public Map<String, Object> delTeacher(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            List<Map<String, String>> baseTeacherList = baseHessianService.querySysUser(2, id);
            map.put("sysUserId", baseTeacherList.get(0).get("id"));
            map.put("id", id);
            Gson gson = new Gson();
            hrHessianService.deleteEmployee(gson.toJson(map));
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 选择职工
     *
     * @param request
     * @return
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/selectEmployeeNo")
    public String selectEmployeeNo(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("teacher") Teacher teacher) {
        try {
            StringBuffer sb = request.getRequestURL().append("?pagination.currentPage=" + pagination.getCurrentPage());
            String whereSql = " status!=2 and type!=2 and type!=3 and source=1";
            if (!StringUtils.isTrimEmpty(teacher.getName())) {
                whereSql += " and name like '%" + teacher.getName() + "%'";
                sb.append("&teacher.name=" + teacher.getName());
            }
            if (!StringUtils.isTrimEmpty(teacher.getEmployeeNo())) {
                whereSql += " and employeeNo like '%" + teacher.getEmployeeNo() + "%'";
                sb.append("&teacher.employeeNo=" + teacher.getEmployeeNo());
            }
            pagination.setCurrentUrl(sb.toString());
            Map<String, Object> map = hrHessianService.getEmployeeListBySql(pagination, whereSql);
            List<Map<String, String>> employeeList = (List<Map<String, String>>) map.get("employeeList");
            Map<String, String> paginationFromBase = (Map<String, String>) map.get("pagination");
            pagination.setCurrentPage(Integer.parseInt(paginationFromBase.get("currentPage")));
            pagination.setTotalPages(Integer.parseInt(paginationFromBase.get("totalPages")));
            pagination.setTotalCount(Integer.parseInt(paginationFromBase.get("totalCount")));
            request.setAttribute("employeeList", employeeList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teacher", teacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/employee/selectEmployeeNo";
    }

    /**
     * 弹出框形式的班主任列表
     *
     * @param request
     * @param pagination
     * @param teacher
     * @return
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/classLeaderListForSelect")
    public String classLeaderListForSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("teacher") Teacher teacher) {
        try {
            StringBuffer sb = request.getRequestURL().append("?pagination.currentPage=" + pagination.getCurrentPage());
            String whereSql = " type=1 and status!=2";
            if (!StringUtils.isTrimEmpty(teacher.getName())) {
                whereSql += " and name like '%" + teacher.getName() + "%'";
                sb.append("&teacher.name=" + teacher.getName());
            }
            if (!StringUtils.isTrimEmpty(teacher.getEmployeeNo())) {
                whereSql += " and employeeNo like '%" + teacher.getEmployeeNo() + "%'";
                sb.append("&teacher.employeeNo=" + teacher.getEmployeeNo());
            }
            pagination.setCurrentUrl(sb.toString());
            Map<String, Object> map = hrHessianService.getEmployeeListBySql(pagination, whereSql);
            List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
            Map<String, String> paginationFromBase = (Map<String, String>) map.get("pagination");
            pagination.setCurrentPage(Integer.parseInt(paginationFromBase.get("currentPage")));
            pagination.setTotalPages(Integer.parseInt(paginationFromBase.get("totalPages")));
            pagination.setTotalCount(Integer.parseInt(paginationFromBase.get("totalCount")));
            request.setAttribute("teacherList", teacherList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("teacher", teacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teacher/classLeader_list_forSelect";
    }

}
