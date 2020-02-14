package com.houqin.entity.electricityType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 列表展示
 *
 * @author YaoZhen
 * @date 06-25, 16:08, 2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EleSecTypeVO extends EleSecType {
    /**
     * 一级类型的名称
     */
    private String eleTypeName;
}
