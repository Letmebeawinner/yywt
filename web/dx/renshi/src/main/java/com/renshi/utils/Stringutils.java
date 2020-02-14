package com.renshi.utils;

import com.a_268.base.util.CollectionUtils;

import java.util.Collection;

/**
 * 字符串
 *
 * @author YaoZhen
 * @date 04-10, 17:03, 2018.
 */
public class Stringutils {
    /**
     * 给每个元素加上单引号
     * 去掉最后一个逗号
     * @param list 请确保参数不为空, 否则会报NPE
     */
    public static String listToString(Collection<?> list) {
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Object[] temp = list.toArray();
        for (Object aTemp : temp) {
            if (!"".equals(aTemp) && aTemp != null) {
                sb.append("'").append(aTemp.toString().replace(" ","")).append("',");
                
            }
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
