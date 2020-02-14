package com.jiaowu.biz.topic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.jiaowu.dao.topic.TopicDao;
import com.jiaowu.entity.reply.Reply;
import com.jiaowu.entity.topic.Topic;

@Service
public class TopicBiz extends BaseBiz<Topic,TopicDao>{
	
	/**
	 * 增加话题查询条件
	 * @param request
	 * @param topic
	 * @return
	 */
	public String addCondition(HttpServletRequest request,Topic topic){
		StringBuffer sb=new StringBuffer();
		String title=request.getParameter("title");
		if(!StringUtils.isTrimEmpty(title)){
			sb.append(" and title like '%"+title+"%'");
			topic.setTitle(title);
		}
		String createUserName=request.getParameter("createUserName");
		if(!StringUtils.isTrimEmpty(createUserName)){
			sb.append(" and createUserName like '%"+createUserName+"%'");
			topic.setCreateUserName(createUserName);
		}
		return sb.toString();
	}
	
	/**
	 * 增加话题查看人数
	 * @param topic
	 */
	public void addTopicViewNum(Topic topic){
		topic.setViewNum(topic.getViewNum()+1);
		update(topic);
	}
	
	/**
	 * 增加话题评论数量
	 * @param topicId
	 */
	public void addTopicReplyNum(Long topicId){
		Topic topic=findById(topicId);
		topic.setReplyNum(topic.getReplyNum()+1);
		update(topic);
	}
	
	/**
	 * 为回复set话题的标题
	 * @param replyList
	 */
	public void setTopicTitle(List<Reply> replyList){
		if (replyList != null && replyList.size() > 0) {
			for (Reply r : replyList) {
				Topic topic = findById(r.getTopicId());
				r.setTopicTitle(topic.getTitle());
			}
		}
	}
}
