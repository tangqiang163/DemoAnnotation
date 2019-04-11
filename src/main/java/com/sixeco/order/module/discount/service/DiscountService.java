package com.sixeco.order.module.discount.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sixeco.order.base.context.PageInfo;
import com.sixeco.order.base.context.RtnInfo;
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
public interface DiscountService {

    // 校验活动
    RtnInfo checkDiscount();
}
