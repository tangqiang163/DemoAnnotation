package com.sixeco.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sixeco.order.model.CarOrderItem;

/**
 * 车辆明细Mapper
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
public interface CarOrderItemMapper extends BaseMapper<CarOrderItem> {

    void insertCarOrderItem(CarOrderItem carOrderItem);
}
