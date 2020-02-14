package com.houqin.entity.natural;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NaturalType extends BaseEntity {

    private String type;
}
