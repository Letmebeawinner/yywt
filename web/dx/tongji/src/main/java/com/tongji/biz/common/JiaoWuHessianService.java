package com.tongji.biz.common;

import com.a_268.base.core.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 教务Hessian
 *
 * @author 李帅雷
 */
public interface JiaoWuHessianService {


    /**
     * 学员列表
     *
     * @param pagination
     * @param whereSql
     * @return
     */
    Map<String, Object> userList(Pagination pagination, String whereSql);

    /**
     * @param id
     * @return
     * @Description 根据ID获取学员
     */
    Map<String, String> getUserById(Long id);

    /**
     * @param num
     * @return
     * @Description 获取最新的教学动态
     */
    List<Map<String, String>> getLatestTeachingInfo(Long num);

    /**
     * @param id
     * @return
     * @Description 查询教学动态详情
     */
    Map<String, String> getTeachingInfoById(Long id);

    /**
     * 教学动态列表
     *
     * @param pagination
     * @param whereSql
     * @return
     */
    Map<String, Object> teachingInfoList(Pagination pagination, String whereSql);

    /**
     * 获取点击量最高的教学动态
     *
     * @param num
     * @return
     */
    List<Map<String, String>> hotTeachingInfoList(Long num);

    /**
     * 增加教学动态的点击量
     *
     * @param id
     */
    void addClickTimes(Long id);

    /**
     * 根据学员ID集合获取学员信息
     *
     * @param studentIds
     * @return
     */
    List<Map<String, String>> queryStudentByIds(List<Long> studentIds);

    /**
     * 获取班次列表
     *
     * @param pagination
     * @param sql
     * @return
     */
    Map<String, Object> queryClassesList(Pagination pagination, String sql);

    /**
     * 查询班次类型
     *
     * @return {@code List<String>} 班次类型集合
     * @since 2017-02-28
     */
    Map<String, Object> listClassType();
}
