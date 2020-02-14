package com.oa.biz.countryside;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.oa.dao.countryside.CountrysideDao;
import com.oa.entity.countryside.Countryside;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 下乡Biz
 *
 * @author 268
 */
@Service
public class CountrysideBiz extends BaseBiz<Countryside, CountrysideDao> {
    /**
     * 下乡列表
     *
     * @param countryside
     */
    public List<Countryside> getCountrysideList(Countryside countryside, Pagination pagination) {
        String whereSql = " 1=1";
        whereSql += " and status!=2";
        Long id = countryside.getId();
        if (id!=null && id > 0) {
            whereSql += " and id=" + id;
        }
        whereSql += " order by id desc";
        return this.find(pagination,whereSql);
    }
}
