package com.jiaowu.biz.material;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.jiaowu.dao.material.MaterialAnalysisDao;
import com.jiaowu.entity.material.MaterialAnalysis;
@Service
public class MaterialAnalysisBiz extends BaseBiz<MaterialAnalysis,MaterialAnalysisDao>{
	/**
	 * 增加党性材料分析的搜索条件
	 * @param request
	 * @param materialAnalysis
	 * @return
	 */
	public String addCondition(HttpServletRequest request,MaterialAnalysis materialAnalysis){
		StringBuffer sb=new StringBuffer();
		String studentId=request.getParameter("studentId");
		if(!StringUtils.isTrimEmpty(studentId) && Long.parseLong(studentId)>0){
            sb.append(" and studentId="+studentId);
            materialAnalysis.setStudentId(Long.parseLong(studentId));
        }
		String meetingId=request.getParameter("meetingId");
		if(!StringUtils.isTrimEmpty(meetingId) && Long.parseLong(meetingId)>0){
            sb.append(" and meetingId="+meetingId);
            materialAnalysis.setMeetingId(Long.parseLong(meetingId));
        }
		return sb.toString();
	}
}
