package com.oa.controller.function;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.function.FunctionBiz;
import com.oa.entity.function.Function;
import com.oa.utils.GenerateSqlUtil;
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
 * 功能列表
 *
 * @author ccl
 * @create 2017-01-16-11:43
 */
@Controller
@RequestMapping("/admin/oa")
public class FunctionController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(FunctionController.class);


    @Autowired
    private FunctionBiz functionBiz;

    @InitBinder("function")
    public void initFunctions(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("function.");
    }

    private static final String toAddFunction = "/function/function_add";
    private static final String toUpdateFunction = "/function/function_update";
    private static final String FunctionList = "/function/function_list";

    /**
     * 查询所有的功能列表
     *
     * @author: ccl
     * @Param: [request, pagination, Function]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/queryAllFunction")
    public String queryAllFunction(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("function") Function function) {
        try {
            String whereSql = GenerateSqlUtil.getSql(function);
            whereSql += " order by sort desc";
            pagination.setRequest(request);
            List<Function> functionList = functionBiz.find(pagination, whereSql);
            request.setAttribute("functionList", functionList);
            request.setAttribute("functions",function);
        } catch (Exception e) {
            logger.info("FunctionController--queryAllFunction", e);
            return this.setErrorPath(request, e);
        }
        return FunctionList;
    }


    /**
     * @Description:去添加功能列表
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toAddFunction")
    public String toAddFunction(HttpServletRequest request) {
        return toAddFunction;
    }

    /**
     * @Description:
     * @author: ccl
     * @Param: [Function]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/addSaveFunction")
    @ResponseBody
    public Map<String, Object> addSaveFunction(@ModelAttribute("function") Function function) {
        Map<String, Object> resultMap = null;
        try {
            functionBiz.save(function);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("FunctionController--addSaveFunction", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:去修改
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toUpdateFunction")
    public String toUpdateFunction(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            Function function =functionBiz.findById(id);
            request.setAttribute("function", function);
        } catch (Exception e) {
            logger.error("FunctionController--toUpdateFunction", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateFunction;
    }


    /**
     * @Description:修改公告功能列表
     * @author: ccl
     * @Param: [request, Function]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/updateFunction")
    @ResponseBody
    public Map<String, Object> updateFunction(HttpServletRequest request, @ModelAttribute("function") Function function) {
        Map<String, Object> resultMap = null;
        try {
            functionBiz.update(function);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("FunctionController--updateFunction", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:删除功能列表
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/deleteFunction")
    @ResponseBody
    public Map<String, Object> deleteFunction(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            functionBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("FunctionController-deleteFunction", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


}
