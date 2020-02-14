package com.houqin.entity.water;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 水表
 * Created by Administrator on 2016/12/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Water extends BaseEntity {
    /**
     * 录入人id
     */
    private Long userId;

    /**
     * 用水类型
     */
    private Long waterType;

    /**
     * 月份
     */
    private String monthTime;

    /**
     * 上期读数
     */
    private Double preRead;

    /**
     * 本期读数
     */
    private Double curRead;

    /**
     * 用水吨数
     */
    private Double tunnage;
    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 水费 = （本期读数－上期读数）×单价
     */
    private BigDecimal eleFee;
    /**
     * 备注
     */
    private String context;
    /**
     * 会否确认
     */
    private Long affirm;

    /**
     * 获得记录的年份
     *
     * @return 年份
     * @since 2017-03-02
     */
    public Integer getYear() {
        return getCreateTime().getYear() + 1900;
    }

    /**
     * 获得记录的月份
     *
     * @return 月份
     * @since 2017-03-02
     */
    public Integer getMonth() {
        return getCreateTime().getMonth() + 1;
    }
}
