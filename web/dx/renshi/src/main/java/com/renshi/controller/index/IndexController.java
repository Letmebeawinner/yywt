package com.renshi.controller.index;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.StringUtils;
import com.renshi.biz.educate.EducateBiz;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.entity.educate.Educate;
import com.renshi.entity.employee.Employee;
import com.renshi.entity.employee.QueryEmployee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/rs")
public class IndexController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 人事首页
     *
     * @param request
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView toAddEducate(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/index/index");
        try {
        } catch (Exception e) {
            logger.error("toAddEducate", e);
        }
        return modelAndView;
    }

}
