package com.houqin.controller.meeting;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.houqin.biz.meeting.MeetingBiz;
import com.houqin.biz.meeting.MeetingRepairRecordBiz;
import com.houqin.entity.meeting.Meeting;
import com.houqin.entity.meeting.MeetingRepairRecord;
import com.houqin.utils.GenerateSqlUtil;
import org.apache.commons.lang.StringUtils;
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
public class MeetingRepairRecordController extends BaseController {


    @Autowired
    private MeetingRepairRecordBiz meetingRepairRecordBiz;
    @Autowired
    private MeetingBiz meetingBiz;

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"meetingRepairRecord"})
    public void initMeetingRepair(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("meetingRepairRecord.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 查询维修记录
     *
     * @param meetingRepairRecord
     * @param pagination
     * @return
     */
    @RequestMapping("/queryRepairRecordList")
    public ModelAndView meetingRepairRecordList(@ModelAttribute("meetingRepairRecord") MeetingRepairRecord meetingRepairRecord, @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/meeting/meeting_repair_list");
        try {
            String whereSql = GenerateSqlUtil.getSql(meetingRepairRecord);
            whereSql += " order by id desc ";
            List<MeetingRepairRecord> meetingRepairRecordList = meetingRepairRecordBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(meetingRepairRecordList)) {
                for (MeetingRepairRecord m : meetingRepairRecordList) {
                    Meeting meeting = meetingBiz.findById(m.getMeetingId());
                    if (ObjectUtils.isNotNull(meeting)) {
                        if (StringUtils.isNotBlank(meeting.getName())) {
                            m.setMeetingName(meeting.getName());
                        }
                    }
                }
            }
            mv.addObject("meetingRepairRecordList", meetingRepairRecordList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/toAddMeetingRepairRecord")
    public String toAddRepairRecord(HttpServletRequest request) {
        return "/meeting/meeting_repair";
    }

    /**
     * 添加
     *
     * @return
     */
    @RequestMapping("/addMeetingRepairRecord")
    @ResponseBody
    public Map<String, Object> addMeetingRepairRecord(@ModelAttribute("meetingRepairRecord") MeetingRepairRecord meetingRepairRecord) {
        Map<String, Object> json = null;
        try {
            meetingRepairRecordBiz.save(meetingRepairRecord);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 修改
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateRepairRecord")
    public String toUpdateRepairRecord(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            MeetingRepairRecord meetingRepairRecord = meetingRepairRecordBiz.findById(id);
            request.setAttribute("meetingRepairRecord", meetingRepairRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/meeting/meeting_repair_update";
    }

    /**
     * 修改
     *
     * @param meetingRepairRecord
     * @return
     */
    @RequestMapping("/updateRepairRecord")
    @ResponseBody
    public Map<String, Object> updateRepairRecord(@ModelAttribute("meetingRepairRecord") MeetingRepairRecord meetingRepairRecord) {
        Map<String, Object> json = null;
        try {
            meetingRepairRecordBiz.update(meetingRepairRecord);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteRepairRecord")
    @ResponseBody
    public Map<String, Object> deleteRepairRecord(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            meetingRepairRecordBiz.deleteById(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
