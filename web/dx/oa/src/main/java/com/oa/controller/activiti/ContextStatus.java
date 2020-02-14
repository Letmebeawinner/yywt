package com.oa.controller.activiti;

import javax.servlet.http.HttpServletRequest;

/**
 * 改装版，状态模式和工厂模式结合
 *
 * @author lzh
 * @create 2017-12-29-18:01
 */
public class ContextStatus {

    //状态
    private State state;
    private HttpServletRequest request;
    private String processInstanceId; //流程实例id

    public ContextStatus(HttpServletRequest request, String processInstanceId, String processKey) {
        this.state = ProcessFactory.createProcessFactory(processKey);
        this.request = request;
        this.processInstanceId = processInstanceId;
    }

    public String handle() {
        return state.handle(request, processInstanceId);
    }
}
