package com.tongji.controller.statistic.analysis;

import com.a_268.base.controller.BaseController;
import com.a_268.base.util.ObjectUtils;
import com.tongji.biz.common.GanBuHessianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 干部统计分析Controller
 *
 * @author sk
 * @since 2017-02-28
 */
@Controller
@RequestMapping("/admin/statistic/analysis/cadre")
public class CadreController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CadreController.class);

    @Autowired
    private GanBuHessianService ganBuHessianService;

    /**
     * 查询各部门人数统计
     *
     * @param request {@link HttpServletRequest}
     * @return 各部门人数统计
     * @since 2017-03-01
     */
    @RequestMapping("/listCadreStatistic")
    @SuppressWarnings("unchecked")
    public ModelAndView listCadreStatistic(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/statistic/analysis/cadre/cadreStatistic-list");
        try {
            Map<String, Object> data = ganBuHessianService.hessianCadreStatistics();
            if (ObjectUtils.isNotNull(data)) {
                List<Map<String, Object>> statisticList = (List<Map<String, Object>>) data.get("statisticList");
                mv.addObject("statisticList", statisticList);
            }
        } catch (Exception e) {
            logger.error("listCadreStatistic()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 查询干部培训统计
     *
     * @param request {@link HttpServletRequest}
     * @since 2017-03-01
     */
    @RequestMapping("/listCadreTrainStatistic")
    @SuppressWarnings("unchecked")
    public ModelAndView listCadreTrainStatistic(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/statistic/analysis/cadre/cadreTrainStatistic-list");
        try {
            Map<String, Object> data = ganBuHessianService.hessianTrainStatistics();
            if (ObjectUtils.isNotNull(data)) {
                List<Map<String, Object>> trainStatisticList = (List<Map<String, Object>>)data.get("trainStatisticList");
                mv.addObject("trainStatisticList", trainStatisticList);
            }
        } catch (Exception e) {
            logger.error("listCadreTrainStatistic()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }
}
