package com.jiaowu.entity.repairType;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 维修类型
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RepairType extends BaseEntity {

    /**
     * 类型名称
     */
    private String name;
    /**
     * 排序
     */
    private Long sort;
    /**
     * 父级id
     */
    private Long parentId;

}
