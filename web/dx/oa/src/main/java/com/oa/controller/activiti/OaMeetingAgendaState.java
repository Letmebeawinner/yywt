package com.oa.controller.activiti;

import com.oa.biz.conference.OaMeetingAgendaBiz;
import com.oa.entity.conference.OaMeetingAgenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * @author jin shuo
 * @create 2018-06-011-15:12
 * 议程申请状态
 */
@Component
public class OaMeetingAgendaState implements State{

    private static final String oaMeetingAgendaApplyHistory = "/conference/oa_agenda_apply_history";

    @Autowired
    private OaMeetingAgendaBiz oaMeetingAgendaBiz;

    private static OaMeetingAgendaState oaMeetingAgendaState;

    @Override
    public String handle(HttpServletRequest request, String processInstanceId) {
        OaMeetingAgenda oaMeetingAgenda = oaMeetingAgendaState.oaMeetingAgendaBiz.getOaMeetingAgendaByProcessInstanceId(processInstanceId);
        request.setAttribute("oaMeetingAgenda", oaMeetingAgenda);
        return oaMeetingAgendaApplyHistory;
    }

    @PostConstruct
    public void init() {
        oaMeetingAgendaState = this;
    }
}
