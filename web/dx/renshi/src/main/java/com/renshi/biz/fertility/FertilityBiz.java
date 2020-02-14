package com.renshi.biz.fertility;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.renshi.biz.common.BaseHessianBiz;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.dao.fertility.FertilityDao;
import com.renshi.entity.employee.Employee;
import com.renshi.entity.fertility.Fertility;
import com.renshi.entity.fertility.QueryFertility;
import com.renshi.entity.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 计生Biz
 *
 * @author 268
 */
@Service
public class FertilityBiz extends BaseBiz<Fertility, FertilityDao> {
    @Autowired
    private EmployeeBiz employeeBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    /**
     * 计生列表
     *
     * @param queryFertility
     */
    public List<QueryFertility> getFertilityList(QueryFertility queryFertility, Pagination pagination) {
        String whereSql = " 1=1";
        whereSql += " and status!=2";
        Long id = queryFertility.getId();
        if (id!=null && id > 0) {
            whereSql += " and id=" + id;
        }
        Integer ifPass = queryFertility.getIfPass();
        if (ifPass!=null && ifPass > 0) {
            whereSql += " and ifPass=" + ifPass;
        }
        String name = queryFertility.getName();
        if (!StringUtils.isTrimEmpty(name)) {
            whereSql += " and name like '" + name+"'";
        }
        String epmloyeeName=queryFertility.getEmployeeName();
        if (!StringUtils.isTrimEmpty(epmloyeeName)) {
            String ids="";
            String _whereSql=" name like '%" + epmloyeeName + "%'";
            List<Employee> employeeList=employeeBiz.find(null,_whereSql);
            if(!CollectionUtils.isEmpty(employeeList)){
                for(Employee employee:employeeList){
                    ids+=employee.getId().toString()+' ';
                }
                ids=ids.trim().replace(' ',',');
            }else{
                return  null;
            }
            whereSql+=" and employeeId in("+ids+")";
        }
        whereSql += " order by id desc";
        List<Fertility> list=this.find(pagination,whereSql);
        List<QueryFertility> queryFertilityList=new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for(Fertility fertility:list){
                QueryFertility _queryFertility=new QueryFertility(fertility);
                SysUser sysUser=baseHessianBiz.querySysUserById(fertility.getEmployeeId());
                if(ObjectUtils.isNotNull(sysUser)) {
                    if (sysUser.getUserType() == 2) {
                        Employee employee = employeeBiz.findById(sysUser.getLinkId());
                        if (ObjectUtils.isNotNull(employee)) {
                            _queryFertility.setEmployeeName(employee.getName());
                        }
                    } else {
                        _queryFertility.setEmployeeName(sysUser.getUserName());
                    }
                }
                queryFertilityList.add(_queryFertility);
            }
        }
        return queryFertilityList;
    }
}
