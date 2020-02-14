package com.houqin.controller.lot;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.houqin.biz.lot.LotBiz;
import com.houqin.biz.lot.LotRecordBiz;
import com.houqin.biz.messstock.MessStockBiz;
import com.houqin.dao.lot.LotDao;
import com.houqin.entity.lot.Lot;
import com.houqin.entity.lot.LotRecord;
import com.houqin.entity.messstock.MessStock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 点击食堂库存列表中的货物 进行货物批次管理
 * 批次入库 包含货物id 时间编号 数量 价格
 * 出库货物批次 直接对批次进行操作
 *
 * @author YaoZhen
 * @create 10-24, 10:33, 2017.
 */
@Controller
@RequestMapping("/admin/houqin/lot")
public class LotController extends BaseController {
    /**
     * 已入库未出库
     */
    public static final int NOT_OUTBOUND = 1;
    /**
     * 已出库
     */
    public static final int OUTBOUND = 0;
    /**
     * 食堂库存列表
     */
    private static final String lotpriceCurve = "/messStock_list";
    /**
     * 当前线程的req
     */
    @Autowired
    private HttpServletRequest request;
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(LotController.class);

    /**
     * 批次
     */
    @Autowired
    private LotBiz lotBiz;

    @Autowired
    private LotDao lotDao;

    @Autowired
    private MessStockBiz messStockBiz;
    @Autowired
    private LotRecordBiz lotRecordBiz;

    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder({"lot"})
    public void lotInit(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("lot.");
    }

    @InitBinder({"lotRecord"})
    public void lotRecordInit(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("lotRecord.");
    }

    /**
     * 进出库列表
     *
     * @param messStockId 商品id
     * @param pagination  分页
     * @return 列表页
     */
    public
    @RequestMapping("/list/{messStockId}")
    ModelAndView listLot(@PathVariable("messStockId") Long messStockId,
                         @ModelAttribute("pagination") Pagination pagination,
                         @RequestParam(value = "searchOpt", required = false) String searchOpt) {
        ModelAndView mv = new ModelAndView("/lot/list_lot");
        try {
            // 传货物id
            mv.addObject("messStockId", messStockId);

            // 查询编号
            StringBuffer whereSql = new StringBuffer("1=1");
            whereSql.append(" and messStockId = ").append(messStockId);
            if (!StringUtils.isTrimEmpty(searchOpt)) {
                whereSql.append(" and lotNumber = ").append(searchOpt);
                mv.addObject("searchOpt", searchOpt);
            }
            pagination.setRequest(request);
            List<Lot> lotList = lotBiz.find(pagination, whereSql.toString());

            mv.addObject("lotList", lotList);
        } catch (Exception e) {
            logger.error("LotController.listLot", e);
        }
        return mv;
    }

    /**
     * 跳转到添加页面
     *
     * @param messStockId 货物的id
     * @return 添加页面
     */
    public
    @RequestMapping("/toSave/{messStockId}")
    ModelAndView toSave(@PathVariable("messStockId") Long messStockId) {
        ModelAndView mv = new ModelAndView("/lot/save_lot");
        try {
            MessStock messStock = messStockBiz.findById(messStockId);
            mv.addObject("messStock", messStock);
            mv.addObject("messStockId", messStockId);
        } catch (Exception e) {
            logger.error("LotController.toSave", e);
        }
        return mv;
    }

    /**
     * 添加
     *
     * @param lot 批次
     * @return json
     */
    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(@ModelAttribute("lot") Lot lot) {
        Map<String, Object> json;
        try {
            // 生成编号
            /*Long number = System.currentTimeMillis();*/
            SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
            // 取当前日历
            Calendar cal = Calendar.getInstance();
            String number = format.format(cal.getTime());
            lot.setLotNumber(Long.parseLong(number));
            // 入库
            lot.setStatus(NOT_OUTBOUND);
            lotBiz.save(lot);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("LotController.save", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * 跳转到出库页面
     *
     * @param lotId
     * @return
     */
    public
    @RequestMapping("/toOutbound/{lotId}")
    ModelAndView toOutbound(@PathVariable("lotId") Long lotId) {
        ModelAndView mv = new ModelAndView("/lot/out_lot");
        try {
            Lot lot = lotBiz.findById(lotId);
            mv.addObject("lot", lot);
        } catch (Exception e) {
            logger.error("LotController.outbound", e);
        }
        return mv;
    }

    /**
     * 出库 更新库存
     *
     * @param lot      批次
     * @param amount   出库数量
     * @param original 库存数量
     * @return
     */
    @RequestMapping("/outbound")
    @ResponseBody
    public Map<String, Object> outbound(@ModelAttribute("lot") Lot lot,
                                        @ModelAttribute("lotRecord") LotRecord lotRecord,
                                        @RequestParam("amount") Long amount,
                                        @RequestParam("original") Long original) {
        Map<String, Object> json;
        try {
            // 出库数量不能大于库存数量
            if (amount > original) {
                return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.ERROR_PARAMETER_VERIFY, null);
            }
            // 计算剩余库存
            lot.setLotAmount(original - amount);
            if (lot.getLotAmount() == 0) {
                lot.setStatus(OUTBOUND);
            }
            Integer flag = lotBiz.update(lot);
            if (flag > 0) {
                json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } else {
                json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
            }

            // 同步出库记录
            lotRecord.setLotId(lot.getId());
            lotRecord.setAmount(amount);
            lotRecordBiz.save(lotRecord);

        } catch (Exception e) {
            logger.error("LotController.outbound", e);
            json = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * 查询出库记录
     *
     * @param lotId 出库批次的id
     * @return 记录列表
     */
    @RequestMapping("listLotRecord/{id}")
    public ModelAndView listLotRecord(@PathVariable("id") Long lotId,
                                      @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/lot/list_lot_record");

        try {
            pagination.setRequest(request);
            List<LotRecord> records = lotRecordBiz.find(pagination, "lotId = " + lotId);
            mv.addObject("records", records);
        } catch (Exception e) {
            logger.error("LotController.listLotRecord", e);
        }
        return mv;
    }

    /**
     * 查询物品批次的价格
     *
     * @param request
     * @return
     */
    @RequestMapping("/priceCurve/{id}")
    public String priceCurve(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            List<Lot> lotList = lotDao.queryCountByLot(id.toString());
            request.setAttribute("lotList", lotList);


            MessStock messStock = messStockBiz.findById(id);
            request.setAttribute("messStock", messStock);
            //纵坐标
            String data = "";
            for (Lot l : lotList) {
                data += l.getLotPrice() + ",";
            }
            if (data != "") {
                data = data.substring(0, data.length() - 1);
                request.setAttribute("data", data);
            }

            //横坐标
            String categories = "";
            for (Lot lot : lotList) {
                categories += lot.getLotNumber() + ",";
            }
            if (categories != "") {
                categories = categories.substring(0, categories.length() - 1);
                request.setAttribute("categories", categories);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/lot/statistic_lotprice";
    }


}
