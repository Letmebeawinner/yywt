package com.oa.controller.meeting;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.meeting.MeetingBiz;
import com.oa.entity.meeting.Meeting;
import com.oa.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 会议室配置
 *
 * @author ccl
 * @create 2016-12-27-11:28
 */
@Controller
@RequestMapping("/admin/oa")
public class MeetingController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    private MeetingBiz meetingBiz;

    @InitBinder({"meeting"})
    public void initMeeting(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("meeting.");
    }

    private static final String createMeeting = "/meeting/meeting_add";
    private static final String toUpdateMeeting = "/meeting/meeting_update";
    private static final String meetingList = "/meeting/meeting_list";

    /**
     * @Description:查询所有的会议室
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-27
     */
    @RequestMapping("/queryAllMeeting")
    public String queryAllMeeting(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("meeting") Meeting meeting) {
        try {
            String whereSql = GenerateSqlUtil.getSql(meeting);
            pagination.setRequest(request);
            List<Meeting> meetingList = meetingBiz.find(pagination, whereSql);
            request.setAttribute("meetingList", meetingList);
            request.setAttribute("meeting", meeting);
        } catch (Exception e) {
            logger.error("MeetingController--queryAllMeeting", e);
            return this.setErrorPath(request, e);
        }
        return meetingList;
    }

    /**
     * @Description:查询所有的会议室 (ajax)
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     */
    @RequestMapping("/ajax/queryAllMeeting")
    @ResponseBody
    public Map<String, Object> ajaxQueryAllMeeting() {
        Map<String, Object> json = null;
        try {
            List<Meeting> meetings = meetingBiz.findAll();
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, meetings);
        } catch (Exception e) {
            logger.error("MeetingController--ajaxQueryAllMeeting", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.ERROR_SYSTEM, null);
        }
        return json;
    }

    /**
     * @Description:查询会议信息通过id (ajax)
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     */
    @RequestMapping("/ajax/get/meeting")
    @ResponseBody
    public Map<String, Object> getMeetingById(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
             Meeting meeting = meetingBiz.findById(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, meeting);
        } catch (Exception e) {
            logger.error("MeetingController--getMeetingById", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.ERROR_SYSTEM, null);
        }
        return json;
    }

    /**
     * @Description:去添加会议室
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-27
     */
    @RequestMapping("/toAddMeeting")
    public String toAddMeeting(HttpServletRequest request) {
        return createMeeting;
    }


    /**
     * @Description:添加保存
     * @author: ccl
     * @Param: [request, meeting]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-27
     */
    @RequestMapping("/addSaveMeeting")
    @ResponseBody
    public Map<String, Object> addSaveMeeting(HttpServletRequest request, @ModelAttribute("meeting") Meeting meeting) {
        Map<String, Object> resultMap = null;
        try {
            meetingBiz.save(meeting);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("MeetingController--addSaveMeeting", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:去修改会议室
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-27
     */
    @RequestMapping("/toUpdateMeeting")
    public String toUpdateMeeting(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            Meeting meeting = meetingBiz.findById(id);
            request.setAttribute("meeting", meeting);
        } catch (Exception e) {
            logger.error("MeetingController--toUpdateMeeting", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateMeeting;
    }

    /**
     * @Description:修改会议室
     * @author: ccl
     * @Param: [request, meeting]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-27
     */
    @RequestMapping("/updateMeeting")
    @ResponseBody
    public Map<String, Object> updateMeeting(HttpServletRequest request, @ModelAttribute("meeting") Meeting meeting) {
        Map<String, Object> resultMap = null;
        try {
            meetingBiz.update(meeting);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.info("MeetingController--updateMeeting", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:删除
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-27
     */
    @RequestMapping("/deleteMeeting")
    @ResponseBody
    public Map<String, Object> deleteMeeting(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            meetingBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("MeetingController--deleteMeeting", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


}
