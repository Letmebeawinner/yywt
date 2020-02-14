package com.jiaowu.common;

import com.a_268.base.util.PropertyUtil;

/**
 * Created by 李帅雷 on 2017/10/17.
 */
public class CommonConstants {
    //获取属性配置文件
    private static PropertyUtil propertyUtil = PropertyUtil.getInstance("config");
    //项目本身域名
    public final static String contextPath = propertyUtil.getProperty("context.path");
    //图片上传服务域名
    public final static String imageServicePath = propertyUtil.getProperty("image.service.path");
    //文件服务器地址
    public final static String fileRootDir= propertyUtil.getProperty("file.root.dir");
    // sms根域名
    public final static String smsPath = propertyUtil.getProperty("sms.root");

    public final static String BASE_PATH=propertyUtil.getProperty("base.root");

    public final static String YICARDTONG_PATH=propertyUtil.getProperty("yicardtong.root");
    //班主任角色ID
    public static final Long CLASS_LEADER_ROLE_ID=Long.parseLong(propertyUtil.getProperty("classleader.roleid"));
}
