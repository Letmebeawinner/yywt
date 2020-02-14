package com.yicardtong.dao;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;

/**
 * 通过的Dao接口
 *
 * @author s.li
 * @create 2017-02-22-9:25
 */
public interface GenericDao<T> {

    /**
     * 查询
     * @param sql
     * @param rowMapper
     * @return 数据列表
     */
    List<T> queryList(String sql, RowMapper<T> rowMapper);



    int insert(String sql, Object[] args);

}
