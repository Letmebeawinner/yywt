package com.base.common;

import com.a_268.base.util.PropertyUtil;

/**
 * 公共常量属性对象
 *
 * @author s.li
 */
public class CommonConstants {
    //获取属性配置文件
    private static PropertyUtil propertyUtil = PropertyUtil.getInstance("config");
    //项目本身域名
    public final static String contextPath = propertyUtil.getProperty("context.path");
    //图片上传服务域名
    public final static String imageServicePath = propertyUtil.getProperty("image.service.path");
    // sms根域名
    public final static String smsPath = propertyUtil.getProperty("sms.root");
    // 当前系统Key的前缀
    public final static String THIS_SYS_KEY_PX = "this_sys_key_px_";
    //在线学习
    public final static String SSOPath=propertyUtil.getProperty("ZXXY");
    public final static String oaPath=propertyUtil.getProperty("oa.root");
    public final static String kyPath=propertyUtil.getProperty("ky.root");
    public final static String zzPath=propertyUtil.getProperty("zz.root");

}
