package com.houqin.entity.electricityType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 展示用电区域饼状图
 *
 * @author YaoZhen
 * @create 10-28, 11:52, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ElectricityTypeVO {
    /**
     * 区域名称
     */
    private String name;
    /**
     * 用电量
     */
    private Double num;

    public ElectricityTypeVO(String name, Double num) {
        this.name = name;
        this.num = num;
    }
}
