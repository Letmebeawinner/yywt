package com.oa.controller.form;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.form.FormTypeBiz;
import com.oa.entity.form.FormType;
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
 * 表单分类
 *
 * @author ccl
 * @create 2017-01-05-17:54
 */
@Controller
@RequestMapping("/admin/oa/")
public class FormTypeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(FormTypeController.class);
    @Autowired
    private FormTypeBiz formTypeBiz;

    @InitBinder({"formType"})
    public void initFormType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("formType.");
    }

    private static final String toAddFormType = "/form/formType_add";
    private static final String toUpdateFormType = "/form/formType_update";
    private static final String formTypeList = "/form/formType_list";

    /**
     * 查询所有的类型
     *
     * @author: ccl
     * @Param: [request, pagination, formType]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/queryAllFormType")
    public String queryAllFormType(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("formType") FormType formType) {
        try {
            String whereSql = GenerateSqlUtil.getSql(formType);
            whereSql += " order by sort desc";
            pagination.setRequest(request);
            List<FormType> formTypeList = formTypeBiz.find(pagination, whereSql);
            request.setAttribute("formTypeList", gson.toJson(formTypeList));
            request.setAttribute("formType", formType);
        } catch (Exception e) {
            logger.info("formTypeController--queryAllFormType", e);
            return this.setErrorPath(request, e);
        }
        return formTypeList;
    }


    /**
     * @Description:去添加类型
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toAddFormType")
    public String toAddFormType(HttpServletRequest request) {
        try {
            List<FormType> formTypeList = formTypeBiz.formTypeList();
            request.setAttribute("formTypeList", formTypeList);
        } catch (Exception e) {
            logger.info("formTypeController--toAddFormType", e);
            return this.setErrorPath(request, e);
        }
        return toAddFormType;
    }

    /**
     * @Description:
     * @author: ccl
     * @Param: [formType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/addSaveFormType")
    @ResponseBody
    public Map<String, Object> addSaveFormType(@ModelAttribute("formType") FormType formType) {
        Map<String, Object> resultMap = null;
        try {
            formTypeBiz.save(formType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("formTypeController--addSaveFormType", e);
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
    @RequestMapping("/toUpdateFormType")
    public String toUpdateFormType(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            FormType formType = formTypeBiz.findById(id);
            request.setAttribute("formType", formType);

            List<FormType> formTypeList = formTypeBiz.formTypeList();
            request.setAttribute("formTypeList", formTypeList);
        } catch (Exception e) {
            logger.error("formTypeController--toUpdateFormType", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateFormType;
    }


    /**
     * @Description:修改公告类型
     * @author: ccl
     * @Param: [request, formType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/updateFormType")
    @ResponseBody
    public Map<String, Object> updateFormType(HttpServletRequest request, @ModelAttribute("formType") FormType formType) {
        Map<String, Object> resultMap = null;
        try {
            formTypeBiz.update(formType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("formTypeController--updateFormType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:删除公告类型
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/deleteFormType")
    public String deleteFormType(HttpServletRequest request, @RequestParam("ids") List<Long> ids) {
        try {
            formTypeBiz.deleteByIds(ids);
        } catch (Exception e) {
            logger.error("formTypeController-deleteFormType", e);
        }
        return "redirect:/admin/oa/queryAllFormType.json";
    }


}
