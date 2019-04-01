package com.sixeco.order.module.order.service.impl;

import com.google.common.collect.Iterables;
import com.sixeco.order.mapper.CarOrderItemMapper;
import com.sixeco.order.mapper.MainOrderMapper;
import com.sixeco.order.mapper.OtherOrderItemMapper;
import com.sixeco.order.mapper.SubOrderMapper;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.dto.MainOrderDTO;
import com.sixeco.order.module.order.Enum.OrderStatusEnum;
import com.sixeco.order.module.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 订单服务实现
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MainOrderMapper mainOrderMapper;

    @Autowired
    private SubOrderMapper subOrderMapper;

    @Autowired
    private CarOrderItemMapper carOrderItemMapper;

    @Autowired
    private OtherOrderItemMapper otherOrderItemMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object add(MainOrderDTO mainOrderDTO) {
        //TODO 验证入参UUID
        //判断订单类型
        //生成订单号
        //获取商品价格
        //总价计算
        //插入主表
        //生成子订单号
        //插入子表
        //插入商品表
        MainOrder mainOrder = MainOrder.builder()
                .mobile(mainOrderDTO.getMobile())
                .source(mainOrderDTO.getSource())
                .purchaserName(mainOrderDTO.getPurchaserName())
                .purchaserId(mainOrderDTO.getPurchaserId())
                .equipment(mainOrderDTO.getEquipment())
                .orderStatus(OrderStatusEnum.PENDING_PAYMENT.getCode())
                .idNumber(mainOrderDTO.getIdNumber())
                .remark(mainOrderDTO.getRemark())
                .build();
        mainOrderMapper.insertMainOrder(mainOrder);
        //根据id查询订单号？

        return mainOrderMapper.selectById(mainOrder.getId());
    }

    @Override
    public Object list() {
        List emptyList = Collections.EMPTY_LIST;
        boolean empty = Iterables.isEmpty(emptyList);
        return mainOrderMapper.selectById(3);
    }
}
