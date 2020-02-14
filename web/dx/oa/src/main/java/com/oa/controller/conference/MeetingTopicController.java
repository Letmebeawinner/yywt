package com.oa.controller.conference;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.oa.biz.conference.MeetingTopicBiz;
import com.oa.common.StatusConstants;
import com.oa.entity.conference.MeetingTopic;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 会议管理
 *
 * @author YaoZhen
 * @create 10-23, 11:19, 2017.
 */
@Controller
public class MeetingTopicController extends BaseController {
    /**
     * 通用URL
     */
    public static final String ADMIN_OA_CONFERENCE = "/admin/oa/conference";
    private static final Logger logger = Logger.getLogger(MeetingTopicController.class);
    @Autowired
    private MeetingTopicBiz meetingTopicBiz;

    //数据绑定到实体
    @InitBinder({"meetingTopic"})
    public void meetingTopicInit(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("meetingTopic.");
    }

    //数据绑定到实体
    @InitBinder({"agenda"})
    public void agendaInit(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("agenda.");
    }

    /**
     * 添加议题
     *
     * @return 添加议题
     */
    @RequestMapping(ADMIN_OA_CONFERENCE + "/meetingTopic/toSave")
    public String toSaveMeetingTopic() {
        return "/conference/save_meeting_topic";
    }


    /**
     * 保存议题
     *
     * @param meetingTopic 议题
     * @return json
     */
    @RequestMapping(ADMIN_OA_CONFERENCE + "/meetingTopic/save")
    @ResponseBody
    public Map<String, Object> meetingTopicSave(@ModelAttribute("meetingTopic") MeetingTopic meetingTopic) {
        Map<String, Object> json;
        try {
            meetingTopicBiz.save(meetingTopic);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("MeetingTopicController.meetingTopicSave", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * 议题列表
     *
     * @param pagination 分页
     * @param name  议题名称
     * @param request    请求
     * @return
     */
    @RequestMapping(ADMIN_OA_CONFERENCE + "/meetingTopic/list")
    public ModelAndView meetingTopicList(@ModelAttribute("pagination") Pagination pagination,
                                         @RequestParam(value = "name", required = false) String name,
                                         HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/conference/list_meeting_topic");
        try {
            StringBuffer sb = new StringBuffer("status = ");
            sb.append(StatusConstants.normal);
            if (!StringUtils.isTrimEmpty(name)) {
                sb.append(" and name like '%").append(name).append("%'");
                // 环回
                mv.addObject("name", name);
            }
            // 环回URL
            pagination.setRequest(request);
            List<MeetingTopic> meetingTopics = meetingTopicBiz.find(pagination, sb.toString());
            mv.addObject("meetingTopics", meetingTopics);

            // 环回
            mv.addObject("pagination", pagination);
        } catch (Exception e) {
            logger.error("MeetingTopicController.meetingTopic", e);
        }
        return mv;
    }


    /**
     * 跳转编辑
     *
     * @param id id
     * @return 编辑
     */
    @RequestMapping(ADMIN_OA_CONFERENCE + "/meetingTopic/toUpdate")
    public ModelAndView toUpdateMeetingTopic(Long id) {
        ModelAndView mv = new ModelAndView("/conference/update_meeting_topic");
        try {
            MeetingTopic meetingTopic = meetingTopicBiz.findById(id);
            mv.addObject("meetingTopic", meetingTopic);
        } catch (Exception e) {
            logger.error("MeetingTopicController.toUpdateTeacherLibrary", e);
        }
        return mv;
    }

    /**
     * 修改
     *
     * @param meetingTopic 议题
     * @return json
     */
    @RequestMapping(ADMIN_OA_CONFERENCE + "/meetingTopic/update")
    @ResponseBody
    public Map<String, Object> updateMeetingTopic(@ModelAttribute("meetingTopic") MeetingTopic meetingTopic) {
        Map<String, Object> json;
        try {
            meetingTopicBiz.update(meetingTopic);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("MeetingTopicController.updateMeetingTopic", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 删除
     *
     * @param id 主键
     * @return
     */
    @RequestMapping(ADMIN_OA_CONFERENCE + "/meetingTopic/del")
    @ResponseBody
    public Map<String, Object> delMeetingTopic(Long id) {
        Map<String, Object> json;
        try {
            MeetingTopic topic = meetingTopicBiz.findById(id);
            if (ObjectUtils.isNotNull(topic)) {
                topic.setStatus(StatusConstants.remove);
                meetingTopicBiz.update(topic);
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("MeetingTopicController.delMeetingTopic", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * ajax查询所有信息，用用户下拉框
     *
     * @return
     */
    @RequestMapping("/admin/oa/ajax/get/all/topic")
    @ResponseBody
    public Map<String, Object> getAllCar() {
        Map<String, Object> json;
        // 默认为0
        String sql = " status = 0";
        try {
            List<MeetingTopic> topics = meetingTopicBiz.find(null, sql);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, topics);
        } catch (Exception e) {
            logger.error("OaMeetingTopicController.getAllCar", e);
            json = this.resultJson(ErrorCode.SYS_ERROR_MSG, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * ajax通过id获取信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/admin/oa/ajax/topic/getTopic")
    @ResponseBody
    public Map<String, Object> getCarById(@RequestParam("id") Long id) {
        Map<String, Object> json;
        try {
            MeetingTopic topic = meetingTopicBiz.findById(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, topic);
        } catch (Exception e) {
            logger.error("OaMeetingTopicController.getCarById", e);
            json = this.resultJson(ErrorCode.SYS_ERROR_MSG, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

}
