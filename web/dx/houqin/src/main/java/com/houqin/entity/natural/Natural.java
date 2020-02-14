package com.houqin.entity.natural;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 天燃气
 *
 * @author ccl
 * @create 2017-05-18-17:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Natural extends BaseEntity {

    private BigDecimal amount;//用量

    private BigDecimal price;//单价

    private String context;//内容

    private Long userId;//录入人姓名

    private Long affirm;//会否确认

    private Long type;//用气区域
    
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
     * 气费 = （本期读数－上期读数）×单价
     */
    private BigDecimal eleFee;
}
