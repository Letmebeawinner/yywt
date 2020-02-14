package com.oa.entity.workflow;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工作流表单数据表
 *
 * @author lzh
 * @create 2017-03-01-15:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WorkflowForm extends BaseEntity {
    private String processInstanceId;//流程实例id
    private String formKey;//流程key
    private String formValue;//流程值
    private String formPropertyType;//表单属性类型
}
