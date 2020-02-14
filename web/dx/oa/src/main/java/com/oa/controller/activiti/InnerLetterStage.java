package com.oa.controller.activiti;

import com.oa.biz.conference.OaMeetingTopicBiz;
import com.oa.biz.workflow.OaLetterBiz;
import com.oa.entity.conference.OaMeetingTopic;
import com.oa.entity.workflow.OaLetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lzh
 * @create 2018-01-02-15:12
 * 议题申请状态
 */
@Component
public class InnerLetterStage implements State{

    private static final String oaInnerLetterHistory = "/letter/oa_inner_letter_history";

    @Autowired
    private OaLetterBiz oaLetterBiz;

    private static InnerLetterStage innerLetterStage;

    @Override
    public String handle(HttpServletRequest request, String processInstanceId) {
        OaLetter oaLetter = innerLetterStage.oaLetterBiz.getOaLetterByProcessInstanceId(processInstanceId);
        request.setAttribute("oaLetter", oaLetter);
        return oaInnerLetterHistory;
    }

    @PostConstruct
    public void init() {
        innerLetterStage = this;
    }
}
