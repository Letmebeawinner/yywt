package com.houqin.entity.water;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WaterType extends BaseEntity {

    private String type;

}
