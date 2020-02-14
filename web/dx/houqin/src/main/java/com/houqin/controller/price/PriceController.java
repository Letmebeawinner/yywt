package com.houqin.controller.price;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.price.PriceBiz;
import com.houqin.entity.price.Price;
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
import java.util.List;
import java.util.Map;

/**
 * @description 价格业务层
 * @author lzh
 * @create 2017-02-27-16:47
 */
@Controller
@RequestMapping("/admin/houqin")
public class PriceController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(PriceController.class);

    private static final String priceList = "/price/price_list";
    private static final String priceAdd = "/price/price_add";
    private static final String priceUpdate = "/price/price_update";

    @Autowired
    private PriceBiz priceBiz;

    @InitBinder("price")
    public void initBinderPrice(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("price.");
    }

    /**
     * @Description: 价格列表
     * @author: lzh
     * @Param: [request, pagination]
     * @Return: java.lang.String
     * @Date: 16:50
     */
    @RequestMapping("/price/list")
    public String queryAllPrice(HttpServletRequest request,
                                @ModelAttribute("pagination") Pagination pagination,
                                @ModelAttribute("price") Price price) {
        try {
            pagination.setRequest(request);
            String whereSql = GenerateSqlUtil.getSql(price);
            List<Price> prices = priceBiz.find(pagination, whereSql);
            request.setAttribute("prices", prices);
            request.setAttribute("pagination", pagination);
        } catch(Exception e) {
            logger.error("PriceController.queryAllPrice", e);
            return this.setErrorPath(request, e);
        }
        return priceList;
    }

    /**
     * @Description: 跳转到增加单价页面
     * @author: lzh
     * @Param: []
     * @Return: java.lang.String
     * @Date: 17:23
     */
    @RequestMapping("/price/to/add")
    public String toPriceAdd() {
        return priceAdd;
    }

    /**
     * @Description: 增加单价
     * @author: lzh
     * @Param: [request, price]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 17:25
     */
    @RequestMapping("/ajax/price/add")
    @ResponseBody
    public Map<String, Object> priceAdd(@ModelAttribute("price") Price price) {
        Map<String,Object> resultMap = null;
            try {
                priceBiz.save(price);
                resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } catch (Exception e) {
                logger.error("PriceController.priceAdd", e);
                return this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
            }
            return resultMap;
    }

    /**
     * @Description: 修改单价
     * @author: lzh
     * @Param: [request, priceId]
     * @Return: java.lang.String
     * @Date: 17:29
     */
    @RequestMapping("/price/to/update")
    public String toPriceUpdate(HttpServletRequest request, @RequestParam("priceId") Long priceId) {
        try {
            Price price = priceBiz.findById(priceId);
            request.setAttribute("price", price);
        } catch(Exception e) {
            logger.error("PriceController.toPriceUpdate", e);
            return this.setErrorPath(request, e);
        }
        return priceUpdate;
    }

    /**
     * @Description: 更新单价
     * @author: lzh
     * @Param: [price]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 17:30
     */
    @RequestMapping("/ajax/price/update")
    @ResponseBody
    public Map<String, Object> priceUpdate(@ModelAttribute("price") Price price) {
        Map<String, Object> resultMap = null;
            try {
                priceBiz.update(price);
                resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } catch (Exception e) {
                logger.error("", e);
                resultMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
            }
            return resultMap;
    }

    /**
     * @Description: 删除单价
     * @author: lzh
     * @Param: [priceId]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 17:33
     */
    @RequestMapping("/ajax/price/delete")
    @ResponseBody
    public Map<String, Object> priceDel(@RequestParam("priceId") Long priceId) {
        Map<String, Object> resultMap = null;
            try {
                priceBiz.deleteById(priceId);
                resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } catch (Exception e) {
                logger.error("", e);
                resultMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
            }
            return resultMap;
    }

}
