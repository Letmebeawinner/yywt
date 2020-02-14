package com.houqin.common;

import com.a_268.base.core.Pagination;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lizhenhui
 * @create 2017-02-23 15:27
 */
public interface HqHessianService {

    /**
     * 年度收支统计之能源支出统计
     *
     * @param year 年份
     * @return 能源支出
     * @since 2017-03-03
     */
    Double getAnnualEnergyExpense(String year);

    /**
     * 获取用水量统计根据用户id分组
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    Map<String, Object> getWaterStatisticsGroupByUserId(Date fromDate, Date toDate, Pagination pagination);

    /**
     * 用水总量，所有的记录
     *
     * @param start
     * @param end
     * @param pagination
     * @return
     */

    Map<String, Object> getWaterStatistics(Date start, Date end, Pagination pagination);

    /**
     * 获取用电量统计根据用户id分组
     *
     * @param fromDate
     * @param toDate
     * @return
     */
    Map<String, Object> getElectricityStatisticsGroupByUserId(Date fromDate, Date toDate, Pagination pagination);

    /**
     * 用电总量，所有的用户记录
     *
     * @param start
     * @param end
     * @param pagination
     * @return
     */
    Map<String, Object> getElectricityStatistics(Date start, Date end, Pagination pagination);

    /**
     * 初始化添加报修页面
     */
    String toSaveRepair();

    /**
     * 添加报修申请
     */
    String saveRepair(String param);

    /**
     * 查询学员本人的报修
     *
     * @param repairJson 维修对象
     * @param pagination 分页对象
     * @return 个人报修列表 分页对象
     */
    Map<String, String> getRepairsDtos(String repairJson, Pagination pagination);

    /**
     * 查看报修详情
     */
    String getRepairDtosById(Long id);

    /**
     * 获取所有学员的报修申请
     *
     * @param pagination 分页对象
     * @return Json
     */
    Map<String, String> getAllStudentRepairList(String repairJson, Pagination pagination);


    /**
     * 查询所有的会场
     *
     * @return
     */
    public List<Map<String, String>> queryAllMeeting();

    /**
     * 根据学员id 查询保修记录
     *
     * @param join       学员ids
     * @param pagination 分页
     * @return 分页和报修记录
     */
    Map<String, String> queryStudentRepairRecord(String join, Pagination pagination);

    /**
     * 获取后勤会场
     *
     * @param pagination
     * @return
     */
    Map<String, Object> queryHqAllMeeting(Pagination pagination, String name);

    /**
     * 查看会场记录详情
     *
     * @param id 会场ID
     */
    Map<String, String> getMeetingForOAById(Long id);

    /**
     * 查看会场记录详情
     *
     * @param id 会场ID
     */
    Map<String, String> getMeetingRecordForOAById(Long id);

    public List<Map<String, String>> queryMeetingList();

    /**
     * 时间备用
     *
     * @param useTime
     * @param turnTime
     * @param name
     * @return
     */
    List<Map<String, String>> queryMeetingListByTimeOrName(String useTime, String turnTime, String name);

    /**
     * 选择会场前，排查会场使用状态
     */
    void changeMeetingStatusWhenChose();

    /**
     * 修改会场的状态
     *
     * @param id
     * @param status 使用状态 0未使用正常  1是维修中 2是使用者中
     * @return
     */
    Map<String, String> updateMeetingStatus(Long id, Integer status);

    /**
     * 添加会场使用记录
     *
     * @param userId      用户id
     * @param meetingId   会场id
     * @param useTime     会场使用时间
     * @param turnTime    会场结束时间
     * @param description 备注说明
     * @param status      状态 0正常 1取消
     * @param classesName 班级名称
     * @param classesId   班级id
     * @return
     */
    Map<String, String> addMeetingRecordFromOther(Long userId, Long meetingId, Integer status, Long classesId, String classesName, Date useTime, Date turnTime, String description);

    /**
     * 修改会场使用记录的状态
     *
     * @param id          会场记录id
     * @param meetingId   会场id
     * @param status      状态 0正常 1取消
     * @param classesId   班级id
     * @param classesName 班级名称
     * @param useTime     会场使用时间
     * @param turnTime    会场结束时间
     * @param description 备注说明
     * @return
     */
    Map<String, String> updateMeetingRecordStatus(Long id, Long meetingId, Integer status, Long classesId, String classesName, Date useTime, Date turnTime, String description);

    /**
     * 判断这个时间段是否有使用的,添加操作不用传meetingRecordId，修改需要。别的参数都需要
     *
     * @param meetingRecordId 会场记录id
     * @param meetingId       会场id
     * @param useTime         会场使用时间
     * @param turnTime        会场结束时间
     * @param operation       ADD,UPDATE
     * @return true 有冲突，false没有冲突
     */
    Map<String, Boolean> whetherToUseDuringTheTimePeriod(Long meetingRecordId, Long meetingId, Date useTime, Date turnTime, String operation);

}
