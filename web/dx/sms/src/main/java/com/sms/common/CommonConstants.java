package com.sms.common;

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
    //项目本身域名
    public final static String imagePath= propertyUtil.getProperty("image.path");
    //上传域名
    public final static String basePath= propertyUtil.getProperty("base.path");
    //所有的配置在缓存中的Key
    public final static  String ALL_CONFIG_KEY="sms_redis_all_cinfog";
    // sms根域名
    public final static String smsPath = contextPath;
}
