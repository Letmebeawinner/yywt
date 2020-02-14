package com.yizhilu.os.res.commmon;

import com.a_268.base.util.PropertyUtil;

/**
 * 常量属性对象
 *
 * @author s.li
 * @create 2016-12-15-17:43
 */
public class CommonConstants {

    /***
     * 加载配置文件
     */
    private static PropertyUtil propertyUtil = PropertyUtil.getInstance("config");

    /**
     * 获取项目域名
     */
    public static String contextPath = propertyUtil.getProperty("context.path");

    /**
     * 获取上传根目录
     */
    public static String uploadRootDir = propertyUtil.getProperty("uploadroot.dir");

    /**
     * 文件访问域名
     */
    public static String imagePath = propertyUtil.getProperty("image.path");
}
