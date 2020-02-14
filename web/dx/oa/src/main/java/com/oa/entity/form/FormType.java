package com.oa.entity.form;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表单类型
 *
 * @author ccl
 * @create 2017-01-07-15:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FormType extends BaseEntity{

    private String name;//名称

    private Long parentId;//父节点

    private Long sort;//排序

}
