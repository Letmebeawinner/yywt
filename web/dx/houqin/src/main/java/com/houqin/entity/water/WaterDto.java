package com.houqin.entity.water;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 水拓展表
 *
 * @author ccl
 * @create 2017-01-23-18:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WaterDto extends Water {

    private String userName;//用户名
//    private Double price;//单价
    private String waterTypeName;//用水区域

}
