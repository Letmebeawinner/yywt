package com.oa.controller.activiti;

import com.a_268.base.controller.BaseController;
import com.oa.biz.workflow.ActTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lzh
 * @create 2017-03-16-14:37
 */
@Controller
@RequestMapping("/admin/oa")
public class ActController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ActController.class);

    @Autowired
    private ActTaskService actTaskService;

}
