package com.base.entity.connection;

import com.a_268.base.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和权限关联Entity
 *
 * @author s.li
 */
@Data
@EqualsAndHashCode
public class RoleResource extends BaseEntity {

    private static final long serialVersionUID = 4727037393550224035L;

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 权限ID
     */
    private Long resourceId;
}
