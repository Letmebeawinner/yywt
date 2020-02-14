package com.houqin.entity.oil;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by WangHaiLong on 2017/12/28 0028.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OilUse extends BaseEntity {
    private String name;//名称
    private Integer sort; //排序
}
