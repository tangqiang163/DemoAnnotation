package com.sixeco.order.base.annotation;

import java.lang.annotation.*;

/**
 * 加密返回注解(方法，类)
 *
 * @author: Zhanghe
 * @date: 2019-04-04
 */
@Target({ElementType.METHOD , ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EncryptResponse {

    boolean value() default true;
}
