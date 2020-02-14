package com.renshi.biz.union;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.renshi.dao.union.MaterialsDao;
import com.renshi.entity.union.Materials;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 工会物资Biz
 *
 * @author 268
 */
@Service
public class MaterialsBiz extends BaseBiz<Materials, MaterialsDao> {
    /**
     * 工会物资列表
     *
     * @param materials
     */
    public List<Materials> getMaterialsList(Materials materials, Pagination pagination) {
        String whereSql = " 1=1";
        whereSql += " and status!=2";
        Long id = materials.getId();
        if (id!=null && id > 0) {
            whereSql += " and id=" + id;
        }
        String createTime=materials.getMaterial();
        if(!StringUtils.isTrimEmpty(createTime)){
            whereSql += " and left(createTime,10) like left('"+createTime+"',10)";
        }
        whereSql += " order by id desc";
        return this.find(pagination,whereSql);
    }
}
