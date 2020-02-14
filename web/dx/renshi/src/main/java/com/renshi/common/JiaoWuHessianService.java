package com.renshi.common;

import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * 教务Hessian
 *
 * @author 李帅雷
 */
public interface JiaoWuHessianService {

    /**
     * 教师列表
     * @param pagination
     * @param whereSql
     * @return
     */
//	public Map<String,Object> teacherList(Pagination pagination, String whereSql);

    /**
     * @Description 根据ID获取教师
     * @param id
     * @return
     */
//	public Map<String,String> getTeacherById(Long id);

    /**
     * 学员列表
     *
     * @param pagination
     * @param whereSql
     * @return
     */
    public Map<String, Object> userList(Pagination pagination, String whereSql);

    /**
     * @param id
     * @return
     * @Description 根据ID获取学员
     */
    public Map<String, String> getUserById(Long id);

    /**
     * @param num
     * @return
     * @Description 获取最新的教学动态
     */
    public List<Map<String, String>> getLatestTeachingInfo(Long num);

    /**
     * @param id
     * @return
     * @Description 查询教学动态详情
     */
    public Map<String, String> getTeachingInfoById(Long id);

    /**
     * 教学动态列表
     *
     * @param pagination
     * @param whereSql
     * @return
     */
    public Map<String, Object> teachingInfoList(Pagination pagination, String whereSql);

    /**
     * 获取点击量最高的教学动态
     *
     * @param num
     * @return
     */
    public List<Map<String, String>> hotTeachingInfoList(Long num);

    /**
     * 增加教学动态的点击量
     *
     * @param id
     */
    public void addClickTimes(Long id);

    /**
     * 根据学员ID集合获取学员信息
     *
     * @param studentIds
     * @return
     */
    public List<Map<String, String>> queryStudentByIds(List<Long> studentIds);

    /**
     * 获取班次列表
     *
     * @param pagination
     * @param sql
     * @return
     */
    public Map<String, Object> queryClassesList(Pagination pagination, String sql);

    /**
     * 查询班次类型
     *
     * @return {@code List<String>} 班次类型集合
     * @since 2017-02-28
     */
    Map<String, Object> listClassType();

    /**
     * 查询所有的班次
     *
     * @return
     */
    Map<String, Object> listClasses();


    /**
     * 查询个人的考勤记录
     */
    Map<String, Object> listHolidayByUserId(Pagination pagination, String userId);

    /***
     * 查询打卡记录
     *
     * @param pagination
     * @param whereSql
     * @return
     */
    Map<String, String> listUserWorkDayData(Pagination pagination, String whereSql);

}
