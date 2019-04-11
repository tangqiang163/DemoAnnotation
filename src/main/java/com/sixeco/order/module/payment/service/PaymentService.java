package com.sixeco.order.module.payment.service;

import com.sixeco.order.model.dto.PaymentDTO;

/**
 * 支付回调服务
 *
 * @author: Zhanghe
 * @date: 2019-04-11
 */
public interface PaymentService {

    void callBack(PaymentDTO paymentDTO);
}
