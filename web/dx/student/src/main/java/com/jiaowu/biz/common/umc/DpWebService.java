package com.jiaowu.biz.common.umc;

import java.util.Date;

/**
 * wifi账号
 *
 * @author YaoZhen
 * @date 03-28, 16:05, 2018.
 */
public interface DpWebService {
    /**
     * 添加UMC 的wifi
     *  UMC添加有三种情况
     *      1. 添加成功
     *      2. 用户已存在
     *      3. 参数不匹配
     *  本地DB在UMC.1和2.1时, 添加有三种情况
     *      1. 添加成功
     *      2. 用户已存在
     *      3. 添加失败
     *
     * @param phone 手机号就是账号
     * @param realName 真实姓名
     * @param classEndTIme 班级结束时间
     * @param oldId jiaowu_user_id(base_user_linkid)
     * @return 是否添加成功
     */
    boolean saveWifiUser(String phone, String realName, Date classEndTIme, Long oldId);

    /**
     * 删除UMC中的用户
     * @param phone 用户id
     * @return 是否删除
     */
    boolean delWifi(String phone);

    /**
     * 编辑UMC用户
     * @param password 页面隐藏域传值
     * @param id 页面隐藏域传值
     * @return 是否成功
     */
    boolean editWifi(String account, String password, long overdueTime, String phone, Long id);

    /**
     * 重置用户的密码
     * @param wifiUserId 本库wifi用户主键
     * @return 是否重置
     */
    boolean resetUserPassWord(Long wifiUserId);
}
