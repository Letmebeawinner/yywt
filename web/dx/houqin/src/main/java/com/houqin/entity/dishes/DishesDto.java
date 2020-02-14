package com.houqin.entity.dishes;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜品拓展类
 * Created by Administrator on 2016/12/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DishesDto extends Dishes{
    /**
     * 菜品名称
     */
    private String dishesTypeName;
}
