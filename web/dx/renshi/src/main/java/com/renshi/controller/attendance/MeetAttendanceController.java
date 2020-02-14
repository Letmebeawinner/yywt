package com.renshi.controller.attendance;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.StringUtils;
import com.renshi.biz.attendance.MeetAttendanceBiz;
import com.renshi.entity.attendance.MeetAttendance;
import com.renshi.entity.attendance.QueryMeetAttendance;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 268 on 2017/1/3.
 */
@Controller
@RequestMapping("/admin/rs")
public class MeetAttendanceController extends BaseController {

    /*private static Logger logger = LoggerFactory.getLogger(MeetAttendanceController.class);

    @InitBinder("meetAttendance")
    public void initMeetAttendance(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("meetAttendance.");
    }
    @InitBinder("queryMeetAttendance")
    public void initQueryMeetAttendance(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("queryMeetAttendance.");
    }
    @Autowired
    private MeetAttendanceBiz meetAttendanceBiz;

    *//**
     * 添加培训
     *
     * @param request
     * @param meetAttendance
     * @return
     *//*
    @RequestMapping("/addMeetAttendance")
    @ResponseBody
    public Map<String, Object> addMeetAttendance(HttpServletRequest request, @ModelAttribute("meetAttendance")MeetAttendance meetAttendance) {
        Map<String, Object> objectMap = null;
        try {

            meetAttendanceBiz.save(meetAttendance);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", meetAttendance);
        } catch (Exception e) {
            logger.error("addMeetAttendance", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    *//**
     * 培训列表
     *
     * @param request
     * @return
     *//*
    @RequestMapping("/getMeetAttendanceList")
    public ModelAndView getMeetAttendanceList(HttpServletRequest request,
                                              @ModelAttribute("pagination") Pagination pagination,
                                              @ModelAttribute("queryMeetAttendance") QueryMeetAttendance queryMeetAttendance) {
        ModelAndView modelAndView = new ModelAndView("/attendance/meetAttendance_list");
        try {
            String date=request.getParameter("queryCreateTime");
            if(!StringUtils.isTrimEmpty(date)){
                queryMeetAttendance.setSignInTime(DateUtils.parse(date,"yyyy-MM-dd"));
            }
            pagination.setRequest(request);
//            List<QueryMeetAttendance> meetAttendanceList = meetAttendanceBiz.getMeetAttendanceList(queryMeetAttendance,pagination);
            List<QueryMeetAttendance> meetAttendanceList =null;
            modelAndView.addObject("meetAttendanceList", meetAttendanceList);
            modelAndView.addObject("queryMeetAttendance", queryMeetAttendance);
        } catch (Exception e) {
            logger.error("getMeetAttendanceList", e);
        }
        return modelAndView;
    }*/
}
