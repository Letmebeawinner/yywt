package com.jiaowu.biz.praise;

import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.jiaowu.dao.praise.PraiseDao;
import com.jiaowu.entity.praise.Praise;

@Service
public class PraiseBiz extends BaseBiz<Praise,PraiseDao>{
	/**
	 * 保存点赞信息
	 * @param userType
	 * @param userId
	 * @param replyId
	 */
	public void save(Integer userType,Long userId,Long replyId,Integer belong){
		Praise praise=new Praise();
		praise.setReplyId(replyId);
		praise.setUserId(userId);
		praise.setType(userType);
		praise.setBelong(belong);
		save(praise);
	}
}
