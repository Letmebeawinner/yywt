package com.jiaowu.controller.user;

import com.a_268.base.constants.BaseCommonConstants;
import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.a_268.base.util.WebUtils;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.classroom.ClassroomBiz;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.biz.common.JiaoWuHessianService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.registerDeadline.RegisterDeadlineBiz;
import com.jiaowu.biz.search.SearchBiz;
import com.jiaowu.biz.teach.TeachingProgramCourseBiz;
import com.jiaowu.biz.unit.UnitBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.biz.userExcelRecord.UserExcelRecordBiz;
import com.jiaowu.common.CommonConstants;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.dao.classes.ClassesStatisticDao;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.classroom.Classroom;
import com.jiaowu.entity.registerDeadline.RegisterDeadline;
import com.jiaowu.entity.search.Search;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import com.jiaowu.entity.unit.Unit;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.userExcelRecord.UserExcelRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.a_268.base.util.SysUserUtils.getLoginSysUser;


/**
 * 用户Controller
 *
 * @author 李帅雷
 */
@Controller
public class UserController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final int HMAC_KEY_LEN = 60;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @InitBinder({"user"})
    public void initUser(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("user.");
    }

    @InitBinder({"userInfo"})
    public void initUserInfo(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("userInfo.");
    }

    @InitBinder({"unit"})
    public void initUnit(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("unit.");
    }

    @InitBinder({"search"})
    public void initSearch(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("search.");
    }

    @InitBinder({"userExcelRecord"})
    public void initUserExcelRecord(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("userExcelRecord.");
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    @Autowired
    UserBiz userBiz;
    @Autowired
    ClassesBiz classesBiz;
    @Autowired
    ClassTypeBiz classTypeBiz;
    @Autowired
    BaseHessianService baseHessianService;

    @Autowired
    JiaoWuHessianService jiaoWuHessianService;

    @Autowired
    TeachingProgramCourseBiz teachingProgramCourseBiz;
    @Autowired
    CourseArrangeBiz courseArrangeBiz;
    @Autowired
    UnitBiz unitBiz;
    @Autowired
    RegisterDeadlineBiz registerDeadlineBiz;
    @Autowired
    SearchBiz searchBiz;
    @Autowired
    private ClassroomBiz classroomBiz;
    @Autowired
    private UserExcelRecordBiz userExcelRecordBiz;

    private static final String ADMIN_PREFIX = "/admin/jiaowu/user";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/user";

    /**
     * @param request
     * @return
     * @Description 跳转到创建学员的页面
     */
    @RequestMapping(ADMIN_PREFIX + "/toCreateUser")
    public String toCreateUser(HttpServletRequest request, @RequestParam(value = "flag", required = false) Integer flag) {
        try {
            if (flag != null && flag.equals(1)) {
                request.setAttribute("info", "报名成功");
            }
            classTypeBiz.setAttributeClassTypeList(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/create_user";
    }

    /**
     * 计算年龄
     */
    @RequestMapping(ADMIN_PREFIX + "/checkAge")
    @ResponseBody
    public Map<String, Object> checkAge(HttpServletRequest request, @RequestParam("idNumber") String idNumber) {
        Map<String, Object> json = null;
        try {
            if (idNumber == null || (idNumber.length() != 18 && idNumber.length() != 15)) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "身份证号请输入15位或18位", null);
                return json;
            }
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
            int year = c.get(Calendar.YEAR);    //获取年
            int len = idNumber.length();
            int userAge = 0;
            //18位身份证
            if (len == 18) {
                String idNumber1 = idNumber.substring(6, 10); //截取身份证的年
                int b = Integer.valueOf(idNumber1).intValue();
                userAge = year - b; //实际年龄
            } else {
                //15位身份证
                String idNumber1 = idNumber.substring(6, 8); //截取身份证的年
                int b = Integer.valueOf("19" + idNumber1).intValue();
                userAge = year - b; //实际年龄
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, userAge);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param user
     * @return
     * @Description 创建学员
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(ADMIN_PREFIX + "/createUser")
    @ResponseBody
    public Map<String, Object> createUser(HttpServletRequest request, @ModelAttribute("user") User user) {
        Map<String, Object> json = null;
        List<Long> departmentIdList = new ArrayList<>();
        departmentIdList.add(78L);//学员
        try {
            String errorInfo = validateUser(user);
            if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
                return json;
            }
            errorInfo = validatePassword(user);
            if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
                return json;
            }
            Classes classes = classesBiz.findById(user.getClassId());
            if (classes.getMaxNum() <= classes.getStudentTotalNum()) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "该班次报名人数已满,请选择其它班次!", null);
                return json;
            }
            //客户邮箱要求是非必填的,但是base那里必须要验证邮箱,所以这样写.
            /*if(user.getMobile()!=null&&!user.getMobile().trim().equals("")&&(user.getEmail()==null||user.getEmail().equals(""))){
				user.setEmail(user.getMobile()+"@qq.com");
			}*/
            Calendar calendar = Calendar.getInstance();
            String year = calendar.get(Calendar.YEAR) + "";
            String month = (calendar.get(calendar.MONTH) + 1) + "";
            if (month.length() == 1) {
                month = "0" + month;
            }
            //根据用户信息查询数据
            Map<String, String> map = SysUserUtils.getLoginSysUser(request);
            //证明当前用户是学员
//			if(map.get("userType").equals("3")){
            //根据身份证号查询用户
            User user1 = userBiz.queryUserByIdentityNumber(user.getIdNumber());
            //根据手机号查询是否有用户
            String whereSql = " mobile=" + user.getMobile();
            Map<String, Object> stringObjectMap = baseHessianService.querySysUserList(null, whereSql);

            //根据身份证查到用户，，覆盖原来的数据
            if (ObjectUtils.isNotNull(user1)) {
                user.setId(user1.getId());
                //根据用户id查询用户是否有部门，没有添加部门，有才去添加
//					List<Map<String, String>> map1 =	baseHessianService.queryDepartmentByTypeAndId(user.getId());
//					if(map1!=null){
                List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, user.getId());
                if (baseUserList != null && baseUserList.size() > 0) {
                    baseHessianService.updateSysUserDepartment(Long.parseLong(baseUserList.get(0).get("id")), departmentIdList);
                }
//					}
                json = checkUpdate(user);
            } else if (stringObjectMap != null && stringObjectMap.get("userList") != null) {
                List<Map<String, String>> list = (List<Map<String, String>>) stringObjectMap.get("userList");
                Map<String, String> _map = list.get(0);
                String linkId = _map.get("linkId");
                user.setId(Long.parseLong(linkId));

                //根据用户id查询用户是否有部门，没有添加部门，有才去添加
//					List<Map<String, String>> map1 = baseHessianService.queryDepartmentByTypeAndId(user.getId());
//					if(map1!=null){
                //执行修改部门操作
                baseHessianService.updateSysUserDepartment(Long.parseLong(_map.get("id")), departmentIdList);
//					}
				/*	//为学员设置部门
					baseHessianService.addSysUserDepartment(user.getId(),departmentIdList);*/
                json = checkUpdate(user);
            } else {
                ClassType classType = classTypeBiz.findById(user.getClassTypeId());
                user.setClassTypeName(classType.getName());
                classes = classesBiz.findById(user.getClassId());
                user.setClassName(classes.getName());
                errorInfo = userBiz.createUser(user, year, month, map);
                System.out.println(errorInfo);

                //新建完成后,将用户的部门设置为学员
                if (isNumeric(errorInfo)) {
                    //根据登录帐号获取当前学员的id
                    whereSql = " userNo=" + Long.parseLong(errorInfo);
                    Map<String, Object> stringObjectMap1 = baseHessianService.querySysUserList(null, whereSql);
                    List<Map<String, String>> list = (List<Map<String, String>>) stringObjectMap1.get("userList");
                    Map<String, String> _map = list.get(0);
                    String linkId = _map.get("linkId");

                    //根据用户id查询用户是否有部门，没有添加部门，有才去添加
//						List<Map<String, String>> map1 = baseHessianService.queryDepartmentByTypeAndId(Long.parseLong(linkId));
//						if(map1!=null){
                    //执行修改部门操作
                    baseHessianService.updateSysUserDepartment(Long.parseLong(_map.get("id")), departmentIdList);
							/*//将新添加的学员设置部门
							baseHessianService.addSysUserDepartment(Long.parseLong(linkId),departmentIdList);*/
//						}
                }

                if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
                    /*json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);*/
                    if (isNumeric(errorInfo)) {
                        json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, errorInfo);
                    } else {
                        json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, errorInfo);
                    }

                } else {
//				Classes classes=classesBiz.findById(userInfo.getClassId());
                    classes.setStudentTotalNum(classes.getStudentTotalNum() + 1);
                    classesBiz.update(classes);
                    json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
                }
            }

			/*}else {
				json=this.resultJson(ErrorCode.ERROR_SYSTEM, "您没有报名的权限", null);
			}*/
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * 判断字符穿是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    public String validatePassword(User user) {
        //验证密码
        if (StringUtils.isTrimEmpty(user.getPassword())) {
            return "密码不能为空";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return "两次密码不匹配";
        }
        return null;
    }

    public String validateUser(User user) {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (user.getClassTypeId() == null || user.getClassTypeId() <= 0) {
            return "班型不能为空";
        }
        if (user.getClassId() == null || user.getClassId() <= 0) {
            return "班次不能为空";
        }
        if (StringUtils.isTrimEmpty(user.getName())) {
            return "名称不能为空";
        }
        if (StringUtils.isTrimEmpty(user.getSex())) {
            return "性别不能为空";
        }
        if (user.getAge() == null || user.getAge().equals(0L)) {
            return "年龄不能为空";
        }
        if (StringUtils.isTrimEmpty(user.getNationality())) {
            return "民族不能为空";
        }
        if (StringUtils.isTrimEmpty(user.getQualification())) {
            return "学历不能为空";
        }
        if (StringUtils.isTrimEmpty(user.getIdNumber())) {
            return "身份证号不能为空";
        }
        Matcher isNum = pattern.matcher(user.getIdNumber());
        if (!isNum.matches()) {
            return "身份证号格式错误";
        }
        if ((user.getIdNumber().length() != 18 && user.getIdNumber().length() != 15)) {
            return "身份证号格式错误";
        }
        List<User> userList = userBiz.find(null, " status!=0 and idNumber='" + user.getIdNumber() + "'");
        if (userList != null && userList.size() > 0) {
            return "身份证号重复";
        }
        return null;
    }

    public String validateEmail(String email) {
        if (StringUtils.isTrimEmpty(email))
            return "邮箱不能为空";
        if (!StringUtils.isEmail(email))
            return "邮箱格式不正确";
        if (baseHessianService.isEmailOrMobileExist(email, 1))
            return "邮箱已注册";
        return null;
    }

    public String validateMobile(String mobile) {
        if (StringUtils.isTrimEmpty(mobile))
            return "手机号不能为空";
        if (!StringUtils.isMobile(mobile))
            return "手机号格式错误";
        if (baseHessianService.isEmailOrMobileExist(mobile, 2))
            return "手机号已注册";
        return null;
    }

    /**
     * @param request
     * @return
     * @Description 跳转到修改学员的页面
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdateUser")
    public String toUpdateUser(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            classTypeBiz.setAttributeClassTypeList(request);
            RegisterDeadline registerDeadline = registerDeadlineBiz.findById(1L);
            if (new Date().getTime() > registerDeadline.getDeadline().getTime()) {
                request.setAttribute("errorInfo", "报名已截止,您不能更改自己信息。");
            }
            User user = userBiz.findById(id);
            request.setAttribute("user", user);
			/*if(user.getClassId()!=null&&user.getClassId()!=0){
				Classes classes=classesBiz.findById(user.getClassId());
				request.setAttribute("className", classes.getName());
			}*/
			/*String year=user.getStudentId().substring(0, 4);
			String month=user.getStudentId().substring(4, 6);
			request.setAttribute("year", year);
			request.setAttribute("month", month);*/
			/*List<ClassType> classTypeList = classTypeBiz.find(null," 1=1");
			request.setAttribute("classTypeList",classTypeList);*/
            List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, id);
            if (baseUserList != null && baseUserList.size() > 0) {
                request.setAttribute("email", baseUserList.get(0).get("email"));
                request.setAttribute("mobile", baseUserList.get(0).get("mobile"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/update_user";
    }


    /**
     * @param request
     * @param user
     * @return
     * @Description 创建学员
     */
    @RequestMapping(ADMIN_PREFIX + "/updateUser")
    @ResponseBody
    public Map<String, Object> updateUser(HttpServletRequest request, @ModelAttribute("user") User user) {
        Map<String, Object> json = null;
        try {
            User updateUser = new User();
            updateUser.setId(user.getId());
            updateUser.setCarNumber(user.getCarNumber());
            userBiz.update(updateUser);
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
     * @Description 轮训报名列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/userList")
    public String userList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            List<ClassType> classTypeList = classTypeBiz.find(null, " 1=1 order by id desc");
            request.setAttribute("classTypeList", classTypeList);

            String whereSql = " status in (1,7,8)";
            User user = new User();
            String userId = request.getParameter("userId");
            if (!StringUtils.isTrimEmpty(userId) && Long.parseLong(userId) > 0) {
                whereSql += " and id=" + userId;
                user.setId(Long.parseLong(userId));
            }
            String classTypeId = request.getParameter("classTypeId");
            if (!StringUtils.isTrimEmpty(classTypeId) && Long.parseLong(classTypeId) > 0) {
                whereSql += " and classTypeId=" + classTypeId;
                user.setClassTypeId(Long.parseLong(classTypeId));
            }
            String classId = request.getParameter("classId");
            if (!StringUtils.isTrimEmpty(classId) && Long.parseLong(classId) > 0) {
                whereSql += " and classId=" + classId;
                user.setClassId(Long.parseLong(classId));
                Classes classes = classesBiz.findById(Long.parseLong(classId));
                user.setClassName(classes.getName());
            }
            String studentId = request.getParameter("studentId");
            if (!StringUtils.isTrimEmpty(studentId)) {
                whereSql += " and studentId like '%" + studentId + "%'";
                user.setStudentId(studentId);
            }
            String name = request.getParameter("name");
            if (!StringUtils.isTrimEmpty(name)) {
                whereSql += " and name like '%" + name + "%'";
                user.setName(name);
            }
            String idNumber = request.getParameter("idNumber");
            if (!StringUtils.isTrimEmpty(idNumber)) {
                whereSql += " and idNumber like '%" + idNumber + "%'";
                user.setIdNumber(idNumber);
            }
            String unitId = request.getParameter("unitId");
            if (!StringUtils.isTrimEmpty(unitId) && !unitId.equals("0")) {
                whereSql += " and unitId=" + unitId;
                user.setUnitId(Long.parseLong(unitId));
                user.setUnit(request.getParameter("unit"));
            }
            String time = request.getParameter("time");
            request.setAttribute("time", time);
            if (!StringUtils.isTrimEmpty(time)) {
                if (time.equals("1")) {
                    whereSql += " and createTime>'" + getThisYearBeginTime() + "' and createTime<'" + getThisYearMiddleTime() + "'";
                } else if (time.equals("2")) {
                    whereSql += " and createTime>'" + getThisYearBeginTime() + "' and createTime<'" + getThisYearEndTime() + "'";
                } else if (time.equals("3")) {
                    whereSql += " and createTime>'" + getFourYearBeforeTime() + "' and createTime<'" + getThisYearEndTime() + "'";
                }
            }
            pagination.setRequest(request);
            List<User> userList = userBiz.find(pagination, whereSql + " order by unitId");
            addEmailAndMobileToUserList(userList);
            addBirthdayToUserList(userList);
            request.setAttribute("userList", userList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("user", user);
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            request.setAttribute("showIdnumber", userMap.get("userType").equals("1") ? true : false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/user_list";
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 学员列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/userInfo")
    public String userInfo(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            List<ClassType> classTypeList = classTypeBiz.find(null, " 1=1");
            request.setAttribute("classTypeList", classTypeList);

            //获取用户的用户id
            Map<String, String> map = baseHessianService.querySysUserById(SysUserUtils.getLoginSysUserId(request));
            //拼接查询条件
            String whereSql = " status!=0" + " and id=" + Long.parseLong(map.get("linkId"));
            User user = new User();
            String userId = request.getParameter("userId");
            if (!StringUtils.isTrimEmpty(userId) && Long.parseLong(userId) > 0) {
                whereSql += " and id=" + userId;
                user.setId(Long.parseLong(userId));
            }
            String classTypeId = request.getParameter("classTypeId");
            if (!StringUtils.isTrimEmpty(classTypeId) && Long.parseLong(classTypeId) > 0) {
                whereSql += " and classTypeId=" + classTypeId;
                user.setClassTypeId(Long.parseLong(classTypeId));
            }
            String classId = request.getParameter("classId");
            if (!StringUtils.isTrimEmpty(classId) && Long.parseLong(classId) > 0) {
                whereSql += " and classId=" + classId;
                user.setClassId(Long.parseLong(classId));
                Classes classes = classesBiz.findById(Long.parseLong(classId));
                user.setClassName(classes.getName());
            }
            String studentId = request.getParameter("studentId");
            if (!StringUtils.isTrimEmpty(studentId)) {
                whereSql += " and studentId like '%" + studentId + "%'";
                user.setStudentId(studentId);
            }
            String name = request.getParameter("name");
            if (!StringUtils.isTrimEmpty(name)) {
                whereSql += " and name like '%" + name + "%'";
                user.setName(name);
            }
            String idNumber = request.getParameter("idNumber");
            if (!StringUtils.isTrimEmpty(idNumber)) {
                whereSql += " and idNumber like '%" + idNumber + "%'";
                user.setIdNumber(idNumber);
            }

            pagination.setRequest(request);
            List<User> userList = userBiz.find(pagination, whereSql);
            addEmailAndMobileToUserList(userList);
            request.setAttribute("userList", userList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/user_Info";
    }


    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 弹出框形式的一个班次下的学员列表
     * @author 李帅雷
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/userListOfOneClassForSelect")
    public String userListOfOneClassForSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            StringBuffer sb = request.getRequestURL().append("?pagination.currentPage=" + pagination.getCurrentPage());
            List<User> userList = null;
            User user = null;
            Map<String, String> userMap = getLoginSysUser(request);
            if (userMap.get("userType").equals("2")) {
                List<Classes> classList = classesBiz.find(null, " teacherId=" + userMap.get("linkId"));
                if (classList != null && classList.size() > 0) {
                    String whereSql = " status!=0 and classId=" + classList.get(0).getId();
                    user = new User();
                    String studentId = request.getParameter("studentId");
                    if (!StringUtils.isTrimEmpty(studentId)) {
                        whereSql += " and studentId like '%" + studentId + "%'";
                        user.setStudentId(studentId);
                        sb.append("&studentId=" + studentId);
                    }
                    pagination.setCurrentUrl(sb.toString());
                    userList = userBiz.find(pagination, whereSql + " order by createTime desc");
                    addEmailAndMobileToUserList(userList);
                }
            }

            request.setAttribute("userList", userList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/user_list_of_oneclass_for_select";
    }

    public void addEmailAndMobileToUserList(List<User> userList) {
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, user.getId());
                if (baseUserList != null && baseUserList.size() > 0) {
                    user.setMobile(baseUserList.get(0).get("mobile"));
                    user.setEmail(baseUserList.get(0).get("email"));
                }
            }
        }
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 新生列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/newUserList")
    public String newUserList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            List<ClassType> classTypeList = classTypeBiz.find(null, " 1=1");
            request.setAttribute("classTypeList", classTypeList);

            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            String whereSql = " status in (1,2,3,4,5,6)";

            User user = new User();
            String classTypeId = request.getParameter("classTypeId");
            if (!StringUtils.isTrimEmpty(classTypeId) && Long.parseLong(classTypeId) > 0) {
                whereSql += " and classTypeId=" + classTypeId;
                user.setClassTypeId(Long.parseLong(classTypeId));
            }
            String classId = request.getParameter("classId");
            if (!StringUtils.isTrimEmpty(classId) && Long.parseLong(classId) > 0) {
                whereSql += " and classId=" + classId;
                user.setClassId(Long.parseLong(classId));
                Classes classes = classesBiz.findById(Long.parseLong(classId));
                user.setClassName(classes.getName());
            }
            String studentId = request.getParameter("studentId");
            if (!StringUtils.isTrimEmpty(studentId)) {
                whereSql += " and studentId like '%" + studentId + "%'";
                user.setStudentId(studentId);
            }
            String time = request.getParameter("time");
            request.setAttribute("time", time);
            if (!StringUtils.isTrimEmpty(time)) {
                if (time.equals("1")) {
                    whereSql += " and createTime>'" + getThisYearBeginTime() + "' and createTime<'" + getThisYearMiddleTime() + "'";
                } else if (time.equals("2")) {
                    whereSql += " and createTime>'" + getThisYearBeginTime() + "' and createTime<'" + getThisYearEndTime() + "'";
                } else if (time.equals("3")) {
                    whereSql += " and createTime>'" + getFourYearBeforeTime() + "' and createTime<'" + getThisYearEndTime() + "'";
                }
            }
            pagination.setRequest(request);
            List<User> userList = userBiz.find(pagination, whereSql + " order by createTime desc");
            addEmailAndMobileToUserList(userList);
            addBirthdayToUserList(userList);
            request.setAttribute("userList", userList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("user", user);
            request.setAttribute("totalNum", pagination.getTotalCount());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/new_user_list";
    }

    /**
     * @param request
     * @param id
     * @return java.util.Map
     * @Description 删除某用户
     */
    @RequestMapping(ADMIN_PREFIX + "/delUser")
    @ResponseBody
    public Map<String, Object> delUser(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            User user = userBiz.findById(id);
            user.setStatus(0);
            userBiz.update(user);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 跳转到批量导入学员的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/toBatchImportUser")
    public String toBatchImportUser(HttpServletRequest request) {
        return "/admin/user/batch_import_user";
    }

    /**
     * @param request
     * @param myFile
     * @return java.lang.String
     * @Description 批量导入学员
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/batchImportUser")
    public String batchImportUser(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        try {
            String errorInfo = userBiz.batchImportStudent(myFile, request);
            if (errorInfo != null && !errorInfo.equals("")) {
                request.setAttribute("errorInfo", errorInfo);
                return "/admin/user/batch_import_user";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/jiaowu/user/newUserList";
    }


    public static String getRandomString(int strLength) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < strLength; i++) {
            if (random.nextBoolean()) {
                int charInt = 48 + random.nextInt(10);
                char c = (char) charInt;
                buffer.append(c);
            } else {
                int charInt = 65;
                if (random.nextBoolean())
                    charInt = 65 + random.nextInt(26);
                else
                    charInt = 97 + random.nextInt(26);
                if (charInt == 79)
                    charInt = 111;
                char c = (char) charInt;
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    /**
     * @param
     * @param id
     * @return java.util.Map
     * @Description 将学员的状态由未报到改为已报到
     */
    @RequestMapping(ADMIN_PREFIX + "/baodao")
    @ResponseBody
    public Map<String, Object> baodao(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            User user = userBiz.findById(id);
            user.setBaodao(1);
            user.setStatus(1);
            userBiz.update(user);
            Classes classes = classesBiz.findById(user.getClassId());
            classes.setStudentSignNum(classes.getStudentSignNum() + 1);
            classesBiz.update(classes);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param id
     * @return String
     * @Description 跳转到部门领导审核新生的页面
     */
    @RequestMapping(ADMIN_PREFIX + "/toApproveNewUser")
    public String toApproveNewUser(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            User user = userBiz.findById(id);
            request.setAttribute("user", user);
            if (user.getClassId() != null && user.getClassId() != 0) {
                Classes classes = classesBiz.findById(user.getClassId());
                request.setAttribute("className", classes.getName());
            }
            String year = user.getStudentId().substring(0, 4);
            String month = user.getStudentId().substring(4, 6);
            request.setAttribute("year", year);
            request.setAttribute("month", month);
            List<ClassType> classTypeList = classTypeBiz.find(null, " 1=1");
            request.setAttribute("classTypeList", classTypeList);
            List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, id);
            if (baseUserList != null && baseUserList.size() > 0) {
                request.setAttribute("email", baseUserList.get(0).get("email"));
                request.setAttribute("mobile", baseUserList.get(0).get("mobile"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/approve_new_user";
    }

    /**
     * @param request
     * @param id
     * @return Map
     * @Description 新生审核成功
     */
    @RequestMapping(ADMIN_PREFIX + "/approveNewUser")
    @ResponseBody
    public Map<String, Object> approveNewUser(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            User user = userBiz.findById(id);
            user.setHasApprove(1);
            userBiz.update(user);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param id
     * @return String
     * @Description 跳转到负责人核对新生的页面
     */
    @RequestMapping(ADMIN_PREFIX + "/toCheckNewUser")
    public String toCheckNewUser(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            User user = userBiz.findById(id);
            request.setAttribute("user", user);
            if (user.getClassId() != null && user.getClassId() != 0) {
                Classes classes = classesBiz.findById(user.getClassId());
                request.setAttribute("className", classes.getName());
            }
            String year = user.getStudentId().substring(0, 4);
            String month = user.getStudentId().substring(4, 6);
            request.setAttribute("year", year);
            request.setAttribute("month", month);
            List<ClassType> classTypeList = classTypeBiz.find(null, " 1=1");
            request.setAttribute("classTypeList", classTypeList);
            List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, id);
            if (baseUserList != null && baseUserList.size() > 0) {
                request.setAttribute("email", baseUserList.get(0).get("email"));
                request.setAttribute("mobile", baseUserList.get(0).get("mobile"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/check_new_user";
    }

    /**
     * @param request
     * @param id
     * @return Map
     * @Description 新生核对成功
     */
    @RequestMapping(ADMIN_PREFIX + "/checkNewUser")
    @ResponseBody
    public Map<String, Object> checkNewUser(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            User user = userBiz.findById(id);
            user.setHasCheck(1);
            userBiz.update(user);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param userId
     * @return Map
     * @Description 获取某班次班长信息
     */
    @RequestMapping(ADMIN_PREFIX + "/getMonitor")
    @ResponseBody
    public Map<String, Object> getMonitor(HttpServletRequest request, @RequestParam("userId") Long userId) {
        Map<String, Object> json = null;
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            User user = userBiz.findById(userId);
            List<User> monitor = userBiz.find(null, " classId=" + user.getClassId() + " and isMonitor=1");
            if (monitor != null && monitor.size() > 0) {
                data.put("hasMonitor", 1);
                data.put("monitorId", monitor.get(0).getId());
                data.put("monitorName", monitor.get(0).getName());
            } else {
                data.put("hasMonitor", 0);
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, data);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param userId
     * @param monitorId
     * @return Map
     * @Description 设置班长
     */
    @RequestMapping(ADMIN_PREFIX + "/assignMonitor")
    @ResponseBody
    public Map<String, Object> assignMonitor(HttpServletRequest request, @RequestParam("userId") Long userId, @RequestParam(value = "monitorId", required = false) Long monitorId) {
        Map<String, Object> json = null;
        try {
            if (monitorId != null && monitorId > 0) {
                User monitor = new User();
                monitor.setId(monitorId);
                monitor.setIsMonitor(0);
                userBiz.update(monitor);
            }
            User user = new User();
            user.setId(userId);
            user.setIsMonitor(1);
            userBiz.update(user);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param userId
     * @return Map
     * @Description 撤销班长
     */
    @RequestMapping(ADMIN_PREFIX + "/cancelMonitor")
    @ResponseBody
    public Map<String, Object> cancelMonitor(HttpServletRequest request, @RequestParam("userId") Long userId) {
        Map<String, Object> json = null;
        try {
            User user = new User();
            user.setId(userId);
            user.setIsMonitor(0);
            userBiz.update(user);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @return String
     * @Description 查看当前登录学员的教学计划完成情况, 该权限只有学员拥有.
     */
    @RequestMapping(ADMIN_PREFIX + "/progressOfCurrentStudentTeachingProgram")
    public String progressOfCurrentStudentTeachingProgram(HttpServletRequest request) {
        try {
            Map<String, String> userMap = getLoginSysUser(request);
            if (userMap.get("userType").equals("3")) {
                User user = userBiz.findById(Long.parseLong(userMap.get("linkId")));
                List<TeachingProgramCourse> teachingProgramCourseList = teachingProgramCourseBiz.find(null, " status=1 and classId=" + user.getClassId());
                List<Map<String, Object>> list = userBiz.getAllProgressOfOneStudentProgramCourse(teachingProgramCourseList);
                request.setAttribute("list", list);
                request.setAttribute("user", user);
                if (list == null || list.size() == 0) {
                    request.setAttribute("errorInfo", "该学员所在班次还未有教学计划课程!");
                }
            } else {
                request.setAttribute("errorInfo", "当前登录人非学员!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/progress_of_one_student_teaching_program";
    }


    /**
     * @param request
     * @param userId
     * @return String
     * @Description 查看某学员的教学计划完成情况.
     */
    @RequestMapping(ADMIN_PREFIX + "/progressOfOneStudentTeachingProgram")
    public String progressOfOneStudentTeachingProgram(HttpServletRequest request, @RequestParam("userId") Long userId) {
        try {
            User user = userBiz.findById(userId);
            List<TeachingProgramCourse> teachingProgramCourseList = teachingProgramCourseBiz.find(null, " status=1 and classId=" + user.getClassId());
            request.setAttribute("list", userBiz.getAllProgressOfOneStudentProgramCourse(teachingProgramCourseList));
            request.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/progress_of_one_student_teaching_program";
    }

    /**
     * 验证登录时如果用户报名过，修改信息的方法
     *
     * @param user
     * @return
     */
    public Map<String, Object> checkUpdate(User user) {
        Map<String, Object> json = null;
        try {
            String errorInfo = validateUser(user);
            if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
                return json;
            }
            //从base项目中获取学员的邮箱和手机号,与现在的手机号和邮箱做对比,如果不一致,才进行判断是否重复.
            List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, user.getId());
            if (baseUserList != null && baseUserList.size() > 0) {
                if (!user.getEmail().equals(baseUserList.get(0).get("email"))) {
                    errorInfo = validateEmail(user.getEmail());
                    if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
                        json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
                        return json;
                    }
                }
                if (!user.getMobile().equals(baseUserList.get(0).get("mobile"))) {
                    errorInfo = validateMobile(user.getMobile());
                    if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
                        json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
                        return json;
                    }
                }
            }
            userBiz.updateUser(user);
            //json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            json = this.resultJson(ErrorCode.SUCCESS, "修改学员信息成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    //演示案例
    @RequestMapping(ADMIN_PREFIX + "/demoList")
    public String demoTest(HttpServletRequest request, Pagination pagination) {

        String whereSql = " 1=1";

        //查询所有用户信息
        pagination.setRequest(request);
        List<User> userList = userBiz.find(pagination, whereSql);
        addEmailAndMobileToUserList(userList);

        request.setAttribute("userList", userList);

        return "/admin/user/userDemo_list";
    }


    /**
     * 回显数据
     */

    @RequestMapping(ADMIN_PREFIX + "/toUpdateForDemo")
    public String toUpdateForDemo(HttpServletRequest request, @RequestParam("id") Long id) {

        User user = userBiz.findById(id);

        request.setAttribute("user", user);

        List<Map<String, String>> list = baseHessianService.querySysUser(3, id);

        request.setAttribute("mobile", list.get(0).get("mobile"));

        request.setAttribute("email", list.get(0).get("email"));

        return "/admin/user/update_userForDemo";
    }

    /**
     * 供选择的单位列表
     *
     * @param request
     * @param pagination
     * @param unit
     * @return
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/unitListForSelect")
    public String unitListForSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("unit") Unit unit) {
        try {
            pagination.setPageSize(50);
            StringBuffer sb = request.getRequestURL().append("?pagination.currentPage=" + pagination.getCurrentPage());
            String whereSql = " 1=1";
            if (!StringUtils.isTrimEmpty(unit.getName())) {
                whereSql += " and name like '%" + unit.getName() + "%'";
                sb.append("&unit.name=" + unit.getName());
            }
            pagination.setCurrentUrl(sb.toString());
            List<Unit> unitList = unitBiz.find(pagination, whereSql);
            request.setAttribute("unitList", unitList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("unit", unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/unit_list_forSelect";
    }

    /**
     * 跳转到修改报名截止时间的页面
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdateRegisterDeadLine")
    public String toUpdateRegisterDeadLine(HttpServletRequest request) {
        try {
            RegisterDeadline registerDeadline = registerDeadlineBiz.findById(1L);
            request.setAttribute("registerDeadline", registerDeadline);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/updateRegisterDeadLine";
    }

    /**
     * 修改报名截止时间
     *
     * @param deadline
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/updateRegisterDeadLine")
    @ResponseBody
    public Map<String, Object> updateRegisterDeadLine(Date deadline) {
        Map<String, Object> json = null;
        try {
            RegisterDeadline registerDeadline = new RegisterDeadline();
            registerDeadline.setId(1L);
            registerDeadline.setDeadline(deadline);
            registerDeadlineBiz.update(registerDeadline);
            json = this.resultJson(ErrorCode.SUCCESS, "修改报名截止时间成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 设置单位人员为轮训人员
     *
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/setNewUserToLunXun")
    @ResponseBody
    public Map<String, Object> setNewUserToLunXun(Long id) {
        Map<String, Object> json = null;
        try {
            User user = new User();
            user.setId(id);
            user.setStatus(3);
            userBiz.update(user);
            json = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
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
     * @Description 轮训预报名列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/beforeSignUserList")
    public String beforeSignUserList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            List<ClassType> classTypeList = classTypeBiz.find(null, " 1=1");
            request.setAttribute("classTypeList", classTypeList);

            String whereSql = " status in (1,3,4,5,6)";
            User user = new User();
            String classTypeId = request.getParameter("classTypeId");
            if (!StringUtils.isTrimEmpty(classTypeId) && Long.parseLong(classTypeId) > 0) {
                whereSql += " and classTypeId=" + classTypeId;
                user.setClassTypeId(Long.parseLong(classTypeId));
            }
            String classId = request.getParameter("classId");
            if (!StringUtils.isTrimEmpty(classId) && Long.parseLong(classId) > 0) {
                whereSql += " and classId=" + classId;
                user.setClassId(Long.parseLong(classId));
                Classes classes = classesBiz.findById(Long.parseLong(classId));
                user.setClassName(classes.getName());
            }
            String studentId = request.getParameter("studentId");
            if (!StringUtils.isTrimEmpty(studentId)) {
                whereSql += " and studentId like '%" + studentId + "%'";
                user.setStudentId(studentId);
            }

            pagination.setRequest(request);
            List<User> userList = userBiz.find(pagination, whereSql + " order by createTime desc");
            addEmailAndMobileToUserList(userList);
            request.setAttribute("userList", userList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("user", user);
            request.setAttribute("totalNum", pagination.getTotalCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/beforesign_user_list";
    }

    /**
     * 通过轮训人员
     *
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/passLunXun")
    @ResponseBody
    public Map<String, Object> passLunXun(Long id) {
        Map<String, Object> json = null;
        try {
            User user = new User();
            user.setId(id);
            user.setStatus(4);
            userBiz.update(user);
            json = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 驳回轮训人员
     *
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/denyLunXun")
    @ResponseBody
    public Map<String, Object> denyLunXun(Long id) {
        Map<String, Object> json = null;
        try {
            User user = new User();
            user.setId(id);
            user.setStatus(5);
            userBiz.update(user);
            json = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 属于班次
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/belongClass")
    public String belongClass(HttpServletRequest request) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (userMap.get("userType").equals("3")) {
                User user = userBiz.findById(Long.parseLong(userMap.get("linkId")));
                Classes classes = classesBiz.findById(user.getClassId());
                request.setAttribute("classes", classes);
                request.setAttribute("user", user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/belongclass";
    }

    /**
     * 确认参加班次
     *
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/passJoinClass")
    @ResponseBody
    public Map<String, Object> passJoinClass(Long id) {
        Map<String, Object> json = null;
        try {
            User user = new User();
            user.setId(id);
            user.setStatus(1);
            userBiz.update(user);
            json = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 取消参加班次
     *
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/denyJoinClass")
    @ResponseBody
    public Map<String, Object> denyJoinClass(Long id) {
        Map<String, Object> json = null;
        try {
            User user = new User();
            user.setId(id);
            user.setStatus(6);
            userBiz.update(user);
            json = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 跳转到填写调查表的页面
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toAddSearch")
    public String toAddSearch(HttpServletRequest request) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            User user = userBiz.findById(Long.parseLong(userMap.get("linkId")));
            request.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/addSearch";
    }

    /**
     * 添加调查记录
     *
     * @param search
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/addSearch")
    @ResponseBody
    public Map<String, Object> addSearch(HttpServletRequest request, Search search) {
        Map<String, Object> json = null;
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (!userMap.get("userType").equals("3")) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您没有权限填写调查表", null);
                return json;
            }
            List<Search> searchList = searchBiz.find(null, " userId=" + userMap.get("linkId"));
            if (searchList != null && searchList.size() > 0) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您已填写过调查表", null);
                return json;
            }
            User user = userBiz.findById(Long.parseLong(userMap.get("linkId")));
            search.setUserId(user.getId());
            search.setUserName(user.getName());
            search.setClassId(user.getClassId());
            search.setClassName(user.getClassName());
            search.setUnitId(user.getUnitId());
            search.setUnit(user.getUnit());
            searchBiz.save(search);
            json = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 学习需求调查表列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/searchList")
    public String searchList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = " status=1";
            pagination.setRequest(request);
            List<Search> searchList = searchBiz.find(pagination, whereSql);
            request.setAttribute("searchList", searchList);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/searchList";
    }

    /**
     * 删除某学习调查记录
     *
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/delSearch")
    @ResponseBody
    public Map<String, Object> delSearch(Long id) {
        Map<String, Object> json = null;
        try {
            Search search = new Search();
            search.setId(id);
            search.setStatus(0);
            searchBiz.update(search);
            json = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    public void addBirthdayToUserList(List<User> userList) {
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                String idNumber = user.getIdNumber();
                StringBuffer sb = new StringBuffer();
                if (idNumber.length() == 18) {
                    sb.append(idNumber.substring(6, 10) + "年");
                    if (idNumber.substring(10, 11).equals("0")) {
                        sb.append(idNumber.substring(11, 12) + "月");
                    } else {
                        sb.append(idNumber.substring(10, 12) + "月");
                    }
                    if (idNumber.substring(12, 13).equals("0")) {
                        sb.append(idNumber.substring(13, 14) + "日");
                    } else {
                        sb.append(idNumber.substring(12, 14) + "日");
                    }
                } else {
                }
                user.setBirthday(sb.toString());
            }
        }
    }

    public String getThisYearBeginTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-01-01 :00:00:00";
    }

    public String getThisYearMiddleTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-06-01 :00:00:00";
    }

    public String getThisYearEndTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-12-31 :00:00:00";
    }

    public String getFourYearBeforeTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -4);
        return c.get(Calendar.YEAR) + "-01-01 :00:00:00";
    }

    /**
     * 登录后直接跳转到单位名单页面
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/directToNewUserList")
    public String directToNewUserList(HttpServletRequest request) {
        try {
            String sid = WebUtils.getCookie(request, BaseCommonConstants.LOGIN_KEY);
            Long userId = SysUserUtils.getLoginSysUserId(request);
            //只缓存24小时
            redisCache.set("this_sys_key_px_" + sid + userId, "JW", 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/jiaowu/user/newUserList.json";
    }

    /**
     * @param request
     * @param response
     * @Description 导出轮训报名名单
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/exportUserList")
    public void exportUserList(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/userList");
            String expName = "培训报名名单_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] headName = {"班型", "班次", "姓名", "学号", "身份证号", "性别", "年龄", "单位", "职务职称", "级别", "政治面貌", "学历"};
            List<File> srcfile = userBiz.getExcelUserList(request, dir, headName, expName);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param request
     * @param response
     * @Description 导出单位人员名单
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/exportNewUserList")
    public void exportNewUserList(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/newUserList");
            String expName = "单位人员名单_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] headName = {"班型", "班次", "姓名", "学号", "身份证号", "性别", "年龄", "单位", "职务职称", "级别", "政治面貌", "学历"};
            List<File> srcfile = userBiz.getExcelUserList(request, dir, headName, expName);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param request
     * @param response
     * @Description 导出轮训预报名名单
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/exportBeforeSignUserList")
    public void exportBeforeSignUserList(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/beforeSignUserList");
            String expName = "培训预报名名单_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] headName = {"班型", "班次", "姓名", "学号", "身份证号", "性别", "年龄", "单位", "职务职称", "级别", "政治面貌", "学历"};
            List<File> srcfile = userBiz.getExcelUserList(request, dir, headName, expName);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 弹出框形式的学员列表
     * @author 李帅雷
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/userListForSelect")
    public String userListForSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            StringBuffer sb = request.getRequestURL().append("?pagination.currentPage=" + pagination.getCurrentPage());
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            List<Classes> classesList = classesBiz.find(null, " teacherId=" + userMap.get("linkId"));
            String classIds = "";
            if (ObjectUtils.isNotNull(classesList)) {
                classIds = classesList.stream().map(c -> c.getId().toString()).collect(Collectors.joining(","));
            }
            List<User> userList = null;
            String whereSql = " status!=0";
            User user = new User();
            String studentId = request.getParameter("studentId");
            if (!StringUtils.isTrimEmpty(studentId)) {
                whereSql += " and studentId like '%" + studentId + "%'";
                user.setStudentId(studentId);
                sb.append("&studentId=" + studentId);
            }
            if (!StringUtils.isEmpty(classIds)) {
                whereSql += " and classId in (" + classIds + ")";
            }
            pagination.setCurrentUrl(sb.toString());
            userList = userBiz.find(pagination, whereSql + " order by createTime desc");
            addEmailAndMobileToUserList(userList);
            request.setAttribute("userList", userList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/user_list_forSelect";
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/zuoqutu")
    public String zuoqutu(HttpServletRequest request, @RequestParam(value = "classTypeId", required = false) Long classTypeId, @RequestParam(value = "classId", required = false) Long classId,
                          @RequestParam(value = "classroomId", required = false) Long classroomId) {
        try {
            Classroom classroom = classroomBiz.findById(classroomId);

            List<ClassType> classTypeList = classTypeBiz.find(null, " status=1 order by id asc");
            List<Classes> classesList = null;
            if (classTypeList != null && classTypeList.size() > 0) {
                if (classTypeId != null && !classTypeId.equals(0L)) {
                    classesList = classesBiz.find(null, " status=1 and classTypeId=" + classTypeId + " order by id asc");
                } else {
                    classesList = classesBiz.find(null, " status=1 and classTypeId=" + classTypeList.get(0).getId() + " order by id asc");
                }
            }
            request.setAttribute("classTypeList", classTypeList);
            request.setAttribute("classesList", classesList);
            List<Classroom> classroomList = classroomBiz.find(null, " status=1");
            request.setAttribute("classroomList", classroomList);

            //座区图单位为班次
            if (classTypeId == null || classTypeId.equals(0L)) {
                if (classTypeList != null && classTypeList.size() > 0) {
                    classTypeId = classTypeList.get(0).getId();
                } else {
                    classTypeId = 0L;
                }
            }
            if (classId == null || classId.equals(0L)) {
                if (classTypeId.equals(0L)) {
                    classId = 0L;
                } else {
                    if (classesList != null && classesList.size() > 0) {
                        classId = classesList.get(0).getId();
                    } else {
                        classId = 0L;
                    }
                }
            }
            Classes classes = classesBiz.findById(classId);
            request.setAttribute("classes", classes);
            request.setAttribute("classTypeId", classTypeId);
            request.setAttribute("classId", classId);
            request.setAttribute("classroomId", classroomId);

            long current = System.currentTimeMillis();
            //当填充假的学员时，设置ID用
            int a = 1;
            //排序值
            int sorttag = 0;
            //描述座区图每排座位的个数
            int[] lengthArray = null;
            if (classroomId == null || classroomId.equals(0L) || classroom.getType().equals(1)) {
                lengthArray = new int[]{
                        4, 17, 4,//1
                        5, 17, 5,//2
                        6, 17, 6,//3
                        7, 17, 7,//4
                        7, 17, 7,//5
                        7, 17, 7,//6
                        7, 17, 7,//7
                        7, 17, 7,//8
                        7, 17, 7,//9
                        5, 17, 5,//10
                        7, 17, 7,//11
                        7, 17, 7,//12
                        7, 17, 7,//13
                        7, 17, 7,//14
                        7, 17, 7,//15
                        7, 17, 7//16
                };
            } else if (classroom.getType().equals(2)) {
                lengthArray = new int[]{
                        4, 6, 4,//1
                        4, 6, 4,//2
                        4, 6, 4,//3
                        4, 6, 4,//4
                        4, 6, 4,//5
                        4, 6, 4,//6
                        4, 6, 4,//7
                        4, 6, 4//8
                };
            } else if (classroom.getType().equals(3)) {
                lengthArray = new int[]{
                        4, 17, 4,//1
                        5, 17, 5,//2
                        6, 17, 6,//3
                        7, 17, 7,//4
                        7, 17, 7,//5
                        7, 17, 7,//6
                        7, 17, 7,//7
                        7, 17, 7,//8
                        7, 17, 7,//9
                        5, 17, 5,//10
                        7, 17, 7,//11
                        7, 17, 7,//12
                        7, 17, 7,//13
                        7, 17, 7,//14
                        7, 17, 7,//15
                        7, 17, 7,//16
                        5, 8, 8, 5,//1
                        5, 8, 8, 5,//2
                        5, 8, 8, 5,//3
                        5, 8, 8, 5,//4
                        5, 8, 8, 5,//5
                        5, 8, 8, 5,//6
                        5, 8, 8, 5,//7
                        5, 8, 8, 5,//8
                        5, 8, 8, 5,//9
                        5, 8, 8, 5//10
                };
            }
            StringBuffer sb = new StringBuffer();
            List<User> userList = userBiz.find(null, " classTypeId=" + classTypeId + " and classId=" + classId + " and status=1 order by unitId asc,business asc");
            int index = 0;
            List<List<User>> all = new LinkedList<List<User>>();
            if (userList == null || userList.size() == 0) {
                User fakeUser = new User();
                fakeUser.setId(current + a);
                fakeUser.setSort(sorttag);
                fakeUser.setName("");
                userList.add(fakeUser);
            }
            if (userList != null && userList.size() > 0) {
                for (int j = 0; j < lengthArray.length; j++) {
                    List<User> list = new LinkedList<User>();

//					if(classroomId!=null&&!classroomId.equals(0L)&&classroom.getType().equals(3)){
                    for (int i = 0; i < lengthArray[j]; i++) {
                        if (index < userList.size()) {
                            userList.get(index).setSort(sorttag);
                            sorttag++;
                            list.add(userList.get(index));
                            sb.append(userList.get(index).getName() + ",");
                            index++;
                        } else {
                            User user = new User();
                            user.setId(current + a);
                            user.setSort(sorttag);
                            sorttag++;
                            a++;
                            list.add(user);
                            sb.append(" ,");
                        }
                    }

					/*}else{
						if((j%6>=0)&&(j%6<=2)) {
							for (int i = 0; i < lengthArray[j]; i++) {
								if (index < userList.size()) {
									userList.get(index).setSort(sorttag);
									sorttag++;
									list.add(userList.get(index));
									sb.append(userList.get(index).getName()+",");
									index++;
								} else {
									User user=new User();
									user.setId(current+a);
									user.setSort(sorttag);
									sorttag++;
									a++;
									list.add(user);
									sb.append(" ,");
								}
							}
						}else{
							for (int i = 0; i < lengthArray[j]; i++) {
								list.add(null);
							}
							int temp=lengthArray[j]-1;
							for (int i = 0; i < lengthArray[j]; i++) {
								if (index < userList.size()) {
									userList.get(index).setSort(sorttag);
									sorttag++;
									list.set(temp,userList.get(index));
									sb.append(userList.get(index).getName()+",");
									index++;
								} else {
									User user=new User();
									user.setId(current+a);
									user.setSort(sorttag);
									sorttag++;
									a++;
									list.set(temp,user);
									sb.append(" ,");
								}
								temp--;
							}
						}
					}*/

                    all.add(list);
                }
            } else {
                List<User> fakeUserList = new ArrayList<User>();
                User fakeUser = new User();
                fakeUser.setId(current + a);
                fakeUser.setSort(sorttag);
                fakeUser.setName("333");
                fakeUserList.add(fakeUser);
                all.add(fakeUserList);
                for (int i = 1; i < 48; i++) {
                    all.add(null);
                }
            }
            request.setAttribute("all", all);
            System.out.println(all.size());
            request.setAttribute("names", sb.toString());
            System.out.println(sb.toString());
            System.out.println(sb.toString().split(",").length);
            String[] sss = sb.toString().split(",");
            for (int i = 0; i < sss.length; i++) {
                System.out.println(sss[i]);
            }
			/*List<String> aaalist=new ArrayList<String>();
			aaalist.add("111");
			aaalist.add("222");
			aaalist.add("333");
			aaalist.add("444");
			request.setAttribute("aaalist",aaalist);*/
            if (classroomId == null || classroomId.equals(0L)) {
                return "/admin/user/zuoqutu";
            }

            if (classroom.getType().equals(1)) {
                return "/admin/user/zuoqutu";
            } else if (classroom.getType().equals(2)) {
                return "/admin/user/zuoqutu100";
            } else if (classroom.getType().equals(3)) {
                return "/admin/user/zuoqutu765";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导出座区图
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/zuoqutuExcel")
    public void zuoqutuExcel(HttpServletRequest request, HttpServletResponse response, String names, Long classroomId, String className) {
        try {
            Classroom classroom = classroomBiz.findById(classroomId);
            File file = null;
            if (classroomId == null || classroomId.equals(0L) || classroom.getType().equals(1)) {
                file = userBiz.zuoqutuExcel(request, names.split(","), className);
            } else if (classroom.getType().equals(2)) {
                file = userBiz.zuoqutuExcel100(request, names.split(","), className);
            }


//			File file=null;
            List<File> fileList = new ArrayList<File>();
            fileList.add(file);
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/zuoqutu");
            String expName = "座区图_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            FileExportImportUtil.createRar(response, dir, fileList, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(ADMIN_PREFIX + "/updateUserName")
    public void updateUserName(Long id, String userName) {
        try {
            User user = new User();
            user.setId(id);
            user.setName(userName);
            userBiz.update(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(ADMIN_PREFIX + "/test")
    @ResponseBody
    public Map<String, Object> test() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "身份证号请输入15位或18位", null);
    }

    @RequestMapping("/test2")
    public void test2() {
        System.out.println(CommonConstants.BASE_PATH);
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 某班次学员列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/userListOfOneClass")
    public String userListOfOneClass(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (!userMap.get("userType").equals("2")) {
                return "/admin/user/user_list_of_one_class";
            }

            String whereSql = " status=1";

            String teacherId = userMap.get("linkId");
            List<Classes> classesList = classesBiz.find(null, " teacherId=" + teacherId);
            if (classesList != null && classesList.size() > 0) {
                whereSql += " and classId=" + classesList.get(0).getId();
            }

            User user = new User();
            String userId = request.getParameter("userId");
            if (!StringUtils.isTrimEmpty(userId) && Long.parseLong(userId) > 0) {
                whereSql += " and id=" + userId;
                user.setId(Long.parseLong(userId));
            }

            String studentId = request.getParameter("studentId");
            if (!StringUtils.isTrimEmpty(studentId)) {
                whereSql += " and studentId like '%" + studentId + "%'";
                user.setStudentId(studentId);
            }
            String name = request.getParameter("name");
            if (!StringUtils.isTrimEmpty(name)) {
                whereSql += " and name like '%" + name + "%'";
                user.setName(name);
            }
            String idNumber = request.getParameter("idNumber");
            if (!StringUtils.isTrimEmpty(idNumber)) {
                whereSql += " and idNumber like '%" + idNumber + "%'";
                user.setIdNumber(idNumber);
            }
            String unitId = request.getParameter("unitId");
            if (!StringUtils.isTrimEmpty(unitId) && !unitId.equals("0")) {
                whereSql += " and unitId=" + unitId;
                user.setUnitId(Long.parseLong(unitId));
                user.setUnit(request.getParameter("unit"));
            }
            String time = request.getParameter("time");
            request.setAttribute("time", time);
            if (!StringUtils.isTrimEmpty(time)) {
                if (time.equals("1")) {
                    whereSql += " and createTime>'" + getThisYearBeginTime() + "' and createTime<'" + getThisYearMiddleTime() + "'";
                } else if (time.equals("2")) {
                    whereSql += " and createTime>'" + getThisYearBeginTime() + "' and createTime<'" + getThisYearEndTime() + "'";
                } else if (time.equals("3")) {
                    whereSql += " and createTime>'" + getFourYearBeforeTime() + "' and createTime<'" + getThisYearEndTime() + "'";
                }
            }

            pagination.setRequest(request);
            List<User> userList = userBiz.find(pagination, whereSql + " order by unitId");
            addEmailAndMobileToUserList(userList);
            addBirthdayToUserList(userList);
            request.setAttribute("userList", userList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("user", user);
            request.setAttribute("showIdnumber", userMap.get("userType").equals("1") ? true : false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/user_list_of_one_class";
    }

    /**
     * 获取当前登录人的部门
     *
     * @param request
     * @return
     */
    @RequestMapping("/getCurrentUserDepartment")
    @ResponseBody
    public Map<String, Object> getCurrentUserDepartment(HttpServletRequest request) {
        try {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 使用新表格导出学员列表
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/newUserListExcel")
    public void newUserListExcel(HttpServletRequest request, HttpServletResponse response, Long classId) {
        try {
            File file = userBiz.newUserListExcel(request, classId);
            List<File> fileList = new ArrayList<File>();
            fileList.add(file);
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/user");
            String expName = "学员名单_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            FileExportImportUtil.createRar(response, dir, fileList, expName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到导入学员名单的页面
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toCreateUserExcelRecord")
    public String toCreateUserExcelRecord() {
        return "/admin/user/create_UserExcelRecord";
    }

    /**
     * 导入学员名单
     *
     * @param userExcelRecord
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/createUserExcelRecord")
    @ResponseBody
    public Map<String, Object> createUserExcelRecord(UserExcelRecord userExcelRecord) {
        try {
            String errorInfo = userExcelRecordBiz.validate(userExcelRecord);
            if (!StringUtils.isTrimEmpty(errorInfo)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            }
            userExcelRecord.setClassTypeName(classTypeBiz.findById(userExcelRecord.getClassTypeId()).getName());
            userExcelRecord.setClassName(classesBiz.findById(userExcelRecord.getClassId()).getName());
            userExcelRecordBiz.save(userExcelRecord);
            return this.resultJson(ErrorCode.SUCCESS, "导入成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 导入学员名单列表
     *
     * @param userExcelRecord
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/userExcelRecordList")
    public String userExcelRecordList(HttpServletRequest request, @ModelAttribute("userExcelRecord") UserExcelRecord userExcelRecord, @ModelAttribute("pagination") Pagination pagination) {
        try {

            List<UserExcelRecord> userExcelRecordList = null;
            StringBuffer whereSql = new StringBuffer(" status=1");
            if (userExcelRecord.getClassTypeId() != null && !userExcelRecord.getClassTypeId().equals(0L)) {
                whereSql.append(" and classTypeId=" + userExcelRecord.getClassTypeId());
            }
            if (userExcelRecord.getClassId() != null && !userExcelRecord.getClassId().equals(0L)) {
                whereSql.append(" and classId=" + userExcelRecord.getClassId());
            }
            if (!StringUtils.isTrimEmpty(userExcelRecord.getClassName())) {
                whereSql.append(" and className like '%" + userExcelRecord.getClassName() + "%'");
            }
            if (userExcelRecord.getCreateTime() != null) {
                whereSql.append(" and to_days(createTime)=to_days('" + userExcelRecord.getCreateTime() + "')");
            }
            pagination.setRequest(request);
            userExcelRecordList = userExcelRecordBiz.find(pagination, whereSql.toString());
            request.setAttribute("userExcelRecordList", userExcelRecordList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/userExcelRecord_list";
    }

    /**
     * @param request
     * @return
     * @Description 跳转到查看学员详细信息的页面
     */
    @RequestMapping(ADMIN_PREFIX + "/detailOfUser")
    public String detailOfUser(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            User user = userBiz.findById(id);
            Classes classes = classesBiz.findById(user.getClassId());
            request.setAttribute("classes", classes);
            request.setAttribute("user", user);
            List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, id);
            if (baseUserList != null && baseUserList.size() > 0) {
                request.setAttribute("email", baseUserList.get(0).get("email"));
                request.setAttribute("mobile", baseUserList.get(0).get("mobile"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/user_detail";
    }


}
