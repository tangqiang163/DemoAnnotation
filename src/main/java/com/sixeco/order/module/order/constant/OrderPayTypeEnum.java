package com.sixeco.order.module.order.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 订单支付类型枚举
 *
 * @author: chenxiang
 * @date: 2019-04-11
 */
@NoArgsConstructor
@AllArgsConstructor
public enum OrderPayTypeEnum {

    // 支付类型0 全款支付 1 定金支付 2 分期支付 默认0
    ALL(0, "全款支付 "),
    DEPOSIT(1, "定金支付"),
    INSTALLMENT(2, "分期支付");

    @Getter
    private Integer code;

    @Getter
    private String message;
}
