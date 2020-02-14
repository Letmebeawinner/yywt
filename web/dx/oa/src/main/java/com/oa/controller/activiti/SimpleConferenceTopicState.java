package com.oa.controller.activiti;

import com.oa.biz.conference.OaMeetingTopicBiz;
import com.oa.entity.conference.OaMeetingTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lzh
 * @create 2018-01-02-15:12
 * 议题申请状态(简易版)
 */
@Component
public class SimpleConferenceTopicState implements State{

    private static final String oaMetingTopicApplyHistory = "/conference/oa_conference_topic_apply_history";

    @Autowired
    private OaMeetingTopicBiz oaMeetingTopicBiz;

    private SimpleConferenceTopicState conferenceTopicState;

    @Override
    public String handle(HttpServletRequest request, String processInstanceId) {
        OaMeetingTopic oaMeetingTopic = conferenceTopicState.oaMeetingTopicBiz.getOaMeetingByProcessInstanceId(processInstanceId);
        request.setAttribute("oaMeetingTopic", oaMeetingTopic);
        return oaMetingTopicApplyHistory;
    }

    @PostConstruct
    public void init() {
        conferenceTopicState = this;
    }
}
