package com.jiaowu.controller.teacher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.a_268.base.util.DateUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.classes.ClassesTeacherRecordBiz;
import com.jiaowu.common.StudentCommonConstants;
import com.jiaowu.entity.classes.ClassesTeacherRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.common.JiaoWuHessianService;
import com.jiaowu.biz.teacher.TeacherBiz;
//import com.jiaowu.biz.teacher.TeacherBiz;
import com.jiaowu.controller.classes.ClassesController;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.teacher.Teacher;
import com.jiaowu.entity.userInfo.UserInfo;

import static com.a_268.base.util.SysUserUtils.getLoginSysUserId;

/**
 * 教师Controller
 *
 * @author 李帅雷
 */
@Controller
public class TeacherController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(TeacherController.class);
    Gson gson = new Gson();

    @Autowired
    private HrHessianService hrHessianService;
    @Autowired
    BaseHessianService baseHessianService;
    @Autowired
    JiaoWuHessianService jiaoWuHessianService;
    @Autowired
    private ClassesTeacherRecordBiz classesTeacherRecordBiz;
    @Autowired
    private ClassesBiz classesBiz;


    @Autowired
    TeacherBiz teacherBiz;

    private static final String ADMIN_PREFIX = "/admin/jiaowu/teacher";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/teacher";

    @InitBinder({"teacher"})
    public void initTeacher(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("teacher.");
    }

    @InitBinder({"userInfo"})
    public void initUserInfo(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("userInfo.");
    }

    @InitBinder({"classesTeacherRecord"})
    public void initClassesTeacherRecord(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("classesTeacherRecord.");
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
    public String toCreateTeacher(HttpServletRequest request) {
/*
			//获取用户的身份信息
		Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
		//获取当前用户的管理类型
		String userType =	userMap.get("userType");

		System.out.println(userType);

		request.setAttribute("userType",userType);*/

        return "/admin/teacher/create_teacher";
    }

    /**
     * @param teacher
     * @return java.util.Map
     * @Description 创建教师
     */
    @RequestMapping(ADMIN_PREFIX + "/createTeacher")
    @ResponseBody
    public Map<String, Object> createTeacher(@ModelAttribute("teacher") Teacher teacher, @ModelAttribute("userInfo") UserInfo userInfo) {
        Map<String, Object> json = null;
        try {


            json = validate(teacher, userInfo);
            if (json != null) {
                return json;
            }
            teacher.setAge(teacherBiz.calculateAge(teacher));
            String errorInfo = addTeacher(teacher, userInfo);
            if (!StringUtils.isTrimEmpty(errorInfo) && !com.jiaowu.common.StringUtils.isNumeric(errorInfo) && !errorInfo.contains("成功")) {
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


    public Map<String, Object> validate(Teacher teacher, UserInfo userInfo) {
        Map<String, Object> error = null;
        String errorInfo = teacherBiz.validateTeacher(teacher);
        if (!StringUtils.isTrimEmpty(errorInfo)) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            return error;
        }
        errorInfo = teacherBiz.validateEmail(userInfo.getEmail());
        if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            return error;
        }
        errorInfo = teacherBiz.validateMobile(userInfo.getMobile());
        if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            return error;
        }
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
        return "/admin/teacher/teacher_list";
    }

    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/teacherListForSelect")
    public String teacherListForSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("teacher") Teacher teacher) {
        try {
            StringBuffer sb = request.getRequestURL().append("?pagination.currentPage=" + pagination.getCurrentPage());
            String whereSql = " employeeType=1 and status!=2";
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
            if (!StringUtils.isTrimEmpty(errorInfo)) {
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
            //获取未结束的班次
            List<Classes> classesList = classesBiz.find(null, " status!=2  and endTime > '" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "'");
            //在任的班主任id串
            String teacherIds = "0,";
            if (classesList != null && classesList.size() > 0) {
                for (Classes c : classesList) {
                    if (c.getTeacherId() != null && c.getTeacherId() > 0) {
                        teacherIds += c.getTeacherId() + ",";
                    }
                    if (c.getDeputyTeacherId() != null && c.getDeputyTeacherId() > 0) {
                        teacherIds += c.getDeputyTeacherId() + ",";
                    }
                }
            }
            teacherIds = teacherIds.substring(0, teacherIds.length() - 1);
            whereSql += " and id in (" + teacherIds + ")";

//			pagination.setRequest(request);
            Map<String, Object> map = hrHessianService.getEmployeeListBySql(pagination, whereSql);
            List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
            if (teacherList != null && teacherList.size() > 0) {
                for (Map<String, String> teacherMap : teacherList) {
                    Long id = Long.valueOf(teacherMap.get("id"));
                    String classess = "";
                    String startTimes = "";
                    String endTimes = "";
                    classesList = classesBiz.find(null, " status!=2 and ( teacherId=" + id + " or deputyTeacherId=" + id + " ) and endTime > '" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "'");
                    if (classesList != null && classesList.size() > 0) {
                        for (Classes c : classesList) {
                            classess += c.getName() + "、";
                            startTimes += DateUtils.format(c.getStartTime(), "yyyy-MM-dd") + "、";
                            endTimes += DateUtils.format(c.getEndTime(), "yyyy-MM-dd") + "、";
                        }
                        classess = classess.substring(0, classess.length() - 1);
                        startTimes = startTimes.substring(0, startTimes.length() - 1);
                        endTimes = endTimes.substring(0, endTimes.length() - 1);
                    }
                    teacherMap.put("classess", classess);
                    teacherMap.put("endTimes", endTimes);
                    teacherMap.put("startTimes", startTimes);
                }
            }
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
        return "/admin/teacher/leader_list";
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 历任班主任列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/leaderHistoryList")
    public String LeaderHistoryList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("classesTeacherRecord") ClassesTeacherRecord classesTeacherRecord) {
        try {
            pagination.setRequest(request);
            List<ClassesTeacherRecord> classesTeacherRecordList = classesTeacherRecordBiz.queryClassesTeacherRecordList(classesTeacherRecord, pagination);
            request.setAttribute("pagination", pagination);
            request.setAttribute("classesTeacherRecordList", classesTeacherRecordList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teacher/leader_history_list";
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

            String errorInfo = hrHessianService.updateEmployeeType(gson.toJson(map));
            if (StringUtils.isTrimEmpty(errorInfo)) {
                Map<String, String> sysUserRoleMap = new HashMap<String, String>();
                sysUserRoleMap.put("userId", baseTeacherList.get(0).get("id"));
                sysUserRoleMap.put("roleId", StudentCommonConstants.CLASS_LEADER_ROLE_ID + "");
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
    @RequestMapping(ADMIN_PREFIX + "/cancelClassLeader")
    @ResponseBody
    public Map<String, Object> cancelClassLeader(Long employeeId) {
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
                    sysUserRoleMap.put("roleId", StudentCommonConstants.CLASS_LEADER_ROLE_ID + "");
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
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 弹出框形式的教职工列表
     * @author 李帅雷
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/employeeListForSelect")
    public String employeeListForSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            StringBuffer sb = request.getRequestURL().append("?pagination.currentPage=" + pagination.getCurrentPage());
            String whereSql = " status!=2 and type!=1";
			/*User user = new User();
			String studentId = request.getParameter("studentId");
			if (!StringUtils.isTrimEmpty(studentId)) {
				whereSql += " and studentId like '%" + studentId + "%'";
				user.setStudentId(studentId);
				sb.append("&studentId=" + studentId);
			}*/
            pagination.setCurrentUrl(sb.toString());
            Map<String, Object> map = hrHessianService.getEmployeeListBySql(pagination, whereSql);
            List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
            Map<String, String> paginationFromBase = (Map<String, String>) map.get("pagination");
            pagination.setCurrentPage(Integer.parseInt(paginationFromBase.get("currentPage")));
            pagination.setTotalPages(Integer.parseInt(paginationFromBase.get("totalPages")));
            pagination.setTotalCount(Integer.parseInt(paginationFromBase.get("totalCount")));
            request.setAttribute("teacherList", teacherList);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/teacher/employee_list_forSelect";
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
