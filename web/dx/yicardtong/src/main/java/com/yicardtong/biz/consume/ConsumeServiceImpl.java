package com.yicardtong.biz.consume;

import com.yicardtong.dao.commons.GenericDaoImpl;
import com.yicardtong.entity.consume.Consume;
import com.yicardtong.entity.consume.ConsumeSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 考勤Service接口实现类
 *
 * @author s.li
 * @create 2017-02-24-13:40
 */
@Service("consumeService")
public class ConsumeServiceImpl extends GenericDaoImpl<Map<String, String>> implements ConsumeService {

    /**
     * 获取的消费列表
     *
     * @param sql 查询Sql语句
     * @return
     */
    @Override
    public List<Map<String, String>> queryConsumeList(String sql) {
        return this.queryList(sql, new Consume());
    }


    /**
     * 查询消费的原纪录
     */
    @Override
    public List<Map<String, String>> queryConsumeSourceList(String sql) {
        return this.queryList(sql, new ConsumeSource());
    }

}
