package com.houqin.entity.lot;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 出库记录
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LotRecord extends BaseEntity {

    /**
     * 批次id
     */
    private Long lotId;

    /**
     * 出库量
     */
    private Long amount;

    /**
     * 经办人
     */
    private String manager;

    /**
     * 出库人
     */
    private String outboundPerson;

    /**
     * 接受人
     */
    private String receiver;
}
