package com.jiaowu.biz.activity;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.jiaowu.dao.activity.ActivityDao;
import com.jiaowu.entity.activity.Activity;
import com.jiaowu.entity.activityReply.ActivityReply;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 李帅雷 on 2017/8/24.
 */
@Service
public class ActivityBiz extends BaseBiz<Activity,ActivityDao> {
    /**
     * 增加活动查询条件
     * @param request
     * @param activity
     * @return
     */
    public String addCondition(HttpServletRequest request, Activity activity){
        StringBuffer sb=new StringBuffer();
        String title=request.getParameter("title");
        if(!StringUtils.isTrimEmpty(title)){
            sb.append(" and title like '%"+title+"%'");
            activity.setTitle(title);
        }
        String createUserName=request.getParameter("createUserName");
        if(!StringUtils.isTrimEmpty(createUserName)){
            sb.append(" and createUserName like '%"+createUserName+"%'");
            activity.setCreateUserName(createUserName);
        }
        return sb.toString();
    }

    /**
     * 增加活动查看人数
     * @param activity
     */
    public void addActivityViewNum(Activity activity){
        activity.setViewNum(activity.getViewNum()+1);
        update(activity);
    }

    /**
     * 增加活动评论数量
     * @param activityId
     */
    public void addActivityReplyNum(Long activityId){
        Activity activity=findById(activityId);
        activity.setReplyNum(activity.getReplyNum()+1);
        update(activity);
    }

    /**
     * 为回复set活动的标题
     * @param activityReplyList
     */
    public void setActivityTitle(List<ActivityReply> activityReplyList){
        if (activityReplyList != null && activityReplyList.size() > 0) {
            for (ActivityReply activityReply : activityReplyList) {
                Activity activity = findById(activityReply.getActivityId());
                activityReply.setActivityTitle(activity.getTitle());
            }
        }
    }
}
