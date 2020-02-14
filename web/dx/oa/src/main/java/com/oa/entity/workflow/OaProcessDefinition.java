package com.oa.entity.workflow;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户流程定义
 *
 * @author lzh
 * @create 2017-03-16-16:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OaProcessDefinition {
    private String id;
    private String key;
    private int revision = 1;
    private int version;
    private String category;
    private String deploymentId;
    private String resourceName;
    private String tenantId = "";
    private Integer historyLevel;
    private String diagramResourceName;
    private String name;
    private int suspensionState;
}
