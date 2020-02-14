package com.houqin.entity.propertymessage;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper =false)
public class InsidePropertyMessage extends BaseEntity{

    //名称
    private String name;

    //库房id
    private Long storageId;

    //用户id
    private Long userId;

    //来源id
    private Long source;

    //资产类型id
    private Long propertyId;

    //规格
    private String product;

    //价格
    private BigDecimal price;

    //总数
    private Integer amount;

    //单位
    private String unit;

    //入库单号
    private String insideNumber;


    private String storageName;

    private String userName;

    private String propertyType;


}
