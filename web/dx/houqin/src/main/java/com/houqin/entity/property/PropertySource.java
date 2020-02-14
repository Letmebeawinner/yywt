package com.houqin.entity.property;

import com.a_268.base.core.BaseEntity;
import com.houqin.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PropertySource extends BaseEntity{

    @Like
    private String name;

    private Integer sort;


}
