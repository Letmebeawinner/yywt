package com.base.entity.role;

import com.a_268.base.core.BaseEntity;
import com.a_268.base.enums.StateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRole extends BaseEntity {

    //角色id
    private Long roleId;

    //用户id
    private Long userId;


    /**
     * 用户添加部门时 判断该部门是否已添加过 1：添加过 0：未添加过  数据库没有该字段  添加或者修改时不用set该字段
     */

    private int isHave = StateEnum.NOT_AVAILABLE.getState();

}
