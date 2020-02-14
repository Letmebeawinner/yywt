package com.houqin.entity.property;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class PropertyReceive extends BaseEntity{


    private Long userId;//领用人

    private Long propertyId;//资产id

    private Long propertyTypeId;//资产类型id

    private String codeNumber;//领用单号

    private Date receiveTime;//接受时间

    private String receiveArea;//领用区域

    private String receivePlace;//领用存放地点

    private String description;//备注

    private Date beforeStockTime;//预退还时间

    private String typeName;//类型名称

    private String userName;//用户名

    private String propertyName;//资产名称

    private String unit;//规格

    private BigDecimal price;//金额


}
