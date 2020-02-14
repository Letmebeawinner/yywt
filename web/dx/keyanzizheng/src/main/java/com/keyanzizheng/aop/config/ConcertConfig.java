package com.keyanzizheng.aop.config;

import com.keyanzizheng.aop.Audience;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 配置类
 * 启动AspectJ自动代理
 *
 * @author YaoZhen
 * @date 01-10, 08:40, 2018.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class ConcertConfig {

    /**
     * 声明Audience bean
     */
    @Bean
    public Audience audience() {
        return new Audience();
    }

}