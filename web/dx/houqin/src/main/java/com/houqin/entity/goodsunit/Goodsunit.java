package com.houqin.entity.goodsunit;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 物品单位
 *
 * @author ccl
 * @create 2016-12-19-13:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goodsunit extends BaseEntity{
    /**单位名称*/
    private String name;
}
