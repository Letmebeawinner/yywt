package com.jiaowu.biz.research;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.jiaowu.dao.research.ResearchReportDao;
import com.jiaowu.entity.research.ResearchReport;
@Service
public class ResearchReportBiz extends BaseBiz<ResearchReport,ResearchReportDao>{
    
	public String addCondition(HttpServletRequest request,ResearchReport researchReport){
		String whereSql="";
		String researchId=request.getParameter("researchId");
		if(!StringUtils.isTrimEmpty(researchId) && Long.parseLong(researchId)>0){
            whereSql+=" and researchId="+researchId;
            researchReport.setResearchId(Long.parseLong(researchId));
        }
		String researchName=request.getParameter("researchName");
		if(!StringUtils.isTrimEmpty(researchName)){
            researchReport.setResearchName(researchName);
        }
		return whereSql;
	}
}
