package com.houqin.controller.goodsunit;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.goodsunit.GoodsunitBiz;
import com.houqin.entity.goodsunit.Goodsunit;
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

/**
 * 物品单位管理
 *
 * @author ccl
 * @create 2016-12-19-14:05
 */
@Controller
@RequestMapping("/admin/houqin")
public class GoodsunitController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(GoodsunitController.class);

    private static final String createGoodsunit = "/goodsunit/add-goodsunit";//添加物品单位
    private static final String toUpdateGoodstunit = "/goodsunit/update-goodsunit";//修改物品单位
    private static final String goodsunitList = "/goodsunit/goodsunit_list";//物品单位列表

    @Autowired
    private GoodsunitBiz goodsunitBiz;

    @InitBinder({"goodsunit"})
    public void initGoodsUnit(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("goodsunit.");
    }

    /**
     * @Description:查询物品单位
     * @author: ccl
     * @Param: [request, pagination]
     * @Return: java.lang.String
     * @Date: 2016-12-19
     */
    @RequestMapping("/queryAllGoodsUnit")
    public String queryAllGoodsUnit(HttpServletRequest request,
                                    @ModelAttribute("pagination") Pagination pagination,
                                    @ModelAttribute("goodsunit") Goodsunit goodsunit) {
        try {
            String whereSql = GenerateSqlUtil.getSql(goodsunit);
            pagination.setRequest(request);
            List<Goodsunit> goodsUnitList = goodsunitBiz.find(pagination, whereSql);
            request.setAttribute("goodsUnitList", goodsUnitList);
        } catch (Exception e) {
            logger.info("--queryAllGoodsUnit", e);
            return this.setErrorPath(request, e);
        }
        return goodsunitList;
    }


    /**
     * @Description:添加物品单位
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-19
     */
    @RequestMapping("/toAddGoodsunit")
    public String toAddGoodsUnit(HttpServletRequest request) {
        try {
        } catch (Exception e) {
            logger.info("--toAddGoodsUnit", e);
            return this.setErrorPath(request, e);
        }
        return createGoodsunit;
    }


    /**
     * @Description:添加保存
     * @author: ccl
     * @Param: [request, goodsunit]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-19
     */
    @RequestMapping("/addSaveGoodsunit")
    @ResponseBody
    public Map<String, Object> addSaveGoodsUnit(HttpServletRequest request, @ModelAttribute("goodsunit") Goodsunit goodsunit) {
        Map<String, Object> resultMap = null;
        try {
            goodsunitBiz.save(goodsunit);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("GoodsunitController--addSaveGoodsUnit", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:去修改物品单位
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-19
     */
    @RequestMapping("/toUpdateGoodsunit")
    public String toUpdateGoodsunit(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            Goodsunit goodsunit = goodsunitBiz.findById(id);
            request.setAttribute("goodsunit", goodsunit);
        } catch (Exception e) {
            logger.info("--toUpdateGoodsunit", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateGoodstunit;
    }


    /**
     * @Description:修改
     * @author: ccl
     * @Param: [request, goodsunit]
     * @Return: java.lang.String
     * @Date: 2016-12-19
     */
    @RequestMapping("/updateGoodsunit")
    @ResponseBody
    public Map<String, Object> updateGoodsUnit(HttpServletRequest request, @ModelAttribute("goodsunit") Goodsunit goodsunit) {
        Map<String, Object> resultMap = null;
        try {
            goodsunitBiz.update(goodsunit);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("GoodsunitController--updateGoodsUnit", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:删除物品单位
     * @author: ccl
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-19
     */
    @RequestMapping("/delGoodsunit")
    @ResponseBody
    public Map<String, Object> delGoodsunit(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            goodsunitBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("GoodsunitController--delGoodsunit", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

}
