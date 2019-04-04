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

    //  7充电小程序 8酷屏精品 9分笔支付 10合并下单 11开票 12意向金 13众筹 15二手车服务费 16点单(coffee) 17预约空间 18充电报装 19活动报名 20二手车
    CAR(0, "整车"),
    ELITE(1, "精品(微方)"),
    SERVICE(2, "服务"),
    CAR_SERVICE(3, "车服"),
    INSURANCE(4, "保险"),
    COUPON(5, "优惠券"),
    DEPOSIT(6, "体验车押金");

    @Getter
    private Integer code;

    @Getter
    private String message;
}
