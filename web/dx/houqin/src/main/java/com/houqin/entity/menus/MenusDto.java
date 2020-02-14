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
public class MenusDto extends Menus {
    /**
     * 菜单类型名称
     */
    private String menuTypeName;

}
