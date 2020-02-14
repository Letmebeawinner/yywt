package com.base.entity.role;

import com.a_268.base.core.BaseEntity;

import com.a_268.base.enums.StateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色Entity
 *
 * @author s.li
 */
@Data
@EqualsAndHashCode
public class Role extends BaseEntity {

    private static final long serialVersionUID = 5214908966627492349L;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 用户添加部门时 判断该部门是否已添加过 1：添加过 0：未添加过  数据库没有该字段  添加或者修改时不用set该字段
     */

    private int isHave = StateEnum.NOT_AVAILABLE.getState();
    /**
     * 排序值
     */
    private Long sort;

}
