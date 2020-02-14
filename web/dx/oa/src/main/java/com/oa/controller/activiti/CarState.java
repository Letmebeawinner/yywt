package com.oa.controller.activiti;

import com.oa.biz.car.OaCarApplyBiz;
import com.oa.entity.car.OaCarApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用车的状态
 *
 * @author lzh
 * @create 2018-01-02-14:36
 */
@Component
public class CarState implements State {

    private static final String oaCarApplyHistory = "/car/oa_car_apply_history";

    @Autowired
    private OaCarApplyBiz carApplyBiz;

    private static CarState carState;

    @Override
    public String handle(HttpServletRequest request, String processInstanceId) {
        OaCarApply carApply = carState.carApplyBiz.getCarApplyByProcessInstanceId(processInstanceId);
        request.setAttribute("carApply", carApply);
        return oaCarApplyHistory;
    }

    @PostConstruct
    public void init() {
        carState = this;
    }
}
