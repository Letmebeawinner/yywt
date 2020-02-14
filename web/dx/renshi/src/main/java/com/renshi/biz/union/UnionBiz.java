package com.renshi.biz.union;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.renshi.dao.union.UnionDao;
import com.renshi.entity.union.Union;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工会Biz
 *
 * @author 268
 */
@Service
public class UnionBiz extends BaseBiz<Union, UnionDao> {
    /**
     * 工会-列表
     *
     * @param union
     */
    public List<Union> getUnionList(Union union, Pagination pagination) {
        String whereSql = " 1=1";
        whereSql += " and status!=2";
        Long id = union.getId();
        if (id!=null && id > 0) {
            whereSql += " and id=" + id;
        }
        String name = union.getName();
        if (!StringUtils.isTrimEmpty(name)) {
            whereSql += " and name like '%" + name + "%'";
        }
        whereSql += " order by id";
        return this.find(pagination,whereSql);
    }
}
