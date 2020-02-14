package com.base.entity.unit;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Unit extends BaseEntity{

    private String name;

    private Long sort;

}
