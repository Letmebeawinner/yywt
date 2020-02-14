package com.jiaowu.controller;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.a_268.base.constants.ErrorCode;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.common.BaseHessianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.a_268.base.controller.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/jiaowu")
public class CommonController extends BaseController {


    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private BaseHessianService baseHessianService;

    /**
     * 查询部门ID
     * @param request
     * @return
     */
    @RequestMapping("/queryUserDepartment")
    @ResponseBody
    public Map<String, Object> queryUserDepartment(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            /*Long userId = SysUserUtils.getLoginSysUserId(request);
            if(userId!=null&&!userId.equals(0L)){
                Map<String, String> departmentId = baseHessianService.queryDepartmentBySysuserId(userId);
                if(departmentId!=null){
                    json = this.resultJson(ErrorCode.SUCCESS, "", departmentId.get("departmentId"));
                }
            }*/


        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
