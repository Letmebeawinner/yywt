package com.menhu.biz.common;

import com.a_268.base.core.Pagination;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * OA  Hession
 */
public interface OAHessianService {

    /**
     * 政策法规
     * @param pagination
     * @param whereSql
     * @return
     */
    public Map<String,Object> getRuleList(Pagination pagination, String whereSql);

    /**
     * 获取规章详细信息
     * @param id
     * @return
     */
    public Map<String,String> getRuleById(Long id);


    /**
     * 通知公告
     * @param pagination
     * @param whereSql
     * @return
     */
    public Map<String,Object> getNoticeList(Pagination pagination, String whereSql);

    /**
     * 获取通知公告信息
     * @param id
     * @return
     */
    public Map<String,String> getNoticeById(Long id);






}
