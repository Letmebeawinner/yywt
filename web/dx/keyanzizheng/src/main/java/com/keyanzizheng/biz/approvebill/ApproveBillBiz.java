package com.keyanzizheng.biz.approvebill;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.keyanzizheng.dao.approvebill.ApproveBillDao;
import com.keyanzizheng.entity.approvebill.ApproveBill;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 审批Biz
 *
 * @author 268
 */
@Service
public class ApproveBillBiz extends BaseBiz<ApproveBill, ApproveBillDao> {
    /**
     * 分页查询
     *
     */
    public List<ApproveBill> getApproveBillList(Pagination pagination,ApproveBill approveBill){
        String whereSql = " 1=1";
        whereSql += " and status!=2";
        Long id = approveBill.getId();
        if (id!=null && id > 0) {
            whereSql += " and id=" + id;
        }
        Long resultId = approveBill.getResultId();
        if (resultId!=null && resultId > 0) {
            whereSql += " and resultId=" + resultId;
        }
        whereSql += " order by id desc";
        return  this.find(pagination,whereSql);
    }
}
