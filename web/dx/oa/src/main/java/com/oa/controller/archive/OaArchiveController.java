package com.oa.controller.archive;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.archivetype.OaArchiveSearchBiz;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.entity.archivetype.OaArchive;
import com.oa.entity.archivetype.OaArchiveSearch;
import com.oa.entity.sysuser.SysUser;
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
 * 档案申请流程
 *
 */
@Controller
@RequestMapping("/admin/oa")
public class OaArchiveController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(OaArchiveController.class);

    @Autowired
    private OaArchiveSearchBiz oaArchiveBiz;
    @Autowired
    private WorkflowFormBiz workflowFormBiz;

    @InitBinder("oaArchive")
    public void initArchive(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("oaArchive.");
    }

    /**
     * @Description: 启动流程，将数据存入业务表，同时关联起来
     * @author: lzh
     * @Param: [request, archive, processDefinitionId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 20:35
     */
    @RequestMapping("/archive/process/start")
    @ResponseBody
    public Map<String, Object> startProcess(HttpServletRequest request,
                                            @ModelAttribute("oaArchive") OaArchiveSearch archive,
                                            @RequestParam("processDefinitionId") String processDefinitionId) {
        Map<String, Object> json = null;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            String processInstanceId = oaArchiveBiz.tx_startArchiveProcess(archive, processDefinitionId, userId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, processInstanceId);
        } catch (Exception e) {
            logger.error("OaArchiveController.startProcess", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    @RequestMapping("/archive/complete")
    @ResponseBody
    public Map<String, Object> completeTask(@RequestParam("taskId") String taskId,
                                            @RequestParam("flag") int flag,
                                            @RequestParam("processInstanceId") String processInstanceId,
                                            @RequestParam("comment") String comment,
                                            @ModelAttribute("oaArchive") OaArchive oaArchive,
                                            HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            workflowFormBiz.tx_startAudit(taskId, processInstanceId, comment, flag, userId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("OaArchiveController.completeTask", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description: 档案审核
     * @author: lzh
     * @Param: [taskId, carApplyOpiont, refuseReason]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:31
     */
    @RequestMapping("/archiveApplyAudit")
    @ResponseBody
    public Map<String, Object> archiveApplyAudit(@RequestParam("taskId") String taskId,
                                                 @ModelAttribute("oaArchive") OaArchiveSearch oaArchive,
                                                 @RequestParam(value = "comment", required = false) String comment,
                                                 HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            oaArchiveBiz.tx_startArchiveApplyAudit(oaArchive, taskId, comment, userId);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("OaArchiveController.archiveApplyAudit", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }

        return json;
    }

    /**
     * 发送档案
     * @param archiveIds
     * @param taskId
     * @param userId 当前申请人id
     * @return
     */
    @RequestMapping("/archive/give")
    @ResponseBody
    public Map<String, Object> giveArchive(@RequestParam("archiveIds") String archiveIds,
                                           @RequestParam("taskId") String taskId,
                                           @RequestParam("userId") Long userId,
                                           HttpServletRequest request) {
        Map<String, Object> json = null;
        Long currUserId = SysUserUtils.getLoginSysUserId(request);
        try {
            oaArchiveBiz.tx_sendArchive(archiveIds,taskId, currUserId, userId);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch(Exception e) {
            logger.error("OaArchiveController.giveArchive");
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }
        return json;
    }
}
