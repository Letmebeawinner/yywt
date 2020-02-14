package com.lock.biz.common;


import java.util.Map;

public interface LockHessianService {

    /**
     * 剩余房间列表
     *
     * @return
     */
    public Map<String, Object> queryAvaliavableList();

    /**
     * 查询房间
     *
     * @param bedRoomId
     * @return
     */
    public Map<String, Object> queryBedRoomList(String bedRoomId);

    /**
     * 修改用户的信息
     *
     * @param userInfoMap
     * @return
     */
    public String updateBaseUserInfo(Map<String, Object> userInfoMap) throws Exception;

    /**
     * 后勤更换房间
     *
     * @param userInfoMap
     * @return
     */
    public Boolean updateCridentialIdByUserId(Map<String, Object> userInfoMap) throws Exception;

    /**
     * 查询可用房间通过查询条件
     *
     * @return
     */
    public Map<String, Object> queryAvaliableRoomByRoom(Map<String, Object> userInfoMap);

    /**
     * 根据id查询到房间信息
     *
     * @param whereSql
     * @return
     */
    public Map<String, Object> queryUserInfoList(String whereSql);

    /**
     * 根据房间名字查询到房间信息
     *
     * @param whereSql
     * @return
     */
    Map<String, String> queryBedRoomByName(String whereSql);


}
