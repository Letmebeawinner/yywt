package com.oa.entity.flow;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程分类
 *
 * @author ccl
 * @create 2017-01-05-17:11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FlowType extends BaseEntity{

    private String name;//名称

    private Long parentId;//父节点

    private Long sort;//排序

}
