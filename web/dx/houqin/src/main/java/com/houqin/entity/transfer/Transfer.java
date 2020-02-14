package com.houqin.entity.transfer;

import com.a_268.base.core.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 调拨走向
 *
 * @author YaoZhen
 * @date 01-25, 18:29, 2018.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Transfer extends BaseEntity {

    /**
     * 资产Id
     * @see com.houqin.entity.propertymessage.PropertyMessage
     */
    Long propertyMessageId;

    /**
     * 操作记录
     */
    String operationRecord;
}
