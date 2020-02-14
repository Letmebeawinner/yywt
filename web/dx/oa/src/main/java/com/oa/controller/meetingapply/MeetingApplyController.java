package com.oa.controller.meetingapply;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.meeting.MeetingBiz;
import com.oa.biz.meetingapply.MeetingapplyBiz;
import com.oa.entity.meeting.Meeting;
import com.oa.entity.meetingapply.MeetingApplyDto;
import com.oa.entity.meetingapply.Meetingapply;
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
 * 会议室申请
 *
 * @author ccl
 * @create 2016-12-28-10:40
 */
@Controller
@RequestMapping("/admin/oa")
public class MeetingApplyController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(MeetingApplyController.class);

    @Autowired
    private MeetingBiz meetingBiz;
    @Autowired
    private MeetingapplyBiz meetApplyBiz;

    @InitBinder("meetApply")
    public void initBinderMeetApply(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("meetApply.");
    }

    private static final String toAddMeetApply = "/meetApply/meetApply_add";
    private static final String toUpdateMeetApply = "/meetApply/meetApply_update";
    private static final String MeetApplyList = "/meetApply/meetApply_list";

    /**
     * @Description:查询所有申请
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-28
     */
    @RequestMapping("/queryAllMeetApply")
    public String queryAllMeetApply(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,@ModelAttribute("meetApply") Meetingapply meetApply) {
        try {
            pagination.setRequest(request);
            List<MeetingApplyDto> meetingApplyList=meetApplyBiz.getMeetingApplyDtos(pagination,meetApply);
            request.setAttribute("meetingApplyList",meetingApplyList);

            //查询所有的会议室
            List<Meeting> meetingList = meetingBiz.meetingList();
            request.setAttribute("meetingList", meetingList);
            request.setAttribute("meetApply", meetApply);

        } catch (Exception e) {
            logger.info("MeetingApplyController--queryAllMeetApply", e);
            return this.setErrorPath(request, e);
        }
        return MeetApplyList;
    }


    /**
     * @Description:去添加申请
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-28
     */
    @RequestMapping("/toAddMeetApply")
    public String toAddMeetApply(HttpServletRequest request) {
        try {
            List<Meeting> meetingList = meetingBiz.meetingList();
            request.setAttribute("meetingList", meetingList);
        } catch (Exception e) {
            logger.info("MeetingApplyController--toAddMeetApply", e);
            return this.setErrorPath(request, e);
        }
        return toAddMeetApply;
    }

    /**
     * @Description:添加保存申请
     * @author: ccl
     * @Param: [request, meetApply]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-28
     */
    @RequestMapping("/addSaveApply")
    @ResponseBody
    public Map<String, Object> addSaveApply(HttpServletRequest request, @ModelAttribute("meetApply") Meetingapply meetApply) {
        Map<String, Object> resultMap = null;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            meetApply.setStatus(0);
            meetApply.setUserId(userId);
            meetApplyBiz.save(meetApply);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.info("MeetingApplyController--addSaveApply", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:去修改会议室申请
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-28
     */
    @RequestMapping("/toUpdateMeetApply")
    public String toUpdateMeetApply(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            Meetingapply meetApply = meetApplyBiz.findById(id);
            request.setAttribute("meetApply", meetApply);

            List<Meeting> meetingList = meetingBiz.meetingList();
            request.setAttribute("meetingList", meetingList);
        } catch (Exception e) {
            logger.info("MeetingApplyController--toUpdateApply", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateMeetApply;
    }


    /**
     * @Description:会议室申请修改
     * @author: ccl
     * @Param: [request, meetApply]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-28
     */
    @RequestMapping("/updateMeetApply")
    @ResponseBody
    public Map<String, Object> updateMeetApply(HttpServletRequest request, @ModelAttribute("meetApply") Meetingapply meetApply) {
        Map<String, Object> resultMap = null;
        try {
            meetApplyBiz.update(meetApply);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.info("MeetingApplyController--updateMeetApply", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:删除会议室申请
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-28
     */
    @RequestMapping("/deleteMeetApply")
    @ResponseBody
    public Map<String, Object> deleteMeetApply(HttpServletRequest request,@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            meetApplyBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("MeetingApplyController--deleteMeetApply", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:会议审核
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-28
     */
    @RequestMapping("/checkMeetApply")
    @ResponseBody
    public Map<String, Object> checkMeetApply(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            Meetingapply meetApply=meetApplyBiz.findById(id);
            meetApply.setStatus(1);
            meetApplyBiz.update(meetApply);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "审核成功", null);
        } catch (Exception e) {
            logger.info("MeetingApplyController--checkMeetApply", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }



}
