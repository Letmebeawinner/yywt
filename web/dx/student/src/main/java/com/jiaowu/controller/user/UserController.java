package com.jiaowu.controller.user;

import com.a_268.base.constants.BaseCommonConstants;
import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.*;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.classroom.ClassroomBiz;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.common.umc.DpWebService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.registerDeadline.RegisterDeadlineBiz;
import com.jiaowu.biz.search.SearchBiz;
import com.jiaowu.biz.teach.TeachingProgramCourseBiz;
import com.jiaowu.biz.unit.UnitBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.biz.userExcelRecord.UserExcelRecordBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.common.FileUtils;
import com.jiaowu.common.StudentCommonConstants;
import com.jiaowu.dao.classes.ClassTypeStatisticDao;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.ClassTypeStatistic;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.classroom.Classroom;
import com.jiaowu.entity.registerDeadline.RegisterDeadline;
import com.jiaowu.entity.search.Search;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import com.jiaowu.entity.unit.Unit;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.userExcelRecord.UserExcelRecord;
import com.jiaowu.util.StringUtil;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.a_268.base.util.SysUserUtils.getLoginSysUser;

//import com.jiaowu.entity.userInfo.UserInfo;

/**
 * 用户Controller
 *
 * @author 李帅雷
 */
@Controller
public class UserController extends BaseController {

    private static final int HMAC_KEY_LEN = 60;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String ADMIN_PREFIX = "/admin/jiaowu/user";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/user";
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private ClassTypeBiz classTypeBiz;
    @Autowired
    private BaseHessianService baseHessianService;
    @Autowired
    private TeachingProgramCourseBiz teachingProgramCourseBiz;
    @Autowired
    private CourseArrangeBiz courseArrangeBiz;
    @Autowired
    private UnitBiz unitBiz;
    @Autowired
    private RegisterDeadlineBiz registerDeadlineBiz;
    @Autowired
    private SearchBiz searchBiz;
    @Autowired
    private UserExcelRecordBiz userExcelRecordBiz;
    @Autowired
    private ClassroomBiz classroomBiz;
    @Autowired
    private DpWebService dpWebService;
    @Autowired
    private ClassTypeStatisticDao classTypeStatisticDao;
    @Autowired
    private HrHessianService hrHessianService;

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

    @InitBinder({"classTypeStatistic"})
    public void initClassTypeStatistic(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("classTypeStatistic.");
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    @InitBinder
    protected void initBinder2(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }


    /**
     * @param request
     * @return
     * @Description 跳转到创建学员的页面
     */
    @RequestMapping(ADMIN_PREFIX + "/toCreateUser")
    public String toCreateUser(HttpServletRequest request,
                               @RequestParam(value = "flag", required = false) Integer flag) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (userMap.get("userType").equals("5")) {
                Unit unit = unitBiz.findById(Long.parseLong(userMap.get("unitId")));
                request.setAttribute("unit", unit);
            }
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
        try {
            String errorInfo = validateUser(user);
            if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
                return json;
            }
            Classes classes = classesBiz.findById(user.getClassId());
            if (classes.getMaxNum() <= classes.getStudentTotalNum()) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "该班次报名人数已满,请选择其它班次!", null);
                return json;
            }
            Long userId = SysUserUtils.getLoginSysUserId(request);
            List<Long> roleList = baseHessianService.queryUserRoleByUserId(userId);
            boolean isManage = false;
            if (roleList != null) {
                for (Long roleId : roleList) {
                    if (roleId.longValue() == 25) {//如果具有学员处角色
                        isManage = true;
                        user.setStatus(1);
                        break;
                    }
                }
            }
            //报名截至时间
            Date registerDeadline = classes.getSignEndTime();
