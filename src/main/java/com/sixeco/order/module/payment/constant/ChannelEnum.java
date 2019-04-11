package com.sixeco.order.module.payment.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 支付方式枚举
 *
 * @author: Zhanghe
 * @date: 2019-04-11
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ChannelEnum {

    // 支付宝
    ALIPAY(1, ""),
    // 微信
    WECHAT(2, "WX_"),
    // 银联
    UNION(3, "UNION_");

    @Getter
    private Integer code;

    @Getter
    private String prefix;
}
