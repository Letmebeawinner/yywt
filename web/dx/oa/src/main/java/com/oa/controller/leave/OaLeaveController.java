package com.oa.controller.leave;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.leave.OaLeaveBiz;
import com.oa.common.BaseHessianService;
import com.oa.entity.leave.OaLeave;
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
 * @author lzh
 * @create 2017-12-25-10:57
 */
@Controller
@RequestMapping("/admin/oa")
public class OaLeaveController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OaLeaveController.class);

    @Autowired
    private OaLeaveBiz oaLeaveBiz;
    @Autowired
    private BaseHessianService baseHessianService;
   
    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder("oaLeave")
    public void oaMeetingLeaveInit(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("oaLeave.");
    }

    /**
     * 启动
     * @param oaLeave 请假流程
     * @return map
     */
    @RequestMapping("/leave/process/start")
    @ResponseBody
    public Map<String, Object> startProcess(HttpServletRequest request, 
                                            @ModelAttribute("oaLeave") OaLeave oaLeave,
                                            @RequestParam("processDefinitionId") String processDefinitionId,
                                            @RequestParam(value = "userIds", required = false) String userIds) {
        Map<String, Object> json;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            String processInstanceId = oaLeaveBiz.tx_startLeaveProcess(oaLeave, processDefinitionId, userId, userIds);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, processInstanceId);
        } catch (Exception e) {
            logger.error("OaMeetingLeaveController.startProcess", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description: 议题审核
     */
    @RequestMapping("/leaveApplyAudit")
    @ResponseBody
    public Map<String, Object> leaveApplyAudit(@RequestParam("taskId") String taskId,
                                               @ModelAttribute("oaLeave") OaLeave leave,
                                               @RequestParam(value = "comment", required = false) String comment,
                                               @RequestParam(value = "userIds", required = false) String userIds,
                                               HttpServletRequest request) {
        Map<String, Object> json;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            oaLeaveBiz.tx_startLeaveApplyAudit(leave, taskId, comment, userId,userIds);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("OaMeetingLeaveController.leaveApplyAudit", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }

        return json;
    }


    /**
     * 查询分校领导
     *
     * @param request    {@link HttpServletRequest}
     * @return 分校领导列表
     */
    @RequestMapping("/ajax/querySchoolRole")
    @SuppressWarnings("unchecked")
    public String querySchoolRole(HttpServletRequest request) {
        try {
            List<Map<String,String>> userList = baseHessianService.querySchoolRole();
            request.setAttribute("userList", userList);
        } catch (Exception e) {
            logger.error("OaMeetingAgendaController.querySchoolRole()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/user/school_role_list";
    }
}
