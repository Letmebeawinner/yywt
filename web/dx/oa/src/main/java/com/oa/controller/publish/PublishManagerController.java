package com.oa.controller.publish;

import com.a_268.base.core.Pagination;
import com.oa.biz.news.NewsBiz;
import com.oa.biz.notice.NoticeBiz;
import com.oa.biz.publish.PublishManagerBiz;
import com.oa.entity.publish.PublishManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 发布管理控制层
 *
 * @author lzh
 * @create 2016-12-30-14:17
 */
@Controller
@RequestMapping("/admin/oa")
public class PublishManagerController {

    private Logger logger = LoggerFactory.getLogger(PublishManagerController.class);

    @Autowired
    private NewsBiz newsBiz;
    @Autowired
    private NoticeBiz noticeBiz;

    private static final String publishList = "/publish/publish_manager_list";

    @RequestMapping("/queryAllPublishList")
    public String getPublishList(@ModelAttribute("pagination") Pagination pagination, @ModelAttribute("publishManager")PublishManager publishManager) {
        try {

        } catch(Exception e) {
            logger.error("PublishManagerController.getPublishList", e);
        }
        return publishList;
    }
}
