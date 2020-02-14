package com.tongji.common;

import com.a_268.base.util.PropertyUtil;

public class CommonConstants {
	
	 //获取属性配置文件
    private static PropertyUtil propertyUtil = PropertyUtil.getInstance("config");
    //项目本身域名
    public final static String contextPath = propertyUtil.getProperty("context.path");

}
