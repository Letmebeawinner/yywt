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
import com.oa.entity.schedule.ScheduleArrange;
import com.oa.entity.schedule.ScheduleDto;
import com.oa.utils.GenerateSqlUtil;
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
import java.util.List;
import java.util.Map;

/**
 * 日程安排
 *
 * @author ccl
 * @create 2017-01-17-18:37
 */
@Controller
@RequestMapping("/admin/oa")
public class ScheduleController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ScheduleController.class);

    @Autowired
    private ScheduleBiz scheduleBiz;
    @Autowired
    private ScheduleArrangeBiz scheduleArrangeBiz;
    @Autowired
    private HrHessianBiz hrHessianBiz;

    @InitBinder({"schedule"})
    public void initSchedule(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("schedule.");
    }

    @InitBinder({"scheduleArrange"})
    public void initArrangeSchedule(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("scheduleArrange.");
    }

    @InitBinder
    public void initBinderSchedule(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private static final String toAddSchedule = "/schedule/schedule_add";//添加日程
    private static final String toUpdateSchedule = "/schedule/schedule_update";//修改日程
    private static final String scheduleList = "/schedule/schedule_list";//日程列表

    private static final String myScheduleList = "/schedule/mySchedule_list";//我的日程
    private static final String arrangeList = "/schedule/select_arrange_list";//选择教职工列表
    private static final String arrangeForMeList = "/schedule/arrangeForMe_list";//安排我的日程列表


    /**
     * @Description:去添加日程
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toAddSchedule")
    public String toAddSchedule(HttpServletRequest request) {
        try {
        } catch (Exception e) {
            logger.info("ScheduleController--toAddSchedule", e);
            return this.setErrorPath(request, e);
        }
        return toAddSchedule;
    }


    /**
     * @Description:保存日程
     * @author: ccl
     * @Param: [schedule]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/addSaveSchedule")
    @ResponseBody
    public Map<String, Object> addSaveSchedule(HttpServletRequest request, @ModelAttribute("schedule") Schedule schedule, @RequestParam(value = "employeeIds", required = false) String employees) {
        Map<String, Object> resultMap = null;
        try {
            //获取发送人id
            Long userId = SysUserUtils.getLoginSysUserId(request);
            schedule.setSenderId(userId);
            scheduleBiz.save(schedule);

            //添加安排日程的人的id
            if (employees != null) {
                scheduleArrangeBiz.tx_updateScheduleArrange(schedule.getId(), employees);
            }
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("scheduleController--addSaveSchedule", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * 查询所有的日程
     *
     * @author: ccl
     * @Param: [request, pagination, schedule]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/queryAllSchedule")
    public String queryAllSchedule(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("schedule") Schedule schedule) {
        try {
            String whereSql = GenerateSqlUtil.getSql(schedule);
            pagination.setRequest(request);
            List<Schedule> scheduleList = scheduleBiz.find(pagination, whereSql);
            request.setAttribute("scheduleList", scheduleList);
        } catch (Exception e) {
            logger.info("ScheduleController--queryAllSchedule", e);
            return this.setErrorPath(request, e);
        }
        return scheduleList;
    }


    /**
     * @Description:查询我的日程
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2017-01-20
     */
    @RequestMapping("/queryMySchedule")
    public String queryMySchedule(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("schedule") Schedule schedule) {
        try {
            String whereSql = GenerateSqlUtil.getSql(schedule);
            Long senderId = SysUserUtils.getLoginSysUserId(request);
            whereSql += " and senderId=" + senderId;
            List<Schedule> scheduleList = scheduleBiz.find(pagination, whereSql);
            request.setAttribute("scheduleList", scheduleList);
        } catch (Exception e) {
            logger.error("ScheduleController--queryMySchedule", e);
            return this.setErrorPath(request, e);
        }
        return myScheduleList;
    }


    /**
     * @Description:安排我的日程
     * @author: ccl
     * @Param: [request, pagination, schedule]
     * @Return: java.lang.String
     * @Date: 2017-01-20
     */
    @RequestMapping("/sendToMySchedule")
    public String sendToMySchedule(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("scheduleArrange") ScheduleArrange scheduleArrange) {
        try {
            String whereSql = GenerateSqlUtil.getSql(scheduleArrange);
            //接收人id
            Long receiverId = SysUserUtils.getLoginSysUserId(request);
            whereSql += " and receiverId=" + receiverId;

            List<ScheduleDto> scheduleArrangeList = scheduleArrangeBiz.getScheduleDtos(pagination, whereSql);
            request.setAttribute("scheduleArrangeList", scheduleArrangeList);
        } catch (Exception e) {
            logger.error("ScheduleController--sendToMySchedule", e);
            return this.setErrorPath(request, e);
        }
        return arrangeForMeList;
    }


    /**
     * @Description:去修改日程
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toUpdateSchedule")
    public String toUpdateSchedule(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            Schedule schedule = scheduleBiz.findById(id);
            request.setAttribute("schedule", schedule);
        } catch (Exception e) {
            logger.error("ScheduleController--toUpdateSchedule", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateSchedule;
    }


    /**
     * @Description:修改日程
     * @author: ccl
     * @Param: [request, schedule]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/updateSchedule")
    @ResponseBody
    public Map<String, Object> updateSchedule(HttpServletRequest request, @ModelAttribute("schedule") Schedule schedule) {
        Map<String, Object> resultMap = null;
        try {
            scheduleBiz.update(schedule);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("ScheduleController--updateSchedule", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:删除日程
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/deleteSchedule")
    @ResponseBody
    public Map<String, Object> deleteSchedule(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            scheduleBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("ScheduleController-deleteSchedule", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:处理日程
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/cancelSchedule")
    @ResponseBody
    public Map<String, Object> cancelSchedule(HttpServletRequest request, @ModelAttribute("schedule") Schedule schedule) {
        Map<String, Object> resultMap = null;
        try {
            scheduleBiz.update(schedule);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("ScheduleController-cancelSchedule", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:添加教职工列表
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2017-01-18
     */
    @RequestMapping("/ajax/selectEmployee")
    public String selectEmployee(HttpServletRequest request) {
        try {
            List<Employee> employeeList = hrHessianBiz.queryAllEmployee();
            request.setAttribute("employeeList", employeeList);
        } catch (Exception e) {
            logger.error("ScheduleController--selectEmployee", e);
            return this.setErrorPath(request, e);
        }
        return arrangeList;
    }


    /**
     * @Description:
     * @author: ccl
     * @Param: [arrange]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-19
     */
    @RequestMapping("/updateArrange")
    @ResponseBody
    public Map<String, Object> updateArrange(@RequestParam("userId") Long userId, @RequestParam("scheduleIds") String scheduleIds) {
        Map<String, Object> resultMap = null;
        try {
            scheduleArrangeBiz.tx_updateScheduleArrange(userId, scheduleIds);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("ScheduleController--addSaveArrange", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:查询教职工通过多个id
     * @author: ccl
     * @Param: [request, ids]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-02-23
     */
    @RequestMapping("/getEmployeeByIds")
    @ResponseBody
    public Map<String, Object> getEmployeeByIds(HttpServletRequest request, @RequestParam("ids") String ids) {
        Map<String, Object> resultMap = null;
        try {
            List<Map<String,String>>  employees=hrHessianBiz.queryEmployeeByIds(ids);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "", employees);
        } catch (Exception e) {
            logger.error("ScheduleController--getEmployeeByIds", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


}
