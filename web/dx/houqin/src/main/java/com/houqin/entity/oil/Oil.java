package com.houqin.entity.oil;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 柴油表
 * Created by Administrator on 2017/6/13 0013.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Oil extends BaseEntity{
    /**
     * 录入人id
     */
    private Long userId;
    /**
     * 用油升数
     */
    private Double litre;
    /**
     * 备注
     */
    private String context;

    /**
     * 外接用能管理
     * 铁塔IRON 小卖部BUFFET 公园PARK
     */
    private String type;

    /**
     * 🈶️类型   1是汽油  2是柴油
     */
    private Long oilType;
    /**
     *  作用于  1是绿化  2是发电  3是其他
     */
    private Long purpose;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 确认
     */
    private Long affirm;


}
