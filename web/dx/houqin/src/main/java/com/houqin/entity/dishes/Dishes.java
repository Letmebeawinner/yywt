package com.houqin.entity.dishes;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 菜品管理
 * Created by Administrator on 2016/12/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Dishes extends BaseEntity {
    /**
     * 食堂id
     */
    private Long messId;
    /**
     * 早餐
     */
    private String breakfast;
    /**
     * 早餐价格
     */
    private BigDecimal breakprice;
    /**
     * 午餐
     */
    private String lunch;
    /**
     * 午餐价格
     */
    private BigDecimal lunchprice;
    /**
     * 晚餐
     */
    private String dinner;
    /**
     * 晚餐价格
     */
    private BigDecimal dinnerprice;
    /**
     * 使用时间
     */
    private Date usetime;

    /**
     * 图片
     */
    private String imagePath;
    /**
     * 描述
     */
    private String description;
    /**
     * 农残检测
     */
    private String pesticideResidues;
    /**
     * 菜品检样
     */
    private String retentionSamples;

}
