package com.houqin.entity.food;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 食材分类
 *
 * @author wanghailong
 * @create 2017-07-25-上午 10:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FoodType extends BaseEntity {
    private String content;//内容
}
