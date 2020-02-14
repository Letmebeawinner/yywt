package com.tongji.controller.statistic.analysis;

import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.tongji.biz.common.KyHessianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 科研咨政统计分析Controller
 *
 * @author sk
 * @since 2017-02-28
 */
@Controller
@RequestMapping("/admin/statistic/analysis/sra")
public class SRAController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SRAController.class);

    @Autowired
    private KyHessianService kyHessianService;

    /**
     * 查询科研
     *
     * @param request {@link HttpServletRequest}
     * @return 科研统计页面
     */
    @RequestMapping("/listSciResearch")
    @SuppressWarnings("unchecked")
    public ModelAndView listSciResearch(HttpServletRequest request,
                                        @ModelAttribute("pagination") Pagination pagination,
                                        @RequestParam(value = "type", required = false) Integer type,
                                        @RequestParam(value = "startYear", required = false) Integer startYear,
                                        @RequestParam(value = "startMonth", required = false) Integer startMonth) {
        ModelAndView mv = new ModelAndView("statistic/analysis/sra/sciResearch-list");
        try {
            if (ObjectUtils.isNull(startYear)) startYear = LocalDate.now().getYear();
            Map<String, Object> map = kyHessianService.resultStatistics(type, pagination, startYear, startMonth);
            if (ObjectUtils.isNotNull(map)) {
                //分页
                Object obj = map.get("pagination");
                pagination = gson.fromJson(gson.toJson(obj), Pagination.class);
                pagination.setRequest(request);
                //科研列表
                List<Map<String, String>> resultStatisticList = (List<Map<String, String>>) map.get("resultStatisticsList");
                mv.addObject("pagination", pagination);
                mv.addObject("resultStatisticList", resultStatisticList);
                mv.addObject("type", type);
                mv.addObject("startYear", startYear);
                mv.addObject("startMonth", startMonth);
            }
        } catch (Exception e) {
            logger.error("listSciResearch()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }
}
