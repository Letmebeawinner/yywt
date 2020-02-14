package com.houqin.common;

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
    //图片
    public final static String imgPath=propertyUtil.getProperty("image.path");
    //工程主管
    public final static String gczg=propertyUtil.getProperty("gczg.root");

    /**
     * 一卡通地址
     */
    public final static String cardPath = propertyUtil.getProperty("yikatong.root");
    public final static String LockPath = propertyUtil.getProperty("lock.root");
    public static final String priceWater = "priceWater";//水单价缓存
    public static final String priceElectricity = "priceElectricity";//电单价缓存
}
