package com.houqin.entity.dishesType;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜品类型管理
 * Created by Administrator on 2016/12/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DishesType extends BaseEntity {
    /**
     * 菜品名称
     */
    private String name;
    /**
     * 菜品排序
     */
    private Long sort;

}
