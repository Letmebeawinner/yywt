package com.base.api;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.base.biz.feedback.FeedBackBiz;
import com.base.entity.feedback.FeedBack;
import com.base.utils.DelHTMLTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 意见反馈
 *
 * @author ccl
 * @create 2017-03-14-13:35
 */
@Controller
@RequestMapping("/api/base/feedback")
public class ApiFeedBackController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ApiFeedBackController.class);

    @Autowired
    private FeedBackBiz feedBackBiz;

    @InitBinder({"feedback"})
    public void initFeedBack(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("feedback.");
    }

    /**
     * @Description:添加反馈
     * @author: ccl
     * @Param: [request, feedback]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-03-14
     */
    @RequestMapping("/addFeedback")
    @ResponseBody
    public Map<String, Object> addFeedback(HttpServletRequest request, @ModelAttribute("feedback") FeedBack feedback) {
        Map<String, Object> resultMap = null;
        try {
            feedback.setContext(DelHTMLTag.delHTMLTag(feedback.getContext()));
            feedBackBiz.save(feedback);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("ApiFeedBackController--addFeedback", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }
}
