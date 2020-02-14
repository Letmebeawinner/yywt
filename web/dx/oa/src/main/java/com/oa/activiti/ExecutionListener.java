package com.oa.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 流程监听器
 *
 * @author lzh
 * @create 2017-03-03-17:21
 */
public class ExecutionListener implements org.activiti.engine.delegate.ExecutionListener, TaskListener {

    private static final String EVENT_CREATE = "create";
    private static final String EVENT_ASSIGNMENT = "assignment";
    private static final String EVENT_COMPLETE = "complete";
    private static final String EVENT_DELETE = "delete";
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        System.out.println("流程监听器启用");
        delegateExecution.getCurrentActivityName();
    }

    @Override
    public void notify(DelegateTask delegateTask) {

    }
}
