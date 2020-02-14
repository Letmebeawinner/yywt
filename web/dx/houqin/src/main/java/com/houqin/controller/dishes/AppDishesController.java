package com.houqin.controller.dishes;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.dishes.DishesBiz;
import com.houqin.biz.mess.MessBiz;
import com.houqin.entity.dishes.Dishes;
import com.houqin.entity.mess.Mess;
import com.houqin.utils.GenerateSqlUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜品管理（APP接口）
 * Created by Administrator on 2016/12/15.
 */
@Controller
@RequestMapping("/app/houqin")
public class AppDishesController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AppDishesController.class);


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
     * @Description:食堂列表
     * @author: ccl
     * @Param: [request, pagination]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/queryAllMess")
    @ResponseBody
    public Map<String, Object> queryAllMess() {
        try {
            List<Mess> messList = messBiz.find(null, "1=1");
            return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, messList);
        } catch (Exception e) {
            logger.error("MessController--queryAllMess", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

    /**
     * 查询当日菜品

     * @param messId 食堂ID
     * @param date 日期
     * @return
     */
    @RequestMapping("/queryDishes")
    @ResponseBody
    public Map<String, Object> queryAllDishes(HttpServletRequest request,
                                              @RequestParam(value = "date") String date,
                                              @RequestParam(value = "messId") Long messId) {
        try {
            String whereSql = "1=1";
            whereSql += " and messId=" + messId;
            whereSql += " and TO_DAYS( usetime )=TO_DAYS( '" + date + "' )";
            List<Dishes> dishesList = dishesBiz.find(null, whereSql);
            if(dishesList!=null && dishesList.size()>0){
                Dishes dishes=dishesList.get(0);
                Mess mess = messBiz.findById(messId);
                Map<String, Object> dataMap=new HashMap<>();
                dataMap.put("dishes",dishes);
                dataMap.put("mess",mess);
                return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, dataMap);
            }else {
                return this.resultJson(ErrorCode.SUCCESS, "当前条件下暂无菜品", null);
            }
        } catch (Exception e) {
            logger.error("DishesController--queryAllDishes", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
    }

}
