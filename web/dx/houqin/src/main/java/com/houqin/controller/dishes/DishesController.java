package com.houqin.controller.dishes;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.dishes.DishesBiz;
import com.houqin.biz.mess.MessBiz;
import com.houqin.entity.dishes.Dishes;
import com.houqin.entity.mess.Mess;
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
 * 菜品管理
 * Created by Administrator on 2016/12/15.
 */
@Controller
@RequestMapping("/admin/houqin")
public class DishesController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(DishesController.class);
    //添加菜品
    private static final String createDishes = "/dishes/add-dishes";
    //修改菜品
    private static final String toUpdateDishes = "/dishes/update-dishes";
    //菜品列表
    private static final String DishesList = "/dishes/dishes_list";


    @Autowired
    private DishesBiz dishesBiz;

    @Autowired
    private MessBiz messBiz;


    @InitBinder({"dishes"})
    public void initDishes(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("dishes.");
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 查询菜品列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/queryAllDishes")
    public String queryAllDishes(HttpServletRequest request,
                                 @ModelAttribute("pagination") Pagination pagination,
                                 @RequestParam(value = "id") Long id) {
        try {
            String whereSql = "1=1";
            whereSql += " and messId=" + id;
            pagination.setRequest(request);
            List<Dishes> dishesList = dishesBiz.find(pagination, whereSql);
            request.setAttribute("dishesList", dishesList);
            Mess mess = messBiz.findById(id);
            request.setAttribute("mess", mess);
        } catch (Exception e) {
            logger.error("DishesController--queryAllDishes", e);
            return this.setErrorPath(request, e);
        }
        return DishesList;
    }


    /**
     * 去菜品添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddDishes")
    public String toAddDishes(HttpServletRequest request, @RequestParam(value = "messId") Long id) {
        try {
            request.setAttribute("messId", id);
        } catch (Exception e) {
            logger.error("DishesController--toAddDishes", e);
            return this.setErrorPath(request, e);
        }
        return createDishes;
    }

    /**
     * 菜品添加页面
     *
     * @param request
     * @param dishes
     * @return
     */
    @RequestMapping("/addSaveDishes")
    @ResponseBody
    public Map<String, Object> addSaveDishes(HttpServletRequest request, @ModelAttribute("dishes") Dishes dishes) {
        Map<String, Object> resultMap = null;
        try {
            dishesBiz.save(dishes);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("DishesController--addSaveDishes", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 去修改菜品页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateDishes")
    public String toUpdateDishes(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            Dishes dishes = dishesBiz.findById(id);
            request.setAttribute("dishes", dishes);
        } catch (Exception e) {
            logger.error("DishesController--toUpdateDishes", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateDishes;
    }

    /**
     * 修改菜品
     *
     * @param request
     * @param dishes
     * @return
     */
    @RequestMapping("/updateDishes")
    @ResponseBody
    public Map<String, Object> updateDishes(HttpServletRequest request, @ModelAttribute("dishes") Dishes dishes) {
        Map<String, Object> resultMap = null;
        try {
            dishesBiz.update(dishes);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("DishesController--updateDishes", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 根据id删除菜品
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteDishes")
    @ResponseBody
    public Map<String, Object> deleteDishes(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            dishesBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("DishesController--deleteDishes", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }
}
