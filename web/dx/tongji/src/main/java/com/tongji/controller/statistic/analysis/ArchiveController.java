package com.tongji.controller.statistic.analysis;

import com.a_268.base.controller.BaseController;
import com.a_268.base.util.ObjectUtils;
import com.tongji.biz.common.DangAnHessianService;
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
 * 档案统计分析Controller
 *
 * @author sk
 * @since 2017-02-28
 */
@Controller
@RequestMapping("/admin/statistic/analysis/archives")
public class ArchiveController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ArchiveController.class);

    @Autowired
    private DangAnHessianService dangAnHessianService;

    /**
     * 查询档案统计
     *
     * @param request {@link HttpServletRequest}
     * @return 档案统计页面
     */
    @RequestMapping("/listArchiveStatistic")
    @SuppressWarnings("unchecked")
    public ModelAndView listArchiveStatistic(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/statistic/analysis/archive/archiveStatistic-list");
        try {
            Map<String, Object> map = dangAnHessianService.hessianArchivesStatistics();
            if (ObjectUtils.isNotNull(map)) {
                //各类档案数量
                Map<String, Object> statisticMap = (Map<String, Object>) map.get("statisticMap");
                mv.addObject("statisticMap", statisticMap);
                //各类档案总数
                mv.addObject("total", map.get("total"));
            }
        } catch (Exception e) {
            logger.error("listArchiveStatistic()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 查询档案借阅统计
     *
     * @param request {@link HttpServletRequest}
     * @return 档案借阅统计页面
     */
    @RequestMapping("/listArchiveBorrowStatistic")
    @SuppressWarnings("unchecked")
    public ModelAndView listArchiveBorrowStatistic(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/statistic/analysis/archive/archiveBorrowStatistic-list");
        try {
            Map<String, Object> map = dangAnHessianService.hessianBorrowApplyStatistics();
            if (ObjectUtils.isNotNull(map)) {
                //各类档案借阅总次数
                mv.addObject("total", map.get("total"));
                //各类档案借阅次数
                List<Map<String, Object>> borrowList = (List<Map<String, Object>>) map.get("borrowList");
                mv.addObject("borrowList", borrowList);
            }
        } catch (Exception e) {
            logger.error("listArchiveBorrowStatistic()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }
}
