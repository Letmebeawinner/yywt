package com.keyanzizheng.biz.common;

import com.a_268.base.core.Pagination;

import java.util.List;
import java.util.Map;

/**
 * OA  Hession
 *
 * @author ccl
 * @create 2017-01-11-16:27
 */
public interface OAHessianService {

    /**
     * 政策法规
     *
     * @param pagination
     * @param whereSql
     * @return
     */
    public Map<String, Object> getRuleList(Pagination pagination, String whereSql);

    /**
     * 获取规章详细信息
     *
     * @param id
     * @return
     */
    public Map<String, String> getRuleById(Long id);


    /**
     * 通知公告
     *
     * @param pagination
     * @param whereSql
     * @return
     */
    public Map<String, Object> getNoticeList(Pagination pagination, String whereSql);

    /**
     * 获取通知公告信息
     *
     * @param id
     * @return
     */
    public Map<String, String> getNoticeById(Long id);

    /**
     * 获取公文列表
     *
     * @param pagination
     * @param whereSql
     * @return
     */
    Map<String, Object> getLetterList(Pagination pagination, String whereSql);

    /**
     * 获取公文通过id
     *
     * @param id
     * @return
     */
    Map<String, String> getLetterById(Long id);

    /**
     * 获取所有档案分类
     *
     * @return hashMapList
     */
    List<Map<String, String>> listArchiveType();

    /**
     * 归入OA档案
     *
     * @param json json
     * @return 档案id
     */
    Long saveArchive(String json);

    /**
     * 查看档案
     *
     * @param oaArchiveId 档案id
     * @return obj2map
     */
    Map<String, String> findArchiveById(Long oaArchiveId);
}
