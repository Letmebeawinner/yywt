package com.jiaowu.biz.common;

import com.a_268.base.core.Pagination;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 后勤系统Hessian接口
 *
 * @author xiayong
 * @create 2016-12-16-16:07
 */
public interface HqHessionService {

    List<Map<String, String>> queryMeetingList();

    List<Map<String, String>> queryMeetingListByTimeOrName(String useTime, String turnTime, String name);

    Map<String, Object> queryHqAllMeeting(Pagination pagination, String name);

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
