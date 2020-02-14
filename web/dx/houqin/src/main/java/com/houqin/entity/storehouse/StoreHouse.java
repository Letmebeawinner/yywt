package com.houqin.entity.storehouse;

import com.a_268.base.core.BaseEntity;
import com.houqin.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 入库管理
 *
 * @author ccl
 * @create 2016-12-20-11:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreHouse extends BaseEntity {

    /*** 商品分类*/
    private Long typeId;
    /*** 商品单位*/
    private Long unitId;
    /**
     * 物品名称
     */
    @Like
    private String name;
    /**
     * 编码
     */
    private String code;
    /*** 单价*/
    private BigDecimal price;
    /*** 数量*/
    private Long num;
    /*** 经办人*/
    private Long sysUserId;
    /*** 出库人*/
    private Long userId;

    /*** 库房id */
    private Long storageId;
    
    /*** 库房名称 **/
    
    private String storageName;

    /**
     * 入库单号
     */
    @Like
    private String storageNum;
    //物品id
    private Long goodsId;

}
