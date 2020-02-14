package com.keyanzizheng.biz.investigate;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.keyanzizheng.biz.common.BaseHessianBiz;
import com.keyanzizheng.biz.common.HrHessianBiz;
import com.keyanzizheng.dao.investigate.InvestigateReportDao;
import com.keyanzizheng.entity.employee.Employee;
import com.keyanzizheng.entity.investigate.InvestigateReport;
import com.keyanzizheng.entity.investigate.QueryInvestigateReport;
import com.keyanzizheng.entity.investigate.QueryResearchDirection;
import com.keyanzizheng.entity.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 调研报告Biz
 *
 * @author 268
 */
@Service
public class InvestigateReportBiz extends BaseBiz<InvestigateReport, InvestigateReportDao> {
    @Autowired
    private ResearchDirectionBiz researchDirectionBiz;
    @Autowired
    private HrHessianBiz hrHessianBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    /**
     * 分页查询
     *
     */
    public List<QueryInvestigateReport> getInvestigateReportList(Pagination pagination, QueryInvestigateReport queryInvestigateReport){
        String whereSql = " 1=1";
        whereSql += " and status!=2";
        Long id =queryInvestigateReport.getId();
        if (id!=null && id > 0) {
            whereSql += " and id=" + id;
        }
        String name = queryInvestigateReport.getName();
        if (!StringUtils.isTrimEmpty(name)) {
            whereSql += " and name like '%" + name + "%'";
        }
        if(!StringUtils.isTrimEmpty(queryInvestigateReport.getDepartmentName())){
            String ids="";
            QueryResearchDirection queryResearchDirection=new QueryResearchDirection();
            queryResearchDirection.setDepartmentName(queryInvestigateReport.getDepartmentName());
            List<QueryResearchDirection> researchDirectionList = researchDirectionBiz.getResearchDirectionList(null, queryResearchDirection);
            if(!CollectionUtils.isEmpty(researchDirectionList)){
                for(QueryResearchDirection queryResearchDirection1:researchDirectionList){
                    ids+=queryResearchDirection1.getId().toString()+' ';
                }
                ids=ids.trim().replace(' ',',');
                whereSql +=" and researchId in("+ids+")";
            }else{
                return null;
            }
        }
        Integer ifPass=queryInvestigateReport.getIfPass();
        if (ifPass!=null && ifPass > 0) {
            whereSql += " and ifPass=" + ifPass;
        }
        whereSql += " order by id desc";
        List<QueryInvestigateReport> queryInvestigateReportList=new ArrayList<>();
        List<InvestigateReport> investigateReportList=this.find(pagination,whereSql);
        if(CollectionUtils.isEmpty(investigateReportList)){
            return null;
        }else {
            for(InvestigateReport investigateReport:investigateReportList){
                QueryInvestigateReport _queryInvestigateReport=this.getInvestigateReportById(investigateReport.getId());
                queryInvestigateReportList.add(_queryInvestigateReport);
            }
        }
        return queryInvestigateReportList;
    }
    /**
     * id查询
     *
     */
    public QueryInvestigateReport getInvestigateReportById(Long id){
        InvestigateReport investigateReport=this.findById(id);
        if(ObjectUtils.isNull(investigateReport)){
            return null;
        }
        QueryInvestigateReport queryInvestigateReport=new QueryInvestigateReport(investigateReport);
        QueryResearchDirection researchDirection = researchDirectionBiz.getResearchDirectionById(queryInvestigateReport.getResearchId());
        if(ObjectUtils.isNotNull(researchDirection)){
            queryInvestigateReport.setResearchName(researchDirection.getName());
            queryInvestigateReport.setDepartmentName(researchDirection.getDepartmentName());
        }
        SysUser sysUser = baseHessianBiz.querySysUserById(investigateReport.getEmployeeId());
        if(ObjectUtils.isNotNull(sysUser)){
            if(sysUser.getUserType()==2){
                Employee employee = hrHessianBiz.queryEmployeeById(sysUser.getLinkId());
                if(ObjectUtils.isNotNull(employee)){
                    queryInvestigateReport.setEmployeeName(employee.getName());
                }
            }else{
                queryInvestigateReport.setEmployeeName(sysUser.getUserName());
            }
        }
        return queryInvestigateReport;
    }
}
