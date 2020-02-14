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
public class RepairType extends BaseEntity {

    /**类型名称*/
    @Like
    private String name;
    /**排序*/
    private Long sort;
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 作用区域
     */
    private Long functionType;


}
