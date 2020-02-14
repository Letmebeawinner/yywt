package com.oa.controller.seal;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.seal.SealFunctionBiz;
import com.oa.entity.seal.SealFunction;
import com.oa.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 印章用途
 *
 * @author lzh
 * @create 2016/12/27
 */
@Controller
@RequestMapping("/admin/oa")
public class SealFunctionController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(SealFunctionController.class);

    @Autowired
    private SealFunctionBiz sealFunctionBiz;

    private static final String sealFunctionList = "/seal/seal_function_list";//印章用途列表
    private static final String toAddSealFunction = "/seal/seal_function_add";//添加印章用途
    private static final String toUpdateSealFunction = "/seal/seal_function_update";//修改印章用途

    @InitBinder("sealFunction")
    public void initBinderSealFunction(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("sealFunction.");
    }

    /**
     * @Description:印章用途列表
     * @author: lzh
     * @Param: request, pagination
     * @Return: String
     * @Date: 2016-12-17
     */
    @RequestMapping("/queryAllSealFunction")
    public String queryAllSealFunction(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("sealFunction") SealFunction sealFunction) {
        try {
            String whereSql = GenerateSqlUtil.getSql(sealFunction);
            pagination.setRequest(request);
            List<SealFunction> sealFunctions = sealFunctionBiz.find(pagination, whereSql);
            request.setAttribute("sealFunctions", sealFunctions);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            logger.error("SealFunctionController.queryAllSealFunction", e);
            return this.setErrorPath(request, e);
        }
        return sealFunctionList;
    }

    /**
     * @Description:添加印章用途
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-17
     */
    @RequestMapping("/toAddSealFunction")
    public String toAddSealFunction() {
        return toAddSealFunction;
    }

    /**
     * @Description:修改印章用途
     * @author: lzh
     * @Param:
     * @Return:
     * @Date: 2016-12-27
     */
    @RequestMapping("/toUpdateSealFunction")
    public String toUpdateSealFunction(HttpServletRequest request, @RequestParam("id") Long id, @RequestParam(value = "flag", required = false) int flag) {
        try {
            SealFunction sealFunction = sealFunctionBiz.findById(id);
            request.setAttribute("sealFunction", sealFunction);
            request.setAttribute("flag", flag);
        } catch (Exception e) {
            logger.error("SealFunctionController.toUpdateSealFunction", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateSealFunction;
    }

    /**
     * @Description:添加保存印章用途
     * @author: lzh
     * @Param: [sealFunction]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 17:59
     */
    @RequestMapping("/addSealFunction")
    @ResponseBody
    public Map<String, Object> addSealFunction(@ModelAttribute("sealFunction") SealFunction sealFunction) {
        Map<String, Object> resultMap = null;
        try {
            sealFunctionBiz.save(sealFunction);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("SealFunctionController.addSealFunction", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:修改印章用途
     * @author: lzh
     * @Param: [sealFunction]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:41
     */
    @RequestMapping("/updateSealFunction")
    @ResponseBody
    public Map<String, Object> updateSealFunction(@ModelAttribute("sealFunction") SealFunction sealFunction) {
        Map<String, Object> resultMap = null;
        try {
            sealFunctionBiz.update(sealFunction);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("SealFunctionController.updateSealFunction", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:删除印章用途
     * @author: lzh
     * @Param: [id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 10:55
     */
    @RequestMapping("/deleteSealFunction")
    @ResponseBody
    public Map<String, Object> delSealFunction(@RequestParam("id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            sealFunctionBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("SealFunctionController.delSealFunction", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;

    }


}
