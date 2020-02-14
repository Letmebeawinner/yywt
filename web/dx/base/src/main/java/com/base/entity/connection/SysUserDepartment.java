package com.base.entity.connection;

import com.a_268.base.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户部门关联Entity
 * @author s.li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SysUserDepartment extends BaseEntity{

	private static final long serialVersionUID = -1846754974591741687L;
	/**部门ID*/
	private Long departmentId;
	/**用户ID*/
	private Long userId;
}
