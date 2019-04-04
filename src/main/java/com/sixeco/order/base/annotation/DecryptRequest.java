package com.sixeco.order.base.annotation;

import java.lang.annotation.*;

/**
 * 解密请求注解(方法，类)
 *
 * @author: Zhanghe
 * @date: 2019-04-04
 */
@Target({ElementType.METHOD , ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DecryptRequest {

    boolean value() default true;
}
