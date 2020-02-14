package com.houqin.entity.propertymessage;


import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 出库
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OutOfPropertyMessage extends BaseEntity{

    /**
     * 出库编号
     */
    private String serialNo;

    /**
     * 出库物品名称
     */
    private String outboundItemName;

    /**
     * 出库物品id
     */
    private Long outboundItemId;

    /**
     * 出库数量
     */
    private Integer outboundNumber;

    /**
     * 经办人
     */
    private String manager;

    /**
     * 出库人
     */
    private String outboundPerson;

    /**
     * 出库备注
     */
    private String context;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 库房名称
     */
    private String storageName;
}
