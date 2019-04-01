package com.sixeco.order.module.order.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 订单状态枚举
 *
 * @author: Zhanghe
 * @date: 2019-04-01
 */
@NoArgsConstructor
@AllArgsConstructor
public enum OrderStatusEnum {

    PENDING_PAYMENT(1, "待付款"),
    CANCELLED(2, "已取消"),
    DEPOSIT_PAID(3, "已付定金"),
    TO_BE_SHIPPED(4, "待发货"),
    CLEARED_UP(5, "已结清"),
    PAYMENTS_OUTSTANDING(6, "已付款未结清"),
    REFUNDED(7, "已退款"),
    SHIPPED(8, "已发货"),
    REFUND(9, "退款中"),
    CONFIRMED_CONTRACT(10, "已确认合同");

    @Getter
    private Integer code;

    @Getter
    private String message;

}
