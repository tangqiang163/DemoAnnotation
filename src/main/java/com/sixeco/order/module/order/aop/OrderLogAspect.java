package com.sixeco.order.module.order.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sixeco.order.base.aop.BaseAspect;
import com.sixeco.order.base.context.RtnInfo;
import com.sixeco.order.mapper.MainOrderMapper;
import com.sixeco.order.mapper.OrderLogMapper;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.OrderLog;
import com.sixeco.order.model.dto.MainOrderUpdateDTO;
import com.sixeco.order.module.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单日志切面
 *
 * @author: Zhanghe
 * @date: 2019-04-03
 */
@Aspect
@Component
@Slf4j
public class OrderLogAspect extends BaseAspect {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MainOrderMapper mainOrderMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    @AfterReturning(pointcut = "@annotation(com.sixeco.order.base.annotation.InsertOrderLog)", returning="rvt")
    public void insertOrderLog(JoinPoint joinPoint, Object rvt) throws JsonProcessingException {
        RtnInfo<MainOrder> r = (RtnInfo<MainOrder>) rvt;
        MainOrder order = r.getData();
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(orderService.detail(order.getMainOrderNo()));
        orderLogMapper.insert(OrderLog.builder()
                .mainOrderNo(order.getMainOrderNo())
                .orderData(data)
                .createBy(order.getCreateBy()).build());
    }

    @AfterReturning("@annotation(com.sixeco.order.base.annotation.UpdateOrderLog)")
    public void updateOrderLog(JoinPoint joinPoint) throws JsonProcessingException {
        Object[] args = joinPoint.getArgs();
        MainOrderUpdateDTO o = (MainOrderUpdateDTO) args[0];
        MainOrder order = mainOrderMapper.selectById(o.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(orderService.detail(order.getMainOrderNo()));
        orderLogMapper.insert(OrderLog.builder()
                .mainOrderNo(order.getMainOrderNo())
                .orderData(data)
                .createBy(order.getCreateBy()).build());
    }
}
