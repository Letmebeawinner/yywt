package com.houqin.entity.price;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 价格表
 *
 * @author lzh
 * @create 2017-02-27-16:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Price extends BaseEntity {
    private String name;//名称
    private String type;//类型
    private Double inwardPrice;//内部单价
    private Double exteriorPrice;//外部单价
}
