package com.lock.biz.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lock.dao.BedRoomDao;
import com.lock.dao.UserBedroomRefDao;
import com.lock.dao.UserInfoDao;
import com.lock.dao.UserInfoDtoDao;
import com.lock.entity.BedRoom;
import com.lock.entity.UserBedroomRef;
import com.lock.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LockHessianBiz implements LockHessianService {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserInfoDtoDao userInfoDtoDao;

    @Autowired
    private UserBedroomRefDao userBedroomRefDao;
    @Autowired
    private BedRoomDao bedRoomDao;

    /**
     * 剩余房间列表
     *
     * @return
     */
    @Override
    public Map<String, Object> queryAvaliavableList() {
        //查询未住的房间
        List<UserInfo> userInfoList = userInfoDao.UserList();
        //查询管理房间
        List<UserBedroomRef> userBedroomRefList = userBedroomRefDao.queryUserList();

        List<UserInfo> newUserInfoList = new ArrayList<UserInfo>();

        for (int i = 0; i < userInfoList.size(); i++) {
            for (int j = 0; j < userBedroomRefList.size(); j++) {
                if (userInfoList.get(i).getUserId() == userBedroomRefList.get(j).getUserId()) {
                    newUserInfoList.add(userInfoList.get(i));
                }
            }
        }
        userInfoList.removeAll(newUserInfoList);
        Map<String, Object> map = new HashMap<>();
        map.put("userInfoList", userInfoList);
        return map;

    }


    /**
     * 查询房间
     *
     * @param bedRoomId
     * @return
     */
    @Override
    public Map<String, Object> queryBedRoomList(String bedRoomId) {
        //查询未住的房间
        List<UserInfo> userInfoList = userInfoDao.queryUserInfoList(bedRoomId);
        Map<String, Object> map = new HashMap<>();
        map.put("userInfoList", userInfoList);
        return map;
    }

    /**
     * 修改用户的信息
     *
     * @param userInfoMap
     */
    @Override
    public String updateBaseUserInfo(Map<String, Object> userInfoMap) throws Exception {

        String userId = userInfoMap.get("userId").toString();
        String userName = userInfoMap.get("userName").toString();
        String BedchamberId = userInfoMap.get("BedchamberId").toString();
        String identityNo = userInfoMap.get("identityNo").toString();
        String ExpiredTime = userInfoMap.get("ExpiredTime").toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setUserId(Integer.parseInt(userId));
        userInfo.setBedchamberId(Integer.parseInt(BedchamberId));
        userInfo.setIdentityNo(identityNo);
//        userInfo.setExpiredTime(sdf.parse(ExpiredTime));
        userInfoDao.updateUserInfoByUserId(userInfo);
        return null;
    }

    /**
     * 更换房间
     *
     * @param userInfoMap
     */
    @Override
    public Boolean updateCridentialIdByUserId(Map<String, Object> userInfoMap) throws Exception {
        boolean flag = false;
        String userId = userInfoMap.get("userId").toString();
        String BedchamberId = userInfoMap.get("BedchamberId").toString();
        String CridentialId = userInfoMap.get("CridentialId").toString();

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Integer.parseInt(userId));
        userInfo.setBedchamberId(Integer.parseInt(BedchamberId));
        userInfo.setCridentialId(CridentialId);
        userInfoDao.updateCridentialIdByUserId(userInfo);
        flag = true;
        return flag;
    }

    /**
     * 根据id查询到房间信息
     *
     * @param whereSql
     * @return
     */
    @Override
    public Map<String, Object> queryUserInfoList(String whereSql) {
        List<UserInfo> userInfoList = userInfoDao.queryUserInfoList(whereSql);
        Map<String, Object> map = new HashMap<>();
        map.put("userInfoList", userInfoList);
        return map;
    }

    /**
     * 查询可用房间通过查询条件
     *
     * @return
     */
    @Override
    public Map<String, Object> queryAvaliableRoomByRoom(Map<String, Object> userInfoMap) {
        List<UserInfo> userInfoList = userInfoDao.queryAvailable(userInfoMap);
        Map<String, Object> map = new HashMap<>();
        map.put("userInfoList", userInfoList);
        return map;
    }

    @Override
    public Map<String, String> queryBedRoomByName(String whereSql) {
        Map<String, String> map = new HashMap<>();
        BedRoom bedRoom = bedRoomDao.queryBedRoomByName(whereSql);
        map.put("id", bedRoom.getId().toString());
        map.put("Name", bedRoom.getName());
        map.put("code", bedRoom.getCode());
        return map;
    }

}
