package com.houqin.entity.storage;

import com.a_268.base.core.BaseEntity;
import com.houqin.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StorageType extends BaseEntity{

    @Like
    private String name;

    private Long sort;

}
