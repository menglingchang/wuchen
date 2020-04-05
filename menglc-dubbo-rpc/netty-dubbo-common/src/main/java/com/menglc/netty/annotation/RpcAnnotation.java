package com.menglc.netty.annotation;

import java.lang.annotation.*;

/**
 * 自定义dubbo服务注册注解
 */
@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcAnnotation {
    Class value();
}
