package com.jiaowu.controller.loginauth;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.common.BaseHessianBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 教职工不能切换子系统
 *
 * @author YaoZhen
 * @since 11-24, 15:48, 2017.
 */
@Controller
@RequestMapping("/admin/jw")
public class LoginAuthController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(LoginAuthController.class);

    /**
     * 线上可以申报课题的教职工所在的部门
     */
    public static final String DECLARE = "89";

    @Autowired
    HttpServletRequest request;
    @Autowired
    BaseHessianBiz baseHessianBiz;

    /**
     * 查询当前用户所在的部门, 单个角色看不到菜单
     */
    @RequestMapping("/getDEPTByUserId")
    @ResponseBody
    public Map<String, Object> getDEPTByUserId() {
        Map<String, Object> json;
        try {
           /* // 查询用户部门中间表
            Map<String, String> result =
                    baseHessianBiz.queryDepartmentBySysUserId(SysUserUtils.getLoginSysUserId(request));
            Long deptId = Long.parseLong(result.get("departmentId"));
            // 查询部门
            Map<String, String> dept = baseHessianBiz.queryDepartemntById(deptId);
            String id = dept.get("id");
            // 属于审批部门的不显示
            if (id.contains(DECLARE)) {
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, true);
            } else {
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, false);
            }*/
            return null;
        } catch (Exception e) {
            logger.error("IndexController.getDEPTByUserId", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

}
