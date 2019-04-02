package com.sixeco.order.module.order.service;

import com.sixeco.order.model.dto.MainOrderDTO;

/**
 * 订单服务
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
public interface OrderService {

    Object add(MainOrderDTO mainOrderDTO);

    Object list();

    Object detail(Long id);

}
