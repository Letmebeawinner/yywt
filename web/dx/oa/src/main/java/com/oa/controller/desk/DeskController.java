package com.oa.controller.desk;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.desk.DeskBiz;
import com.oa.biz.function.FunctionBiz;
import com.oa.entity.desk.Desk;
import com.oa.entity.desk.DeskDto;
import com.oa.entity.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 我的桌面
 *
 * @author ccl
 * @create 2017-01-17-14:44
 */
@Controller
@RequestMapping("/admin/oa")
public class DeskController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(DeskController.class);

    @Autowired
    private DeskBiz deskBiz;

    @Autowired
    private FunctionBiz functionBiz;

    @InitBinder("desk")
    public void initDesk(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("desk.");
    }

    private static final String toAddFunctions = "/desk/select_function_list";
    private static final String DeskList = "/desk/desk_list";

    @RequestMapping("/queryAllDesk")
    public String queryAllDesk(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            String whereSql ="1=1 and userId="+userId;
            List<DeskDto> deskList = deskBiz.getDeskDtoList(pagination,whereSql);
            request.setAttribute("deskList", deskList);
            request.setAttribute("userId", userId);
        } catch (Exception e) {
            logger.error("DeskController--queryAllDesk", e);
            return this.setErrorPath(request, e);
        }
        return DeskList;
    }

    /**
     * @Description:添加个人桌面
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2017-01-20
     */
    @RequestMapping("/ajax/toAddFunction")
    public String toAddFunction(HttpServletRequest request) {
        try {
            List<Function> functionList = functionBiz.queryFunctionList();
            request.setAttribute("functionList", functionList);

            Long userId = SysUserUtils.getLoginSysUserId(request);
            String whereSql = " userId=" + userId;
            List<Desk> deskList = deskBiz.queryDeskList(whereSql);

            for (Desk desk : deskList) {
                for (Function arr : functionList) {
                    if (desk.getFunctionId() == arr.getId()) {
                        arr.setFlag(1);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("DeskController--toAddFunctions", e);
            return this.setErrorPath(request, e);
        }
        return toAddFunctions;
    }


    /**
     * @Description:
     * @author: ccl
     * @Param: [arrange]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-19
     */
    @RequestMapping("/updateDesk")
    @ResponseBody
    public Map<String, Object> updateDesk(HttpServletRequest request, @RequestParam("functionsIds") String functionsIds) {
        Map<String, Object> resultMap = null;
        try {
            Long userId=SysUserUtils.getLoginSysUserId(request);
            deskBiz.tx_updateDesk(userId, functionsIds);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("DeskController--updateDesk", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

}
