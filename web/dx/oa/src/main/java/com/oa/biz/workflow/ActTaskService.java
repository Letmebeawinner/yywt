package com.oa.biz.workflow;

/**
 * @author lizhenhui
 * @create 2017-03-16 11:55
 */
public interface ActTaskService {
    /**
     * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
     * @return
     */
    String getFormKey(String procDefId);
}
