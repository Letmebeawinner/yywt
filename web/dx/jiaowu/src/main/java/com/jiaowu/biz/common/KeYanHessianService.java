package com.jiaowu.biz.common;

import com.a_268.base.core.Pagination;

import java.util.Map;

/**
 * @author YaoZhen
 * @create 11-01, 11:05, 2017.
 */
public interface KeYanHessianService {

    /**
     * @Description: 获得某用户的全部成果
     * @author: xiayong
     * @Return: java.util.Map<java.lang.String,java.lang.String>
     * @Date: 2016/12/26
     */
    Map<String, String> queryAllResult(String result, String pagination);

    /**
     * @Description: 科研成果数据统计(type 1:科研 2：咨政)
     * @author: xiayong
     * @Return: Map<String,Object>
     * @Date: 2017/2/27
     */
    Map<String, Object> resultStatistics(Integer type, Pagination pagination, Integer year, Integer month);

    /**
     * 查询成果形式列表
     *
     * @return json
     */
    Map<String, String> scientificResearchResults();

    /**
     * 添加成果
     *
     * @param json result类
     * @return 是否成果
     */
    Boolean addScientificResearchResults(String json);

    /**
     * 查询个人的成果
     *
     * @param pagination 分页
     * @param sysUserId  userId
     * @param resultType 1科研 2咨政
     * @param yearOrMonthly 咨政的年度/月度
     * @param resultName 课题名称
     * @return 成果列表
     */
    Map<String, String> queryResultListByUserId(Pagination pagination, Long sysUserId, Integer resultType, Integer yearOrMonthly,
                                                String resultName);

    /**
     * 查询成果详情
     * @param id id
     * @return result json
     */
    String getResultById(Long id);

    /**
     * 添加课题结项
     */
    Boolean addProblemStatementDeclaration(byte[] byUrl, byte[] byId);

    /**
     * 获奖申报
     *
     * @param params
     * 附件地址 用户名称 用户Id 附件标题
     * 成果形式 获奖情况 获奖描述
     * @return 是否成功
     */
    Boolean addAwardDeclaration(Map<String, Object> params);

    /**
     * 获奖申报列表
     *
     * @param id userId
     * @return json
     */
    String listAwardByUserId(byte[] id);

    /**
     * 查询获奖
     *
     * @param id 获奖id
     * @return 详情
     */
    Map<String, String> findAwardById(Long id);
}
