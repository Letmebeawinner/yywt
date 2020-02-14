package com.houqin.entity.propertymessage;

import com.a_268.base.core.BaseEntity;
import com.houqin.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PropertyMessage extends BaseEntity {

    /**
     * 资产信息名称
     */
    @Like
    private String name;

    /**
     * 资产管理id
     */
    private Long propertyId;

    /**
     * 备注
     */
    private String context;

    /**
     * 规格
     */
    private String product;

    /**
     * 数量
     * 状态为未使用时 可以出库
     */
    private Integer amount;

    /**
     * 单位
     */
    private String unit;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 购入时间
     */
    private Date buyTime;

    /**
     * 使用期限
     */
    private Date liftTime;

    /**
     * 来源 1购入 2捐赠
     */
    private Long source;

    /**
     * 管理员
     */
    private Long userId;

    /**
     * 库房id
     */
    private Long storageId;

    //来源
    private String propertySource;


}
