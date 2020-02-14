package com.houqin.entity.repairType;

import com.a_268.base.core.BaseEntity;
import com.houqin.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 维修类型
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RepairTypeDto extends RepairType {
    /**
     * 父级名称
     */
    private String parentName;
}
