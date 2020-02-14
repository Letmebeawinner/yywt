package com.oa.controller.notice;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.oa.biz.notice.NoticeBiz;
import com.oa.entity.notice.Notice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 通知公告app接口
 *
 * @author ccl
 * @create 2017-01-11-14:01
 */
@Controller
@RequestMapping("/app/oa/notice")
public class AppNoticeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(AppNoticeController.class);

    @Autowired
    private NoticeBiz noticeBiz;

    /**
     * @Description:
     * @author: ccl
     * @Param: []
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-11
     */
    @RequestMapping("/getAppNotice")
    @ResponseBody
    public Map<String, Object> getAppNotice() {
        Map<String, Object> resultMap = null;
        try {
            List<Notice> noticeList = noticeBiz.queryAppNotice();
            resultMap = this.resultJson(ErrorCode.SUCCESS, "操作成功", noticeList);
        } catch (Exception e) {
            logger.error("AppNoticeController--getAppNotice", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-11
     */
    @RequestMapping("/getAppNoticeInfo")
    @ResponseBody
    public Map<String, Object> getAppNoticeInfo(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            Notice notice = noticeBiz.findById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "操作成功", notice);
        } catch (Exception e) {
            logger.error("AppNoticeController--getAppNoticeInfo", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


}
