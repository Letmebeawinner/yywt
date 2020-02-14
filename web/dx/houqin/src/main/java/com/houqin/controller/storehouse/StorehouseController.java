package com.houqin.controller.storehouse;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.houqin.biz.goodstype.GoodstypeBiz;
import com.houqin.biz.storage.StorageBiz;
import com.houqin.biz.storehouse.StoreHouseBiz;
import com.houqin.entity.goods.Goods;
import com.houqin.entity.goodstype.Goodstype;
import com.houqin.entity.repair.RepairStatistics;
import com.houqin.entity.storage.Storage;
import com.houqin.entity.storehouse.StoreHouse;
import com.houqin.entity.storehouse.StoreHouseDto;
import com.houqin.utils.FileExportImportUtil;
import com.houqin.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 库存管理
 *
 * @author ccl
 * @create 2016-12-20-12:38
 */
@Controller
@RequestMapping("/admin/houqin")
public class StorehouseController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(StorehouseController.class);
    //库存管理列表
    private static final String storehouseList = "/storehouse/storehouse_list";

    @Autowired
    private StoreHouseBiz storehouseBiz;

    @Autowired
    private GoodstypeBiz goodstypeBiz;

    @Autowired
    private StorageBiz storageBiz;

    @InitBinder({"storeHouse"})
    public void initStoreHouse(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("storeHouse.");
    }

    @InitBinder("goods")
    public void initGoods(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("goods.");
    }

    /**
     * @Description:库存管理
     * @author: ccl
     * @Param: [request, pagination]
     * @Return: java.lang.String
     * @Date: 2016-12-20
     */
    @RequestMapping("/queryStorehouse")
    public String queryStorehouse(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("storeHouse") StoreHouse storeHouse) {
        try {
            //物品类型
            List<Goodstype> goodTypeList = goodstypeBiz.getAllGoodstype();
            request.setAttribute("goodTypeList", goodTypeList);

            //库房列表
            List<Storage> storageList = storageBiz.find(null, " 1=1");
            request.setAttribute("storageList", storageList);


            String whereSql = GenerateSqlUtil.getSql(storeHouse);
            pagination.setRequest(request);
            List<StoreHouseDto> storeList = storehouseBiz.getAllStoreDto(pagination, whereSql);
            request.setAttribute("storeList", storeList);
        } catch (Exception e) {
            logger.error("StorehouseController--queryStorehouse", e);
            return this.setErrorPath(request, e);
        }
        return storehouseList;
    }


    @RequestMapping("/storeHouseExportExcel")
    public void storeHouseExportExcel(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("storeHouse") StoreHouse storeHouse) {
        try {
            String dir = request.getSession().getServletContext().getRealPath("/FileList");
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            String year = request.getParameter("year");
            String date = df.format(new Date());
            // 文件名
            String expName = year + "库存_" + date;
            // 表头信息
            String[] headName = {"入库仓库", "入库单号", "物品名称", "剩余数量", "物品单位", "物品编号", "物品类型", "经办人", "入库时间"};

            String whereSql = GenerateSqlUtil.getSql(storeHouse);
            List<StoreHouseDto> storeList = storehouseBiz.getAllStoreDto(null, whereSql);

            List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
            List<List<String>> list = storeHouseJoint(storeList);
            File file = FileExportImportUtil.createExcel(headName, list, expName, dir);
            srcfile.add(file);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> storeHouseJoint(List<StoreHouseDto> storeHouses) throws Exception {
        List<List<String>> list = new ArrayList<List<String>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < storeHouses.size(); i++) {
            List<String> small = new ArrayList<String>();
            StoreHouseDto storeHouse = storeHouses.get(i);
            small.add(storeHouse.getStorageName());
            small.add(storeHouse.getStorageNum());
            small.add(storeHouse.getGoodsName());
            small.add(storeHouse.getNum().toString());
            small.add(storeHouse.getUnitName());
            small.add(storeHouse.getCode());
            small.add(storeHouse.getTypeName());
            small.add(storeHouse.getUserName());
            small.add(sdf.format(storeHouse.getCreateTime()));
            list.add(small);
        }
        return list;
    }


    /**
     * 入库，增加记录，修改库存
     *
     * @param goods
     * @param storeHouseId
     * @param note
     * @return
     */
    @RequestMapping("/updateStock")
    @ResponseBody
    public Map<String, Object> addUpdateStock(@ModelAttribute("goods") Goods goods,
                                              @RequestParam("storeHouseId") Long storeHouseId,
                                              @RequestParam("note") String note) {
        Map<String, Object> json = null;
        try {
            storehouseBiz.tx_updateStore(goods, storeHouseId, note);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("StoreHouseController.addUpdateStock", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


}
