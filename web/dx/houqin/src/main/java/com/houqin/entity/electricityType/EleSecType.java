package com.houqin.entity.electricityType;

import com.a_268.base.core.BaseEntity;
import com.houqin.annotation.Like;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用电区域二级
 *
 * @author YaoZhen
 * @date 06-25, 10:07, 2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EleSecType extends BaseEntity {
    /**
     * 父级ID
     */
    Long eleTypeId;

    /**
     * 二级名称
     */
    @Like
    String typeName;
}