//            Map<String, String> resultMap = baseHessianService.queryDepartmentBySysuserId(SysUserUtils.getLoginSysUserId(request));
            if (!isManage && registerDeadline.getTime() < System.currentTimeMillis()) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "该班次报名时间已截止", null);
            }
            errorInfo = validateMobile(user.getMobile());
            if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
                return json;
            }
            errorInfo = validatePassword(user);
            if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
                return json;
            }
            boolean sameInOneClass = userBiz.checkInOneClass(user);
            if (sameInOneClass) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请选择其它班次，该班次存在相同的手机号或身份证号！", null);
            }

            Calendar calendar = Calendar.getInstance();
            String year = calendar.get(Calendar.YEAR) + "";
            String month = (calendar.get(Calendar.MONTH) + 1) + "";
            if (month.length() == 1) {
                month = "0" + month;
            }
            //根据用户信息查询数据
            Map<String, String> map = SysUserUtils.getLoginSysUser(request);

            ClassType classType = classTypeBiz.findById(user.getClassTypeId());
            user.setClassTypeName(classType.getName());
            classes = classesBiz.findById(user.getClassId());
            user.setClassName(classes.getName());

            // 创建用户
            errorInfo = userBiz.createUser(user, year, month, map);
            if (isNumeric(errorInfo)) {
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, errorInfo);
            } else {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, errorInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    private String validatePassword(User user) {
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
        if (StringUtils.isTrimEmpty(user.getUnit())) {
            return "单位不能为空";
        }
        if (StringUtils.isTrimEmpty(user.getJob())) {
            return "职务职称不能为空";
        }
        if (user.getBusiness() == null || user.getBusiness().equals(0)) {
            return "级别不能为空";
        }
        /*if(user.getAge()==null||user.getAge().equals(0L)){
            return "年龄不能为空";
		}*/
        if (StringUtils.isTrimEmpty(user.getNationality())) {
            return "民族不能为空";
        } else {
            if (user.getNationality().indexOf("族") <= -1) {
                user.setNationality(user.getNationality() + "族");
            }
        }
        if (StringUtils.isTrimEmpty(user.getQualification())) {
            return "学历不能为空";
        }
        if (StringUtils.isTrimEmpty(user.getIdNumber())) {
            return "身份证号不能为空";
        }
//		if(!StringUtils.isTrimEmpty(user.getIdNumber())) {
//			Matcher isNum = pattern.matcher(user.getIdNumber());
//			if (!isNum.matches()) {
//				return "身份证号格式错误";
//			}
//			if ((user.getIdNumber().length() != 18)) {
//				return "身份证号格式错误";
//			}
//			/*List<User> userList = userBiz.find(null, " status!=0 and idNumber='" + user.getIdNumber() + "'");
//			if (userList != null && userList.size() > 0&&(!userList.get(0).getId().equals(user.getId()))) {
//				for(User tempUser:userList){
//					if(tempUser.getClassId().equals(user.getClassId())){
//						return "身份证号重复";
//					}
//				}
//
//			}*/
//		}
        return null;
    }

    public String validateEmail(String email) {
        /*if (StringUtils.isTrimEmpty(email))
            return "邮箱不能为空";*/
        if (!StringUtils.isTrimEmpty(email)) {
            if (!StringUtils.isEmail(email))
                return "邮箱格式不正确";
            /*if (baseHessianService.isEmailOrMobileExist(email, 1))
                return "邮箱已注册";*/
        }
        return null;
    }

    public String validateMobile(String mobile) {
        if (StringUtils.isTrimEmpty(mobile))
            return "手机号不能为空";
        if (!StringUtils.isMobile(mobile))
            return "手机号格式错误";
        /*if (baseHessianService.isEmailOrMobileExist(mobile,2))
            return "手机号已注册";*/
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
            User user = userBiz.findById(id);
            Classes classes = classesBiz.findById(user.getClassId());

            request.setAttribute("classes", classes);
            classTypeBiz.setAttributeClassTypeList(request);
            Date registerDeadline = classes.getSignEndTime();

            if (registerDeadline.getTime() < System.currentTimeMillis()) {
                //查询用户角色
                Long userId = SysUserUtils.getLoginSysUserId(request);

                String roleIds = baseHessianService.queryUserRolesByUserId(userId);

                ///如果是学员处或者组织部
                if (ObjectUtils.isNotNull(roleIds)) {
                    if (!StringUtils.isEmpty(roleIds)) {
                        if (roleIds.indexOf("25") != -1) {
                            request.setAttribute("errorInfo", "");
                        } else {
                            request.setAttribute("errorInfo", "报名已截止,您不能更改信息。");
                        }
                    }
                } else {
                    request.setAttribute("errorInfo", "报名已截止,您不能更改信息。");
                }
            }
            request.setAttribute("user", user);
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
            String category = request.getParameter("category");
            String whereSql = " status in (1,4,7,8)";
            if (ObjectUtils.isNotNull(category)) {
                switch (category) {
                    case "0":
                        whereSql = " status in (1,4,7,8)";
                        break;
                    case "1":
                        whereSql = " status in (1,4,8)";
                        break;
                    case "2":
                        whereSql = " status in (7)";
                        break;
                }
            }
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
            String groupId = request.getParameter("groupId");
            request.setAttribute("groupId", groupId);
            if (!StringUtils.isTrimEmpty(groupId)) {
                whereSql += " and groupId=" + groupId;
            }
            String unit = request.getParameter("unit");
            if (!StringUtils.isTrimEmpty(unit)) {
                whereSql += " and unit like '%" + unit + "%'";
                user.setUnit(unit);
            }
            String business = request.getParameter("business");
            if (!StringUtils.isTrimEmpty(business)) {
                whereSql += " and business=" + business;
                user.setBusiness(Integer.parseInt(business));
            }
            pagination.setRequest(request);
            List<User> userList = userBiz.find(pagination, whereSql + " order by unitId,business ");
            addEmailAndMobileToUserList(userList);
            addBirthdayToUserList(userList);
            request.setAttribute("userList", userList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("user", user);
            request.setAttribute("category", category);
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            request.setAttribute("sysUser", userMap);
            request.setAttribute("showIdnumber", userMap.get("userType").equals("1") ? true : false);
            //查询用户角色

            //查询用户角色
            String roleIds = baseHessianService.queryUserRolesByUserId(SysUserUtils.getLoginSysUserId(request));
            if (!StringUtils.isEmpty(roleIds)) {
                if (roleIds.indexOf("26") == -1) {
                    request.setAttribute("flag", "true");
                }
            }
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
            /*String status=request.getParameter("status");
            if(!StringUtils.isTrimEmpty(status) && Long.parseLong(status)>0){
                whereSql+=" and status="+status;
                user.setStatus(Integer.parseInt(status));
            }*/
			/*String baodao=request.getParameter("baodao");
			if(!StringUtils.isTrimEmpty(baodao) && Long.parseLong(baodao)>-1){
                whereSql+=" and baodao="+baodao;
                user.setBaodao(Integer.parseInt(baodao));
            }*/
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
			/*String email=request.getParameter("email");
			if(!StringUtils.isTrimEmpty(email)){
				whereSql+=" and email like '%"+email+"%'";
				user.setEmail(email);
			}
			String mobile=request.getParameter("mobile");
			if(!StringUtils.isTrimEmpty(mobile)){
				whereSql+=" and mobile like '%"+mobile+"%'";
				user.setMobile(mobile);
			}*/
            pagination.setRequest(request);
            List<User> userList = userBiz.find(pagination, whereSql);
            addEmailAndMobileToUserList(userList);
            request.setAttribute("userList", userList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("user", user);
//	        request.setAttribute("totalNum", pagination.getTotalCount());
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
    public String userListOfOneClassForSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination
            pagination) {
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
                    String studentName = request.getParameter("studentName");
                    if (!StringUtils.isTrimEmpty(studentName)) {
                        whereSql += " and name like '%" + studentName + "%'";
                        user.setName(studentName);
                        sb.append("&name=" + studentName);
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

    /**
     * 关联查询手机号和邮箱
     *
     * @param userList list里存的都是引用
     */
    private void addEmailAndMobileToUserList(List<User> userList) {
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
           /* Map<String, String> department = baseHessianService.queryDepartmentBySysuserId(Long.parseLong(userMap.get("id")));
            //如果当前登录人所属部门为学员处，则在页面显示“一键通过”按钮。
            if (department.get("departmentId").equals("83")) {
                request.setAttribute("usermanager", true);
            }*/
//			Map<String, String> department=baseHessianService.queryDepartmentBySysuserId(Long.parseLong(userMap.get("id")));
//			String whereSql=" status in (1,2,3,4,5,6)";
//            String whereSql = " (status = 2 or status = 3) and sysUserId=" + userMap.get("id");
            String whereSql = " (status = 2 or status = 3) and unitId=" + userMap.get("unitId");
			/*if(department!=null){
				if(department.get("departmentId").equals("18")){
					request.setAttribute("department","superManager");
				}else if(department.get("departmentId").equals("80")){
					request.setAttribute("department","unitManager");
//					whereSql+="and sysUserId="+userMap.get("id");
				}else if(department.get("departmentId").equals("81")){
					request.setAttribute("name","organization");
//					whereSql+="and sysUserId="+userMap.get("id");
				}
			}*/

            User user = new User();
			/*String status=request.getParameter("status");
			if(!StringUtils.isTrimEmpty(status) && Long.parseLong(status)>0){
                whereSql+=" and status="+status;
                user.setStatus(Integer.parseInt(status));
            }*/
			/*String baodao=request.getParameter("baodao");
			if(!StringUtils.isTrimEmpty(baodao) && Long.parseLong(baodao)>-1){
                whereSql+=" and baodao="+baodao;
                user.setBaodao(Integer.parseInt(baodao));
            }*/
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
			/*String email=request.getParameter("email");
			if(!StringUtils.isTrimEmpty(email)){
                whereSql+=" and email like '%"+email+"%'";
                user.setEmail(email);
            }
			String mobile=request.getParameter("mobile");
			if(!StringUtils.isTrimEmpty(mobile)){
                whereSql+=" and mobile like '%"+mobile+"%'";
                user.setMobile(mobile);
            }*/
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
            userBiz.deleteById(id);
            //删除账号的同时删除WiFi
            String mobile = request.getParameter("mobile");
            if (StringUtil.biggerThanZero(mobile)) {
                dpWebService.delWifi(mobile);
            }
            List<Map<String, String>> map = baseHessianService.querySysUser(3, id);

            String linkId = map.get(0).get("id");
            baseHessianService.deleteSysUserId(linkId);
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
			/*if(errorInfo!=null&&!errorInfo.equals("")){
				request.setAttribute("errorInfo", errorInfo);
				return "/admin/user/batch_import_user";
			}*/
            if (StringUtils.isTrimEmpty(errorInfo)) {
                errorInfo = "导入成功";
            }
            request.setAttribute("errorInfo", errorInfo);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorInfo", "系统繁忙，请稍后再试。");
        }
        return "/admin/user/batch_import_user";
//		return "redirect:/admin/jiaowu/user/newUserList";
    }

    @RequestMapping(ADMIN_PREFIX + "/batchImportUsertest")
    public void batchImportUsertest(HttpServletRequest request, HttpServletResponse response) {
        try {

			/*File file=userBiz.batchImportStudent2(request);
			List<File> fileList=new ArrayList<File>();
			fileList.add(file);
			String dir = request.getSession().getServletContext().getRealPath("/excelfile/zuoqutu");
			String expName = "座区图_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			FileExportImportUtil.createRar(response, dir, fileList, expName);// 生成的多excel的压缩包*/
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            pagination.setPageSize(10);
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
     * 单位列表
     *
     * @param request
     * @param pagination
     * @param unit
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/unitList")
    public String unitList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("unit") Unit unit) {
        try {
            pagination.setPageSize(50);
//			StringBuffer sb=request.getRequestURL().append("?pagination.currentPage="+pagination.getCurrentPage());
            String whereSql = " 1=1";
//			String name=request.getParameter("name");
            if (!StringUtils.isTrimEmpty(unit.getName())) {
                whereSql += " and name like '%" + unit.getName() + "%'";
//				sb.append("&unit.name="+unit.getName());
            }
            pagination.setRequest(request);
//			pagination.setCurrentUrl(sb.toString());
            List<Unit> unitList = unitBiz.find(pagination, whereSql);
            unitList.forEach(u -> {
                u.setUnitNameNo(baseHessianService.queryUnitByUnitId(u.getId()));
            });
            request.setAttribute("unitList", unitList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("unit", unit);
            //只有学员处拥有重置密码的权限
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            List<Long> roleList = baseHessianService.queryUserRoleByUserId(Long.parseLong(userMap.get("id")));
            if (roleList != null && roleList.size() > 0) {
                for (Long role : roleList) {
                    if (role.equals(StudentCommonConstants.XUEYUANCHU_ROLE_ID)) {
                        request.setAttribute("showUpdatePassword", true);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/unit_list";
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
            List<ClassType> classTypeList = classTypeBiz.find(null, " 1=1 order by id desc");
            request.setAttribute("classTypeList", classTypeList);

            String roleIds = baseHessianService.queryUserRolesByUserId(SysUserUtils.getLoginSysUserId(request));
            if (!StringUtils.isEmpty(roleIds)) {
                roleIds = "," + roleIds + ",";
                if (roleIds.indexOf(",25,") != -1 || roleIds.indexOf(",10,") != -1) {
                    request.setAttribute("flag", "true");
                }
            }
            String whereSql = " status in (3)";
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
//			user.setStatus(4);
            //取消学员确认班次
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
                List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, user.getId());
                if (baseUserList != null && baseUserList.size() > 0) {
                    request.setAttribute("email", baseUserList.get(0).get("email"));
                    request.setAttribute("mobile", baseUserList.get(0).get("mobile"));
                }
                List<Map<String, String>> administrator = baseHessianService.querySysUser(1, 1L);
                if (user.getSysUserId() != null && !user.getSysUserId().equals(0L)) {
                    List<Map<String, String>> sysUserList = baseHessianService.querySysUser(1, user.getSysUserId());
                    if (sysUserList != null && sysUserList.size() > 0) {
                        request.setAttribute("sysusermobile", sysUserList.get(0).get("mobile"));
                    } else {
                        request.setAttribute("sysusermobile", administrator.get(0).get("mobile"));
                    }
                } else {
                    request.setAttribute("sysusermobile", administrator.get(0).get("mobile"));
                }
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


    /**
     * 添加学员
     *
     * @param userList 都是引用
     */
    private void addBirthdayToUserList(List<User> userList) {
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
//					sb.append("19"+idNumber.substring(6,8)+"年");
//					if(idNumber.substring(8,9).equals("0")){
//						sb.append(idNumber.substring(9,10)+"月");
//					}else{
//						sb.append(idNumber.substring(8,10)+"月");
//					}
//					if(idNumber.substring(10,11).equals("0")){
//						sb.append(idNumber.substring(11,12)+"日");
//					}else{
//						sb.append(idNumber.substring(10,12)+"日");
//					}
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
            List<File> srcfile = userBiz.getUnitExcelUserList(request, dir, headName, expName);
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
            List<User> userList = null;
            String whereSql = " status in (1,4,8)";
            User user = new User();
            String studentName = request.getParameter("studentName");
            if (!StringUtils.isTrimEmpty(studentName)) {
                whereSql += " and name like '%" + studentName + "%'";
                user.setName(studentName);
                sb.append("&name=" + studentName);
            }
            //如何当前用户是班主任，则只选取他所属班次的学员
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (userMap.get("userType").equals("2")) {
                List<Classes> classesList = classesBiz.find(null, " teacherId=" + userMap.get("linkId"));
                StringBuilder classIds = new StringBuilder().append("(");
                if (classesList != null && classesList.size() > 0) {
                    for (Classes classes : classesList) {
                        classIds.append(classes.getId() + ",");
                    }
                    classIds.replace(classIds.length() - 1, classIds.length(), ")");
                } else {
                    classIds.append("0)");
                }
                whereSql += " and classId in " + classIds;
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
     * 若该页面仍需改，需要重写，现在写的太乱，因为改的太多。
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/zuoqutu")
    public String zuoqutu(HttpServletRequest request, @RequestParam(value = "classTypeId", required = false) Long classTypeId, @RequestParam(value = "classId", required = false) Long classId,
                          @RequestParam(value = "classroomId", required = false) Long classroomId) {
        try {
            Classroom classroom = classroomBiz.findById(classroomId);

            List<ClassType> classTypeList = classTypeBiz.find(null, " status=1 order by id desc");
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
                lengthArray = StudentCommonConstants.ZUOQUTU478;
            } else if (classroom.getType().equals(2)) {
                lengthArray = StudentCommonConstants.ZUOQUTU100;
            } else if (classroom.getType().equals(3)) {
                lengthArray = StudentCommonConstants.ZUOQUTU736;
            } else if (classroom.getType().equals(4)) {
                lengthArray = StudentCommonConstants.ZUOQUTU200;
            } else if (classroom.getType().equals(5)) {
                lengthArray = StudentCommonConstants.ZUOQUTU158;
            } else if (classroom.getType().equals(6)) {
                lengthArray = StudentCommonConstants.ZUOQUTU80;
            } else if (classroom.getType().equals(7)) {
                lengthArray = StudentCommonConstants.ZUOQUTU312;
            } else if (classroom.getType().equals(8)) {
                lengthArray = StudentCommonConstants.ZUOQUTU207;
            } else if (classroom.getType().equals(9)) {
                lengthArray = StudentCommonConstants.ZUOQUTU100_2;
            }
            StringBuffer sb = new StringBuffer();
            List<User> userList = userBiz.find(null, " classTypeId=" + classTypeId + " and classId=" + classId + " and status=1 order by unitId asc,business asc");
            int index = 0;
            List<List<User>> all = new LinkedList<List<User>>();
            if (userList == null || userList.size() == 0) {
                User fakeUser = new User();
                fakeUser.setId(current + a);
                fakeUser.setSort(sorttag++);
                fakeUser.setName("");
                userList.add(fakeUser);
            }
            if (userList != null && userList.size() > 0) {
                for (int j = 0; j < lengthArray.length; j++) {
                    List<User> list = new LinkedList<User>();

//					if(classroomId!=null&&!classroomId.equals(0L)&&classroom.getType().equals(3)){
                    for (int i = 0; i < lengthArray[j]; i++) {
                        if (index < userList.size()) {
                            userList.get(index).setSort(sorttag++);
//                            sorttag++;
                            list.add(userList.get(index));
                            sb.append(userList.get(index).getName() + ",");
                            index++;
                        } else {
                            User user = new User();
                            user.setId(current + a);
                            user.setSort(sorttag++);
//                            sorttag++;
                            a++;
                            list.add(user);
                            sb.append(" ,");
                        }
                    }


                    all.add(list);
                }
            } else {
                List<User> fakeUserList = new ArrayList<User>();
                User fakeUser = new User();
                fakeUser.setId(current + a);
                fakeUser.setSort(sorttag++);
                fakeUser.setName("");
                fakeUserList.add(fakeUser);
                all.add(fakeUserList);
                for (int i = 1; i < 48; i++) {
                    all.add(null);
                }
            }
            request.setAttribute("all", all);
            request.setAttribute("names", sb.toString());
            String[] sss = sb.toString().split(",");

            if (classroomId == null || classroomId.equals(0L)) {
                return "/admin/user/zuoqutu";
            }

            if (classroom.getType().equals(1)) {
                return "/admin/user/zuoqutu";
            } else if (classroom.getType().equals(2)) {
                return "/admin/user/zuoqutu100";
            } else if (classroom.getType().equals(3)) {
                return "/admin/user/zuoqutu765";
            } else if (classroom.getType().equals(4)) {
                return "/admin/user/zuoqutu200";
            } else if (classroom.getType().equals(5)) {
                return "/admin/user/zuoqutu158";
            } else if (classroom.getType().equals(6)) {
                return "/admin/user/zuoqutu80";
            } else if (classroom.getType().equals(7)) {
                return "/admin/user/zuoqutu312";
            } else if (classroom.getType().equals(8)) {
                return "/admin/user/zuoqutu207";
            } else if (classroom.getType().equals(9)) {
                return "/admin/user/zuoqutu100_2";
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
            } else if (classroom.getType().equals(3)) {
                file = userBiz.zuoqutuExcel765(request, names.split(","), className);
            } else if (classroom.getType().equals(4)) {
                file = userBiz.zuoqutuExcel200(request, names.split(","), className);
            } else if (classroom.getType().equals(5)) {
                file = userBiz.zuoqutuExcel158(request, names.split(","), className);
            } else if (classroom.getType().equals(6)) {
                file = userBiz.zuoqutuExcel80(request, names.split(","), className);
            } else if (classroom.getType().equals(7)) {
                file = userBiz.zuoqutuExcel312(request, names.split(","), className);
            } else if (classroom.getType().equals(8)) {
                file = userBiz.zuoqutuExcel207(request, names.split(","), className);
            } else if (classroom.getType().equals(9)) {
                file = userBiz.zuoqutuExcel100of2(request, names.split(","), className);
            }


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

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 单位学员列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/unitUserList")
    public String unitUserList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);

            List<ClassType> classTypeList = classTypeBiz.find(null, " 1=1");
            request.setAttribute("classTypeList", classTypeList);

//            String whereSql = " status in (1,4,7,8) and sysUserId=" + userMap.get("id");
            String whereSql = " unitId=" + userMap.get("unitId");
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
            /**
             * 將班次id对应的班次名改为名字
             */
            if (userList != null && userList.size() > 0) {
                for (User _user : userList) {
                    Classes classes = classesBiz.findById(_user.getClassId());
                    if (ObjectUtils.isNotNull(classes)) {
                        _user.setClassName(classes.getName());
                    }
                }
            }
            request.setAttribute("userList", userList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/unit_user_list";
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 培训学员列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/signUserList")
    public String signUserList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            List<ClassType> classTypeList = classTypeBiz.find(null, " 1=1");
            request.setAttribute("classTypeList", classTypeList);

            String whereSql = " status=1 and isnull(sysUserId)";
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
//	        request.setAttribute("totalNum", pagination.getTotalCount());
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            request.setAttribute("showIdnumber", userMap.get("userType").equals("1") ? true : false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/sign_user_list";
    }

    /**
     * @param request
     * @return
     * @Description 跳转到基本信息的页面
     */
    @RequestMapping(ADMIN_PREFIX + "/baseCondition")
    public String baseCondition(HttpServletRequest request) {
        try {
            classTypeBiz.setAttributeClassTypeList(request);
            Long userId = Long.parseLong(SysUserUtils.getLoginSysUser(request).get("linkId"));
            User user = userBiz.findById(userId);
            request.setAttribute("user", user);
            List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, userId);
            if (baseUserList != null && baseUserList.size() > 0) {
                request.setAttribute("email", baseUserList.get(0).get("email"));
                request.setAttribute("mobile", baseUserList.get(0).get("mobile"));
            }
            if (user.getSysUserId() != null && !user.getSysUserId().equals(0L)) {
                List<Map<String, String>> sysUserList = baseHessianService.querySysUser(1, user.getSysUserId());
                if (sysUserList != null && sysUserList.size() > 0) {
                    request.setAttribute("sysusermobile", sysUserList.get(0).get("mobile"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/baseCondition";
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
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            /*Map<String, String> sysuserDepartmentMap = baseHessianService.queryDepartmentBySysuserId(Long.parseLong(userMap.get("id")));
            return this.resultJson(ErrorCode.SUCCESS, "添加成功", sysuserDepartmentMap.get("departmentId"));*/
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 一键通过
     *
     * @param userId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/oneButtonPass")
    @ResponseBody
    public Map<String, Object> oneButtonPass(Long userId) {
        Map<String, Object> json = null;
        try {
            User user = new User();
            user.setId(userId);
            user.setStatus(1);
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
     * @param user
     * @return
     * @Description 修改学员信息
     */
    @RequestMapping(ADMIN_PREFIX + "/updateUserInfo")
    @ResponseBody
    public Map<String, Object> updateUserInfo(HttpServletRequest request, @ModelAttribute("user") User user) {
        Map<String, Object> json = null;
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            String errorInfo = validateUserInfo(user);
            if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
                return json;
            }
            Map<String, Object> sysUserMap = new HashMap<String, Object>();
            sysUserMap.put("sysUser.id", userMap.get("id"));
            sysUserMap.put("sysUser.email", user.getEmail());
            sysUserMap.put("sysUser.mobile", user.getMobile());
            errorInfo = baseHessianService.updateSysUser(sysUserMap);
            if (!StringUtils.isTrimEmpty(errorInfo) && !(errorInfo.equals(""))) {
                json = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
                return json;
            }
            user.setMobile(null);
            user.setEmail(null);
            userBiz.update(user);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
			/*Calendar calendar=Calendar.getInstance();
			String year=calendar.get(Calendar.YEAR)+"";
			String month=(calendar.get(calendar.MONTH)+1)+"";
			if(month.length()==1){
				month="0"+month;
			}*/
            /*if(!StringUtils.isTrimEmpty(errorInfo)&&!(errorInfo.equals(""))){
             *//*json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);*//*
				if(isNumeric(errorInfo)){
					json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, errorInfo);
				}else {
					json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, errorInfo);
				}

			}*/
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    public String validateUserInfo(User user) {
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
		/*List<User> userList=userBiz.find(null," status!=0 and idNumber='"+user.getIdNumber()+"'"+" and id!="+user.getId());
		if(userList!=null&&userList.size()>0){
			return "身份证号重复";
		}*/
        return null;
    }

    /**
     * 跳转到修改学员编号的页面
     *
     * @param request
     * @param userId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdateUserPerId")
    public String toUpdateUserPerId(HttpServletRequest request, Long userId) {
        try {
            User user = userBiz.findById(userId);
            request.setAttribute("user", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/updateUserPerId";
    }

    /**
     * 修改学员编号
     *
     * @param userId
     * @param perId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/updateUserPerId")
    @ResponseBody
    public Map<String, Object> updateUserPerId(Long userId, String perId) {
        try {
            User user = new User();
            user.setId(userId);
            user.setTimeCardNo(perId);
            userBiz.update(user);
            return this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 跳转到导入学员名单的页面
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toCreateUserExcelRecord")
    public String toCreateUserExcelRecord(HttpServletRequest request) {
        try {
            List<ClassType> classTypeList = classTypeBiz.find(null, " status=1 order by id desc");
            request.setAttribute("classTypeList", classTypeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/create_UserExcelRecord";
    }

    /**
     * 导入学员名单
     *
     * @param request
     * @param userExcelRecord
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/createUserExcelRecord")
    @ResponseBody
    public Map<String, Object> createUserExcelRecord(HttpServletRequest request, @ModelAttribute("userExcelRecord") UserExcelRecord userExcelRecord) {
        try {
            String errorInfo = userExcelRecordBiz.validate(userExcelRecord);
            if (!StringUtils.isTrimEmpty(errorInfo)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            }
            errorInfo = userBiz.updateUser(userExcelRecord, request);
            if (!StringUtils.isTrimEmpty(errorInfo)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请上传规定的学员名单的excel文件", null);
            }
            userExcelRecord.setClassTypeName(classTypeBiz.findById(userExcelRecord.getClassTypeId()).getName());
            userExcelRecord.setClassName(classesBiz.findById(userExcelRecord.getClassId()).getName());
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            userExcelRecord.setUserType(Integer.parseInt(userMap.get("userType")));
            userExcelRecord.setSysuserId(Long.parseLong(userMap.get("id")));
            userExcelRecordBiz.save(userExcelRecord);
            return this.resultJson(ErrorCode.SUCCESS, "导入成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 导入学员名单列表
     */
    @RequestMapping(ADMIN_PREFIX + "/userExcelRecordList")
    public String userExcelRecordList(HttpServletRequest request, @ModelAttribute("userExcelRecord") UserExcelRecord userExcelRecord, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String recordCreateTime = request.getParameter("recordCreateTime");

            List<ClassType> classTypeList = classTypeBiz.find(null, " status=1 order by id desc");
            request.setAttribute("classTypeList", classTypeList);

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
            if (!StringUtils.isTrimEmpty(recordCreateTime)) {
                whereSql.append(" and to_days(createTime)=to_days('" + recordCreateTime + "')");
            }
            pagination.setRequest(request);
            userExcelRecordList = userExcelRecordBiz.find(pagination, whereSql.toString());
            request.setAttribute("userExcelRecordList", userExcelRecordList);
            request.setAttribute("recordCreateTime", recordCreateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/userExcelRecord_list";
    }

    //打开文档
    @RequestMapping("/open/openDocuments")
    public String openDocuments(HttpServletRequest request, HttpServletResponse response, @RequestParam("userExcelRecordId") Long userExcelRecordId) {
        try {
            UserExcelRecord userExcelRecord = userExcelRecordBiz.findById(userExcelRecordId);
            String dirPath = userExcelRecord.getUrl();
            String filePath = dirPath.substring(dirPath.indexOf("/") + 2);
            filePath = filePath.substring(filePath.indexOf("/") + 1);
            filePath = filePath.substring(0, filePath.indexOf("."));
            String realPath = request.getServletContext().getRealPath("");
            String pathUrl = realPath + "/" + filePath + "/" + dirPath.substring((dirPath.lastIndexOf("/") + 1), dirPath.length());
            if (!FileUtils.fileExists(pathUrl)) {
                FileUtils.getInternetRes(realPath + "/" + filePath, dirPath, dirPath.substring((dirPath.lastIndexOf("/") + 1), dirPath.length()));
            }
            String substring = realPath.substring(realPath.lastIndexOf("\\") + 1);
            logger.info("substring:=========" + substring);
            if (!substring.equals("webapp")) {
                pathUrl = "file://" + pathUrl;
            }

            Map<String, String> map = SysUserUtils.getLoginSysUser(request);
            PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
            poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
            poCtrl.setAllowCopy(false);//禁止拷贝
            poCtrl.setMenubar(false);//隐藏菜单栏
            poCtrl.setOfficeToolbars(false);//隐藏Office工具条
            poCtrl.webOpen(pathUrl, OpenModeType.xlsNormalEdit, map.get("userName"));
            request.setAttribute("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/openDocuments";
    }

    /**
     * 删除学员
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/userDelete")
    @ResponseBody
    public Map<String, Object> deleteUser(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            UserExcelRecord userExcelRecord = new UserExcelRecord();
            userExcelRecord.setId(id);
            userExcelRecord.setStatus(0);
            userExcelRecordBiz.update(userExcelRecord);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.SYS_ERROR_MSG, ErrorCode.SUCCESS_MSG, null);
        }
        return json;
    }

    /**
     * 使用新表格导出学员列表(按照某班次分组别找)
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/newUserListExcel")
    public void newUserListExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("classTypeId") Long classTypeId, @RequestParam("classId") Long classId) {
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
     * 使用新表格导出学员列表(按照某班次分组别找)/改回旧的
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/newUserListExcelTwo")
    public void newUserListExcelTwo(HttpServletRequest request, HttpServletResponse response, @RequestParam("classTypeId") Long classTypeId, @RequestParam("classId") Long classId) {
        try {
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/userList");
            String expName = "培训报名名单_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] headName = {"班型", "班次", "姓名", "学号", "身份证号", "性别", "年龄", "单位", "职务职称", "级别", "政治面貌", "学历"};
            List<File> srcfile = userBiz.newUserListExcelTwo(request, dir, headName, expName, classId);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据条件导出正式学员列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(ADMIN_PREFIX + "/userListExcelByCondition")
    public void userListExcelByCondition(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/userList");
            String expName = "培训报名名单_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] headName = {"姓名", "性别", "年龄", "政治面貌", "民族", "单位", "级别", "职务职称", "学历", "联系电话", "身份证号", "报名时间", "组别", "状态", "备注"};
            List<File> srcfile = userBiz.userListExcelByCondition(request, dir, headName, expName);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据条件导出正式学员列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(ADMIN_PREFIX + "/userListExcelByGraCondition")
    public void userListExcelByGraCondition(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/userList");
            String expName = "培训报名名单_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] headName = {"姓名", "性别", "年龄", "政治面貌", "民族", "单位", "级别", "职务职称", "联系电话", "身份证号", "报名时间", "组别", "状态", "备注"};
            List<File> srcfile = userBiz.userListExcelByGraCondition(request, dir, headName, expName);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 一键通过某班次所有学员
     *
     * @param classId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/oneButtonPassOfOneClass")
    @ResponseBody
    public Map<String, Object> oneButtonPassOfOneClass(Long classId) {
        try {
            List<User> userList = userBiz.find(null, " status in (2,3,4,5,6)");
            if (userList != null && userList.size() > 0) {
                for (User user : userList) {
                    user.setStatus(1);
                    userBiz.update(user);
                }
            }
            return this.resultJson(ErrorCode.SUCCESS, "更新成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }


    /**
     * @param request
     * @return java.lang.String
     * @Description 学员所属班次列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/studentClassList")
    public String studentClassList(HttpServletRequest request) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            String roleIds = baseHessianService.queryUserRolesByUserId(Long.parseLong(userMap.get("id")));

            //学员查看不了同班同学的身份证号
            if (!StringUtils.isEmpty(roleIds)) {
                if (roleIds.indexOf(StudentCommonConstants.STUDENT_ROLE_ID.toString()) != -1) {
                    request.setAttribute("isUser", "true");
                }
            }
            User user = userBiz.findById(Long.parseLong(userMap.get("linkId")));
            String whereSql = " status in (1,4,7,8) and classId=" + user.getClassId();
            List<User> userList = userBiz.find(null, whereSql + " order by unitId");
            if (userList != null) {
                for (User u : userList) {
                    Classes c = classesBiz.findById(u.getClassId());
                    if (c != null)
                        u.setTeacherName(c.getTeacherName());
                }
            }
            Classes classes = classesBiz.findById(user.getClassId());
            Map<String, String> teacher = hrHessianService.queryEmployeeById(classes.getTeacherId());
            Map<String, String> deputyTeacher = hrHessianService.queryEmployeeById(classes.getDeputyTeacherId());
            request.setAttribute("teacher", teacher);
            request.setAttribute("deputyTeacher", deputyTeacher);
            addEmailAndMobileToUserList(userList);
            addBirthdayToUserList(userList);
            request.setAttribute("classes", classes);
            request.setAttribute("userList", userList);
            request.setAttribute("userMap", groupUserList(userList));
            request.setAttribute("noteUsers", getNoteUser(userList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/user_class_list";
    }


    /**
     * 按分组信息将学员分组
     *
     * @param users
     * @return
     */
    private Map<Integer, List<User>> groupUserList(List<User> users) {
        if (null == users || users.size() <= 0) {
            return null;
        }
        for (User user : users) {
            if (null == user.getGroupId()) {
                user.setGroupId(0);
            }
        }
        Map<Integer, List<User>> map = users.stream().collect(Collectors.groupingBy(User::getGroupId));
        return map;
    }

    /**
     * 获取班委信息
     *
     * @param users
     * @return
     */
    private List<User> getNoteUser(List<User> users) {
        List<User> noteUsers = users.stream().filter(user -> null != user.getNote() && user.getNote().length() > 0).collect(Collectors.toList());
        return noteUsers;
    }


    /**
     * 导出 批量导入学员模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(ADMIN_PREFIX + "/getBatchImportUserModel")
    public void getBatchImportUserModel(HttpServletRequest request, HttpServletResponse response) {
        try {
            File file = userBiz.getBatchImportUserModel(request);
			/*List<File> fileList=new ArrayList<File>();
			fileList.add(file);
			String dir = request.getSession().getServletContext().getRealPath("/excelfile/batchImportUserModel");
			String expName = "批量导入用户模板";
			FileExportImportUtil.createRar(response, dir, fileList, expName);// 生成的多excel的压缩包*/
            FileExportImportUtil.exportFile(response, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 一个人不能在同一班次
     *
     * @return
     */
    /*public String validateSameStudentInOneClass(User user) {
		if(user.getClassId()==null||user.getClassId().equals(0L)){
			return "请选择班次";
		}
		if(user.getIdNumber()==null||user.getIdNumber().equals("")){
			return "请填写身份证号";
		}
		List<User> userList=userBiz.find(null," classId="+user.getClassId()+" and idNumber='"+user.getIdNumber()+"'");
		if(userList!=null&&userList.size()>0){
			return "您已参加该班次，请选择其它班次。";
		}
        return null;
    }*/
    @RequestMapping(ADMIN_PREFIX + "/zuoqutu3")
    public String zuoqutu3() {
        return "/admin/user/zuoqutu3";
    }

    /**
     * 跳转到单位批量再次报名的页面
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toBatchSignByUnit")
    public String toBatchSignByUnit(HttpServletRequest request, Long classId) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            request.setAttribute("unitId", userMap.get("unitId"));
            request.setAttribute("classId", classId);
        } catch (Exception e) {
            logger.error("UserController.toBatchSignByUnit", e);
        }
        return "/admin/user/batch_sign_by_unit";
    }

    /**
     * 由单位来进行批量再次报名
     *
     * @param request
     * @param userIds
     * @param classId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/batchSignByUnit")
    @ResponseBody
    public Map<String, Object> batchSignByUnit(HttpServletRequest request, String userIds, Long classId) {
        try {
            if (classId == null || Long.valueOf(0).equals(classId)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请选择班次", null);
            }
            if (StringUtils.isTrimEmpty(userIds)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "请选择干部", null);
            }
            Classes classes = classesBiz.findById(classId);
            Long classTypeId = classes.getClassTypeId();
            String[] userIdArray = userIds.split(",");
            Timestamp now = new Timestamp(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            String year = calendar.get(Calendar.YEAR) + "";
            String month = (calendar.get(Calendar.MONTH) + 1) + "";
            if (month.length() == 1) {
                month = "0" + month;
            }
            for (String userId : userIdArray) {
                User user = userBiz.findById(Long.parseLong(userId));
                Long oldId = user.getId();


                // 实现再次报名
                user.setId(null);
                user.setClassTypeId(classTypeId);
                user.setClassTypeName(classes.getClassType());
                user.setClassId(classes.getId());
                user.setClassName(classes.getName());

                int userNum = userBiz.count(" status=1 and classId=" + classes.getId());
                user.setSerialNumber(userBiz.calculateSerialNumber((long) userNum));
                user.setStudentId(year + month + user.getClassTypeId() + classes.getClassNumber() + user.getSerialNumber());

                user.setCreateTime(now);
                user.setUpdateTime(now);
                user.setStatus(2);
                userBiz.save(user);


                // 查询学员的旧手机号
                String phone = "";
                List<Map<String, String>> maps = baseHessianService.querySysUser(3, oldId);
                if (!CollectionUtils.isEmpty(maps)) {
                    phone = maps.get(0).get("mobile");

                    //修改关联user
                    baseHessianService.updateSysUserLink(maps.get(0).get("id"), user.getId());
                }
                // 删了原来的wifi账号 再次开通
                dpWebService.delWifi(phone);
                dpWebService.saveWifiUser(phone, user.getName(), classes.getEndTime(), user.getId());

            }
            return this.resultJson(ErrorCode.SUCCESS, "导入成功", null);
        } catch (Exception e) {
            logger.error("UserController.batchSignByUnit", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 弹出框形式的学员列表(多选)
     * @author 李帅雷
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/userListForMultiSelect")
    public String userListForMultiSelect(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            StringBuffer sb = request.getRequestURL().append("?pagination.currentPage=" + pagination.getCurrentPage());
            List<User> userList = null;
            String whereSql = " status!=0";
            User user = new User();
            String name = request.getParameter("name");
            if (!StringUtils.isTrimEmpty(name)) {
                whereSql += " and name like '%" + name + "%'";
                user.setStudentId(name);
                sb.append("&studentId=" + name);
            }
            String classId = request.getParameter("classId");
            if (!StringUtils.isTrimEmpty(classId)) {
                whereSql += " and classId=" + classId;
                user.setClassId(Long.parseLong(classId));
                sb.append("&classId=" + classId);
            }
            String unitId = request.getParameter("unitId");
            if (!StringUtils.isTrimEmpty(unitId)) {
                whereSql += " and unitId=" + unitId;
                user.setUnitId(Long.parseLong(unitId));
                sb.append("&unitId=" + unitId);
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
        return "/admin/user/user_list_forMultiSelect";
    }

    /**
     * @param request
     * @param
     * @return java.lang.String
     * @Description 班主任的学员列表，因为一个班主任可能是多个班次的班主任，所以选最新班次的。可能之后会改
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/userListOfCurrentTeacher")
    public String userListOfCurrentTeacher(HttpServletRequest request) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);

            // 只有教职工能查看
            if (!userMap.get("userType").equals("2")) {
                return null;
            }

            // 该班主任教的最新的一个班级
            List<Classes> classesList = classesBiz.find(null,
                    " teacherId=" + userMap.get("linkId")+" or deputyTeacherId="+userMap.get("linkId") + " order by id desc limit 1");
            List<User> userList = null;

            // 查询确认参加班次的学员
            // 轮训人员通过的学员
            // 毕业的学员
            // 8的含义未知
            if (classesList != null && classesList.size() > 0) {
                userList = userBiz.find(null, " status in (1,4,7,8) and classId=" + classesList.get(0).getId() + " order by importsort desc");
                if (userList != null) {
                    for (User u : userList) {
                        u.setTeacherName(classesList.get(0).getTeacherName());
                    }
                }
                Map<String, String> teacher = hrHessianService.queryEmployeeById(classesList.get(0).getTeacherId());
                Map<String, String> deputyTeacher = hrHessianService.queryEmployeeById(classesList.get(0).getDeputyTeacherId());
                request.setAttribute("teacher", teacher);
                request.setAttribute("deputyTeacher", deputyTeacher);
                request.setAttribute("classes", classesList.get(0));
            }
            this.addEmailAndMobileToUserList(userList);
            this.addBirthdayToUserList(userList);
            request.setAttribute("userList", userList);

            request.setAttribute("userMap", groupUserList(userList));
            request.setAttribute("noteUsers", getNoteUser(userList));
            request.setAttribute("start", "0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/userlist_of_currentteacher";
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 已毕业学员列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/graduatedUserList")
    public String graduatedUserList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            List<ClassType> classTypeList = classTypeBiz.find(null, " 1=1 order by id desc");
            request.setAttribute("classTypeList", classTypeList);

            String whereSql = " status=7";
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
            String groupId = request.getParameter("groupId");
            request.setAttribute("groupId", groupId);
            if (!StringUtils.isTrimEmpty(groupId)) {
                whereSql += " and groupId=" + groupId;
            }
            String unit = request.getParameter("unit");
            if (!StringUtils.isTrimEmpty(unit)) {
                whereSql += " and unit like '%" + unit + "%'";
                user.setUnit(unit);
            }
            String business = request.getParameter("business");
            if (!StringUtils.isTrimEmpty(business)) {
                whereSql += " and business=" + business;
                user.setBusiness(Integer.parseInt(business));
            }
            pagination.setRequest(request);
            List<User> userList = userBiz.find(pagination, whereSql + " order by unitId,business ");
            addEmailAndMobileToUserList(userList);
            addBirthdayToUserList(userList);
            request.setAttribute("userList", userList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("user", user);
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            request.setAttribute("sysUser", userMap);
            request.setAttribute("showIdnumber", userMap.get("userType").equals("1") ? true : false);
            //查询用户角色

            //查询用户角色
            String roleIds = baseHessianService.queryUserRolesByUserId(SysUserUtils.getLoginSysUserId(request));
            if (!StringUtils.isEmpty(roleIds)) {
                if (roleIds.indexOf("26") == -1) {
                    request.setAttribute("flag", "true");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/graduated_user_list";
    }

    /**
     * 跳转到更新单位管理员密码的页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdateUnitUserPassword")
    public String toUpdateUnitUserPassword(HttpServletRequest request, Long id) {
        try {
            Unit unit = unitBiz.findById(id);
            request.setAttribute("unit", unit);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/unit/update_unitUser_password";
    }

    /**
     * 更新单位管理员密码
     *
     * @param request
     * @param unitId
     * @param password
     * @param confirmPassword
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/updateUnitUserPassword")
    @ResponseBody
    public Map<String, Object> updateUnitUserPassword(HttpServletRequest request, Long unitId, String password, String confirmPassword) {
        try {
            if (password == null || "".equals(password)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                        "密码不能为空", null);
            }
            if (!password.equals(confirmPassword)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                        "密码与确认密码不一样，请重新填写!", null);
            }
            int result = baseHessianService.updateUnitUserPassword(unitId, password);
            if (result == 1) {
                return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                        null);
            } else {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                        "更新失败，请稍后重试！", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 用户统计列表
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/userStatisticList")
    public String userStatisticList(HttpServletRequest request, @ModelAttribute("classTypeStatistic") ClassTypeStatistic classTypeStatistic) {
        try {
            List<ClassTypeStatistic> classTypeStatisticList = classTypeStatisticDao.queryClassTypeCount(classTypeStatistic);
            String number = "";
            String name = "";
            if (ObjectUtils.isNotNull(classTypeStatisticList)) {
                for (ClassTypeStatistic c : classTypeStatisticList) {
                    number += c.getNum() + ",";
                    name += '\"' + c.getClassTypeName() + "-" + c.getClassName() + '\"' + ",";
                }
                if (!StringUtils.isTrimEmpty(number)) {
                    number = number.substring(0, number.length() - 1);
                    request.setAttribute("number", number);
                }
                if (!StringUtils.isTrimEmpty(name)) {
                    name = name.substring(0, name.length() - 1);
                    request.setAttribute("name", name);
                }
            }

            request.setAttribute("classTypeStatistic", classTypeStatistic);
            List<ClassType> classTypeList = classTypeBiz.find(null, " status=1 order by id desc");
            request.setAttribute("classTypeList", classTypeList);
            if (ObjectUtils.isNotNull(classTypeStatistic.getClassTypeId())) {
                List<Classes> classesList = classesBiz.find(null, " status=1 and classTypeId=" + classTypeStatistic.getClassTypeId() + " order by id desc");
                request.setAttribute("classesList", classesList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/user_statistic_list";
    }

    @RequestMapping(ADMIN_PREFIX + "/batchImportUnit")
    public String batchImportUnit(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        try {
            String errorInfo = userBiz.batchImportUnit(myFile, request);
            if (StringUtils.isTrimEmpty(errorInfo)) {
                errorInfo = "导入成功";
            }
            request.setAttribute("errorInfo", errorInfo);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorInfo", "系统繁忙，请稍后再试。");
        }
        return "/admin/user/batch_import_user";
//		return "redirect:/admin/jiaowu/user/newUserList";
    }


    /**
     * @param request
     * @param
     * @return java.lang.String
     * @Description 班主任的学员列表，因为一个班主任可能是多个班次的班主任，所以选最新班次的。可能之后会改
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/userListOfCurrentTeacherByClassId")
    public String userListOfCurrentTeacherByClassId(HttpServletRequest request, @RequestParam("classId") Long classId) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);

            // 只有教职工能查看
            if (!userMap.get("userType").equals("2")) {
                return null;
            }

            // 该班主任教的最新的一个班级
//            List<Classes> classesList = classesBiz.find(null,
//                    " teacherId=" + userMap.get("linkId") + " order by id desc limit 1");
            List<User> userList = null;
            Classes classes = classesBiz.findById(classId);
            // 查询确认参加班次的学员
            // 轮训人员通过的学员
            // 毕业的学员
            // 8的含义未知
            if (ObjectUtils.isNotNull(classes)) {
                userList = userBiz.find(null, " status in (1,4,7,8) and classId=" + classId + " order by importsort desc");
                if (userList != null) {
                    for (User u : userList) {
                        if (!StringUtils.isEmpty(classes.getTeacherName())) {
                            u.setTeacherName(classes.getTeacherName());
                        } else {
                            u.setTeacherName("");
                        }
                    }
                }
                Map<String, String> teacher = hrHessianService.queryEmployeeById(classes.getTeacherId());
                Map<String, String> deputyTeacher = hrHessianService.queryEmployeeById(classes.getDeputyTeacherId());
                request.setAttribute("teacher", teacher);
                request.setAttribute("deputyTeacher", deputyTeacher);
                request.setAttribute("classes", classes);
            }
            this.addEmailAndMobileToUserList(userList);
            this.addBirthdayToUserList(userList);
            request.setAttribute("userList", userList);

            request.setAttribute("userMap", groupUserList(userList));
            request.setAttribute("noteUsers", getNoteUser(userList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/user/userlist_of_currentteacher";
    }
}
