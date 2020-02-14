package com.oa.controller.activiti;

import com.oa.biz.workflow.OaLetterBiz;
import com.oa.entity.workflow.OaLetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 公文状态
 *
 * @author lzh
 * @create 2018-01-02-14:40
 */
@Component
public class LetterState implements State {

    private static final String oaLetterHistory = "/letter/oa_letter_history";

    @Autowired
    private OaLetterBiz oaLetterBiz;

    private static LetterState letterState;

    @Override
    public String handle(HttpServletRequest request, String processInstanceId) {
        OaLetter oaLetter = letterState.oaLetterBiz.getOaLetterByProcessInstanceId(processInstanceId);
        request.setAttribute("oaLetter", oaLetter);
        return oaLetterHistory;
    }

    @PostConstruct
    public void init() {
        letterState = this;
    }
}
