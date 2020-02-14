package com.jiaowu.entity.repairType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 维修类型
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RepairTypeDto extends RepairType {
    /**
     * 父级名称
     */
    private String parentName;
}
