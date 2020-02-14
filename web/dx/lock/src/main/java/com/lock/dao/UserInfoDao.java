package com.lock.dao;

import com.a_268.base.core.BaseDao;
import com.lock.entity.UserInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by caichenglong on 2017/10/25.
 */
@Component
public interface UserInfoDao extends BaseDao<UserInfo> {

    /**
     * 获取用户所有信息
     *
     * @return
     */
    public List<UserInfo> UserList();


    /**
     * 添加住房
     * @param userInfo
     */
    void addUserInfo(UserInfo userInfo);


    /**
     * 修改住房
     */

    void updateUserInfoByUserId(UserInfo userInfo);

    /**
     * 查询用户
     * @param whereSql
     * @return
     */
    List<UserInfo> queryUserInfoList(String whereSql);

    /**
     * 查询
     * @param resultMap
     * @return
     */
    List<UserInfo> queryAvailable(Map<String,Object> resultMap);

    /**
     * 查询用户id
     * @param whereSql
     * @return
     */
    int queryUserId(String whereSql);

    /**
     * 临时修改
     * @param userInfo
     */
    void updateTemUserInfoByUserId(UserInfo userInfo);

    /**
     * 更换房间
     * @param userInfo
     */
    void updateCridentialIdByUserId(UserInfo userInfo);

}
