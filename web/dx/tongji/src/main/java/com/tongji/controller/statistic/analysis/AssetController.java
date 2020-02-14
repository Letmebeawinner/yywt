package com.tongji.controller.statistic.analysis;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.tongji.biz.annual.AnnualBudgetBiz;
import com.tongji.biz.common.HqHessianService;
import com.tongji.biz.common.HrHessianService;
import com.tongji.entity.annual.AnnualBudget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 资产统计分析Controller
 *
 * @author sk
 * @since 2017-02-28
 */
@Controller
@RequestMapping("/admin/statistic/analysis/asset")
public class AssetController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AssetController.class);

    @Autowired
    private AnnualBudgetBiz annualBudgetBiz;
    @Autowired
    private HqHessianService hqHessianService;
    @Autowired
    private HrHessianService hrHessianService;

    /**
     * 查询年度预算详情
     *
     * @param request    {@link HttpServletRequest}
     * @param pagination 分页
     * @return 年度预算详情页面
     */
    @RequestMapping("/listAnnualBudget")
    public ModelAndView listAnnualBudget(HttpServletRequest request,
                                         @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/statistic/analysis/asset/annualBudget-list");
        try {
            List<AnnualBudget> budgetList = annualBudgetBiz.listAnnualBudget(pagination);
            pagination.setRequest(request);
            mv.addObject("budgetList", budgetList);
            mv.addObject("pagination", pagination);
        } catch (Exception e) {
            logger.error("listAnnualBudget()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 重新审计年度结余
     *
     * @param request {@link HttpServletRequest}
     * @return {@link Map} json
     * @since 2017-03-06
     */
    @RequestMapping("/redoneBalance")
    @ResponseBody
    public Map<String, Object> redoneBalance(HttpServletRequest request,
                                             @RequestParam(value = "year") String year) {
        try {
            if (StringUtils.isTrimEmpty(year))
                return this.resultJson(ErrorCode.ERROR_PARAMETER, "重新审计结余时年份不能为空", null);
            annualBudgetBiz.redoneBalance(year);
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("redoneBalance()--error", e);
            return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }
}
