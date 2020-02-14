package com.houqin.entity.electricityType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YaoZhen
 * @create 10-28, 11:40, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ElectricityTypeDTO {
    /**
     * 电量的年份
     */
    private Integer year;

    /**
     * 用电区域的id
     */
    private Long typeId;

    /**
     * 用电区域的名称
     */
    private String typeName;

    /**
     * 该区域的用电量
     */
    private Double energyUsed;
}
