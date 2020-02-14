package com.keyanzizheng.dao.personnel;

import com.a_268.base.core.Pagination;

import java.util.List;

/**
 * 人员列表
 *
 * @author YaoZhen
 * @create 11-21, 16:06, 2017.
 */
public interface PersonnelDao {
    /**
     * 查询人员列表 当前页的id
     */
    List<Long> distinctSysUserIds(Pagination pagination);

}
