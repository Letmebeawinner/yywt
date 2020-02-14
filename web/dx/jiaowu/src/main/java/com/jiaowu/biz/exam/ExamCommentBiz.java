package com.jiaowu.biz.exam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.jiaowu.dao.exam.ExamCommentDao;
import com.jiaowu.entity.exam.ExamComment;

@Service
public class ExamCommentBiz extends BaseBiz<ExamComment, ExamCommentDao>{
	
	/**
	 * 增加考评搜索的条件
	 * @param request
	 * @param examComment
	 * @return
	 */
	public String addCondition(HttpServletRequest request,ExamComment examComment){
		StringBuffer sb=new StringBuffer();
		String studentName=request.getParameter("studentName");
		if(!StringUtils.isTrimEmpty(studentName)){
            sb.append(" and studentName like '%"+studentName+"%'");
            examComment.setStudentName(studentName);
        }
		return sb.toString();
	}
}
