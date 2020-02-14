package com.a_268.base.extend;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 *  <code>Transient.java</code>
 *  <p>功能:实现hibernate-jpa包中的不持久化相应的get方法
 *  @author jingxue.chen
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD })
public @interface Transient {

}
