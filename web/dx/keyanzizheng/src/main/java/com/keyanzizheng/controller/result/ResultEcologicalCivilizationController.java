package com.keyanzizheng.controller.result;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.keyanzizheng.biz.result.ResultBiz;
import com.keyanzizheng.biz.subsection.SubSectionBiz;
import com.keyanzizheng.constant.PaginationConstants;
import com.keyanzizheng.constant.ResultFormConstants;
import com.keyanzizheng.constant.ResultTypeConstants;
import com.keyanzizheng.entity.result.QueryResult;
import com.keyanzizheng.entity.result.Result;
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
 * 生态文明所的课题申报
 * 申报流程：<br>
 * ---各处室人员提交申报—->文明生态所确认是否立项-确认立项（填写结束时间）<br>
 * ---处室人员提交课题相关文件—生态文明所通过—结项评级 <br>
 *
 * @author YaoZhen
 * @date 12-04, 10:10, 2017.
 */
@Controller
@RequestMapping("/admin/zz/projectEstablishment")
public class ResultEcologicalCivilizationController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ResultEcologicalCivilizationController.class);
    /**
     * 当前线程的req
     */
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ResultBiz resultBiz;
    @Autowired
    private SubSectionBiz subSectionBiz;

    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder("result")
    public void initResult(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("result.");
    }

    @InitBinder("queryResult")
    public void initQueryResult(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("queryResult.");
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    /**
     * 课题列表
     *
     * @param pagination  分页
     * @param queryResult 课题
     * @return 待立项的列表/已立项文件未审批的课题列表
     */
    @RequestMapping("list")
    public ModelAndView projectEstablishmentList(@ModelAttribute("pagination") Pagination pagination,
                                                 @ModelAttribute("queryResult") QueryResult queryResult) {
        ModelAndView mv = new ModelAndView("projectestablishment/result_zz_list");
        try {

            pagination.setRequest(request);
            pagination.setPageSize(PaginationConstants.PAGE_SIZE);
            queryResult.setResultType(ResultTypeConstants.ZI_ZHENG);
            queryResult.setResultForm(ResultFormConstants.QUESTION);
            List<QueryResult> resultList = resultBiz.getResultList(pagination, queryResult);
            mv.addObject("resultList", resultList);
        } catch (Exception e) {
            logger.error("ResultEcologicalCivilizationController.projectEstablishmentList", e);
            mv.setViewName(super.setErrorPath(request, e));
        }
        return mv;
    }


    /**
     * 跳转到立项审批页面
     *
     * @param resultId 成果id
     * @return 成果详情
     */
    @RequestMapping("approvalProject")
    public ModelAndView projectEstablishmentApprovalProject(@RequestParam("resultId") Long resultId) {
        ModelAndView mv = new ModelAndView("projectestablishment/result_zz_approval");
        try {
            QueryResult result = resultBiz.getResultById(resultId);
            mv.addObject("result", result);

            Long sysUserId=SysUserUtils.getLoginSysUserId(request);
            // 查询处室
            List<SubSection> subSectionList = subSectionBiz.querySubSectionListBySysUserId(sysUserId);
            mv.addObject("subSectionList", subSectionList);
        } catch (Exception e) {
            logger.error("ResultEcologicalCivilizationController.projectEstablishmentApprovalProject", e);
        }
        return mv;
    }

    /**
     * 跳转到立项审批页面
     *
     * @param resultId 成果id
     * @return 成果详情
     */
    @RequestMapping("knotProject")
    public ModelAndView projectEstablishmentKnotProject(@RequestParam("resultId") Long resultId) {
        ModelAndView mv = new ModelAndView("projectestablishment/result_zz_knot");
        try {
            QueryResult result = resultBiz.getResultById(resultId);
            mv.addObject("result", result);

            Long sysUserId=SysUserUtils.getLoginSysUserId(request);
            // 查询处室
            List<SubSection> subSectionList = subSectionBiz.querySubSectionListBySysUserId(sysUserId);
            mv.addObject("subSectionList", subSectionList);
        } catch (Exception e) {
            logger.error("ResultEcologicalCivilizationController.projectEstablishmentApprovalProject", e);
        }
        return mv;
    }


    /**
     * 修改成果状态 同意/拒绝立项
     *
     * @param result 课题 包含id和passStatus
     * @return map
     */
    @RequestMapping("update")
    @ResponseBody
    public Map<String, Object> projectEstablishmentUpdate(@ModelAttribute("result") Result result) {
        Map<String, Object> objectMap;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            resultBiz.updatePassStatusRecard(result,userId);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", result);
        } catch (Exception e) {
            logger.error("ResultEcologicalCivilizationController.projectEstablishmentUpdate", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }
}
