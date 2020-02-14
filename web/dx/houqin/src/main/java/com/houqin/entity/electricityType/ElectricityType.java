package com.houqin.entity.electricityType;

import com.a_268.base.core.BaseEntity;
import com.houqin.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用电区域类型
 * Created by Administrator on 2017/6/14 0014.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ElectricityType extends BaseEntity{
    /**
     * 区域类型
     */
    @Like
    private String type;
}
