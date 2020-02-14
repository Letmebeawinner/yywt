package com.zaixianxuexi.controller;

import com.a_268.base.constants.BaseCommonConstants;
import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zaixianxuexi.common.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caichenglong on 2017/10/17.
 */
@Controller
public class RuiquController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(RuiquController.class);
    private static final String ADMIN_PREFIX = "/zaixianxuexi";
    private static final String redirectURL = "http://10.100.20.200/weixin?cmd=90009";

    /**
     * 单点登录
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/SingleLogin")
    @ResponseBody
    public String SingleLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取用户信息
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            if (ObjectUtils.isNotNull(userMap)) {
                //用户名
                String userNo = userMap.get("userNo");
                //密码
                String password = userMap.get("password");
                //拼接的参数串
                String param = "&coId=10001&username='" + userNo + "'&password='" + password + "'";
                response.sendRedirect(redirectURL + param);
            }else{
                response.sendRedirect(BaseCommonConstants.basePath);
            }
        } catch (Exception e) {
            logger.error("SingleLogin()---error", e);
        }
        return null;
    }


}
