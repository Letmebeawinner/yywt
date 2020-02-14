package com.houqin.entity.electricity;

import com.a_268.base.core.BaseEntity;
import com.houqin.entity.electricityType.EleSecType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 电表
 * Created by Administrator on 2016/12/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Electricity extends BaseEntity {
    /**
     * 录入人id
     */
    private Long userId;
    /**
     * 用电度数
     */
    private Double degrees;
    /**
     * 用电区域类型
     */
    private Long typeId;
    /**
     * 二级用电区域ID
     *
     * @see EleSecType
     */
    private Long secTypeId;
    /**
     * 备注
     */
    private String context;
    /**
     * 会否确认
     */
    private Long affirm;
    /**
     * 上期读数
     */
    private Double previousDegrees;

    /**
     * 本期读数
     */
    private Double currentDegrees;
    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 倍率
     */
    private Integer rate;

    /**
     * 电费 = （本期读数－上期读数）×单价×倍率
     */
    private BigDecimal eleFee;
    //年月
    private String monthTime;

    /**
     * 获得创建时间的年份
     *
     * @return 年份
     */
    public Integer getYear() {
        return getCreateTime().getYear() + 1900;
    }

    public Integer getMonth() {
        return getCreateTime().getMonth() + 1;
    }

    
}
