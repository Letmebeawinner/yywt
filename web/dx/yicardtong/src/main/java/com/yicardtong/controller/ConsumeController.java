package com.yicardtong.controller;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.yicardtong.biz.consume.ConsumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by caichenglong on 2017/10/17.
 */
@Controller
public class ConsumeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AttendController.class);

    private static final String ADMIN_PREFIX = "/yikatong";

    @Autowired
    private ConsumeService consumeService;

    /**
     * @param request
     * @return java.lang.String
     * @Description 获取每日消费列表
     * @author CCL
     */
    @RequestMapping(ADMIN_PREFIX + "/showConsumeList")
    @ResponseBody
    public Map<String, Object> showConsumeList(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            List<Map<String, String>> consumeList = consumeService.queryConsumeList("select * from Consume_Per_Date");
            json = this.resultJson(ErrorCode.SUCCESS, "", consumeList);
        } catch (Exception e) {
            logger.info("ConsumeController.showConsumeList", e);
        }
        return json;
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 获取每日消费列表
     * @author CCL
     */
    @RequestMapping(ADMIN_PREFIX + "/showConsumeListBySeach")
    @ResponseBody
    public Map<String, Object> showConsumeListBySeach(HttpServletRequest request, String whereSql) {
        Map<String, Object> json = null;
        try {
            List<Map<String, String>> consumeList = consumeService.queryConsumeList("select * from Consume_Per_Date left join General_Personnel on Consume_Per_Date.Base_PerID=General_Personnel.Base_PerID" + whereSql);
            json = this.resultJson(ErrorCode.SUCCESS, "", consumeList);
        } catch (Exception e) {
            logger.info("ConsumeController.showConsumeList", e);
        }
        return json;
    }


    /**
     * @param request
     * @return java.lang.String
     * @Description 获取每日消费列表
     * @author CCL
     */
    @RequestMapping(ADMIN_PREFIX + "/showConsumeSourceList")
    @ResponseBody
    public Map<String, Object> showConsumeSourceList(HttpServletRequest request) {
        Map<String, Object> json = null;
        try {
            List<Map<String, String>> consumeSourceList = consumeService.queryConsumeSourceList("select * from Consume_Source");
            json = this.resultJson(ErrorCode.SUCCESS, "", consumeSourceList);
        } catch (Exception e) {
            logger.info("ConsumeController.showConsumeSourceList", e);
        }
        return json;
    }


}
