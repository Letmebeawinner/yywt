package com.houqin.entity.electricity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 电表拓展表
 *
 * @author ccl
 * @create 2017-01-23-17:40
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ElectricityDto extends Electricity {

    private String userName;//用户人
//    private Double price;//单价
    private String secTypeName;//二级用电区域列表
}
