package com.houqin.entity.stock;

import com.a_268.base.core.BaseEntity;
import com.houqin.annotation.Like;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 库存出入库记录
 *
 * @author lzh
 * @create 2018-01-08-17:32
 */
@Data
public class StockRecord extends BaseEntity{
    //货物id
    private Long goodsId;
    //出入库类型 1 出库 2 入库
    private Integer type;
    // 出入库数量
    private Long num;
    //操作人
    private Long operatorId;
    //单位
    private Long unitId;
    //库房id
    private Long storeHouseId;
    //价格
    private BigDecimal price;
    //入库编号
    @Like
    private String billNum;
    //备注
    private String note;
}
