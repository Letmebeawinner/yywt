package com.oa.controller.activiti;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.workflow.OaLetterBiz;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.entity.workflow.OaLetter;
import com.oa.entity.workflow.OaLetterDto;
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
 * Oa公文控制层
 *
 * @author lzh
 * @create 2017-03-16-11:47
 */
@Controller
@RequestMapping("/admin/oa")
public class OaLetterController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OaLetterController.class);

    @Autowired
    private OaLetterBiz oaLetterBiz;
    @Autowired
    private WorkflowFormBiz workflowFormBiz;
    private final String oaLetterList = "/letter/oa_letter_list";

    @InitBinder("oaLetter")
    public void initLetter(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("oaLetter.");
    }


    /**
     * @Description: 启动流程，将数据存入业务表，同时关联起来
     * @author: lzh
     * @Param: [request, letter, processDefinitionId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 20:35
     */
    @RequestMapping("/inner/letter/process/start")
    @ResponseBody
    public Map<String, Object> startInnerLetterProcess(HttpServletRequest request,
                                            @ModelAttribute("oaLetter") OaLetter letter,
                                            @RequestParam("processDefinitionId") String processDefinitionId,
                                            @RequestParam(value = "userIds", required = false) String userIds) {
        Map<String, Object> json = null;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            if (ObjectUtils.isNotNull(redisCache.get("internal_" + letter.getTimeStamp()))) {
                Map<String, Object> map = (Map) (redisCache.get("internal_" + letter.getTimeStamp()));
                letter.setFileUrl(map.get("fileUrl").toString());
                letter.setFileName(map.get("fileName").toString());
                redisCache.remove("internal_" + letter.getTimeStamp());
            }
            String processInstanceId = oaLetterBiz.tx_startLetterProcess(letter, processDefinitionId, userId, userIds);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, processInstanceId);
        } catch (Exception e) {
            logger.error("OaLetterController.startProcess", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description: 内部公文申請開始
     * @author: lzh
     * @Param: [request, letter, processDefinitionId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 20:35
     */
    @RequestMapping("/letter/process/start")
    @ResponseBody
    public Map<String, Object> startProcess(HttpServletRequest request,
                                            @ModelAttribute("oaLetter") OaLetter letter
                                            ,@RequestParam("processDefinitionId") String processDefinitionId) {
        Map<String, Object> json = null;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            String processInstanceId = oaLetterBiz.tx_startLetterProcess(letter, processDefinitionId, userId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, processInstanceId);
        } catch (Exception e) {
            logger.error("OaLetterController.startProcess", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    @RequestMapping("/letter/complete")
    @ResponseBody
    public Map<String, Object> completeTask(@RequestParam("taskId") String taskId,
                                            @RequestParam("flag") int flag,
                                            @RequestParam("processInstanceId") String processInstanceId,
                                            @RequestParam("comment") String comment,
                                            @ModelAttribute("oaLetter") OaLetter oaLetter,
                                            HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            workflowFormBiz.tx_startAudit(taskId, processInstanceId, comment, flag, userId);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("OaLetterController.completeTask", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @Description: 公文审核
     * @author: lzh
     * @Param: [taskId, carApplyOpiont, refuseReason]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:31
     */
    @RequestMapping("inner/letterApplyAudit")
    @ResponseBody
    public Map<String, Object> innerLetterApplyAudit(@RequestParam("taskId") String taskId,
                                                @ModelAttribute("oaLetter") OaLetter oaLetter,
                                                @RequestParam(value = "comment", required = false) String comment,
                                                @RequestParam(value = "userIds", required = false) String userIds,
                                                HttpServletRequest request) {
        Long userId = SysUserUtils.getLoginSysUserId(request);
        Map<String, Object> json = null;
        try {
            oaLetterBiz.tx_stsrtInnerLetterApplyAudit(oaLetter, taskId, comment, userId, userIds);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("OaLetterController.letterApplyAudit", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }

        return json;
    }

    /**
     * @Description: 公文审核
     * @author: lzh
     * @Param: [taskId, carApplyOpiont, refuseReason]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:31
     */
    @RequestMapping("/letterApplyAudit")
    @ResponseBody
    public Map<String, Object> letterApplyAudit(@RequestParam("taskId") String taskId,
                                                @ModelAttribute("oaLetter") OaLetter oaLetter,
                                                @RequestParam(value = "comment", required = false) String comment,
                                                @RequestParam(value = "approvalId", required = false) Long approvalId,
                                                HttpServletRequest request) {
        Long userId = SysUserUtils.getLoginSysUserId(request);
        Map<String, Object> json = null;
        try {
            oaLetterBiz.tx_startLetterApplyAudit(oaLetter, taskId, comment, userId,approvalId);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch (Exception e) {
            logger.error("OaLetterController.letterApplyAudit", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }

        return json;
    }

    /**
     * 发布公文
     * @param taskId
     * @param userIds
     * @param letterId
     * @param request
     * @return
     */
    @RequestMapping("/letter/publish")
    @ResponseBody
    public Map<String, Object> letterPublish(@RequestParam("taskId") String taskId,
                                             @RequestParam("userIds") String userIds,
                                             @RequestParam("letterId") Long letterId,
                                             HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            oaLetterBiz.saveLetterPublish(taskId, userIds, letterId, userId);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch(Exception e) {
            logger.error("OaLetterController.letterPublish", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }
        return json;
    }

    /**
     * 获取公文审理列表
     * @param oaLetter
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/letter/list")
    public String letterList(@ModelAttribute("oaLetter") OaLetter oaLetter,
                             HttpServletRequest request,
                             @ModelAttribute("pagination") Pagination pagination,
                             @RequestParam(value = "applyName", required = false) String applyName) {
        try {
            oaLetter.setAudit(1);
            List<OaLetterDto> oaLetters = oaLetterBiz.getOaLetterDtoList(pagination, oaLetter, applyName);
            request.setAttribute("oaLetters", oaLetters);
            request.setAttribute("applyName", applyName);
            request.setAttribute("oaLetter", oaLetter);
        } catch (Exception e) {
            logger.error("OaLetterController.letterList", e);
            return this.setErrorPath(request, e);
        }
        return oaLetterList;
    }

}
