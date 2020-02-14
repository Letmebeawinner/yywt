package com.jiaowu.controller.workAttendance;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.holiday.HolidayBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.holiday.Holiday;
import com.jiaowu.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 李帅雷 on 2017/8/28.
 */
@Controller
public class WorkAttendanceController extends BaseController {
    private static Logger logger = LoggerFactory
            .getLogger(WorkAttendanceController.class);
    @Autowired
    private HolidayBiz holidayBiz;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private HrHessianService hrHessianService;

    private static final String ADMIN_PREFIX = "/admin/jiaowu";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu";

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    @InitBinder({"holiday"})
    public void initBinderHoliday(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("holiday.");
    }

    /**
     * 跳转到请假页面
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/holiday/toCreateHoliday")
    public String toCreateHoliday(HttpServletRequest request) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (userMap.size() > 0) {
                String linkId = userMap.get("linkId");
                String userType = userMap.get("userType");
                //当前用户是学员
                if (userType.equals("3")) {
                    User userBizById = userBiz.findById(Long.parseLong(linkId));
                    request.setAttribute("user", userBizById);
                }
                request.setAttribute("userType", userType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/workAttendance/toCreateHoliday";
    }

    /**
     * 添加请假记录
     *
     * @param request
     * @param holiday
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/holiday/createHoliday")
    @ResponseBody
    public Map<String, Object> createHoliday(HttpServletRequest request, @ModelAttribute("holiday") Holiday holiday) {
        Map<String, Object> json = null;
        try {
            String errorInfo = validateHoliday(holiday);
            if (!StringUtils.isTrimEmpty(errorInfo)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo,
                        null);
            }
            /*Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            String userType=userMap.get("userType");*/
            /*if(userType==null||userType.equals("")||userType.equals("1")||userType.equals("2")){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您无权限进行请假",
                        null);
            }*/
            /*if(userType.equals("2")){
                Map<String,String> teacherMap=hrHessianService.queryEmployeeById(Long.parseLong(userMap.get("linkId")));
                holiday.setUserId(Long.parseLong(userMap.get("linkId")));
                holiday.setUserName(teacherMap.get("name"));
            }else{
                User user=userBiz.findById(Long.parseLong(userMap.get("linkId")));
                holiday.setClassId(user.getClassId());
                holiday.setUserId(user.getId());
                holiday.setUserName(user.getName());
            }*/
            User user = userBiz.findById(holiday.getUserId());
            holiday.setClassId(user.getClassId());
            /*holiday.setUserId(user.getId());
            holiday.setUserName(user.getName());*/

