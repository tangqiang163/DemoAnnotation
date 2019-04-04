package com.sixeco.order.module.order.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 订单来源枚举
 *
 * @author: Zhanghe
 * @date: 2019-04-01
 */
@NoArgsConstructor
@AllArgsConstructor
public enum OrderSourceEnum {

    // 订单来源
    IOS("A1", "IOS"),
    COOL_SCREEN("A2", "酷屏"),
    MINI_APP("A3", "工会小程序"),
    TDC("A4", "TDC"),
    SHOP("A5", "商城");

    @Getter
    private String code;

    @Getter
    private String message;
}
