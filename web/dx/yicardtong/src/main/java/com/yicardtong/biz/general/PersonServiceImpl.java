package com.yicardtong.biz.general;

import com.yicardtong.dao.commons.GenericDaoImpl;
import com.yicardtong.entity.general.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 考勤Service接口实现类
 *
 * @author s.li
 * @create 2017-02-24-13:40
 */
@Service("personService")
public class PersonServiceImpl extends GenericDaoImpl<Map<String, String>> implements PersonService {

    /**
     * 获取的人员列表
     *
     * @param sql 查询Sql语句
     * @return
     */
    @Override
    public List<Map<String, String>> queryPersonList(String sql) {
        return this.queryList(sql, new Person());
    }




}
