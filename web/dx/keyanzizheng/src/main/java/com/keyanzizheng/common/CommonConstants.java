package com.keyanzizheng.common;

import com.a_268.base.util.PropertyUtil;

/**
 * 公共常量属性对象
 * @author  s.li
 */
public class CommonConstants {
    //获取属性配置文件
    private static PropertyUtil propertyUtil = PropertyUtil.getInstance("config");
    //项目本身域名
    public final static String contextPath= propertyUtil.getProperty("context.path");
    //图片上传服务域名
    public final static String imageServicePath= propertyUtil.getProperty("image.service.path");
    //文件上传服务域名
    public final static String fileServicePath= propertyUtil.getProperty("file.service.path");
    //sms根域名
    public final static String smsPath = propertyUtil.getProperty("sms.root");
    //基础系统
    public final static String basePath = propertyUtil.getProperty("base.root");
}
