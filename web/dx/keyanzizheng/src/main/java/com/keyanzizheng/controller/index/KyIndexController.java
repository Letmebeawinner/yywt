package com.keyanzizheng.controller.index;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.SysUserUtils;
import com.keyanzizheng.biz.common.BaseHessianBiz;
import com.keyanzizheng.constant.ResearchConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/ky")
public class KyIndexController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(KyIndexController.class);

    @Autowired
    HttpServletRequest request;
    @Autowired
    BaseHessianBiz baseHessianBiz;

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

    /**
     * 查询当前用户所在的部门, 单个角色看不到菜单
     */
    @RequestMapping("/getDEPTByUserId")
    @ResponseBody
    public Map<String, Object> getDEPTByUserId() {
        Map<String, Object> json;
        try {
            // 查询用户部门中间表
            Map<String, String> result =
                    baseHessianBiz.queryDepartmentBySysUserId(SysUserUtils.getLoginSysUserId(request));
            Long deptId = Long.parseLong(result.get("departmentId"));
            // 查询部门
            Map<String, String> dept = baseHessianBiz.queryDepartemntById(deptId);
            // 所有的父级部门
            String parentIds = dept.get("parentIds");
            // 属于审批部门的不显示
            if (parentIds.contains(String.valueOf(ResearchConstants.KY_DEPT)) ||
                    parentIds.contains(String.valueOf(ResearchConstants.ZZ_DEPT))) {
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, true);
            } else {
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, false);
            }
        } catch (Exception e) {
            logger.error("IndexController.getDEPTByUserId", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

}
