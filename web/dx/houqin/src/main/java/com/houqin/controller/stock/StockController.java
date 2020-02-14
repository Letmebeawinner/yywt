package com.houqin.controller.stock;

import com.a_268.base.controller.BaseController;
import com.houqin.dao.stock.StockRecordDao;
import com.houqin.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lzh
 * @create 2018-01-09-18:59
 */
@RequestMapping("/admin/houqin")
@Controller
public class StockController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockRecordDao stockRecordDao;

    @RequestMapping("/to/stock/statistics")
    public String toStatisticStock(HttpServletRequest request, @RequestParam(value = "year", required = false) String year) {
        SimpleDateFormat smf = new SimpleDateFormat("yyyy");
        Date nowDate = new Date();
        String nowYear = smf.format(nowDate);
        try {
            if (StringUtils.isBlank(year)) {
                year = nowYear;
            }
            List<Map<String, Object>> joinStockRecords = stockRecordDao.getJoinStockRecordByYear(year);
            List<Map<String, Object>> outStockRecords = stockRecordDao.getOutStockRecordByYear(year);
            Map<String, Object> treeJoinStockRecords = new TreeMap<>((s1, s2) -> Long.parseLong(s1) > Long.parseLong(s2) ? 1 : 0);

            String pictureJoinData = "";
            String pictureOutData = "";
            for (int i = 1; i <= 12; i++) {
                Map<String, String> dataMap = new HashMap<>();
                String key = "" + i;
                if (joinStockRecords != null && joinStockRecords.size() > 0) {
                    if (joinStockRecords.get(0) != null) {
                        dataMap.put("join", joinStockRecords.get(0).get(key).toString());
                        pictureJoinData += joinStockRecords.get(0).get(key).toString() + ",";
                    }

                }
                if (outStockRecords != null && outStockRecords.size() > 0) {
                    if (outStockRecords.get(0) != null) {
                        dataMap.put("out", outStockRecords.get(0).get(key).toString());
                        pictureOutData += outStockRecords.get(0).get(key).toString() + ",";
                    }
                }
                treeJoinStockRecords.put(key, dataMap);

            }

            request.setAttribute("data", treeJoinStockRecords);
            request.setAttribute("pictureJoinData", pictureJoinData);
            request.setAttribute("pictureOutData", pictureOutData);
            request.setAttribute("queryYear", year);

        } catch (Exception e) {
            logger.error("StockController.toStatisticStock", e);
            return this.setErrorPath(request, e);
        }

        return "/stockRecord/statistics_stock";
    }

    /**
     * 物品在对应年月的出入库情况，以物品进行分组
     *
     * @param request
     * @param type
     * @param year
     * @param month
     * @return
     */
    @RequestMapping("/to/stock/statistics/detail")
    public String toStatisticStockDetail(HttpServletRequest request,
                                         @RequestParam("type") String type,
                                         @RequestParam("year") String year,
                                         @RequestParam("month") String month) {
        try {
            List<Map<String, String>> recordsDetail = null;
            if ("join".equals(type)) {
                recordsDetail = stockRecordDao.getJoinStockRecordDetailByYearAndMonth(year, month);
            } else {
                recordsDetail = stockRecordDao.getOutStockRecordDetailByYearAndMonth(year, month);
            }
            request.setAttribute("recordsDetail", recordsDetail);
            request.setAttribute("year", year);
            request.setAttribute("month", month);
        } catch (Exception e) {
            logger.error("StockController.toStatisticStock", e);
            return this.setErrorPath(request, e);
        }

        return "/outstock/statistics_outStock_list";
    }

}
