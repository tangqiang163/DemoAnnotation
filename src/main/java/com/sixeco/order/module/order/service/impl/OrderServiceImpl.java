package com.sixeco.order.module.order.service.impl;

import com.google.common.collect.Iterables;
import com.sixeco.order.mapper.CarOrderItemMapper;
import com.sixeco.order.mapper.MainOrderMapper;
import com.sixeco.order.mapper.OtherOrderItemMapper;
import com.sixeco.order.mapper.SubOrderMapper;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.dto.MainOrderDTO;
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
        MainOrder build = MainOrder.builder()
                .mainOrderNo("222")
                .mobile(mainOrderDTO.getMobile())
                .source("1")
                .purchaserName("张三")
                .purchaserId("7")
                .equipment(3)
                .orderStatus(1)
                .idNumber(mainOrderDTO.getIdNumber())
                .remark(mainOrderDTO.getRemark())
                .build();
        mainOrderMapper.insert(build);

        return build;
    }

    @Override
    public Object list() {
        List emptyList = Collections.EMPTY_LIST;
        boolean empty = Iterables.isEmpty(emptyList);
        //MainOrder mainOrder = mainOrderMapper.selectById(3);
        return mainOrderMapper.selectById(3);
    }
}
