package com.renshi.controller.union;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.StringUtils;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.biz.union.MaterialsBiz;
import com.renshi.entity.union.Materials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/rs")
public class MaterialsController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MaterialsController.class);

    @InitBinder("materials")
    public void initMaterials(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("materials.");
    }
    @Autowired
    private MaterialsBiz materialsBiz;
    @Autowired
    private EmployeeBiz employeeBiz;

    /**
     * 跳转物资添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddMaterials")
    public ModelAndView toAddMaterials(HttpServletRequest request
                                       ) {
        ModelAndView modelAndView = new ModelAndView("/materials/materials_add");
        try {
        } catch (Exception e) {
            logger.error("toAddMaterials", e);
        }
        return modelAndView;
    }

    /**
     * 添加物资
     *
     * @param request
     * @param materials
     * @return
     */
    @RequestMapping("/addMaterials")
    @ResponseBody
    public Map<String, Object> addMaterials(HttpServletRequest request, @ModelAttribute("materials")Materials materials) {
        Map<String, Object> objectMap = null;
        try {
            materials.setStatus(1);
            materialsBiz.save(materials);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", materials);
        } catch (Exception e) {
            logger.error("addMaterials", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 删除物资
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteMaterials")
    @ResponseBody
    public Map<String, Object> addMaterials(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        Map<String, Object> objectMap = null;
        try {
            Materials materials = materialsBiz.findById(id);
            materials.setStatus(2);
            materialsBiz.update(materials);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", materials);
        } catch (Exception e) {
            logger.error("addMaterials", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 去修改页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdateMaterials")
    public ModelAndView toUpdateMaterials(HttpServletRequest request, @RequestParam(value = "id",required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/materials/materials_update");
        try {
            Materials materials = materialsBiz.findById(id);
            modelAndView.addObject("materials", materials);
        } catch (Exception e) {
            logger.error("toUpdateMaterials", e);
        }
        return modelAndView;
    }

    /**
     * 修改物资
     *
     * @param request
     * @param materials
     * @return
     */
    @RequestMapping("/updateMaterials")
    @ResponseBody
    public Map<String, Object> updateMaterials(HttpServletRequest request, @ModelAttribute("materials") Materials materials) {
        Map<String, Object> objectMap = null;
        try {
            materialsBiz.update(materials);
            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", materials);
        } catch (Exception e) {
            logger.error("updateMaterials", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", materials);
        }
        return objectMap;
    }

    /**
     * 物资列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getMaterialsList")
    public ModelAndView getMaterialsList(HttpServletRequest request,
                                        @ModelAttribute("pagination") Pagination pagination,
                                        @ModelAttribute("materials") Materials materials) {
        ModelAndView modelAndView = new ModelAndView("/materials/materials_list");
        try {
            pagination.setRequest(request);
            List<Materials> materialsList = materialsBiz.getMaterialsList(materials,pagination);
            modelAndView.addObject("materialsList", materialsList);
        } catch (Exception e) {
            logger.error("getMaterialsList", e);
        }
        return modelAndView;
    }
}
