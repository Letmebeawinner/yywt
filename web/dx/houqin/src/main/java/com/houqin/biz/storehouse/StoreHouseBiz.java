package com.houqin.biz.storehouse;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.goods.GoodsBiz;
import com.houqin.biz.goodstype.GoodstypeBiz;
import com.houqin.biz.goodsunit.GoodsunitBiz;
import com.houqin.biz.outstock.OutStockBiz;
import com.houqin.biz.stock.StockRecordBiz;
import com.houqin.biz.storage.StorageBiz;
import com.houqin.dao.storehouse.StoreHouseDao;
import com.houqin.entity.goods.Goods;
import com.houqin.entity.goodstype.Goodstype;
import com.houqin.entity.goodsunit.Goodsunit;
import com.houqin.entity.outstock.OutStock;
import com.houqin.entity.stock.StockRecord;
import com.houqin.entity.storage.Storage;
import com.houqin.entity.storehouse.StoreHouse;
import com.houqin.entity.storehouse.StoreHouseDto;
import com.houqin.entity.sysuser.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 库房管理
 *
 * @author ccl
 * @create 2016-12-20-12:36
 */
@Service
public class StoreHouseBiz extends BaseBiz<StoreHouse, StoreHouseDao> {

    @Autowired
    private GoodsBiz goodsBiz;
    @Autowired
    private GoodsunitBiz goodsunitBiz;
    @Autowired
    private GoodstypeBiz goodstypeBiz;

    @Autowired
    private OutStockBiz outStockBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private StorageBiz storageBiz;
    @Autowired
    private StockRecordBiz stockRecordBiz;

    /**
     * 保证如何和添加库存一致
     *
     * @param goods
     * @param storeHouse
     */
    public void tx_addStoreHouse(Goods goods, StoreHouse storeHouse) {
        SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
        storeHouse.setSysUserId(goods.getUserId());
        //保存物品
        goodsBiz.save(goods);
        //将物品存入库存
        storeHouse.setGoodsId(goods.getId());
        this.save(storeHouse);
        StockRecord stockRecord = new StockRecord();
        //库存操作日志
        stockRecord.setGoodsId(goods.getId());
        stockRecord.setOperatorId(storeHouse.getSysUserId());
        stockRecord.setNum(goods.getNum());
        stockRecord.setUnitId(storeHouse.getUnitId());
        stockRecord.setStoreHouseId(storeHouse.getStorageId());
        if (goods.getPrice() != null) {
            stockRecord.setPrice(goods.getPrice());
        } else {
            stockRecord.setPrice(new BigDecimal(0));
        }

        stockRecord.setBillNum("RK" + smf.format(new Date()));
        //入库
        stockRecord.setType(1);
        stockRecordBiz.save(stockRecord);

    }


    /**
     * @Description:修改 (修改库存, 增加库存)
     * @author: ccl
     * @Param: [outStock, storeHouse]
     * @Return: void
     * @Date: 2016-12-20
     */
    public void tx_editStoreHouse(OutStock outStock, StoreHouse storeHouse) {
        //增加出库记录
        outStockBiz.save(outStock);
        //修改库房库存
        this.update(storeHouse);
    }

    /**
     * 修改库存，增加库存操作记录
     *
     * @param goods        物品信息
     * @param storeHouseId 库存信息
     */
    public void tx_updateStore(Goods goods, Long storeHouseId, String note) {
        SimpleDateFormat smf = new SimpleDateFormat("yyyyMMddHHmmss");
        StockRecord stockRecord = new StockRecord();
        //库存操作日志
        StoreHouse storeHouse = this.findById(storeHouseId);
        stockRecord.setGoodsId(storeHouse.getGoodsId());
        stockRecord.setOperatorId(storeHouse.getSysUserId());
        //增加库存
        stockRecord.setNum(goods.getNum());
        stockRecord.setUnitId(storeHouse.getUnitId());
        stockRecord.setStoreHouseId(storeHouse.getStorageId());
        stockRecord.setPrice(goods.getPrice());
        stockRecord.setBillNum("RK" + smf.format(new Date()));
        stockRecord.setNote(note);
        //入库
        stockRecord.setType(1);


        storeHouse.setNum(goods.getNum() + storeHouse.getNum());
        //更新库存
        this.update(storeHouse);
        stockRecordBiz.save(stockRecord);
    }


    public void tx_deleteStoreByGoodsId(Long goodsId) {
        if (goodsId != null) {
            goodsBiz.deleteById(goodsId);
            List<StoreHouse> storeHouseList = this.findStoreByGoodsId(goodsId);
            if (ObjectUtils.isNotNull(storeHouseList)) {
                for (StoreHouse storeHouse : storeHouseList) {
                    deleteById(storeHouse.getId());
                }
            }

        }

    }


    public List<StoreHouse> findByCode(String code) {
        List<StoreHouse> storeList = this.find(null, "code=" + code);
        return storeList;
    }

    public List<StoreHouse> findStoreByGoodsId(Long id) {
        List<StoreHouse> storeList = this.find(null, "goodsId = " + id);
        return storeList;
    }

    public List<StoreHouseDto> getAllStoreDto(@ModelAttribute("pagination") Pagination pagination, String whereSql) {
        List<StoreHouse> storeHouseList = this.find(pagination, whereSql);
        List<StoreHouseDto> list = new ArrayList<StoreHouseDto>();
        if (storeHouseList != null && storeHouseList.size() > 0) {
            for (StoreHouse storeHouse : storeHouseList) {
                StoreHouseDto dto = new StoreHouseDto();
                Goodstype goodstype = goodstypeBiz.findById(storeHouse.getTypeId());

                Goodsunit goodsunit = goodsunitBiz.findById(storeHouse.getUnitId());
                dto.setId(storeHouse.getId());
                dto.setName(storeHouse.getName());
                dto.setStorageNum(storeHouse.getStorageNum());
                if (ObjectUtils.isNotNull(goodstype)) {
                    dto.setTypeName(goodstype.getTypeName());
                } else {
                    dto.setTypeName("");
                }
                if (ObjectUtils.isNotNull(goodsunit)) {
                    dto.setUnitName(goodsunit.getName());
                } else {
                    dto.setUnitName("");
                }

                SysUser sysUser = baseHessianBiz.getSysUserById(storeHouse.getSysUserId());
                if (ObjectUtils.isNotNull(sysUser)) {
                    dto.setUserName(sysUser.getUserName());
                }

                Storage storage = storageBiz.findById(storeHouse.getStorageId());
                if (ObjectUtils.isNotNull(storage)) {
                    dto.setStorageName(storage.getName());
                }
                dto.setPrice(storeHouse.getPrice());
                dto.setName(storeHouse.getName());
                dto.setNum(storeHouse.getNum());
                dto.setCode(storeHouse.getCode());
                dto.setCreateTime(storeHouse.getCreateTime());
                Goods goods = goodsBiz.findById(storeHouse.getGoodsId());
                //如果为空则goodsName为"";
                String goodsName = Optional.ofNullable(goods)
                        .map(Goods::getName)
                        .orElse("");
                dto.setGoodsId(storeHouse.getGoodsId());
                dto.setGoodsName(goodsName);
                Long goodsId = storeHouse.getGoodsId();
                Goods byId = goodsBiz.findById(goodsId);
                if(ObjectUtils.isNotNull(byId)){
                    String model = byId.getModel();
                    if(!StringUtils.isTrimEmpty(model)){
                        dto.setModel(model);
                    }
                }
                list.add(dto);
            }
        }
        return list;
    }


}
