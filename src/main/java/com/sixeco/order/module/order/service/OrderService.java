package com.sixeco.order.module.order.service;

import com.sixeco.order.base.context.PageInfo;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.dto.MainOrderDTO;

/**
 * 订单服务
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
public interface OrderService {

    Object add(MainOrderDTO mainOrderDTO);

    Object list(PageInfo<MainOrder> pageInfo, MainOrder mainOrder);

    Object detail(String mainOrderNo);

}
