package com.oa.controller.seal;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.seal.OaSealBiz;
import com.oa.entity.seal.OaSeal;
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
 * oacontroller
 *
 * @author lzh
 * @create 2017-10-27 11:05
 */
@Controller
@RequestMapping("/admin/oa")
public class OaSealApplyController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(OaSealApplyController.class);

    @Autowired
    private OaSealBiz oaSealBiz;

    @InitBinder("sealApply")
    public void initBinderCar(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("sealApply.");
    }

    /**
     * @Description:开启印章申请工作流
     * @author: lzh
     * @Param: [request, carApply]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 14:14
     */
    @RequestMapping("/startSealApply")
    @ResponseBody
    public Map<String, Object> startSealApplyWorkFlow(HttpServletRequest request,
                                                     @ModelAttribute("sealApply") OaSeal sealApply,
                                                     @RequestParam("processDefinitionId") String processDefinitionId) {
        Map<String,Object> resultMap = null;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            String processInstanceId = oaSealBiz.tx_startSealApplyProcess(sealApply, processDefinitionId, userId);
            logger.info("印章申请流程已启动 id = {}",processInstanceId);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("OaCarApplyController.startCarApplyWorkFlow", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description: 用印审核
     * @author: lzh
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:31
     */
    @RequestMapping("/sealApplyDeptAudit")
    @ResponseBody
    public Map<String, Object> sealApplyAudit(@RequestParam("taskId") String taskId,
                                             @ModelAttribute("sealApply") OaSeal sealApply,
                                             @RequestParam(value = "comment", required = false) String comment,
                                              @RequestParam(value = "approvalId", required = false) Long approvalId,
                                             HttpServletRequest request) {
        Map<String, Object> json = null;
        Long userId = SysUserUtils.getLoginSysUserId(request);
        try {
            oaSealBiz.tx_startSealApplyAudit(sealApply, taskId, comment, userId, approvalId);
            json = this.resultJson(ErrorCode.SUCCESS, "操作成功", null);
        } catch(Exception e) {
            logger.error("OaCarApplyController.carApplyAudit", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍候再试", null);
        }

        return json;
    }
}
