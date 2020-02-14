package com.tongji.biz.common;

import com.a_268.base.core.Pagination;

import java.util.Map;

/**
 * 科研资政管理系统Hessian接口
 *
 * @author s.li
 * @create 2016-12-16-16:07
 */
public interface KyHessianService {


    /**
     * @Description: 获得某用户的全部成果
     * @author: xiayong
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     * @Date: 2016/12/26
     */
    Map<String, String> queryAllResult(String result, String pagination);

    /**
     * 科研咨政统计结果
     *
     * @param type       类型. 1.科研 2.咨政 0.所有
     * @param pagination 分页
     * @param year       年份(required)
     * @param month      月份(optional)
     * @Description: 科研成果数据统计
     * @author: xiayong
     * @Return: Map<String,Object>
     * @Date: 2017/2/27
     */
    Map<String, Object> resultStatistics(Integer type, Pagination pagination, Integer year, Integer month);

}
