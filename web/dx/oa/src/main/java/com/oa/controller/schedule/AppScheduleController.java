package com.oa.controller.schedule;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.common.HrHessianBiz;
import com.oa.biz.schedule.ScheduleArrangeBiz;
import com.oa.biz.schedule.ScheduleBiz;
import com.oa.entity.employee.Employee;
import com.oa.entity.schedule.Schedule;
import com.oa.entity.schedule.ScheduleDto;
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
 * app日程接口
 *
 * @author ccl
 * @create 2017-01-22-14:31
 */
@Controller
@RequestMapping("/app/oa")
public class AppScheduleController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AppScheduleController.class);

    @Autowired
    private ScheduleBiz scheduleBiz;

    @Autowired
    private ScheduleArrangeBiz scheduleArrangeBiz;

    @Autowired
    private HrHessianBiz hrHessianBiz;

    @InitBinder({"schedule"})
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("schedule.");
    }

    /**
     * @Description:查询我的日程
     * @author: ccl
     * @Param: [userId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-22
     */
    @RequestMapping("/queryMySchedule")
    @ResponseBody
    public Map<String, Object> queryMySchedules(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @RequestParam("userId") Long userId) {
        Map<String, Object> resultMap = null;
        try {
            List<Schedule> scheduleList=new ArrayList<>();
            if (userId != null && userId > 0) {
                scheduleList = scheduleBiz.find(pagination, " senderId=" + userId);
            }
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("scheduleList", scheduleList);
            map.put("pagination", pagination);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "操作成功", map);
        } catch (Exception e) {
            logger.error("AppScheduleController--queryMySchedule", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:查询安排我的日程
     * @author: ccl
     * @Param: [userId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-22
     */
    @RequestMapping("/queryArrangeMySchedule")
    @ResponseBody
    public Map<String, Object> queryArrangeMySchedules(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,@RequestParam("userId") Long userId) {
        Map<String, Object> resultMap = null;
        try {
            List<ScheduleDto> scheduleArrangeList=new ArrayList<>();
            if(userId!=null&&userId>0){
                scheduleArrangeList = scheduleArrangeBiz.getScheduleDtos(pagination, " receiverId=" + userId);
            }
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("scheduleList", scheduleArrangeList);
            map.put("pagination", pagination);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "操作成功", map);
        } catch (Exception e) {
            logger.error("AppScheduleController--queryMySchedule", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:修改我的日程状态
     * @author: ccl
     * @Param: [schedule]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-22
     */
    @RequestMapping("/updateScheduleStatus")
    @ResponseBody
    public Map<String, Object> updateScheduleStatuses(@ModelAttribute("schedule") Schedule schedules) {
        Map<String, Object> resultMap = null;
        try {
            scheduleBiz.update(schedules);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("AppScheduleController--queryMySchedule", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

//    /**
//     * @Description:修改安排我的日程状态
//     * @author: ccl
//     * @Param: [schedule]
//     * @Return: java.util.Map<java.lang.String,java.lang.Object>
//     * @Date: 2017-01-22
//     */
//    @RequestMapping("/updateScheduleStatus")
//    @ResponseBody
//    public Map<String,Object> updateArrangeMyStatuses( @ModelAttribute("arrange") Arrange arrange){
//        Map<String, Object> resultMap = null;
//        try {
//            arrangeBiz.update(arrange);
//            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
//        } catch (Exception e) {
//            logger.error("AppScheduleController--queryMySchedule", e);
//            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
//        }
//        return resultMap;
//    }

    /**
     * @Description:添加日程
     * @author: ccl
     * @Param: [request, schedule]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-02-06
     */
    @RequestMapping("/saveSchedule")
    @ResponseBody
    public Map<String, Object> saveSchedule(HttpServletRequest request, @ModelAttribute("schedule") Schedule schedule, @RequestParam(value = "employeeIds", required = false) String employees) {
        Map<String, Object> resultMap = null;
        try {
            scheduleBiz.save(schedule);

            //添加安排日程的人的id
            if (employees != null) {
                scheduleArrangeBiz.tx_updateScheduleArrange(schedule.getId(), employees);
            }
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("AppScheduleController--saveSchedule", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:查询所有教职工
     * @author: ccl
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-03-06
     */
    @RequestMapping("/queryAllEmployee")
    @ResponseBody
    public Map<String, Object> queryAllEmployee(HttpServletRequest request) {
        Map<String, Object> resultMap = null;
        try {
            List<Employee> employeeList = hrHessianBiz.queryAllEmployee();
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", employeeList);
        } catch (Exception e) {
            logger.error("AppScheduleController--queryAllEmployee", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;

    }


}