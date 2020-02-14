package com.jiaowu.biz.activityReply;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.jiaowu.dao.activityReply.ActivityReplyDao;
import com.jiaowu.entity.activity.Activity;
import com.jiaowu.entity.activityReply.ActivityReply;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 李帅雷 on 2017/8/25.
 */
@Service
public class ActivityReplyBiz extends BaseBiz<ActivityReply,ActivityReplyDao> {
    /**
     * 增加回复点赞数量
     * @param replyId
     */
    public Long addReplyPraiseNum(Long replyId){
        ActivityReply activityReply=findById(replyId);
        activityReply.setPraiseNum(activityReply.getPraiseNum()+1);
        update(activityReply);
        return activityReply.getPraiseNum();
    }

    /**
     * 增加回复子回复数量
     * @param replyId
     * @return
     */
    public Long addReplyChildReplyNum(Long replyId){
        ActivityReply parentReply = findById(replyId);
        parentReply.setReplyNum(parentReply.getReplyNum() + 1);
        update(parentReply);
        return parentReply.getReplyNum();
    }

    /**
     * 增加回复搜索条件
     * @param request
     * @param activityReply
     * @return
     */
    public String addCondition(HttpServletRequest request, ActivityReply activityReply){
        StringBuffer sb=new StringBuffer();
        String content = request.getParameter("content");
        if (!StringUtils.isTrimEmpty(content)) {
            sb.append(" and content like '%" + content + "%'");
            activityReply.setContent(content);
        }
        String userName = request.getParameter("userName");
        if (!StringUtils.isTrimEmpty(userName)) {
            sb.append(" and userName like '%" + userName + "%'");
            activityReply.setUserName(userName);
        }
        String activityId = request.getParameter("activityId");
        if (!StringUtils.isTrimEmpty(activityId)
                && Long.parseLong(activityId) > 0) {
            String activityTitle = request.getParameter("activityTitle");
            sb.append(" and activityId=" + activityId);
            activityReply.setActivityId(Long.parseLong(activityId));
            activityReply.setActivityTitle(activityTitle);
        }
        return sb.toString();
    }
}
