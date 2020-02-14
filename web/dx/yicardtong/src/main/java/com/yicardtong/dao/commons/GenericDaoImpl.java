package com.yicardtong.dao.commons;

import com.yicardtong.dao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.List;

/**
 * 通过用的Dao实现类
 *
 * @author s.li
 * @create 2017-02-22-9:30
 */
@Service
public class GenericDaoImpl<T> extends SpringBeanAutowiringSupport implements GenericDao<T> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<T> queryList(String sql, RowMapper<T> rowMapper) {
        List<T> resultList = jdbcTemplate.query(sql,rowMapper);
        return resultList;
    }

    public int insert(String sql){
        return jdbcTemplate.update(sql);
    }

    public int insert(String sql,Object[] args){
        int result= jdbcTemplate.update(sql,args);
        return result;
    }
}
