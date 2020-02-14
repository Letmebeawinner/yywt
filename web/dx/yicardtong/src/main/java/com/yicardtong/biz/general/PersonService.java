package com.yicardtong.biz.general;

import java.util.List;
import java.util.Map;

/**
 * 考勤数据Service
 *
 * @author s.li
 * @create 2017-02-24-13:39
 */
public interface PersonService {

    /**
     * 获取的人员列表
     * @param sql 查询Sql语句
     */
    List<Map<String,String>> queryPersonList(String sql);


}
