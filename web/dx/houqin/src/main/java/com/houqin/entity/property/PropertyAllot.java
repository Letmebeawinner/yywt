package com.houqin.entity.property;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class PropertyAllot extends BaseEntity {

    private String name;//调拨人

    private Date allotTime;//调入时间

    private Long handleId;//处理人id

    private Date handleTime;//调拨时间

    private  String allotContext;//调拨说明

    private Long propertyId;//资产id

    private Long allotId;//调拨人id

    private Long propertyTypeId;//资产类型id



    private String typeName;//类型名称

    private String userName;//用户名

    private String propertyName;//资产名称


}
