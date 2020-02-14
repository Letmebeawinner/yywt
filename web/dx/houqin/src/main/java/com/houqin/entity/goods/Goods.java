package com.houqin.entity.goods;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品管理
 *
 * @author ccl
 * @create 2016-12-19-12:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goods extends BaseEntity {
    /*** 商品分类*/
    private Long typeId;
    /*** 商品单位*/
    private Long unitId;
    /**
     * 录入人
     */
    private Long userId;
    /**
     * 物品名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 商品编号
     */
    private String model;
    /*** 单价*/
    private BigDecimal price;
    /*** 数量*/
    private Long num;
    /*** 编码*/
    private String describes;

    /**
     * 库房id
     */
    private Long storageId;

}
