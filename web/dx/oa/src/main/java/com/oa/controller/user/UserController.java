package com.oa.controller.user;

import com.a_268.base.controller.BaseController;
import com.a_268.base.util.StringUtils;
import com.oa.common.BaseHessianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author lzh
 * @create 2017-12-25-13:48
 */
@Controller
@RequestMapping("/admin/oa")
public class UserController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private BaseHessianService baseHessianService;

    @RequestMapping("/get/sysUser")
    public Map<String, Object> getSysUser() {
        String where = " mobile IS NOT NULL AND mobile != '' AND status = 0 and userType = 2";
        Map<String, Object> map = baseHessianService.querySysUserList(null, where);
        return null;
    }

}
