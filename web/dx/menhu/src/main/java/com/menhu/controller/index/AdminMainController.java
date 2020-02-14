package com.menhu.controller.index;

import com.a_268.base.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统控制中心
 * @author s.li
 */
@Controller
@RequestMapping("/admin/menhu")
public class AdminMainController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(AdminMainController.class);


    /**
     * @Description: 基础系统中心
     * @author: s.li
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016/12/14
     */
    @RequestMapping("/index")
    public String baseIndex(HttpServletRequest request){
        return "/admin/main/index";
    }
}
