package com.houqin.entity.property;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class PropertyBorrow extends BaseEntity{

    private Long propertyTypeId;//资产类型id

    private Long propertyId;//资产id

    private Long userId;//借用人

    private String codeNumber;//借用单号

    private Date borrowTime;//借用时间

    private Date returnTime;//归还时间

    private Date beforereturnTime;//预归还时间

    private String description;//说明

    private String typeName;//类型名称

    private String userName;//用户名

    private String propertyName;//资产名称

    private String unit;//规格

    private BigDecimal price;//金额


}
