package com.sixeco.order.module.order.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 订单类型枚举
 *
 * @author: Zhanghe
 * @date: 2019-04-01
 */
@NoArgsConstructor
@AllArgsConstructor
public enum OrderTypeEnum {

    CAR(0, "车"),
    SERVICE(1, "服务"),
    INSURANCE(2, "保险"),
    ELITE(3, "精品"),
    INTENTION(4, "意向金"),
    SECOND_HAND(5, "二手车");

    @Getter
    private Integer code;

    @Getter
    private String message;
}
