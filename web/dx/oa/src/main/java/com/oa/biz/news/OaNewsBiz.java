package com.oa.biz.news;

import com.a_268.base.core.BaseBiz;
import com.oa.biz.workflow.WorkflowFormBiz;
import com.oa.dao.news.OaNewsDao;
import com.oa.entity.news.OaNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 新闻发布审批biz
 *
 * @author lzh
 * @create 2017-11-01-17:07
 */
@Service
public class OaNewsBiz extends BaseBiz<OaNews, OaNewsDao>{
    
    @Autowired
    private WorkflowFormBiz workflowFormBiz;
    
    /**
     * 开启新闻工作流
     * @param oaNews
     * @param processDefinitionId
     * @param userId
     * @return
     */
    public String tx_startNewsProcess(OaNews oaNews, String processDefinitionId, Long userId) {
        oaNews.setApplyId(userId);
        oaNews.setApplyTime(new Date());
        oaNews.setAudit(0);
        this.save(oaNews);
        Long id = oaNews.getId();
        String businessKey = "oaNewsBiz." + id;
        String processInstanceId = workflowFormBiz.startWorkFlowById(userId, businessKey, processDefinitionId);
        oaNews.setProcessInstanceId(processInstanceId);
        this.update(oaNews);
        return processInstanceId;
    }

    /**
     * @Description: 根据流程实例id返回值
     * @author: lzh
     * @Param: [processInstanceId]
     * @Return: com.oa.entity.workflow.OaNews
     * @Date: 10:54
     */
    public OaNews getOaNewsByProcessInstanceId(String processInstanceId) {
        String sql = " processInstanceId = " + processInstanceId;
        List<OaNews> oaNewss = this.find(null, sql);
        return oaNewss.get(0);
    }

    /**
     * 开始审批
     * @param oaNews
     * @param taskId
     * @param comment
     */
    public void tx_startNewsApplyAudit(OaNews oaNews, String taskId, String comment, Long userId) {
        String sql = " processInstanceId = " + oaNews.getProcessInstanceId();
        this.updateByStrWhere(oaNews, sql);
        workflowFormBiz.tx_startAudit(taskId, oaNews.getProcessInstanceId(), comment, oaNews.getAudit(), userId);
    }

    /**
     *
     * @param taskId 任务id
     * @param userIds 需要发送的用户ids
     * @param newsId 公文id
     * @param creatorId 操作者id
     */
//    public void saveNewsPublish(String taskId, String userIds, Long newsId, Long creatorId) {
////        批量发送公文
//        if (!StringUtils.isEmpty(userIds)) {
//            String[] ids = userIds.split(",");
//            List<UserNews> userNewsList = Arrays.stream(ids)
//                    .map(id -> {
//                        UserNews userNews = new UserNews();
//                        userNews.setUserId(Long.parseLong(id));
//                        userNews.setCreateId(creatorId);
//                        userNews.setNewsId(newsId);
//                        userNews.setStatus(0);
//                        return userNews;
//                    })
//                    .collect(Collectors.toList());
//            userNewsBiz.saveBatch(userNewsList);
//            taskService.complete(taskId);
//        }
//    }
    
}
