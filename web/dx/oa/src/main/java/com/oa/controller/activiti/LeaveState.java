package com.oa.controller.activiti;

import com.oa.biz.leave.OaLeaveBiz;
import com.oa.entity.leave.OaLeave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lzh
 * @create 2017-12-29-18:11
 */
@Component
public class LeaveState implements State {

    private static final String oaLeaveHistory = "/leave/oa_leave_apply_history";

    @Autowired
    private OaLeaveBiz oaLeaveBiz;

    private static LeaveState leaveState;

    @Override
    public String handle(HttpServletRequest request, String processInstanceId) {
        OaLeave oaLeave = leaveState.oaLeaveBiz.getOaLeaveByProcessInstanceId(processInstanceId);
        request.setAttribute("oaLeave", oaLeave);
        return oaLeaveHistory;
    }

    @PostConstruct
    public void init() {
        leaveState = this;
    }

}
