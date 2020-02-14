package com.houqin.entity.mess;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 食堂拓展表
 *
 * @author ccl
 * @create 2016-12-22-19:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MessDto extends Mess {

    private String typeName;//类型名称
    private String dinerName; //用餐人类型
}
