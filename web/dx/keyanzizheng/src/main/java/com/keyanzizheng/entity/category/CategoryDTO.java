package com.keyanzizheng.entity.category;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 增加形式
 * <p><p>
 *
 * @author YaoZhen
 * @date 12-20, 18:04, 2017.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CategoryDTO extends Category {
    /**
     * 所属形式的名称
     */
    private String resultFormName;
}
