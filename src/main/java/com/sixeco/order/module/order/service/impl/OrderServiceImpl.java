package com.sixeco.order.module.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Strings;
import com.sixeco.order.base.context.PageInfo;
import com.sixeco.order.mapper.CarOrderItemMapper;
import com.sixeco.order.mapper.MainOrderMapper;
import com.sixeco.order.mapper.OtherOrderItemMapper;
import com.sixeco.order.mapper.SubOrderMapper;
import com.sixeco.order.model.CarOrderItem;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.OtherOrderItem;
import com.sixeco.order.model.SubOrder;
import com.sixeco.order.model.dto.*;
import com.sixeco.order.model.vo.MainOrderVO;
import com.sixeco.order.module.order.constant.OrderStatusEnum;
import com.sixeco.order.module.order.constant.OrderTypeEnum;
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
    public MainOrder add(MainOrderDTO mainOrderDTO) {
        //插入主订单
        MainOrder mainOrder = MainOrder.builder()
                .mobile(mainOrderDTO.getMobile())
                .source(mainOrderDTO.getSource())
                .purchaserName(mainOrderDTO.getPurchaserName())
                .purchaserId(mainOrderDTO.getPurchaserId())
                .equipment(mainOrderDTO.getEquipment())
                .userIdTdc(mainOrderDTO.getUserIdTdc())
                .orderStatus(OrderStatusEnum.PENDING_PAYMENT.getCode())
                .idNumber(mainOrderDTO.getIdNumber())
                .remark(mainOrderDTO.getRemark())
                .splitFlag(mainOrderDTO.getSplitFlag())
                .splitPlan(mainOrderDTO.getSplitPlan()).build();
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
                    .receiverAddress(mainOrderDTO.getReceiverAddress())
                    .receiverMobile(mainOrderDTO.getReceiverMobile())
                    .receiverName(mainOrderDTO.getReceiverName()).build();
            subOrderMapper.insertSubOrder(subOrder);
            //根据id查询子订单号
            String subOrderNo = subOrderMapper.selectById(subOrder.getId()).getSubOrderNo();
            //判断订单类型 此处具体情况再做修改
            if (OrderTypeEnum.CAR.getCode().equals(subOrderDTO.getOrderType())) {
                //插入车
                for (CarOrderItemDTO carOrderItemDTO : subOrderDTO.getCarItems()) {
                    CarOrderItem carOrderItem = CarOrderItem.builder()
                            .subOrderNo(subOrderNo)
                            .purchaseCount(carOrderItemDTO.getPurchaseCount())
                            .type(carOrderItemDTO.getType())
                            .carBodyColor(carOrderItemDTO.getCarBodyColor())
                            .carInteriorColor(carOrderItemDTO.getCarInteriorColor())
                            .carPartInfo(carOrderItemDTO.getCarPartInfo())
                            .carRoofColor(carOrderItemDTO.getCarRoofColor())
                            .carVin(carOrderItemDTO.getCarVin())
                            .carVin(carOrderItemDTO.getCarVin())
                            .receiverAddress(mainOrderDTO.getReceiverAddress())
                            .receiverMobile(mainOrderDTO.getReceiverMobile())
                            .receiverName(mainOrderDTO.getReceiverName()).build();
                    carOrderItemMapper.insertCarOrderItem(carOrderItem);
                }
            } else {
                //插入其他商品
                for (OtherOrderItemDTO otherOrderItemDTO : subOrderDTO.getOtherItems()) {
                    OtherOrderItem otherOrderItem = OtherOrderItem.builder()
                            .subOrderNo(subOrderNo)
                            .purchaseCount(otherOrderItemDTO.getPurchaseCount())
                            .productCode(otherOrderItemDTO.getProductCode())
                            .productName(otherOrderItemDTO.getProductName())
                            .productAttr(otherOrderItemDTO.getProductAttr())
                            .drawBillFlag(otherOrderItemDTO.getDrawBillFlag())
                            .billCode(otherOrderItemDTO.getBillCode())
                            .supplierCode(otherOrderItemDTO.getSupplierCode())
                            .shippingWay(otherOrderItemDTO.getShippingWay())
                            .orderLogisticsId(otherOrderItemDTO.getOrderLogisticsId())
                            .taxOtherPrice(otherOrderItemDTO.getTaxOtherPrice())
                            .taxReason(otherOrderItemDTO.getTaxReason())
                            .shopId(otherOrderItemDTO.getShopId())
                            .discountFlag(otherOrderItemDTO.getDiscountFlag())
                            .accountType(otherOrderItemDTO.getAccountType())
                            .receiverCountry("0")
                            .receiverProvince(mainOrderDTO.getReceiverProvince())
                            .receiverCity(mainOrderDTO.getReceiverCity())
                            .receiverDistrict(mainOrderDTO.getReceiverDistrict())
                            .receiverAddress(mainOrderDTO.getReceiverAddress())
                            .receiverMobile(mainOrderDTO.getReceiverMobile())
                            .receiverName(mainOrderDTO.getReceiverName()).build();
                    otherOrderItemMapper.insertOtherOrderItem(otherOrderItem);
                }
            }
        }
        //返回插入数据
        return mainOrderMapper.selectById(mainOrder.getId());
    }

    @Override
    public IPage<MainOrder> list(PageInfo<MainOrder> pageInfo, MainOrder mainOrder) {
        //分页，条件查询
        QueryWrapper<MainOrder> wrapper = new QueryWrapper<>();
        if (!Strings.isNullOrEmpty(mainOrder.getMainOrderNo())) {
            wrapper.like("main_order_no", mainOrder.getMainOrderNo());
        }
        if (!Strings.isNullOrEmpty(mainOrder.getMobile())) {
            wrapper.like("mobile", mainOrder.getMobile());
        }
        if (!Strings.isNullOrEmpty(mainOrder.getIdNumber())) {
            wrapper.like("id_number", mainOrder.getIdNumber());
        }
        if (!Strings.isNullOrEmpty(mainOrder.getPurchaserId())) {
            wrapper.like("purchaser_id", mainOrder.getPurchaserId());
        }
        if (!Strings.isNullOrEmpty(mainOrder.getPurchaserName())) {
            wrapper.like("purchaser_name", mainOrder.getPurchaserName());
        }
        if (!Strings.isNullOrEmpty(mainOrder.getUserIdTdc())) {
            wrapper.like("user_id_tdc", mainOrder.getUserIdTdc());
        }
        return mainOrderMapper.selectPage(pageInfo.getPage(), wrapper);
    }

    @Override
    public MainOrderVO detail(String mainOrderNo) {
        return mainOrderMapper.orderDetail(mainOrderNo);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        return mainOrderMapper.updateById(MainOrder.builder().id(id).orderStatus(status).build());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(MainOrderUpdateDTO mainOrderUpdateDTO) {
        int result = 0;
        result += mainOrderMapper.updateById(MainOrder.builder()
                .id(mainOrderUpdateDTO.getId())
                .mobile(mainOrderUpdateDTO.getMobile())
                .idNumber(mainOrderUpdateDTO.getIdNumber()).build());
        if (mainOrderUpdateDTO.getSubOrders() != null) {
            for (SubOrderUpdateDTO subOrderUpdateDTO : mainOrderUpdateDTO.getSubOrders()) {
                result += subOrderMapper.updateById(SubOrder.builder()
                        .id(subOrderUpdateDTO.getId())
                        .receiverName(subOrderUpdateDTO.getReceiverName())
                        .receiverMobile(subOrderUpdateDTO.getReceiverMobile())
                        .receiverAddress(subOrderUpdateDTO.getReceiverAddress())
                        .storeName(subOrderUpdateDTO.getStoreName())
                        .storeCode(subOrderUpdateDTO.getStoreCode())
                        .merchantId(subOrderUpdateDTO.getMerchantId())
                        .merchantName(subOrderUpdateDTO.getMerchantName())
                        .payType(subOrderUpdateDTO.getPayType()).build());
            }
        }
        return result;
    }
}
