package com.keyanzizheng.entity.investigate;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 调研方向
 * Created by tiger on 2016/12/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryResearchDirection extends ResearchDirection {
    private static final long serialVersionUID = -9111782250797021008L;
    private String employeeName;//申报人姓名
    public QueryResearchDirection(){}
    public QueryResearchDirection(ResearchDirection researchDirection){
        this.setDepartmentName(researchDirection.getDepartmentName());
        this.setName(researchDirection.getName());
        this.setInfo(researchDirection.getInfo());
        this.setId(researchDirection.getId());
        this.setIfReport(researchDirection.getIfReport());
    }
}
