package com.houqin.controller.property;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.property.PropertySourceBiz;
import com.houqin.entity.property.PropertySource;
import com.houqin.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/houqin")
public class PropertySourceController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(PropertySourceController.class);

    //添加资产来源
    private static final String createPropertySource = "/property/add-property-source";
    //修改资产来源
    private static final String toUpdatePropertySource = "/property/update-property-source";
    //资产来源列表
    private static final String propertySourceList = "/property/property_source_list";

    @Autowired
    private PropertySourceBiz propertySourceBiz;

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"propertySource"})
    public void initPropertySource(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("propertySource.");
    }


    /**
     * @Description:查询所有的
     * @author: ccl
     * @Param: [request, pagination, PropertySource]
     * @Return: java.lang.String
     * @Date: 2017-02-06
     */
    @RequestMapping("/propertySourceList")
    public String propertySourceList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,
                                         @ModelAttribute("propertySource") PropertySource propertySource) {
        try {
            String whereSql= GenerateSqlUtil.getSql(propertySource);
            whereSql+=" order by sort desc";
            List<PropertySource> propertySourceList = propertySourceBiz.find(pagination, whereSql);
            request.setAttribute("propertySourceList", propertySourceList);
        } catch (Exception e) {
            logger.error("PropertySourceController--propertySourceList", e);
            return this.setErrorPath(request, e);
        }
        return propertySourceList;
    }


    /**
     * 初始化添加资产来源页面
     *
     * @param request HttpServletRequest
     * @return 添加页面
     */
    @RequestMapping("/toAddPropertySource")
    public String addPropertySource(HttpServletRequest request) {
        try {
        } catch (Exception e) {
            logger.error("PropertySourceController--addPropertySource", e);
            return this.setErrorPath(request, e);
        }
        return createPropertySource;
    }


    /**
     * 添加资产来源
     */
    @RequestMapping("/addSavePropertySource")
    @ResponseBody
    public Map<String, Object> addSavePropertySource(HttpServletRequest request, @ModelAttribute("propertySource") PropertySource propertySource) {
        Map<String, Object> resultMap = null;
        try {
            propertySourceBiz.save(propertySource);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("PropertySourceController--addSavePropertySource", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 去修改资产来源
     */
    @RequestMapping("/toUpdatePropertySource")
    public String toUpdatePropertySource(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            PropertySource propertySource = propertySourceBiz.findById(id);
            request.setAttribute("propertySource", propertySource);
        } catch (Exception e) {
            logger.info("toUpdatePropertySource--error", e);
            return this.setErrorPath(request, e);
        }
        return toUpdatePropertySource;
    }

    /**
     * 修改资产来源
     */
    @RequestMapping("/updatePropertySource")
    @ResponseBody
    public Map<String, Object> updatePropertySource(HttpServletRequest request, @ModelAttribute("propertySource") PropertySource propertySource) {
        Map<String, Object> resultMap = null;
        try {
            propertySourceBiz.update(propertySource);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("PropertySourceController--updatePropertySource", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * 修改资产来源
     */

    @RequestMapping("/delPropertySource")
    @ResponseBody
    public Map<String, Object> delPropertySource(HttpServletRequest request) {
        Map<String, Object> resultMap = null;
        try {
            String id = request.getParameter("id");
            propertySourceBiz.deleteById(Long.parseLong(id));
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("PropertySourceController--delPropertySource", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


}
