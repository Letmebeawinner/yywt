package com.houqin.entity.goodstype;

import com.a_268.base.core.BaseEntity;
import com.houqin.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 物品分类
 * Created by Administrator on 2016/12/15.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goodstype extends BaseEntity {
    /**
     * 物品分类名称
     */
    @Like
    private String typeName;
    /**
     * 排序
     */
    private Long sort;

}
