package com.a_268.base.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

/**
 * AJAX请求返回数据中将时间对象转换成yyyy-MM-dd HH:mm:ss
 *
 * @author s.li
 * @create 2016-12-10-17:02
 */
public class ObjectMapperUtils extends ObjectMapper{

    public ObjectMapperUtils(){
        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
}
