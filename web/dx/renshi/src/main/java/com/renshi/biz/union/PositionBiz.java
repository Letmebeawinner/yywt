package com.renshi.biz.union;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.renshi.dao.union.PositionDao;
import com.renshi.entity.union.Position;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职位列表
 *
 * @author 268
 */
@Service
public class PositionBiz extends BaseBiz<Position, PositionDao> {
    /**
     * 职位列表
     *
     * @param position
     */
    public List<Position> getPositionList(Position position, Pagination pagination) {
        String whereSql = " 1=1";
        whereSql += " and status!=2";
        Long id = position.getId();
        if (id!=null && id > 0) {
            whereSql += " and id=" + id;
        }
        String name = position.getName();
        if (!StringUtils.isTrimEmpty(name)) {
            whereSql += " and name like '%" + name + "%'";
        }
        whereSql += " order by id desc";
        return this.find(pagination,whereSql);
    }
}
