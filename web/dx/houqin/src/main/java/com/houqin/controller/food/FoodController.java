package com.houqin.controller.food;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.food.FoodBiz;
import com.houqin.biz.mess.MessBiz;
import com.houqin.entity.food.Food;
import com.houqin.entity.mess.Mess;
import com.houqin.entity.messstock.MessStock;
import com.houqin.entity.outstock.OutStock;
import com.houqin.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 食材
 *
 * @author wanghailong
 * @create 2017-07-25-下午 1:50
 */
@Controller
@RequestMapping("/admin/houqin")
public class FoodController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(FoodController.class);
    //添加食材
    private static final String toAddFood = "/food/add-food";
    //更新食材
    private static final String toUpdateFood = "/food/update-food";
    //食材分类
    private static final String foodList = "/food/food_list";

    private static final String messStock_add_outStock = "/food/messStock_add_outStock";//食堂库存列表

    @Autowired
    private FoodBiz foodBiz;
    @Autowired
    private MessBiz messBiz;

    @InitBinder("food")
    public void initFood(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("food.");
    }

    @InitBinder("outStock")
    public void initRepair(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("outStock.");
    }

    /**
     * 跳转添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddFood")
    public String toAddFood(HttpServletRequest request) {
        try {
            List<Mess> messTypeList = messBiz.find(null," 1=1");
            request.setAttribute("messTypeList", messTypeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toAddFood;
    }

    /**
     * 添加方法
     *
     * @param request
     * @param food
     * @return
     */
    @RequestMapping("/addFood")
    @ResponseBody
    public Map<String, Object> addFood(HttpServletRequest request, @ModelAttribute("food") Food food) {
        try {
            foodBiz.save(food);
        } catch (Exception e) {
            logger.info("FoodController--addFood", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 去查询
     *
     * @param request
     * @param food
     * @param pagination
     * @return
     */
    @RequestMapping("/selectAllFood")
    public String selectAllFood(HttpServletRequest request, @ModelAttribute("food") Food food, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = GenerateSqlUtil.getSql(food);
            pagination.setRequest(request);
            List<Food> foods = foodBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(foods)) {
                for (Food food1 : foods) {
                    Mess mess = messBiz.findById(food1.getMessId());
                    food1.setMess(mess);
                }
            }
            request.setAttribute("foods", foods);
            request.setAttribute("food", food);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("FoodController--selectAllFood", e);
        }
        return foodList;
    }

    /**
     * 去更新
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateFood")
    public String toUpdateFood(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            Food food = foodBiz.findById(id);
            request.setAttribute("food", food);
            List<Mess> messList = messBiz.find(null," 1=1");
            request.setAttribute("messList", messList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("FoodController--toUpdateFood", e);
        }
        return toUpdateFood;
    }

    /**
     * 更新
     *
     * @param request
     * @param food
     * @return
     */
    @RequestMapping("/updateFood")
    @ResponseBody
    public Map<String, Object> updateFood(HttpServletRequest request, @ModelAttribute("food") Food food) {
        try {
            foodBiz.update(food);
        } catch (Exception e) {
            logger.info("FoodController--updateFood", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 删除
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/delFood")
    @ResponseBody
    public Map<String, Object> delFood(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            foodBiz.deleteById(id);
        } catch (Exception e) {
            logger.info("FoodController--delFood", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    @RequestMapping("/foodStock_add_outStock/{id}")
    public String foodStock_add_outStock(HttpServletRequest request, @PathVariable("id") long id) {
        Food food = foodBiz.findById(id);
        MessStock messStock = new MessStock();
        if (ObjectUtils.isNotNull(food)) {
            messStock.setName(food.getName());
            messStock.setCount(food.getCont());
            messStock.setContent("");
        }
        request.setAttribute("messStock", messStock);
        return messStock_add_outStock;
    }

    @RequestMapping("/addSaveOutStockPlus")
    @ResponseBody
    public Map<String, Object> addSaveOutStockPlus(HttpServletRequest request, @ModelAttribute("outStock") OutStock outStock, @RequestParam("id") Long id) {
        try {

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String number = "CK" + sdf.format(date);

            Long userId = SysUserUtils.getLoginSysUserId(request);//出库人
            outStock.setBillNum(number);
            outStock.setUserId(userId);

            //查询库存的信息
            Food food = foodBiz.findById(id);
            //查询数量
            Integer oldNum = food.getCont();
            Long newNum = oldNum - outStock.getNum();
            food.setCont(newNum.intValue());
            foodBiz.tx_editStoreHousePlus(outStock, food);
        } catch (Exception e) {
            logger.info("--", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);

        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }
}
