package com.keyanzizheng.entity.department;

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

    private static final long serialVersionUID = -6151855828533471420L;
    /**上级部门ID*/
    private Long parentId;
    /**所有上级部门ID*/
    private String parentIds;
    /**部门名*/
    private String departmentName;
    /**部门描述*/
    private String departmentDesc;
}
