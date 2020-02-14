package com.jiaowu.biz.reply;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.jiaowu.dao.reply.ReplyDao;
import com.jiaowu.entity.reply.Reply;

@Service
public class ReplyBiz extends BaseBiz<Reply,ReplyDao>{
	/**
	 * 增加回复点赞数量
	 * @param topicId
	 */
	public Long addReplyPraiseNum(Long replyId){
		Reply reply=findById(replyId);
		reply.setPraiseNum(reply.getPraiseNum()+1);
		update(reply);
		return reply.getPraiseNum();
	}
	
	/**
	 * 增加回复子回复数量
	 * @param replyId
	 * @return
	 */
	public Long addReplyChildReplyNum(Long replyId){
		Reply parentReply = findById(replyId);
		parentReply.setReplyNum(parentReply.getReplyNum() + 1);
		update(parentReply);
		return parentReply.getReplyNum();
	}
	
	/**
	 * 增加回复搜索条件
	 * @param request
	 * @param reply
	 * @return
	 */
	public String addCondition(HttpServletRequest request,Reply reply){
		StringBuffer sb=new StringBuffer();
		String content = request.getParameter("content");
		if (!StringUtils.isTrimEmpty(content)) {
			sb.append(" and content like '%" + content + "%'");
			reply.setContent(content);
		}
		String userName = request.getParameter("userName");
		if (!StringUtils.isTrimEmpty(userName)) {
			sb.append(" and userName like '%" + userName + "%'");
			reply.setUserName(userName);
		}
		String topicId = request.getParameter("topicId");
		if (!StringUtils.isTrimEmpty(topicId)
				&& Long.parseLong(topicId) > 0) {
			String topicTitle = request.getParameter("topicTitle");
			sb.append(" and topicId=" + topicId);
			reply.setTopicId(Long.parseLong(topicId));
			reply.setTopicTitle(topicTitle);
		}
		return sb.toString();
	}
}
