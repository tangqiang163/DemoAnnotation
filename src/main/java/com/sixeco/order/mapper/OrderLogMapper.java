package com.sixeco.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sixeco.order.model.OrderLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单日志Mapper
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Mapper
public interface OrderLogMapper extends BaseMapper<OrderLog> {
}
