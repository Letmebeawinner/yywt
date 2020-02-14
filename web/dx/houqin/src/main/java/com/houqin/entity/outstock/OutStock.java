package com.houqin.entity.outstock;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 出库表
 *
 * @author ccl
 * @create 2016-12-20-14:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OutStock extends BaseEntity {
    /**
     * 经办人id
     */
    private Long userId;
    /**
     * 类型
     */
    private Long typeId;
    /**
     * 单位
     */
    private Long unitId;
    /**
     * 名称
     */
    private String name;
    /**
     * 商品编号
     */
    private String code;
    /**
     * 出库数量
     */
    private Long num;
    /**
     * 单据号
     */
    private String billNum;
    /**
     * 出库价格
     */
    private BigDecimal price;
    /**
     * 来源
     */
    private String source;
    /**
     * 备注
     */
    private String context;
    /***
     * 0是转让；1是捐赠*/
    private Long outStockType;

    private String userName;

    /**
     * 库房id
     */
    private Long storageId;


    private String receiver;//领用人
    //物品id
    private Long goodsId;

    /**
     * 单位名称
     */
    private String unitName;
}
