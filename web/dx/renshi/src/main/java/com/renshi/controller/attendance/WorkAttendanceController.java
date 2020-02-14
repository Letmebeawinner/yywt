package com.renshi.controller.attendance;

import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.renshi.biz.attendance.WorkAttendanceBiz;
import com.renshi.biz.attendance.WorkStatisticsBiz;
import com.renshi.entity.attendance.QueryWorkAttendance;
import com.renshi.entity.attendance.WorkStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 268 on 2017/1/3.
 */
@Controller
@RequestMapping("/admin/rs")
public class WorkAttendanceController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(WorkAttendanceController.class);

    /*@InitBinder("workAttendance")
    public void initWorkAttendance(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("workAttendance.");
    }
    @InitBinder("queryWorkAttendance")
    public void initQueryWorkAttendance(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("queryWorkAttendance.");
    }
    @Autowired
    private WorkAttendanceBiz workAttendanceBiz;
    @Autowired
    private WorkStatisticsBiz workStatisticsBiz;

    *//**
     * 工作考勤列表
     *
     * @param request
     * @return
     *//*
    @RequestMapping("/getWorkAttendanceList")
    public ModelAndView getWorkAttendanceList(HttpServletRequest request,
                                        @ModelAttribute("pagination") Pagination pagination,
                                        @ModelAttribute("queryWorkAttendance") QueryWorkAttendance queryWorkAttendance) {
        ModelAndView modelAndView = new ModelAndView("/attendance/workAttendance_list");
        try {
            String date=request.getParameter("queryCreateTime");
            if(!StringUtils.isTrimEmpty(date)){
                queryWorkAttendance.setWorkDate(date);
            }
            List<QueryWorkAttendance> workAttendanceList = workAttendanceBiz.getWorkAttendanceList(queryWorkAttendance,null);
            pagination.setRequest(request);
            modelAndView.addObject("workAttendanceList",workAttendanceList);
        } catch (Exception e) {
            logger.error("getWorkAttendanceList", e);
        }
        return modelAndView;
    }

    *//**
     * 工作考勤统计列表
     *
     * @param request
     * @return
     *//*
    @RequestMapping("/getWorkStatisticsList")
    public ModelAndView getWorkStatisticsList(HttpServletRequest request,
                                              @ModelAttribute("pagination") Pagination pagination
                                              ) {
        ModelAndView modelAndView = new ModelAndView("/attendance/workStatistics_list");
        try {
            String year=request.getParameter("year");
            String month=request.getParameter("month");
            String day=request.getParameter("day");
            pagination.setRequest(request);
            List<WorkStatistics> workStatisticsList = workStatisticsBiz.getWorkStatisticsList(pagination, year, month, day);
            modelAndView.addObject("year",year);
            modelAndView.addObject("month",month);
            modelAndView.addObject("day",day);
            modelAndView.addObject("workStatisticsList",workStatisticsList);
        } catch (Exception e) {
            logger.error("getWorkStatisticsList", e);
        }
        return modelAndView;
    }*/
}
