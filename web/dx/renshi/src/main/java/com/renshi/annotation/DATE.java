package com.renshi.annotation;

import java.lang.annotation.ElementType;

/**
 * Created by lzh on 2017/12/8.
 * 十年生死两茫茫，不思量，自难忘，千里孤魂，无处话凄凉
 * 日期注解，用于生成sql语句，用于开始时间可结束时间，默认为createTime >= 有该注解的字段
 * 默认为创建时间， 即加上该注解默认是创建时间大于这个时间
 */
@java.lang.annotation.Target({ElementType.FIELD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Documented
public @interface DATE {
    boolean start() default true;
    String value() default "createTime";
}
