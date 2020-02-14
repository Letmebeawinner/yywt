package com.yicardtong.biz.consume;

import java.util.List;
import java.util.Map;

/**
 * 考勤数据Service
 *
 * @author s.li
 * @create 2017-02-24-13:39
 */
public interface ConsumeService {

    /**
     * 查询消费记录
     * @param sql 查询Sql语句
     * @return 考勤记录列表
     */
    List<Map<String,String>> queryConsumeList(String sql);


    /**
     * 查询消费的原纪录
     */

    List<Map<String,String>> queryConsumeSourceList(String sql);
}
