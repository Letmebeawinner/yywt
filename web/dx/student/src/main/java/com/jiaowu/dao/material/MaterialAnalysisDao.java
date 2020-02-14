package com.jiaowu.dao.material;

import com.a_268.base.core.BaseDao;
import com.jiaowu.entity.material.MaterialAnalysis;
import com.jiaowu.entity.material.MaterialAnalysisDTO;

import java.util.List;
import java.util.Map;

public interface MaterialAnalysisDao extends BaseDao<MaterialAnalysis>{

    public List<MaterialAnalysisDTO> getMaterialAnalysisList(Map<String,Object> map);

    public int getMaterialAnalysisListCount(Map<String,Object> map);
}
