package com.sms.controller.common;

import com.a_268.base.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * SMS公共Controller
 *
 * @author s.li
 * @create 2017-02-22-14:42
 */
@Controller
@RequestMapping("/admin/sms/common")
public class SsmCommonController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(SsmCommonController.class);

    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        return "index";
    }
}
