package com.jiaowu.controller.app;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.classes.ClassesTeacherRecordBiz;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.meeting.MeetingBiz;
import com.jiaowu.biz.teach.TeachingProgramCourseBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.biz.userWorkDayData.UserWorkDayDataBiz;
import com.jiaowu.biz.xinde.XinDeBiz;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.classes.ClassesTeacherRecord;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.meeting.Meeting;
import com.jiaowu.entity.sysuser.SysUser;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.user.UserCondition;
import com.jiaowu.entity.userWorkDayData.UserWorkDayData;
import com.jiaowu.entity.xinde.XinDe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APPController
 *
 * @author 李帅雷
 */
@Controller
@RequestMapping("/api/jiaowu")
public class AppController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AppController.class);

    @InitBinder({"xinDe"})
    public void initXinDe(WebDataBinder binder){
        binder.setFieldDefaultPrefix("xinDe.");
    }

    @Autowired
    private CourseArrangeBiz courseArrangeBiz;
    @Autowired
    private TeachingProgramCourseBiz teachingProgramCourseBiz;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    UserWorkDayDataBiz userWorkDayDataBiz;
    @Autowired
    private HrHessianService hrHessianService;
    @Autowired
    private ClassesTeacherRecordBiz classesTeacherRecordBiz;
    @Autowired
    private XinDeBiz xinDeBiz;
    @Autowired
    private MeetingBiz meetingBiz;
    @Autowired
    private BaseHessianService baseHessianService;
    @Autowired
    private ClassTypeBiz classTypeBiz;


    @InitBinder({"userCondition"})
    public void initUserCondition(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("userCondition.");
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * @param request
     * @param userId
     * @return
     * @Description ajax分页获取学员课表的内容
     */
    @RequestMapping("/course/appStudentCourseArrange")
    @ResponseBody
    public Map<String, Object> appStudentCourseArrange(HttpServletRequest request, @RequestParam("userId") Long userId, @ModelAttribute("pagination") Pagination pagination) {
        Map<String, Object> json = null;
        try {
            User user = userBiz.findById(userId);
            List<CourseArrange> courseArrangeList = null;
            if (user != null) {
                courseArrangeList = courseArrangeBiz.find(pagination, " classId=" + user.getClassId());
                classesBiz.setCourseInfoAndDeleteUselessCourseArrange(courseArrangeList);
            }
            System.out.println(courseArrangeList == null);

            Map<String, Object> map = new HashMap<String, Object>();
            System.out.println("map==null:" + map == null);
            map.put("courseArrangeList", courseArrangeList);
            map.put("pagination", pagination);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, map);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    @RequestMapping("/appUserWorkDayDataList")
    @ResponseBody
    public Map<String, Object> appUserWorkDayDataList(@RequestParam("userId") Long userId, @ModelAttribute("userCondition") UserCondition userCondition, @ModelAttribute("pagination") Pagination pagination) {
        try {
            StringBuilder whereSql = new StringBuilder(" userName is not null and userName!=''");
            SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            whereSql.append(" and userId=" + userId);
            if (ObjectUtils.isNotNull(userCondition.getStartTime())) {
                whereSql.append(" and  TO_DAYS(workDate) >= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getStartTime()).trim()).append("')");
            }
            if (ObjectUtils.isNotNull(userCondition.getEndTime())) {
                whereSql.append(" and  TO_DAYS(workDate) <= ").append("TO_DAYS('").append(bartDateFormat.format(userCondition.getEndTime()).trim()).append("')");
            }
            whereSql.append(" order by TO_DAYS(workDate) desc");

            List<UserWorkDayData> userWorkDayDataList = userWorkDayDataBiz.find(pagination, whereSql.toString());
            Map<String, Object> map = new HashMap<String, Object>();
            int outTime = 0;//正常
            int retreat = 0;//迟到
            int leave = 0;//早退
            int absenteeism = 0;//旷工
            if (ObjectUtils.isNotNull(userWorkDayDataList)) {
                for (UserWorkDayData workDayData : userWorkDayDataList) {
                    if (workDayData.getMorningAttendanceStatus() == 1 || workDayData.getMorningAttendanceStatus() == 1) {
                        outTime++;
                    }
                    if (workDayData.getMorningAttendanceStatus() == 2 || workDayData.getMorningAttendanceStatus() == 2) {
                        retreat++;
                    }
                    if (workDayData.getMorningAttendanceStatus() == 3 || workDayData.getMorningAttendanceStatus() == 3) {
                        leave++;
                    }
                    if (workDayData.getMorningAttendanceStatus() == 4 || workDayData.getMorningAttendanceStatus() == 4) {
                        absenteeism++;
                    }
                }
                map.put("outTime", outTime);
                map.put("retreat", retreat);
                map.put("leave", leave);
                map.put("absenteeism", absenteeism);
                map.put("pagination", pagination);
                map.put("userWorkDayDataList", userWorkDayDataList);
                return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, map);
            }
        } catch (Exception e) {
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.ERROR_PARAMETER, "无数据", null);
    }

    /**
     * @Description 获取现任班主任列表
     * @param request
     * @return
     */
    @RequestMapping("/getLeaderList")
    @ResponseBody
    public Map<String,Object> LeaderList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
        Map<String,Object> json=null;
        Map<String,Object> dataMap = new HashMap<String,Object>();
        try{
            pagination.setPageSize(10);
            String whereSql = " status=1 and type=1";
            //获取未结束的班次
            List<Classes> classesList=classesBiz.find(null," status=1  and endTime > '" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "'");
            //在任的班主任id串
            String teacherIds="0,";
            if(classesList!=null && classesList.size()>0){
                for(Classes c:classesList){
                    if(c.getTeacherId()!=null && c.getTeacherId()>0){
                        teacherIds+=c.getTeacherId()+",";
                    }
                    if(c.getDeputyTeacherId()!=null && c.getDeputyTeacherId()>0){
                        teacherIds+=c.getDeputyTeacherId()+",";
                    }
                }
            }
            teacherIds=teacherIds.substring(0,teacherIds.length()-1);
            whereSql+=" and id in ("+teacherIds+")";

//			pagination.setRequest(request);
            Map<String, Object> map = hrHessianService.getEmployeeListBySql(pagination, whereSql);
            List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
            if(teacherList!=null && teacherList.size()>0){
                for(Map<String, String> teacherMap:teacherList){
                    Long id=Long.valueOf(teacherMap.get("id"));
                    String classess="";
                    classesList=classesBiz.find(null," status=1 and ( teacherId=" + id + " or deputyTeacherId=" + id + " )");
                    if(classesList!=null && classesList.size()>0){
                        for(Classes c:classesList){
                            classess+=c.getName()+"、";
                        }
                        classess=classess.substring(0,classess.length()-1);
                    }
                    teacherMap.put("classess",classess);
                }
            }
            dataMap.put("teacherList",teacherList);
            dataMap.put("pagination",pagination);
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, dataMap);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("AppController.LeaderList",e);
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 获取历任任班主任列表
     * @param request
     * @return
     */
    @RequestMapping("/leaderHistoryList")
    @ResponseBody
    public Map<String,Object> leaderHistoryList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
        Map<String,Object> json=null;
        Map<String,Object> dataMap = new HashMap<String,Object>();
        try{
            pagination.setPageSize(10);
            String whereSql = " status=1 and type=1";
            //获取班主任记录
            List<ClassesTeacherRecord> classesTeacherRecordList = classesTeacherRecordBiz.queryAppClassesTeacherRecordList();
            //在任的班主任id串
            String teacherIds="0,";
            if(classesTeacherRecordList!=null && classesTeacherRecordList.size()>0){
                for(ClassesTeacherRecord c:classesTeacherRecordList){
                    if(c.getTeacherId()!=null && c.getTeacherId()>0){
                        teacherIds+=c.getTeacherId()+",";
                    }
                }
            }
            teacherIds=teacherIds.substring(0,teacherIds.length()-1);
            whereSql+=" and id in ("+teacherIds+")";

//			pagination.setRequest(request);
            Map<String, Object> map = hrHessianService.getEmployeeListBySql(pagination, whereSql);
            List<Map<String, String>> teacherList = (List<Map<String, String>>) map.get("employeeList");
            if(teacherList!=null && teacherList.size()>0){
                for(Map<String, String> teacherMap:teacherList){
                    Long id=Long.valueOf(teacherMap.get("id"));
                    String classess="";
                    classesTeacherRecordList=classesTeacherRecordBiz.find(null," status=1 and teacherId=" + id);
                    if(classesTeacherRecordList!=null && classesTeacherRecordList.size()>0){
                        for(ClassesTeacherRecord c:classesTeacherRecordList){
                            classess+=c.getClassesName()+"、";
                        }
                        classess=classess.substring(0,classess.length()-1);
                    }
                    teacherMap.put("classess",classess);
                }
            }

            dataMap.put("teacherList",teacherList);
            dataMap.put("pagination",pagination);
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, dataMap);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("AppController.leaderHistoryList",e);
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 获取我的心得列表
     * @param request
     * @return
     */
    @RequestMapping("/getMyXinDeList")
    @ResponseBody
    public Map<String,Object> getMyXinDeList(HttpServletRequest request,
                                         @RequestParam(value = "userId")Long userId,
                                         @ModelAttribute("pagination") Pagination pagination){
        Map<String,Object> json=null;
        Map<String,Object> dataMap = new HashMap<String,Object>();
        try{
            pagination.setPageSize(10);
            Map<String, String> sysUser=baseHessianService.querySysUserById(userId);

            if(sysUser==null || !"3".equals(sysUser.get("userType"))){
                json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "只有学员拥有心得权限!", null);
                return json;
            }
            String whereSql=" status=1";
            whereSql+=" and studentId="+sysUser.get("linkId");
            whereSql+=" order by id desc";
            List<XinDe> xinDeList = xinDeBiz.find(pagination,whereSql);
            dataMap.put("xinDeList",xinDeList);
            dataMap.put("pagination",pagination);
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, dataMap);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("AppController.getMyXinDeList",e);
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 获取会议列表
     * @param request
     * @return
     */
    @RequestMapping("/meetingListForSelect")
    @ResponseBody
    public Map<String,Object> meetingListForSelect(HttpServletRequest request,
                                                   @RequestParam(value = "name",required = false)String name,
                                                   @RequestParam(value = "startTime",required = false)String startTime,
                                                   @RequestParam(value = "endTime",required = false)String endTime,
                                                   @ModelAttribute("pagination") Pagination pagination){
        Map<String,Object> json=null;
        Map<String,Object> dataMap = new HashMap<String,Object>();
        try{
            pagination.setPageSize(10);
            String whereSql=" status=1";

            if (!StringUtils.isTrimEmpty(name)) {
                whereSql+=" and name like '%" + name + "%'";
            }
            if (!StringUtils.isTrimEmpty(startTime)) {
                whereSql+=" and startTime > '" + startTime + "'";
            }
            if (!StringUtils.isTrimEmpty(endTime)) {
                whereSql+=" and endTime < '" + endTime + "'";
            }
            List<Meeting> meetingList = meetingBiz.find(pagination,whereSql);
            dataMap.put("meetingList",meetingList);
            dataMap.put("pagination",pagination);
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, dataMap);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("AppController.meetingListForSelect",e);
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 获取心得详情
     * @param request
     * @return
     */
    @RequestMapping("/queryXinDeInfo")
    @ResponseBody
    public Map<String,Object> queryXinDeInfo(HttpServletRequest request,
                                         @RequestParam("id")Long id){
        Map<String,Object> json=null;
        try{
            XinDe xinDe=xinDeBiz.findById(id);
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, xinDe);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("AppController.queryXinDeInfo",e);
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 保存心得
     * @param request
     * @return
     */
    @RequestMapping("/saveXinDe")
    @ResponseBody
    public Map<String,Object> saveXinDe(HttpServletRequest request,
                                             @ModelAttribute("xinDe")XinDe xinDe){
        Map<String,Object> json=null;
        try{
            Map<String, String> sysUser=baseHessianService.querySysUserById(xinDe.getStudentId());

            if(sysUser==null || !"3".equals(sysUser.get("userType"))){
                json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "只有学员拥有创建心得的权限!", null);
                return json;
            }
            User user=userBiz.findById(Long.parseLong(sysUser.get("linkId")));
            xinDe.setStudentId(Long.parseLong(sysUser.get("linkId")));
            xinDe.setStudentName(user.getName());
            if(xinDe.getId()==null || xinDe.getId()<=0){
                xinDe.setClassId(user.getClassId());
                xinDeBiz.save(xinDe);
            }else {
                xinDeBiz.update(xinDe);
            }
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,  null);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("AppController.saveXinDe",e);
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 获取班型列表
     * @param request
     * @return
     */
    @RequestMapping("/getClassTypeList")
    @ResponseBody
    public Map<String,Object> getClassTypeList(HttpServletRequest request){
        Map<String,Object> json=null;
        try{
            List<ClassType> classTypeList=classTypeBiz.find(null,"status=1 order by id desc");
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, classTypeList);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("AppController.getClassTypeList",e);
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description 获取班次列表
     * @param request
     * @param classTypeId
     * @return
     */
    @RequestMapping("/getClassList")
    @ResponseBody
    public Map<String,Object> getClassList(HttpServletRequest request,
                                           @RequestParam(value = "classTypeId",required = false)Long classTypeId,
                                           @RequestParam(value = "className",required = false)String className,
                                           @ModelAttribute("pagination") Pagination pagination){
        Map<String,Object> json=null;
        Map<String,Object> dataMap = new HashMap<String,Object>();
        try{
            pagination.setPageSize(10);
            String whereSql="status=1";
            if(classTypeId!=null && classTypeId>0){
                whereSql+=" and classTypeId="+classTypeId;
            }
            if(className!=null && !className.trim().equals("")){
                whereSql+=" and name like '%"+className+"%'";
            }
            whereSql+=" order by id desc";
            List<Classes> classesList=classesBiz.find(pagination,whereSql);
            dataMap.put("classesList",classesList);
            dataMap.put("pagination",pagination);
            json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, dataMap);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("AppController.getClassList",e);
            json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }
}
