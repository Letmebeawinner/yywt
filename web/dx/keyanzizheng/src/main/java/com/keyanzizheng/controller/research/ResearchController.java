package com.keyanzizheng.controller.research;

import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.keyanzizheng.biz.common.JwHessianBiz;
import com.keyanzizheng.entity.research.ResearchReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/ky")
public class ResearchController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ResearchController.class);

    @InitBinder("researchReport")
    public void initresearchReport(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("researchReport.");
    }
    @Autowired
    private JwHessianBiz jwHessianBiz;
    /**
     * 班级调研报告列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getResearchReportList")
    public ModelAndView getResearchReportList(HttpServletRequest request,
                                      @ModelAttribute("pagination") Pagination pagination,
                                      @ModelAttribute("researchReport") ResearchReport researchReport) {
        ModelAndView modelAndView = new ModelAndView("/research/researchReport_list");
        try {

        } catch (Exception e) {
            logger.error("getresearchReportList", e);
        }
        return modelAndView;
    }
}
