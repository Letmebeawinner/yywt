package com.keyanzizheng.controller.statistics;

import com.a_268.base.controller.BaseController;
import com.keyanzizheng.biz.result.ResultBiz;
import com.keyanzizheng.common.StudentHessianService;
import com.keyanzizheng.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 生态文明所统计
 * <p><p>
 *
 * @author YaoZhen
 * @date 12-18, 09:32, 2017.
 */
@Controller
@RequestMapping("/admin/ky")
public class EcologicalCivilizationStatisticsController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(EcologicalCivilizationStatisticsController.class);

    @Autowired private ResultBiz resultBiz;
    @Autowired private StudentHessianService studentHessianService;
    @Autowired private HttpServletRequest request;

    /**
     * 统计生态文明所的成果
     *
     * @return 柱状图, 饼状图, 列表
     */
    @RequestMapping("ecologicalCivilizationResultsStatistics")
    public ModelAndView ecologicalCivilizationResultsStatistics(
            @RequestParam(value = "year", required = false) String year) {
        ModelAndView mv = new ModelAndView(
                "/result/resultStatistics/result_zz_every_year_statistics");
        try {
            // 按年份查询
            String currentYear = DateUtil.getSysYear();
            // 未选择年份时 搜索当前年
            if (year == null) {
                year = currentYear;
            }
            mv.addObject("year", year);
            mv.addObject("currentYear", currentYear);

            // 计算课题数量 已归档: and ifFile = 2
            int resultCount = resultBiz.count(
                    "resultForm = 3 and resultType = 2 and year(createTime) = " + year);
            mv.addObject("resultCount", resultCount);
            // 计算调研报告数量
            int researchReport = studentHessianService.countResearchReport(year);
            mv.addObject("researchReport", researchReport);
        } catch (Exception e) {
            logger.error("ResultStatisticsController.ecologicalCivilizationResultsStatistics", e);
            mv.setViewName(super.setErrorPath(request, e));
        }
        return mv;
    }

}
