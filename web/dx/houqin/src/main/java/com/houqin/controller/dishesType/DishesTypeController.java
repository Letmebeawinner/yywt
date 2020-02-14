package com.houqin.controller.dishesType;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.houqin.biz.dishesType.DishesTypeBiz;
import com.houqin.entity.dishesType.DishesType;
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
 * 菜品类型管理
 * Created by Administrator on 2016/12/15.
 */
@Controller
@RequestMapping("/admin/houqin")
public class DishesTypeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(DishesTypeController.class);
    //添加菜品类型
    private static final String createDishesType = "/dishesType/add-dishesType";
    //修改菜品类型
    private static final String toUpdateDishesType = "/dishesType/update-dishesType";
    //菜品类型列表
    private static final String DishesTypeList = "/dishesType/dishesType_list";

    @Autowired
    private DishesTypeBiz dishesTypeBiz;


    @InitBinder({"dishesType"})
    public void initDishesType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("dishesType.");
    }

    /**
     * 查询菜品分类列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/queryAllDishesType")
    public String queryAllDishesType(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = "1=1";
            String id = request.getParameter("typeId");
            if (!StringUtils.isTrimEmpty(id) && Integer.parseInt(id) > 0) {
                whereSql += " and id=" + id;
            }
            String name = request.getParameter("typeName");
            if (!StringUtils.isTrimEmpty(name)) {
                whereSql += " and name like '%" + name.trim() + "%'";
            }
            whereSql = whereSql + " order by sort desc";
            pagination.setRequest(request);
            List<DishesType> dishesTypeList = dishesTypeBiz.find(pagination, whereSql);
            request.setAttribute("dishesTypeList", dishesTypeList);
            request.setAttribute("name", name);
            request.setAttribute("id", id);
        } catch (Exception e) {
            logger.info("--", e);
            return this.setErrorPath(request, e);
        }
        return DishesTypeList;
    }


    /**
     * 去添加菜品分类页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddDishesType")
    public String toAddDishesType(HttpServletRequest request) {
        try {

        } catch (Exception e) {
            logger.info("--", e);
            return this.setErrorPath(request, e);
        }
        return createDishesType;
    }

    /**
     * 添加菜品分类
     *
     * @param request
     * @param dishesType
     * @return
     */
    @RequestMapping("/addSaveDishesType")
    @ResponseBody
    public Map<String, Object> addSaveDishesType(HttpServletRequest request, @ModelAttribute("dishesType") DishesType dishesType) {

        try {
            dishesTypeBiz.save(dishesType);
        } catch (Exception e) {
            logger.info("--", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);

        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }


    /**
     * 去修改菜品分类
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateDishesType/{id}")
    public String toUpdateDishesType(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            DishesType dishesType = dishesTypeBiz.findById(id);
            request.setAttribute("dishesType", dishesType);
        } catch (Exception e) {
            logger.info("--", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateDishesType;
    }

    /**
     * 修改菜品分类
     *
     * @param request
     * @param dishesType
     * @return
     */
    @RequestMapping("/updateDishesType")
    public String updateMenuType(HttpServletRequest request, @ModelAttribute("dishesType") DishesType dishesType) {
        try {
            dishesTypeBiz.update(dishesType);
        } catch (Exception e) {
            logger.info("--", e);
            return this.setErrorPath(request, e);
        }
        return "redirect:/admin/houqin/queryAllDishesType.json";
    }

    /**
     * 根据id删除菜品分类
     *
     * @param request
     * @return
     */
    @RequestMapping("/delDishesType")
    @ResponseBody
    public Map<String, Object> delDishesType(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            dishesTypeBiz.deleteById(Long.parseLong(id));
        } catch (Exception e) {
            logger.info("--", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }


}
