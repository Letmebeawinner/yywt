package com.keyanzizheng.controller.ecology;

import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.keyanzizheng.biz.reportfiled.ReportFiledBiz;
import com.keyanzizheng.common.StudentHessianService;
import com.keyanzizheng.constant.PaginationConstants;
import com.keyanzizheng.entity.reportfiled.ReportFiled;
import com.keyanzizheng.entity.research.ResearchReport;
import com.keyanzizheng.utils.GenerateSqlUtil;
import com.keyanzizheng.utils.HessianUtil;
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

import static com.a_268.base.constants.ErrorCode.*;
import static com.keyanzizheng.constant.StatusConstants.DONE;

/**
 * 生态调研报告归档
 *
 * @author YaoZhen
 * @date 01-09, 14:26, 2018.
 */
@Controller
@RequestMapping("admin/ky")
public class EcologyReportController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(EcologyReportController.class);
    /**
     * 当前线程的req
     */
    private final HttpServletRequest request;
    private final StudentHessianService studentHessianService;
    private final ReportFiledBiz reportFiledBiz;

    @Autowired
    public EcologyReportController(HttpServletRequest request, StudentHessianService studentHessianService, ReportFiledBiz reportFiledBiz) {
        this.request = request;
        this.studentHessianService = studentHessianService;
        this.reportFiledBiz = reportFiledBiz;
    }


    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder("reportFiled")
    public void researchReportInit(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("reportFiled.");
    }

    /**
     * 填写生态所的调研报告归档表单
     *
     * @param id 报告ID
     * @return 调研报告
     */
    @RequestMapping("zz/researchReportFiled")
    public ModelAndView researchReportFiled(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("researchreport/filed_researchReport_zz");
        try {
            byte[] rr = studentHessianService.findResearchReportById(HessianUtil.serialize(id));
            if (rr != null) {
                Object o = HessianUtil.deserialize(rr);
                mv.addObject("researchReport", o);
            }
        } catch (Exception e) {
            logger.error("ResearchReportController.researchReportFiled", e);
        }
        return mv;
    }

    /**
     * 生态所调研报告归档
     * 调研报告添加在jiaowu 归档时保存到科研
     *
     * @return 报告
     */
    @RequestMapping("zz/doReportFiled")
    @ResponseBody
    public Map<String, Object> doReportFiled(@ModelAttribute("reportFiled") ReportFiled reportFiled,
                                             @RequestParam("reportId") Long reportId) {
        Map<String, Object> objMap;
        try {
            byte[] by = studentHessianService.findResearchReportById(
                    HessianUtil.serialize(reportId));
            if (by != null) {
                String reportJson = gson.toJson(HessianUtil.deserialize(by));
                ResearchReport researchReport = gson.fromJson(reportJson, ResearchReport.class);

                if (researchReport.getArchive() == DONE) {
                    return this.resultJson(ERROR_DATA, "此成果已经归档", null);
                }

                // 归档
                String code = reportFiledBiz.filed(researchReport, reportFiled);

                if (ERROR_DATA.equals(code)) {
                    return this.resultJson(ERROR_DATA, "未做出任何修改", null);
                }
            }
            objMap = this.resultJson(SUCCESS, SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("EcologyReportController.doReportFiled", e);
            objMap = this.resultJson(ERROR_SYSTEM, SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    /**
     * 调研报告档案
     * 生态所的调研报告归档在keyanzizheng
     * 科研处的调研报告归档在oa
     * @param reportFiled  继承了原调研报告类
     * @see GenerateSqlUtil 反射不到父类私有属性
     * @return 档案列表
     *
     */
    @RequestMapping("zz/reportFile")
    public ModelAndView reportFile(@ModelAttribute("pagination") Pagination pagination,
                                   @ModelAttribute("reportFiled") ReportFiled reportFiled) {
        ModelAndView mv = new ModelAndView("researchreport/filed_research_report_list_zz");
        try {
            String whereSql = GenerateSqlUtil.getSql(reportFiled);
            pagination.setPageSize(PaginationConstants.PAGE_SIZE);
            pagination.setRequest(request);
            List<ReportFiled> reportFiledList = reportFiledBiz.find(pagination, whereSql + " order by id desc");
            mv.addObject("reportFiledList", reportFiledList);
        } catch (Exception e) {
            logger.error("EcologyReportController.reportFile", e);
            mv.setViewName(super.setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 查看档案
     *
     * @param fileId 档案id
     * @return 调研报告详情
     */
    @RequestMapping("zz/detailFile")
    public ModelAndView detailFile(@RequestParam("id") Long fileId) {
        ModelAndView mv = new ModelAndView("researchreport/query_researchReport");
        try {
            ReportFiled reportFiled = reportFiledBiz.findById(fileId);
            mv.addObject("researchReport", reportFiled);
        } catch (Exception e) {
            logger.error("EcologyReportController.detailFile", e);
        }
        return mv;
    }


}
