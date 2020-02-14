package com.jiaowu.biz.teachingComment;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.common.CommonBiz;
import com.jiaowu.dao.teachingComment.TeachingCommentManagementDao;
import com.jiaowu.entity.teachingComment.TeachingComment;
import com.jiaowu.entity.teachingComment.TeachingCommentManagement;

@Service
public class TeachingCommentManagementBiz extends BaseBiz<TeachingCommentManagement, TeachingCommentManagementDao>{
	@Autowired
	private CommonBiz commonBiz;
	@Autowired
	private TeachingCommentBiz teachingCommentBiz;
	/**
	 * 获取当前登录人的类型,返回不同的类型.
	 * @param request
	 * @return
	 */
	public String getType(HttpServletRequest request){
		Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
		if(userMap!=null){
			if(userMap.get("userType").equals("1")){
				return "leader_to_teacher";
			}else if(userMap.get("userType").equals("2")){
				return "teacher_to_teacher";
			}else{
				return "student_to_teacher";
			}
		}
		return null;
	}
	
	/**
	 * 获取教学评价列表
	 * @param request
	 * @param pagination
	 * @return
	 */
	public List<TeachingCommentManagement> getTeachingCommentManagementList(HttpServletRequest request,Pagination pagination){
		Long userId=commonBiz.getCurrentUserId(request);
		List<TeachingCommentManagement> teachingCommentManagementList=find(pagination," fromPeopleId="+userId+" and status=2");
		if(teachingCommentManagementList!=null&&teachingCommentManagementList.size()>0){
			Date now=new Date();
			for(TeachingCommentManagement teachingCommentManagement:teachingCommentManagementList){
				TeachingComment teachingComment=teachingCommentBiz.findById(teachingCommentManagement.getTeachingCommentId());
				teachingCommentManagement.setStartTime(teachingComment.getStartTime());
				teachingCommentManagement.setEndTime(teachingComment.getEndTime());
				if(teachingComment.getEndTime().getTime()>now.getTime()){
					teachingCommentManagement.setOverdue(false);
				}else{
					teachingCommentManagement.setOverdue(true);
				}
			}
		}
		return null;
	}
}
