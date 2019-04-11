package com.sixeco.order.module.payment.handler;

import com.sixeco.order.model.dto.PaymentDTO;

/**
 * 回调处理抽象
 *
 * @author: Zhanghe
 * @date: 2019-04-11
 */
public interface CallbackHandler {

    void doCallback(PaymentDTO paymentDTO, Integer channel);
}
