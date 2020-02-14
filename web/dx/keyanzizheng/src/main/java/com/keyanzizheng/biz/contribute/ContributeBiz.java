package com.keyanzizheng.biz.contribute;


import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.keyanzizheng.biz.common.BaseHessianBiz;
import com.keyanzizheng.biz.common.HrHessianBiz;
import com.keyanzizheng.dao.contribute.ContributeDao;
import com.keyanzizheng.entity.contribute.Contribute;
import com.keyanzizheng.entity.contribute.QueryContribute;
import com.keyanzizheng.entity.employee.Employee;
import com.keyanzizheng.entity.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 投稿Biz
 *
 * @author 268
 */
@Service
public class ContributeBiz extends BaseBiz<Contribute, ContributeDao> {
    @Autowired
    private HrHessianBiz hrHessianBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    /**
     * 分页查询
     *
     */
    public List<QueryContribute> getContributeList(Pagination pagination,QueryContribute contribute){
        String whereSql = " 1=1";
        whereSql += " and status!=2";
        Long id =contribute.getId();
        if (id!=null && id > 0) {
            whereSql += " and id=" + id;
        }
        Long employeeId =contribute.getEmployeeId();
        if (employeeId!=null && employeeId > 0) {
            whereSql += " and employeeId=" + employeeId;
        }
        String name=contribute.getName();
        if(!StringUtils.isTrimEmpty(name)){
            whereSql += " and name like '%" + name+"%'";
        }
        Integer ifPass =contribute.getIfPass();
        if (ifPass!=null && ifPass > 0) {
            whereSql += " and ifPass=" + ifPass;
        }
        whereSql += " order by id desc";
        List<Contribute> contributes=this.find(pagination,whereSql);
        List<QueryContribute> contributeList=new ArrayList<>();
        if(CollectionUtils.isEmpty(contributes)){
            return null;
        }else{
            for(Contribute _contribute:contributes){
                QueryContribute queryContribute=new QueryContribute(_contribute);
                SysUser sysUser = baseHessianBiz.querySysUserById(_contribute.getEmployeeId());
                if(ObjectUtils.isNotNull(sysUser)){
                    if(sysUser.getUserType()==2){
                        Employee employee = hrHessianBiz.queryEmployeeById(sysUser.getLinkId());
                        if(ObjectUtils.isNotNull(employee)){
                            queryContribute.setEmployeeName(employee.getName());
                        }
                    }else{
                        queryContribute.setEmployeeName(sysUser.getUserName());
                    }
                }
                contributeList.add(queryContribute);
            }
        }
        return contributeList;
    }
}
