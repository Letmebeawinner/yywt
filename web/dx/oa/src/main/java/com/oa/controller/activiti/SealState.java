package com.oa.controller.activiti;

import com.oa.biz.seal.OaSealBiz;
import com.oa.entity.seal.OaSeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lzh
 * @create 2018-01-02-15:12
 * 印章申请状态
 */
@Component
public class SealState implements State{

    private static final String oaSealApplyHistory = "/seal/oa_seal_apply_history";

    @Autowired
    private OaSealBiz oaSealBiz;

    private static SealState sealState;

    @Override
    public String handle(HttpServletRequest request, String processInstanceId) {
        OaSeal oaSeal = sealState.oaSealBiz.getOaSealByProcessInstanceId(processInstanceId);
        request.setAttribute("oaSeal", oaSeal);
        return oaSealApplyHistory;
    }

    @PostConstruct
    public void init() {
        sealState = this;
    }
}
