package com.tongji.controller.statistic.analysis;

import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.tongji.biz.common.HqHessianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 后勤统计分析 Controller
 *
 * @author sk
 * @since 2017-02-27
 */
@Controller
@RequestMapping("/admin/statistic/analysis/logistics")
public class LogisticsController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(LogisticsController.class);

    @Autowired
    private HqHessianService hqHessianService;

    /**
     * 查询食堂的收支(经营)情况
     */
    public void listCanteenAcct() {
    }

    /**
     * 查询会议室使用及收支(经营)情况
     */
    public void listConfRm() {
    }

    /**
     * 查询部门的收支(经营)情况
     *
     * @param deptId 部门id
     */
    @RequestMapping("/listDeptAcct")
    public void listDeptAcct(Integer deptId) {

    }

    /**
     * 查询部门收支(经营)情况明细
     *
     * @param deptId 部门id
     */
    public void listDeptAcctDetail(Integer deptId) {
    }

    /**
     * 查询电费使用
     *
     * @param request {@link HttpServletRequest}
     * @since 2017-02-28
     */
    @RequestMapping("/listElectricFee")
    @SuppressWarnings("unchecked")
    public ModelAndView listElectricFee(HttpServletRequest request,
                                        @ModelAttribute("pagination") Pagination pagination,
                                        @RequestParam(value = "startTime", required = false) String startTime,
                                        @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView mv = new ModelAndView("statistic/analysis/logistics/electricity-list");
        try {
            Date start = null, end = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (!StringUtils.isTrimEmpty(startTime)) start = format.parse(startTime);
            if (!StringUtils.isTrimEmpty(endTime)) end = format.parse(endTime);
            Map<String, Object> map = hqHessianService.getElectricityStatistics(start, end, pagination);
            mv = assembleData(request, mv, map);
            if (start != null) mv.addObject("startTime", format.format(start));
            if (end != null) mv.addObject("endTime", format.format(end));
        } catch (Exception e) {
            logger.error("listElectricFee()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 查询加班
     */
    public void listExtraWork() {
    }

    /**
     * 查询客房的收支(经营)情况
     */
    public void listRoomAcct() {
    }

    /**
     * 查询临聘人员工资
     */
    public void listTempStaffWage() {
    }

    /**
     * 查询水费使用
     *
     * @param request    {@link HttpServletRequest}
     * @param pagination 分页
     * @since 2017-03-02
     */
    @RequestMapping("/listWaterFee")
    @SuppressWarnings("unchecked")
    public ModelAndView listWaterFee(HttpServletRequest request,
                                     @ModelAttribute("pagination") Pagination pagination,
                                     @RequestParam(value = "startTime", required = false) String startTime,
                                     @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView mv = new ModelAndView("statistic/analysis/logistics/water-list");
        try {
            Date start = null, end = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (!StringUtils.isTrimEmpty(startTime)) start = format.parse(startTime);
            if (!StringUtils.isTrimEmpty(endTime)) end = format.parse(endTime);
            Map<String, Object> map = hqHessianService.getWaterStatistics(start, end, pagination);
            mv = assembleData(request, mv, map);
            if (null != start) mv.addObject("startTime", format.format(start));
            if (null != end) mv.addObject("endTime", format.format(end));
        } catch (Exception e) {
            logger.error("listElectricFee()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 将数据组装到指定的{@link ModelAndView} 中
     *
     * @param request {@link HttpServletRequest}
     * @param mv      {@link ModelAndView}
     * @param data    数据
     * @return {@link ModelAndView}
     * @since 2017-03-02
     */
    @SuppressWarnings("unchecked")
    private ModelAndView assembleData(HttpServletRequest request,
                                      ModelAndView mv, Map<String, Object> data) {
        if (ObjectUtils.isNotNull(data)) {
            //分页
            Object obj = data.get("pagination");
            Pagination pagination = gson.fromJson(gson.toJson(obj), Pagination.class);
            pagination.setRequest(request);
            //用水统计
            List<Map<String, Object>> perMonthList = (List<Map<String, Object>>) data.get("perMonthList");
            Double totalConsume = (Double) data.getOrDefault("totalConsume", 0.0);
            Double totalExpense = (Double) data.getOrDefault("totalExpense", 0.0);
            mv.addObject("totalConsume", totalConsume);
            mv.addObject("totalExpense", totalExpense);
            mv.addObject("perMonthList", perMonthList);
            mv.addObject("pagination", pagination);
        }
        return mv;
    }
}
