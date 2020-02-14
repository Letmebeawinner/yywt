package com.base.controller.feedback;

import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.base.biz.feedback.FeedBackBiz;
import com.base.entity.feedback.FeedBack;
import com.base.entity.feedback.FeedBackDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 意见反馈
 *
 * @author ccl
 * @create 2017-03-14-11:46
 */
@RequestMapping("/admin/base/feedback")
@Controller
public class FeedBackController extends BaseController {
    
    private static Logger logger = LoggerFactory.getLogger(FeedBackController.class);

    @Autowired
    private FeedBackBiz feedBackBiz;

    @InitBinder({"feedback"})
    public void initFeedBack(WebDataBinder binder){ binder.setFieldDefaultPrefix("feedback.");}


    /**
     * @Description:查询所有的意见反馈
     * @author: ccl
     * @Param: [request, feedback, pagination]
     * @Return: java.lang.String
     * @Date: 2017-03-14
     */
    @RequestMapping("/queryAllFeedback")
    public String queryAllFeedback(HttpServletRequest request, @ModelAttribute("feedback") FeedBack feedback, @ModelAttribute("pagination") Pagination pagination){
        try{
            List<FeedBackDto>  feedbackList=feedBackBiz.getFeedBackDtos(pagination,"1=1 order by createTime desc");
            request.setAttribute("feedbackList",feedbackList);
        }catch (Exception e){
            logger.error("FeedBackController--queryAllFeedback",e);
            return  this.setErrorPath(request,e);
        }
        return "/feedback/feedback-list";
    }

























}
