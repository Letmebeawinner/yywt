package com.keyanzizheng.controller.approvebill;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.SysUserUtils;
import com.keyanzizheng.biz.approvebill.ApproveBillBiz;
import com.keyanzizheng.biz.common.SmsHessianService;
import com.keyanzizheng.biz.result.ResultBiz;
import com.keyanzizheng.biz.result.TaskChangeBiz;
import com.keyanzizheng.biz.subsection.SubSectionBiz;
import com.keyanzizheng.constant.ApprovalStatusConstants;
import com.keyanzizheng.constant.ResultTypeConstants;
import com.keyanzizheng.entity.approvebill.ApproveBill;
import com.keyanzizheng.entity.result.Result;
import com.keyanzizheng.entity.result.TaskChange;
import com.keyanzizheng.entity.subsection.SubSection;
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

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/ky")
public class ApproveBillController extends BaseController {

    /**
     * 通过审核
     */
    public static final int PASS = 2;
    /**
     * 未通过审核
     */
    public static final int NOT_PASS = 1;
    private static Logger logger = LoggerFactory.getLogger(ApproveBillController.class);
    @Autowired
    private ApproveBillBiz approveBillBiz;
    @Autowired
    private ResultBiz resultBiz;
    @Autowired
    private TaskChangeBiz taskChangeBiz;
    @Autowired
    private SmsHessianService smsHessianService;
    @Autowired
    private SubSectionBiz subSectionBiz;

    @InitBinder("approveBill")
    public void initApproveBill(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("approveBill.");
    }

    /**
     * 跳转审批添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddApproveBill")
    public ModelAndView toAddApproveBill(HttpServletRequest request,
                                         @ModelAttribute("approveBill") ApproveBill approveBill,
                                         @RequestParam("resultType") Integer resultType) {
        ModelAndView modelAndView = new ModelAndView("/approvebill/approvebill_add");
        try {
            Result result = resultBiz.findById(approveBill.getResultId());
            modelAndView.addObject("result", result);

            Long sysUserId=SysUserUtils.getLoginSysUserId(request);
            // 查询处室
            List<SubSection> subSectionList = subSectionBiz.querySubSectionListBySysUserId(sysUserId);
            modelAndView.addObject("subSectionList", subSectionList);
            modelAndView.addObject("resultType", resultType);
        } catch (Exception e) {
            logger.error("toAddApproveBill", e);
        }
        return modelAndView;
    }

    /**
     * 添加审批
     *
     * @param approveBill
     * @return
     */
    @RequestMapping("/addApproveBill")
    @ResponseBody
    public Map<String, Object> addApproveBill(HttpServletRequest request,@ModelAttribute ApproveBill approveBill,
                                              @RequestParam("resultType") Integer resultType) {
        Map<String, Object> objectMap = null;
        try {
            approveBill.setStatus(2);
            approveBillBiz.save(approveBill);
            Result result=resultBiz.findById(approveBill.getResultId());
            TaskChange taskChange=new TaskChange();
            taskChange.setTaskId(result.getId());
            if(approveBill.getIfPass()==1){
                result.setPassStatus(ApprovalStatusConstants.NOT_PASS_DEPT);
                taskChange.setOperate("课题未通过部门审批");
                String content = "你申报的课题" + result.getName() + "未通过部门审批！";
                smsHessianService.sendInfo(content, 0L, result.getSysUserId().toString(), 0);
            }else{
                result.setPassStatus(ApprovalStatusConstants.PASS_DEPT);
                taskChange.setOperate("课题通过部门审批");
            }
            taskChange.setStatus(1);
            taskChangeBiz.save(taskChange);
            Long userId = SysUserUtils.getLoginSysUserId(request);
            resultBiz.updatePassStatusRecard(result,userId);

            // ifPass 2:通过 1:未通过
            if (approveBill.getIfPass() == PASS) {
                objectMap = this.resultJson(ErrorCode.SUCCESS, "已通过审批", resultType);
            }
            if (approveBill.getIfPass() == NOT_PASS) {
                objectMap = this.resultJson(ErrorCode.SUCCESS, "已拒绝审批", resultType);
            }

        } catch (Exception e) {
            logger.error("addApproveBill", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }


    /**
     * 跳转科研/咨政处审批页面
     */
    @RequestMapping("officeApproval")
    public ModelAndView officeApproval(HttpServletRequest request,@ModelAttribute("approveBill") ApproveBill approveBill,
                                       @RequestParam("type") Integer typeId) {
        ModelAndView mv = new ModelAndView("/approvebill/office_approvebill");
        try {
            Result result = resultBiz.findById(approveBill.getResultId());
            mv.addObject("result", result);

            Long sysUserId=SysUserUtils.getLoginSysUserId(request);
            // 查询处室
            List<SubSection> subSectionList = subSectionBiz.querySubSectionListBySysUserId(sysUserId);
            mv.addObject("subSectionList", subSectionList);
            if (typeId == ResultTypeConstants.KE_YAN) {
                mv.addObject("resultTypeName", "科研处");
            }
            if (typeId == ResultTypeConstants.ZI_ZHENG) {
                mv.addObject("resultTypeName", "生态文明所");
            }
            mv.addObject("resultType", typeId);
        } catch (Exception e) {
            logger.error("ApproveBillController.officeApproval", e);
        }

        return mv;
    }

    @RequestMapping("/saveOfficeApproval")
    @ResponseBody
    public Map<String, Object> saveOfficeApproval(HttpServletRequest request,@ModelAttribute ApproveBill approveBill,
                                                  @RequestParam("resultType") Integer resultType) {
        Map<String, Object> objectMap = null;
        try {
            String resultName = null;
            if (resultType == 1) {
                resultName = "科研处";
            }
            if (resultType == 2) {
                resultName = "生态文明所";
            }

            approveBill.setStatus(2);
            approveBillBiz.save(approveBill);

            TaskChange taskChange = new TaskChange();
            Result result = resultBiz.findById(approveBill.getResultId());
            taskChange.setTaskId(result.getId());

            if (approveBill.getIfPass() == 1) {
                result.setPassStatus(ApprovalStatusConstants.NOT_PASS_OFFICE);
                taskChange.setOperate("课题未通过" + resultName + "审批");
                String content = "你申报的课题" + result.getName() + "未通过" + resultName + "审批！";
                smsHessianService.sendInfo(content, 0L, result.getSysUserId().toString(), 0);
            } else {
                result.setPassStatus(ApprovalStatusConstants.PASS_OFFICE);
                taskChange.setOperate("课题通过" + resultName + "审批");
            }
            taskChange.setStatus(1);
            taskChangeBiz.save(taskChange);
            Long userId = SysUserUtils.getLoginSysUserId(request);
            resultBiz.updatePassStatusRecard(result,userId);

            // ifPass 2:通过 1:未通过
            if (approveBill.getIfPass() == PASS) {
                objectMap = this.resultJson(ErrorCode.SUCCESS, "已通过审批", resultType);
            }
            if (approveBill.getIfPass() == NOT_PASS) {
                objectMap = this.resultJson(ErrorCode.SUCCESS, "已拒绝审批", resultType);
            }

        } catch (Exception e) {
            logger.error("addApproveBill", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

}
