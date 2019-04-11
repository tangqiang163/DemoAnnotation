package com.sixeco.order.module.payment.service.impl;

import com.google.common.base.Strings;
import com.sixeco.order.model.Payment;
import com.sixeco.order.model.dto.PaymentDTO;
import com.sixeco.order.module.order.constant.OrderTypeEnum;
import com.sixeco.order.module.payment.constant.ChannelEnum;
import com.sixeco.order.module.payment.handler.CallbackHandler;
import com.sixeco.order.module.payment.handler.impl.NormalCallbackHandler;
import com.sixeco.order.module.payment.handler.impl.PurposeCallbackHandler;
import com.sixeco.order.module.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 支付回调服务实现
 *
 * @author: Zhanghe
 * @date: 2019-04-11
 */
@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Override
    public void callBack(PaymentDTO paymentDTO) {
        //判断支付渠道
        //默认支付宝
        int channel = ChannelEnum.ALIPAY.getCode();
        if (Strings.isNullOrEmpty(paymentDTO.getChannelType())) {
            if (paymentDTO.getChannelType().contains(ChannelEnum.WECHAT.getPrefix())) {
                channel = ChannelEnum.WECHAT.getCode();
            } else if (paymentDTO.getChannelType().contains(ChannelEnum.UNION.getPrefix())) {
                channel = ChannelEnum.UNION.getCode();
            }
        }
        //订单回调处理
        CallbackHandler callbackHandler;
        if (OrderTypeEnum.PURPOSE.getCode().equals(paymentDTO.getOrderType())) {
            callbackHandler = new PurposeCallbackHandler();
        } else {
            callbackHandler = new NormalCallbackHandler();
        }
        callbackHandler.doCallback(paymentDTO, channel);
    }
}
