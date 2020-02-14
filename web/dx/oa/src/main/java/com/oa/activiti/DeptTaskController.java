package com.oa.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 部门领导意见监听器
 *
 * @author lzh
 * @create 2017-03-03-17:06
 */
public class DeptTaskController implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("listener" + delegateTask);
        System.out.println(delegateTask.getCandidates());
        System.out.println(delegateTask.getAssignee());
        delegateTask.setVariable("user", "2");
    }
}
