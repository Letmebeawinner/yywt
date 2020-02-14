package com.keyanzizheng.controller.investigate;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.keyanzizheng.biz.employee.EmployeeBiz;
import com.keyanzizheng.biz.investigate.InvestigateReportBiz;
import com.keyanzizheng.biz.investigate.ResearchDirectionBiz;
import com.keyanzizheng.entity.employee.Employee;
import com.keyanzizheng.entity.investigate.InvestigateReport;
import com.keyanzizheng.entity.investigate.QueryInvestigateReport;
import com.keyanzizheng.entity.investigate.ResearchDirection;
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
public class InvestigateReportController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(InvestigateReportController.class);

    @InitBinder("investigateReport")
    public void initInvestigateReport(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("investigateReport.");
    }
    @InitBinder("queryInvestigateReport")
    public void initQueryInvestigateReport(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("queryInvestigateReport.");
    }

    @Autowired
    private InvestigateReportBiz investigateReportBiz;
    @Autowired
    private ResearchDirectionBiz researchDirectionBiz;
    @Autowired
    private EmployeeBiz employeeBiz;
    /**
     * 跳转调研报告添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddInvestigateReport")
    public ModelAndView toAddInvestigateReport(HttpServletRequest request,@ModelAttribute("investigateReport") InvestigateReport investigateReport) {
        ModelAndView modelAndView = new ModelAndView("/investigate/investigateReport_add");
        try {
        } catch (Exception e) {
            logger.error("toAddInvestigateReport", e);
        }
        return modelAndView;
    }

    /**
     * 添加调研报告
     *
     * @param request
     * @param investigateReport
     * @return
     */
    @RequestMapping("/addInvestigateReport")
    @ResponseBody
    public Map<String, Object> addInvestigateReport(HttpServletRequest request, @ModelAttribute("investigateReport") InvestigateReport investigateReport) {
        Map<String, Object> objectMap = null;
        try {
            investigateReport.setEmployeeId(SysUserUtils.getLoginSysUserId(request));
            investigateReport.setStatus(1);
            investigateReport.setIfPass(1);
            investigateReportBiz.save(investigateReport);
            ResearchDirection researchDirection=researchDirectionBiz.findById(investigateReport.getResearchId());
            researchDirection.setIfReport(2);
            researchDirectionBiz.update(researchDirection);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", investigateReport);
        } catch (Exception e) {
            logger.error("addInvestigateReport", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 删除调研报告
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteInvestigateReport")
    @ResponseBody
    public Map<String, Object> addInvestigateReport(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        Map<String, Object> objectMap = null;
        try {
            InvestigateReport investigateReport = investigateReportBiz.findById(id);
            investigateReport.setStatus(2);
            investigateReportBiz.update(investigateReport);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", investigateReport);
        } catch (Exception e) {
            logger.error("addInvestigateReport", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 调研报告详情
     *
     * @param request
     * @return
     */
    @RequestMapping("/getInvestigateReportInfo")
    public ModelAndView getInvestigateReportById(HttpServletRequest request,@ModelAttribute("queryInvestigateReport") QueryInvestigateReport queryInvestigateReport) {
        ModelAndView modelAndView = new ModelAndView("/investigate/investigateReport_info");
        try {
            List<QueryInvestigateReport> investigateReportList=investigateReportBiz.getInvestigateReportList(null,queryInvestigateReport);
            if(!CollectionUtils.isEmpty(investigateReportList)){
                QueryInvestigateReport investigateReport = investigateReportList.get(0);
                modelAndView.addObject("investigateReport", investigateReport);
            }
        } catch (Exception e) {
            logger.error("getInvestigateReportInfo", e);
        }
        return modelAndView;
    }

    /**
     * 去修改页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdateInvestigateReport")
    public ModelAndView toUpdateInvestigateReport(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/investigate/investigateReport_update");
        try {
            QueryInvestigateReport investigateReport = investigateReportBiz.getInvestigateReportById(id);
            modelAndView.addObject("investigateReport", investigateReport);
        } catch (Exception e) {
            logger.error("toUpdateInvestigateReport", e);
        }
        return modelAndView;
    }

    /**
     * 修改调研报告
     *
     * @param request
     * @param investigateReport
     * @return
     */
    @RequestMapping("/updateInvestigateReport")
    @ResponseBody
    public Map<String, Object> updateInvestigateReport(HttpServletRequest request, @ModelAttribute("investigateReport") InvestigateReport investigateReport) {
        Map<String, Object> objectMap = null;
        try {
            investigateReportBiz.update(investigateReport);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", investigateReport);
        } catch (Exception e) {
            logger.error("updateInvestigateReport", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", investigateReport);
        }
        return objectMap;
    }

    /**
     * 调研报告列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getInvestigateReportList")
    public ModelAndView getInvestigateReportList(HttpServletRequest request,
                                      @ModelAttribute("pagination") Pagination pagination,
                                      @ModelAttribute("queryInvestigateReport") QueryInvestigateReport queryInvestigateReport) {
        ModelAndView modelAndView = new ModelAndView("/investigate/investigateReport_list");
        try {
            pagination.setRequest(request);
            List<QueryInvestigateReport> investigateReportList = investigateReportBiz.getInvestigateReportList(pagination, queryInvestigateReport);
            if(!CollectionUtils.isEmpty(investigateReportList)){
                modelAndView.addObject("investigateReportList", investigateReportList);
            }
            modelAndView.addObject("pagination", pagination);
            modelAndView.addObject("investigateReport", queryInvestigateReport);
        } catch (Exception e) {
            logger.error("getInvestigateReportList", e);
        }
        return modelAndView;
    }
    /**
     * 调研报告列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/toApproveInvestigateReport")
    public ModelAndView toApproveInvestigateReport(HttpServletRequest request,
                                                 @ModelAttribute("pagination") Pagination pagination,
                                                 @ModelAttribute("queryInvestigateReport") QueryInvestigateReport queryInvestigateReport) {
        ModelAndView modelAndView = new ModelAndView("/investigate/investigateReport_approve_list");
        try {
            queryInvestigateReport.setIfPass(1);
            pagination.setRequest(request);
            List<QueryInvestigateReport> investigateReportList = investigateReportBiz.getInvestigateReportList(pagination, queryInvestigateReport);
            if(!CollectionUtils.isEmpty(investigateReportList)){
                modelAndView.addObject("investigateReportList", investigateReportList);
            }
        } catch (Exception e) {
            logger.error("getInvestigateReportList", e);
        }
        return modelAndView;
    }
}
