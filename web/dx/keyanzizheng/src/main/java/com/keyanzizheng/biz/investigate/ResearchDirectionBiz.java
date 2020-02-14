package com.keyanzizheng.biz.investigate;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.keyanzizheng.biz.common.BaseHessianBiz;
import com.keyanzizheng.biz.common.HrHessianBiz;
import com.keyanzizheng.dao.investigate.ResearchDirectionDao;
import com.keyanzizheng.entity.employee.Employee;
import com.keyanzizheng.entity.investigate.QueryResearchDirection;
import com.keyanzizheng.entity.investigate.ResearchDirection;
import com.keyanzizheng.entity.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 调研方向Biz
 *
 * @author 268
 */
@Service
public class ResearchDirectionBiz extends BaseBiz<ResearchDirection, ResearchDirectionDao> {
    @Autowired
    private HrHessianBiz hrHessianBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    /**
     * 分页查询
     *
     */
    public List<QueryResearchDirection> getResearchDirectionList(Pagination pagination, QueryResearchDirection queryResearchDirection){
        String whereSql = " 1=1";
        whereSql += " and status!=2";
        Long id =queryResearchDirection.getId();
        if (id!=null && id > 0) {
            whereSql += " and id=" + id;
        }
        String name = queryResearchDirection.getName();
        if (!StringUtils.isTrimEmpty(name)) {
            whereSql += " and name like '%" + name + "%'";
        }
        if(!StringUtils.isTrimEmpty(queryResearchDirection.getDepartmentName())){
            whereSql += " and departmentName like '%" + queryResearchDirection.getDepartmentName() + "%'";
        }
        whereSql += " order by id desc";
        List<ResearchDirection> researchDirectionList=this.find(pagination,whereSql);
        List<QueryResearchDirection> _researchDirectionList=new ArrayList<>();
        if(CollectionUtils.isEmpty(researchDirectionList)){
            return null;
        }else{
            for(ResearchDirection researchDirection:researchDirectionList){
                QueryResearchDirection _queryResearchDirection=this.getResearchDirectionById(researchDirection.getId());
                _researchDirectionList.add(_queryResearchDirection);
            }
        }
        return _researchDirectionList;
    }
    /**
     * id查询
     *
     */
    public QueryResearchDirection getResearchDirectionById(Long id){
        ResearchDirection researchDirection=this.findById(id);
        if(ObjectUtils.isNull(researchDirection)){
            return null;
        }
        QueryResearchDirection queryResearchDirection=new QueryResearchDirection(researchDirection);
        SysUser sysUser = baseHessianBiz.querySysUserById(researchDirection.getSysUserId());
        if(ObjectUtils.isNotNull(sysUser)){
            if(sysUser.getUserType()==2){
                Employee employee = hrHessianBiz.queryEmployeeById(sysUser.getLinkId());
                if(ObjectUtils.isNotNull(employee)){
                    queryResearchDirection.setEmployeeName(employee.getName());
                }
            }else{
                queryResearchDirection.setEmployeeName(sysUser.getUserName());
            }
        }
        return queryResearchDirection;
    }
}
