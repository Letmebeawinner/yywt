package com.jiaowu.controller.classes;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.google.gson.reflect.TypeToken;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.*;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.unit.UnitBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.biz.userWorkDayData.UserWorkDayDataBiz;
import com.jiaowu.dao.classes.ClassTypeStatisticDao;
import com.jiaowu.dao.classes.ClassesStatisticDao;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.ClassTypeStatistic;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.classes.ClassesStatistic;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.meeting.Meeting;
import com.jiaowu.entity.unit.Unit;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.userWorkDayData.UserWorkDayData;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 班次Controller
 *
 * @author 李帅雷
 */
@Controller
public class ClassesController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ClassesController.class);

    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private ClassTypeBiz classTypeBiz;
    @Autowired
    private CourseArrangeBiz courseArrangeBiz;
    @Autowired
    private ClassesStatisticDao classesStatisticDao;
    @Autowired
    private ClassTypeStatisticDao classTypeStatisticDao;
    @Autowired
    private UnitBiz unitBiz;
    @Autowired
    private BaseHessianService baseHessianService;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private HrHessianService hrHessianService;
    @Autowired
    private HqHessionService hqHessionService;
    @Autowired
    private SmsHessianService smsHessianService;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private UserWorkDayDataBiz userWorkDayDataBiz;

    private static final String ADMIN_PREFIX = "/admin/jiaowu/class";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/class";

    @InitBinder({"classes"})
    public void initClasses(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("classes.");
    }


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    @RequestMapping("/admin/jiaowu/jiaowuIndex")
    public String jiaowuIndex() {
        return "/admin/jiaowuIndex";
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 跳转到创建班次的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/createClass")
    public String createClass(HttpServletRequest request) {
        try {
            setAttributeClassTypeList(request);
            hqHessionService.changeMeetingStatusWhenChose();
        } catch (Exception e) {
            logger.info("ClassesController.createClass", e);
        }
        return "/admin/class/create_class";
    }


    /**
     * @param request
     * @param classes
     * @return java.util.Map
     * @Description 创建班次
     */
    @RequestMapping(ADMIN_PREFIX + "/doCreateClass")
    @ResponseBody
    public Map<String, Object> doCreateClass(HttpServletRequest request,
                                             @ModelAttribute("classes") Classes classes) {
        Map<String, Object> json = null;
        try {
            json = validateClasses(classes);
            if (json != null) {
                return json;
            }
            ClassType classType = classTypeBiz.findById(classes
                    .getClassTypeId());
            classes.setClassNumber(classesBiz.getClassNumber(classes));
            classes.setClassType(classType.getName());

            String note = "";
            if(!StringUtils.isTrimEmpty(classes.getNote())){
                note = classes.getNote();
            }
            //判断会场是否有冲突
            Map<String, Boolean> map = hqHessionService.whetherToUseDuringTheTimePeriod(null,classes.getClassId(),classes.getStartTime(),classes.getEndTime(),note);
            if(map.get("whetherUse")){
                json = this.resultJson(ErrorCode.ERROR_DATA_NULL, "该教室已被使用，请选择其它教室",null);
                return json;
            }

            //修改会场使用状态
            hqHessionService.updateMeetingStatus(classes.getClassId(),2);
            //增加会场使用记录
            Map<String, String> resultData = hqHessionService.addMeetingRecordFromOther(SysUserUtils.getLoginSysUserId(request),classes.getClassId(),2,classes.getId(),classes.getClassName(),classes.getStartTime(),classes.getEndTime(),note);
            classes.setRecordId(Long.valueOf(resultData.get("meetingRecordId")));
            classesBiz.save(classes);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,null);
        } catch (Exception e) {
            logger.info("ClassesController.doCreateClass", e);
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 跳转到更新班次的页面上
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/updateClass")
    public String updateClass(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            Classes classes = classesBiz.findById(id);
            request.setAttribute("classes", classes);
            setAttributeClassTypeList(request);
            hqHessionService.changeMeetingStatusWhenChose();
        } catch (Exception e) {
            logger.info("ClassesController.updateClass", e);
        }
        return "/admin/class/update_class";
    }

    /**
     * @param request
     * @param classes
     * @return java.util.Map
     * @Description 更新班次
     */
    @RequestMapping(ADMIN_PREFIX + "/doUpdateClass")
    @ResponseBody
    public Map<String, Object> doUpdateClass(HttpServletRequest request,
                                             @ModelAttribute("classes") Classes classes) {
        Map<String, Object> json = null;
        try {
            json = validateClasses(classes);
            if (json != null) {
                return json;
            }
            ClassType classType = classTypeBiz.findById(classes
                    .getClassTypeId());
            classes.setClassType(classType.getName());

            String type = request.getParameter("type");
            if("1".equals(type) && classes.getRecordId() != null && classes.getRecordId().intValue() > 0){
                String note = "";
                if(!StringUtils.isTrimEmpty(classes.getNote())){
                    note = classes.getNote();
                }
                Map<String, Boolean> result = hqHessionService.whetherToUseDuringTheTimePeriod(classes.getRecordId(),classes.getClassId(),classes.getStartTime(),classes.getEndTime(),note);
                if(result.get("whetherUse")){
                    json = this.resultJson(ErrorCode.ERROR_DATA_NULL, "该教室已被使用，请选择其它教室",null);
                    return json;
                }
                //修改会场使用状态
                hqHessionService.updateMeetingStatus(classes.getClassId(),2);
                hqHessionService.updateMeetingRecordStatus(classes.getRecordId(),classes.getClassId(),0,classes.getId(),classes.getName(),classes.getStartTime(),classes.getEndTime(),note);
            }
            classesBiz.update(classes);
            User u = new User();
            u.setClassName(classes.getName());
//            UserWorkDayData ud = new UserWorkDayData();
//            ud.setClassName(classes.getName());
            userBiz.updateByStrWhere(u,"classId="+classes.getId());
//            userWorkDayDataBiz.updateByStrWhere(ud,"classId="+classes.getId());
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 班次列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/classList")
    public String classList(HttpServletRequest request,
                            @ModelAttribute("pagination") Pagination pagination) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);

            //查询用户角色
            Long userId = SysUserUtils.getLoginSysUserId(request);
            String roleIds = baseHessianService.queryUserRolesByUserId(Long.parseLong(userMap.get("id")));


            Classes classes = new Classes();
            String whereSql = classesBiz.addCondition(request, classes);
            //如果当前登录人是教师角色，则只获取该班主任对应班次。
            if (userMap.get("userType").equals("2")) {
                if (!StringUtils.isEmpty(roleIds)) {
                    if (roleIds.indexOf("30") != -1) {
                        whereSql += " and teacherId=" + userMap.get("linkId");
                    }
                }
                if (!StringUtils.isEmpty(roleIds)) {
                    if (roleIds.indexOf("29") == -1) {
                        request.setAttribute("teacher", true);
                    }
                }
            }
            whereSql+=" order by startTime desc";
            pagination.setRequest(request);
            List<Classes> classList = classesBiz.find(pagination, whereSql);
            List<ClassesStatistic> classesStatisticList = classesStatisticDao.queryClassPersonNum();

            if (ObjectUtils.isNotNull(classList) && ObjectUtils.isNotNull(classesStatisticList)) {
                for (Classes classes1 : classList) {
                    for (ClassesStatistic classesStatistic : classesStatisticList) {
                        if (classes1.getId().equals(classesStatistic.getClassId())) {
                            classes1.setTotalNum(classesStatistic.getNum());
                        }
                    }
                }
            }
//            if (userMap.get("userType").equals("2")) {
//                request.setAttribute("classList", (classList != null && classList.size() > 0) ? classList.subList(0, 1) : null);
//            } else {
            request.setAttribute("classList", classList);
//            }

            request.setAttribute("pagination", pagination);
            request.setAttribute("classes", classes);

            setAttributeClassTypeList(request);
        } catch (Exception e) {
            e.printStackTrace();
            return this.setErrorPath(request, e);
        }
        return "/admin/class/class_list";
    }

    /**
     * 发送短信通知教师来上课
     * @param request
     * @param classId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/sendMobile")
    @ResponseBody
    public Map<String, Object> classList(HttpServletRequest request, @RequestParam("classId") Long classId) {
        Map<String, Object> resultMap = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            List<CourseArrange> courseArrangeList = courseArrangeBiz.find(null, " classId=" + classId);
            if(ObjectUtils.isNotNull(courseArrangeList)){
                for(CourseArrange courseArrange : courseArrangeList){
                    String time = simpleDateFormat.format(courseArrange.getStartTime());
                    String teachArr [] = courseArrange.getTeacherIds().split(",");
                    if(teachArr != null && teachArr.length > 1){
                        int count = courseArrange.getTeacherIds().indexOf(",");
                        teachArr = courseArrange.getTeacherIds().substring(count+1,courseArrange.getTeacherIds().length()).split(",");
                    }
                    if(teachArr != null && teachArr.length > 0){
                        for(String id : teachArr){
                            StringBuilder sb = new StringBuilder();
                            Map<String, Object> map = hrHessianService.getEmployeeListBySql(null, "status = 1 and id = "+Long.valueOf(id));
                            List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
                            if(ObjectUtils.isNotNull(teacherList) && !StringUtils.isTrimEmpty(teacherList.get(0).get("mobile"))){
                                sb.append("请您"+ time + "来党校" + courseArrange.getClassroomName()+"上课");
                                Map<String, String> map1 = new HashedMap();
                                map1.put("mobiles", teacherList.get(0).get("mobile").toString());
                                map1.put("context", sb.toString());
                                map1.put("sendType", "2");
                                map1.put("sendUserId", SysUserUtils.getLoginSysUserId(request).toString());
                                Boolean isok = smsHessianService.sendMsg(map1);
                            }
                        }
                    }
                }
            }
            resultMap = this.resultJson(ErrorCode.SUCCESS, "发送成功", null);
        } catch (Exception e) {
            logger.error("OaCarApplyController.startCarApplyWorkFlow", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     *
     * 查询班次下所有课程讲师名称
     * @param request
     * @param classId
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/queryTeacher")
    @ResponseBody
    public Map<String, Object> queryTeacher(HttpServletRequest request, @RequestParam("classId") Long classId) {
        Map<String, Object> resultMap = null;
        try {
            List<CourseArrange> courseArrangeList = courseArrangeBiz.find(null, " classId=" + classId);
            if(ObjectUtils.isNotNull(courseArrangeList)){
                StringBuffer stringBuffer = new StringBuffer();
                for(CourseArrange courseArrange : courseArrangeList){
                    String teachArr [] = courseArrange.getTeacherIds().split(",");
                    if(teachArr != null && teachArr.length > 1){
                        int count = courseArrange.getTeacherIds().indexOf(",");
                        teachArr = courseArrange.getTeacherIds().substring(count+1,courseArrange.getTeacherIds().length()).split(",");
                    }
                    if(teachArr != null && teachArr.length > 0){
                        for(String id : teachArr){
                            if(stringBuffer.toString().indexOf(id) == -1){
                                stringBuffer.append(id).append(",");
                            }
                        }
                    }
                }
                String ids = stringBuffer.toString();
                Map<String, Object> map = hrHessianService.getEmployeeListBySql(null, "status = 1 and id in ("+ids.substring(0,ids.length()-1)+")");
                List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
                if(ObjectUtils.isNotNull(teacherList)){
                    stringBuffer = new StringBuffer();
                    for(Map<String, String> m : teacherList){
                        stringBuffer.append(m.get("name")).append(",");
                    }
                }
                ids = stringBuffer.toString();
                resultMap = this.resultJson(ErrorCode.SUCCESS, ids.substring(0,ids.length()-1), null);
            }else{
                resultMap = this.resultJson(ErrorCode.ERROR_DATA_NULL, "该班次下暂时没有讲师", null);
            }
        } catch (Exception e) {
            logger.error("ClassesController.queryTeacher", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }



    /**
     * 查询各个班次的报名人数
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/queryClassPersonNum")
    public ModelAndView queryClassPersonNum() {
        ModelAndView mv = new ModelAndView("/admin/class/class_statistic_list");
        try {
            List<ClassesStatistic> classesStatisticList = classesStatisticDao.queryClassPersonNum();
            mv.addObject("classesStatisticList", classesStatisticList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


    /**
     * 查询各个班次的报名人数
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/queryUnitPersonNum")
    public ModelAndView queryUnitPersonNum(HttpServletRequest request, @RequestParam("classId") String classId, @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/admin/class/class_unit_statistic_list");
        try {
            pagination.setRequest(request);
            Classes classes = classesBiz.findById(Long.parseLong(classId));
            mv.addObject("classes", classes);

            List<ClassesStatistic> classesStatisticList = classesStatisticDao.queryUnitPersonNum(classId);

            List<ClassesStatistic> classesTypeStatisticList = classesStatisticDao.queryUnitClassTypeNum(classes.getClassTypeId().toString());


            List<Unit> unitList = unitBiz.find(pagination, "1=1");
            if (ObjectUtils.isNotNull(classesStatisticList) && ObjectUtils.isNotNull(unitList) && ObjectUtils.isNotNull(classesTypeStatisticList)) {
                for (Unit c : unitList) {
                    for (ClassesStatistic statistic : classesStatisticList) {
                        if (c.getId().equals(statistic.getId())) {
                            c.setNum(statistic.getNum());
                        }
                    }
                }

                for (Unit u : unitList) {
                    for (ClassesStatistic statistic : classesTypeStatisticList) {
                        if (u.getId().equals(Long.parseLong(String.valueOf(statistic.getUnitId())))) {
                            u.setClassNum(statistic.getNum());
                        }
                    }
                }

            }
            mv.addObject("unitList", unitList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 弹出框形式的班次列表
     * @author 李帅雷
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/classListForSelect")
    public String classListForSelect(HttpServletRequest request,
                                     @ModelAttribute("pagination") Pagination pagination) {
        try {
            Classes classes = new Classes();
            String whereSql = classesBiz.addCondition(request, classes);
            pagination.setCurrentUrl(classesBiz.getCurrentUrl(request, pagination, classes));
            List<Classes> classList = classesBiz.find(pagination, whereSql);
            request.setAttribute("classList", classList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("classes", classes);
            setAttributeClassTypeList(request);
        } catch (Exception e) {
            e.printStackTrace();
            return this.setErrorPath(request, e);
        }
        return "/admin/class/class_list_for_select";
    }

    /**
     * @param request
     * @return java.util.Map
     * @Description 删除班次
     */
    @RequestMapping(ADMIN_PREFIX + "/deleteClass")
    @ResponseBody
    public Map<String, Object> deleteClass(HttpServletRequest request,
                                           @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            Classes classes = classesBiz.findById(id);
            classes.setStatus(0);
            classesBiz.update(classes);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param classTypeId
     * @return java.util.Map
     * @Description 通过班型ID获取班次列表
     */
    @RequestMapping(ADMIN_PREFIX + "/getClassListByClassType")
    @ResponseBody
    public Map<String, Object> getClassListByClassType(
            HttpServletRequest request,
            @RequestParam("classTypeId") Long classTypeId) {
        Map<String, Object> json = null;
        try {
            List<Classes> classList = classesBiz.find(null, " classTypeId="
                    + classTypeId + " and status=1");
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    classList);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 班次课表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/classActivity")
    public String classActivity(HttpServletRequest request,
                                @ModelAttribute("pagination") Pagination pagination, @RequestParam("classId") Long classId) {
        try {

            String whereSql = " classId=" + classId;
            CourseArrange courseArrange = new CourseArrange();
            courseArrange.setClassId(classId);
            whereSql += classesBiz.addCondition(request, courseArrange);
            pagination.setRequest(request);
            List<CourseArrange> courseArrangeList = courseArrangeBiz.find(
                    pagination, whereSql);
            classesBiz.setCourseInfoAndDeleteUselessCourseArrange(courseArrangeList);
            request.setAttribute("courseArrangeList", courseArrangeList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("courseArrange", courseArrange);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/class/class_activity";
    }

    /**
     * 验证班次信息
     *
     * @param classes
     * @return
     */
    public Map<String, Object> validateClasses(Classes classes) {
        Map<String, Object> errorInfo = null;
        if (StringUtils.isTrimEmpty(classes.getName())) {
            errorInfo = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                    "名称不能为空", null);
            return errorInfo;
        }
        /*if (classes.getTeacherId() == null || classes.getTeacherId() <= 0) {
			errorInfo = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
					"教师不能为空", null);
			return errorInfo;
		}*/
        if (classes.getStartTime() == null) {
            errorInfo = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                    "开始时间不能为空", null);
            return errorInfo;
        }
        if (classes.getEndTime() == null) {
            errorInfo = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                    "结束时间不能为空", null);
            return errorInfo;
        }
        if (classes.getStartTime().getTime() >= classes.getEndTime().getTime()) {
            errorInfo = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
                    "开始时间不能大于结束时间", null);
            return errorInfo;
        }
        Long registration = classes.getRegistration();
        if(registration.equals("1")){
            if (classes.getMaxNum() == null || classes.getMaxNum().equals(0L)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "最大人数填写不正确", null);
            }
        }
