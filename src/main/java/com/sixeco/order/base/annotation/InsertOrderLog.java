package com.sixeco.order.base.annotation;

import java.lang.annotation.*;

/**
 * 插入订单日志注解
 *
 * @author: Zhanghe
 * @date: 2019-04-03
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InsertOrderLog {
}
