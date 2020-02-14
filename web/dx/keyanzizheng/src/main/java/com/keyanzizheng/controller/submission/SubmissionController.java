package com.keyanzizheng.controller.submission;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.keyanzizheng.biz.submission.SubmissionBiz;
import com.keyanzizheng.constant.PaginationConstants;
import com.keyanzizheng.constant.StatusConstants;
import com.keyanzizheng.entity.submission.Submission;
import com.keyanzizheng.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 生态所投稿
 *
 * @author YaoZhen
 * @date 01-08, 11:42, 2018.
 */
@Controller
@RequestMapping("/admin/ky")
public class SubmissionController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SubmissionController.class);
    /**
     * 当前线程的req
     */
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SubmissionBiz submissionBiz;

    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder("submission")
    public void submissionInit(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("submission.");
    }

    /**
     * 填写表单
     */
    @RequestMapping(value = "saveSubmission")
    public String saveSubmission() {
        return "submission/save_submission";
    }

    /**
     * 添加投稿
     */
    @RequestMapping(value = "doSaveSubmission")
    @ResponseBody
    public Map<String, Object> doSaveSubmission(@ModelAttribute("submission") Submission submission) {
        Map<String, Object> objMap;
        try {

            Map<String, String> sysUser = SysUserUtils.getLoginSysUser(request);
            submission.setApplicantId(Long.parseLong(sysUser.get("id")));
            submission.setApplicantName(sysUser.get("userName"));

            submissionBiz.save(submission);
            objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("SubmissionController.doSaveSubmission", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    /**
     * 列表
     *
     * @param pagination 分页
     * @param submission 生态所投稿
     * @return 列表
     */
    @RequestMapping("listSubmission")
    public ModelAndView listSubmission(
            @ModelAttribute("pagination") Pagination pagination,
            @ModelAttribute("submission") Submission submission) {
        ModelAndView mv = new ModelAndView("submission/list_submission");
        try {
            pagination.setRequest(request);
            pagination.setPageSize(PaginationConstants.PAGE_SIZE);
            submission.setStatus(StatusConstants.NEGATE);
            List<Submission> submissionList = submissionBiz.find(pagination,
                    GenerateSqlUtil.getSql(submission));
            mv.addObject("submissionList", submissionList);
        } catch (Exception e) {
            logger.error("SubmissionController.listSubmission", e);
            mv.setViewName(super.setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 投稿详情
     *
     * @param id 稿件id
     * @return 详情
     */
    @RequestMapping("detailSubmission")
    public ModelAndView detailSubmission(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("submission/detail_submission");
        try {
            Submission submission = submissionBiz.findById(id);
            mv.addObject("submission", submission);
        } catch (Exception e) {
            logger.error("SubmissionController.detailSubmission", e);
        }
        return mv;
    }

    /**
     * 删除投稿
     *
     * @param id 投稿id
     * @return map
     */
    @RequestMapping("/removeSubmission")
    @ResponseBody
    public Map<String, Object> removeSubmission(@RequestParam("id") Long id) {
        Map<String, Object> objMap;
        try {
            Submission submission = submissionBiz.findById(id);
            if (submission != null) {
                submission.setStatus(StatusConstants.DONE);
                submissionBiz.update(submission);
            }
            objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("SubmissionController.removeSubmission", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    /**
     * 审批投稿
     */
    @RequestMapping("auditSubmission")
    public ModelAndView auditSubmission(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("submission/audit_submission");
        try {
            Submission submission = submissionBiz.findById(id);
            mv.addObject("submission", submission);
        } catch (Exception e) {
            logger.error("SubmissionController.auditSubmission", e);
        }
        return mv;
    }

    /**
     * 审批投稿
     *
     * @param id    投稿id
     * @param audit 审批状态
     * @return json
     */
    @RequestMapping("/doAuditSubmission")
    @ResponseBody
    public Map<String, Object> doAuditSubmission(
            @RequestParam("id") Long id,
            @RequestParam("audit") Integer audit) {
        Map<String, Object> objMap;
        try {
            Submission submission = submissionBiz.findById(id);
            if (submission != null) {
                submission.setAudit(audit);
                submissionBiz.update(submission);
            }
            objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("SubmissionController.doAuditSubmission", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }


}
