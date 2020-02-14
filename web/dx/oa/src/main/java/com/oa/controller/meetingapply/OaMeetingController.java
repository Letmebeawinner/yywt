package com.oa.controller.meetingapply;
import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.meetingapply.OaMeetingBiz;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.entity.meetingapply.OaMeeting;
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
import java.util.Map;

/**
 * Oa会议控制层
 *
 * @author lzh
 * @create 2017-03-16-11:47
 */
@Controller
@RequestMapping("/admin/oa")
public class OaMeetingController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OaMeetingController.class);

    @Autowired
    private OaMeetingBiz oaMeetingBiz;
    @Autowired
    private WorkflowFormBiz workflowFormBiz;

    @InitBinder("oaMeeting")
    public void initMeeting(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("oaMeeting.");
    }

    /**
     * @Description: 启动流程，将数据存入业务表，同时关联起来
     * @author: lzh
     * @Param: [request, meeting, processDefinitionId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 20:35
     */
    @RequestMapping("/meeting/process/start")
    @ResponseBody
    public Map<String, Object> startProcess(HttpServletRequest request,
                                            @ModelAttribute("oaMeeting") OaMeeting meeting,
                                            @RequestParam("processDefinitionId") String processDefinitionId) {
        Map<String, Object> json = null;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            String processInstanceId = oaMeetingBiz.tx_startMeetingProcess(meeting, processDefinitionId, userId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, processInstanceId);
        } catch (Exception e) {
            logger.error("OaMeetingController.startProcess", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    @RequestMapping("/meeting/complete")
    @ResponseBody
    public Map<String, Object> completeTask(@RequestParam("taskId") String taskId,
                                            @RequestParam("flag") int flag,
                                            @RequestParam("processInstanceId") String processInstanceId,
                                            @RequestParam("comment") String comment,
                                            @ModelAttribute("oaMeeting") OaMeeting oaMeeting,
                                            HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            workflowFormBiz.tx_startAudit(taskId, processInstanceId, comment, flag, userId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("OaMeetingController.completeTask", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description: 会议审核
     * @author: lzh
     * @Param: [taskId, carApplyOpiont, refuseReason]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:31
     */
    @RequestMapping("/meetingApplyAudit")
    @ResponseBody
    public Map<String, Object> meetingApplyAudit(@RequestParam("taskId") String taskId,
                                                @ModelAttribute("oaMeeting") OaMeeting oaMeeting,
                                                @RequestParam(value = "comment", required = false) String comment,
                                                HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            oaMeetingBiz.tx_startMeetingApplyAudit(oaMeeting, taskId, comment, userId);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("OaMeetingController.meetingApplyAudit", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }

        return json;
    }

}
