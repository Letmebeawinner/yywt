package com.base.entity.connection;

import com.a_268.base.core.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门和角色关联Entity
 * @author s.li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class DepartmentRole extends BaseEntity{

	private static final long serialVersionUID = 8027696731765935061L;

	/**部门ID*/
	private Long departmentId;
	/**角色ID*/
	private Long roleId;
}
