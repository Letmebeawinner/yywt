package com.keyanzizheng.controller.result;

import com.a_268.base.controller.BaseController;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.DateUtils;
import com.keyanzizheng.biz.result.ResultBiz;
import com.keyanzizheng.biz.result.ResultStatisticsBiz;
import com.keyanzizheng.common.StudentHessianService;
import com.keyanzizheng.entity.result.ResultStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 268 on 2017/2/27.
 */
@Controller
@RequestMapping("/admin/ky")
public class ResultStatisticsController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ResultStatisticsController.class);

    @Autowired
    private ResultStatisticsBiz resultStatisticsBiz;
    @Autowired
    private ResultBiz resultBiz;
    @Autowired
    private StudentHessianService studentHessianService;

    /**
     * 跳转成果统计页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/resultStatistics")
    public ModelAndView resultStatistics(HttpServletRequest request, @RequestParam("type") Integer type) {
        ModelAndView modelAndView = new ModelAndView("result/resultStatistics/result_statistics");
        try {
            String year = DateUtils.format(new Date(), "yyyy");
            modelAndView.addObject("year", year);
            modelAndView.addObject("type", type);
        } catch (Exception e) {
            logger.error("resultStatistics", e);
        }
        return modelAndView;
    }

    /**
     * 课程结项统计
     *
     * @param request
     * @return
     */
    @RequestMapping("/resultTaskStatistics")
    public ModelAndView resultTaskStatistics(HttpServletRequest request, @RequestParam("type") Integer type, @RequestParam("year") String year) {
        ModelAndView modelAndView = new ModelAndView("result/resultStatistics/result_task_statistics");
        try {
            ResultStatistics resultStatistics3 = resultStatisticsBiz.getResultStatisticsByYear(type, year, 3);
            modelAndView.addObject("resultStatistics3", resultStatistics3);
            modelAndView.addObject("year", year);
            modelAndView.addObject("type", type);
        } catch (Exception e) {
            logger.error("resultTaskStatistics", e);
        }
        return modelAndView;
    }

    /**
     * 按年成果统计页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/resultYearStatistics")
    public ModelAndView resultToYearStatistics(HttpServletRequest request, @RequestParam("type") Integer type, @RequestParam("year") String year) {
        ModelAndView modelAndView = new ModelAndView("/result/resultStatistics/result_year_statistics");
        try {
            ResultStatistics resultStatistics1 = resultStatisticsBiz.getResultStatisticsByYear(type, year, 1);
            ResultStatistics resultStatistics2 = resultStatisticsBiz.getResultStatisticsByYear(type, year, 2);
            ResultStatistics resultStatistics3 = resultStatisticsBiz.getResultStatisticsByYear(type, year, 3);
            ResultStatistics resultStatistics4 = resultStatisticsBiz.getResultStatisticsByYear(type, year, 4);
            modelAndView.addObject("resultStatistics1", resultStatistics1);
            modelAndView.addObject("resultStatistics2", resultStatistics2);
            modelAndView.addObject("resultStatistics3", resultStatistics3);
            modelAndView.addObject("resultStatistics4", resultStatistics4);
            modelAndView.addObject("year", year);
            modelAndView.addObject("type", type);
        } catch (Exception e) {
            logger.error("resultYearStatistics", e);
        }
        return modelAndView;
    }

    /**
     * 科研历年成果统计页面
     *
     * @param request 年份
     * @param type    系统类型
     * @return 柱状图
     */
    @RequestMapping("/resultEveryYearStatistics")
    public ModelAndView resultEveryYearStatistics(HttpServletRequest request, @RequestParam("type") Integer type) {
        ModelAndView mv = new ModelAndView("/result/resultStatistics/result_every_year_statistics");
        try {
            //获取当前年
            Calendar c = Calendar.getInstance();
            int currentYear = c.get(Calendar.YEAR);
            request.setAttribute("currentYear", currentYear);

            String year = request.getParameter("year");
            if (year == null || "".equals(year)) {
                year = String.valueOf(currentYear);
            }
            request.setAttribute("year", year);

            String yearString = "";
            String count1 = "";
            String count2 = "";
            String count3 = "";
            String count4 = "";
            List<ResultStatistics> resultStatisticsList = resultStatisticsBiz.find(null, " 1=1 and resultType=" + 1 + " order by createTime desc ");
            if (!CollectionUtils.isEmpty(resultStatisticsList)) {
                for (int i = 0; i < resultStatisticsList.size(); i++) {
                    if (i > 0) {
                        String date1 = resultStatisticsList.get(i).getDate().substring(0, 4);
                        String date2 = resultStatisticsList.get(i - 1).getDate().substring(0, 4);
                        if (date1.equals(date2)) {
                            continue;
                        } else {
                            yearString += date1 + " ";
                            year = date1;
                        }
                    } else {
                        yearString += resultStatisticsList.get(i).getDate().substring(0, 4) + " ";
                        year = resultStatisticsList.get(i).getDate().substring(0, 4);
                    }
                    for (int j = 1; j <= 4; j++) {
                        ResultStatistics resultStatistics = resultStatisticsBiz.getResultStatisticsByYear(type, year, j);
                        if (j == 1) {
                            count1 += resultStatistics.getDeclareCount().toString() + " ";
                        } else if (j == 2) {
                            count2 += resultStatistics.getDeclareCount().toString() + " ";
                        } else if (j == 3) {
                            count3 += resultStatistics.getDeclareCount().toString() + " ";
                        } else {
                            count4 += resultStatistics.getDeclareCount().toString() + " ";
                        }
                    }
                }
                count1 = count1.trim().replace(' ', ',');
                count2 = count2.trim().replace(' ', ',');
                count3 = count3.trim().replace(' ', ',');
                count4 = count4.trim().replace(' ', ',');
                yearString = yearString.trim().replace(' ', ',');
            }
            mv.addObject("yearString", yearString);
            mv.addObject("type", type);
            mv.addObject("count1", count1);
            mv.addObject("count2", count2);
            mv.addObject("count3", count3);
            mv.addObject("count4", count4);

            // table数据
            ResultStatistics resultStatistics1 = resultStatisticsBiz.getResultStatisticsByYear(type, year, 1);
            ResultStatistics resultStatistics2 = resultStatisticsBiz.getResultStatisticsByYear(type, year, 2);
            ResultStatistics resultStatistics3 = resultStatisticsBiz.getResultStatisticsByYear(type, year, 3);
            ResultStatistics resultStatistics4 = resultStatisticsBiz.getResultStatisticsByYear(type, year, 4);
            mv.addObject("resultStatistics1", resultStatistics1);
            mv.addObject("resultStatistics2", resultStatistics2);
            mv.addObject("resultStatistics3", resultStatistics3);
            mv.addObject("resultStatistics4", resultStatistics4);
        } catch (Exception e) {
            logger.error("resultEveryYearStatistics", e);
        }
        return mv;
    }

}
