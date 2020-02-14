package com.yicardtong.dao.workdata;

import com.a_268.base.core.BaseDao;
import com.yicardtong.entity.work.WorkSource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 查考勤记录
 *
 * @author YaoZhen
 * @date 04-04, 16:21, 2018.
 */
@Component
public interface WorkSourceDao extends BaseDao<WorkSource> {

    /**
     * 查询打卡树
     */
    List<WorkSource> queryAllWorkSource(@Param(value = "valMap") Map<String, Object> params);
}
