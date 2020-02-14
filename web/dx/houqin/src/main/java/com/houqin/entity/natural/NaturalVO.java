package com.houqin.entity.natural;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 展示录入人
 *
 * @author YaoZhen
 * @create 10-26, 15:11, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NaturalVO extends Natural {
    /**
     * 录入人姓名
     */
    private String name;

    private String typeName;
}
