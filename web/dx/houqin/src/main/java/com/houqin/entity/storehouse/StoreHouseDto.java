package com.houqin.entity.storehouse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 库存表
 *
 * @author ccl
 * @create 2016-12-20-14:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreHouseDto extends StoreHouse {
    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 库房名称
     */
    private String storageName;
    //货物id
    private Long goodsId;
    //物品名称
    private String goodsName;

    /**
     * 规格型号
     */
    private String model;
}
