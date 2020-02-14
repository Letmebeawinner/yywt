package com.jiaowu.util;


import com.a_268.base.core.BaseEntity;
import com.jiaowu.annotation.Cut;
import com.jiaowu.annotation.Like;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 该方法仅适用于包装类型，如int应写成Integer，long写成Long
 * @author lzh
 * @description
 * @create 2016-12-27 22:54
 */
public class GenerateSqlUtil {
    public static <T extends BaseEntity> String getSql(T object) {
        Class clazz = object.getClass();
        StringBuilder sql = new StringBuilder("1 = 1");
        Field [] fields = clazz.getDeclaredFields();
        sql.append(getSql2("id", object));
        sql.append(getSql2("status", object));
        try {
            for(Field field : fields) {
                String name = field.getName();
                String methodName = "get" + name.substring(0,1).toUpperCase() + name.substring(1);
                Method method = clazz.getMethod(methodName);
                Object o = method.invoke(object);
                if (o == null || o.equals("")) {
                    continue;
                } else {
                    if (field.isAnnotationPresent(Cut.class)) {
                        continue;
                    }
                    boolean flag = field.isAnnotationPresent(Like.class);
                    if (flag) {
                        sql.append(" and " + name + " like '%" + method.invoke(object).toString().trim() + "%'");
                    } else {
                        sql.append(" and " + name + " = '" + method.invoke(object).toString().trim() + "'");
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成拼接sql语句异常");
        }

        return sql.toString();
    }

    private static String getSql2(String name, Object object){
        StringBuilder sql = new StringBuilder("");
        Class clazz = object.getClass();
        String methodName = "get" + name.substring(0,1).toUpperCase() + name.substring(1);
        try {
            Method m = clazz.getMethod(methodName);
            Object o = m.invoke(object);
            if (o != null && !o.equals("")) {
                sql.append(" and " + name + " = " + o);
            }
        } catch(Exception e) {
           e.printStackTrace();
            throw new RuntimeException("生成拼接sql语句异常");
        }
        return sql.toString();
    }
}
