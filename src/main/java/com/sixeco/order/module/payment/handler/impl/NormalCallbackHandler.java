package com.sixeco.order.module.payment.handler.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Iterables;
import com.sixeco.order.mapper.MainOrderMapper;
import com.sixeco.order.mapper.PaymentMapper;
import com.sixeco.order.mapper.SeparatePaymentMapper;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.Payment;
import com.sixeco.order.model.SeparatePayment;
import com.sixeco.order.model.dto.PaymentDTO;
import com.sixeco.order.model.vo.CarOrderItemVO;
import com.sixeco.order.model.vo.MainOrderVO;
import com.sixeco.order.model.vo.SubOrderVO;
import com.sixeco.order.module.order.service.OrderService;
import com.sixeco.order.module.payment.handler.CallbackHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 常规回调处理
 *
 * @author: Zhanghe
 * @date: 2019-04-11
 */
@Component
@Slf4j
public class NormalCallbackHandler implements CallbackHandler {

    @Autowired
    private SeparatePaymentMapper separatePaymentMapper;

    @Autowired
    private MainOrderMapper mainOrderMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrderService orderService;

    @Override
    public void doCallback(PaymentDTO paymentDTO, Integer channel) {
        //判断是否分笔订单
        //查询订单数据
        String orderSubNo = null;
        MainOrder mainOrder = MainOrder.builder().build();
        //此处定义混乱，需要调整
        SeparatePayment separatePayment = null;
        if(paymentDTO.getMainOrderNo().length() >= 20) {
            //位数大于20则为分笔支付订单号
            orderSubNo = paymentDTO.getMainOrderNo();
            QueryWrapper<SeparatePayment> separatePaymentQueryWrapper = new QueryWrapper<>();
            separatePaymentQueryWrapper.eq("main_order_no", paymentDTO.getMainOrderNo());
            separatePayment = separatePaymentMapper.selectOne(separatePaymentQueryWrapper);
            if (separatePayment == null) {
                log.error("无订单数据");
            } else {
                QueryWrapper<MainOrder> mainOrderQueryWrapper = new QueryWrapper<>();
                mainOrderQueryWrapper.eq("main_order_no", separatePayment.getMainOrderNo());
                mainOrder = mainOrderMapper.selectOne(mainOrderQueryWrapper);
                if (mainOrder == null) {
                    log.error("无订单数据");
                }
            }
        } else {
            QueryWrapper<MainOrder> mainOrderQueryWrapper = new QueryWrapper<>();
            mainOrderQueryWrapper.eq("main_order_no", paymentDTO.getMainOrderNo());
            mainOrder = mainOrderMapper.selectOne(mainOrderQueryWrapper);
            if (mainOrder == null) {
                log.error("无订单数据");
            }
        }
        //查询支付信息表
        QueryWrapper<Payment> paymentQueryWrapper = new QueryWrapper<>();
        paymentQueryWrapper.eq("out_trade_no", paymentDTO.getOutTradeNo());
        List<Payment> payments = paymentMapper.selectList(paymentQueryWrapper);
        //判断是否有支付信息
        if (!Iterables.isEmpty(payments)) {
            if (payments.get(0).getStatus() == 1) {
                log.error("该订单已支付");
            }
        } else {
            //执行插入
            Payment payment = Payment.builder()
                    .mainOrderNo(paymentDTO.getMainOrderNo())
                    .outTradeNo(paymentDTO.getOutTradeNo())
                    .channel(channel)
                    .type(0)
                    .orderSubNo(orderSubNo)
                    .paymentStartTime(mainOrder.getCreateTime())
                    .payType(mainOrder.getPayType())
                    .amount(paymentDTO.getAmount())
                    .status(0).build();
            paymentMapper.insert(payment);
            //生成商户订单号
            payment.setMerOrderNo(mainOrder.getMainOrderNo() + "-" + payment.getId());
            //判断订单支付状态
            if (paymentDTO.getOrderStatus() != 2) {
                //订单支付失败
                if (separatePayment != null) {
                    separatePayment.setPayStatus(2);
                    separatePayment.setPaidTime(LocalDateTime.now());
                    separatePaymentMapper.updateById(separatePayment);
                }
                payment.setStatus(2);
                paymentMapper.updateById(payment);
                log.error("支付失败");
            } else {
                //根据主订单号获取vsn，优化成一条sql
                MainOrderVO detail = orderService.detail(mainOrder.getMainOrderNo());
                List<SubOrderVO> subOrders = detail.getSubOrders();
                List<CarOrderItemVO> cars = new ArrayList<>();
                for (SubOrderVO subOrder : subOrders) {
                    cars.addAll(subOrder.getCarOrderItems());
                }
                String carVsn = cars.get(0).getCarVsn();
                //此处代码考虑优化
                boolean flag = true;
                QueryWrapper<Payment> paymentQueryWrapper2 = new QueryWrapper<>();
                paymentQueryWrapper2.eq("main_order_no", detail.getMainOrderNo());
                List<Payment> paymentsByOrderNo = paymentMapper.selectList(paymentQueryWrapper2);
                if (!Iterables.isEmpty(paymentsByOrderNo)) {
                    flag = false;
                }
                if (mainOrder.getSplitFlag()) {
                    QueryWrapper<SeparatePayment> separatePaymentQueryWrapper = new QueryWrapper<>();
                    separatePaymentQueryWrapper.eq("main_order_no", detail.getMainOrderNo());
                    List<SeparatePayment> separatePayments = separatePaymentMapper.selectList(separatePaymentQueryWrapper);
                    for (SeparatePayment temp : separatePayments) {
                        if (temp.getPayStatus() == 1) {
                            flag = false;
                        }
                    }
                }
                //如果是上牌车并且没有支付成功记录，减库存
                if (cars.get(0).getType() == 5 && flag) {
                    //远程调用第三方，减去库存
                }
                //订单状态变更？

                //记账

            }
        }
    }
}
