package com.menhu.common;

import com.a_268.base.util.PropertyUtil;

/**
 * 公共属性，读取配置文件
 *
 * @author guoshiqi
 * @create 2016-12-09-17:56
 */
public class CommonConstants {
    //获取属性配置文件
    private static PropertyUtil propertyUtil = PropertyUtil.getInstance("config");
    //项目本身域名
    public final static String contextPath= propertyUtil.getProperty("context.path");
    //base域名
    public final static String baseRoot= propertyUtil.getProperty("layout.root");

    //文件服务器
    public final static String imagePath= propertyUtil.getProperty("image.path");

    public static final String VERSION = System.currentTimeMillis() + ""; // 资源文件版本号


}
