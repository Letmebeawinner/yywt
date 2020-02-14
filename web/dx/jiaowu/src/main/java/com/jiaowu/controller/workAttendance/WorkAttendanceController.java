package com.jiaowu.controller.workAttendance;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.holiday.HolidayBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.holiday.Holiday;
import com.jiaowu.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }
    @InitBinder({"holiday"})
    public void initBinderHoliday(WebDataBinder binder){
        binder.setFieldDefaultPrefix("holiday.");
    }

    /**
     * 跳转到请假页面
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/holiday/toCreateHoliday")
    public String toCreateHoliday(){
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
        return "/admin/workAttendance/toCreateHoliday";
    }

    /**
     * 添加请假记录
     * @param request
     * @param holiday
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/holiday/createHoliday")
    @ResponseBody
    public Map<String,Object> createHoliday(HttpServletRequest request,@ModelAttribute("holiday")Holiday holiday){
        Map<String,Object> json=null;
        try{
            String errorInfo=validateHoliday(holiday);
            if(!StringUtils.isTrimEmpty(errorInfo)){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY,errorInfo,
                        null);
            }
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            String userType=userMap.get("userType");
            if(userType==null||userType.equals("")||userType.equals("1")){
                return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "您无权限进行请假",
                        null);
            }
            if(userType.equals("2")){
                Map<String,String> teacherMap=hrHessianService.queryEmployeeById(Long.parseLong(userMap.get("linkId")));
                holiday.setUserId(Long.parseLong(userMap.get("linkId")));
                holiday.setUserName(teacherMap.get("name"));
            }else{
                User user=userBiz.findById(Long.parseLong(userMap.get("linkId")));
                holiday.setClassId(user.getClassId());
                holiday.setUserId(user.getId());
                holiday.setUserName(user.getName());
            }


            holiday.setType(Integer.parseInt(userType));

            holiday.setLength(calculateLength(holiday));
            holidayBiz.save(holiday);
            json = this.resultJson(ErrorCode.SUCCESS, "您已提交请假申请,请等待管理员审批。",
                    null);
        }catch (Exception e){
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 请假列表
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/holiday/holidayList")
    public String holidayList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
        try{
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            boolean isAdministrator=userMap.get("userType").equals("1")?true:false;
            request.setAttribute("isAdministrator",isAdministrator);

            List<Holiday> holidayList=null;
            String whereSql=" 1=1";

            if(userMap.get("userType").equals("2")) {
                String teacherId = userMap.get("linkId");
                List<Classes> classesList = classesBiz.find(null, " teacherId=" + teacherId);
                if (classesList != null && classesList.size() > 0) {
                    whereSql+=" and classId=" + classesList.get(0).getId();
                }
            }

            Holiday holiday=new Holiday();
            String userName=request.getParameter("userName");
            if(!StringUtils.isTrimEmpty(userName)){
                whereSql+=" and userName like '%"+userName+"%'";
                holiday.setUserName(userName);
            }
            String status=request.getParameter("status");
            if(!StringUtils.isTrimEmpty(status)){
                whereSql+=" and status="+status;
                holiday.setStatus(Integer.parseInt(status));
            }
            if(isAdministrator) {
                String classId = request.getParameter("classId");
                if (!StringUtils.isTrimEmpty(classId) && !classId.equals("0")) {
                    whereSql += " and classId=" + classId;
                    holiday.setClassId(Long.parseLong(classId));
                }
                String type=request.getParameter("type");
                if(!StringUtils.isTrimEmpty(type)){
                    whereSql+=" and type="+type;
                    holiday.setType(Integer.parseInt(type));
                }
            }else{
                List<Classes> classesList=classesBiz.find(null," status=1 and teacherId="+userMap.get("linkId"));
                holiday.setClassId(classesList!=null&&classesList.size()>0?classesList.get(0).getId():-1L);
            }
            pagination.setRequest(request);
            holidayList = holidayBiz.find(pagination,whereSql);
            if(holidayList!=null&&holidayList.size()>0){
                for(Holiday holiday1:holidayList){
                    Classes classes=classesBiz.findById(holiday1.getClassId());
                    holiday1.setClassName(classes.getName());
                }
            }
            request.setAttribute("holidayList",holidayList);
            request.setAttribute("pagination",pagination);
            request.setAttribute("holiday",holiday);
            request.setAttribute("className",request.getParameter("className"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return "/admin/workAttendance/holiday_list";
    }

    /**
     * 审核通过
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/holiday/pass")
    @ResponseBody
    public Map<String,Object> pass(Long id){
        Map<String,Object> json=null;
        try{
            Holiday holiday=new Holiday();
            holiday.setId(id);
            holiday.setStatus(1);
            holidayBiz.update(holiday);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        }catch (Exception e){
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 审核不通过
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX+"/holiday/deny")
    @ResponseBody
    public Map<String,Object> deny(Long id){
        Map<String,Object> json=null;
        try{
            Holiday holiday=new Holiday();
            holiday.setId(id);
            holiday.setStatus(0);
            holidayBiz.update(holiday);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        }catch (Exception e){
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM,
                    ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 验证假期
     * @param holiday
     * @return
     */
    public String validateHoliday(Holiday holiday){
        if(holiday.getBeginTime()==null||holiday.getEndTime()==null){
            return "请填写开始时间和结束时间";
        }
        if(holiday.getBeginTime().getTime()<=new Date().getTime()){
            return "开始时间不能早于当前时间";
        }
        if(holiday.getEndTime().getTime()<=holiday.getBeginTime().getTime()){
            return "结束时间不能早于开始时间";
        }
        if(StringUtils.isTrimEmpty(holiday.getReason())){
            return "请填写请假原因";
        }
        return null;
    }

    /**
     * 计算时间差
     * @param holiday
     * @return
     */
    public  String calculateLength(Holiday holiday){
        StringBuffer sb=new StringBuffer();
        TimeZone tz = TimeZone.getTimeZone("GMT");
        Calendar beginCalendar= Calendar.getInstance();
        Calendar endCalendar=Calendar.getInstance();
        beginCalendar.setTime(holiday.getBeginTime());
        endCalendar.setTime(holiday.getEndTime());
        beginCalendar.setTimeZone(tz);
        endCalendar.setTimeZone(tz);
        /*SimpleDateFormat sdf=new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
        String[] beginTimeArray=sdf.format(beginCalendar).split(":");
        String[] endTimeArray=sdf.format(endCalendar).split(":");*/
        long interval=endCalendar.getTimeInMillis()-beginCalendar.getTimeInMillis();
        //24*60*60*1000
        if(interval>=24*60*60*1000){
            sb.append(interval/(24*60*60*1000)+"天");
            long leftTime=interval%(24*60*60*1000);
            if(leftTime!=0) {
                sb.append(leftTime % (60 * 60 * 1000) == 0 ? leftTime / (60 * 60 * 1000) + "小时" : (leftTime / (60 * 60 * 1000) + 1) + "小时");
            }
        }else{
            sb.append(interval%(60*60*1000)==0?interval/(60*60*1000)+"小时":(interval/(60*60*1000)+1)+"小时");
        }
        return sb.toString();
    }
}
