package com.houqin.entity.consumption;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 存储人员每天的消费信息
 *
 * @author YaoZhen
 * @create 10-21, 16:24, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Consumption extends BaseEntity {

    /**
     * 人员编号
     */
    private String basePerId;

    /**
     * 日期
     */
    private String costDate;

    /**
     * 消费记录数
     */
    private Integer costCount;

    /**
     * 消费累计金额
     */
    private BigDecimal costSumMoney;
}
