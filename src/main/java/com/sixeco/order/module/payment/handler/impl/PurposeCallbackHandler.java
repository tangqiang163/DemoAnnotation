package com.sixeco.order.module.payment.handler.impl;

import com.sixeco.order.model.Payment;
import com.sixeco.order.model.dto.PaymentDTO;
import com.sixeco.order.module.payment.handler.CallbackHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 意向金回调处理
 * @author: Zhanghe
 * @date: 2019-04-11
 */
@Component
@Slf4j
public class PurposeCallbackHandler implements CallbackHandler {

    @Override
    public void doCallback(PaymentDTO paymentDTO, Integer channel) {
        //查找意向订单
    }
}
