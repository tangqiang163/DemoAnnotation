package com.sixeco.order.module.discount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Strings;
import com.sixeco.order.base.context.PageInfo;
import com.sixeco.order.base.context.RtnInfo;
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
import com.sixeco.order.module.discount.service.DiscountService;
import com.sixeco.order.module.insure.service.InsureService;
import com.sixeco.order.module.order.constant.OrderStatusEnum;
import com.sixeco.order.module.order.constant.OrderTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单服务实现
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Service
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    @Override
    public RtnInfo checkDiscount() {
        return null;
    }
}
