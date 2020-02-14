package com.oa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lzh on 2016/12/27.
 * 十年生死两茫茫，不思量，自难忘，千里孤魂，无处话凄凉
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Like {
    String value() default "";
}
