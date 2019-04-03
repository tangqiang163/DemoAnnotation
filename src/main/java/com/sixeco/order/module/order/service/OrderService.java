package com.sixeco.order.module.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sixeco.order.base.context.PageInfo;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.dto.MainOrderDTO;
import com.sixeco.order.model.dto.MainOrderUpdateDTO;
import com.sixeco.order.model.vo.MainOrderVO;

/**
 * 订单服务
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
public interface OrderService {

    MainOrder add(MainOrderDTO mainOrderDTO);

    IPage<MainOrder> list(PageInfo<MainOrder> pageInfo, MainOrder mainOrder);

    MainOrderVO detail(String mainOrderNo);

    int updateStatus(Long id, Integer status);

    int update(MainOrderUpdateDTO mainOrderUpdateDTO);
}
