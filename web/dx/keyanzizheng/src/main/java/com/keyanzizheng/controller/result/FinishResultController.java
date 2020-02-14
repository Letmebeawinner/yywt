package com.keyanzizheng.controller.result;

import com.a_268.base.controller.BaseController;
import com.a_268.base.util.SysUserUtils;
import com.keyanzizheng.biz.result.ResultBiz;
import com.keyanzizheng.biz.subsection.SubSectionBiz;
import com.keyanzizheng.entity.result.QueryResult;
import com.keyanzizheng.entity.subsection.SubSection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 课题结项
 *
 * @author YaoZhen
 * @create 11-21, 10:17, 2017.
 */
@RequestMapping("/admin/ky/")
@Controller
public class FinishResultController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(FinishResultController.class);
    @Autowired
    private ResultBiz resultBiz;
    @Autowired
    private SubSectionBiz subSectionBiz;

    /**
     * 结项页面
     *
     * @param id
     * @return
     */
    @RequestMapping("finish")
    public ModelAndView finishResult(HttpServletRequest request, @RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("/result/finish_result");
        try {
            QueryResult result = resultBiz.getResultById(id);
            mv.addObject("result", result);


            Long sysUserId=SysUserUtils.getLoginSysUserId(request);
            // 查询处室
            List<SubSection> subSectionList = subSectionBiz.querySubSectionListBySysUserId(sysUserId);
            mv.addObject("subSectionList", subSectionList);
        } catch (Exception e) {
            logger.error("FinishResultController.finishResult", e);
        }
        return mv;
    }
}
