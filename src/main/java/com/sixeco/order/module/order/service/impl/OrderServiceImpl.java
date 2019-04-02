package com.sixeco.order.module.order.service.impl;

import com.google.common.collect.Iterables;
import com.sixeco.order.mapper.CarOrderItemMapper;
import com.sixeco.order.mapper.MainOrderMapper;
import com.sixeco.order.mapper.OtherOrderItemMapper;
import com.sixeco.order.mapper.SubOrderMapper;
import com.sixeco.order.model.CarOrderItem;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.SubOrder;
import com.sixeco.order.model.dto.CarOrderItemDTO;
import com.sixeco.order.model.dto.MainOrderDTO;
import com.sixeco.order.model.dto.SubOrderDTO;
import com.sixeco.order.module.order.Enum.OrderStatusEnum;
import com.sixeco.order.module.order.Enum.OrderTypeEnum;
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
        //根据id查询订单号
        String mainOrderNo = mainOrderMapper.selectById(mainOrder.getId()).getMainOrderNo();
        //插入子订单
        for (SubOrderDTO subOrderDTO : mainOrderDTO.getSubOrders()) {
            SubOrder subOrder = SubOrder.builder()
                    .mainOrderNo(mainOrderNo)
                    .orderType(subOrderDTO.getOrderType())
                    .merchantName(subOrderDTO.getMerchantName())
                    .merchantId(subOrderDTO.getMerchantId())
                    .receiverAddress(mainOrderDTO.getReceiverAddress())
                    .receiverMobile(mainOrderDTO.getReceiverMobile())
                    .receiverName(mainOrderDTO.getReceiverName())
                    .build();
            subOrderMapper.insertSubOrder(subOrder);
            //判断订单类型 此处具体情况再做修改
            if (OrderTypeEnum.CAR.getCode().equals(subOrderDTO.getOrderType())) {
                //插入商品
                for (CarOrderItemDTO carOrderItemDTO : subOrderDTO.getCarItems()) {
                    CarOrderItem carOrderItem = CarOrderItem.builder()
                            .carBodyColor(carOrderItemDTO.getCarBodyColor())
                            .carInteriorColor(carOrderItemDTO.getCarInteriorColor())
                            .carPartInfo(carOrderItemDTO.getCarPartInfo())
                            .carRoofColor(carOrderItemDTO.getCarRoofColor())
                            .carVin(carOrderItemDTO.getCarVin())
                            .carVin(carOrderItemDTO.getCarVin())
                            .receiverAddress(mainOrderDTO.getReceiverAddress())
                            .receiverMobile(mainOrderDTO.getReceiverMobile())
                            .receiverName(mainOrderDTO.getReceiverName())
                            .build();
                    carOrderItemMapper.insertCarOrderItem(carOrderItem);
                }
            } else if (OrderTypeEnum.SERVICE.getCode().equals(subOrderDTO.getOrderType())) {

            }
            //返回插入数据？

        }
        return mainOrderMapper.selectById(mainOrder.getId());
    }

    @Override
    public Object list() {
        List emptyList = Collections.EMPTY_LIST;
        boolean empty = Iterables.isEmpty(emptyList);
        return mainOrderMapper.selectById(3);
    }

    @Override
    public Object detail(String mainOrderNo) {
        return mainOrderMapper.orderDetail(mainOrderNo);
    }
}
