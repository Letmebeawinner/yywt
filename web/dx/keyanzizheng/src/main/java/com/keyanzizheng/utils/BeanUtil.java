package com.keyanzizheng.utils;

import org.springframework.beans.BeanUtils;

/**
 * 复制属性
 * 解耦第三方工具类
 *
 * @author YaoZhen
 * @date 01-12, 16:29, 2018.
 */
public class BeanUtil {
    /**
     * 深层复制属性归档在本数据库
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }
}
