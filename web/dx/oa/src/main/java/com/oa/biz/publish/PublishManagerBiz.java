package com.oa.biz.publish;

import com.a_268.base.core.BaseBiz;
import com.oa.biz.news.NewsBiz;
import com.oa.biz.news.NewsDepartmentBiz;
import com.oa.biz.notice.NoticeBiz;
import com.oa.biz.notice.NoticeDepartmentBiz;
import com.oa.dao.publish.PublishManagerDao;
import com.oa.entity.publish.PublishManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发布管理业务层
 *
 * @author lzh
 * @create 2016-12-30-14:16
 */
@Service
public class PublishManagerBiz extends BaseBiz<PublishManager, PublishManagerDao> {
    @Autowired
    private NewsBiz newsBiz;
    @Autowired
    private NoticeBiz noticeBiz;
    @Autowired
    private NewsDepartmentBiz newsDepartmentBiz;
    @Autowired
    private NoticeDepartmentBiz noticeDepartmentBiz;


}
