package com.app.controller;

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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * App更新控制Controller
 *
 * @author sk
 * @since 2017-02-20
 */
@Controller
@RequestMapping("/admin/app/update")
public class AppUpdateController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AppUpdateController.class);

    @Autowired
    private AppUpdateBiz appUpdateBiz;

    @InitBinder("appUpdate")
    public void binderAppUpdate(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("appUpdate.");
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        System.out.println(request.getRequestURL());
        try {
        } catch (Exception e) {
            logger.error("index()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/app/index";
    }

    /**
     * 查询app更新信息
     *
     * @param request {@link HttpServletRequest} param:mobileType 1.Android 2.IOS
     * @return app更新信息页面
     */
    @RequestMapping("/queryAppUpdateInfo")
    public String queryAppUpdateInfo(HttpServletRequest request) {
        try {
            String mobileType = request.getParameter("mobileType");
            if (!StringUtils.isTrimEmpty(mobileType)) {
                String where = " mobileType=" + mobileType;
                List<AppUpdate> updateList = appUpdateBiz.find(null, where);
                if (ObjectUtils.isNotNull(updateList))
                    request.setAttribute("appUpdate", updateList.get(0));
            }
            return "/app/appUpdate-save";
        } catch (Exception e) {
            logger.error("queryAppUpdateInfo()--error", e);
            return setErrorPath(request, e);
        }
    }

    /**
     * 添加或保存app更新信息
     *
     * @param update app更新信息
     */
    @RequestMapping("/saveAppUpdateInfo")
    @ResponseBody
    public Map<String, Object> saveAppUpdateInfo(@ModelAttribute("appUpdate") AppUpdate update) {
        try {
            Map<String, Object> verify = verify(update);
            if (!verify.get("code").equals(ErrorCode.SUCCESS))
                return verify;
            appUpdateBiz.save(update);
            return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("saveAppUpdateInfo()--error", e);
            return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 验证app更新信息字段的合法性
     *
     * @param update app更新信息
     * @return {@link Map}
     */
    private Map<String, Object> verify(AppUpdate update) {
        if (StringUtils.isTrimEmpty(update.getVersion())) {
            return resultJson(ErrorCode.ERROR_PARAMETER, "app版本不能为空", null);
        }
        if (ObjectUtils.isNull(update.getMobileType())) {
            return resultJson(ErrorCode.ERROR_PARAMETER, "app系统平台类型不能为空", null);
        }
        if (StringUtils.isTrimEmpty(update.getUpdateUrl())) {
            return resultJson(ErrorCode.ERROR_PARAMETER, "app更新下载链接不能为空", null);
        }
        return resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }
}
