package com.houqin.entity.menus;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 菜单类型管理
 *
 * @author ccl
 * @create 2016-12-14-12:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Menus extends BaseEntity {
    /**
     * 菜单名称
     */
    private String title;
    /**
     * 菜单价格
     */
    private BigDecimal price;
    /**
     * 菜单id
     */
    private Long typeId;

}