            holiday.setType(3);
//            if (holiday.getEndTime().getTime() > holiday.getBeginTime().getTime()) {
//                holiday.setLength("");
//            }
//            if (StringUtils.isEmpty(holiday.getLength())) {
            holiday.setLength(calculateLength(holiday));
//            }
            String result = holidayBiz.addWorkLeaveInfo(holiday, user);
            if (result != null && result.equals("1")) {
                holidayBiz.save(holiday);
                json = this.resultJson(ErrorCode.SUCCESS, "您已提交请假申请,请等待管理员审批。", null);
            } else {
                json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * 跳转到修改请假页面
     *
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/holiday/toUpdateHoliday")
    public String toUpdateHoliday(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (userMap.size() > 0) {
                String linkId = userMap.get("linkId");
                String userType = userMap.get("userType");
                //当前用户是学员
                if (userType.equals("3")) {
                    User userBizById = userBiz.findById(Long.parseLong(linkId));
                    request.setAttribute("user", userBizById);
                }
                request.setAttribute("userType", userType);
            }
            Holiday holiday = holidayBiz.findById(id);
            request.setAttribute("holiday", holiday);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/workAttendance/toUpdateHoliday";
    }


    /**
     * 添加请假记录
     *
     * @param request
     * @param holiday
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/holiday/updateHoliday")
    @ResponseBody
    public Map<String, Object> updateHoliday(HttpServletRequest request, @ModelAttribute("holiday") Holiday holiday) {
        Map<String, Object> json = null;
        try {
            String errorInfo = validateHoliday(holiday);
            if (!StringUtils.isTrimEmpty(errorInfo)) {
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
            }
            /*Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            String userType=userMap.get("userType");*/
            /*if(userType==null||userType.equals("")||userType.equals("1")||userType.equals("2")){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您无权限进行请假",
                        null);
            }*/
            /*if(userType.equals("2")){
                Map<String,String> teacherMap=hrHessianService.queryEmployeeById(Long.parseLong(userMap.get("linkId")));
                holiday.setUserId(Long.parseLong(userMap.get("linkId")));
                holiday.setUserName(teacherMap.get("name"));
            }else{
                User user=userBiz.findById(Long.parseLong(userMap.get("linkId")));
                holiday.setClassId(user.getClassId());
                holiday.setUserId(user.getId());
                holiday.setUserName(user.getName());
            }*/
            User user = userBiz.findById(holiday.getUserId());
            holiday.setClassId(user.getClassId());
            /*holiday.setUserId(user.getId());
            holiday.setUserName(user.getName());*/

            holiday.setType(3);
            if (holiday.getEndTime().getTime() > holiday.getBeginTime().getTime()) {
                holiday.setLength("");
            }
            if (StringUtils.isEmpty(holiday.getLength())) {
                holiday.setLength(calculateLength(holiday));
            }
//            String result = holidayBiz.addWorkLeaveInfo(holiday, user);
//            if (result != null && result.equals("1")) {
            holidayBiz.update(holiday);
            json = this.resultJson(ErrorCode.SUCCESS, "您已提交请假申请,请等待管理员审批。", null);
//            } else {
//                json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 根据id删除请假记录
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/holiday/delHoliday")
    @ResponseBody
    public Map<String, Object> delHoliday(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            Holiday holiday = new Holiday();
            holiday.setStatus(4);
            holiday.setId(id);
            holidayBiz.update(holiday);
            json = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 请假列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/holiday/holidayList")
    public String holidayList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            boolean isAdministrator = userMap.get("userType").equals("1") ? true : false;
            request.setAttribute("isAdministrator", isAdministrator);
            List<Holiday> holidayList = null;
            String whereSql = " 1=1";
            if (userMap.size() > 0) {
                String linkId = userMap.get("linkId");
                String userType = userMap.get("userType");
                if (userType.equals("3")) {
                    whereSql += " and userId=" + linkId;
                }
            }
            Holiday holiday = new Holiday();
            String userName = request.getParameter("userName");
            if (!StringUtils.isTrimEmpty(userName)) {
                whereSql += " and userName like '%" + userName + "%'";
                holiday.setUserName(userName);
            }
            String status = request.getParameter("status");
            if (!StringUtils.isTrimEmpty(status)) {
                whereSql += " and status=" + status;
                holiday.setStatus(Integer.parseInt(status));
            } else {
                whereSql += " and status!=4";
            }
            if (isAdministrator) {
                String classId = request.getParameter("classId");
                if (!StringUtils.isTrimEmpty(classId) && !classId.equals("0")) {
                    whereSql += " and classId=" + classId;
                    holiday.setClassId(Long.parseLong(classId));
                }
                String type = request.getParameter("type");
                if (!StringUtils.isTrimEmpty(type)) {
                    whereSql += " and type=" + type;
                    holiday.setType(Integer.parseInt(type));
                }
            } else {
                List<Classes> classesList = classesBiz.find(null, " status=1 and teacherId=" + userMap.get("linkId"));
                holiday.setClassId(classesList != null && classesList.size() > 0 ? classesList.get(0).getId() : -1L);
            }
            //开始时间
            String startTime = request.getParameter("startTime");
            //结束时间
            String endTime = request.getParameter("endTime");
            if (!StringUtils.isEmpty(startTime)) {
                whereSql += " and beginTime >= '" + startTime + "'";
                request.setAttribute("startTime", startTime);
            }
            if (!StringUtils.isEmpty(endTime)) {
                whereSql += " and endTime <= '" + endTime + "'";
                request.setAttribute("endTime", endTime);
            }
            whereSql += " order by userName, beginTime";
            pagination.setRequest(request);
            holidayList = holidayBiz.find(pagination, whereSql);
            if (holidayList != null && holidayList.size() > 0) {
                for (Holiday holiday1 : holidayList) {
                    Classes classes = classesBiz.findById(holiday1.getClassId());
                    if (ObjectUtils.isNotNull(classes)) {
                        holiday1.setClassName(classes.getName());
                    }
                }
            }
            request.setAttribute("holidayList", holidayList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("holiday", holiday);
            request.setAttribute("className", request.getParameter("className"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/workAttendance/holiday_list";
    }

    /**
     * 审核通过
     *
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/holiday/pass")
    @ResponseBody
    public Map<String, Object> pass(Long id) {
        Map<String, Object> json = null;
        try {
            Holiday holiday = new Holiday();
            holiday.setId(id);
            holiday.setStatus(1);
            holidayBiz.update(holiday);
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
     * 审核不通过
     *
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/holiday/deny")
    @ResponseBody
    public Map<String, Object> deny(Long id) {
        Map<String, Object> json = null;
        try {
            Holiday holiday = new Holiday();
            holiday.setId(id);
            holiday.setStatus(0);
            holidayBiz.update(holiday);
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
     * 验证假期
     *
     * @param holiday
     * @return
     */
    public String validateHoliday(Holiday holiday) {
        if (holiday.getBeginTime() == null || holiday.getEndTime() == null) {
            return "请填写开始时间和结束时间";
        }
//        if(holiday.getBeginTime().getTime()<new Date().getTime()){
//            return "开始时间不能早于当前时间";
//        }
        System.out.println(holiday.getEndTime().getTime() + "------------------------------------" + holiday.getBeginTime().getTime());
//        if (holiday.getEndTime().getTime() == holiday.getBeginTime().getTime()) {
//            if (StringUtils.isEmpty(holiday.getLength())) {
//                return "请选择请假时长类型";
//            }
//        }
        if (holiday.getEndTime().getTime() < holiday.getBeginTime().getTime()) {
            return "结束时间不能早于开始时间";
        }
        if (StringUtils.isTrimEmpty(holiday.getReason())) {
            return "请填写请假原因";
        }
        return null;
    }

    /**
     * 计算时间差
     *
     * @param holiday
     * @return
     */
    public String calculateLength(Holiday holiday) {
        StringBuffer sb = new StringBuffer();
        TimeZone tz = TimeZone.getTimeZone("GMT");
        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        beginCalendar.setTime(holiday.getBeginTime());
        endCalendar.setTime(holiday.getEndTime());
        beginCalendar.setTimeZone(tz);
        endCalendar.setTimeZone(tz);
        /*SimpleDateFormat sdf=new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
        String[] beginTimeArray=sdf.format(beginCalendar).split(":");
        String[] endTimeArray=sdf.format(endCalendar).split(":");*/
        long interval = endCalendar.getTimeInMillis() - beginCalendar.getTimeInMillis();
        //24*60*60*1000
        if (interval != 0) {
            sb.append(interval / (24 * 60 * 60 * 1000) + "天");
            if (holiday.getBeginLastAfternoon() == 0) {//如果是从上午开始请假
                if (holiday.getBeginLastAfternoon() == 0 && holiday.getEndLastAfternoon() == 0) {
                    sb.insert(sb.indexOf("天"), ".5");
                } else if (holiday.getBeginLastAfternoon() == 0 && holiday.getEndLastAfternoon() == 1) {
                    String s = sb.toString().split("天")[0];
                    sb.delete(0, sb.indexOf("天") + 1).append((Integer.parseInt(s) + 1) + "天");
                } else if (holiday.getBeginLastAfternoon() == 1 && holiday.getEndLastAfternoon() == 1) {
                    sb.insert(sb.indexOf("天"), ".5");
                }
            } else {//如果是从下午开始请假
                if (holiday.getEndLastAfternoon() == 0) {//结束时间是上午

                } else {//结束时间是下午
                    sb.insert(sb.indexOf("天"), ".5");
                }
            }
        } else {
            if (holiday.getBeginLastAfternoon() == 0 && holiday.getEndLastAfternoon() == 0) {
                sb.append("半天");
            } else if (holiday.getBeginLastAfternoon() == 0 && holiday.getEndLastAfternoon() == 1) {
                sb.append("1天");
            } else if (holiday.getBeginLastAfternoon() == 1 && holiday.getEndLastAfternoon() == 1) {
                sb.append("半天");
            }
//            sb.append(interval % (60 * 60 * 1000) == 0 ? interval / (60 * 60 * 1000) + "小时" : (interval / (60 * 60 * 1000) + 1) + "小时");
        }
        return sb.toString();
    }

    //if (interval >= 24 * 60 * 60 * 1000) {
//        sb.append(interval / (24 * 60 * 60 * 1000) + "天");
//        if (holiday.getBeginLastAfternoon() == 0 && holiday.getEndLastAfternoon() == 0) {
//        sb.insert(1, ".5");
////                sb.append("半天");
//        } else if (holiday.getBeginLastAfternoon() == 0 && holiday.getEndLastAfternoon() == 1) {
//        sb.append("1天");
//        } else if (holiday.getBeginLastAfternoon() == 1 && holiday.getEndLastAfternoon() == 1) {
//        sb.append("半天");
//        }
////            long leftTime = interval % (24 * 60 * 60 * 1000);
////            if (leftTime != 0) {
////                sb.append(leftTime % (60 * 60 * 1000) == 0 ? leftTime / (60 * 60 * 1000) + "小时" : (leftTime / (60 * 60 * 1000) + 1) + "小时");
////            }
//        } else {
//        if (holiday.getBeginLastAfternoon() == 0 && holiday.getEndLastAfternoon() == 0) {
//        sb.append("半天");
//        } else if (holiday.getBeginLastAfternoon() == 0 && holiday.getEndLastAfternoon() == 1) {
//        sb.append("1天");
//        } else if (holiday.getBeginLastAfternoon() == 1 && holiday.getEndLastAfternoon() == 1) {
//        sb.append("半天");
//        }
////            sb.append(interval % (60 * 60 * 1000) == 0 ? interval / (60 * 60 * 1000) + "小时" : (interval / (60 * 60 * 1000) + 1) + "小时");
//        }
//        return sb.toString();
//        }

    @RequestMapping(ADMIN_PREFIX + "/holiday/holidayExport")
    public void holidayExport(HttpServletRequest request, HttpServletResponse response,
                              @ModelAttribute("pagination") Pagination pagination) {
        try {
            // 指定文件生成路径
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/holiday");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//定义格式，不显示毫秒
            // 文件名
            String expName = "请假列表_" + df.format(new Date());
            // 表头信息
            String[] headName = {"ID", "用户类型", "班次", "用户", "开始时间", "结束时间", "请假时长", "请假类型", "原因"};
            //以下获取数据
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            boolean isAdministrator = userMap.get("userType").equals("1") ? true : false;
            request.setAttribute("isAdministrator", isAdministrator);
            List<Holiday> holidayList = null;
            String whereSql = " 1=1";
            if (userMap.size() > 0) {
                String linkId = userMap.get("linkId");
                String userType = userMap.get("userType");
                if (userType.equals("3")) {
                    whereSql += " and userId=" + linkId;
                }
            }
            Holiday holiday = new Holiday();
            String userName = request.getParameter("userName");
            if (!StringUtils.isTrimEmpty(userName)) {
                whereSql += " and userName like '%" + userName + "%'";
                holiday.setUserName(userName);
            }
            String status = request.getParameter("status");
            if (!StringUtils.isTrimEmpty(status)) {
                whereSql += " and status=" + status;
                holiday.setStatus(Integer.parseInt(status));
            } else {
                whereSql += " and status!=4";
            }
            if (isAdministrator) {
                String classId = request.getParameter("classId");
                if (!StringUtils.isTrimEmpty(classId) && !classId.equals("0")) {
                    whereSql += " and classId=" + classId;
                    holiday.setClassId(Long.parseLong(classId));
                }
                String type = request.getParameter("type");
                if (!StringUtils.isTrimEmpty(type)) {
                    whereSql += " and type=" + type;
                    holiday.setType(Integer.parseInt(type));
                }
            } else {
                List<Classes> classesList = classesBiz.find(null, " status=1 and teacherId=" + userMap.get("linkId"));
                holiday.setClassId(classesList != null && classesList.size() > 0 ? classesList.get(0).getId() : -1L);
            }
            //开始时间
            String startTime = request.getParameter("startTime");
            //结束时间
            String endTime = request.getParameter("endTime");
            if (!StringUtils.isEmpty(startTime)) {
                whereSql += " and beginTime >= '" + startTime + "'";
                request.setAttribute("startTime", startTime);
            }
            if (!StringUtils.isEmpty(endTime)) {
                whereSql += " and endTime <= '" + endTime + "'";
                request.setAttribute("endTime", endTime);
            }
            whereSql += " order by userName, beginTime";
            pagination.setRequest(request);
            holidayList = holidayBiz.find(pagination, whereSql);
            if (holidayList != null && holidayList.size() > 0) {
                for (Holiday holiday1 : holidayList) {
                    Classes classes = classesBiz.findById(holiday1.getClassId());
                    if (ObjectUtils.isNotNull(classes)) {
                        holiday1.setClassName(classes.getName());
                    }
                }
            }
            List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
            List<List<String>> list = holidayJoint(holidayList);
            File file = FileExportImportUtil.createExcel(headName, list, expName, dir);
            srcfile.add(file);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 学员信息excel格式拼接
     *
     * @return
     */
    public List<List<String>> holidayJoint(List<Holiday> holidayList) throws Exception {
        List<List<String>> list = new ArrayList<List<String>>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
        for (int i = 0; i < holidayList.size(); i++) {
            Holiday holiday = holidayList.get(i);
            if (ObjectUtils.isNotNull(holiday)) {
                List<String> small = new ArrayList<String>();
                //"ID", "用户类型", "班次", "用户", "开始时间", "结束时间", "请假时长", "请假类型", "原因"
                small.add((i + 1) + "");
                Integer type = holiday.getType();
                if (type == 2) {
                    small.add("讲师");
                } else if (type == 3) {
                    small.add("学员");
                }
                small.add(!StringUtils.isEmpty(holiday.getClassName()) ? holiday.getClassName() : "");
                small.add(!StringUtils.isEmpty(holiday.getUserName()) ? holiday.getUserName() : "");
                Date beginTime = holiday.getBeginTime();
                if (ObjectUtils.isNotNull(beginTime)) {
                    small.add(df.format(beginTime));
                } else {
                    small.add("");
                }
                Date endTime = holiday.getEndTime();
                if (ObjectUtils.isNotNull(endTime)) {
                    small.add(df.format(endTime));
                } else {
                    small.add("");
                }
                small.add(!StringUtils.isEmpty(holiday.getLength()) ? holiday.getLength() : "");
                small.add(!StringUtils.isEmpty(holiday.getLeaType()) ? holiday.getLeaType() : "");
                small.add(!StringUtils.isEmpty(holiday.getReason()) ? holiday.getReason() : "");
                list.add(small);
            }
        }
        return list;
    }


    /**
     * 请假列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/holiday/studentHolidayList")
    public String studentHolidayList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            boolean isAdministrator = userMap.get("userType").equals("1") ? true : false;
            List<Holiday> holidayList = null;
            String whereSql = " 1=1";
            if (userMap.size() > 0) {
                String linkId = userMap.get("linkId");
                String userType = userMap.get("userType");
                if (userType.equals("3")) {
                    whereSql += " and userId=" + linkId;
                }
            }
            Holiday holiday = new Holiday();
            String userName = request.getParameter("userName");
            if (!StringUtils.isTrimEmpty(userName)) {
                whereSql += " and userName like '%" + userName + "%'";
                holiday.setUserName(userName);
            }
            String status = request.getParameter("status");
            if (!StringUtils.isTrimEmpty(status)) {
                whereSql += " and status=" + status;
                holiday.setStatus(Integer.parseInt(status));
            } else {
                whereSql += " and status!=4";
            }
            if (isAdministrator) {
                String classId = request.getParameter("classId");
                if (!StringUtils.isTrimEmpty(classId) && !classId.equals("0")) {
                    whereSql += " and classId=" + classId;
                    holiday.setClassId(Long.parseLong(classId));
                }
                String type = request.getParameter("type");
                if (!StringUtils.isTrimEmpty(type)) {
                    whereSql += " and type=" + type;
                    holiday.setType(Integer.parseInt(type));
                }
            } else {
                List<Classes> classesList = classesBiz.find(null, " status=1 and teacherId=" + userMap.get("linkId"));
                holiday.setClassId(classesList != null && classesList.size() > 0 ? classesList.get(0).getId() : -1L);
            }
            //开始时间
            String startTime = request.getParameter("startTime");
            //结束时间
            String endTime = request.getParameter("endTime");
            if (!StringUtils.isEmpty(startTime)) {
                whereSql += " and beginTime >= '" + startTime + "'";
                request.setAttribute("startTime", startTime);
            }
            if (!StringUtils.isEmpty(endTime)) {
                whereSql += " and endTime <= '" + endTime + "'";
                request.setAttribute("endTime", endTime);
            }
            whereSql += " order by userName, beginTime";
            pagination.setRequest(request);
            holidayList = holidayBiz.find(pagination, whereSql);
            if (holidayList != null && holidayList.size() > 0) {
                for (Holiday holiday1 : holidayList) {
                    Classes classes = classesBiz.findById(holiday1.getClassId());
                    if (ObjectUtils.isNotNull(classes)) {
                        holiday1.setClassName(classes.getName());
                    }
                }
            }
            request.setAttribute("holidayList", holidayList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("holiday", holiday);
            request.setAttribute("className", request.getParameter("className"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/workAttendance/student_holiday_list";
    }
}
