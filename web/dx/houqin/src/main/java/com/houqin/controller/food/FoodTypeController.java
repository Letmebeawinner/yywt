package com.houqin.controller.food;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.equipment.EquipmentBiz;
import com.houqin.biz.food.FoodTypeBiz;
import com.houqin.controller.equipment.EquipmentController;
import com.houqin.entity.food.FoodType;
import com.houqin.utils.GenerateSqlUtil;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.List;
import java.util.Map;

/**
 * 食材分类
 *
 * @author wanghailong
 * @create 2017-07-25-上午 10:18
 */
@Controller
@RequestMapping("/admin/houqin")
public class FoodTypeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(FoodTypeController.class);
    //添加食材分类
    private static final String toAddFoodType = "/food/add-foodType";
    //更新食材分类
    private static final String toUpdateFoodType = "/food/update-foodType";
    //食材分类列表
    private static final String foodTypeList = "/food/foodType_list";

    @Autowired
    private FoodTypeBiz foodTypeBiz;

    @InitBinder("foodType")
    public void initFoodType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("foodType.");
    }

    /**
     * 跳转添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddFoodType")
    public String toAddFoodType(HttpServletRequest request) {
        return toAddFoodType;
    }

    /**
     * 添加方法
     *
     * @param request
     * @param foodType
     * @return
     */
    @RequestMapping("/addFoodType")
    @ResponseBody
    public Map<String, Object> addFoodType(HttpServletRequest request, @ModelAttribute("foodType") FoodType foodType) {
        try {
            foodTypeBiz.save(foodType);
        } catch (Exception e) {
            logger.info("FoodTypeController--addFoodType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 去查询
     *
     * @param request
     * @param foodType
     * @param pagination
     * @return
     */
    @RequestMapping("/selectAllFoodType")
    public String selectAllFoodType(HttpServletRequest request, @ModelAttribute("foodType") FoodType foodType, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = GenerateSqlUtil.getSql(foodType);
            pagination.setRequest(request);
            List<FoodType> foodTypeList = foodTypeBiz.find(pagination, whereSql);
            request.setAttribute("foodTypeList", foodTypeList);
            request.setAttribute("foodType", foodType);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("FoodTypeController--selectAllFoodType", e);
        }
        return foodTypeList;
    }

    /**
     * 去更新
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateFoodType")
    public String toUpdateFoodType(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            FoodType foodType = foodTypeBiz.findById(id);
            request.setAttribute("foodType", foodType);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("FoodTypeController--toUpdateFoodType", e);
        }
        return toUpdateFoodType;
    }

    /**
     * 更新
     *
     * @param request
     * @param foodType
     * @return
     */
    @RequestMapping("/updateFoodType")
    @ResponseBody
    public Map<String, Object> updateFoodType(HttpServletRequest request, @ModelAttribute("foodType") FoodType foodType) {
        try {
            foodTypeBiz.update(foodType);
        } catch (Exception e) {
            logger.info("FoodTypeController--updateFoodType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 删除
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/delFoodType")
    @ResponseBody
    public Map<String, Object> delFoodType(HttpServletRequest request, @RequestParam("id")Long id) {
        try {
            foodTypeBiz.deleteById(id);
        } catch (Exception e) {
            logger.info("FoodTypeController--delFoodType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }
}
