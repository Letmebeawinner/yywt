package com.houqin.entity.menuType;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单类型管理
 *
 * @author ccl
 * @create 2016-12-14-12:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuType extends BaseEntity {
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单排序
     */
    private Long sort;


}
