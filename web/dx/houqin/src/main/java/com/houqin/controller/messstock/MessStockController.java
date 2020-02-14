package com.houqin.controller.messstock;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.houqin.biz.food.FoodTypeBiz;
import com.houqin.biz.goodsunit.GoodsunitBiz;
import com.houqin.biz.lot.LotBiz;
import com.houqin.biz.messstock.MessStockBiz;
import com.houqin.entity.food.FoodType;
import com.houqin.entity.goodsunit.Goodsunit;
import com.houqin.entity.lot.Lot;
import com.houqin.entity.messstock.MessStock;
import com.houqin.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 食堂库存列表
 *
 * @author wanghailong
 * @create 2017-06-15-上午 10:08
 */
@Controller
@RequestMapping("/admin/houqin")
public class MessStockController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MessStockController.class);

    private static final String mess_StockList = "/mess/messStock_list";//食堂库存列表
    private static final String mess_Stock_Add = "/mess/messStock_add";//食堂库存列表
    private static final String mess_Stock_Update = "/mess/messStock_update";//食堂库存列表

    @Autowired
    private MessStockBiz messStockBiz;
    @Autowired
    private GoodsunitBiz goodsunitBiz;
    @Autowired
    private LotBiz lotBiz;
    @Autowired
    private FoodTypeBiz foodTypeBiz;
    /**
     * 当前线程的req
     */
    @Autowired
    private HttpServletRequest request;

    // 格式化时间
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder({"messStock"})
    public void messStockInit(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("messStock.");
    }

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"messStock"})
    public void initRepair(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("messStock.");
    }

    /**
     * 查询库存
     *
     * @param request
     * @param pagination
     * @param messStock
     * @return
     */
    @RequestMapping("/queryAllMessStock")
    public String queryAllMessStock(HttpServletRequest request,
                                    @ModelAttribute("pagination") Pagination pagination,
                                    @ModelAttribute("messStock") MessStock messStock) {
        try {
            pagination.setPageSize(6);
            String whereSql = GenerateSqlUtil.getSql(messStock);
            List<MessStock> messStockList = messStockBiz.find(pagination, whereSql);


            if (ObjectUtils.isNotNull(messStockList)) {
                for (int i = 0; i < messStockList.size(); i++) {
                    MessStock ms = messStockList.get(i);
                    // 查询批次
                    List<Lot> lots = lotBiz.find(null, "messStockId = " + ms.getId());
                    if (ObjectUtils.isNotNull(lots)) {
                        // 总数量
                        Long totalAmount = 0L;
                        // 总价格
                        BigDecimal totalPrice = new BigDecimal(0);
                        for (Lot l : lots) {
                            Long a = l.getLotAmount();
                            // 累加总数
                            totalAmount += a;
                            // 累加总价(数量乘单价)
                            BigDecimal lotAmountBigDecimal = new BigDecimal(l.getLotAmount());
                            BigDecimal b = l.getLotPrice().multiply(lotAmountBigDecimal);
                            totalPrice = totalPrice.add(b);
                        }
                        ms.setCount(totalAmount.intValue());
                        ms.setPrice(totalPrice.doubleValue());
                    }
                }
            }

            // 进货名称
            List<FoodType> foodTypeList = foodTypeBiz.find(null, "1=1");

            request.setAttribute("foodTypeList", foodTypeList);
            request.setAttribute("messStockList", messStockList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mess_StockList;
    }

    /**
     * 增加食堂库存
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddMessStock")
    public String toAddMessStock(HttpServletRequest request) {
        try {
            List<Goodsunit> goodsunitList = goodsunitBiz.findAll();

            // 进货名称
            List<FoodType> foodTypeList = foodTypeBiz.find(null, "1=1");

            request.setAttribute("foodTypeList", foodTypeList);
            request.setAttribute("goodsunitList", goodsunitList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mess_Stock_Add;
    }

    /**
     * 添加库存
     *
     * @param messStock
     * @return
     */
    @RequestMapping("/addMessStock")
    @ResponseBody
    public Map<String, Object> addMessStock(@ModelAttribute("messStock") MessStock messStock) {
        Map<String, Object> json;
        try {
            messStockBiz.save(messStock);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("MessStockController.addMessStock", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 去更新
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateMessStock/{id}")
    public String  toUpdateMessStock(HttpServletRequest request,@PathVariable("id")long id){
        try {
            MessStock messStock = messStockBiz.findById(id);

            List<Goodsunit> goodsunitList = goodsunitBiz.findAll();
            // 进货名称
            List<FoodType> foodTypeList = foodTypeBiz.find(null, "1=1");
            request.setAttribute("foodTypeList", foodTypeList);
            request.setAttribute("messStock", messStock);
            request.setAttribute("goodsunitList", goodsunitList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mess_Stock_Update;
    }

    /**
     * 更新库存
     * @param request
     * @param messStock
     * @return
     */
    @RequestMapping("/updateMessStock")
    public String updateMessStock(HttpServletRequest request,@ModelAttribute("messStock") MessStock messStock){
        try {
            messStockBiz.update(messStock);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/houqin/queryAllMessStock.json";
    }

    /**
     * 去批量添加
     *
     * @return
     */
    @RequestMapping("/toBatchMessStock")
    public ModelAndView toBatchMessStock() {
        ModelAndView modelAndView = new ModelAndView("/mess/batch_import_mess");
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 批量添加食堂库存
     *
     * @return
     */
    @RequestMapping("/batchAddImportMess")
    public ModelAndView batchAddImportMess(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        ModelAndView modelAndView = new ModelAndView("/mess/batch_import_mess");
        try {
            String errorInfo = messStockBiz.batchImportMess(myFile, request);
            if (StringUtils.isTrimEmpty(errorInfo)) {
                errorInfo = "导入成功";
            }
            modelAndView.addObject("errorInfo", errorInfo);
        } catch (Exception e) {
            logger.error("EmployeeController.batchAddEmployee", e);
            modelAndView.addObject("errorInfo", "导入失败, 请核对模板格式");
        }
        return modelAndView;
    }
}
