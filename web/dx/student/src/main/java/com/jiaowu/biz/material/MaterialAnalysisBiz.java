package com.jiaowu.biz.material;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.a_268.base.core.Pagination;
import com.jiaowu.entity.material.MaterialAnalysisDTO;
import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.jiaowu.dao.material.MaterialAnalysisDao;
import com.jiaowu.entity.material.MaterialAnalysis;

import java.util.List;
import java.util.Map;

@Service
public class MaterialAnalysisBiz extends BaseBiz<MaterialAnalysis,MaterialAnalysisDao>{
	@Resource
	private MaterialAnalysisDao materialAnalysisDao;
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

	/**
	 * 分页获取党性材料分析列表
	 * @param conditionMap
	 * @param pagination
     * @return
     */
	public List<MaterialAnalysisDTO> getMaterialAnalysisList(Map<String,Object> conditionMap, Pagination pagination){
		int totalCount=materialAnalysisDao.getMaterialAnalysisListCount(conditionMap);
		if(totalCount<=0){
			return null;
		}
		pagination.setTotalCount(totalCount);
		pagination.setTotalPages(totalCount%pagination.getPageSize()==0?totalCount/pagination.getPageSize():totalCount/pagination.getPageSize()+1);
		conditionMap.put("start",(pagination.getCurrentPage()-1)*pagination.getPageSize());
		conditionMap.put("end",pagination.getCurrentPage()*pagination.getPageSize());
		List<MaterialAnalysisDTO> materialAnalysisList=materialAnalysisDao.getMaterialAnalysisList(conditionMap);
		return materialAnalysisList;
	}
}
