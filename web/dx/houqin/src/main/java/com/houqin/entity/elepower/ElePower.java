package com.houqin.entity.elepower;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 供电局抄表数
 *
 * @author YaoZhen
 * @date 05-24, 09:10, 2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ElePower extends BaseEntity {

    /**
     * 录入人id
     */
    private Long userId;

    /**
     * 录入人Name
     */
    private String userName;

    /**
     * 用电区域类型
     */
    private Long typeId;

    /**
     * 二级用电区域类型
     */
    private Long secTypeId;


    /**
     * 抄表时间
     */
    private String meteTime;

    /**
     * 上期读数
     */
    private Double previousDegrees;

    /**
     * 本期读数
     */
    private Double currentDegrees;

    /**
     * 用电度数 = (本期读数 - 上期读数) * 倍率
     */
    private Double degrees;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 倍率
     */
    private Integer rate;

    /**
     * 电费 = （本期读数－上期读数）×倍率 ×单价
     */
    private BigDecimal eleFee;

    /**
     * 备注
     */
    private String context;
    /**
     * 月份
     */
    private String monthTime;
}
