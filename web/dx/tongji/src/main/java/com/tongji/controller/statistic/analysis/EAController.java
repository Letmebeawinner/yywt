package com.tongji.controller.statistic.analysis;

import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.tongji.biz.common.JiaoWuHessianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 教务统计Controller
 *
 * @author sk
 * @since 2017-02-27
 */
@Controller
@RequestMapping("/admin/statistic/analysis/ea")
public class EAController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(EAController.class);

    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;

    /**
     * 查询培训期次
     *
     * @param request {@link HttpServletRequest}
     * @return 培训期次页面
     */
    @RequestMapping("/listTrainBatches")
    @SuppressWarnings("unchecked")
    public ModelAndView listTrainBatches(HttpServletRequest request,
                                         @ModelAttribute("pagination") Pagination pagination,
                                         @RequestParam(value = "classType", required = false) Long classType) {
        ModelAndView mv = new ModelAndView("/statistic/analysis/ea/eaTrainBatches-list");
        try {
            String where = where(classType);
            Map<String, Object> map = jiaoWuHessianService.queryClassesList(pagination, where);
            if (ObjectUtils.isNotNull(map)) {
                //分页
                Object obj = map.get("pagination");
                pagination = gson.fromJson(gson.toJson(obj), Pagination.class);
                pagination.setRequest(request);
                //培训批次
                List<Map<String, String>> classesList = (List<Map<String, String>>) map.get("classesList");
                //班级类型列表
                Map<String, Object> classTypeMap = jiaoWuHessianService.listClassType();
                mv.addObject("pagination", pagination);
                mv.addObject("classesList", classesList);
                mv.addObject("classTypeMap", classTypeMap);
                mv.addObject("classType", classType);
            }
        } catch (Exception e) {
            logger.error("listTrainBatches()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 查询培训人员
     *
     * @param request {@link HttpServletRequest}
     */
    @RequestMapping("/listTrainStu")
    @SuppressWarnings("unchecked")
    public ModelAndView listTrainStu(HttpServletRequest request,
                                     @ModelAttribute("pagination") Pagination pagination,
                                     @RequestParam(value = "classType", required = false) Long classType) {
        ModelAndView mv = new ModelAndView("/statistic/analysis/ea/eaTrainStu-list");
        try {
            String where = where(classType);
            Map<String, Object> map = jiaoWuHessianService.userList(pagination, where);
            if (ObjectUtils.isNotNull(map)) {
                //分页
                Object obj = map.get("pagination");
                pagination = gson.fromJson(gson.toJson(obj), Pagination.class);
                pagination.setRequest(request);
                //培训人员列表
                List<Map<String, String>> userList = (List<Map<String, String>>) map.get("userList");
                //班级类型
                Map<String, Object> classTypeMap = jiaoWuHessianService.listClassType();
                mv.addObject("pagination", pagination);
                mv.addObject("userList", userList);
                mv.addObject("classTypeMap", classTypeMap);
                mv.addObject("classType", classType);
            }
        } catch (Exception e) {
            logger.error("listTrainStu()--error", e);
            mv.setViewName(setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 培训有关where查询子句
     *
     * @param classType 班级类型
     * @return where子句
     */
    private String where(Long classType) {
        String where = " 1=1";
        if (ObjectUtils.isNotNull(classType)) {
            where += " AND classTypeId=" + classType;
        }
        return where;
    }
}
