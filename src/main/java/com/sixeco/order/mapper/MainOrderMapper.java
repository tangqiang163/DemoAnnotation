package com.sixeco.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sixeco.order.model.MainOrder;

/**
 * 订单Mapper
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
public interface MainOrderMapper extends BaseMapper<MainOrder> {

    MainOrder insertMainOrder();
}
