package com.keyanzizheng.entity.investigate;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 调研报告
 * Created by tiger on 2016/12/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryInvestigateReport extends InvestigateReport {

    private static final long serialVersionUID = 3972012720946900681L;
    private String researchName; //调研方向名
    private String EmployeeName; //提交人姓名
    private String departmentName; //部门名称
    public QueryInvestigateReport (){}
    public QueryInvestigateReport (InvestigateReport investigateReport){
        this.setId(investigateReport.getId());
        this.setEmployeeId(investigateReport.getEmployeeId());
        this.setResearchId(investigateReport.getResearchId());
        this.setRelatedUrl(investigateReport.getRelatedUrl());
        this.setName(investigateReport.getName());
        this.setInfo(investigateReport.getInfo());
        this.setIfPass(investigateReport.getIfPass());
    }
}
