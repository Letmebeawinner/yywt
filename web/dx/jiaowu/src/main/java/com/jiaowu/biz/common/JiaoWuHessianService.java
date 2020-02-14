package com.jiaowu.biz.common;

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
     * 查询
     * @param id
     * @return
     */
    public Map<String,String> queryClassNameById(Long id);
    /**
     * 查询所有的班次
     * @return
     */
    Map<String,Object> listClasses();


    /**
     * 查询个人的考勤记录
     */
    Map<String, Object> listHolidayByUserId(Pagination pagination, String employeeNo);

    /**
     * 调研报告列表
     * @param pagination 分页
     * @param name 调研人姓名
     * @param resultType 审核处 1: 科研 2:生态文明
     * @return
     */
    Map<String, String> listResearchReport(Pagination pagination, String name, Integer resultType);

    /**
     * 调研过审
     *
     * @param id 调研id
     * @param status 过审状态
     * @param assessmentLevel 审批等级
     * @return 是否成功
     */
    Boolean juryResearchReport(Long id, Integer status, Integer assessmentLevel);

    /**
     * 查看调研信息
     *
     * @param byId 调研报告id
     * @return 字节流
     */
    byte[] findResearchReportById(byte[] byId) throws Exception;

    /**
     * 计算调研报告数量
     *
     * @param year 年份
     * @return int
     */
    int countResearchReport(String year);

    /**
     * 计算科研的调研报告数量
     *
     * @param whereTime 时间段
     * @param keYan
     * @return 0
     */
    int countResearchReportByKy(String whereTime, int keYan);

    /**
     * 成果入库
     *
     * @param researchReport json
     * @return 是否修改
     */
    Integer updateResearchReport(String researchReport);

    Map<String, String> listUserWorkDayData(Pagination pagination, String whereSql);
}
