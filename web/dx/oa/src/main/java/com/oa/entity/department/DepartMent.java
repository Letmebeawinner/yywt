package com.oa.entity.department;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 部门
 * Created by 268 on 2016/12/21.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DepartMent extends BaseEntity {

    private static final long serialVersionUID = 4765597009140942809L;
    /**上级部门ID*/
    private Long parentId;
    /**所有上级部门ID*/
    private String parentIds;
    /**部门名*/
    private String departmentName;
    /**部门描述*/
    private String departmentDesc;

    public DepartMent(){}
    public DepartMent(Map<String, String> map){
        this.setId(Long.valueOf(map.get("id")));
        this.setParentId(Long.valueOf(map.get("parentId")));
        this.setParentIds(map.get("parentIds"));
        this.setDepartmentDesc(map.get("departmentDesc"));
        this.setDepartmentName(map.get("departmentName"));
    }
}
