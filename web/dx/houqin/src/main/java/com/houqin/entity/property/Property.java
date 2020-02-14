package com.houqin.entity.property;

import com.a_268.base.core.BaseEntity;
import com.houqin.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资产管理
 * Created by Administrator on 2016/12/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Property extends BaseEntity{
    /**
     * 资产类型名称
     */
    @Like
    private String typeName;
    /**
     * 排序
     */
    private Long sort;
}
