package com.oa.biz.workflow;

import org.activiti.engine.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公用的任务
 *
 * @author lzh
 * @create 2017-03-16-14:36
 */
@Service
public class ActTaskServiceImpl implements ActTaskService {

    @Autowired
    private FormService formService;
    @Override
    public String getFormKey(String procDefId) {
        return formService.getStartFormKey(procDefId);
    }
}
