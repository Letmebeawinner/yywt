package com.yicardtong.biz.common;


import java.util.Map;

public interface AttendanceHessianService {

    /**
     * 修改用户的信息
     *
     * @param generalPersonMap
     */
    public String updateGeneralPersonal(Map<String, Object> generalPersonMap);



    /**
     * 验证房间对应考勤卡是否有效
     *
     * @param BaseCardNo
     */
    public boolean queryRoomExist(String BaseCardNo);


}
