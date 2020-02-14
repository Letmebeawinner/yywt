package com.oa.common;

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
    // sms根域名
    public final static String smsPath = propertyUtil.getProperty("sms.root");

    public final static String imagePath= propertyUtil.getProperty("image.path");//文件图片服务器地址
    public final static String informationPath = propertyUtil.getProperty("information.root");
    public final static String basePath = propertyUtil.getProperty("base.root");
}
