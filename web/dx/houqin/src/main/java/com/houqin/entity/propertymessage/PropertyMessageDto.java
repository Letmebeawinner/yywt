package com.houqin.entity.propertymessage;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资产信息扩展表
 * Created by Administrator on 2016/12/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PropertyMessageDto extends PropertyMessage{
    /**
     * 资产管理名称
     */
    private String propertyTypeName;

    /**
     * 系统用户名称
     */
    private String sysUserName;

    /**
     * 库房名称
     */
    private String wareHouseName;
}
