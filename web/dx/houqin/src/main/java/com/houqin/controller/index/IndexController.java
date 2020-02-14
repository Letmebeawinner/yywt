package com.houqin.controller.index;

import com.a_268.base.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ccl on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/houqin")
public class IndexController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 后勤首页
     *
     * @param request
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView toIndex(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/index/index");
        try {
        } catch (Exception e) {
            logger.error("toIndex", e);
        }
        return modelAndView;
    }

}
