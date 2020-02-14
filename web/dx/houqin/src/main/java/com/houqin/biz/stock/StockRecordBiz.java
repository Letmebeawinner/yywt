package com.houqin.biz.stock;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.goods.GoodsBiz;
import com.houqin.biz.goodsunit.GoodsunitBiz;
import com.houqin.biz.storage.StorageBiz;
import com.houqin.dao.stock.StockRecordDao;
import com.houqin.entity.goods.Goods;
import com.houqin.entity.goodsunit.Goodsunit;
import com.houqin.entity.stock.StockRecord;
import com.houqin.entity.stock.StockRecordDto;
import com.houqin.entity.storage.Storage;
import com.houqin.entity.sysuser.SysUser;
import com.houqin.utils.GenerateSqlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 出入库service
 *
 * @author lzh
 * @create 2018-01-08-17:35
 */
@Service
public class StockRecordBiz extends BaseBiz<StockRecord, StockRecordDao>{

    @Autowired
    private GoodsBiz goodsBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private StorageBiz storageBiz;
    @Autowired
    private GoodsunitBiz goodsunitBiz;

    public List<StockRecordDto> getStockRecordDto(Pagination pagination, StockRecord stockRecord) {
        List<StockRecord> stockRecords = this.find(pagination, GenerateSqlUtil.getSql(stockRecord));
        if (stockRecords != null && stockRecords.size() > 0) {
            return stockRecords.stream()
                .map(stock -> {
                    return convertToStockRecordDto(stock);
                })
                .collect(Collectors.toList());
        }
        return null;
    }


    private StockRecordDto convertToStockRecordDto(StockRecord stockRecord) {
        StockRecordDto stockRecordDto = new StockRecordDto();
        BeanUtils.copyProperties(stockRecord, stockRecordDto);
        Goods goods = goodsBiz.findById(stockRecord.getGoodsId());
        String goodsName = Optional.ofNullable(goods)
                .map(Goods::getName)
                .orElse("");
        String goodsCode = Optional.ofNullable(goods)
                .map(Goods::getCode)
                .orElse("");
        stockRecordDto.setGoodsName(goodsName);
        stockRecordDto.setGoodsCode(goodsCode);

        String operatorName = Optional.ofNullable(stockRecord)
                .map(StockRecord::getOperatorId)
                .map(id -> {
                    SysUser sysUser = baseHessianBiz.getSysUserById(id);
                    return sysUser.getUserName();
                })
                .orElse("");
        stockRecordDto.setOperatorName(operatorName);

        //库房名
        String stockHouseName = Optional.ofNullable(stockRecord)
                .map(StockRecord::getStoreHouseId)
                .map(id -> {
                    Storage storage = storageBiz.findById(id);
                    return storage;
                })
                .map(Storage::getName)
                .orElse("");
        stockRecordDto.setStockHouseName(stockHouseName);

        //单位名
        String unitName = Optional.of(stockRecord)
                .map(StockRecord::getUnitId)
                .map(id -> {
                    Goodsunit goodsunit = goodsunitBiz.findById(id);
                    return goodsunit;
                })
                .map(Goodsunit::getName)
                .orElse("");
        stockRecordDto.setUnitName(unitName);

        return stockRecordDto;
    }

    /**
     * 通过id获取入库信息
     * @param id
     * @return
     */
    public StockRecordDto getStockDtoById(Long id) {
        StockRecord stockRecord = this.findById(id);
        if (stockRecord == null) {
            return null;
        }
        StockRecordDto stockRecordDto = convertToStockRecordDto(stockRecord);
        return stockRecordDto;
    }

}
