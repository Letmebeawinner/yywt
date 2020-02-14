package com.houqin.controller.meeting;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.meeting.MeetingBiz;
import com.houqin.entity.meeting.Meeting;
import com.houqin.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/houqin")
public class MeetingController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    private MeetingBiz meetingBiz;

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"meeting"})
    public void initMeeting(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("meeting.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 查询所有的会场
     *
     * @param request
     * @param meeting
     * @param pagination
     * @return
     */
    @RequestMapping("/queryAllMeeting")
    public ModelAndView queryAllMeeting(HttpServletRequest request, @ModelAttribute("meeting") Meeting meeting, @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/meeting/meeting_list");
        try {
            String whereSql = GenerateSqlUtil.getSql(meeting);
            whereSql += " order by name";
            pagination.setPageSize(20);
            pagination.setRequest(request);
            List<Meeting> meetingList = meetingBiz.find(pagination, whereSql);
            mv.addObject("meetingList", meetingList);
            mv.addObject("meeting", meeting);
            mv.addObject("page", pagination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;

    }


    /**
     * 去添加
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddMeeting")
    public String toAddMeeting(HttpServletRequest request) {
        return "/meeting/add-meeting";
    }

    /**
     * 添加保存
     *
     * @param meeting
     * @return
     */
    @RequestMapping("/addSaveMeeting")
    @ResponseBody
    public Map<String, Object> addSaveMeeting(@ModelAttribute("meeting") Meeting meeting) {
        Map<String, Object> json = null;
        try {
            meeting.setStatus(0);
            meetingBiz.save(meeting);
            json = this.resultJson(ErrorCode.SUCCESS, "添加成功", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 修改会场
     *
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateMeeting")
    public ModelAndView toUpdateMeeting(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("/meeting/update-meeting");
        try {
            Meeting meeting = meetingBiz.findById(id);
            mv.addObject("meeting", meeting);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }

    /**
     * 修改会场
     *
     * @param request
     * @param meeting
     * @return
     */
    @RequestMapping("/updateMeeting")
    @ResponseBody
    public Map<String, Object> updateMeeting(HttpServletRequest request, @ModelAttribute("meeting") Meeting meeting) {
        Map<String, Object> json = null;
        try {
            meetingBiz.update(meeting);
            json = this.resultJson(ErrorCode.SUCCESS, "修改成功", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * @Description:删除
     * @author: ccl
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     */
    @RequestMapping("/delMeeting")
    @ResponseBody
    public Map<String, Object> delMeeting(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            meetingBiz.deleteById(Long.parseLong(id));
        } catch (Exception e) {
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 修改维修状态
     *
     * @param request
     * @return
     */
    @RequestMapping("/repairMeeting")
    @ResponseBody
    public Map<String, Object> repairMeeting(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            String id = request.getParameter("id");
            String status = request.getParameter("status");
            Meeting meeting = new Meeting();
            meeting.setStatus(Integer.parseInt(status));
            meeting.setId(Long.parseLong(id));
            meetingBiz.update(meeting);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
