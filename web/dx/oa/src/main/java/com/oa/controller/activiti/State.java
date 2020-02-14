package com.oa.controller.activiti;

import javax.servlet.http.HttpServletRequest;

/**
 * 状态接口
 *
 * @author lzh
 * @create 2017-12-29-18:09
 */
public interface State {
    String handle(HttpServletRequest request, String processInstanceId);
}
