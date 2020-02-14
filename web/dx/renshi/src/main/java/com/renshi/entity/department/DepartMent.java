package com.renshi.entity.department;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门
 * Created by 268 on 2016/12/21.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DepartMent extends BaseEntity {

    private static final long serialVersionUID = -5709767830966952845L;
    /**上级部门ID*/
    private Long parentId;
    /**所有上级部门ID*/
    private String parentIds;
    /**部门名*/
    private String departmentName;
    /**部门描述*/
    private String departmentDesc;
    /**职务名*/
    private String departmentDuty;
    /**排序数值*/
    private Integer sort;

}
