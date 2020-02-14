package com.app.api;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.app.biz.app.AppUpdateBiz;
import com.app.entity.AppUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * app controller
 */
@Controller
@RequestMapping("/api/app")
public class ApiAppController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ApiAppController.class);

    @Autowired
    private AppUpdateBiz appUpdateBiz;

    /**
     * app检查更新
     *
     * @param request {@link HttpServletRequest}
     * @return {@link Map}
     * @since 2017-02-23
     */
    @RequestMapping("/getAppUpdate")
    @ResponseBody
    public Map<String, Object> getAppUpdate(HttpServletRequest request) {
        try {
            String mobileType = request.getParameter("mobileType");
            if (StringUtils.isTrimEmpty(mobileType)) {
                return resultJson(ErrorCode.ERROR_PARAMETER, "请选择app系统平台类型", null);
            }
            String where = " mobileType=" + mobileType;
            List<AppUpdate> list = appUpdateBiz.find(null, where);
            if (ObjectUtils.isNull(list)) {
                return resultJson(ErrorCode.SUCCESS, "暂无新版本更新", null);
            }
            return resultJson(ErrorCode.SUCCESS, "", list.get(0));
        } catch (Exception e) {
            logger.error("getAppUpdate()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }
}
