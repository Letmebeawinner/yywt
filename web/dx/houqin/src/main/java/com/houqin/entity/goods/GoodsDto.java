package com.houqin.entity.goods;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品的拓展表
 *
 * @author ccl
 * @create 2016-12-19-18:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsDto extends Goods {

    /**类型名称*/
    private String typeName;

    /**单位名称*/
    private String unitName;

    /**库房名称*/
    private String storageName;
}
