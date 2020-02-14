package com.zaixianxuexi.common;

import com.a_268.base.util.PropertyUtil;

/**
 * 公共常量属性对象
 *
 * @author s.li
 * @create 2017-02-14-10:13
 */
public class CommonConstants {
    //获取属性配置文件
    private static PropertyUtil propertyUtil = PropertyUtil.getInstance("config");
    //项目本身域名
    public final static String contextPath = propertyUtil.getProperty("context.path");
}
