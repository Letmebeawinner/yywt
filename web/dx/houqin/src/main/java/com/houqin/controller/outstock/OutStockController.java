package com.houqin.controller.outstock;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.google.common.collect.Lists;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.goods.GoodsBiz;
import com.houqin.biz.goodsunit.GoodsunitBiz;
import com.houqin.biz.outstock.OutStockBiz;
import com.houqin.biz.storage.StorageBiz;
import com.houqin.biz.storage.StorageTypeBiz;
import com.houqin.biz.storehouse.StoreHouseBiz;
import com.houqin.entity.goods.Goods;
import com.houqin.entity.goodsunit.Goodsunit;
import com.houqin.entity.outstock.OutStock;
import com.houqin.entity.outstock.OutStockDto;
import com.houqin.entity.storage.Storage;
import com.houqin.entity.storage.StorageType;
import com.houqin.entity.storehouse.StoreHouse;
import com.houqin.entity.sysuser.SysUser;
import com.houqin.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 出库管理
 *
 * @author ccl
 * @create 2016-12-20-15:13
 */
@Controller
@RequestMapping("/admin/houqin")
public class OutStockController extends BaseController {

    //出库管理
    private static final String outStockList = "/outstock/outStock_list";
    //物品出库
    private static final String add_StockList = "/outstock/add-outStock";
    private static final String add_StockList_batch = "/outstock/add-outStock-batch";
    //增加库存
    private static final String updateStock = "/outstock/updateStock";
    private static Logger logger = LoggerFactory.getLogger(OutStockController.class);
    @Autowired
    private OutStockBiz outStockBiz;
    @Autowired
    private StoreHouseBiz storeHouseBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private StorageBiz storageBiz;
    @Autowired
    private StorageTypeBiz storageTypeBiz;
    @Autowired
    private GoodsBiz goodsBiz;
    @Autowired
    private GoodsunitBiz goodsunitBiz;

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"outStock"})
    public void initOutStock(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("outStock.");
    }

    /**
     * @Description:查看出库
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-20
     */
    @RequestMapping("/queryOutStock")
    public String queryOutStock(HttpServletRequest request,
                                @ModelAttribute("pagination") Pagination pagination,
                                @ModelAttribute("outStock") OutStock outStock) {
        try {
            String whereSql = GenerateSqlUtil.getSql(outStock);
            pagination.setRequest(request);
            List<OutStock> outStockList = outStockBiz.find(pagination, whereSql);

            if (ObjectUtils.isNotNull(outStockList)) {
                for (OutStock o : outStockList) {
                    SysUser sysUser = baseHessianBiz.getSysUserById(o.getUserId());
                    String userName = "";
                    if (sysUser != null) {
                        userName = sysUser.getUserName();
                    }
                    o.setUserName(userName);
                    String goodsName = Optional.ofNullable(o)
                            .map(OutStock::getGoodsId)
                            .map(id -> {
                                return goodsBiz.findById(id);
                            })
                            .map(Goods::getName)
                            .orElse("");
                    o.setName(goodsName);
                    Goodsunit goodsunitBizById = goodsunitBiz.findById(o.getUnitId());
                    if (ObjectUtils.isNotNull(goodsunitBizById)) {
                        o.setUnitName(goodsunitBizById.getName());
                    }
                }
            }
            request.setAttribute("outStock", outStock);
            request.setAttribute("outStockList", outStockList);
        } catch (Exception e) {
            logger.info("-queryOutStock-", e);
            return this.setErrorPath(request, e);
        }
        return outStockList;
    }


    /**
     * 去批量出库
     *
     * @param request
     * @param ids
     * @return
     */
    @RequestMapping("/toAddOutStockBatch")
    public String toAddOutStock(HttpServletRequest request, @RequestParam("ids") String ids) {
        try {
            //查询出库类型
            List<StorageType> storageTypeList = storageTypeBiz.find(null, " 1=1");
            request.setAttribute("storageTypeList", storageTypeList);
            String[] split = ids.split(",");
            List<StoreHouse> list = Lists.newArrayList();
            for (String s : split) {
                StoreHouse storeHouse = storeHouseBiz.findById(Long.valueOf(s));
                Long storeId = storeHouse.getStorageId();
                if (ObjectUtils.isNotNull(storeId)) {
                    Storage storage = storageBiz.findById(storeId);
                    storeHouse.setStorageName(storage.getName());
                }
                list.add(storeHouse);
            }
            request.setAttribute("list", list);

        } catch (Exception e) {
            logger.info("-toAddOutStock-", e);
            return this.setErrorPath(request, e);
        }
        return add_StockList_batch;
    }

    /**
     * @Description:执行批量出库
     * @author: ccl
     * @Param: [request, outStock]
     * @Return: java.util.Map<java.lang.String                                                               ,                                                               java.lang.Object>
     * @Date: 2016-12-20
     */
    @RequestMapping("/addOutStockBatch")
    @ResponseBody
    public Map<String, Object> addOutStockBatch(HttpServletRequest request,
                                                @ModelAttribute("outStock") OutStock outStock,
                                                @RequestParam("storeHouseIdStr") String idStr,
                                                @RequestParam("storeHouseNumStr") String numStr) {
        try {
            //生成单号
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String number = "CK" + sdf.format(date);
            //出库人
            Long userId = SysUserUtils.getLoginSysUserId(request);
            outStock.setBillNum(number);
            outStock.setUserId(userId);
            String[] ids = idStr.split("#");
            String[] nums = numStr.split("#");
            if (ids.length == 0 || ids.length != nums.length) {
                return this.resultJson(ErrorCode.ERROR_DATA, "", null);
            }
            for (int i = 0; i < ids.length; i++) {
                OutStock osk = new OutStock();
                BeanUtils.copyProperties(outStock, osk);
                //查询库存的信息
                StoreHouse store = storeHouseBiz.findById(Long.valueOf(ids[i]));
                osk.setStorageId(store.getStorageId());
                osk.setTypeId(store.getTypeId());
                osk.setUnitId(store.getUnitId());
                osk.setPrice(store.getPrice());
                //将物品id放到出库记录里面
                osk.setGoodsId(store.getGoodsId());
                osk.setCode(store.getCode());
                osk.setNum(Long.valueOf(nums[i]));
                //查询数量
                Long oldNum = store.getNum();
                Long newNum = oldNum - Long.valueOf(nums[i]);
                if (newNum < 0) {
                    continue;
                }
                //更新库存
                StoreHouse storeHouse = new StoreHouse();
                storeHouse.setNum(newNum);
                storeHouse.setId(Long.valueOf(ids[i]));
                storeHouse.setUserId(SysUserUtils.getLoginSysUserId(request));
                storeHouseBiz.tx_editStoreHouse(osk, storeHouse);
            }
        } catch (Exception e) {
            logger.info("--", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);

        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }


    /**
     * @Description:去添加出库
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-20
     */
    @RequestMapping("/toAddOutStock/{id}")
    public String toAddOutStock(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            //查询出库类型
            List<StorageType> storageTypeList = storageTypeBiz.find(null, " 1=1");
            request.setAttribute("storageTypeList", storageTypeList);
            StoreHouse storeHouse = storeHouseBiz.findById(id);
            request.setAttribute("storeHouse", storeHouse);
            // 库房列表
            Long storeId = storeHouse.getStorageId();
            if (ObjectUtils.isNotNull(storeId)) {
                Storage storage = storageBiz.findById(storeId);
                request.setAttribute("storage", storage);
            } else {
                request.setAttribute("storage", new Storage());
            }
        } catch (Exception e) {
            logger.info("-toAddOutStock-", e);
            return this.setErrorPath(request, e);
        }
        return add_StockList;
    }

    /**
     * @Description:去添加入库
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-20
     */
    @RequestMapping("/toUpdateStorage/{id}")
    public String toUpdateStock(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            StoreHouse storeHouse = storeHouseBiz.findById(id);
            request.setAttribute("storeHouse", storeHouse);
            // 库房列表
            Long storeId = storeHouse.getStorageId();
            if (ObjectUtils.isNotNull(storeId)) {
                Storage storage = storageBiz.findById(storeId);
                request.setAttribute("storage", storage);
            } else {
                request.setAttribute("storage", new Storage());
            }
        } catch (Exception e) {
            logger.info("OutStockController.toUpdateStock", e);
            return this.setErrorPath(request, e);
        }
        return updateStock;
    }

    /**
     * @Description:添加保存
     * @author: ccl
     * @Param: [request, outStock]
     * @Return: java.util.Map<java.lang.String                                                               ,                                                               java.lang.Object>
     * @Date: 2016-12-20
     */
    @RequestMapping("/addSaveOutStock")
    @ResponseBody
    public Map<String, Object> addSaveOutStock(HttpServletRequest request,
                                               @ModelAttribute("outStock") OutStock outStock,
                                               @RequestParam("storeId") Long storeId) {
        try {
            //生成单号
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String number = "CK" + sdf.format(date);
            //出库人
            Long userId = SysUserUtils.getLoginSysUserId(request);
            outStock.setBillNum(number);
            outStock.setUserId(userId);
            //查询库存的信息
            StoreHouse store = storeHouseBiz.findById(storeId);
            //将物品id放到出库记录里面
            outStock.setGoodsId(store.getGoodsId());
            outStock.setCode(store.getCode());
            //查询数量
            Long oldNum = store.getNum();
            Long newNum = oldNum - outStock.getNum();
            //更新库存
            StoreHouse storeHouse = new StoreHouse();
            storeHouse.setNum(newNum);
            storeHouse.setId(storeId);
            storeHouse.setUserId(SysUserUtils.getLoginSysUserId(request));
            storeHouseBiz.tx_editStoreHouse(outStock, storeHouse);
        } catch (Exception e) {
            logger.info("--", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 查询出库记录详情
     */
    @RequestMapping("/detailOutStock")
    public ModelAndView detailOutStock(@RequestParam("id") Long outStockId) {
        ModelAndView mv = new ModelAndView("/outstock/detail_outStock");
        try {
            OutStockDto outStockDto = outStockBiz.getStockDtoById(outStockId);
            mv.addObject("outStock", outStockDto);
            // 查询库存
            Long storageId = outStockDto.getStorageId();
            if (ObjectUtils.isNotNull(storageId)) {
                Storage storage = storageBiz.findById(storageId);
                mv.addObject("storage", storage);
            }
            //查询出库类型
            List<StorageType> storageTypeList = storageTypeBiz.find(null, " 1=1");
            mv.addObject("storageTypeList", storageTypeList);
        } catch (Exception e) {
            logger.error("OutStockController--detailOutStock", e);
        }
        return mv;
    }

    /**
     * 根据id删除出库记录
     *
     * @param request
     * @return
     */
    @RequestMapping("/delOutStock")
    @ResponseBody
    public Map<String, Object> delOutStock(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            outStockBiz.deleteById(Long.parseLong(id));
        } catch (Exception e) {
            logger.info("OutStockController--delOutStock", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * @Description:去修改
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2018-11-01
     */
    @RequestMapping("/toUpdateOutStock")
    public ModelAndView toUpdateOutStock(HttpServletRequest request, @RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/outstock/update_outStock");
        try {
            OutStockDto outStockDto = outStockBiz.getStockDtoById(id);
            modelAndView.addObject("outStock", outStockDto);
            // 查询库存
            Long storageId = outStockDto.getStorageId();
            if (ObjectUtils.isNotNull(storageId)) {
                Storage storage = storageBiz.findById(storageId);
                modelAndView.addObject("storage", storage);
            }
        } catch (Exception e) {
            logger.error("OutStockController.toUpdateOutStock", e);
        }
        return modelAndView;
    }

    /**
     * @Description:修改
     * @Param: [request, goods]
     * @Return: java.lang.String
     * @Date: 2018-11-01
     */
    @RequestMapping("/updateOutStock")
    @ResponseBody
    public Map<String, Object> updateOutStock(HttpServletRequest request, @ModelAttribute("outStock") OutStock outStock) {
        try {
            outStockBiz.update(outStock);
        } catch (Exception e) {
            logger.error("OutStockController.updateOutStock", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

}
