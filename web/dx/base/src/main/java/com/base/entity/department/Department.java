package com.base.entity.department;

import com.a_268.base.core.BaseEntity;

import com.a_268.base.enums.StateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 部门Entity
 * @author s.li
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Department extends BaseEntity {
	
	private static final long serialVersionUID = 4765597009140942809L;
	/**上级部门ID*/
	private Long parentId;
	/**所有上级部门ID*/
	private String parentIds;
	/**部门名*/
	private String departmentName;
	/**职务名*/
	private String departmentDuty;
	/**部门描述*/
	private String departmentDesc;
	/**是否激活：1激活，0禁用*/
	private Integer departmentAvailable;
	/**排序数值*/
	private Integer sort;

	/**
	 * 用户添加部门时 判断该部门是否已添加过 1：添加过 0：未添加过  数据库没有该字段  添加或者修改时不用set该字段
	 */

	private int isHave = StateEnum.NOT_AVAILABLE.getState();
}
