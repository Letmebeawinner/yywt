package com.houqin.entity.lot;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 库存食材的批次
 *
 * @author YaoZhen
 * @create 10-24, 09:44, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Lot extends BaseEntity {
    /**
     * 货物id
     */
    private Integer messStockId;

    /**
     * 本批次编号
     */
    private Long lotNumber;

    /**
     * 本批次价格
     */
    private BigDecimal lotPrice;

    /**
     * 本批次数量
     */
    private Long lotAmount;
}