//        if (classes.getSignEndTime() == null && classes.getRegistration() ==1) {
//            return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,
//                    "请填写报名截止时间", null);
//        }
        return null;
    }

    /**
     * 向页面返回班型列表
     *
     * @param request
     */
    public void setAttributeClassTypeList(HttpServletRequest request) {
        List<ClassType> classTypeList = classTypeBiz.find(null, " status=1");
        request.setAttribute("classTypeList", classTypeList);
    }


    @RequestMapping("/queryList")
    @ResponseBody
    public Map<String, Object> queryList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {

        Map<String, Object> json = null;
        try {
            //封装查询参数
            Classes classes = new Classes();
            String whereSql = classesBiz.addCondition(request, classes);
            pagination.setRequest(request);
            //执行查询
            List<Classes> classList = classesBiz.find(null, whereSql);
            //返回结果
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, classList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }


    @RequestMapping("file/download")
    public void fileDownload(HttpServletRequest request, HttpServletResponse response) {
        //文件下载
        downFile(response, request);
    }


    public void downFile(HttpServletResponse response, HttpServletRequest request) {

        /*        HttpServletResponse response = this.getResponse();*/
        try {
            // 清空response
            response.reset();
            //设置输出的格式
            response.setContentType("multipart/form-data");// 设置为下载application/x-download
			/*response.addHeader("content-type ","application/x-msdownload");
			response.setContentType("application/octet-stream");*/
            response.setHeader("Content-Disposition", "attachment; filename=" + transCharacter(request, "a.pdf"));//设定输出文件头
            response.addHeader("Content-Length", "1024");
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream("c:/input/download.pdf"));
            ServletOutputStream toClient = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = fis.read(buffer)) != -1) {
                toClient.write(buffer, 0, n);
                toClient.flush();
            }
            fis.close();
            //输出文件
            toClient.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 根据不同的浏览器转字符编码
     *
     * @param request
     * @param str
     * @return
     * @throws Exception
     */
    private String transCharacter(HttpServletRequest request, String str) throws Exception {
        if (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0) {
            return URLEncoder.encode(str, "UTF-8");
        } else if (request.getHeader("USER-AGENT").toLowerCase().indexOf("firefox") > 0) {
            return new String(str.getBytes("UTF-8"), "ISO8859-1");
        }
        return new String(str.getBytes("UTF-8"), "ISO8859-1");
    }


    /**
     * 查询各个班次的报名人数
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/queryClassTypePersonNum")
    public ModelAndView queryClassTypePersonNum(HttpServletRequest request, @RequestParam(value = "id", required = false) Long type) {
        ModelAndView mv = new ModelAndView("/admin/class/classType_statistic_list");
        try {
            String whereSql = "1=1";
            List<ClassTypeStatistic> classTypeStatisticList = classTypeStatisticDao.queryClassTypeCount(whereSql);
            mv.addObject("classTypeStatisticList", classTypeStatisticList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 选择教室(后勤会场)
     *
     * @param request
     * @return
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/meetList")
    public String meetList(HttpServletRequest request) {
        try {
            String name = request.getParameter("name");
            request.setAttribute("name", name);
            //使用时间
            String useTime = request.getParameter("startTime");
            //归还时间
            String turnTime = request.getParameter("endTime");
            List<Map<String, String>> map = hqHessionService.queryMeetingListByTimeOrName(useTime, turnTime, name);
            String str = gson.toJson(map);
            List<Meeting> meetingList = gson.fromJson(str, new TypeToken<List<Meeting>>() {
            }.getType());
            request.setAttribute("meetingList", meetingList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/class/select_meeting_list";
    }

    /**
     * 选择讨论室(后勤会场)
     *
     * @param request
     * @return
     */
    @RequestMapping(WITHOUT_ADMIN_PREFIX + "/discussList")
    public String discussList(HttpServletRequest request) {
        try {
            String name = request.getParameter("name");
            request.setAttribute("name", name);
            //使用时间
            String useTime = request.getParameter("startTime");
            //归还时间
            String turnTime = request.getParameter("endTime");
            List<Map<String, String>> map = hqHessionService.queryMeetingListByTimeOrName(useTime, turnTime, name);
            String str = gson.toJson(map);
            List<Meeting> discussList = gson.fromJson(str, new TypeToken<List<Meeting>>() {
            }.getType());
            request.setAttribute("discussList", discussList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/class/select_discuss_list";
    }

    /**
     * 跳转到设置班主任的页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdateClassLeaderOfClass")
    public String toUpdateClassLeaderOfClass(HttpServletRequest request, Long id) {
        try {
            Classes classes = classesBiz.findById(id);
            request.setAttribute("classes", classes);
        } catch (Exception e) {
            e.printStackTrace();
            return this.setErrorPath(request, e);
        }
        return "/admin/class/update_classleader_of_class";
    }

    /**
     * @param request
     * @param classes
     * @return java.util.Map
     * @Description 设置班主任
     */
    @RequestMapping(ADMIN_PREFIX + "/updateClassLeaderOfClass")
    @ResponseBody
    public Map<String, Object> updateClassLeaderOfClass(HttpServletRequest request,
                                                        @ModelAttribute("classes") Classes classes) {
        Map<String, Object> json = null;
        try {
            json = validateClasses(classes);
            if (json != null) {
                return json;
            }
            //修改班主任信息
            ClassType classType = classTypeBiz.findById(classes.getClassTypeId());
            classes.setClassType(classType.getName());
            classesBiz.updateClasses(classes);

            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }
    @RequestMapping(ADMIN_PREFIX + "/myClassList")
    public String myClassList(HttpServletRequest request) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            //查询用户角色
            Long userId = SysUserUtils.getLoginSysUserId(request);
            hrHessianService.getEmployeeBySysUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return this.setErrorPath(request, e);
        }
        return "/admin/class/update_classleader_of_class";
    }
}
