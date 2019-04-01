package com.sixeco.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sixeco.order.model.MainOrder;
import org.springframework.stereotype.Repository;

/**
 * 订单Mapper
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Repository
public interface MainOrderMapper extends BaseMapper<MainOrder> {

    MainOrder insertMainOrder();
}
