package com.sixeco.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sixeco.order.model.MainOrder;
import com.sixeco.order.model.vo.MainOrderVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单Mapper
 *
 * @author: Zhanghe
 * @date: 2019-03-28
 */
@Mapper
public interface MainOrderMapper extends BaseMapper<MainOrder> {

    void insertMainOrder(MainOrder mainOrder);

    MainOrderVO orderDetail(String mainOrderNo);
}
