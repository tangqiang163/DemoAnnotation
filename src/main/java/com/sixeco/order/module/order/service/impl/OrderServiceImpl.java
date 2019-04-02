package com.sixeco.order.module.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Iterables;
import com.sixeco.order.base.context.PageInfo;
import com.sixeco.order.mapper.CarOrderItemMapper;
import com.sixeco.order.mapper.MainOrderMapper;
import com.sixeco.order.mapper.OtherOrderItemMapper;
import com.sixeco.order.mapper.SubOrderMapper;
import com.sixeco.order.model.CarOrderItem;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.OtherOrderItem;
import com.sixeco.order.model.SubOrder;
import com.sixeco.order.model.dto.CarOrderItemDTO;
import com.sixeco.order.model.dto.MainOrderDTO;
import com.sixeco.order.model.dto.OtherOrderItemDTO;
import com.sixeco.order.model.dto.SubOrderDTO;
import com.sixeco.order.module.order.Enum.OrderStatusEnum;
import com.sixeco.order.module.order.Enum.OrderTypeEnum;
import com.sixeco.order.module.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
        //TODO 验证入参UUID log处理
        //计算购买商品总数
        //计算主订单和子订单金额，根据明细金额计算
        int count = 0;
        BigDecimal sellPrice = new BigDecimal(0);
        BigDecimal price = new BigDecimal(0);
        for (SubOrderDTO s : mainOrderDTO.getSubOrders()) {
            BigDecimal subSellPrice = new BigDecimal(0);
            BigDecimal subPrice = new BigDecimal(0);
            if (s.getCarItems() != null) {
                for (CarOrderItemDTO c : s.getCarItems()) {
                    count += c.getPurchaseCount();
                    subSellPrice.add(c.getProductSellPrice());
                    subPrice.add(c.getProductPrice());
                }
            }
            if (s.getOtherItems() != null) {
                for (OtherOrderItemDTO o : s.getOtherItems()) {
                    count += o.getPurchaseCount();
                    subSellPrice.add(o.getProductSellPrice());
                    subPrice.add(o.getProductPrice());
                }
            }
            sellPrice.add(subSellPrice);
            s.setSubAmount(sellPrice);
            price.add(subPrice);
            s.setSubOriginalAmount(price);
        }
        //插入主订单
        MainOrder mainOrder = MainOrder.builder()
                .mobile(mainOrderDTO.getMobile())
                .source(mainOrderDTO.getSource())
                .productCount(count)
                .purchaserName(mainOrderDTO.getPurchaserName())
                .purchaserId(mainOrderDTO.getPurchaserId())
                .equipment(mainOrderDTO.getEquipment())
                .userIdTdc(mainOrderDTO.getUserIdTdc())
                .orderStatus(OrderStatusEnum.PENDING_PAYMENT.getCode())
                .originalAmount(price)
                .amount(sellPrice)
                .idNumber(mainOrderDTO.getIdNumber())
                .remark(mainOrderDTO.getRemark())
                .splitFlag(mainOrderDTO.getSplitFlag())
                .splitPlan(mainOrderDTO.getSplitPlan())
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
                    .payType(subOrderDTO.getPayType())
                    .storeCode(subOrderDTO.getStoreCode())
                    .storeName(subOrderDTO.getStoreName())
                    .subOriginalAmount(subOrderDTO.getSubOriginalAmount())
                    .subAmount(subOrderDTO.getSubAmount())
                    .carriagePrice(subOrderDTO.getCarriagePrice())
                    .receiverAddress(mainOrderDTO.getReceiverAddress())
                    .receiverMobile(mainOrderDTO.getReceiverMobile())
                    .receiverName(mainOrderDTO.getReceiverName())
                    .build();
            subOrderMapper.insertSubOrder(subOrder);
            //判断订单类型 此处具体情况再做修改
            if (OrderTypeEnum.CAR.getCode().equals(subOrderDTO.getOrderType())) {
                //插入车
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
                //插入其他商品
                for (OtherOrderItemDTO otherOrderItemDTO : subOrderDTO.getOtherItems()) {
                    OtherOrderItem otherOrderItem = OtherOrderItem.builder()
                            .receiverAddress(mainOrderDTO.getReceiverAddress())
                            .receiverMobile(mainOrderDTO.getReceiverMobile())
                            .receiverName(mainOrderDTO.getReceiverName())
                            .build();
                    otherOrderItemMapper.insertOtherOrderItem(otherOrderItem);
                }
            }
        }
        //返回插入数据
        return mainOrderMapper.selectById(mainOrder.getId());
    }

    @Override
    public Object list(PageInfo<MainOrder> pageInfo, MainOrder mainOrder) {
        //分页，条件查询
        QueryWrapper<MainOrder> wrapper = new QueryWrapper<MainOrder>()
                .like("main_order_no", mainOrder.getMainOrderNo());
        return mainOrderMapper.selectPage(pageInfo.getPage(), wrapper);
    }

    @Override
    public Object detail(String mainOrderNo) {
        return mainOrderMapper.orderDetail(mainOrderNo);
    }
}
