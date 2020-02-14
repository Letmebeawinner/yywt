package com.houqin.entity.property;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class PropertyClean extends BaseEntity {

    private Long propertyId;//资产id

    private Long propertyTypeId;//资产类型id

    private Date cleanTime;//清理时间

    private String codeNumber;//清理单号

    private Long userId;//用户

    private String description;//清理说明

    private String typeName;//类型名称

    private String userName;//用户名

    private String propertyName;//资产名称

    private String unit;//规格

    private BigDecimal price;//金额



}
