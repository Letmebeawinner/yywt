package com.keyanzizheng.controller.department;

import com.a_268.base.controller.BaseController;
import com.keyanzizheng.biz.common.BaseHessianBiz;
import com.keyanzizheng.entity.department.DepartMent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/ky")
public class DepartMentController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(DepartMentController.class);

    @InitBinder("departMent")
    public void initDepartMent(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("departMent.");
    }
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    /**
     * 部门选择列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/ajax/selectDepartMentList")
    public ModelAndView selectEmployeeList(HttpServletRequest request,
                                        @ModelAttribute("departMent") DepartMent departMent) {
        ModelAndView modelAndView = new ModelAndView("/department/select_department_list");
        try {
            List<DepartMent> departMentList = baseHessianBiz.getDepartMentList(departMent);
            modelAndView.addObject("departMentList", departMentList);
        } catch (Exception e) {
            logger.error("getEmployeeList", e);
        }
        return modelAndView;
    }
}
